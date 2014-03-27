/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.TxMagicCardException;
import com.cppoon.tencent.magiccard.http.client.HttpClientFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionAuthStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.AccountHomePageParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.LoginForm;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.LoginPageParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.AccountHomePageParser20140318;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.ExchangeCardBoxParser20140320;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.LoginPageParser20140319;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.SafeBoxParser20140320;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionImpl implements Session {

	private static final Logger log = LoggerFactory.getLogger(SessionImpl.class);
	
	/**
	 * Default user agent string.
	 */
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 4.0.3; de-de; Galaxy S II Build/GRJ22) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
	
	private final HttpClientFactory httpClientFactory;

	/**
	 * Cookie store for this session. This represents a single user identity.
	 */
	private CookieStore cookieStore;
	
	/**
	 * The session ID returned from the web server.
	 */
	private String sid;

	private SessionAuthStatus authStatus;

	private LoginPageParser loginPageParser;

	private AccountHomePageParser accountHomePageParser;

	// credential begins

	String username;

	String password;

	// credential ends

	/**
	 * Builds an instance of session.
	 * 
	 * @param username
	 * @param password
	 */
	public SessionImpl(final HttpClientFactory httpClientFactory,
			String username, String password) {

		this.httpClientFactory = httpClientFactory;

		// save the credential.
		this.username = username;
		this.password = password;

		loginPageParser = new LoginPageParser20140319();
		accountHomePageParser = new AccountHomePageParser20140318();

		reset();
	}

	/**
	 * Reset the internal authentication status.
	 */
	protected void reset() {

		this.authStatus = SessionAuthStatus.UNAUTHENTICATED;

		cookieStore = new BasicCookieStore();

	}

	@Override
	public AccountOverview getAccountOverview() {

		// trigger authenticate if not.
		if (authStatus != SessionAuthStatus.AUTHENTICATED) {
			triggerAuthentication();
		}
		
		// send a request to retrieve the account overview.
		return doGetAccountOverview();
		
	}

	
	private AccountOverview doGetAccountOverview() {
		
		// build a get request
		URI uri = null;
		try {
			uri = new URIBuilder(QzappMagicCardConstants.APP_URL_MAIN_PAGE)
				.addParameter(QzappMagicCardConstants.SESSION_ID_NAME, sid).build();
		} catch (URISyntaxException e) {
			log.error("unexpected error when building magic card main page URL", e);
		}
		HttpGet request = new HttpGet(uri);
		this.sanitizeUriRequest(request);
		
		
		// send it
		HttpClient httpClient = getHttpClient();
		HttpContext httpContext = getHttpContext();
		try {
			
			HttpResponse response = httpClient.execute(request, httpContext);

			String html = EntityUtils.toString(response.getEntity());
			return accountHomePageParser.parse(html);
			
		} catch (ClientProtocolException e) {
			log.warn("error when reading game main page", e);
		} catch (IOException e) {
			log.warn("error when reading game main page", e);
		}
		
		return null;
	}

	protected void triggerAuthentication() {

		// do nothing if already authenticated.
		if (authStatus == SessionAuthStatus.AUTHENTICATED)
			return;

		// 
		String redirectedUrl = null;
		
		
		this.authStatus = SessionAuthStatus.AUTHENTICATING;

		
		try {
			HttpClient httpClient = this.getHttpClient();

			// do login
			HttpPost loginRequest = buildLoginRequest(username, password);

			HttpContext httpContext = getHttpContext();
			HttpResponse response = httpClient.execute(loginRequest,
					httpContext);

			// check the redirected location header.
			if (response.getStatusLine().getStatusCode() == 301
					|| response.getStatusLine().getStatusCode() == 302) {

				Header header = response.getFirstHeader("Location");
				if (header == null) {
					return;
				}

				// remember the sid (session ID).
				String sid = extractSidFromUrl(header.getValue());
				if (sid == null) {
					log.warn("failed to extract session ID from the responded URL after login " + header.getValue());
					return;
				} else {
					// save the sid.
					this.sid = sid;
					redirectedUrl = header.getValue();
				}

			} else {
				
				log.warn("301/302 redirection is expected after login, however it is not (status code is "
						+ response.getStatusLine().getStatusCode() + ")");

				return;
			}
			
			
			// manually continue the redirection.
			log.trace("continue redirected URL after login");
			HttpGet getRequest = new HttpGet(redirectedUrl);
			sanitizeUriRequest(getRequest);
			response = httpClient.execute(getRequest, httpContext);
			
			// done!
			log.debug("user (qq={}) authenticated", username);
			this.authStatus = SessionAuthStatus.AUTHENTICATED;

		} catch (ClientProtocolException e) {
			log.warn("an error has occurred when performing login", e);
		} catch (IOException e) {
			log.warn("an error has occurred when performing login", e);
		} finally {
			
			// at this stage, we expect the session is authenticated, 
			// if not, update the authenticate status as "Unauthenticated".
			if (this.authStatus != SessionAuthStatus.AUTHENTICATED) {
				authStatus = SessionAuthStatus.UNAUTHENTICATED;
			}
			
		}

	}

	/**
	 * Extract the SID from the specified URL.
	 * 
	 * @param url
	 *            the URL to extract.
	 * @return the SID.
	 */
	private String extractSidFromUrl(String url) {
		
		if (url == null) return null;
		
		try {
			
			List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(url), "UTF-8");
			// look for the "sid" parameter
			if (nvps != null && !nvps.isEmpty()) {
				for (NameValuePair nvp : nvps) {
					if (QzappMagicCardConstants.SESSION_ID_NAME.equals(nvp.getName())) {
						return nvp.getValue();
					}
				}
			}
			
		} catch (URISyntaxException e) {
			log.warn("failed to parse query string parameters from the URL to look up SID from URL " + url, e);
			return null;
		}
		
		// not found
		return null;
	}

	private HttpPost buildLoginRequest(String username, String password)
			throws ClientProtocolException, IOException {

		// we need to fetch the login page, as there are parameters there.

		LoginForm loginForm = readLoginPageHtmlForm();

		if (loginForm == null) {
			// XXX 2014-03-19 cyril: reports it is caused by improper HTML
			// response, the layout maybe changed.
			return null;
		}

		// build a POST request. we use the fetched login page information
		// to build it.
		HttpPost request = new HttpPost(loginForm.getFormSubmitUrl());

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> e : loginForm.getFormFields().entrySet()) {
			nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
		}
		// add our user name and password pair
		nvps.add(new BasicNameValuePair("qq", username));	// user name
		nvps.add(new BasicNameValuePair("pwd", password));	// password
		request.setEntity(new UrlEncodedFormEntity(nvps, Charset
				.forName("UTF-8")));

		this.sanitizeUriRequest(request);

		RequestConfig config = RequestConfig.custom()
				.setRedirectsEnabled(false).build();
		request.setConfig(config);

		return request;
	}

	protected LoginForm readLoginPageHtmlForm() throws ClientProtocolException,
			IOException {

		//
		// read the login page.
		//

		HttpGet request = new HttpGet("http://qzone.z.qq.com");
		sanitizeUriRequest(request);

		HttpClient httpClient = getHttpClient();
		HttpContext httpContext = getHttpContext();

		HttpResponse response = httpClient.execute(request, httpContext);

		// return null if no proper response
		if (response.getStatusLine().getStatusCode() != 200) {
			return null;
		}

		//
		// read the complete HTML and parse it.
		//
		String html = EntityUtils.toString(response.getEntity());
		LoginForm loginForm = parseLoginFormFromHtml(html);
		return loginForm;

	}

	private LoginForm parseLoginFormFromHtml(String html) {

		return loginPageParser.parse(html);

	}

	@Override
	public SessionAuthStatus getAuthStatus() {
		return authStatus;
	}
	
	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.vendor.qzapp.Session#getSafeBoxCards()
	 */
	@Override
	public List<ExchangeBoxSlot> getSafeBoxSlots() {

		// trigger authenticate if not.
		if (authStatus != SessionAuthStatus.AUTHENTICATED) {
			triggerAuthentication();
		}
		
		ArrayList<ExchangeBoxSlot> ret = new ArrayList<ExchangeBoxSlot>();
		
		for (int page = 0; ; page++) {
			
			log.trace("retriving page {} of safe box", page);
			
			HttpGet request = new HttpGet(UrlUtil.buildSafeBoxUrl(getSid(), page));
			this.sanitizeUriRequest(request);
			
			// send it
			HttpClient httpClient = getHttpClient();
			HttpContext httpContext = getHttpContext();
			try {
				
				HttpResponse response = httpClient.execute(request, httpContext);

				String html = EntityUtils.toString(response.getEntity());
				
				SafeBoxParser20140320 parser = new SafeBoxParser20140320();
				CardBoxInfo safeBoxInfo = parser.parse(html);
				
				List<ExchangeBoxSlot> slots = safeBoxInfo.getSlots();
				
				// merge the slots to avoid duplicate
				mergeSlotsWithoutDuplicateSlotId(slots, ret);
				
				
				// look for stopping condition.
				if (safeBoxInfo.getPageLinks().size() == 1 
						|| safeBoxInfo.getPageLinks().get(safeBoxInfo.getPageLinks().size()-1).isCurrent()) {
					log.trace("no more safe box page found, iteration done");
					// there is only one page, or the last page is encountered,
					// then we stop iterating.
					break;
				}
				
			} catch (ClientProtocolException e) {
				log.warn("error when reading safebox page", e);
				throw new TxMagicCardException(e);
			} catch (IOException e) {
				log.warn("error when reading safebox page", e);
				throw new TxMagicCardException(e);
			}
			
		}
		
		return ret;
		
	}
	
	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.vendor.qzapp.Session#getExchangeBoxSlots()
	 */
	@Override
	public List<ExchangeBoxSlot> getExchangeBoxSlots() {
		
		// trigger authenticate if not.
		if (authStatus != SessionAuthStatus.AUTHENTICATED) {
			triggerAuthentication();
		}
		
		ArrayList<ExchangeBoxSlot> ret = new ArrayList<ExchangeBoxSlot>();
		
		log.trace("retriving exchange box page");
		
		HttpGet request = new HttpGet(UrlUtil.buildExchangeBoxUrl(getSid()));
		this.sanitizeUriRequest(request);
		
		
		// send it
		HttpClient httpClient = getHttpClient();
		HttpContext httpContext = getHttpContext();
		try {
			
			HttpResponse response = httpClient.execute(request, httpContext);

			String html = EntityUtils.toString(response.getEntity());
			
			ExchangeCardBoxParser20140320 parser = new ExchangeCardBoxParser20140320();
			CardBoxInfo exchangeBoxParser = parser.parse(html);
			
			List<ExchangeBoxSlot> slots = exchangeBoxParser.getSlots();
			
			// merge the slots to avoid duplicate
			mergeSlotsWithoutDuplicateSlotId(slots, ret);
			
		} catch (ClientProtocolException e) {
			log.warn("error when reading safebox page", e);
			throw new TxMagicCardException(e);
		} catch (IOException e) {
			log.warn("error when reading safebox page", e);
			throw new TxMagicCardException(e);
		}
			
		return ret;
		
	}

	protected void mergeSlotsWithoutDuplicateSlotId(List<ExchangeBoxSlot> source,
			List<ExchangeBoxSlot> target) {
		
		// first, collect all card Ids in the target list.
		Set<Integer> existingSlotIds = new HashSet<Integer>();
		for (ExchangeBoxSlot existingSlot : target) {
			existingSlotIds.add(existingSlot.getSlotId());
		}
		
		// then, add the source slots to the target list, if duplicated 
		// card ID is found, discard them.
		for (ExchangeBoxSlot srcSlot : source) {
			
			// discard duplicate
			if (existingSlotIds.contains(srcSlot.getSlotId())) {
				continue;
			}
			
			target.add(srcSlot);
		}
		
	}

	/**
	 * Returns the user agent.
	 * 
	 * @return
	 */
	protected String getUserAgent() {
		return DEFAULT_USER_AGENT;
	}

	protected void sanitizeUriRequest(HttpRequestBase request) {

		request.setHeader("User-Agent", getUserAgent());

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(true)
				.build();
		request.setConfig(config);

	}

	protected HttpClient getHttpClient() {

		return httpClientFactory.getHttpClient();

	}

	protected HttpContext getHttpContext() {
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE,
				this.cookieStore);
		return httpContext;
	}
	
	protected String getSid() {
		return sid;
	}

}
