
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class Translator {

	public static void main(String[] args) {
	    Translate.setClientId("ironfist02");
	    Translate.setClientSecret("bRWOu3j3wtCS01uNZglWYeKRanHShGntGQ5itEe3q7A=");

	    String translatedText ="cacat";
		try {
			translatedText = Translate.execute("Bonjour", Language.FRENCH, Language.ENGLISH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    System.out.println(translatedText);

	}

}
