package com.woqu.wap.responsive.browser.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ViewResolver;

import com.woqu.wap.responsive.browser.Browser;
import com.woqu.wap.responsive.browser.BrowserUtils;
/**
 * <p>Alternatively, you may want to have the views adjusted to use a suffix
 * for each Browser type. Using the requested view name of "home", the
 * following table shows the adjusted view names.</p>
 *
 * <table border=1 cellpadding=3 cellspacing=0>
 *   <tr>
 *     <td>Resolved Device</td>
 *     <td>Method</td>
 *     <td>Suffix</td>
 *     <td>Adjusted View</td>
 *   </tr>
 *   <tr>
 *     <td>Normal</td>
 *     <td>{@link #setNormalSuffix(String)} 最好不要设置,这样可以让别的browser找不到view时fallback到normal的view</td>
 *     <td>""</td>
 *     <td>"home"</td>
 *   </tr>
 *   <tr>
 *     <td>WoquIOSApp</td>
 *     <td>{@link #setWoquIOSAppSuffix(String)}</td>
 *     <td>".ios"</td>
 *     <td>"home.ios"</td>
 *   </tr>
 *   <tr>
 *     <td>WoquAndroidApp</td>
 *     <td>{@link #setWoquAndroidAppSuffix(String)}</td>
 *     <td>".android"</td>
 *     <td>"home.android"</td>
 *   </tr>
 *   <tr>
 *     <td>WeiXin</td>
 *     <td>{@link #setWeiXinSuffixSuffix(String)}</td>
 *     <td>".weixin"</td>
 *     <td>"home.weixin"</td>
 *   </tr>
 * </table>
 * @author WuChangming
 *
 */
public class WoquBrowserDelegatingViewResolver extends AbstractBrowserDelegatingViewResolver{
	
	private String normalSuffix = "";

	private String woquIOSAppSuffix = "";

	private String woquAndroidAppSuffix = "";
	
	private String weiXinSuffix = "";
	
	public String getNormalSuffix() {
		return normalSuffix;
	}

	public void setNormalSuffix(String normalSuffix) {
		this.normalSuffix = (normalSuffix != null ? normalSuffix : "");
	}

	public String getWoquIOSAppSuffix() {
		return woquIOSAppSuffix;
	}

	public void setWoquIOSAppSuffix(String woquIOSAppSuffix) {
		this.woquIOSAppSuffix = (woquIOSAppSuffix != null ? woquIOSAppSuffix : "");
	}

	public String getWoquAndroidAppSuffix() {
		return woquAndroidAppSuffix;
	}

	public void setWoquAndroidAppSuffix(String woquAndroidAppSuffix) {
		this.woquAndroidAppSuffix = (woquAndroidAppSuffix != null ? woquAndroidAppSuffix : "");
	}

	public String getWeiXinSuffix() {
		return weiXinSuffix;
	}

	public void setWeiXinSuffix(String weiXinSuffix) {
		this.weiXinSuffix = (weiXinSuffix != null ? weiXinSuffix : "");
	}

	public WoquBrowserDelegatingViewResolver(ViewResolver delegate) {
		super(delegate);
	}

	@Override
	protected String getBrowserViewNameInternal(String viewName) {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		Assert.isInstanceOf(ServletRequestAttributes.class, attrs);
		HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
		Browser browser = BrowserUtils.getCurrentBrowser(request);
		String resolvedViewName = viewName;
		if (browser.isNormal()) {
			resolvedViewName = viewName + getNormalSuffix();
		} else if (browser.isWoquIOSApp()) {
			resolvedViewName = viewName + getWoquIOSAppSuffix();
		} else if (browser.isWoquAndroidApp()) {
			resolvedViewName = viewName + getWoquAndroidAppSuffix();
		} else if (browser.isWeiXin()) {
			resolvedViewName = viewName + getWeiXinSuffix();
		}

		// MOBILE-63 "redirect:/" and "forward:/" can result in the view name containing multiple trailing slashes 
		return stripTrailingSlash(resolvedViewName);
	}
	
	private String stripTrailingSlash(String viewName) {
		if (viewName.endsWith("//")) {
			return viewName.substring(0, viewName.length() - 1);
		}
		return viewName;
	}

}
