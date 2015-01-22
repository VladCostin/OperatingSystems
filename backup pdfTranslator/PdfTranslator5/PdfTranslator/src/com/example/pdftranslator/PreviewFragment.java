package com.example.pdftranslator;


import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import android.R.color;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PreviewFragment extends Fragment implements OnClickListener{

	/**
	 * object containing the book
	 */
 	PdfReader reader;
 	
 	/**
 	 *	pushed to start the activity where the book is shown 
 	 */
 	Button buttonStartReading;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        //if (savedInstanceState != null) {
        //   mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        //}

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }
    
    
    public void onUpdate(String title)
    {
    	TextView text = (TextView) getActivity().findViewById(R.id.textViewShowPreview);
    	Button button = (Button) getActivity().findViewById(R.id.buttonStartReading);
    	button.setOnClickListener(this);
    	
    	Log.i("message", title);
    	
    	if(title.equals(getActivity().getResources().getString(R.string.textViewNoPreview))) 
    	{
    		text.setText(title);
    		text.setTextSize(30);
    		button.setVisibility(View.GONE);
			text.setGravity(Gravity.CENTER);
			text.setBackground(null); 
    		return;
    	}
    	try {
			reader = new PdfReader(title);
			Log.i("NAME", reader.getNumberOfPages() + " ");
			text.setGravity(0);
			text.setTextSize(18);
			text.setText(PdfTextExtractor.getTextFromPage(reader,1) + 
					    PdfTextExtractor.getTextFromPage(reader,2) +  
					    PdfTextExtractor.getTextFromPage(reader, 3));
			
			text.setBackground(getResources().getDrawable(R.drawable.layout_borders));
			button.setVisibility(View.VISIBLE);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			Log.i("NAME","DA EROARE");
		}
    	
    }


	@Override
	public void onClick(View v) {
		
		Log.i("message", FileBrowser.filePath.getAbsolutePath());
		Intent intent = new Intent(getActivity(), ActivityTextDisplayer.class);
		intent.putExtra(Constants.nameExtraStarttextDisplayer, FileBrowser.filePath.getAbsolutePath());
		startActivity(intent);
		
	}
	
	
}
