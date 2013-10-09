package deco2800.arcade.userui;

import javax.swing.ImageIcon;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.model.Friends;

public class Model {
	
	public String status;
	public ImageIcon statusIcon;
	public Friends friends;
	public AchievementClient achievements;
	public boolean statusviewon = false;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ImageIcon getStatusIcon() {
		return statusIcon;
	}

	public void setStatusIcon(ImageIcon statusIcon) {
		this.statusIcon = statusIcon;
	}

	public AchievementClient getAchievements() {
		return achievements;
	}

	public void setAchievements(AchievementClient achievements) {
		this.achievements = achievements;
	}
	
}
