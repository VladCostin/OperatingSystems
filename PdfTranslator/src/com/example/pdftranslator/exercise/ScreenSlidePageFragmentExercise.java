package com.example.pdftranslator.exercise;


import com.example.pdftranslator.R;

import Database.Word;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragmentExercise extends Fragment implements OnClickListener {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    
    /**
     * the edit text where the user has inserted the data
     */
    static String m_editTextValue;
    
    /**
     * the text view where the answer Wrong or Right will be shown
     */
     TextView m_textViewAnswerApp;
     
     
     String valueCorrect;
    
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     * @param pageNumber : the page to be instantiated
     * @param m_controller : the controller that listenes for the events
     * @return : the new fragment
     */
    public static ScreenSlidePageFragmentExercise create(int pageNumber) {
        ScreenSlidePageFragmentExercise fragment = new ScreenSlidePageFragmentExercise();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page_exercise, container, false);
        Word word = ExerciseModel.getWordAtPosition(mPageNumber);
        
        ((TextView) rootView.findViewById(R.id.CellWordValue)).setText(word.getValue());
        ((TextView) rootView.findViewById(R.id.CelllDefinitionValue)).setText(word.getDefinition());
        ((TextView) rootView.findViewById(R.id.CelllExampleValue)).setText(word.getExample());
        ((TextView) rootView.findViewById(R.id.CelllParthSpeechValue)).setText(word.getPart());
        Log.i("message", "Translation" + word.getTranslation());
        
        EditText m_editText = (EditText)  rootView.findViewById(R.id.CellAnswer);
        valueCorrect = word.getTranslation();
        
        m_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            	m_editTextValue = s.toString();
            }
        });
        
        
        Button m_button = (Button)  rootView.findViewById(R.id.ExerciseCellButtonCheck);
        //m_button.setOnClickListener(ExerciseSlider.m_controller);
        m_button.setOnClickListener(this);
        
        m_textViewAnswerApp = (TextView) rootView.findViewById(R.id.CellCheck);
      
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    
    /*
    public static void update()
    {
    }
	*/

	@Override
	public void onClick(View v) {
		
		
	//	 boolean valueCheck = //ExerciseSlider.m_model.checksValue(m_editTextValue );
		 if(valueCorrect.equals(m_editTextValue))
		 {
			 m_textViewAnswerApp.setText(Constants.message_right);
			 m_textViewAnswerApp.setTextColor(getResources().getColor( R.color.green));
			 ExerciseModel.addIfCorrect(mPageNumber);
		 }
		 else
		 {
			 m_textViewAnswerApp.setText(Constants.message_wrong);
			 m_textViewAnswerApp.setTextColor(getResources().getColor( R.color.red));
		 }
		 

		
	}
	

}


