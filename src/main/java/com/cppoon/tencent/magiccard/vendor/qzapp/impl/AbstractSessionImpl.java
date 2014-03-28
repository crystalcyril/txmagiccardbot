/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.impl;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.cppoon.tencent.magiccard.http.client.HttpClientFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionAuthStatus;

/**
 * Abstract implementation of <code>Session</code>.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public abstract class AbstractSessionImpl implements Session {

	/**
	 * Default user agent string.
	 */
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 4.0.3; de-de; Galaxy S II Build/GRJ22) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

	/**
	 * Default value for HTTP header <strong>Accept-Language</strong>
	 */
	private static final String DEFAULT_ACCEPT_LANGUAGE = "en-US,en;q=0.5";
	
	/**
	 * Default value for HTTP header <strong>Accept</strong>
	 */
	private static final String DEFAULT_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	
	/**
	 * Authentication status.
	 */
	protected SessionAuthStatus authStatus;

	/**
	 * Cookie store for this session. This represents a single user identity.
	 */
	protected CookieStore cookieStore;

	private final HttpClientFactory httpClientFactory;

	/**
	 * 
	 * @param httpClientFactory
	 */
	public AbstractSessionImpl(final HttpClientFactory httpClientFactory) {
		super();

		this.httpClientFactory = httpClientFactory;
	}

	/**
	 * Returns the user agent.
	 * <p>
	 * 
	 * Current implementation always return {@link #DEFAULT_USER_AGENT}.
	 * 
	 * @return
	 */
	protected String getUserAgent() {
		return DEFAULT_USER_AGENT;
	}

	/**
	 * Returns the HTTP header <strong>Accept</strong> for sending 
	 * HTTP request to the remote server.
	 * <p>
	 * 
	 * Current implementation always return {@link #DEFAULT_ACCEPT}.
	 * 
	 * @return
	 */
	protected String getAccept() {
		return DEFAULT_ACCEPT;
	}
	
	/**
	 * Returns the HTTP header <strong>Accept-Language</strong> for sending 
	 * HTTP request to the remote server.
	 * <p>
	 * 
	 * Current implementation always return {@link #DEFAULT_ACCEPT_LANGUAGE}.
	 * 
	 * @return
	 */
	protected String getAcceptLanguage() {
		return DEFAULT_ACCEPT_LANGUAGE;
	}
	
	/**
	 * Obtains an instance of http client.
	 * 
	 * @return
	 */
	protected HttpClient getHttpClient() {

		return httpClientFactory.getHttpClient();

	}

	/**
	 * Obtains a <code>HttpContext</code> for sending HTTP requests.
	 * <p>
	 * 
	 * Current implementation will return the cookie store.
	 * 
	 * @return
	 */
	protected HttpContext getHttpContext() {
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE,
				this.cookieStore);
		return httpContext;
	}

	/**
	 * Execute the HTTP request and return the response.
	 * <p>
	 * 
	 * This method will properly configure the request to ensure it looks as if
	 * it is sending from a mobile device.
	 * 
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected HttpResponse executeRequest(HttpRequestBase request)
			throws ClientProtocolException, IOException {

		// make sure the request conforms to this client's requirement.
		sanitizeUriRequest(request);

		HttpClient httpClient = getHttpClient();
		HttpContext httpContext = getHttpContext();

		// execute the request.
		HttpResponse response = httpClient.execute(request, httpContext);

		return response;

	}

	/**
	 * Configure the request to make it looks as if it is sending from a mobile
	 * device.
	 * <p>
	 * 
	 * Current implementation will:
	 * 
	 * <ol>
	 * <li>Set the <code>User-Agent</code> header to a mobile browser.</li>
	 * <li>Enable redirection in the http request.</li>
	 * </ol>
	 * 
	 * @param request
	 *            the request to sanitize.
	 */
	protected void sanitizeUriRequest(HttpRequestBase request) {

		// very important. need to set the user agent to a mobile version
		// of browser, otherwise the response is not correct.
		request.setHeader(HttpHeaders.USER_AGENT, getUserAgent());
		
		// the value of "Accept"
		request.setHeader(HttpHeaders.ACCEPT, getAccept());

		// the value of "Accept-Language"
		request.setHeader(HttpHeaders.ACCEPT_LANGUAGE, getAcceptLanguage());

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(true)
				.build();
		request.setConfig(config);

	}

}
