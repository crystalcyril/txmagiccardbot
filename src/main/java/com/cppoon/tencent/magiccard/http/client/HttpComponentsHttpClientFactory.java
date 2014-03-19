/**
 * 
 */
package com.cppoon.tencent.magiccard.http.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class HttpComponentsHttpClientFactory implements HttpClientFactory {

	HttpClient httpClient;

	PoolingHttpClientConnectionManager clientConnectionManager;

	public HttpComponentsHttpClientFactory() {
		super();

		clientConnectionManager = new PoolingHttpClientConnectionManager();

		httpClient = HttpClientBuilder.create()
				.setConnectionManager(clientConnectionManager)
				.setRedirectStrategy(new LaxRedirectStrategy())
				.build();

	}

	@Override
	public HttpClient getHttpClient() {
		return httpClient;
	}

}
