package com.example.pdftranslator;

import java.io.IOException;


import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;




import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ActivityTextDisplayer extends Activity implements OnSeekBarChangeListener {
	
	/**
	 * the textView where the page will be displayed
	 */
//	TextView textViewPageContainer;
	/**
	 * the slider used to jump to other page
	 */
//	SeekBar  sliderJumpToPage;
	/**
	 * object containing the book
	 */
//	PdfReader reader;
	
	/**
	 * contains the path and the name of the file to be displayed
	 */
//	String stringFilePath;
	
	
	

/*	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_text_displayer);
		
		
		textViewPageContainer = (TextView) findViewById(R.id.textViewPageContainer);
		sliderJumpToPage = (SeekBar) findViewById(R.id.sliderPdfBook);
		sliderJumpToPage.setOnSeekBarChangeListener(this);
		try {
			reader = new PdfReader(getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		obtainDataFileToShow();
	}*/

	/**
	 * gets the pdf file to be shown : whether it was selected from browser or it was a book the user started to read previously
	 */
/*	public void obtainDataFileToShow() {
		
		stringFilePath = getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer);
		sliderJumpToPage.setMax(reader.getNumberOfPages());
		
		
		fillTextView(1);
		

	/*	Intent intentOutput = new Intent();
		intentOutput.putExtra(MainActivity.codeExtraReceiveFromReader,
		stringFilePath + Constants.separatorNamePage + sliderJumpToPage.getProgress());
		setResult(RESULT_OK, intentOutput);
		finish();
		
	*/	
//	}

	/**
	 * fills the textView with the text of the page whose index is received as apramater
	 * @param pageIndex : the index of the page to be shown
	 */
/*	public void fillTextView(int pageIndex) {
		  String textPage;
		
		try {
	      textPage = PdfTextExtractor.getTextFromPage(reader, pageIndex); //Extracting the content from a particular page.
	      textPage = textPage.replaceAll("\n[a-z]", " ");
	      textPage = textPage.replaceAll("\n [a-z]", " ");
	      textPage = textPage.replaceAll("\n[a-z] ", " ");
	      
	      
	      textPage = textPage.replaceAll(".\n[a-z]", " ");
	      textPage = textPage.replaceAll(".\n [a-z]", " ");
	      textPage = textPage.replaceAll(".\n[a-z] ", " ");
	      
	      textPage = textPage.replaceAll(",\n[a-z]", " ");
	      textPage = textPage.replaceAll(",\n [a-z]", " ");
	      textPage = textPage.replaceAll(",\n[a-z] ", " ");
	     
	      
	      textViewPageContainer.setText(textPage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_text_displayer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {


		Log.i("NAME", Integer.toString(seekBar.getProgress()));
		fillTextView(seekBar.getProgress());
		
	}
	
	
	public void onBackPressed(){
		Intent intentOutput = new Intent();
		intentOutput.putExtra(MainActivity.codeExtraReceiveFromReader,
		stringFilePath + Constants.separatorNamePage + sliderJumpToPage.getProgress());
		setResult(RESULT_OK, intentOutput);
		super.onBackPressed();
	}
	

	*/
	
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

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
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
