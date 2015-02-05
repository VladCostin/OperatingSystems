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
}