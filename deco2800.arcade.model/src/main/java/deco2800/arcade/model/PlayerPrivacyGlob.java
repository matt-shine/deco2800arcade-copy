package deco2800.arcade.model;

public class PlayerPrivacyGlob {
	private boolean namePrivacy;
	private boolean emailPrivacy;
	private boolean programPrivacy;
	private boolean bioPrivacy;
	private boolean friendsPrivacy;
	private boolean gamesPrivacy;
	private boolean achievementsPrivacy;
	
	public static final int NAME_PRIVACY_ID = 1;
	public static final int EMAIL_PRIVACY_IDNAME_ID = 2;
	public static final int PROGRAM_PRIVACY_ID = 3;
	public static final int BIO_PRIVACY_ID = 4;
	public static final int FRIENDS_PRIVACY_ID = 5;
	public static final int GAMES_PRIVACY_ID = 6;
	public static final int ACHIEVMENTS_PRIVACY_ID = 7;
	
	public PlayerPrivacyGlob(boolean[] privacy) {
		this.namePrivacy = privacy[NAME_PRIVACY_ID - 1];
		this.emailPrivacy = privacy[EMAIL_PRIVACY_IDNAME_ID - 1];
		this.programPrivacy = privacy[PROGRAM_PRIVACY_ID - 1];
		this.bioPrivacy = privacy[BIO_PRIVACY_ID - 1];
		this.friendsPrivacy = privacy[FRIENDS_PRIVACY_ID - 1];
		this.gamesPrivacy = privacy[GAMES_PRIVACY_ID - 1];
		this.achievementsPrivacy = privacy[ACHIEVMENTS_PRIVACY_ID - 1];
	}
	
	public boolean isNamePrivacy() {
		return namePrivacy;
	}

	public void setNamePrivacy(boolean namePrivacy) {
		this.namePrivacy = namePrivacy;
	}

	public boolean isEmailPrivacy() {
		return emailPrivacy;
	}

	public void setEmailPrivacy(boolean emailPrivacy) {
		this.emailPrivacy = emailPrivacy;
	}

	public boolean isProgramPrivacy() {
		return programPrivacy;
	}

	public void setProgramPrivacy(boolean programPrivacy) {
		this.programPrivacy = programPrivacy;
	}

	public boolean isBioPrivacy() {
		return bioPrivacy;
	}

	public void setBioPrivacy(boolean bioPrivacy) {
		this.bioPrivacy = bioPrivacy;
	}

	public boolean isFriendsPrivacy() {
		return friendsPrivacy;
	}

	public void setFriendsPrivacy(boolean friendsPrivacy) {
		this.friendsPrivacy = friendsPrivacy;
	}

	public boolean isGamesPrivacy() {
		return gamesPrivacy;
	}

	public void setGamesPrivacy(boolean gamesPrivacy) {
		this.gamesPrivacy = gamesPrivacy;
	}

	public boolean isAchievementsPrivacy() {
		return achievementsPrivacy;
	}

	public void setAchievementsPrivacy(boolean achievementsPrivacy) {
		this.achievementsPrivacy = achievementsPrivacy;
	}
	
}
