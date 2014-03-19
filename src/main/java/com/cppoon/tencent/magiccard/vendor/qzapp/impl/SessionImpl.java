/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.cppoon.tencent.magiccard.http.client.HttpClientFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionAuthStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.AccountHomePageParser;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionImpl implements Session {

	/**
	 * Default user agent string.
	 */
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 4.0.3; de-de; Galaxy S II Build/GRJ22) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

	private final HttpClientFactory httpClientFactory;

	private CookieStore cookieStore;

	private SessionAuthStatus authStatus;
	
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

		return null;
	}

	protected void triggerAuthentication() {

		// do nothing if already authenticated.
		if (authStatus == SessionAuthStatus.AUTHENTICATED)
			return;

//		HttpPost loginRequest = buildLoginRequest(username, password);

	}

	private HttpPost buildLoginRequest(String username, String password)
			throws ClientProtocolException, IOException {

		// we need to fetch the login page, as there are parameters there.

		Map<String, String> values = readLoginPageHtmlForm();

		// FIXME not complete
		return null;
	}

	protected Map<String, String> readLoginPageHtmlForm()
			throws ClientProtocolException, IOException {

		//
		// read the login page.
		//
		
		HttpGet request = new HttpGet("http://qzone.z.qq.com");
		sanitizeUriRequest(request);

		HttpClient httpClient = getHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE,
				this.cookieStore);

		HttpResponse response = httpClient.execute(request, httpContext);

		// return null if no proper response
		if (response.getStatusLine().getStatusCode() != 200) {
			return null;
		}

		//
		// read the complete HTML and parse it.
		//
		String html = EntityUtils.toString(response.getEntity());
		Map<String, String> attrs = parseLoginFormFromHtml(html);
		return attrs;

	}

	private Map<String, String> parseLoginFormFromHtml(String html) {

		Hashtable<String, String> ret = new Hashtable<String, String>();

		return ret;

	}

	@Override
	public SessionAuthStatus getAuthStatus() {
		return authStatus;
	}

	/**
	 * Returns the user agent.
	 * 
	 * @return
	 */
	protected String getUserAgent() {
		return DEFAULT_USER_AGENT;
	}

	protected void sanitizeUriRequest(HttpUriRequest request) {

		request.setHeader("User-Agent", getUserAgent());

	}

	protected HttpClient getHttpClient() {

		return httpClientFactory.getHttpClient();

	}

}
