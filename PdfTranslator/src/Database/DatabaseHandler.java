package Database;

import java.util.ArrayList;
import java.util.List;

import com.example.pdftranslator.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.SuperscriptSpan;

public class DatabaseHandler extends SQLiteOpenHelper
{

	static String name = "Dictionary";
	
	static int version = 3;
	
	
	public DatabaseHandler(Context context) {
		super(context, name, null, version);
	
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String create_table_Book, create_table_appearences, create_table_word;
		
		create_table_Book = "Create table " + AttributesBook.table_name + " ( " +
							AttributesBook.ID + " INTEGER PRIMARY KEY, " +
							AttributesBook.TITLE + " TEXT, " +
							AttributesBook.DIFFICULTY + " TEXT,  "+
							AttributesBook.NR_PAGES + " TEXT,  " +
							AttributesBook.PATH +")";
		create_table_appearences = "Create table " + AttributesAppearences.table_name + " ( " +
							AttributesAppearences.ID + " INTEGER PRIMARY KEY, " +
							AttributesAppearences.BOOK + " INTEGER, " +
							AttributesAppearences.WORD + " INTEGET, " +
							AttributesAppearences.PAGE + " INTEGER) ";
		
		create_table_word = "Create table " + AttributesWord.table_name + " ( " +
							AttributesWord.ID + " INTEGER PRIMARY KEY, " +
							AttributesWord.VALUE + " TEXT, " +
							AttributesWord.translation + " TEXT, " +
							AttributesWord.definition	+ " TEXT, " +
							AttributesWord.example + "  TEXT, " +
							AttributesWord.part	+ " TEXT)";
		
		db.execSQL(create_table_Book);
		db.execSQL(create_table_word);
		db.execSQL(create_table_appearences);
		

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + AttributesBook.table_name);
		db.execSQL("DROP TABLE IF EXISTS " + AttributesWord.table_name);
		db.execSQL("DROP TABLE IF EXISTS " + AttributesAppearences.table_name);
		
