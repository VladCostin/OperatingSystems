package com.example.pdftranslator.exercise;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.pdftranslator.MainActivity;

import Database.Word;

/**
 * 
 * class that deals with data managing retrieved from database, that checks values, etc 
 * @author Vlad Herescu
 *
 */
public class ExerciseModel implements ExerciseModelInterface{

	/**
	 * the words to be tested by the user
	 */
	static ArrayList<Word> m_words;
	
	
	/**
	 * the word to be checked by the application
	 */
	static Word m_currentWordChecked;
	
	
	/**
	 * the list of words to which the translation was correct
	 */
	static ArrayList<Integer> m_indexWordsCorrect;
	
	
	
	
	/**
	 * initializing the data
	 */
	public ExerciseModel() {
		m_words = new ArrayList<Word>();
		m_indexWordsCorrect = new ArrayList<Integer>();
	}
	
	
	@Override
	public void retrieveWords(int _wordsLength) {
		
		List<Word> words = MainActivity.m_database.getAllWords();
		m_words.clear();
		
		
		for(int i = words.size() - _wordsLength; i < words.size(); i++)
			m_words.add(words.get(i));

	}

	@Override
	public boolean checksValue( String _valueFromUser) {
		
		Log.i("message", m_currentWordChecked.getTranslation());
		
		if(m_currentWordChecked.getTranslation().equals(_valueFromUser))
			return true;
		
		return false;
	}
	
	/**
	 * get a word to be tested
	 * @param _position : the ndex associated with the word to be tested
	 * @return : the word to be tested
	 */
	public static Word getWordAtPosition(int _position)
	{
		
		m_currentWordChecked = m_words.get(_position);
		return m_words.get(_position);
	}
	
	/**
	 * @param _index : the id of the word to which the user has responded correctly
	 * 
	 */
	public static void addIfCorrect(int _index)
	{
		if(m_indexWordsCorrect.contains(_index) == false)
			m_indexWordsCorrect.add(_index);
	}
	

}
