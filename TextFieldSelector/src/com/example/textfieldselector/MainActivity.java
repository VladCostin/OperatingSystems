package com.example.textfieldselector;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;



import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements  OnTouchListener {

	TextView textContainer;
	LinkedHashMap<Integer,String> letter_index;
	
	/**
	 * object containing the book
	 */
 	PdfReader reader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
	//	TextView textView = (TextView)findViewById(R.id.textViewTextContainer);    
	//	Spannable wordtoSpan = new SpannableString("I know just how to whisper, And I know just how to cry,I know just where to find the answers");          
	//	wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 15, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);    
	//	textView.setText(wordtoSpan);
		
		textContainer = (TextView) this.findViewById(R.id.textViewTextContainer);
		/*String text ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam pretium, dolor in rutrum dignissim, nulla erat dapibus lectus, rutrum suscipit augue neque et orci. Donec interdum vel urna in tempus. Morbi rhoncus fringilla eros in interdum. Vestibulum et sapien et leo varius tincidunt sit amet eu diam. Donec bibendum leo id pellentesque mattis. Sed fringilla nulla gravida ante accumsan imperdiet et a justo. Praesent iaculis venenatis nulla ut blandit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed rhoncus nisi urna, ac elementum velit facilisis bibendum."+
 "Donec arcu nibh, porttitor eu eleifend sed, venenatis eu leo. Nulla porta velit in justo rutrum, vitae ullamcorper est bibendum. Donec at pellentesque leo, nec pretium purus. Aenean tempor, mauris id laoreet convallis, odio purus suscipit orci, vel commodo risus velit vitae nunc. Nulla condimentum egestas ipsum, ac hendrerit ante pellentesque ut. Mauris hendrerit est dolor, quis egestas purus posuere quis. Vestibulum vel magna eget mi pharetra venenatis ac et neque. Curabitur gravida rhoncus quam vel pharetra." +
"In non eros eu urna fermentum euismod vitae sed turpis. Aliquam erat volutpat. Pellentesque volutpat tellus iaculis, luctus quam lobortis, commodo orci. Donec sagittis tempus odio, sed auctor velit pretium vitae. Aenean sit amet dolor velit. Vivamus egestas pulvinar purus at tempor. Phasellus lacus augue, interdum ac elit et, molestie commodo mi. Integer malesuada erat purus, imperdiet elementum tortor commodo eget. Phasellus sit amet magna porttitor, venenatis ligula non, varius elit. Aliquam sit amet aliquet metus. Nullam nunc dui, elementum et tincidunt at, accumsan ac felis. Vestibulum tincidunt non neque at aliquet." +

"Donec dictum, diam sed pulvinar lacinia, eros augue dapibus turpis, nec fringilla lacus neque gravida sem. Donec eu mi quis purus euismod tristique. Duis commodo leo nulla, id semper nisi pharetra pretium. In mollis lacus non mollis fermentum. Nullam id commodo sem. Etiam auctor ligula nec sapien condimentum semper. Vivamus laoreet tincidunt semper. In venenatis pulvinar imperdiet. Maecenas sit amet feugiat urna, quis aliquet arcu." +

"Praesent eget turpis egestas, laoreet neque vel, mattis massa. Sed sollicitudin laoreet magna et vulputate. Fusce nec erat egestas, scelerisque dui feugiat, accumsan erat. Maecenas nibh neque, faucibus eu pulvinar ut, laoreet in nisi. Nunc faucibus interdum sapien, eu vulputate justo. In hac habitasse platea dictumst. Sed augue lectus, malesuada eu mauris sit amet, vehicula sollicitudin quam. Duis et est erat. Maecenas est sem, dictum euismod diam sit amet, gravida suscipit augue. Cras at consequat turpis, at mattis turpis.";
	*/
		
		String text= "cacat";
		Log.i("NAME", "CACAT");
	

		
		try {
			reader = new PdfReader("/storage/emulated/0/EBOOKS/Michael Crichton - Jurassic Park & The Lost World.pdf");
			Log.i("NAME", reader.getNumberOfPages() + " ");
			text = PdfTextExtractor.getTextFromPage(reader,10);
			Log.i("NAME",  text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			Log.i("NAME","DA EROARE");
		}

			
		
		
		letter_index = new  LinkedHashMap<Integer,String>();
		
		for(int i = 0; i < text.length(); i++)
			letter_index.put(i,Character.toString( text.charAt(i)));
		
		textContainer.setText(text);
		textContainer.setTextSize(15);
		textContainer.setOnTouchListener(this);

	
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public boolean onTouch(View v, MotionEvent event) {
		
	    Layout layout = ((TextView) v).getLayout();
	    int x = (int)event.getX();
	    int y = (int)event.getY();
	    if (layout!=null){
	        int line = layout.getLineForVertical(y);
	        int offset = layout.getOffsetForHorizontal(line, x);
	        int copy_offset= offset;
	        String word = letter_index.get(offset);
	        
	        Log.i("NAME", ""+offset + " ---" + word + "--- " ); 
	        while(true)
	        {
	        	copy_offset--;
	        	if(letter_index.get(copy_offset ).equals(" ") || letter_index.get(copy_offset ).equals("\n") || letter_index.get(copy_offset ).equals("."))
	        		break;
	        	word = letter_index.get(copy_offset) + word;
	        }
	        
	        copy_offset = offset;
	        
	        while(true)
	        {
	        	copy_offset++;
	        	if(letter_index.get(copy_offset ).equals(" ") || letter_index.get(copy_offset ).equals("\n") || letter_index.get(copy_offset ).equals("."))
	        		break;
	        	word =  word + letter_index.get(copy_offset);
	        }
	        
	        Log.i("NAME", ""+offset + " " + line + " ------" + letter_index.get(offset ) + "-----   -----" + word + "------"); 
	        }
	    return true;
	}
}
