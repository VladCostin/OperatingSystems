package InternetConnection;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.memetix.mst.speak.Speak;


public class BingTranslator {
	
	public static void setIds()
	{
		  Translate.setClientId(Constants.clientId);
		  Translate.setClientSecret(Constants.ClientSecret);
	}

	/**
	 * @param expression : the expression to be translated
	 * @param lan1 : the language from which is being translated
	 * @param lan2 : the language to which is being translated
	 * @return
	 */
	public static String translateFromBing(String expression, Language lan1, Language lan2)
	{


		  String translatedText ="eroare in traducerea cuvantului " + expression;
		  try {
			translatedText = Translate.execute(expression, lan1, lan2);
			
		   }
		  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }

		  return translatedText;	
	}
	
	public void speakText()
	{

	}
}
