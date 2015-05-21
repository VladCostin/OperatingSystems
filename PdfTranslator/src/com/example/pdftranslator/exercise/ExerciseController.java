package com.example.pdftranslator.exercise;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * implements the listeners and notifies the controller
 * @author Vlad Herescu
 *
 */
public class ExerciseController implements OnClickListener {

	/**
	 * the model that will take care of the action performed by the user
	 */
	ExerciseModel m_model;
	
	
	/**
	 * @param _model : the model which will execute the function for the 
	 * <br> action performed
	 */
	public ExerciseController(ExerciseModel _model) {
	
		m_model = _model;
	}

	@Override
	public void onClick(View v) {
		
		String valueFromUser = ScreenSlidePageFragmentExercise.m_editTextValue;
		
		
	}

}
