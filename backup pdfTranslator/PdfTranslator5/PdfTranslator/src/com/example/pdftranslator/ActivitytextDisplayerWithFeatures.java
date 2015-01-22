package com.example.pdftranslator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class ActivitytextDisplayerWithFeatures extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activitytext_displayer_with_features);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        ActivityTextDisplayer container =  (ActivityTextDisplayer)
        this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        
        
        menu.findItem(R.id.action_previous).setEnabled(container.mPager.getCurrentItem() > 0);
        

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (container.mPager.getCurrentItem() == container.mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	ActivityTextDisplayer container =  (ActivityTextDisplayer)
    	this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
    	
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                container.mPager.setCurrentItem(container.mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                container.mPager.setCurrentItem(container.mPager.getCurrentItem() + 1);
                return true;
        }

        
        
        return super.onOptionsItemSelected(item);
    }
}
