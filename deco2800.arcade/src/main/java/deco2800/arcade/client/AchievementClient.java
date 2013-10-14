package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.protocol.NetworkObject;
import deco2800.arcade.protocol.achievement.AchievementsForGameRequest;
import deco2800.arcade.protocol.achievement.AchievementsForGameResponse;
import deco2800.arcade.protocol.achievement.AchievementsForIDsRequest;
import deco2800.arcade.protocol.achievement.AchievementsForIDsResponse;
import deco2800.arcade.protocol.achievement.IncrementProgressRequest;
import deco2800.arcade.protocol.achievement.IncrementProgressResponse;
import deco2800.arcade.protocol.achievement.ProgressForPlayerRequest;
import deco2800.arcade.protocol.achievement.ProgressForPlayerResponse;
import deco2800.arcade.utils.AsyncFuture;
import deco2800.arcade.utils.Handler;

public class AchievementClient {

	/* ---------- PUBLIC API v2.0 - USE THIS! :) ---------- */

	public AchievementClient(NetworkClient networkClient) {
		this.listeners = new HashSet<AchievementListener>();
		if (achievementsCache == null)
			this.achievementsCache = new ConcurrentHashMap<String, Achievement>();

		this.listener = new NetworkListener() {
			public void received(Connection conn, Object object) {
				AchievementClient.this.received(conn, object);
			}
		};

		setNetworkClient(networkClient);
	}

	public void setNetworkClient(NetworkClient client) {
		if (this.networkClient != null) {
			this.networkClient.removeListener(listener);
		}

		this.networkClient = client;
		if (this.networkClient != null) {
			this.networkClient.addListener(listener);
		}
	}

	/**
	 * Returns a future which will be provided with the Achievement
	 * corresponding to the given achievement ID. If no such achievement exists,
	 * the future will be cancelled.
	 * 
	 * @param achievementID
	 *            The ID of the achievement to request.
	 * @return A future to be provided with the request's result.
	 */
	public AsyncFuture<Achievement> getAchievementForID(String achievementID) {
		// we'll just wrap the list based version of this. when its future
		// gets provided we can provide our future within the handler
		ArrayList<String> ids = new ArrayList<String>();
		ids.add(achievementID);

		final AsyncFuture<Achievement> future = new AsyncFuture<Achievement>();
		final AsyncFuture<ArrayList<Achievement>> listFuture = getAchievementsForIDs(ids);

		listFuture.setHandler(new Handler<ArrayList<Achievement>>() {
			public void handle(ArrayList<Achievement> response) {
				future.provide(response.get(0));
			}
		});

		return future;
	}

	public AsyncFuture<ArrayList<Achievement>> getAchievementsForIDs(
			final ArrayList<String> achievementIDs) {
		final AsyncFuture<ArrayList<Achievement>> future = new AsyncFuture<ArrayList<Achievement>>();
		ArrayList<String> nonCached = new ArrayList<String>();

		// see if there's any non-cached IDs that we'll need to request
		for (String id : achievementIDs) {
			if (!achievementsCache.containsKey(id))
				nonCached.add(id);
		}

		if (!nonCached.isEmpty()) {
			// make a request if we need to get any from the server
			AchievementsForIDsRequest req = new AchievementsForIDsRequest();
			req.achievementIDs = nonCached;

			networkClient.request(req).setHandler(new Handler<NetworkObject>() {
				public void handle(NetworkObject obj) {
					AchievementsForIDsResponse resp = (AchievementsForIDsResponse) obj;

					// just fill in the cache with the responses achievements
					for (Achievement ach : resp.achievements) {
						achievementsCache.put(ach.id, ach);
					}

					// and then build our result from the cache (this is a
					// separate pass to make sure
					// the result has the same order as the list of IDs)
					ArrayList<Achievement> result = new ArrayList<Achievement>();
					for (String id : achievementIDs) {
						result.add(achievementsCache.get(id));
					}

					future.provide(result);
				}
			});
		} else {
			// otherwise we're able to just provide the future now
			ArrayList<Achievement> result = new ArrayList<Achievement>();
			for (String id : achievementIDs) {
				result.add(achievementsCache.get(id));
			}

			future.provide(result);
		}
		return future;
	}

