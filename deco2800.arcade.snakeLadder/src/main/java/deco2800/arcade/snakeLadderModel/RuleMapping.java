package deco2800.arcade.snakeLadderModel;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * @author li.tang
 */
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
	
	/**
	 * A util method to parse the xml configuration file 
	 * @param file xml configuration file
	 * @return a rule string to RuleMapping Object mapping
	 */
	public static HashMap<String,RuleMapping> iniRuleMapping(FileHandle file)
	{
		HashMap<String,RuleMapping> returnMap = new HashMap<String,RuleMapping>();
		XmlReader reader = new XmlReader();
		try {
			Element root = reader.parse(file);
			Array<Element> entries = root.getChildrenByName("entry");
			for (Element entry : entries)
			{
				String rule = entry.getChildByName("rule").getText();
				String icon = entry.getChildByName("icon").getText();
				String implementationClass = entry.getChildByName("implementationClass").getText();
				returnMap.put(rule, new RuleMapping(icon,implementationClass));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

}
