package Database;

/**
 * the book where the word has been found
 * @author admin
 *
 */
public class Book {

	/**
	 * Id of the Book in database
	 */
	int id;
	
	/**
	 * the title of the book 
	 */
	String title;
	
	
	/**
	 * how difficulty was the book for the reader
	 * taking into consideration the difficulty of the words
	 */
	int difficulty;
	
	/**
	 *  the number of pages the book has
	 */
	private int nr_pages;
	
	
	/**
	 * the path of the book
	 */
	private String path;
	
	Book()
	{
		
	}
	

	public Book(int id, String title, int difficulty, int nr_pages, String path)
	{
		this.id = id;
		this.title= title;
		this.difficulty = difficulty;
		this.nr_pages	= nr_pages;
		this.path = path;
	}
	public Book(String title, int difficulty,int nr_pages, String path)
	{
		this.title = title;
		this.difficulty = difficulty;
		this.nr_pages = nr_pages;
		this.path = path;
	}
	
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}


	public int getNr_pages() {
		return nr_pages;
	}


	public void setNr_pages(int nr_pages) {
		this.nr_pages = nr_pages;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	
}
