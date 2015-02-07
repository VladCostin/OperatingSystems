package com.example.pdftranslator.dictionary.controller;

import com.example.pdftranslator.dictionary.ActivityDictionary;
import com.example.pdftranslator.dictionary.model.CoreDictionary;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * receives the input from the user
 * notifies the model to execute the business logic
 * @author Vlad Herescu
 *
 */
public class ControllerDictionary implements  DialogInterface.OnClickListener, OnItemClickListener {

	CoreDictionary m_core;
	
	public ControllerDictionary(CoreDictionary _core)
	{
		m_core = _core;
	}
	
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		m_core.setNewOrder();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		
		m_core.setM_typeOrder( parent.getItemAtPosition(position).toString());
		
		
	}

}
