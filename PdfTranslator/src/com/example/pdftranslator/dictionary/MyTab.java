package com.example.pdftranslator.dictionary;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import Database.PartSpeech;

public class MyTab {

	PartSpeech partSpeech;
	
	Drawable drawable;
	
	int color;
	
	
	MyTab(PartSpeech partSpeech, Drawable drawable, int color)
	{
		this.partSpeech = partSpeech;
		this.drawable = drawable;
		this.color = color;
	}
	
}
