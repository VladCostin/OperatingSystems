import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;




public class PDFReader {

	public static void main(String[] args) {
        try {     
        PdfReader reader = new PdfReader("mock.pdf");
        int n = reader.getNumberOfPages(); 
        String str=PdfTextExtractor.getTextFromPage(reader, 2); //Extracting the content from a particular page.
        System.out.println(str);
        reader.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

	}

}
