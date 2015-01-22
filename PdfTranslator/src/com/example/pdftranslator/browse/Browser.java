package com.example.pdftranslator.browse;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.pdftranslator.ActivityTextDisplayer;
import com.example.pdftranslator.Constants;
import com.example.pdftranslator.R;
import com.example.pdftranslator.R.drawable;
import com.example.pdftranslator.R.id;
import com.example.pdftranslator.R.layout;
import com.example.pdftranslator.R.string;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Browser extends Fragment implements OnItemClickListener,OnClickListener{
	
	
	/**
	 * contains the names of the files shown in listFiles
	 */
    List<String> files;
    
    
    /**
     * contains the names of the files shown in listFiles
     */
    ArrayList<File> allInfoFiles;
	

	/**
	 * the history of directories the user search in
	 */
	ArrayList<File> lastDirectoryPath;
	
	
	/**
	 * the container of the browser
	 */
	RelativeLayout layoutBrowser;
	
	 OnHeadlineSelectedListener mCallback;
	 
	 /**
	 * the list where the files/documents are shown
	 * filled in onStart and when searched for PDF files 
	 */
	ListView listView;
	
	
	/**
	 * associates the id of each button : open/preview with
	 * the id of a file
	 */
	HashMap<Integer,Integer> hashMapFileToOpen;
	


	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {

	        View view = inflater.inflate(R.layout.fragment_browser, container, false);
	        listView = (ListView) view.findViewById(R.id.listviewBrowser);
	        listView.setOnItemClickListener(this);
	        
	        return view;
	 }
	 
	 public void onStart()
	 {
		 super.onStart();
		 FileBrowser.setFilePath(new File(Environment.getExternalStorageDirectory().toString() ));
		 FileBrowser.getFilePath().mkdirs();
	    
	     files = new ArrayList<String>(); // files names obtained from in the current directory
	     allInfoFiles = new ArrayList<File>();
	     hashMapFileToOpen = new HashMap<Integer,Integer>();
	     //listFiles = (ListView) getActivity().findViewById(R.id.listviewBrowser);
	   
	     lastDirectoryPath = new ArrayList<File>();

	     createFileList(FileBrowser.getFilePath().listFiles());

	    

	 }


	
	
	public interface OnHeadlineSelectedListener{
		
		public void onArticleSelected(String title);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Log.i("message", position + " ");
		
		//File f = new File(FileBrowser.filePath+ "/" +  files.get(position)  );
		File f = allInfoFiles.get(position);
		String titleBook= getResources().getString(R.string.textViewNoPreview);
		Log.i("message", f.isFile() + " " + f.isDirectory() + " ----" + f.getAbsolutePath() + "----- #####" + f.getName() + "##### " + FileBrowser.getFilePath());
		if(f.isDirectory())	
		{
			
			Log.i("message", titleBook + "  a intrat in director ");
			lastDirectoryPath.add(FileBrowser.getFilePath());
		    FileBrowser.setFilePath(f);
		    createFileList(FileBrowser.getFilePath().listFiles());
		    FileBrowser.itemBack.setVisible(true);

			
		}
		if(f.isFile())
		{
			FileBrowser.setFileTobeShown(f);
			titleBook = f.getAbsolutePath();
			Log.i("message", titleBook + "  il considera fisier ");

		}
		this.mCallback.onArticleSelected(titleBook);
		
		
	}
	
	/**
	 * determines which files and folders are in the current path and show
	 * them in the listFiles list view
	 * @param filesData :the files to be shown in the listFile
	 */
	public void createFileList(File filesData[])
	{
		
		
		

	/*	List<Integer> ico = new ArrayList<Integer>(); // the icons assciated to the files from the current directory
		ListAdapter arrayAdapter;

		files.clear();
		allInfoFiles.clear();
		Log.i("message", "trebuie sa arate alte fisiere");

		 for(File file : filesData)
		 {
			
				if(file.isDirectory())
				{
						ico.add(R.drawable.ic_folder);
						files.add(file.getName());
						allInfoFiles.add(file);
				}
				if(file.isFile() && file.getName().endsWith("pdf"))
				{
					
					ico.add(R.drawable.ic_file);
					files.add(file.getName());
					allInfoFiles.add(file);
				}
				
					

		}  
		arrayAdapter = new ArrayAdapterWithIcon(getActivity(), files, ico);
	    listView.setAdapter(arrayAdapter);*/
	    
		
		files.clear();
		allInfoFiles.clear();
		hashMapFileToOpen.clear();
		Log.i("message", "trebuie sa arate alte fisiere");
		List<Integer> ico = new ArrayList<Integer>(); 
		 for(File file : filesData)
		 {
			
				if(file.isDirectory())
				{
						ico.add(R.drawable.ic_folder);
						files.add(file.getName());
						allInfoFiles.add(file);
				}
				if(file.isFile() && file.getName().endsWith("pdf"))
				{
					
					ico.add(R.drawable.ic_file);
					files.add(file.getName());
					allInfoFiles.add(file);
				}
				
					

		}  
		 
		
	//	IconAdapter adapter = new IconAdapter(this, R.layout.item_listview_browser, files);
		IconAdapter adapter = new IconAdapter(this, R.layout.item_listview_browser, files,ico);
		listView.setAdapter(adapter); 
	}
	
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			this.mCallback = (OnHeadlineSelectedListener) activity;
		
		} catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
		
	}
	
	public void onUpdate()
	{
		FileBrowser.setFilePath(lastDirectoryPath.
		remove(lastDirectoryPath.size() - 1));
		
		createFileList(FileBrowser.getFilePath().listFiles());
		
		if(lastDirectoryPath.size() == 0)
			FileBrowser.itemBack.setVisible(false);
		
		 
	}

	@Override
	public void onClick(View v) {
		
		Log.i("message", "ID-UL ESTE : " + v.getId());
		if(v instanceof Button)
		{
			Button button = (Button) v;
			Integer id = (Integer) button.getTag();
			Log.i("message", id.toString());
			int idFile = hashMapFileToOpen.get(id);
			String titleBook= getResources().getString(R.string.textViewNoPreview);
			File f = allInfoFiles.get(idFile);
			
			
		//	Log.i("message",  "hashmapul este : " + hashMapFileToOpen.toString());
		//	for(File ff : allInfoFiles)
		//	{
		//		Log.i("message", ff.getAbsolutePath() + " " + ff.getName());
		//	}
			
			if( button.getText().equals(getActivity().getResources().getString(R.string.buttonPreview)))
			{
				FileBrowser.setFileTobeShown(f);
				titleBook = f.getAbsolutePath();
				Log.i("message", titleBook + "  il considera fisier ");
				this.mCallback.onArticleSelected(titleBook);
			}
			
			if(button.getText().equals(getActivity().getResources().getString(R.string.buttonStartReadin)))
			{
				FileBrowser.setFileTobeShown(f);
				FileBrowser.setFilePath(f);
				Log.i("message", FileBrowser.getFilePath().getAbsolutePath());
				startIntent(f.getName(), f.getAbsolutePath());
				//Intent intent = new Intent(getActivity(), ActivityTextDisplayer.class);
				//intent.putExtra(Constants.nameExtraStarttextDisplayer, FileBrowser.getFilePath().getAbsolutePath());
				//startActivity(intent);
				
			}
			
		}
		
		
	}
	
	/**
	 * starts a new activity
	 */
	public void startIntent(String fileName, String pathFileToBeShown)
	{
		Intent intent = new Intent(this.getActivity(), ActivityTextDisplayer.class);
		intent.putExtra(Constants.nameExtraStarttextDisplayer, pathFileToBeShown);
		intent.putExtra(Constants.nameFile, fileName);
		startActivity(intent);
	}

 
}

