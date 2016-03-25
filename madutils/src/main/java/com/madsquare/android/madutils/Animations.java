package com.madsquare.android.madutils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 자주 사용하는 에니메이션 모음 
 * @author justlikehee
 *
 */
public class Animations {

    /**
     * basic alpha animation
     * @param view view
     * @param fromAlpha from alpha
     * @param toAlpha to alpha
     * @param duration duration
     * @param repeatMode repeat mode
     * @param repeatCount repeat count
     * @param isFillAfter is fill after
     * @param listener animation listener
     */
	public static void startAlphaAnimation(View view, float fromAlpha, float toAlpha, long duration,
			int repeatMode, int repeatCount, boolean isFillAfter, AnimationListener listener) {
		Animation alphaAnim = new AlphaAnimation(fromAlpha, toAlpha);
		alphaAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		alphaAnim.setDuration(duration);
		alphaAnim.setRepeatCount(repeatCount);
		alphaAnim.setRepeatMode(repeatMode);
		alphaAnim.setAnimationListener(listener);
		
		if(isFillAfter) {
			alphaAnim.setFillAfter(true);
		}
		
		view.startAnimation(alphaAnim);
	}
	
	/**
	 * basic scale animation
	 * @param view view
	 * @param repeatMode repeat mode
	 * @param repeatCount repeat count
	 * @param fromScaleX from scale x
	 * @param toScaleX to scale x
	 * @param fromScaleY from scale y
	 * @param toScaleY to scale y
	 * @param duration duration
	 * @param isFillAfter is fill after
     * @param listener animation listener
	 */
	public static void startScaleAnimation(View view, int repeatMode, int repeatCount, float fromScaleX,
			float toScaleX, float fromScaleY, float toScaleY, long duration, boolean isFillAfter, AnimationListener listener) {
		Animation scaleAnim = new ScaleAnimation(fromScaleX, toScaleX, fromScaleY, toScaleY,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnim.setInterpolator(new AccelerateInterpolator());
		scaleAnim.setDuration(duration);
		scaleAnim.setRepeatMode(repeatMode);
		scaleAnim.setRepeatCount(repeatCount);
		scaleAnim.setAnimationListener(listener);
		
		if(isFillAfter) {
			scaleAnim.setFillAfter(true);
		}
		
		view.startAnimation(scaleAnim);
	}

    /**
     * basic rotate animation
     * @param view view
     * @param fromDegrees from Degrees
     * @param toDegrees to Degrees
     * @param pivotX pivot x
     * @param pivotY pivot y
     * @param duration duration
     * @param repeatCount repeat count
     * @param repeatMode repeat mode
     * @param isFillAfter is fill after
     * @param listener animation listener
     */
	public static void startRotateAnimation(View view, float fromDegrees, float toDegrees, float pivotX, float pivotY,
			long duration, int repeatCount, int repeatMode, boolean isFillAfter, AnimationListener listener) {
		RotateAnimation anim = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setDuration(duration);
		anim.setRepeatCount(repeatCount);
		anim.setRepeatMode(repeatMode);
		anim.setAnimationListener(listener);
		
		if(isFillAfter) {
			anim.setFillAfter(true);
		}
		
		view.startAnimation(anim);
	}

    /**
     * basic translate animation
     * @param view view
     * @param fromX from x
     * @param toX to x
     * @param fromY from y
     * @param toY to y
     * @param isInterpolator is interpolator
     * @param duration duration
     * @param repeatMode repeat mode
     * @param repeatCount repeat count
     * @param isFillAfter is fill after
     * @param listener animation listener
     */
	public static void startTranslateAnimation(View view, float fromX, float toX, float fromY, float toY, boolean isInterpolator, long duration,
			int repeatMode, int repeatCount, boolean isFillAfter, AnimationListener listener){
		Animation transAnim = new TranslateAnimation(fromX, toX, fromY, toY);
		
		if(isInterpolator) {
			transAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		}
		
		transAnim.setDuration(duration);
		transAnim.setRepeatMode(repeatMode);
		transAnim.setRepeatCount(repeatCount);
		
		if(isFillAfter) {
			transAnim.setFillAfter(true);
		}
		
		transAnim.setAnimationListener(listener);
		
		view.startAnimation(transAnim);																									
	}

     /**
     * Scale Animation with Alpha
     * @param view view
     * @param fromAlpha from alpha
     * @param toAlpha to alpha
     * @param fromX from x
     * @param toX to x
     * @param fromY from y
     * @param toY to y
     * @param pivotX pivot x
     * @param pivotY pivot y
     * @param duration duration
     * @param repeatCount repeat count
     * @param repeatMode repeat mode
     * @param isFillAfter is fill after
     * @param listener animation listener
     */
    public static void startScaleAnimationWithAlpha(View view, float fromAlpha, float toAlpha,
                                                    float fromX, float toX, float fromY, float toY, float pivotX, float pivotY, long duration,
                                                    int repeatCount, int repeatMode, boolean isFillAfter, AnimationListener listener) {

        AnimationSet animSet = new AnimationSet(true);

        Animation alphaAnim = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnim.setDuration(duration);
        alphaAnim.setRepeatCount(repeatCount);

        Animation scaleAnim = new ScaleAnimation(fromX, toX, fromY, toY,
                Animation.RELATIVE_TO_SELF, pivotX,
                Animation.RELATIVE_TO_SELF, pivotY);//0.5f
        scaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnim.setDuration(duration);
        scaleAnim.setRepeatCount(repeatCount);
        scaleAnim.setRepeatMode(repeatMode);
        scaleAnim.setAnimationListener(listener);

        animSet.addAnimation(alphaAnim);
        animSet.addAnimation(scaleAnim);
        animSet.setFillAfter(isFillAfter);

        view.startAnimation(animSet);
    }

    /**
     * translate animation with scale
     * @param view view
     * @param fromX from x
     * @param toX to x
     * @param fromY from y
     * @param toY to y
     * @param fromScaleX from scale x
     * @param toScaleX to scale x
     * @param fromScaleY from scale y
     * @param toScaleY to scale y
     * @param isInterpolator is interpolator
     * @param duration duration
     * @param repeatMode repeat mode
     * @param repeatCount repeat count
     * @param isFillAfter is fill after
     * @param listener animation listener
     */
	public static void startTranslateAnimationWithScale(View view, float fromX, float toX, float fromY, float toY,
			float fromScaleX, float toScaleX, float fromScaleY, float toScaleY,boolean isInterpolator, long duration, 
			int repeatMode, int repeatCount, boolean isFillAfter, AnimationListener listener) {
		
		AnimationSet animSet = new AnimationSet(true);
		
		Animation transAnim = new TranslateAnimation(fromX, toX, fromY, toY);
		
		if(isInterpolator) {
			transAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		}
		
		transAnim.setDuration(duration);
		transAnim.setRepeatMode(repeatMode);
		transAnim.setRepeatCount(repeatCount);
				
		Animation scaleAnim = new ScaleAnimation(fromScaleX, toScaleX, fromScaleY, toScaleY,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
				scaleAnim.setInterpolator(new AccelerateInterpolator());
				scaleAnim.setDuration(duration);
				
		animSet.setFillAfter(isFillAfter);
		animSet.setAnimationListener(listener);
		animSet.addAnimation(transAnim);
		animSet.addAnimation(scaleAnim);
		view.startAnimation(animSet);																						
	}
	
	/**
	 * animation stop
	 * @param view stop view
	 */
	public static void stopAnimation(View view) {
		view.clearAnimation();
		view.setAnimation(null);
	}
}
