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
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.CancelSynthesisResult;
import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.StealStoveResult;
import com.cppoon.tencent.magiccard.TxMagicCardException;
import com.cppoon.tencent.magiccard.http.client.HttpClientFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionAuthStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.SynthesizeResult;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.AccountHomePageParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardRefineParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.LoginForm;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.LoginPageParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.SynthesizeCardInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.AccountHomePageParser20140318;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.CardRefineParser20140328;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.ExchangeCardBoxParser20140320;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.LoginPageParser20140319;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.SafeBoxParser20140320;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionImpl extends AbstractSessionImpl implements Session {

	private static final Logger log = LoggerFactory
			.getLogger(SessionImpl.class);

	private CardThemeManager cardThemeManager;

	private CardManager cardManager;

	/**
	 * The session ID returned from the web server.
	 */
	private String sid;

	private LoginPageParser loginPageParser;

	private AccountHomePageParser accountHomePageParser;

	private CardRefineParser cardRefineParser;

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
			final String username, final String password) {

		super(httpClientFactory);

		// save the credential.
		this.username = username;
		this.password = password;

		loginPageParser = new LoginPageParser20140319();
		accountHomePageParser = new AccountHomePageParser20140318();
		cardRefineParser = new CardRefineParser20140328();

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

		ensureAuthentication();

		// send a request to retrieve the account overview.
		return doGetAccountOverview();

	}

	private void ensureAuthentication() {

		// trigger authenticate if not.
		if (authStatus != SessionAuthStatus.AUTHENTICATED) {
			triggerAuthentication();
		}

	}

	private AccountOverview doGetAccountOverview() {

		// build a get request
		URI uri = null;
		try {
			uri = new URIBuilder(QzappMagicCardConstants.APP_URL_MAIN_PAGE)
					.addParameter(QzappMagicCardConstants.SESSION_ID_NAME, sid)
					.build();
		} catch (URISyntaxException e) {
			log.error(
					"unexpected error when building magic card main page URL",
					e);
			throw new TxMagicCardException("error building account overview URL", e);
		}
		HttpGet request = new HttpGet(uri);
		request.setHeader(HttpHeaders.REFERER, uri.toString());
		

		// send it
		try {
			HttpResponse response = executeRequest(request);

			String html = EntityUtils.toString(response.getEntity());
			AccountOverview ret = accountHomePageParser.parse(html);
			
			if (ret == null) {
				log.error("error reading account overview page of username {}, content=[[{}]]",
						username, html);
			}
			
			return ret;

		} catch (ClientProtocolException e) {
			log.warn("error when reading game main page", e);
			throw new TxMagicCardException("error when reading game main page", e);
		} catch (IOException e) {
			log.warn("error when reading game main page", e);
			throw new TxMagicCardException("error when reading game main page", e);
		}

	}

	protected void triggerAuthentication() {

		// do nothing if already authenticated.
		if (authStatus == SessionAuthStatus.AUTHENTICATED)
			return;

		//
		String redirectedUrl = null;

		this.authStatus = SessionAuthStatus.AUTHENTICATING;

		try {
			// do login
			HttpPost loginRequest = buildLoginRequest(username, password);

			HttpResponse response = executeRequest(loginRequest, false);

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
					log.warn("failed to extract session ID from the responded URL after login "
							+ header.getValue());
					return;
				} else {
					// save the sid.
					this.sid = sid;
					redirectedUrl = header.getValue();
				}

			} else {

				log.warn("301/302 redirection is expected after login, however it is not (status code is "
						+ response.getStatusLine().getStatusCode() + ")");
				
				// peek into the response
				try {
					log.trace("peeking the unexpected response...");
					String unexpectedResponse = EntityUtils.toString(response.getEntity());
					log.warn("unexpected response entity: [[{}]]", unexpectedResponse);
				} catch (Throwable t) {
					// ignore errors.
				}

				return;
			}

			// manually continue the redirection.
			log.trace("continue redirected URL after login");
			HttpGet getRequest = new HttpGet(redirectedUrl);

			response = executeRequest(getRequest);

			// done!
			log.debug("user (qq={}) authenticated", username);
			this.authStatus = SessionAuthStatus.AUTHENTICATED;

		} catch (ClientProtocolException e) {
			log.warn("an error has occurred when performing login", e);
			throw new TxMagicCardException("error authenticating user "
					+ username, e);
		} catch (IOException e) {
			log.warn("an error has occurred when performing login", e);
			throw new TxMagicCardException("error authenticating user "
					+ username, e);
		} finally {

			// at this stage, we expect the session is authenticated,
			// if not, update the authenticate status as "Unauthenticated".
			if (this.authStatus != SessionAuthStatus.AUTHENTICATED) {
				authStatus = SessionAuthStatus.UNAUTHENTICATED;
				
				log.error("unable to authenticate user {}", username);
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

		if (url == null)
			return null;

		try {

			List<NameValuePair> nvps = URLEncodedUtils.parse(new URI(url),
					"UTF-8");
			// look for the "sid" parameter
			if (nvps != null && !nvps.isEmpty()) {
				for (NameValuePair nvp : nvps) {
					if (QzappMagicCardConstants.SESSION_ID_NAME.equals(nvp
							.getName())) {
						return nvp.getValue();
					}
				}
			}

		} catch (URISyntaxException e) {
			log.warn(
					"failed to parse query string parameters from the URL to look up SID from URL "
							+ url, e);
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
		nvps.add(new BasicNameValuePair("qq", username)); // user name
		nvps.add(new BasicNameValuePair("pwd", password)); // password
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

		HttpResponse response = executeRequest(request);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.vendor.qzapp.Session#getSafeBoxCards()
	 */
	@Override
	public List<ExchangeBoxSlot> getSafeBoxSlots() {

		// trigger authenticate if not.
		ensureAuthentication();

		ArrayList<ExchangeBoxSlot> ret = new ArrayList<ExchangeBoxSlot>();

		for (int page = 0;; page++) {

			log.trace("retriving page {} of safe box", page);

			// build the request for reading safe box
			HttpGet request = new HttpGet(UrlUtil.buildSafeBoxUrl(getSid(),
					page));
			request.setHeader(HttpHeaders.REFERER, UrlUtil.buildExchangeBoxUrl(getSid()));

			// send it
			try {

				HttpResponse response = executeRequest(request);

				String html = EntityUtils.toString(response.getEntity());

				SafeBoxParser20140320 parser = new SafeBoxParser20140320();
				CardBoxInfo safeBoxInfo = parser.parse(html);

				List<ExchangeBoxSlot> slots = safeBoxInfo.getSlots();

				// merge the slots to avoid duplicate
				mergeSlotsWithoutDuplicateSlotId(slots, ret);

				// look for stopping condition.
				if (safeBoxInfo.getPageLinks().size() == 1
						|| safeBoxInfo.getPageLinks()
								.get(safeBoxInfo.getPageLinks().size() - 1)
								.isCurrent()) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.vendor.qzapp.Session#getExchangeBoxSlots()
	 */
	@Override
	public List<ExchangeBoxSlot> getExchangeBoxSlots() {

		// trigger authenticate if not.
		ensureAuthentication();

		ArrayList<ExchangeBoxSlot> ret = new ArrayList<ExchangeBoxSlot>();

		log.trace("retriving exchange box page");

		HttpGet request = new HttpGet(UrlUtil.buildExchangeBoxUrl(getSid()));
		request.setHeader(HttpHeaders.REFERER, UrlUtil.buildMainPageUrl(getSid()));

		// send it
		try {

			HttpResponse response = executeRequest(request);

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

	@Override
	public StealStoveResult stealStove(int targetUin, int targetCardId) {

		ensureAuthentication();

		// look up the card theme ID of the target card ID.
		int themeId = lookupThemeIdForCardId(targetCardId);

		// FIXME cyril: handle the case if theme ID is not found.
		if (themeId == -1) {
			return StealStoveResult.THEME_NOT_FOUND;
		}

		// build the URL
		SynthesizeCardInfo cardForStealing = getCardsForStealing(targetUin,
				themeId, targetCardId);

		if (cardForStealing == null) {
			throw new TxMagicCardException(
					"cannot handle the case card to steal is not available");
		}

		// send the HTTP request
		return doStealCard(cardForStealing, targetUin);

	}

	/**
	 * Do the actual work of stealing stove.
	 * 
	 * @param cardForStealing
	 * @param targetUin
	 * @return
	 */
	private StealStoveResult doStealCard(SynthesizeCardInfo cardForStealing, int targetUin) {

		// build request
		HttpGet request = new HttpGet(cardForStealing.getSynthesizeUrl());
		request.setHeader(HttpHeaders.REFERER, 
				UrlUtil.buildViewSynthsizableCardsForStoveStealing(getSid(), targetUin, cardForStealing.getCardThemeId()));

		try {

			HttpResponse response = executeRequest(request);
			String html = EntityUtils.toString(response.getEntity());

			// handle response.
			if (html.contains("您成功的将卡片放入好友的炼卡炉")) {
				return StealStoveResult.OK;
			} else if (html.contains("用户偷炉达到限制的次数")) {
				return StealStoveResult.LIMIT_REACHED;
			}

			// XXX can check the stolen stoves.

			log.trace("unknown steal stove response: {}", html);
			return StealStoveResult.UNKNOWN_RESPONSE;

		} catch (ClientProtocolException e) {
			throw new TxMagicCardException(
					"error reading steal stove result page", e);
		} catch (IOException e) {
			throw new TxMagicCardException(
					"error reading steal stove result page", e);
		}

	}

	protected SynthesizeCardInfo getCardsForStealing(int targetUin,
			int themeId, int cardId) {

		ensureAuthentication();

		String url = UrlUtil.buildViewSynthsizableCardsForStoveStealing(
				getSid(), targetUin, themeId);

		HttpGet request = new HttpGet(url);

		// send it
		try {

			HttpResponse response = executeRequest(request);

			List<SynthesizeCardInfo> cardsForStealing = cardRefineParser
					.parse(response.getEntity().getContent());

			for (SynthesizeCardInfo info : cardsForStealing) {
				if (info.getCardId() == cardId) {
					return info;
				}
			}

			// not found.
			return null;

		} catch (ClientProtocolException e) {
			log.warn("error when reading game main page", e);
		} catch (IOException e) {
			log.warn("error when reading game main page", e);
		}

		// not found.
		return null;

	}
	
	private int lookupThemeIdForCardId(int targetCardId) {

		if (cardManager == null) {
			log.debug("no card manager assigned");
			return -1;
		}

		Card card = cardManager.findCardById(targetCardId);
		if (card == null) {
			log.debug("unable to find card (id={}) in card manager",
					targetCardId);
			return -1;
		}

		return card.getTheme().getId();
	}

	protected void mergeSlotsWithoutDuplicateSlotId(
			List<ExchangeBoxSlot> source, List<ExchangeBoxSlot> target) {

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
	

	@Override
	public SynthesizeResult synthesizeCard(int targetCardId) {
		
		//
		// the mobile version of application will synthesize the card
		// with a random stove.
		//
		
		ensureAuthentication();

		// make sure the card exists
		if (cardManager == null) {
			return SynthesizeResult.THEME_NOT_FOUND;
		}

		Card card = cardManager.findCardById(targetCardId);
		if (card == null) {
			log.debug("unable to find card (id={}) in card manager",
					targetCardId);
			return SynthesizeResult.CARD_NOT_EXISTS;
		}
		
		// look up the card theme ID of the target card ID.
		if (card.getTheme() == null) {
			return SynthesizeResult.THEME_NOT_FOUND;
		}
		
		int themeId = card.getTheme().getId();

		// build the URL
		SynthesizeCardInfo cardsForSynthesizing = getCardsForSynthesizing(themeId, targetCardId);

		if (cardsForSynthesizing == null) {
			throw new TxMagicCardException(
					"unable to find the card ID for synthesizing");
		}

		// send the HTTP request
		return doSynthesizeCard(cardsForSynthesizing);
		
	}

	protected SynthesizeResult doSynthesizeCard(
			SynthesizeCardInfo cardsForSynthesizing) {
		
		// build request
		HttpGet request = new HttpGet(cardsForSynthesizing.getSynthesizeUrl());
		request.setHeader(HttpHeaders.REFERER, 
				UrlUtil.buildViewSynthsizableCardsForOwnedStoves(getSid(), cardsForSynthesizing.getCardThemeId()));

		try {

			HttpResponse response = executeRequest(request);
			String html = EntityUtils.toString(response.getEntity());

			// handle response.
			if (html.contains("炼卡炉满了")) {
				return SynthesizeResult.STOVE_FULL;
			} else if (html.contains("合成失败，可能是您的卡片被其他魔法师换走了，请查看后再试")) {
				return SynthesizeResult.CARD_NOT_EXISTS;
			} else if (html.contains("放入成功")) {
				return SynthesizeResult.OK;
			}
			
			// XXX can check the stoves.

			log.trace("unknown card synthesis response: {}", html);
			return SynthesizeResult.UNKNOWN_RESPONSE;

		} catch (ClientProtocolException e) {
			throw new TxMagicCardException(
					"error reading stove synthesis result page", e);
		} catch (IOException e) {
			throw new TxMagicCardException(
					"error reading stove synthesis result page", e);
		}

	}

	protected SynthesizeCardInfo getCardsForSynthesizing(int themeId,
			int targetCardId) {
		
		ensureAuthentication();

		String url = UrlUtil.buildViewSynthsizableCardsForOwnedStoves(
				getSid(), themeId);

		HttpGet request = new HttpGet(url);

		// send it
		try {

			HttpResponse response = executeRequest(request);

			List<SynthesizeCardInfo> cardsForSynthesizing = cardRefineParser
					.parse(response.getEntity().getContent());

			for (SynthesizeCardInfo info : cardsForSynthesizing) {
				if (info.getCardId() == targetCardId) {
					return info;
				}
			}

			// not found.
			return null;

		} catch (ClientProtocolException e) {
			log.warn("error when reading game main page", e);
		} catch (IOException e) {
			log.warn("error when reading game main page", e);
		}

		// not found.
		return null;
		
	}
	

	/* (non-Javadoc)
	 * @see com.cppoon.tencent.magiccard.vendor.qzapp.Session#cancelSynthesis(int)
	 */
	@Override
	public CancelSynthesisResult cancelSynthesis(int cardSlotId) {
		
		AccountOverview acOverview = this.getAccountOverview();
		
		// locate the slot with matching slot ID
		StoveInfo targetSlot = null;
		for (StoveInfo stoveInfo : acOverview.getStoves()) {
			if (stoveInfo.getSlotId() == cardSlotId) {
				targetSlot = stoveInfo;
				break;
			}
		}
		// slot ID not found, report this error and die.
		if (targetSlot == null) {
			return CancelSynthesisResult.SLOT_NOT_FOUND;
		}
		
		
		// we need authentication.
		ensureAuthentication();
		
		return doCancelSynthesis(targetSlot);
		
	}

	/**
	 * Do the actual work of canceling a card synthesis.
	 * 
	 * @param targetSlot
	 * @return
	 */
	private CancelSynthesisResult doCancelSynthesis(StoveInfo targetSlot) {
		
		try {
			
			// ensure the 'enforce' parameter has a value of '1'. 
			URIBuilder builder = new URIBuilder(targetSlot.getCancelSynthesisUrl());
			builder.setParameter("enforce", "1");
			URI url = builder.build();
			
			// build a http request
			HttpGet request = new HttpGet(url);
			request.setHeader(HttpHeaders.REFERER, targetSlot.getCancelSynthesisUrl());
			
			// execute request
			HttpResponse response = this.executeRequest(request);
			
			// handle the response.
			String html = EntityUtils.toString(response.getEntity());
			if (html.contains("取消卡片成功")) {
				return CancelSynthesisResult.OK;
			} else if (html.contains("您的卡箱已满，请清理卡箱后再试")) {
				return CancelSynthesisResult.ALREADY_CANCELLED;
			}
			
		} catch (URISyntaxException e) {
			throw new TxMagicCardException("error preparing url for canceling card synthesis", e);
		} catch (ClientProtocolException e) {
			throw new TxMagicCardException("error sending request to cancel card synthesis", e);
		} catch (IOException e) {
			throw new TxMagicCardException("error sending request to cancel card synthesis", e);
		}
		
		return null;
	}

	protected String getSid() {
		return sid;
	}

	/**
	 * @return the cardThemeManager
	 */
	public CardThemeManager getCardThemeManager() {
		return cardThemeManager;
	}

	/**
	 * @param cardThemeManager
	 *            the cardThemeManager to set
	 */
	public void setCardThemeManager(CardThemeManager cardThemeManager) {
		this.cardThemeManager = cardThemeManager;
	}

	/**
	 * @return the cardManager
	 */
	public CardManager getCardManager() {
		return cardManager;
	}

	/**
	 * @param cardManager
	 *            the cardManager to set
	 */
	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}

}
