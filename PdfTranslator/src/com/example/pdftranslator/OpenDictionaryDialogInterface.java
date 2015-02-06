package com.example.pdftranslator;

import java.util.List;

import com.example.pdftranslator.dictionary.ActivityDictionary;
import com.example.pdftranslator.dictionary.ConstantsTabs;

import Database.Book;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


/**
 * customized dialog for dictionary 
 * @author admin
 *
 */
public class OpenDictionaryDialogInterface implements DialogInterface.OnClickListener, OnItemClickListener
{
	Context m_context;
	List<Book> books;
	ListView view;

	public OpenDictionaryDialogInterface(Context activity, List<Book> books, ListView spinnerBooks) {
		
		this.m_context = activity;
		this.books = books;
		this.view = spinnerBooks;
		
	}
	

	
	@Override
	public void onClick(DialogInterface dialog, int which) {
	
		//Intent intent = new Intent(packageContext, cls)
		startActivity(Constants.m_AllBooks); 

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		startActivity(parent.getItemAtPosition(position).toString());

	}
	
	
	/**
	 * shows the words in the dictionary from a book selected or from all books
	 * @param titleBook : the title of the book of which the words will be shown
	 * 
	 */
	public void startActivity(String titleBook)
	{
		
    	Intent intent = new Intent(m_context, ActivityDictionary.class);
    	intent.putExtra(ConstantsTabs.titleBook, titleBook);
    	m_context.startActivity(intent);
	}
	
}
