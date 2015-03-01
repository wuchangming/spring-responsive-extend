package com.woqu.wap.responsive.browser;

import javax.servlet.http.HttpServletRequest;

/**
 * Service interface for resolving Browsers that originate web requests with the application.
 * @author WuChangming
 *
 */
public interface BrowserResolver {
	/**
	 * Resolve the Browser that originated the web request.
	 */
	Browser resolveBrowser(HttpServletRequest request);
}
