package com.woqu.wap.responsive.browser;

/**
 * 
 * @author WuChangming
 *
 */
public class WoquBrowser implements Browser {
	
	public static final WoquBrowser NORMAL_INSTANCE = new WoquBrowser(BrowserType.NORMAL);
	
	public static final WoquBrowser WEIXIN_INSTANCE = new WoquBrowser(BrowserType.WEIXIN);
	
	public static final WoquBrowser WOQU_ANDROID_APP_INSTANCE = new WoquBrowser(BrowserType.WOQU_ANDROID_APP);
	
	public static final WoquBrowser WOQU_IOS_APP_INSTANCE = new WoquBrowser(BrowserType.WOQU_IOS_APP);
	
	private final BrowserType browserType;
	
	/**
	 * Creates a WoquBrowser.
	 */
	private WoquBrowser(BrowserType browserType) {
		this.browserType = browserType;
	}
	
	public boolean isNormal() {
		return this.browserType == BrowserType.NORMAL;
	}

	public boolean isWoquIOSApp() {
		return this.browserType == BrowserType.WOQU_IOS_APP;
	}

	public boolean isWoquAndroidApp() {
		return this.browserType == BrowserType.WOQU_ANDROID_APP;
	}

	public boolean isWeiXin() {
		return this.browserType == BrowserType.WEIXIN;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[WoquBrowser ");
		builder.append("type").append("=").append(this.browserType);
		builder.append("]");
		return builder.toString();
	}

}
