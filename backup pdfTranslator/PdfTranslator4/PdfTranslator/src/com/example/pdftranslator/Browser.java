package com.example.pdftranslator;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Browser extends Fragment implements OnItemClickListener{
	
	/**
	 * shows the files in the current directory
	 */
	ListView listFiles;
	
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


	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {

	        return inflater.inflate(R.layout.fragment_browser, container, false);
	 }
	 
	 public void onStart()
	 {
		 super.onStart();
		 FileBrowser.filePath = new File(Environment.getExternalStorageDirectory().toString() );
		 FileBrowser.filePath.mkdirs();
	    
	     files = new ArrayList<String>(); // files names obtained from in the current directory
	     allInfoFiles = new ArrayList<File>();
	     listFiles = (ListView) getActivity().findViewById(R.id.listviewBrowser);
	     listFiles.setOnItemClickListener(this);
	     lastDirectoryPath = new ArrayList<File>();

	     createFileList(FileBrowser.filePath.listFiles());

	    

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
		Log.i("message", f.isFile() + " " + f.isDirectory() + " ----" + f.getAbsolutePath() + "----- #####" + f.getName() + "##### " + FileBrowser.filePath);
		if(f.isDirectory())	
		{
			
			Log.i("message", titleBook + "  a intrat in director ");
			lastDirectoryPath.add(FileBrowser.filePath);
		    FileBrowser.filePath = f;
		    createFileList(FileBrowser.filePath.listFiles());
		    FileBrowser.itemBack.setVisible(true);

			
		}
		if(f.isFile())
		{
			FileBrowser.fileTobeShown = f;
			titleBook = f.getAbsolutePath();
			Log.i("message", titleBook + "  il considera fisier ");
			
		//	mCallback.onArticleSelected(titleBook);
		}
		this.mCallback.onArticleSelected(titleBook);
		
	/*	if(lastDirectoryPath.size() != 0)
	    {
	        	
	        FileBrowser.filePath = lastDirectoryPath.remove(lastDirectoryPath.size() - 1);

	    }*/

		
	/*	System.out.println("I Have chosen " + which + " " + listFiles[which]);
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
		}*/
		
	}
	
	/**
	 * determines which files and folders are in the current path and show
	 * them in the listFiles list view
	 * @param filesData :the files to be shown in the listFile
	 */
	public void createFileList(File filesData[])
	{

		List<Integer> ico = new ArrayList<Integer>(); // the icons assciated to the files from the current directory
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
	    listFiles.setAdapter(arrayAdapter);
	    
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
		FileBrowser.filePath = lastDirectoryPath.
		remove(lastDirectoryPath.size() - 1);
		
		createFileList(FileBrowser.filePath.listFiles());
		
		if(lastDirectoryPath.size() == 0)
			FileBrowser.itemBack.setVisible(false);
		
		 
	}

 
}

class ArrayAdapterWithIcon extends ArrayAdapter<String>{
	
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
	
}
