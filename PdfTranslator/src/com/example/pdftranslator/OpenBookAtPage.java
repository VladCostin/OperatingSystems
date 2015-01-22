package com.example.pdftranslator;

import java.util.List;

import Database.Book;
import android.content.DialogInterface;

public class OpenBookAtPage implements DialogInterface.OnClickListener {

	MainActivity activity;
	
	public OpenBookAtPage(MainActivity activity,List<Book> books ) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
