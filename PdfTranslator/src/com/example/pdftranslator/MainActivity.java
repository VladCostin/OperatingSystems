package com.example.pdftranslator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.pdftranslator.browse.FileBrowser;
import com.example.pdftranslator.dictionary.ActivityDictionary;
import com.example.pdftranslator.dictionary.ConstantsTabs;



import Database.Book;
import Database.DatabaseHandler;
import Database.PartSpeech;
import Database.Word;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * @author Vlad Herescu
 *
 */
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{


	/**
	 * the button used for browsing for a pdf file
	 */
	Button buttonSelectPdf;
	
	
	/**
	 * the button which shows the opened pdfs
	 */
	Button buttonShowStartedPdf;
	
	/**
	 * the button to show dictionary
	 */
	Button buttonShowDictionary;
	
	
	
	/**
	 *  represents the path to the file to be read
	 */
	String	pathFileToBeShown;
	
	
	/**
	 * code to identify the ActivityTextDisplayer from which the page and the title will be received
	 */
	int codeReaderPass= 1234;
	
	
	/**
	 * code associated with the information received from ActivityTextDispayer
	 */
	static String codeExtraReceiveFromReader = "NAME_PAGE";
	
	
	/**
	 * associated to the list of books starte to be read and the page
	 */
	String keySPGetPdgAndPage = "KEY_STARTED_PDF";
	
	
	/**
	 * 
	 */
	String nameSharedPreferences = "SharedPreferences";
	
	
	/**
	 * contains the data saved such as the dictionary, info about the books, etc
	 */
	public static DatabaseHandler database;
	
	
	/**
	 * the ListView used to open a started book at a specified page 
	 */
	ListView viewOpenPdf;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Core core = new Core();
			
		
		buttonSelectPdf = (Button) findViewById(R.id.buttonChoosePdfFile);
		buttonSelectPdf.setOnClickListener(this);
		
		
		buttonShowStartedPdf = (Button) findViewById(R.id.buttonOpenLastPdfFile);
		buttonShowStartedPdf.setOnClickListener(this);
		
		buttonShowDictionary = (Button) findViewById(R.id.buttonOpenDictionary);
		buttonShowDictionary.setOnClickListener(this);
		
		pathFileToBeShown = null; // in case no file has been selected
		
		//this.getSharedPreferences(nameSharedPreferences, MODE_PRIVATE).edit().putStringSet(keySPGetPdgAndPage,  null).commit();
		
		database = new DatabaseHandler(this);
		//database.onUpgrade(database.getWritableDatabase(), 1, 2);

		//database.deleteAllWords();
		//addDataDatabase();
		//database.onUpgrade(database.getWritableDatabase(), 1, 2);
		
	}

	public void addDataDatabase() {
		
		database.addWord(new Word("verb1", "vvaloare1", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.VERB.toString()));
		database.addWord(new Word("verb2", "vvaloare1", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.VERB.toString()));
		database.addWord(new Word("verb3", "vvaloare1", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.VERB.toString()));
		database.addWord(new Word("verb4", "vvaloare1", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.VERB.toString()));
		
		
		
		database.addWord(new Word("noun1", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.NOUN.toString()));
		database.addWord(new Word("noun2", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.NOUN.toString()));
		database.addWord(new Word("noun3", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.NOUN.toString()));
		database.addWord(new Word("noun4", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.NOUN.toString()));
		
		
		database.addWord(new Word("adjective1", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADJECTIVE.toString()));
		database.addWord(new Word("adjectivef", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADJECTIVE.toString()));
		database.addWord(new Word("adjective3", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADJECTIVE.toString()));
		database.addWord(new Word("adjective4", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADJECTIVE.toString()));
		
		database.addWord(new Word("adverb1", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADVERB.toString()));
		database.addWord(new Word("adverb2", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADVERB.toString()));
		database.addWord(new Word("adverb3", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADVERB.toString()));
		database.addWord(new Word("adverb4", "sdfdsfsdfsdfsdfs", "sdfdsfsdfsdfsdfs", "sdfdfdgdfgdf", PartSpeech.ADVERB.toString()));
		
		
	/*	database.addAppeareance(new Appeareance(1, 1, 1));
		database.addAppeareance(new Appeareance(1, 2, 1));
		database.addAppeareance(new Appeareance(1, 3, 1));
		database.addAppeareance(new Appeareance(1, 4, 1));
		
		database.addAppeareance(new Appeareance(1, 5, 1));
		database.addAppeareance(new Appeareance(1, 6, 1));
		database.addAppeareance(new Appeareance(1, 7, 1));
		database.addAppeareance(new Appeareance(1, 8, 1));
		
		database.addAppeareance(new Appeareance(1, 9, 1));
		database.addAppeareance(new Appeareance(1, 10, 1));
		database.addAppeareance(new Appeareance(1, 11, 1));
		database.addAppeareance(new Appeareance(1, 12, 1));
		
	*/	
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		

	

		if( ( ( Button) v) == buttonSelectPdf )
		{ 
				Intent intent = new Intent(this, FileBrowser.class);
				startActivity(intent);
				
		}
			
		if( ( (Button) v ) == buttonShowStartedPdf)
		{	
				Log.i("message", "cacat");
				openedPdfFiles().show();
		}
		
		if( ( (Button) v) == buttonShowDictionary )
		{
			showDialogDictionary().show();
		}
		
		
	}

	private Dialog showDialogDictionary() {
		
		
		final CustomDialog customDialog = new CustomDialog(this, R.color.orange);
		LayoutInflater inflater = this.getLayoutInflater();
		final List<Book> books = database.getAllBooks();
		final List<String> titles = new ArrayList<String>();
	//	View body = inflater.inflate(R.layout.dialog_select_book_dictionary, null);
		View body = inflater.inflate(R.layout.dialog_select_book_dictionary_list, null);
	//	final Spinner spinnerBooks; 
		ArrayAdapter adapter;
		ListView view;
		
		
		for(Book book : books)
			titles.add(book.getTitle());
		
		System.out.println(titles);
		

		customDialog.setTitle(getResources().getString(R.string.dialogSelectBookPage));
		customDialog.setView(body);

		adapter = new ArrayAdapter<>(this, R.layout.spinner_layout_text_center, titles);
		view = (ListView) body.findViewById(R.id.listviewDialogOpenStartedBooks);
		view.setAdapter(adapter);
		//spinnerBooks = (Spinner) body.findViewById(R.id.spinnerBooksDialog);
		//spinnerBooks.setAdapter(adapter);
		
		
		
		//list = (ListView) body.findViewById(R.id.listview);
		//list.setAdapter(adapter);
		
		customDialog.setButton(DialogInterface.BUTTON_POSITIVE,getResources().getString
				(R.string.dialogSelectBookButtonPositive), new OpenDictionaryDialogInterface(this,books,view));
		
		
		customDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString
		(R.string.dialogSelectBookButtonNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		
		return customDialog;
		
		
	}


	private Dialog filesDirectoryDialog() {
		
		final AlertDialog.Builder builder; // instance which will create 
        final List<String> files = new ArrayList<String>(); // files names obtained from in the current directory
        List<Integer> ico = new ArrayList<Integer>(); // the icons assciated to the files from the current directory
		
	    builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString( R.string.dialogBrowserTitle));
        

		return builder.create();
	}
	
	
	/**
	 * shows the user the pdf sterted to be read
	 */
	public Dialog openedPdfFiles()
	{
		
		final CustomDialog customDialog = new CustomDialog(this, R.color.green);
		LayoutInflater inflater = this.getLayoutInflater();
		final Set<String> books = this.getSetDataBookSharedPreferences();
		final List<String> titles = new ArrayList<String>();
		View body = inflater.inflate(R.layout.dialog_select_book_dictionary_list, null);
		//final Spinner spinnerBooks; 
		
		ArrayAdapter adapter;
		
		
		for(String book : books)
		{
			
			String title[] = book.split(Constants.separatorNamePage);
			titles.add(title[0]);
		}
		customDialog.setTitle(getResources().getString(R.string.dialogSelectBookPage));
		customDialog.setView(body);

		adapter = new ArrayAdapter<>(this, R.layout.spinner_layout_text_center, titles);
		viewOpenPdf = (ListView) body.findViewById(R.id.listviewDialogOpenStartedBooks); 		
		viewOpenPdf.setAdapter(adapter);
		viewOpenPdf.setOnItemClickListener(this);
		
		
		customDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString
		(R.string.dialogSelectBookButtonNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		
		return customDialog;
		
	}
	
	/**
	 * getting the set of titles of the books opened
	 * @return : the titles of the books opened
	 */
	public Set<String> getSetDataBookSharedPreferences()
	{
		SharedPreferences prefs;
		Set<String> openedPdf;
		prefs = getSharedPreferences(this.nameSharedPreferences, Context.MODE_PRIVATE);
		openedPdf = prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
		return openedPdf;
	}
	
	
	/**
	 * starts a new activity
	 */
	public void startIntent(String fileName, int page)
	{
		Intent intent = new Intent(this, ActivityTextDisplayer.class);
		intent.putExtra(Constants.nameExtraStarttextDisplayer, pathFileToBeShown);
		intent.putExtra(Constants.pageSaved, page);
		intent.putExtra(Constants.nameFile, fileName);
		startActivityForResult(intent, codeReaderPass);
	}
	
	
	/**
	 * shows the dictionary for a book selected from the list
	 * it sends to ActivityDictionary data as the id of the book and the title
	 * @param book 
	 * 
	 */
	public  void openActivityShowDictionary(Book book) {
		
		Intent intent  = new Intent(this, ActivityDictionary.class);
		intent.putExtra(ConstantsTabs.idBook, book.getId());
		intent.putExtra(ConstantsTabs.titleBook, book.getTitle());
		
		startActivity(intent); 
		
	}
	
	public void onResume()
	{
		super.onResume();
		addDataAboutStartedPDF();
	}

	/**
	 * retrieves data about the books started
	 */
	public void addDataAboutStartedPDF() {
		
		HashMap<String,Integer> data =Core.getHashMapBetweenMainAndReader();
		SharedPreferences prefs;
		Set<String> booksStarted;
		String returnedData;
		String bookName, book_Set_Name;
		Log.i("message", "data : " + data.toString());
		
		if(data.size() == 0)
			return;
		
		prefs = this.getSharedPreferences
		(this.nameSharedPreferences, Context.MODE_PRIVATE);
		
	    booksStarted = prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
		
	 //   Log.i("message", msg)
		prefs.edit().putStringSet(keySPGetPdgAndPage,  null).commit();
	    
		for(String book: data.keySet())
		{
			
			returnedData = book;

			
			returnedData = returnedData.replace
			(Environment.getExternalStorageDirectory().toString() + "/", ""); 
			bookName = returnedData.split(Constants.separatorNamePage)[0];
			
			
			for(String book_page : booksStarted)
			{
				book_Set_Name = book_page.split(Constants.separatorNamePage)[0];
				if(book_Set_Name.equals(bookName))
				{
					booksStarted.remove(book_page);
					break;
				}
			}
			booksStarted.add(returnedData+ Constants.separatorNamePage+data.get(book));
			
		}
		prefs.edit().putStringSet(keySPGetPdgAndPage,  booksStarted).commit();
		Log.i("message", "cacat : " + this.getSetDataBookSharedPreferences());
		data.clear();
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Set<String> openedPdf;
		String title, bookTitleSharedPreferences;
		final List<Book> books = database.getAllBooks();
		int i=0, page;
		
		openedPdf = this.getSetDataBookSharedPreferences();

		
	    title =  parent.getItemAtPosition(position).toString();
		
		for(String openedFile : openedPdf)
		{
			
			bookTitleSharedPreferences = openedFile.split(Constants.separatorNamePage)[0]; 
			page = Integer.parseInt(openedFile.split(Constants.separatorNamePage)[1]);
			Log.i("message", "date : " + page + " " + bookTitleSharedPreferences);
			
			Log.i("message", bookTitleSharedPreferences.toString() + " " + page);
			
			
			
			if(bookTitleSharedPreferences.equals(title))
			{
				
				pathFileToBeShown = Environment.getExternalStorageDirectory().toString() +"/"+bookTitleSharedPreferences;
				startIntent(books.get(i).getTitle(), page );
				break;
			}
				
			i++;
		}
		
		
	}


}