class IconAdapter extends ArrayAdapter<String>
{
	Browser browser;
	Activity activity;
	List<String> objects;
	List<Integer> ico;
	int layoutResourceId;

	public IconAdapter(Browser browser, int resource, List<String> objects, List<Integer> ico) {
		//super(br.getActivity(), resource, objects);
		super(browser.getActivity(),resource, objects);
		this.objects = objects;
		layoutResourceId = resource;
		this.activity = browser.getActivity();
		this.ico = ico;
		this.browser = browser;
	}
	 @Override
	  public View getView(int i, View convertView, ViewGroup viewGroup) {
		 
		 
		 
		 
		 ItemFile file = new ItemFile();
		 LayoutInflater inflater = activity.getLayoutInflater();
		 convertView = inflater.inflate(layoutResourceId, viewGroup, false);
		 file.fileName = (TextView) convertView.findViewById(R.id.textViewFilename);
		 file.button = (Button) convertView.findViewById(R.id.buttonPreview);
		 file.open = (Button) convertView.findViewById(R.id.buttonOpenFile);
		 file.fileName.setText(objects.get(i));
		 
		 
	     file.fileName.setCompoundDrawablesWithIntrinsicBounds(ico.get(i), 0, 0, 0);
	     if(ico.get(i) == R.drawable.ic_file)
	     {
	    	 file.button.setVisibility(View.VISIBLE);
	    	 file.open.setVisibility(View.VISIBLE);
	    	 file.button.setTag(new Integer(i + 1));
	    	 file.open.setTag(new Integer(-(i+ 1)));
	    	 browser.hashMapFileToOpen.put(new Integer(i + 1), i);
	    	 browser.hashMapFileToOpen.put(new Integer(-(i+ 1)), i);
	     }
	     
	     file.button.setOnClickListener(browser);
	     file.open.setOnClickListener(browser);
		 convertView.setTag(file);

	    return convertView; 

	 }
	 
	 class ItemFile
	 {
		 TextView fileName;
		 
		 Button button;
		 
		 Button open;
	 }
	
}


/*class ArrayAdapterWithIcon extends ArrayAdapter<String>{
	
	List<Integer> ico_id;

	public ArrayAdapterWithIcon(Context context,  List<String> files, List<Integer> ico)
	{
		super(context,android.R.layout.simple_list_item_1, files);
		ico_id = ico;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = super.getView(position, convertView, parent);
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setCompoundDrawablesWithIntrinsicBounds(ico_id.get(position), 0, 0, 0);
		textView.setCompoundDrawablePadding(
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
		return view;
	}
	
}*/
