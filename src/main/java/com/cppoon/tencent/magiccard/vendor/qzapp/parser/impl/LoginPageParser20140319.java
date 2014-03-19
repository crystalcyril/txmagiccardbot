/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.LoginForm;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.LoginPageParser;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class LoginPageParser20140319 implements LoginPageParser {

	private static final Logger log = LoggerFactory.getLogger(LoginPageParser20140319.class);
	
	/**
	 * Contains regular expression which can grab all <form>...</form> tags.
	 */
	private Pattern pForm;
	
	private Pattern pFormActionUrl;
	
	private Pattern pFormHiddenValue;
	
	public LoginForm parse(String html) {

		LoginForm ret = new LoginForm();

		parseHtmlForm(ret, html);

		return ret;

	}
	
	protected void parseHtmlForm(LoginForm ret, String html) {

		Matcher m = getPatternForm().matcher(html);

		while (m.find()) {
			
			// we expect two groups captured, one is the form attributes,
			// one is form content.
			if (m.groupCount() != 2) {
				continue;
			}
			
			String formAttrs = m.group(1);
			String formContent = m.group(2);
			
			// make sure the following attributes contains:
			// - id="qq_loginform"
			// - action="..."
			if (!formAttrs.contains("id=\"qq_loginform\"")) {
				continue;	// skip if 'id' not found.
			}
			
			Matcher mFormActionUrl = getPatternFormActionUrl().matcher(formAttrs);
			if (!mFormActionUrl.find() || mFormActionUrl.groupCount() != 1) {
				continue;	// skip if 'action' URL not found.
			}
			
			//
			// we assume we find the correct <form>...</form> closure.
			//
			
			// now we grab the action URL.
			String actionUrl = unescapeHtml(mFormActionUrl.group(1));
			ret.setFormSubmitUrl(actionUrl);
			
			// now we continue to parse the fields inside the HTML form.
			parseFormFields_ByJsoup(ret, formContent);
			
		}
		
	}
	
	protected void parseFormFields_ByJsoup(LoginForm ret, String formContent) {
		
		// parse all <input type="hidden"../> fields.
		Document doc = Jsoup.parse(formContent);
		
		Elements elements = doc.select("input[type=hidden]");
		for (Element element : elements) {
			
			String name = StringUtils.trimToNull(element.attr("name"));
			if (StringUtils.isEmpty(name)) {
				continue;
			}
			
			String value = StringUtils.trimToNull(element.attr("value"));
			
			// special processing of some fields
			if ("login_url".equals(name) || "go_url".equals(name)) {
//				value = unescapeHtml(value);
			}
			
			ret.put(name, value);
		}
		
	}
	
	private String unescapeHtml(String html) {
		
		return StringEscapeUtils.unescapeHtml4(html);
		
	}

	/**
	 * Returns the form regular expression.
	 * 
	 * @return
	 */
	protected Pattern getPatternForm() {
		if (pForm == null) {
			pForm = Pattern.compile("<form([^>]*)>(.*)</form>", Pattern.DOTALL);
		}
		return pForm;
	}
	
	/**
	 * Returns regular expression for capturing <form> tag action URL. 
	 * 
	 * @return
	 */
	protected Pattern getPatternFormActionUrl() {
		if (pFormActionUrl == null) {
			pFormActionUrl = Pattern.compile("action=\"(.+?)\"", Pattern.DOTALL);
		}
		return pFormActionUrl;
	}
	
	protected Pattern getPatternFormHiddenValue() {
		if (pFormHiddenValue == null) {
			pFormHiddenValue = Pattern.compile("<input\\s+type=\"hidden\"(.*?)(?:>|/>)");
		}
		return pFormHiddenValue;
	}
	
}
