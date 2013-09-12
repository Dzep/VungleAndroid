package com.dzcoding.vungleandroid.lib;

import com.vungle.sdk.VunglePub;
import com.vungle.sdk.VunglePub.EventListener;

import android.app.Activity;

public class VungleManager {

	private static String APP_ID = "";
	private boolean mCloseButtonShown = true;
	private static VungleManagerEventListener mListener = null;
	
	public VungleManager() {
		
	}

	/**
	 * Configures Vungle with the given context and application id.
	 * @param activity The activity where the Vungle's activity will be attached to.
	 * @param appId The application id.s
	 */
	public static void configure(Activity activity, String appId) {
		APP_ID = appId;
		VunglePub.init(activity, appId);
		VunglePub.setEventListener(new EventListener() {
			@Override
			public void onVungleView(double watchedSeconds, double totalAdSeconds) {
				final double watchedPercent = watchedSeconds/totalAdSeconds;
				boolean success = (watchedPercent >= 0.8f);
				if (mListener != null && mListener instanceof VungleManagerEventListener) {
					mListener.onVungleManagerView(success);
				}
			}
			
			@Override
			public void onVungleAdStart() {
				if (mListener != null && mListener instanceof VungleManagerEventListener) {
					mListener.onVungleManagerAdStart();
				}
			}
			
			@Override
			public void onVungleAdEnd() {
				if (mListener != null && mListener instanceof VungleManagerEventListener) {
					mListener.onVungleManagerAdEnd();
				}
			}
		});
	}
	
	/**
	 * Call this on onResume method of attached Activity.
	 */
	public static void pause() {
		VunglePub.onPause();
	}
	
	/**
	 * Call this on onPause method of attached Activity.
	 */
	public static void resume() {
		VunglePub.onResume();
	}
	
	/**
	 * 
	 */
	public static void play() {
		if (isAvailable(false)) {
			VunglePub.displayIncentivizedAdvert(true);
		}
	}
	
	/// Setter
	public void setCloseButtonShown(boolean isCloseButtonShown) {
		mCloseButtonShown = isCloseButtonShown;
	}
	
	public static void setAutoRotation(boolean shouldAutoRotate) {
		VunglePub.setAutoRotation(shouldAutoRotate);
	}
	
	public static void setEventListener(VungleManagerEventListener listener) {
		mListener = listener;
	}
	
	/// Getter
	public boolean isCloseButtonShown() {
		return mCloseButtonShown;
	}
	
	public static String getAppId() {
		return APP_ID;
	}
	
	/**
	 * Checks the availability of the video ad.
	 * @param displayLog Display log to LogCat if TRUE.
	 * @return TRUE if the video is available.
	 */
	public static boolean isAvailable(boolean displayLog) {
		return VunglePub.isVideoAvailable(displayLog);
	}
	
	public interface VungleManagerEventListener {
		public void onVungleManagerAdStart();
		public void onVungleManagerAdEnd();
		public void onVungleManagerView(boolean success);
	}
}
