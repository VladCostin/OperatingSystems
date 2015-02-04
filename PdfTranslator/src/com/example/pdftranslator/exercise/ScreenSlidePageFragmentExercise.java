package com.example.pdftranslator.exercise;


import com.example.pdftranslator.R;

import Database.Word;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragmentExercise extends Fragment {
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
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
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
        Word word = ExerciseController.getWordAtPosition(mPageNumber);
        
        ((TextView) rootView.findViewById(R.id.CellWordValue)).setText(word.getValue());
        ((TextView) rootView.findViewById(R.id.CelllDefinitionValue)).setText(word.getDefinition());
        ((TextView) rootView.findViewById(R.id.CelllExampleValue)).setText(word.getExample());
        ((TextView) rootView.findViewById(R.id.CelllParthSpeechValue)).setText(word.getPart());
        
        EditText m_editText = (EditText)  rootView.findViewById(R.id.CellAnswer);
        m_editText.setText("   fututi mortii matti  " + mPageNumber + " ----  ");  
        
        
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
        
      
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}


