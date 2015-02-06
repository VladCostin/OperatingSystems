package Database;

/**
 * mentions where a word appears in a book
 * @author admin
 *
 */
public class Appeareance {

	/**
	 * the id in the database
	 */
	int id;
	
	/**
	 * the book where the word appears
	 */
	int idBook;
	
	/**
	 * the id of the word
	 */
	int idWord;
	
	/**
	 * the page where the word appears in the book
	 */
	int page;
	
	public Appeareance(int id, int idBook, int iWord, int page) {
		
		this.id = id;
		this.idBook = idBook;
		this.idWord = iWord;
		this.page = page;
	}
	
	public Appeareance( int idBook, int iWord, int page) {
		
		this.idBook = idBook;
		this.idWord = iWord;
		this.page = page;
	}
	
	public Appeareance()
	{
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public int getIdWord() {
		return idWord;
	}

	public void setIdWord(int idWord) {
		this.idWord = idWord;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	
}
