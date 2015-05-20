package InternetConnection;

import java.util.List;

import com.example.pdftranslator.ActivityTextDisplayer;
import com.example.pdftranslator.MainActivity;
import com.example.pdftranslator.R;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
/*import com.wordnik.client.api.WordsApi;
import com.wordnik.client.api.WordApi;
import com.wordnik.client.api.WordListApi;
import com.wordnik.client.api.AccountApi;
import com.wordnik.client.model.ApiTokenStatus;
import com.wordnik.client.model.WordObject;
import com.wordnik.client.model.WordOfTheDay;
import com.wordnik.client.model.WordSearchResult;
*/


import com.wordnik.client.model.Definition;
import com.wordnik.client.model.Example;
import com.wordnik.client.model.ExampleSearchResults;
import com.wordnik.client.model.ScoredWord;
import com.wordnik.client.api.WordApi;
import com.wordnik.client.common.ApiException;

import Database.Appeareance;
import Database.PartSpeech;
import Database.Word;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class InternetConnection extends AsyncTask<String, Void, Word> {
	
	ActivityTextDisplayer displayer;
	
    private ProgressDialog progress;
	
	
	public InternetConnection(ActivityTextDisplayer activity)
	{
		  BingTranslator.setIds();
		  this.displayer = activity;
		  progress = new ProgressDialog(displayer);
	}

	@Override
	protected Word doInBackground(String... params) {
		
		/*
		progress.setMessage("Downloading Music :) ");
	    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    progress.setIndeterminate(true);
	    progress.show();
		
		*/
		 WordApi api = new WordApi();
		 Log.i("message", params[0]);
	     api.addHeader("api_key", "938e5765b6eb9c0d2a20007483900cf7b48a83d09b9d32ee8");
	     Word word = new Word();
	     Example example;
	     ExampleSearchResults exampleResult;
	     List<Definition> definitions;
	     
	     word.setValue(params[0]);
	     word.setTranslation("tr1");
	     word.setDefinition("def1");
	     word.setExample("example1");
	     word.setPart(PartSpeech.VERB.toString());
	     
	     
	     Log.i("message", PartSpeech.VERB.toString() + "--  --" + PartSpeech.NOUN.toString() + "-- --" + PartSpeech.ADJECTIVE.toString() + "-- --" + PartSpeech.ADVERB.toString());
	     
	     Log.i("message", "datele primite : " + params[0] + " " + params[1] + " " + params[2]);
	     try {
	    	
	    	 definitions = api.getDefinitions(params[0], null, null, null, null, null, null);
	    	 exampleResult = api.getExamples(params[0], null, null, null, null);
 
	    	 if(definitions.size() != 0)
	    	 {
	    		 word.setDefinition(definitions.get(0).getText());
	    		 word.setPart(definitions.get(0).getPartOfSpeech().toUpperCase());
	    		 Log.i("message", "Part este --" + word.getPart() + "--");
	    	 }
	    	 if(exampleResult.getExamples().size() != 0)
	    		word.setExample( exampleResult.getExamples().get(0).getText());
	    	 
	    	 word.setTranslation(getBingTranslation(params)); 
	    	 word.setValue(params[0]);
	    	 
	    	 
	    	 
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	 
	     return word;
	     
	     
	}
	
	
	public String getBingTranslation(String... params)
	{
		Log.i("NAME", params[0]);
		Log.i("NAME", params[1]);
		Log.i("NAME", params[2]);
		Language lan1 = Language.fromString(params[1]);
		Language lan2 = Language.fromString(params[2]);

		return BingTranslator.translateFromBing(params[0], lan1, lan2);
	}
	
	 protected void onPostExecute(Word word) {
		 
		 Dialog builder = createDialogWithWordTranslated(word);
		 
		/* WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		 lp.copyFrom(displayer.getWindow().getAttributes());
		 lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		 lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		 builder.getWindow().setAttributes(lp);
		 */
		 builder.show();
		 displayer.progress.cancel();

		 
     }
	 
	 /**
	  * saves the word in database
	  * also, adds an entrance in Appeareance table associating the word to the current book
	 * @param word : word to be saved in database
	 */
	public void saveDataIntoDictionary(Word word) {
		
		 Integer iWord, iBook;
		 
		 iWord = MainActivity.m_database.getWordId(word.getValue());
		 if(iWord == null)
		 {
			 MainActivity.m_database.addWord(word);
			 iWord = MainActivity.m_database.getWordId(word.getValue());
		 }
		 
		 Log.i("message", "Part este --" + word.getPart() + "--"); 
		
		 MainActivity.m_database.addAppeareance(
				 new Appeareance(ActivityTextDisplayer.getBook().getId(), iWord,  ActivityTextDisplayer.getSliderJumpToPage().getProgress()));
		
	}

	private Dialog createDialogWithWordTranslated(Word rezult) {
			
	    Log.i("NAME", "A intrat si aici !!!! inca o data");
	    	
	   	AlertDialog.Builder builder = new AlertDialog.Builder(displayer);
	   	TextView textViewTranslation, textViewDefinition, textViewExample, textViewPart;
	    LayoutInflater inflater = 	displayer.getLayoutInflater();
	    final View tableLayout = inflater.inflate(R.layout.dialog_word_translation, null); // first you have to inflate
	    
	    
	   	builder.setTitle(Constants.builderShowTitle + rezult.getValue());
	 //  	builder.setMessage(rezult.getDefinition() + " " + rezult.getPart() + " " + rezult.getExample());
	   	
	   	builder.setView(tableLayout);
	   	Log.i("message", rezult.getDefinition() + " " + rezult.getExample() + " " + rezult.getPart() + " " + rezult.getTranslation() + "  " + rezult.getValue());
	   	
	   	textViewTranslation = (TextView) tableLayout.findViewById( R.id.CellTranslationvalue);
	   	textViewDefinition = (TextView) tableLayout.findViewById(R.id.CelllDefinitionValue);
	   	textViewExample = (TextView) tableLayout.findViewById(R.id.CelllExampleValue);
	   	textViewPart = (TextView) tableLayout.findViewById(R.id.CelllParthSpeechValue);
	
	   	
	   	if(displayer == null)
	   		Log.i("message", "layout null");
	   	if(textViewTranslation == null)
	   		Log.i("message", "tran null");
	   	if(textViewExample == null)
	   		Log.i("message", "exa ple is null");
	   	
	   	
	   	textViewTranslation.setText(rezult.getTranslation());
	   	textViewDefinition.setText(rezult.getDefinition());
	   	textViewPart.setText(rezult.getPart());
	   	textViewExample.setText(rezult.getExample());
	   	
	   	

	    saveDataIntoDictionary(rezult);
		
	   	
	   	builder.setPositiveButton(Constants.builderPositiveResponse, new  DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
					
			}
	    		
	    });
	   	
	   	
	   	builder.setNegativeButton(Constants.buildernegativeResponse, new  DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// save data into
		//		ActivityTextDisplayer.setBooleanSearchNewWord(true); 
					
			}
	    		
	    });
	   	
	   	


	   
	    
	   	ActivityTextDisplayer.setBooleanSearchNewWord(true); 
	    return builder.create();

	}
	


/*	@Override
	protected String doInBackground(String... params) {

		Log.i("NAME", params[0]);
		Log.i("NAME", params[1]);
		Log.i("NAME", params[2]);
		Language lan1 = Language.fromString(params[1]);
		Language lan2 = Language.fromString(params[2]);

		return BingTranslator.translateFromBing(params[0], lan1, lan2);

	}
	 protected void onPostExecute(String translatedText) {
		 createDialogWithWordTranslated(translatedText).show();;
     }
	
	
	private Dialog createDialogWithWordTranslated(String rezult) {
		
    	Log.i("NAME", "A intrat si aici !!!! inca o data");
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(displayer);
    	
    	
    	builder.setTitle(Constants.builderShowTitle + rezult);
    	builder.setMessage(rezult);
    	builder.setPositiveButton(Constants.builderPositiveResponse, new  DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// save data into
				ActivityTextDisplayer.setBooleanSearchNewWord(true); 
				
			}
    		
    	});

    	return builder.create();

	}*/
	

}