		onCreate(db);
		
		
	}
	
	
	public void addBook(Book book)
	{
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesBook.TITLE, book.getTitle());
		values.put(AttributesBook.DIFFICULTY, book.getDifficulty());
		values.put(AttributesBook.NR_PAGES, book.getNr_pages());
		values.put(AttributesBook.PATH, book.getPath());

		db.insert(AttributesBook.table_name, null, values);
		db.close();
	}
	
	
	public void addWord(Word word)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesWord.VALUE, word.getValue());
		values.put(AttributesWord.translation, word.getTranslation());
		values.put(AttributesWord.definition, word.getDefinition());
		values.put(AttributesWord.example, word.getExample());
		values.put(AttributesWord.part, word.getPart());
		
		db.insert(AttributesWord.table_name, null, values);
		
	}
	
	public void addAppeareance(Appeareance appeareance)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AttributesAppearences.BOOK, appeareance.getIdBook());
		values.put(AttributesAppearences.WORD, appeareance.getIdWord());
		values.put(AttributesAppearences.PAGE, appeareance.getPage());
		
		
		db.insert(AttributesAppearences.table_name, null, values);
	}
	
	
	public Book getBook(String fieldName, String value) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(AttributesBook.table_name, new String[] 
	    		{ AttributesBook.ID,AttributesBook.TITLE, AttributesBook.DIFFICULTY, AttributesBook.NR_PAGES, AttributesBook.PATH },
	    		fieldName + "=?",
	            new String[] { value }, null, null, null, null);
	    if(cursor == null || cursor.getCount() == 0)
	    	return null;
	   
	    cursor.moveToFirst();
	   
	 
	    Book book = new Book(Integer.parseInt(cursor.getString(0)),
	            							   cursor.getString(1),
	            							   Integer.parseInt(cursor.getString(2)),
	            							   cursor.getInt(3),
	            							   cursor.getString(4));
	    // return contact
	    return book;
	}
	
	public List<Book> getAllBooks() {
	    List<Book> bookList = new ArrayList<Book>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + AttributesBook.table_name;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Book book = new Book();
	            book.setId(Integer.parseInt(cursor.getString(0)));
	            book.setTitle(cursor.getString(1));
	            book.setDifficulty( Integer.parseInt(cursor.getString(2)));
	            book.setNr_pages(cursor.getInt(3));
	            book.setPath(cursor.getString(4));
	            // Adding contact to list
	            bookList.add(book);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return bookList;
	}
	
	public Word getWord(String fieldName, String value)
	{
	    SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(AttributesWord.table_name, new String[] 
	    		{ AttributesWord.ID,
	    		  AttributesWord.VALUE,
	    		  AttributesWord.translation,
	    		  AttributesWord.definition,
	    		  AttributesWord.example,
	    		  AttributesWord.part
	    		},
	    		fieldName + "=?",
	            new String[] { value }, null, null, null, null);
	    
	    if(cursor.getCount() == 0 || cursor == null)
	    	return null;
	    
	    cursor.moveToFirst();
	 
	    Word word = new  Word(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2),cursor.getString(3),
	            cursor.getString(4), cursor.getString(5));
	    // return contact
	    return word;
	}
	
	public ArrayList<Word> getAllWords() {
	    ArrayList<Word> wordList = new ArrayList<Word>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + AttributesWord.table_name;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Word word = new Word();
	            word.setId(Integer.parseInt(cursor.getString(0)));
	            word.setValue(cursor.getString(1));
	            word.setTranslation(cursor.getString(2));
	            word.setDefinition(cursor.getString(3));
	            word.setExample(cursor.getString(4));
	            word.setPart(cursor.getString(5));
	            // Adding contact to list
	            wordList.add(word);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return wordList;
	}
	
	public Appeareance getApeAppeareance(String fieldName, String value) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(AttributesAppearences.table_name, new String[] 
	    		{ AttributesAppearences.ID,
	    		  AttributesAppearences.BOOK, 
	    		  AttributesAppearences.WORD,
	    		  AttributesAppearences.PAGE},
	    		fieldName + "=?",
	            new String[] { value }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Appeareance appeareance = new Appeareance
	   	(Integer.parseInt(cursor.getString(0)),
	   	 Integer.parseInt(cursor.getString(1)),
	   	 Integer.parseInt(cursor.getString(2)),
	   	 Integer.parseInt(cursor.getString(3))
	   	 );
	    // return contact
	    return appeareance;
	}
	
	public List<Appeareance> getAllAppearances() {
	    List<Appeareance> appearanceList = new ArrayList<Appeareance>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + AttributesAppearences.table_name;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	          Appeareance appeareance = new Appeareance();
	          appeareance.setId(cursor.getInt(0));
	          appeareance.setIdBook(cursor.getInt(1));
	          appeareance.setIdWord(cursor.getInt(2));
	          appeareance.setPage(cursor.getInt(3));
	          appearanceList.add(appeareance);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return appearanceList;
	}
	
	/**
	 * 
	 */
	public List<Appeareance> getAppearances(String fieldName, String value) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Appeareance> appearanceList = new ArrayList<Appeareance>();
	 
		Cursor cursor = db.query(AttributesAppearences.table_name, new String[] 
				{ AttributesAppearences.ID,
				AttributesAppearences.BOOK, 
				AttributesAppearences.WORD,
				AttributesAppearences.PAGE},
				fieldName + "=?",
				new String[] { value }, null, null, null, null);
		if (cursor == null || cursor.getCount() == 0)
			return null;
 
		if (cursor.moveToFirst()) {
		        do {
		          Appeareance appeareance = new Appeareance();
		          appeareance.setId(cursor.getInt(0));
		          appeareance.setIdBook(cursor.getInt(1));
		          appeareance.setIdWord(cursor.getInt(2));
		          appeareance.setPage(cursor.getInt(3));
		          appearanceList.add(appeareance);
		        } while (cursor.moveToNext());
		    }
		 
		 // return contact list
		 return appearanceList;

	}
	
	
	
	public void deleteAllWords()
	{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(AttributesWord.table_name, null, null);
		   db.close();
	}
	
	public void deleteAllApp()
	{
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.delete(AttributesAppearences.table_name, null, null);
		   db.close();
	}
	
	
	/**
	 * @param titleBook : the title of the 
	 * @return : returns the id of a book 
	 */
	public int getBookId(String titleBook)
	{
		 return getBook(AttributesBook.TITLE, titleBook).id;
	}
	
	/**
	 * @param wordValue : the word searched in database
	 * @return : the id of the word searched
	 */
	public Integer getWordId(String wordValue)
	{
		Word word = getWord(AttributesWord.VALUE, wordValue);
		if(word == null)
			return null;
		
		return word.id;
	}
	
	
	
}
