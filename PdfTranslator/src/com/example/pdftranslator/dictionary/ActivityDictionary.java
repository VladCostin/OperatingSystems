package com.example.pdftranslator.dictionary;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.example.pdftranslator.CustomDialog;
import com.example.pdftranslator.MainActivity;
import com.example.pdftranslator.OpenDictionaryDialogInterface;
import com.example.pdftranslator.R;








import Database.Appeareance;
import Database.AttributesAppearences;
import Database.AttributesBook;
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
import android.widget.ListView;
import android.widget.Spinner;

public class ActivityDictionary extends Activity implements ActionBar.TabListener {

	/**
	 * contains the information about the word from the dictionary
	 */
	ViewPager viewPager;
	
	/**
	 * the adapter used to switch between the fragments
	 */
	TabPageAdapter adapter;
	
	
	/**
	 * every fragment gets the list of words needed to be shown from this HASHMAP
	 */
	static HashMap<PartSpeech,ArrayList<Word>> hashMapWords;
	
	/**
	 * the id of the fragment is associated to the Type of words to be shown
	 */
	static HashMap<Integer, MyTab> hashMapTypeWordAssociatedToFragment;
	
	/**
	 * saving the instances of the fragments as ViewGroup for updating
	 */
	//static HashMap<Integer,ViewGroup> hashMapFragments;
	//static HashMap<Integer,View> hashMapFragments;
	static HashMap<Integer,DictionaryFragment> hashMapFragments;
	/**
	 * reference to the ActionBar menu
	 */
	Menu menu;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_dictionary);
			
		init();
		getDataFromIntent();
		setViewPager();
		addTabs();
		getDadaDatabase();
		associateFragmentIdToPartSpeech();
		
		
		
	}

	private void init() {
		hashMapTypeWordAssociatedToFragment = new HashMap<Integer,MyTab>();
		hashMapWords = new HashMap<PartSpeech, ArrayList<Word>>();
		//hashMapFragments = new HashMap<Integer, ViewGroup>();
		//hashMapFragments = new HashMap<Integer, View>();
		hashMapFragments = new HashMap<Integer, DictionaryFragment>();
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
	public void getDataFromIntent() {
		
		Integer idBook = getIntent().getIntExtra(ConstantsTabs.idBook, 0);
		String book = getIntent().getStringExtra(ConstantsTabs.titleBook);
		
		setTitle(getTitle() + ": " + book.substring(0, 30));
		getBookData(book);
	}
	
	/**
	 * retrieves from database the book with the title received as parameter
	 * @param name : the title of the book received
	 */
	public void getBookData(String name)
	{
		/*
		Book book = MainActivity.database.getBook(AttributesBook.TITLE, name);
		
		List<Appeareance> apps = MainActivity.database.getAllAppearances();
		if(apps == null)
			Log.i("message", "este app null");
		else{
			for(Appeareance app : apps )
				Log.i("message", app.getId() + " " + app.getIdBook() + app.getIdWord());
			Log.i("message", "NU ESTE NULL");
		}
		/*
		List<Appeareance> apps = MainActivity.database.getAppearances
				(AttributesAppearences.BOOK, new Integer( book.getId()).toString());
		if(apps == null)
		{
			Log.i("message", "este app null");
		}
		else
			for(Appeareance app : apps )
				Log.i("message", app.getId() + " " + app.getIdBook() + app.getIdWord());
				
		*/
	}
	
	
	/**
	 * obtains data from database regarding the words to be shown 
	 */
	public void getDadaDatabase()
	{
		hashMapWords.clear();
		ArrayList<Word> wordsVerbs = new ArrayList<Word>();
		ArrayList<Word> wordsNouns = new ArrayList<Word>();
		ArrayList<Word> wordsAdjective = new ArrayList<Word>();
		ArrayList<Word> wordsAdverb = new ArrayList<Word>();
		
		List<Word> words = MainActivity.database.getAllWords(); 
		Collections.sort(words, new Comparator(){

			@Override
			public int compare(Object lhs, Object rhs) {
				// TODO Auto-generated method stub
				return 0;
			}
			
		});
		
		for(Word word : words){
		//	Log.i("message", word.getValue());
			if(word.getPart().equals(PartSpeech.VERB.toString()))
				wordsVerbs.add(word);
			if(word.getPart().equals(PartSpeech.NOUN.toString()))
				wordsNouns.add(word);
			if(word.getPart().equals(PartSpeech.ADJECTIVE.toString()))
				wordsAdjective.add(word);
			if(word.getPart().equals(PartSpeech.ADVERB.toString()))
				wordsAdverb.add(word);
		}
		
		hashMapWords.put(PartSpeech.VERB, wordsVerbs);
		hashMapWords.put(PartSpeech.NOUN, wordsNouns);
		hashMapWords.put(PartSpeech.ADJECTIVE, wordsAdjective);
		hashMapWords.put(PartSpeech.ADVERB, wordsAdverb);
		
		Log.i("message", "Size este : " + hashMapWords.get(PartSpeech.VERB));
		
	}
	public void clearDatabase()
	{
		hashMapWords.clear();
	}

	/**
	 * loads the pager adapter
	 * the page adapter is the adapter to which the layout of the VIEWPAGER is hooked
	 */
	public void setViewPager() {
		
		adapter = new TabPageAdapter(this.getFragmentManager(), this);
		this.viewPager = (ViewPager) findViewById(R.id.viewPagerDictionaryTabs);
		viewPager.setAdapter(adapter);
		

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
			
        this.viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
		this.menu = menu;
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
			
		
		
		return super.onOptionsItemSelected(item);
		
	}
	
	
	
	private Dialog showDialogDictionary() {
		
		
		final CustomDialog customDialog = new CustomDialog(this, R.color.orange);
		/*LayoutInflater inflater = this.getLayoutInflater();
		final List<Book> books = MainActivity.database.getAllBooks();
		final List<String> titles = new ArrayList<String>();
		View body = inflater.inflate(R.layout.dialog_select_book_dictionary, null);
		final Spinner spinnerBooks; 
		//View body = inflater.inflate(R.layout.dialog_select_book_dictionary_list, null);
		ListView list;
		ArrayAdapter adapter;
		
		
		for(Book book : books)
			titles.add(book.getTitle());
		
		System.out.println(titles);
		

		customDialog.setTitle(getResources().getString(R.string.dialogSelectBookPage));
		customDialog.setView(body);

		adapter = new ArrayAdapter<>(this, R.layout.spinner_layout_text_center, titles);
		spinnerBooks = (Spinner) body.findViewById(R.id.spinnerBooksDialog);
		spinnerBooks.setAdapter(adapter);
		
		//list = (ListView) body.findViewById(R.id.listview);
		//list.setAdapter(adapter);
		
		customDialog.setButton(DialogInterface.BUTTON_POSITIVE,getResources().getString
				(R.string.dialogSelectBookButtonPositive), new OpenDictionaryDialogInterface(this,books,spinnerBooks));
		
		
		
		customDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString
		(R.string.dialogSelectBookButtonNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
					
				
			}
		});
		
		*/
		return customDialog;
		
		
		
	}
	

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		this.viewPager.setCurrentItem(tab.getPosition());
		
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
	
	
	public void update()
	{
		for(PartSpeech part :hashMapWords.keySet())
		{
			hashMapWords.get(part).clear();
		}

		adapter.notifyDataSetChanged();
		

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
	
	//when you call notifyDataSetChanged(), the view pager will remove all views and reload them all. As so the reload effect is obtained.
//	public int getItemPosition(Object object) {
//	    return POSITION_NONE;
//	}

	/*
    public Object instantiateItem (ViewGroup container, int position)
	{
		 Log.i("message", "sunt in instatiateItem " + position + " " + container.toString());

		 Log.i("message", ActivityDictionary.hashMapFragments.keySet().toString());
		if(!ActivityDictionary.hashMapFragments.containsKey(position+ 1))
		{
			
			
	
			ActivityDictionary.hashMapFragments.put(position+ 1, container);
			container.setTag(ActivityDictionary.hashMapTypeWordAssociatedToFragment.get(position + 1).partSpeech);
		}
		 
		 return super.instantiateItem(container, position);
	}
	*/
	
/*	@Override
	public Object instantiateItem(ViewGroup container, int position) {
	    
	//	LayoutInflater li = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
	//	View root = li.inflate(R.layout.fragment_tab_part_speech, null);
		
	/*	
	    ((ViewPager) container).addView(root);
	    ActivityDictionary.hashMapFragments.put(position+ 1, root);
	    
		Log.i("message","sunt aici" + ActivityDictionary.hashMapFragments.keySet().toString());
		
	    DictionaryFragment fragment = (DictionaryFragment) super.instantiateItem(container, position);
	    if(fragment.view == null)
	    	Log.i("message", "este null");
	    
	    ActivityDictionary.hashMapFragments.put(position, fragment);
	    return fragment;
	}
*/	
/*
	@Override
	public void destroyItem(View collection, int position, Object o) {
	    View view = (View)o;
	    ((ViewPager) collection).removeView(view);
	    ActivityDictionary.hashMapFragments.remove(position);
	    view = null;
	}
  */  
 /*   public void notifyDataSetChanged()
    {
    	/*View v = activity.viewPager.findViewWithTag(PartSpeech.VERB.toString());
    	ListView list = (ListView) v.findViewById(R.id.listViewWords);
    	if(list == null)
    		Log.i("message", "cucu");
    	
    

    	
    	Log.i("message", ActivityDictionary.hashMapFragments.toString());
   // 	Log.i("message", ActivityDictionary.getHashMapTypeWordAssociatedToFragment().toString());
    	Log.i("message", ActivityDictionary.hashMapWords.toString());
    	
    	View view = ActivityDictionary.hashMapFragments.get(1).view;
    	if(view == null)
    		Log.i("message", "ESTE NUUUUL");
    	else
    		Log.i("message", "NU MAI ESTE NULL");
    //	Log.i("message", view.toString());
    	    	
    	 ListView list = (ListView) view.findViewById(R.id.listViewWords);
    	 if(list == null)
    		 Log.i("message", "este null");
    	 
 		ItemAdapter adapter = new ItemAdapter(activity, R.layout.item_listview_word,
				ActivityDictionary.hashMapWords.get(PartSpeech.VERB),ActivityDictionary.getHashMapTypeWordAssociatedToFragment().get(1) );
 		if(adapter ==null)
 			Log.i("message", "adaptrul este null");

		list.setAdapter(adapter);
   /* 	
    	for(Integer key :  ActivityDictionary.hashMapFragments.keySet())
    	{
    		Log.i("message", key + " ");
    		View view = ActivityDictionary.hashMapFragments.get(key);	
    		
    		
    		PartSpeech part = ActivityDictionary.getHashMapTypeWordAssociatedToFragment().
    				get(  key).partSpeech;
    		ListView list = (ListView) view.findViewById(R.id.listViewWords);
    		
    		
    		ItemAdapter adapter = new ItemAdapter(activity, R.layout.item_listview_word,
    				ActivityDictionary.hashMapWords.get(part),ActivityDictionary.getHashMapTypeWordAssociatedToFragment().get(key) );

    		list.setAdapter(adapter);
    	}
    	
    	super.notifyDataSetChanged();
    }
	*/

	
}
