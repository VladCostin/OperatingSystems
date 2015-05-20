package com.example.pdftranslator.dictionary;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.pdftranslator.Constants;
import com.example.pdftranslator.CustomDialog;
import com.example.pdftranslator.MainActivity;
import com.example.pdftranslator.R;


import com.example.pdftranslator.dictionary.controller.ControllerDictionary;
import com.example.pdftranslator.dictionary.model.CoreDictionary;

import Database.Appeareance;
import Database.AttributesAppearences;
import Database.AttributesBook;
import Database.AttributesWord;
import Database.Book;
import Database.PartSpeech;
import Database.Word;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityDictionary extends Activity implements ActionBar.TabListener, ActivityInterface {

	/**
	 * contains the information about the word from the dictionary
	 */
	ViewPager m_viewPager;
	
	/**
	 * the adapter used to switch between the fragments
	 */
	TabPageAdapter m_adapter;
	
	
	/**
	 * represents the business model
	 * it filters the data
	 * and orders it
	 */
	CoreDictionary m_core;
	
	
	/**
	 * the controller that receives the input from the user
	 */
	ControllerDictionary m_controller;
	
	
	/**
	 * every fragment gets the list of words needed to be shown from this HASHMAP
	 */
	static HashMap<PartSpeech,ArrayList<Word>> m_hashMapWords;
	
	/**
	 * the id of the fragment is associated to the Type of words to be shown
	 */
	static HashMap<Integer, MyTab> hashMapTypeWordAssociatedToFragment;
	
	/**
	 * saving the instances of the fragments as ViewGroup for updating
	 */

	static HashMap<Integer,DictionaryFragment> hashMapFragments;
	/**
	 * reference to the ActionBar menu
	 */
	Menu m_menu;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_dictionary);
			
		init();
		getDataToShow();

		setViewPager();
		addTabs();

		associateFragmentIdToPartSpeech();
		getWordsFromCore();
		
		
	}


	/**
	 * initializing the data of the activity
	 */
	public void init() {
		hashMapTypeWordAssociatedToFragment = new HashMap<Integer,MyTab>();
		m_hashMapWords = new HashMap<PartSpeech, ArrayList<Word>>();
		hashMapFragments = new HashMap<Integer, DictionaryFragment>();
		m_core = new CoreDictionary(this);
		m_controller = new ControllerDictionary(m_core);
	}

	/**
	 * initiating and populating the hashMapTypeWordAssociatedToFragment
	 */
	public void associateFragmentIdToPartSpeech() {
		
		
		
		hashMapTypeWordAssociatedToFragment.put
			(1, new MyTab(PartSpeech.VERB, getResources().getDrawable(R.drawable.round_borders_item_verb),
			getResources().getColor(R.color.darkBlue))); 
		hashMapTypeWordAssociatedToFragment.put
			(2, new MyTab(PartSpeech.NOUN, getResources().getDrawable(R.drawable.round_borders_item_noun),
			 getResources().getColor(R.color.purpole)));
		hashMapTypeWordAssociatedToFragment.put
			(3, new MyTab(PartSpeech.ADJECTIVE, getResources().getDrawable(R.drawable.round_borders_item_adjective),
			 getResources().getColor(R.color.darkGreen)));
		hashMapTypeWordAssociatedToFragment.put
			(4, new MyTab(PartSpeech.ADVERB, getResources().getDrawable(R.drawable.round_borders_item_adverb),
		    getResources().getColor(R.color.red)));
		
	}

	/**
	 * obtains the id of the book whose unknown words will be shown
	 */
	public void getDataToShow() {
		
		Integer idBook = getIntent().getIntExtra(ConstantsTabs.idBook, 0);
		String book = getIntent().getStringExtra(ConstantsTabs.titleBook);
		
	
		setTitle(getTitle() + ": " + book.substring(0, Math.min(book.length(), 30)));
		if(book.equals(Constants.m_AllBooks))
			getAllDataFromDatabase();
		else
			getBookData(book);
	}
	
	/**
	 * retrieves from database the book with the title received as parameter
	 * @param name : the title of the book received
	 */
	public void getBookData(String name)
	{
		ArrayList<Word> words = new ArrayList<Word>();
		Book book = MainActivity.m_database.getBook(AttributesBook.TITLE, name);
		//Log.i("message", "date :" + book.getTitle() + " " + book.getId());
		
		List<Appeareance> apps = MainActivity.m_database.getAppearances(
			 AttributesAppearences.BOOK, Integer.toString( book.getId()));
		
		
		if(apps == null)
			Log.i("message", "este app null");
		else{
			for(Appeareance app : apps )
			{
				
			//	Log.i("message","ActivityDictionary - getBookData" + app.getId() + " " + app.getIdBook() + " " + app.getIdWord() + " " + app.getPage());
				
				Word word = MainActivity.m_database.getWord(AttributesWord.ID, Integer.toString( app.getIdWord()) );
				Log.i("message", "ActivityDictionary - getBookData" + word.getValue() +  " " + word.getPart());
				words.add(word);
				
			}

		}
		
		
		m_core.setM_wordsFromBook(words); 
	}
	
	
	/**
	 * obtains data from database regarding the words to be shown 
	 */
	public void getAllDataFromDatabase()
	{

		ArrayList<Word> words = MainActivity.m_database.getAllWords(); 		
		
		
		
		m_core.setM_wordsFromBook(words); 
		Log.i("message", "Size-ul databaselului este este : " + words.size());
		
	}
	

	/**
	 * gets the data from the core to be set in the activity's members
	 */
	public void getWordsFromCore() {
		
		m_hashMapWords = m_core.addWordsInSet();
		
	}


	/**
	 * loads the pager adapter
	 * the page adapter is the adapter to which the layout of the VIEWPAGER is hooked
	 */
	public void setViewPager() {
		
		m_adapter = new TabPageAdapter(this.getFragmentManager(), this);
		m_viewPager = (ViewPager) findViewById(R.id.viewPagerDictionaryTabs);
		m_viewPager.setAdapter(m_adapter);
		

	}

	/**
	 * sets the tabs to the action bar
	 */
	public void addTabs() {

		Tab tabVerb, tabNoun, tabAdjective, tabAdverb, tabExpression;
		final ActionBar actionBar = this.getActionBar();
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		View  tabVerbView = inflater.inflate(R.layout.tab_verb, null);
		View  tabNounView = inflater.inflate(R.layout.tab_noun, null);
		View  tabAdjectiveView = inflater.inflate(R.layout.tab_adjective, null);
		View tabAdverbView = inflater.inflate(R.layout.tab_adverb, null);
		
		
		tabVerb = actionBar.newTab().setText(ConstantsTabs.partVerb);
		tabNoun = actionBar.newTab().setText(ConstantsTabs.partNown);
		tabAdjective = actionBar.newTab().setText(ConstantsTabs.partAdjective);
		tabAdverb = actionBar.newTab().setText(ConstantsTabs.partAdverb);
		
		tabVerb.setCustomView(tabVerbView);
		tabNoun.setCustomView(tabNounView);
		tabAdjective.setCustomView(tabAdjectiveView);
		tabAdverb.setCustomView(tabAdverbView);
		
		tabVerb.setTabListener(this);
		tabNoun.setTabListener(this);
		tabAdjective.setTabListener(this);
		tabAdverb.setTabListener(this);
			
        this.m_viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });
		
		actionBar.addTab(tabVerb);
		actionBar.addTab(tabNoun);
		actionBar.addTab(tabAdjective);
		actionBar.addTab(tabAdverb);
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		m_menu = menu;
		getMenuInflater().inflate(R.menu.activity_dictionary, menu);
		 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		Log.i("message", id + "   "  + item.toString() + "  " + R.id.itemSelectBook);
		
		if (id == R.id.action_settings) {
			return true;
		}
		if(id == R.id.itemSelectBook)
		{
			showDialogDictionary().show();
		}
		if(id == R.id.buttonBefore)
		{
			m_core.showBeforeWords();
			update();
		}
		if(id == R.id.buttonNext)
		{
			m_core.showNextWords();
			update();
		}
		
		if(id == R.id.itemSettingsDictionary)
		{
			showDialogSetSettings().show();
		}
			
		
		
		return super.onOptionsItemSelected(item);
		
	}
	
	
	
	/**
	* 
	*/
	public Dialog showDialogSetSettings() {
		
		final CustomDialog customDialog = new CustomDialog(this, R.color.orange);
		LayoutInflater inflater = this.getLayoutInflater();
		View body = inflater.inflate(R.layout.dialog_select_book_dictionary_list, null);
		ArrayAdapter<String> adapter;
		final List<String> options = new ArrayList<String>();
		
		

		options.add(ConstantsTabs.orderId);
		options.add(ConstantsTabs.orderAlph);
		adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, options);
		
		
		
		ListView list = (ListView) body.findViewById(R.id.listviewDialogOpenStartedBooks); 		
		list.setAdapter(adapter);
		list.setOnItemClickListener(m_controller);
		list.setItemChecked(m_core.getM_indexOrderShown(), true);

		
		
		customDialog.setTitle(getResources().getString(R.string.dialogDictionarySettingsTitle));
		customDialog.setView(body);
		
		
		customDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString
		(R.string.dialogSelectBookButtonNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
					// added a Cancel button
					// if the user pushes it, nothing happens
					// it just exists the dialog
			}
		});
		
		customDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString
		(R.string.dialogSelectBookButtonPositive), m_controller);
		
		return customDialog;
		
	}


	private Dialog showDialogDictionary() {
		
		
		final CustomDialog customDialog = new CustomDialog(this, R.color.orange);
		LayoutInflater inflater = this.getLayoutInflater();
		final List<Book> books = MainActivity.m_database.getAllBooks();
		final List<String> titles = new ArrayList<String>();
		View body = inflater.inflate(R.layout.dialog_select_book_dictionary_list, null);

		ListView list;
		ArrayAdapter<String> adapter;
		
		
		for(Book book : books)
			titles.add(book.getTitle());
		
		System.out.println(titles);
		

		customDialog.setTitle(getResources().getString(R.string.dialogSelectBookPage));
		customDialog.setView(body);

		adapter = new ArrayAdapter<>(this, R.layout.spinner_layout_text_center, titles);
		list = (ListView) body.findViewById(R.id.listviewDialogOpenStartedBooks);
		list.setAdapter(adapter);
		
		
		customDialog.setView(body);
		
		
		customDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString
		(R.string.dialogSelectBookButtonNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				// added a Cancel button
				// if the user pushes it, nothing happens
				// it just exists the dialog
					
				
			}
		});
		
		
		return customDialog;
		
		
		
	}
	

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		this.m_viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public static HashMap<Integer, MyTab> getHashMapTypeWordAssociatedToFragment() {
		return hashMapTypeWordAssociatedToFragment;
	}

	public static void setHashMapTypeWordAssociatedToFragment(
			HashMap<Integer, MyTab> hashMapTypeWordAssociatedToFragment) {
		ActivityDictionary.hashMapTypeWordAssociatedToFragment = hashMapTypeWordAssociatedToFragment;
	}
	
	
	/**
	 * updates the information shown in the tabs
	 * after the user has selected previous or next
	 */
	public void update()
	{

		m_hashMapWords = m_core.addWordsInSet();
		m_adapter.notifyDataSetChanged();
		

	}
	

}

 class TabPageAdapter extends FragmentPagerAdapter
{
	 ActivityDictionary activity;

	public TabPageAdapter(FragmentManager fm, ActivityDictionary activity) 
	{
		super(fm);
		this.activity = activity;
		// TODO Auto-generated constructor stub
	}

	@Override
	public android.app.Fragment getItem(int arg0) {
		Fragment fragment =  new DictionaryFragment();
		Bundle bundle = new Bundle();
		Log.i("message", "id-ul tabului este : "  + (arg0 + 1));
		bundle.putInt(DictionaryFragment.PARAMETER_RECEIVED, arg0 + 1);
		fragment.setArguments(bundle);
		
		
		
		return fragment;
	}
	
	public int getItemPosition(Object object) {
	    return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return ConstantsTabs.NUMBER_TABS;
	}
	
}
