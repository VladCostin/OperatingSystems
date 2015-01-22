package com.example.pdftranslator.dictionary;



import java.util.ArrayList;

import com.example.pdftranslator.ActivityTextDisplayer;
import com.example.pdftranslator.R;

import Database.PartSpeech;
import Database.Word;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DictionaryFragment  extends Fragment
{
	static String PARAMETER_RECEIVED = "TAB_ID";
	

	

	

	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle data)
	{
		 Bundle args = getArguments();
		 
		 
		
		View view = inflater.inflate(R.layout.fragment_tab_part_speech, root, false);
		
		if( ActivityDictionary.getHashMapTypeWordAssociatedToFragment().
				get(  args.getInt(PARAMETER_RECEIVED)) == null)
			Log.i("message", "error at getInt(par" + " " + args.getInt(PARAMETER_RECEIVED));
		if( ActivityDictionary.getHashMapTypeWordAssociatedToFragment().
				get(  args.getInt(PARAMETER_RECEIVED)).partSpeech == null)
			Log.i("message", "error at partSpeech" + " " + args.getInt(PARAMETER_RECEIVED));
		
		
		
		PartSpeech part = ActivityDictionary.getHashMapTypeWordAssociatedToFragment().
				get(  args.getInt(PARAMETER_RECEIVED)).partSpeech;
		ListView list = (ListView) view.findViewById(R.id.listViewWords);
		
		
		ItemAdapter adapter = new ItemAdapter(getActivity(), R.layout.dialog_word_translation,
					ActivityDictionary.hashMapWords.get(part),ActivityDictionary.getHashMapTypeWordAssociatedToFragment().get(args.getInt(PARAMETER_RECEIVED)) );
		
	//	ItemAdapter adapter = new ItemAdapter(getActivity(), R.layout.item_listview_word,
	//			ActivityDictionary.hashMapWords.get(part),ActivityDictionary.getHashMapTypeWordAssociatedToFragment().get(args.getInt(PARAMETER_RECEIVED)) );

		list.setAdapter(adapter);
		
		
		return view;
		
		
	}
	

	
}

//class ItemAdapter extends ArrayAdapter<String>
class ItemAdapter extends ArrayAdapter<Word>
{
	Activity context; 
    int layoutResourceId;    
    String data[];
    MyTab tab;
    ArrayList<Word> wordsToBeShown;
    
    
	 public ItemAdapter(Activity activity, int layoutResourceId,ArrayList<Word> wordsToBeShown, MyTab tab) {
		 	super(activity, layoutResourceId, wordsToBeShown);
	        this.layoutResourceId = layoutResourceId;
	        this.context = activity;
	        this.wordsToBeShown = wordsToBeShown;
	        this.tab = tab;
	 }
	 @Override
	  public View getView(int i, View convertView, ViewGroup viewGroup) {
		LayoutInflater inflater = context.getLayoutInflater();
		WordViews word = new WordViews();
		
		
		convertView = inflater.inflate(layoutResourceId, viewGroup, false);
		convertView.setBackground(tab.drawable);
		
		word.translationKey = (TextView) convertView.findViewById(R.id.CellTranslation);
		word.definitionKey = (TextView) convertView.findViewById(R.id.CellDefinition);
		word.exampleKey = (TextView) convertView.findViewById(R.id.CellExample);
		
		word.header = (TextView) convertView.findViewById(R.id.CellHeadeer);
		word.header.setVisibility(View.VISIBLE);
		word.translation = (TextView) convertView.findViewById(R.id.CellTranslationvalue);
		word.definition = (TextView) convertView.findViewById(R.id.CelllDefinitionValue);
		word.example = (TextView) convertView.findViewById(R.id.CelllExampleValue);
		//convertView.findViewById(R.id.CellPartSpeech).setVisibility(View.GONE);
		convertView.findViewById(R.id.CellPartSpeechRow).setVisibility(View.GONE);
		/*
		word.header = (TextView) convertView.findViewById(R.id.textViewWordValueHeader);
		word.translation = (TextView) convertView.findViewById(R.id.textViewWordTranslation);
		word.definition = (TextView) convertView.findViewById(R.id.textViewWordDefinition);
		word.example = (TextView) convertView.findViewById(R.id.textViewWordExample);
		*/
		word.header.setText(wordsToBeShown.get(i).getValue()); 
		word.translation.setText(wordsToBeShown.get(i).getTranslation());
		word.definition.setText(wordsToBeShown.get(i).getDefinition());
		word.example.setText(wordsToBeShown.get(i).getExample());
		
		word.header.setTextColor(tab.color);
		word.translation.setTextColor(tab.color);
	//	word.definition.setTextColor(tab.color);
	//	word.example.setTextColor(tab.color);

		

		convertView.setTag(word);
		
	    return convertView; 
 
	 }
	 
	 static class WordViews
	 {

		 
		 TextView translationKey;
		 
		 TextView definitionKey;
		 
		 TextView exampleKey;
		 
		 
		 TextView header;
		 
		 TextView translation;
		 
		 TextView definition;
		 
		 TextView example;
	 }
	 
}




