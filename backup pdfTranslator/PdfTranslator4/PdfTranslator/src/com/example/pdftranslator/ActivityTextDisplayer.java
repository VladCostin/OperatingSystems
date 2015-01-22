package com.example.pdftranslator;

import java.io.IOException;

import java.util.LinkedHashMap;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import InternetConnection.BingTranslator;
import InternetConnection.InternetConnection;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


//So for any other FragmentActivity you can reuse the same Fragment.
public class ActivityTextDisplayer extends FragmentActivity implements OnSeekBarChangeListener {
	
	
/*	public void onBackPressed(){
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
    private static int NUM_PAGES;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    static MyViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    
    
	/**
	 * object containing the book
	 */
 	static PdfReader reader;
 	
	/**
	 * the slider used to jump to other page
	 */
	//SeekBar  sliderJumpToPage;
	
	/**
	 * for each letter an index is been associated
	 */
	static 	LinkedHashMap<Integer,String> letter_index;
	
	
	/**
	 * the map index-letter for the next page
	 */
//	static LinkedHashMap<Integer, String>	letter_index_next;
	
	
	/**
	 * the map index-letter for the previous page
	 */
//	static LinkedHashMap<Integer, String>	letter_index_before;
	

	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_text_displayer);

        
        
        
        try {
        	reader = new PdfReader( FileBrowser.fileTobeShown.getAbsolutePath());
        //	Log.i("NAME", getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer));
		//	reader = new PdfReader(getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //  sliderJumpToPage = (SeekBar) findViewById(R.id.sliderPdfBook);
        
        
        NUM_PAGES = reader.getNumberOfPages();
        
//		sliderJumpToPage.setMax(NUM_PAGES);
//		sliderJumpToPage.setOnSeekBarChangeListener(this);
        
 //       obtainDataFileToShow();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (MyViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        
        
		String textFromPdf="";
		try {
			textFromPdf = PdfTextExtractor.getTextFromPage
					(ActivityTextDisplayer.reader,1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		letter_index = new LinkedHashMap<Integer,String>();
		for(int i = 0; i < textFromPdf.length(); i++)
			letter_index.put(i,Character.toString( textFromPdf.charAt(i)));
        
        
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	
    			String textFromPdf="";
				try {
					textFromPdf = PdfTextExtractor.getTextFromPage
							(ActivityTextDisplayer.reader,position + 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			letter_index.clear();
    			for(int i = 0; i < textFromPdf.length(); i++)
    				letter_index.put(i,Character.toString( textFromPdf.charAt(i)));
    			
    			
    		//	Log.i("NAME", "A INTRAT si aici " + letter_index.values().toString());
    			
            	
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
    
    public void overridePendingTransition(int enterAnim,int exitAnim)
    {
    	Log.i("NUME", "CACAT");
    	super.overridePendingTransition(enterAnim, exitAnim);
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
		
	//	mPager.setCurrentItem(seekBar.getProgress());
		mPager.setCurrentItem(seekBar.getProgress(), false);
	}
	




}



