package com.example.pdftranslator;

import java.util.List;

import com.example.pdftranslator.dictionary.ActivityDictionary;

import Database.Book;
import android.content.DialogInterface;
import android.widget.ListView;
import android.widget.Spinner;


/**
 * customized dialog for dictionary 
 * @author admin
 *
 */
public class OpenDictionaryDialogInterface implements DialogInterface.OnClickListener
{
	MainActivity mainActivity;
	ActivityDictionary dictionaryActivity;
	List<Book> books;
	ListView view;
//	Spinner spinnerBooks;
/*	
	public OpenDictionaryDialogInterface(MainActivity activity, List<Book> books, Spinner spinnerBooks) {
		
		this.mainActivity = activity;
		this.books = books;
		this.spinnerBooks = spinnerBooks;
		
	}
	
	public OpenDictionaryDialogInterface(ActivityDictionary activity, List<Book> books, Spinner spinnerBooks) {
		
		this.dictionaryActivity = activity;
		this.books = books;
		this.spinnerBooks = spinnerBooks;
		
	}
*/
	public OpenDictionaryDialogInterface(MainActivity activity, List<Book> books, ListView spinnerBooks) {
		
		this.mainActivity = activity;
		this.books = books;
		this.view = spinnerBooks;
	//	this.spinnerBooks = spinnerBooks;
		
	}
	
	public OpenDictionaryDialogInterface(ActivityDictionary activity, List<Book> books, ListView spinnerBooks) {
		
		this.dictionaryActivity = activity;
		this.books = books;
		this.view = spinnerBooks;
	//	this.spinnerBooks = spinnerBooks;
		
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
	
		for(Book book : books) 
			if(book.getTitle().equals(view.getSelectedItem().toString()))
			{
					if(mainActivity != null)
						mainActivity.openActivityShowDictionary(book);
					else
						dictionaryActivity.update();
					break;
			}

	}
	
}
