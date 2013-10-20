package deco2800.arcade.mixmaze;

public final class Achievements {
	public enum AchievementType {
		Playa,
		TenMatches,
		TwentyMatches,
		FirstWin,
		RisingStar,
		OnFire,
		BuildBig,
		Strategist,
		BreakingGood,
		TNT,
		UsePick,
		UseTNT
	}

	private final static IllegalArgumentException NOT_A_ACHIEVMENT = new IllegalArgumentException(
			"Not an achievment");
	private final static IllegalStateException NOT_INITIALIZED = new IllegalStateException("Achievments not initialized."); 
	
	private static Achievements INSTANCE;

	private MixMaze gameClient;

	private Achievements(MixMaze game) {
		gameClient = game;
	}

	public static void initializeAchievements(MixMaze game) {
		INSTANCE = new Achievements(game);
	}

	private static Achievements getInstance() {
		if(INSTANCE == null) {
			throw NOT_INITIALIZED;
		}
		return INSTANCE;
	}

	private String getAchievementName(AchievementType achvmnt) {
		switch (achvmnt) {
		case Playa:
			return "mixmaze.playa";
		case TenMatches:
			return "mixmaze.tenmatches";
		case TwentyMatches:
			return "mixmaze.twentymatches";
		case FirstWin:
			return "mixmaze.firstwin";
		case RisingStar:
			return "mixmaze.risingstar";
		case OnFire:
			return "mixmaze.onfire";
		case BuildBig:
			return "mixmaze.buildbig";
		case Strategist:
			return "mixmaze.strategist";
		case BreakingGood:
			return "mixmaze.breakinggood";
		case TNT:
			return "mixmaze.tnt";
		case UsePick:
			return "mixmaze.usepick";
		case UseTNT:
			return "mixmaze.usetnt";
		}
		throw NOT_A_ACHIEVMENT;
	}

	private void instanceIncrementAchievement(AchievementType achievment) {
		String achievementID = getAchievementName(achievment);
		try
		{
			gameClient.incrementAchievement(achievementID);
		}
		catch(Exception ex) {
			System.out.println(String.format("Incrementing achievment %s failed.", achievementID));
		}
	}
	
	public static void incrementAchievement(AchievementType achievment) {
		getInstance().instanceIncrementAchievement(achievment);
	}
}
