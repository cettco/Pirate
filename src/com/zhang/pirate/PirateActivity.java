package com.zhang.pirate;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import com.zhang.pirate.managers.ResourcesManager;
import com.zhang.pirate.managers.SceneManager;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;

public class PirateActivity extends BaseGameActivity {

	private Camera camera;
	private static int CAMARA_WIDTH;
	private static int CAMARA_HEIGHT;
	private ResourcesManager resourcesManager;
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		CAMARA_WIDTH = metrics.widthPixels;
		CAMARA_HEIGHT= metrics.heightPixels;
		System.out.println("width "+CAMARA_WIDTH);
		System.out.println("height "+CAMARA_HEIGHT);
		camera = new Camera(0, 0, CAMARA_WIDTH, CAMARA_WIDTH);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMARA_WIDTH, CAMARA_HEIGHT), this.camera);
	    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager(),CAMARA_WIDTH,CAMARA_HEIGHT);
	    resourcesManager = ResourcesManager.getInstance();
	    pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// TODO Auto-generated method stub
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            SceneManager.getInstance().createMenuScene();
	        }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	

}
