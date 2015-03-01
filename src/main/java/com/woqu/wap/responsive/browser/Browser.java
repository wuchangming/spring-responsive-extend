package com.woqu.wap.responsive.browser;
/**
 *  A model for the user agent or device that submitted the current request.  
 *  Callers may introspect this model to vary UI control or rendering logic by device type
 * @author WuChangming
 *
 */
public interface Browser {
	
	/**
	 * True if this browser is not other browsers.
	 */
	boolean isNormal();
	
	/**
	 * True if this browser is iOS APP browser. 
	 */
	boolean isWoquIOSApp();
	
	/**
	 * True if this browser is Android APP browser. 
	 */
	boolean isWoquAndroidApp();
	
	/**
	 * True is this browser is WeiXin browser.
	 */
	boolean isWeiXin();
}
