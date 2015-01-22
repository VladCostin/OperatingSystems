package com.example.pdftranslator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * @author admin
 *
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {

	/**
	 * the current path shown when the user browses for a pdf file
	 */
	File filePath;
	/**
	 * the history of directories the user search in
	 */
	ArrayList<File> lastDirectoryPath = new ArrayList<File>();
	/**
	 * the button used for browsing for a pdf file
	 */
	Button buttonSelectPdf;
	
	
	/**
	 * the button which shows the opened pdfs
	 */
	Button buttonShowStartedPdf;
	
	
	
	/**
	 *  represents the path to the file to be read
	 */
	String	pathFileToBeShown;
	
	
	/**
	 * code to identify the ActivityTextDisplayer from which the page and the title will be received
	 */
	int codeReaderPass= 1234;
	
	
	/**
	 * code associated with the information received from ActivityTextDispayer
	 */
	static String codeExtraReceiveFromReader = "NAME_PAGE";
	
	
	/**
	 * associated to the list of books starte to be read and the page
	 */
	String keySPGetPdgAndPage = "KEY_STARTED_PDF";
	
	
	/**
	 * 
	 */
	String nameSharedPreferences = "SharedPreferences";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		filePath = new File(Environment.getExternalStorageDirectory().toString() );
		filePath.mkdirs();
		buttonSelectPdf = (Button) findViewById(R.id.buttonChoosePdfFile);
		buttonSelectPdf.setOnClickListener(this);
		
		
		buttonShowStartedPdf = (Button) findViewById(R.id.buttonOpenLastPdfFile);
		buttonShowStartedPdf.setOnClickListener(this);
		
		pathFileToBeShown = null; // in case no file has been selected
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		

		if (filePath.exists()) {

			if( ( ( Button) v) == buttonSelectPdf )
			{ 
				Dialog dialogListFiles = filesDirectoryDialog();
				dialogListFiles.show();
			}
			
			if( ( (Button) v ) == buttonShowStartedPdf)
			{	

				openedPdfFiles().show();
			}	
			
			
		}
		
	}

	private Dialog filesDirectoryDialog() {
		
		final AlertDialog.Builder builder; // instance which will create 
        final List<String> files = new ArrayList<String>(); // files names obtained from in the current directory
        List<Integer> ico = new ArrayList<Integer>(); // the icons assciated to the files from the current directory
        final File listFiles[] = filePath.listFiles();	  // the files obtained in the current directory
        ListAdapter arrayAdapter = new ArrayAdapterWithIcon(this, files, ico);
		
	    builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString( R.string.dialogBrowserTitle));
		
		
		for(File file : listFiles)
		{
			if(file.isDirectory())
			{
				ico.add(R.drawable.ic_folder);
				files.add(file.getName());
			}
			if(file.isFile() && file.getName().endsWith("pdf"))
			{
				ico.add(R.drawable.ic_file);
				files.add(file.getName());
			}
			

		}        
        
		
		
        builder.setAdapter(arrayAdapter,
        new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
        
        		System.out.println("I Have chosen " + which + " " + listFiles[which]);
        		File f = new File(filePath+ "/" + files.get(which) + "/" );
        		System.out.println(f.getAbsolutePath());
        		lastDirectoryPath.add(filePath);
        		filePath = f;
        		Log.i("NAME", f.getName() + " " + f.getAbsolutePath()+ " " + f.isDirectory());
        		if(f.isDirectory())	
        			filesDirectoryDialog().show();
        		else
        		{
        			pathFileToBeShown = lastDirectoryPath.
        			get(lastDirectoryPath.size() - 1).getAbsolutePath()+"/"+ f.getName();
        			startIntent();
        		}
        		
        				
        		

        	
        }});
        
        if(lastDirectoryPath.size() != 0)
        {
        	builder.setPositiveButton(this.getResources().getString
        	(R.string.buttonBackBrowser), new DialogInterface.OnClickListener() {
        		
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
        			filePath = lastDirectoryPath.remove(lastDirectoryPath.size() - 1);
        			filesDirectoryDialog().show();
        		}
        	
        	});
        }
        

		return builder.create();
	}
	
	
	public Dialog openedPdfFiles()
	{
		final AlertDialog.Builder builder; // instance which will create 
		SharedPreferences prefs;
		Set<String> openedPdf;
		String[] stringOpenedPdf;
		int i=0;
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString( R.string.dialogBrowserTitle));
		
		prefs = getSharedPreferences(this.nameSharedPreferences, Context.MODE_PRIVATE);
		openedPdf = prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
	//	stringOpenedPdf = openedPdf.toArray(new String[openedPdf.size()]);
		stringOpenedPdf =  new String[openedPdf.size()];
		
		for(String openedPdfFile : openedPdf)
		{
			
			openedPdfFile = openedPdfFile.split(Constants.separatorNamePage)[0];
			stringOpenedPdf[i] = openedPdfFile;
			i++;
		}
		
	/*	String[] aaa = new String[3];
		aaa[0] = "arsdfsdfs";
		aaa[1] = "sdfsdfsdfs";
		aaa[2] = "fgdfgdfgd";
	*/
   //     ListAdapter arrayAdapter = new ArrayAdap
		builder.setSingleChoiceItems(stringOpenedPdf, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return builder.create();
	}
	
	
	/**
	 * starts a new activity
	 */
	public void startIntent()
	{
		Intent intent = new Intent(this, ActivityTextDisplayer.class);
		intent.putExtra(Constants.nameExtraStarttextDisplayer, pathFileToBeShown);
		startActivityForResult(intent, codeReaderPass);
	}
	
	public void onActivityResult (int requestCode, int resultCode, Intent data)
	{
	/*	Log.i("NAME", "am primit ceva " + requestCode + " " + resultCode);
		if(data == null)
			Log.i("NAME", "ESTE NULL");
		else 
			Log.i("NAME", "NU ESTE NULL");
	*/	
		if(requestCode == codeReaderPass && data!= null)
		{
			SharedPreferences prefs;
			Set<String> booksStarted;
			String returnedData = data.getStringExtra(MainActivity.codeExtraReceiveFromReader);
			String bookName, book_Set_Name;
			
			returnedData = returnedData.replace
			(Environment.getExternalStorageDirectory().toString() + "/", ""); 
			bookName = returnedData.split(Constants.separatorNamePage)[0];
			

			
			Log.i("NAME", returnedData + " " + bookName);
			
	   //	Log.i("NAME", data.getStringExtra(codeExtraReceiveFromReader));
			
			 prefs = this.getSharedPreferences
			(this.nameSharedPreferences, Context.MODE_PRIVATE);
			
			 booksStarted = prefs.getStringSet(keySPGetPdgAndPage, new HashSet<String>());
			 for(String book_page : booksStarted)
			 {
				 book_Set_Name = book_page.split(Constants.separatorNamePage)[0];
				 if(book_Set_Name.equals(bookName))
				 {
					 booksStarted.remove(book_page);
					 break;
				 }
			 }
			 booksStarted.add(returnedData);
			 prefs.edit().putStringSet(keySPGetPdgAndPage,  booksStarted).apply();;
		}
	}


}



class ArrayAdapterWithIcon extends ArrayAdapter<String> {

	private List<Integer> images;

	public ArrayAdapterWithIcon(Context context, List<String> items, List<Integer> images) {
		super(context, android.R.layout.select_dialog_item, items);
		this.images = images;
	}

	public ArrayAdapterWithIcon(Context context, String[] items, Integer[] images) {
		super(context, android.R.layout.select_dialog_item, items);
		this.images = Arrays.asList(images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setCompoundDrawablesWithIntrinsicBounds(images.get(position), 0, 0, 0);
		textView.setCompoundDrawablePadding(
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
		return view;
	}

}