	public AsyncFuture<ArrayList<Achievement>> getAchievementsForGame(Game game) {
		final AsyncFuture<ArrayList<Achievement>> future = new AsyncFuture<ArrayList<Achievement>>();

		AchievementsForGameRequest req = new AchievementsForGameRequest();
		req.gameID = game.id;

		// we should probably change this to recieve achievement IDs instead
		// so we can check the cache
		networkClient.request(req).setHandler(new Handler<NetworkObject>() {
			public void handle(NetworkObject obj) {
				AchievementsForGameResponse resp = (AchievementsForGameResponse) obj;
				future.provide(resp.achievements);
			}
		});

		return future;
	}

	public AsyncFuture<AchievementProgress> getProgressForPlayer(Player player) {
		final AsyncFuture<AchievementProgress> future = new AsyncFuture<AchievementProgress>();
		ProgressForPlayerRequest req = new ProgressForPlayerRequest();
		req.playerID = player.getID();

		networkClient.request(req).setHandler(new Handler<NetworkObject>() {
			public void handle(NetworkObject obj) {
				ProgressForPlayerResponse resp = (ProgressForPlayerResponse) obj;
				future.provide(resp.achievementProgress);
			}
		});

		return future;
	}

	/**
	 * Increments the player's progress for the achievement with ID
	 * `achievementID`.
	 * 
	 * @param achievementID
	 *            The ID of the achievement.
	 * @param player
	 *            The player whose progress should be incremented.
	 */
	public void incrementProgress(String achievementID, Player player) {
		IncrementProgressRequest request = new IncrementProgressRequest();
		request.achievementID = achievementID;
		request.playerID = player.getID();

		networkClient.sendNetworkObject(request);
	}

	/**
	 * Adds a listener to be notified of achievement events. The listener is
	 * only notified of events after they've been confirmed by the server.
	 * 
	 * (This is used to power GameClient's achievement overlay)
	 * 
	 * @param listener
	 *            The listener to add.
	 */
	public void addListener(AchievementListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes an achievement listener. If the listener is null or wasn't added
	 * in the first place, nothing happens.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public void removeListener(AchievementListener listener) {
		listeners.remove(listener);
	}

	/* ---------- DEPRECATED API - DON'T USE THESE METHODS ---------- */

	/**
	 * Legacy method that's now a wrapper around getAchievementForID - you
	 * should move to using that method in order to control whether you want the
	 * request to block or not.
	 */
	@Deprecated
	public Achievement achievementForID(String achievementID) {
		return getAchievementForID(achievementID).get();
	}

	/**
	 * Legacy method that's now a wrapper around getAchievementsForIDs - you
	 * should move to using that method in order to control whether you want the
	 * request to block or not.
	 */
	@Deprecated
	public ArrayList<Achievement> achievementsForIDs(
			ArrayList<String> achievementIDs) {
		return getAchievementsForIDs(achievementIDs).get();
	}

	/**
	 * Legacy method that's now a wrapper around getAchievementsForGame - you
	 * should move to using that method in order to control whether you want the
	 * request to block or not.
	 */
	@Deprecated
	public ArrayList<Achievement> achievementsForGame(Game game) {
		return getAchievementsForGame(game).get();
	}

	/**
	 * Legacy method that's now a wrapper around getProgressForPlayer - you
	 * should move to using that method in order to control whether you want the
	 * request to block or not.
	 */
	@Deprecated
	public AchievementProgress progressForPlayer(Player player) {
		return getProgressForPlayer(player).get();
	}

	/* ---------- PRIVATE INTERNALS ---------- */

	private NetworkClient networkClient;
	private HashSet<AchievementListener> listeners;
	private static ConcurrentHashMap<String, Achievement> achievementsCache = null;
	private NetworkListener listener;

	/**
	 * Implementation for servicing IncrementProgressResponses and dispatching
	 * the appropriate calls to listeners.
	 */
	private void received(Connection connection, Object object) {
		if (object instanceof IncrementProgressResponse) {
			final IncrementProgressResponse resp = (IncrementProgressResponse) object;

			getAchievementForID(resp.achievementID).setHandler(
					new Handler<Achievement>() {
						public void handle(final Achievement ach) {
							if (listeners.isEmpty())
								return;

							// post these on the GDX thread so we can actually
							// display overlays
							Gdx.app.postRunnable(new Runnable() {
								public void run() {
									if (resp.newProgress == ach.awardThreshold) {
										for (AchievementListener l : listeners) {
											l.achievementAwarded(ach);
										}
									} else {
										for (AchievementListener l : listeners) {
											l.progressIncremented(ach,
													resp.newProgress);
										}
									}
								}
							});
						}
					});
		}
	}
}
