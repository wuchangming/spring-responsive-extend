package com.woqu.wap.responsive.browser;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;

/**
 * Static helper for accessing request-scoped Browser values.
 * @author WuChangming
 *
 */
public class BrowserUtils {
	/**
	 * The name of the request attribute the current Browser is indexed by.
	 * The attribute name is 'currentWoquBrowser'.
	 */
	public static final String CURRENT_BROWSER_ATTRIBUTE = "currentWoquBrowser";
	
	/**
	 * Static utility method that extracts the current Browser from the web request.
	 * Encapsulates the {@link HttpServletRequest#getAttribute(String)} lookup.
	 * @param request the servlet request
	 * @return the current Browser, or null if no Browser has been resolved for the request
	 */
	public static Browser getCurrentBrowser(HttpServletRequest request) {
		return (Browser) request.getAttribute(CURRENT_BROWSER_ATTRIBUTE);
	}
	
	/**
	 * Static utility method that extracts the current Browser from the request attributes map.
	 * Encapsulates the {@link HttpServletRequest#getAttribute(String)} lookup.
	 * @param attributes the request attributes
	 * @return the current Browser, or null if no Browser has been resolved for the request
	 */
	public static Browser getCurrentBrowser(RequestAttributes attributes) {
		return (Browser) attributes.getAttribute(CURRENT_BROWSER_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
	}
}
