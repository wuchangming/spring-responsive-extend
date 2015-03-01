package com.woqu.wap.responsive.browser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * A Spring MVC interceptor that resolves the Browser that originated the web request <i>before</i> any request handler is invoked.
 * The resolved Browser is exported as a request attribute under the well-known name of {@link BrowserUtils#CURRENT_BROWSER_ATTRIBUTE}.
 * Request handlers such as @Controllers and views may then access the currentBrowser to vary their control and rendering logic, respectively.
 * @author WuChangming
 *
 */
public class BrowserResolverHandlerInterceptor extends HandlerInterceptorAdapter{
	
	private final BrowserResolver browserResolver;
	
	/**
	 * Create a Browser resolving {@link HandlerInterceptor} that defaults to a {@link WoquBrowserResolver} implementation.
	 */
	public BrowserResolverHandlerInterceptor() {
		this(new WoquBrowserResolver());
	}
	
	/**
	 * Create a Browser resolving {@link HandlerInterceptor}.
	 * @param deviceResolver the Browser resolver to delegate to in {@link #preHandle(HttpServletRequest, HttpServletResponse, Object)}.
	 */
	public BrowserResolverHandlerInterceptor(BrowserResolver browserResolver) {
		this.browserResolver = browserResolver;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Browser browser = browserResolver.resolveBrowser(request);
		request.setAttribute(BrowserUtils.CURRENT_BROWSER_ATTRIBUTE, browser);
		return true;
	}
	
}
