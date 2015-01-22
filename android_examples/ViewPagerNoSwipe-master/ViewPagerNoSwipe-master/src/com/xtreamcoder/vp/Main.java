package com.xtreamcoder.vp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class Main extends Activity{

	private MyViewPager myViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		myViewPager = (MyViewPager) findViewById(R.id.my_pager);
		myViewPager.setAdapter(new PagerAdopter());
		
		//Disable Swipe
		myViewPager.setPagingEnabled(false);
	}
	
	class PagerAdopter extends PagerAdapter {
		
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			TextView text = new TextView(Main.this);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(30);
			text.setTextColor(Color.WHITE);
			text.setText("Page " + position);
			int bg = Color.rgb((int) Math.floor(Math.random()*128)+64, 
					(int) Math.floor(Math.random()*128)+64,
					(int) Math.floor(Math.random()*128)+64);
			text.setBackgroundColor(bg);
			container.addView(text, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			return text;
		}
		
		@Override
		public int getCount() {
			return 10;
		}
		
		@Override
		public void destroyItem(View collection, int position, Object view) {
		     ((MyViewPager) collection).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
		    return view == object;
		}
	}
}