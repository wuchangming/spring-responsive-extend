package com.woqu.wap.responsive.browser.view;

import java.net.URL;
import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.woqu.wap.responsive.browser.Browser;

/**
 * /**
 * Abstract {@link ViewResolver} implementation, providing a {@link Browser}
 * aware {@link ViewResolver} wrapper that delegates to another view resolver
 * implementation, allowing for resolution of Browser specific view names without
 * the need for a dedicated mapping to be defined for each view.</p>
 * @author WuChangming
 *
 */
public abstract class AbstractBrowserDelegatingViewResolver extends
		WebApplicationObjectSupport implements ViewResolver, Ordered {
	
	/**
	 * Prefix for special view names that specify a redirect URL (usually
	 * to a controller after a form has been submitted and processed).
	 */
	public static final String REDIRECT_URL_PREFIX = "redirect:";

	/**
	 * Prefix for special view names that specify a forward URL (usually
	 * to a controller after a form has been submitted and processed).
	 */
	public static final String FORWARD_URL_PREFIX = "forward:";
	
	private final ViewResolver delegate;

	private int order = Ordered.LOWEST_PRECEDENCE;

	private boolean enableFallback = true;
	
	/**
	 * Creates a new AbstractBrowserDelegatingViewResolver
	 * @param delegate the ViewResolver in which to delegate
	 */
	protected AbstractBrowserDelegatingViewResolver(ViewResolver delegate) {
		Assert.notNull(delegate, "delegate is required");
		this.delegate = delegate;
	}
	
	/**
	 * Returns the delegate view resolver
	 */
	public ViewResolver getViewResolver() {
		return this.delegate;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	};
	
	/**
	 * Enables support for fallback resolution, meaning if the adjusted view
	 * name cannot be resolved, and attempt will be made to resolve the
	 * original view name. This may be helpful in situations where not all
	 * views within a web site have Browser specific implementations.
	 * 
	 * <p>Note: fallback resolution will only work when delegating to a view 
	 * resolver which returns null from 
	 * {@link #resolveViewName(String, Locale)} if it cannot resolve a view. 
	 * For example, {@link InternalResourceViewResolver} never returns null,
	 * so fallback resolution will not be available.
	 */
	public void setEnableFallback(boolean enableFallback) {
		this.enableFallback = enableFallback;
	}

	/**
	 * Return whether fallback view resolution is enabled
	 * @see #setEnableFallback(boolean)
	 */
	protected boolean getEnableFallback() {
		return this.enableFallback;
	}
	
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		String browserViewName = getBrowserViewName(viewName);
		//InternalResourceViewResolver always return a nut null view.here need a file exist checked.
		View view = delegate.resolveViewName(browserViewName, locale);
		if(view != null && JstlView.class.isAssignableFrom(view.getClass())){
			JstlView jstlView = (JstlView)view;
			URL url = getServletContext().getResource(jstlView.getUrl());
			if(url == null){
				view = null;
			}
		}
		if (enableFallback && view == null) {
			view = delegate.resolveViewName(viewName, locale);
		}
		if (logger.isDebugEnabled() && view != null) {
			logger.debug("Resolved View: " + view.toString());
		}
		return view;
	}
	/**
	 * Returns the adjusted view name as determined by subclass implementation.
	 * In the case where a requested URL is prefixed with "redirect:" or
	 * "forward:", the view name will not be adjusted.
	 * @param viewName the name of the view before browser resolution
	 * @return the adjusted view name
	 * @see #getBrowserViewNameInternal(String)
	 */
	protected String getBrowserViewName(String viewName) {
		// Check for special "redirect:" prefix.
		if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
			return viewName;
		}
		// Check for special "forward:" prefix.
		if (viewName.startsWith(FORWARD_URL_PREFIX)) {
			return viewName;
		}
		return getBrowserViewNameInternal(viewName);
	}
	/**
	 * Subclasses must implement this method, adjusting the browser view name
	 * based on Browser resolution used within the subclass.
	 * @param viewName the name of the view before Browser resolution
	 * @return the adjusted view name
	 * @see #getBrowserViewName(String)
	 */
	protected abstract String getBrowserViewNameInternal(String viewName);
}
