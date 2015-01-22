package com.example.pdftranslator;

import java.util.HashMap;

/**
 * contains data used in whole activity
 * @author Vlad Herescu
 *
 */
public class Core {

	/**
	 * contains data about the books started to be read
	 */
	private static HashMap<String,Integer> hashMapBetweenMainAndReader;
	
	/**
	 * initializing the data
	 */
	Core()
	{
		hashMapBetweenMainAndReader = new HashMap<String,Integer>();
	}

	public static HashMap<String,Integer> getHashMapBetweenMainAndReader() {
		return hashMapBetweenMainAndReader;
	}

	public static void setHashMapBetweenMainAndReader(
			HashMap<String,Integer> hashMapBetweenMainAndReader) {
		Core.hashMapBetweenMainAndReader = hashMapBetweenMainAndReader;
	}
	
}
