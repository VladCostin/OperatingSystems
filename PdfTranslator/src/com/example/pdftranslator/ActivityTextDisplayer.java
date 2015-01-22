package com.example.pdftranslator;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.pdftranslator.dictionary.ActivityDictionary;
import com.example.pdftranslator.dictionary.ConstantsTabs;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import Database.AttributesBook;
import Database.Book;

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
/**
 * activity where the pdf is shown
 * @author Vlad Herescu
 *
 */
public class ActivityTextDisplayer extends FragmentActivity implements OnSeekBarChangeListener {
	
	/**
	 * the path of the currentFile;
	 */
	String stringFilePath;
	
	
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
	private static SeekBar  sliderJumpToPage;
	
	/**
	 * for each letter an index is been associated
	 */
	static 	LinkedHashMap<Integer,String> letter_index;
	
	
    /**
     * set to true if there are no new requests for translating words
     * set to false if there are current requests for translating words
     */
    private static boolean booleanSearchNewWord;
	
    
    /**
     * the book shown on the display
     * the information regarding the current book are used 
     * for adding new data into the database
     */
    private static Book book;
    
    /**
     * the path of the currentFile
     */
    String filePath;
    
    String fileName;
    
    Integer page;
	
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
        


        
        
    
        getDataFromActivities();
        ActivityTextDisplayer.setBooleanSearchNewWord(true);
        setHeaderTitle();
        initSeekBar();
        initTextFromPdf();
        initViewPager();
        addBookIfIdDoesNotExist();
        

    }





	/**
	 * gets data from the activities that have started ActivityTextDisplayer
	 * such as the title of the book and the page
	 */
	public void getDataFromActivities() {
        
		
		String filePath = getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer);
		String fileName = getIntent().getStringExtra(Constants.nameFile);
		page = getIntent().getIntExtra(Constants.pageSaved, 0); 
		Log.i("message","getDataFromActivites :  " + filePath + " " + fileName + " " + page);
		initReader(filePath,fileName, 0);
      
		
	}
	


	private void initReader(String path,String fileName, int nrPage)
	{
		 try {
		       	reader = new PdfReader( path);
		       	stringFilePath = path;
		       	this.fileName = fileName;
		       	
		       	
		       //	Log.i("NAME", getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer));
				//	reader = new PdfReader(getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    NUM_PAGES = reader.getNumberOfPages();
		
	}



	/**
	 * sets the title of the activity as the name of the file to be read
	 */
	public void setHeaderTitle() {
		int titleLength;
    	titleLength = fileName.length();
    	titleLength = titleLength < 50 ? titleLength : 50;
    	this.setTitle(fileName.substring(0, titleLength));
		
	}



	/**
	 * checks if in database exists a book with the same name
	 * if it does, do not include it
	 */
	public void addBookIfIdDoesNotExist() {
		
		  book = MainActivity.m_database.getBook(AttributesBook.TITLE, fileName);
		  if(book != null){
			  Log.i("message", "exista deja");
			  return;
		  }
		  
		  Log.i("message", "nu exista deja");
		  book = new Book(fileName, 0, NUM_PAGES, stringFilePath);
		  MainActivity.m_database.addBook(book);
		  
		  book.setId(MainActivity.m_database.getBookId(book.getTitle()));
	}



	/**
     * instantiate the Seekbar to jump to a page 
     */
    public void initSeekBar()
    {
        
        
        sliderJumpToPage = (SeekBar) findViewById(R.id.sliderPdfBook);
		sliderJumpToPage.setMax(NUM_PAGES);
		sliderJumpToPage.setProgress(page);
		sliderJumpToPage.setOnSeekBarChangeListener(this);
    }
    
    /**
     * initializes the view pager
     */
    public void initViewPager()
    {
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (MyViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    	
    	
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

        
		mPager.setCurrentItem(page);
    }
    
    
    
    /**
     * initialize the first page to be shown in the container
     */
    public void initTextFromPdf() {
    	
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
		
	}
    
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        // this is done here and not in the menu xml file because it may change, depending 
        // if it is the last page or not
        // so where are the items added programatically
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
            case R.id.itemGoToDictionary:
            	Intent intent = new Intent(this, ActivityDictionary.class);
            	intent.putExtra(ConstantsTabs.titleBook, this.book.getTitle());
            	this.startActivity(intent);
        }

        
        
        return super.onOptionsItemSelected(item);
    }
    
	
	public void onPause()
	{	
		
		Core.getHashMapBetweenMainAndReader().put(this.stringFilePath, mPager.getCurrentItem());
		Log.i("message", "a intrat aici : " + mPager.getCurrentItem());
		super.onPause();
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
		mPager.setCurrentItem(seekBar.getProgress(), false);
	}



	public static boolean isBooleanSearchNewWord() {
		return booleanSearchNewWord;
	}



	public static void setBooleanSearchNewWord(boolean booleanSearchNewWord) {
		ActivityTextDisplayer.booleanSearchNewWord = booleanSearchNewWord;
	}



	public static SeekBar getSliderJumpToPage() {
		return sliderJumpToPage;
	}



	public static void setSliderJumpToPage(SeekBar sliderJumpToPage) {
		ActivityTextDisplayer.sliderJumpToPage = sliderJumpToPage;
	}



	public static Book getBook() {
		return book;
	}



	public static void setBook(Book book) {
		ActivityTextDisplayer.book = book;
	}
	
}



