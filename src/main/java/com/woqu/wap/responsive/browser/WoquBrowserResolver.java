package com.woqu.wap.responsive.browser;

import javax.servlet.http.HttpServletRequest;

public class WoquBrowserResolver implements BrowserResolver {
	
	private final String WOQU_IOS_APP_KEYWORD = "Woqu";
	
	private final String WEIXIN_KEYWORD = "MicroMessenger";

	public Browser resolveBrowser(HttpServletRequest request) {
		
		String userAgent = request.getHeader("User-Agent");
		
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// UserAgent keyword detection of iOS APP Browser
			if (userAgent.contains(WOQU_IOS_APP_KEYWORD)) {
				return WoquBrowser.WOQU_IOS_APP_INSTANCE;
			}// UserAgent keyword detection of WeiXin Browser
			if (userAgent.contains(WEIXIN_KEYWORD)) {
				return WoquBrowser.WEIXIN_INSTANCE;
			}
			//TODO: Android
		}
		
		return resolveFallback(request);
	}
	/**
	 * Fallback called if no Browser is matched by this resolver. The default
	 * implementation of this method returns a "normal" {@link Browser} 
	 * Subclasses may override to try additional Browser
	 * matching before falling back to a "normal" Browser.
	 */
	protected Browser resolveFallback(HttpServletRequest request) {
		return WoquBrowser.NORMAL_INSTANCE;
	}

}
