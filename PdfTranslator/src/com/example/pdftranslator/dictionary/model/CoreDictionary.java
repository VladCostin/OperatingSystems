package com.example.pdftranslator.dictionary.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.util.Log;

import com.example.pdftranslator.dictionary.ActivityDictionary;
import com.example.pdftranslator.dictionary.ActivityInterface;
import com.example.pdftranslator.dictionary.ConstantsTabs;

import Database.PartSpeech;
import Database.Word;

/**
 * contains the preferences of the user and also the all the words associated to a book
 * @author Vlad Herescu
 *
 */
public class CoreDictionary {

	/**
	 * the total amount of words from the book selected book/ all books
	 */
	 ArrayList<Word> m_wordsFromBook;
	 
	 
	/**
	 * the current index where the words are shown from
	 */
	int m_indexWordsShown;
	
	/**
	 * the number of words to be shown in the tabs
	 */
	 int m_nrWordsShown;
	 
	 
	 /**
	 * the way the words are arranged in the tabs 
	 */
	 String m_typeOrder;
	
	
	 /**
	 * interface implemented by the classes that extend the activity
	 * used to update their interface
	 */
	 ActivityInterface m_interface;
	 
	 /**
	 * the index of the element which shows the order of the words shown : alphabetically or by index
	 */
	private int m_indexOrderShown;
	 

	/**
	 * initialing the Core
	 * @param _interface : the interface containing the main methods of the activities
	 */
	public CoreDictionary(ActivityInterface _interface) {
		m_wordsFromBook = new ArrayList<Word>();
		m_nrWordsShown = ConstantsTabs.m_WordsShownStart;
		m_interface = _interface;
		
		m_indexOrderShown = 0;
	}
	
	/**
	 * creates the HashMap of words to be shown in the activity
	 * @return : the Hashmap created, to be used by the activity
	 */
	public HashMap<PartSpeech,ArrayList<Word>> addWordsInSet()
	{
		HashMap<PartSpeech,ArrayList<Word>> hashMapWords
		= new HashMap<PartSpeech,ArrayList<Word>>();
		ArrayList<Word> shownWords = filterWords();
		
		ArrayList<Word> wordsVerbs = new ArrayList<Word>();
		ArrayList<Word> wordsNouns = new ArrayList<Word>();
		ArrayList<Word> wordsAdjective = new ArrayList<Word>();
		ArrayList<Word> wordsAdverb = new ArrayList<Word>();
		
		
		for(Word word : shownWords){
			
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
		
		
		Log.i("message", "ActivityDictionary - addWordsInSets : " +  hashMapWords);
		
		return hashMapWords;
	}


	/**
	 * @return : the dictionary filtered depending on the
	 * <br>the number set in edit text from the menu 
	 * 
	 */
	public ArrayList<Word> filterWords() {
		
		ArrayList<Word> dictionaryFiltered = new ArrayList<Word>();
		int indexStart,index;
		
		indexStart = m_indexWordsShown - m_nrWordsShown;
		indexStart = Math.max(0, indexStart);
		Log.i("message", "CoreDictionary - filterWords : " + indexStart);
		Log.i("message", "CoreDictionary - filterWords : " + m_indexWordsShown);
		for( index =indexStart; index < m_indexWordsShown; index++)
			dictionaryFiltered.add(m_wordsFromBook.get(index));
		
		Log.i("message", "CoreDictionary - filterWords : " + dictionaryFiltered.size());
		//return m_wordsFromBook;
		return dictionaryFiltered;
	}
	
	/**
	 * shows the words before the ones shown now
	 */
	public void showBeforeWords() 
	{
		m_indexWordsShown = m_indexWordsShown - m_nrWordsShown;
		m_indexWordsShown = Math.max(0, m_indexWordsShown);
		
	}
	
	/**
	 * shows the words after the ones shown now
	 */
	public void showNextWords() {
		
		m_indexWordsShown = m_indexWordsShown + m_nrWordsShown;
		m_indexWordsShown = Math.min(m_wordsFromBook.size(), m_indexWordsShown);
		
	}

	/**
	 * @return the m_wordsFromBook
	 */
	public ArrayList<Word> getM_wordsFromBook() {
		return m_wordsFromBook;
	}


	/**
	 * @param _wordsFromBook the m_wordsFromBook to set
	 */
	public void setM_wordsFromBook(ArrayList<Word> _wordsFromBook) {
		m_wordsFromBook = _wordsFromBook;
		m_indexWordsShown = m_wordsFromBook.size() ;
	}


	/**
	 * @return the m_indexBooksShown
	 */
	public int getM_indexBooksShown() {
		return m_indexWordsShown;
	}


	/**
	 * @param m_indexBooksShown the m_indexBooksShown to set
	 */
	public void setM_indexBooksShown(int m_indexBooksShown) {
		this.m_indexWordsShown = m_indexBooksShown;
	}


	/**
	 * @return the m_nrWordsShown
	 */
	public int getM_nrWordsShown() {
		return m_nrWordsShown;
	}


	/**
	 * @param m_nrWordsShown the m_nrWordsShown to set
	 */
	public void setM_nrWordsShown(int m_nrWordsShown) {
		this.m_nrWordsShown = m_nrWordsShown;
	}
	
	/**
	 * @return the m_typeOrder
	 */
	public String getM_typeOrder() {
		return m_typeOrder;
	}

	/**
	 * @param _typeOrder the m_typeOrder to set
	 * @param position : the index associated with the order prefered 
	 */
	public void setM_typeOrder(String _typeOrder, int position) {
		m_typeOrder = _typeOrder;
		m_indexOrderShown = position;
	}

	/**
	 * sets the new order of the words
	 * depending of the order selected by the user
	 */
	public void setNewOrder()
	{
		
		if(m_typeOrder.equals(ConstantsTabs.orderId)) 
		{
		
			Collections.sort(m_wordsFromBook, new Comparator<Word>() {
				@Override
				public int compare(Word lhs, Word rhs) {
					
					if(lhs.getId() > rhs.getId() )
						return 1;
					
					if(lhs.getId() < rhs.getId() )
						return -1;
					
					return 0;
					
					
				}
			});
		}
		
		
		if(m_typeOrder.equals(ConstantsTabs.orderAlph)) 
		{
		
			Collections.sort(m_wordsFromBook, new Comparator<Word>() {
				@Override
				public int compare(Word lhs, Word rhs) {
					return lhs.getValue().compareTo(rhs.getValue());
				}
			});
		}
		
		m_interface.update();
		
		
	}

	/**
	 * @return the m_indexOrderShown
	 */
	public int getM_indexOrderShown() {
		return m_indexOrderShown;
	}

	/**
	 * @param m_indexOrderShown the m_indexOrderShown to set
	 */
	public void setM_indexOrderShown(int _indexOrderShown) {
		m_indexOrderShown = _indexOrderShown;
	}


	
}
