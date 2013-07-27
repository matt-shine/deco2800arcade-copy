package deco2800.arcade.model;

public class Achievement {

	//TODO shared between server & client?
	
	//TODO icon?
	
	private String description;
	
	public Achievement(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
