package InternetConnection;

import com.example.pdftranslator.ActivityTextDisplayer;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class InternetConnection extends AsyncTask<String, Void, String> {
	
	
	Activity displayer;
	
	public InternetConnection(Activity activity)
	{
		  BingTranslator.setIds();
		  this.displayer = activity;
	}

	@Override
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
				
			}
    		
    	});

    	return builder.create();

	}
	

}
