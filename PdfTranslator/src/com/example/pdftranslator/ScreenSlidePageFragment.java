/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.pdftranslator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.memetix.mst.language.Language;

import InternetConnection.InternetConnection;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.widget.TextView;

/**
 * A fragment representing a single step in a wizard. 
 *
 */
public class ScreenSlidePageFragment extends Fragment implements OnTouchListener {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
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
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        String textFromPdf;
        TextView textViewDisplayer;

        try {		
					
			textFromPdf = PdfTextExtractor.getTextFromPage
			(ActivityTextDisplayer.reader,mPageNumber + 1);
			textFromPdf = textArranged(textFromPdf);
	
			
			textViewDisplayer =  (TextView) rootView.findViewById(android.R.id.text1);
			textViewDisplayer.setOnTouchListener(this);
			textViewDisplayer.setText(textFromPdf);

			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        


        return rootView;
    }
    
    /**
     * @param textPage : the page read from pdf, without being arranged
     * @return : the arranged text page : the string continues until the end of the line 
     */
    public String textArranged(String textPage)
    {  	    
          String regex = "\n([a-z]|[A-Z]|[',.\"]())";
    	  ArrayList<Integer> indexes = patternMethod(regex, textPage);
    	
    	
  	      return  deleteValues(textPage, indexes);
  	     
  	      

    }
    
	/**
	 * replaces the unnecessary  \n with " " 
	 * @param text : the text unparsed
	 * @param indexes : the indexes of \n
	 * @return : the parsed text
	 */
    public static String deleteValues(String text, ArrayList<Integer> indexes) {
		
		StringBuffer buffer = new StringBuffer(text);
		for(Integer index : indexes)
			buffer.replace(index, index+ 1, " ");
		

		return buffer.toString();
	}

	/**
	 * @param regex : the pattern used to identify the unnecessary \n
	 * @param text : the unparsed text
	 * @return : the list of indexes with the \n identified
	 * 
	 */
	public static ArrayList<Integer> patternMethod(String regex, String text)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
	    Pattern pattern = Pattern.compile(regex, 0);
	    Matcher matcher = pattern.matcher(text);
	    while (matcher.find()) {
	        values.add(matcher.start());
	    }
	    
	    return values;
	}
    
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		Log.i("message", ActivityTextDisplayer.isBooleanSearchNewWord() + " ");
		ViewParent parent = ((TextView) v).getParent(); 
	//	parent.requestDisallowInterceptTouchEvent(true);
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
			//	if(ActivityTextDisplayer.isBooleanSearchNewWord() == false)
			//		return false;
			
			//	ActivityTextDisplayer.setBooleanSearchNewWord(false);
				determineWordSelected(v, event);
				break;

			default:
				
				break;
		}
		

		return true;
	}

    /**
     * @param v : the TEXTVIEW touched
     * @param event : the Action_down event
     */
    public void determineWordSelected(View v,MotionEvent event) {
    	
    	Layout layout = ((TextView) v).getLayout();
   	    int x = (int)event.getX();
   	    int y = (int)event.getY();
   	    
   	    Log.i("NAME", Thread.currentThread().getId() + " in touch event fragment" + " " + event.toString());
   	  

   	    
   	    if (layout!=null){
   	    	
   	    	
   	    //	Log.i( "NAME", ActivityTextDisplayer.letter_index.values().toString());
   	        int line = layout.getLineForVertical(y);
   	        int offset = layout.getOffsetForHorizontal(line, x);	        
   	        int copy_offset= offset;
   	        String word = ActivityTextDisplayer.letter_index.get(offset);
   	       
   	        Log.i("message", ""+offset + " ---" + word + "--- " );
   	        
   	        if(word.contains(" ") || word.contains("\n"))
   	        {
   	        	ActivityTextDisplayer.setBooleanSearchNewWord(true); 
   	    	   return;
   	        }
   	        
   	      
   	        
   	        while(true)
   	        {
   	        	copy_offset--;
   	        	if(ActivityTextDisplayer.letter_index.get(copy_offset ).equals(" ") ||
   	        	   ActivityTextDisplayer.letter_index.get(copy_offset ).equals("\n") || 
   	        	   ActivityTextDisplayer.letter_index.get(copy_offset ).equals("."))
   	        		break;
   	        	word = ActivityTextDisplayer.letter_index.get(copy_offset) + word;
   	        }
   	        
   	        copy_offset = offset;
   	        
   	        while(true)
   	        {
   	        	copy_offset++;
   	        	if(ActivityTextDisplayer.letter_index.get(copy_offset ).equals(" ") || 
   	        	   ActivityTextDisplayer.letter_index.get(copy_offset ).equals("\n") || 
   	        	   ActivityTextDisplayer.letter_index.get(copy_offset ).equals(".") ||
   	        	   ActivityTextDisplayer.letter_index.get(copy_offset ).equals(",")
   	        	   )
   	        		break;
   	        	word =  word + ActivityTextDisplayer.letter_index.get(copy_offset);
   	        }
   	       
   	       
   	    ActivityTextDisplayer.m_item_Translate.setTitle("Translate " + word.substring(0, Math.min(20, word.length())));
   	       
   	  //      Log.i("NAME", ""+offset + " " + line + " ------" + ActivityTextDisplayer.letter_index.get(offset ) + "-----   -----" + word + "------"); 
   	    }
		
	}




	/**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }



}
