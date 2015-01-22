package com.example.pdftranslator;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ActivityTextDisplayer extends ActionBarActivity implements OnSeekBarChangeListener {
	
	/**
	 * the textView where the page will be displayed
	 */
	TextView textViewPageContainer;
	/**
	 * the slider used to jump to other page
	 */
	SeekBar  sliderJumpToPage;
	/**
	 * object containing the book
	 */
	PdfReader reader;
	
	/**
	 * contains the path and the name of the file to be displayed
	 */
	String stringFilePath;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_text_displayer);
		
		
		textViewPageContainer = (TextView) findViewById(R.id.textViewPageContainer);
		sliderJumpToPage = (SeekBar) findViewById(R.id.sliderPdfBook);
		sliderJumpToPage.setOnSeekBarChangeListener(this);
		try {
			reader = new PdfReader(getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		obtainDataFileToShow();
	}

	/**
	 * gets the pdf file to be shown : whether it was selected from browser or it was a book the user started to read previously
	 */
	public void obtainDataFileToShow() {
		
		stringFilePath = getIntent().getStringExtra(Constants.nameExtraStarttextDisplayer);
		sliderJumpToPage.setMax(reader.getNumberOfPages());
		
		
		fillTextView(1);
		

	/*	Intent intentOutput = new Intent();
		intentOutput.putExtra(MainActivity.codeExtraReceiveFromReader,
		stringFilePath + Constants.separatorNamePage + sliderJumpToPage.getProgress());
		setResult(RESULT_OK, intentOutput);
		finish();
	*/	
		
	}

	/**
	 * fills the textView with the text of the page whose index is received as apramater
	 * @param pageIndex : the index of the page to be shown
	 */
	public void fillTextView(int pageIndex) {
		  String textPage;
		
		try {
	      textPage = PdfTextExtractor.getTextFromPage(reader, pageIndex); //Extracting the content from a particular page.
	      textPage = textPage.replaceAll("\n[a-z]", " ");
	      textPage = textPage.replaceAll("\n [a-z]", " ");
	      textPage = textPage.replaceAll("\n[a-z] ", " ");
	      
	      
	      textPage = textPage.replaceAll(".\n[a-z]", " ");
	      textPage = textPage.replaceAll(".\n [a-z]", " ");
	      textPage = textPage.replaceAll(".\n[a-z] ", " ");
	      
	      textPage = textPage.replaceAll(",\n[a-z]", " ");
	      textPage = textPage.replaceAll(",\n [a-z]", " ");
	      textPage = textPage.replaceAll(",\n[a-z] ", " ");
	     
	      
	      textViewPageContainer.setText(textPage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_text_displayer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {


		Log.i("NAME", Integer.toString(seekBar.getProgress()));
		fillTextView(seekBar.getProgress());
		
	}
	
	
	public void onBackPressed(){
		Intent intentOutput = new Intent();
		intentOutput.putExtra(MainActivity.codeExtraReceiveFromReader,
		stringFilePath + Constants.separatorNamePage + sliderJumpToPage.getProgress());
		setResult(RESULT_OK, intentOutput);
		super.onBackPressed();
	}
	

	
}
