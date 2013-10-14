package deco2800.arcade.snakeLadderModel;

public class RuleMapping {
	private String icon;
	private String implementationClass;
	
	public RuleMapping(String icon, String implementationClass)
	{
		this.icon = icon;
		this.implementationClass = implementationClass;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getImplementationClass() {
		return implementationClass;
	}
	public void setImplementationClass(String implementationClass) {
		this.implementationClass = implementationClass;
	}
}
