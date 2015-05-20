package com.example.pdftranslator.exercise;

import com.example.pdftranslator.R;







import InternetConnection.Constants;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * class that contains the viewPager where the fragments with questions will be shown
 * @author Vlad Herescu
 *
 */
public class ExerciseSlider extends   FragmentActivity  {
	
	
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    
    /**
     * class which contains the data to be shown, and manages the queries
     * executes the business logic
     */
    static ExerciseModel	m_model;
    
    /**
     * listens to user input and notifies the model
     */
    static ExerciseController m_controller; 
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_slider);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pagerExercise);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });
        
        
        m_model = new ExerciseModel();
        m_model.retrieveWords(10);
        
        m_controller = new ExerciseController(m_model);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.android_exercise_actionbar, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
            	
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                
            	handleMenuItemNext(item.getTitle().toString().toLowerCase());
            	
                return true;
                
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * @param _itemValue : the value of the button with the id next
     * 
     */
    public void handleMenuItemNext(String _itemValue)
    {
    	if(_itemValue.equals("next"))
    		mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    	if(_itemValue.equals("finish"))
    	{
    		
    	  	
    	   	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	   	TextView textViewScore;
    	    LayoutInflater inflater = 	getLayoutInflater();
    	    final View tableLayout = inflater.inflate(R.layout.dialog_score_shown, null); // first you have to inflate
    	    

    	   	
    	   	builder.setView(tableLayout);
    	   	
    	   	textViewScore = (TextView) tableLayout.findViewById( R.id.textViewScoreValueExerciseActivity);
    	   	if(textViewScore == null)
    	   		Log.i("message", "este null");
    	   	else
    	   		textViewScore.setText(ExerciseModel.m_indexWordsCorrect.size()+"/" + NUM_PAGES  );
    		
    	   	builder.create().show();
    	   	
    	   	
    	   	
    	}
    }
    


	/**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        
        // aici apeleaza noul fragment
        @Override
        public Fragment getItem(int position) {
        	Log.i("message", "getItem" + position);
            return ScreenSlidePageFragmentExercise.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
