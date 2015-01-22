package Database;

/**
 * the word from the local dictionary
 * @author admin
 *
 */
public class Word {

	/**
	 * the id of the word in database
	 */
	int id;

	/**
	 * the string, how it is written
	 */
	String value;
	
	/**
	 * the definition fom wordnik
	 */
	String definition;
	
	/**
	 * how it is being translated in romanian
	 */
	String translation;
	
	/**
	 * the example from wordnik
	 */
	String example;
	
	/**
	 * which part of sentence is the word
	 */
	String part;
	
	public Word(int id,String value, String definition, String translation, String example, String part){
		
		
		this.id = id;
		this.value = value;
		this.definition = definition;
		this.translation = translation;
		this.example = example;
		this.part = part;
		
		
	}
	
	public Word(String value, String definition, String translation, String example, String part){
		
		this.value = value;
		this.definition = definition;
		this.translation = translation;
		this.example = example;
		this.part = part;
		
		
	}
 	public Word()
	{
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	
}
