package com.example.pdftranslator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * creating instance of ViewPager in order to override onTouch Listener
 * @author admin
 *
 */
public class MyViewPager extends ViewPager
{

	boolean toScroll;
	
	/**
	 * overrides the constructor of ViewPager
	 * @param context
	 */
	public MyViewPager(Context context) {

		super(context);
		toScroll = false;
		// TODO Auto-generated constructor stub
	}
	
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		toScroll = false;
	}
/*	 @Override
	 public boolean onInterceptTouchEvent(MotionEvent arg0) {
	        // Never allow swiping to switch between pages
	    	//super.onInterceptTouchEvent(arg0);
		 ///	if(ActivityTextDisplayer.slide == true)
		 //	{
		    	Log.i("NAME", Thread.currentThread().getId() + " onInterceptToucEvent - varianta true" + arg0);
		 		super.onInterceptTouchEvent(arg0);
		 	
		 		return true;
		 /*	}
		 	 ActivityTextDisplayer.slide = true;
			//toScroll = true;
	    	Log.i("NAME", Thread.currentThread().getId() + " onInterceptToucEvent -varianta false");
	        //return true;
		 	return toScroll;
	 }*/
	 
	
/*   @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // Never allow swiping to switch between pages
    	super.onInterceptTouchEvent(arg0);
    	Log.i("NAME", "HEH");
        return true;
    }
*/
/*    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
        // Never allow swiping to switch between pages
    	//super.onTouchEvent(event);
    	Log.i("NAME", Thread.currentThread().getId() + "onTouchEvent ");
        return false;
    }
    
 */   
	
	
/*	public boolean onTouchEvent(MotionEvent event)
	{
		//Log.i("NAME", Thread.currentThread().getId() + " in touch event viewpager");
		return true;
	}
*/	
}