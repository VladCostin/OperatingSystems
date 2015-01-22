package com.example.pdftranslator.browse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.pdftranslator.MainActivity;
import com.example.pdftranslator.R;
import com.example.pdftranslator.browse.Browser.OnHeadlineSelectedListener;

import android.support.v4.app.FragmentActivity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

/**
 * activity where the user can select a book to read
 * 
 * @author Vlad Herescu
 *
 */
public class FileBrowser extends FragmentActivity implements OnHeadlineSelectedListener, OnQueryTextListener {

	
	/**
	 * the current path shown when the user browses for a pdf file
	 */
	private static File filePath;
	
	/**
	 *  the file to be shown
	 */
	private static File fileTobeShown;
	

	 /**
	 *  instance of search view used to find PDF files
	 */
	private MySearchView searchView;
	
	/**
	 * used to collapse after submitting
	 */
	private MenuItem itemSearch;
	
	/**
	 * used to collapse if the user is in root
	 */
	static MenuItem itemBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_browser);
		
		
		Log.i("message", "FileBrowuser : reintra d-aici");

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_browser, menu);
		
	    SearchManager searchManager =
	            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	     searchView =
	             (MySearchView) menu.findItem(R.id.action_search).getActionView();
	     searchView.setSearchableInfo(
	             searchManager.getSearchableInfo(getComponentName()));
	     searchView.setOnQueryTextListener(this);
	     searchView.setQueryHint("pdf");
	     //searchView.setQueryHint(getResources().getText(R.string.action_search));
	     //searchView.setQuery("pdf", false);
	     
	     
	     itemSearch = (MenuItem) menu.findItem(R.id.action_search);
	     itemBack = (MenuItem) menu.findItem(R.id.action_back);
	     itemBack.setVisible(false);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id)
		{
			//case R.id.action_settings:  Log.i("message", "curulet1");return true;
			//case R.id.action_search : Log.i("message", "curulet2"); return true;
			case R.id.action_back : 
				returnToOldDirectory();
				return true;
			default:return super.onOptionsItemSelected(item);
				
		}
		

	}

	private void returnToOldDirectory() {
		
		Browser browser = (Browser)
		getSupportFragmentManager().findFragmentById(R.id.fragment_browser);
		
		if(browser != null)
			browser.onUpdate();
	}


	@Override
	public void onArticleSelected(String title) {
	
		PreviewFragment preview = (PreviewFragment) 
		getSupportFragmentManager().findFragmentById(R.id.fragment_preview);

		if(preview !=null)
			preview.onUpdate(title); 
		
		
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		Log.i("message", query);
		File copy_filePath = new File(filePath.getAbsolutePath());
		List<File> allFiles = (List<File>) recursively(copy_filePath,query);
		File [] allFilesArray	= new File[allFiles.size()];
		allFiles.toArray(allFilesArray);
		
		Browser browser = (Browser) 
		getSupportFragmentManager().findFragmentById(R.id.fragment_browser);
		
		
		browser.createFileList(allFilesArray);
		browser.lastDirectoryPath.add(FileBrowser.filePath);
		itemBack.setVisible(true);
		

		itemSearch.collapseActionView();
		
		
		return true;
	}

	private ArrayList<File> recursively(File path, String queryName) {
		
		 File[] files =  path.listFiles();
		 ArrayList<File> files_check_query = new ArrayList<File>();
		 for(File file : files)
		 {
			 if(file.isDirectory())
				 files_check_query.addAll(recursively(file, queryName));
			 else
				 if(file.getName().contains(queryName))
				 files_check_query.add(file);
		 }
		
		
		// TODO Auto-generated method stub
		return files_check_query;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}


	public static File getFilePath() {
		return filePath;
	}


	public static void setFilePath(File filePath) {
		FileBrowser.filePath = filePath;
	}


	public static File getFileTobeShown() {
		return fileTobeShown;
	}


	public static void setFileTobeShown(File fileTobeShown) {
		FileBrowser.fileTobeShown = fileTobeShown;
	}


	public MySearchView getSearchView() {
		return searchView;
	}


	public void setSearchView(MySearchView searchView) {
		this.searchView = searchView;
	}


	public MenuItem getItemSearch() {
		return itemSearch;
	}


	public void setItemSearch(MenuItem itemSearch) {
		this.itemSearch = itemSearch;
	}
	
	

}


class MySearchView extends SearchView{
	
	public MySearchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MySearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	
}
