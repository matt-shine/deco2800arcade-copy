package deco2800.arcade.mixmaze;

public final class Achievements {
	public enum Achievement {
		Playa
	}

	private final static IllegalArgumentException NOT_A_ACHIEVMENT = new IllegalArgumentException(
			"Not an achievment");

	private static Achievements INSTANCE;

	private MixMaze gameClient;

	private Achievements(MixMaze game) {
		gameClient = game;
	}

	public static Achievements initializeAchievements(MixMaze game) {
		INSTANCE = new Achievements(game);
		return INSTANCE;
	}

	public static Achievements getInstance() {
		return INSTANCE;
	}

	private String getAchievementName(Achievement achvmnt) {
		switch (achvmnt) {
		case Playa:
			return "mixmaze.playa";
		}
		throw NOT_A_ACHIEVMENT;
	}

	public void incrementAchievement(Achievement achvmnt) {
		String achievementID = getAchievementName(achvmnt);
		gameClient.incrementAchievement(achievementID);
	}
}
