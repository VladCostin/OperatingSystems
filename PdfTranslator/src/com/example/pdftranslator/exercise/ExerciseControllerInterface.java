package com.example.pdftranslator.exercise;

import java.util.ArrayList;

import Database.Word;

/** 
 * specifies the methods needed to be implemented by the controller
 * @author Vlad Herescu
 *
 */
public interface ExerciseControllerInterface {

	/**
	 * gets the words to be exercised from database
	 * @param _nrWords : number of words tested
	 * @return : the list with words
	 */
	public void retrieveWords(int _nrWords);
	
	/**
	 * @param _word : the word tested by the user
	 * @param _valueFromUser : the value inserted by the user
	 * @return : true if the value inserted is correct
	 * 
	 */
	public boolean checksValue( String _valueFromUser);
	
}
