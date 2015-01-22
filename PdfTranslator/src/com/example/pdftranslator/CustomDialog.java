package com.example.pdftranslator;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


/**
 * the dialog used to select a book from which the dictionary will be shown
 * @author Vlad Herescu
 *
 */
public class CustomDialog extends AlertDialog {

	Activity context;
	
	/**
	 * the color used for customizing the Dialog
	 */
	int color;
	
	
    /**
     * Constructor for <code>CustomDialog</code>
     * 
     * @param context The {@link Context} to use
     * @param color : the color used for dialog
     */
    public CustomDialog(Activity context, int color) {
        super(context);
        
        this.context = context;
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Resources res = getContext().getResources();
        final int yellow = res.getColor(  color );

        // Title
        final int titleId = res.getIdentifier("alertTitle", "id", "android");
        final View title = findViewById(titleId);
        if (title != null) {
            ((TextView) title).setTextColor(yellow);
            ((TextView) title).setGravity(Gravity.CENTER);
        }

        // Title divider
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        final View titleDivider = findViewById(titleDividerId);
        if (titleDivider != null) {
            titleDivider.setBackgroundColor(yellow);
        }
        
        final int button1Id	= res.getIdentifier("button1", "id", "android");
        final View button = findViewById(button1Id);
        if(button != null)
        {
        	((Button) button).setTextColor(yellow);
        	((Button) button).setTextSize(20);
        }
        
        final int button2Id	= res.getIdentifier("button3", "id", "android");
        final View button2 = findViewById(button2Id);
        if(button2 != null)
        {
        	((Button) button2).setTextColor(yellow);
        	((Button) button2).setTextSize(20);
        }
        
    	WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(this.getWindow().getAttributes());
	  //  lp.x = 800;
	  //  lp.width = WindowManager.LayoutParams.MATCH_PARENT;
	    lp.width = 800;
        
        this.getWindow().setAttributes(lp);
        
    }


}
