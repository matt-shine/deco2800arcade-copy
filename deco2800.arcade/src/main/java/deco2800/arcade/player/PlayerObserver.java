package deco2800.arcade.player;

import java.util.Observable;
import java.util.Observer;

import deco2800.arcade.client.network.*;
import deco2800.arcade.model.*;
import deco2800.arcade.protocol.player.*;

/**
 * 
 * @author Leggy, iamwinrar
 * 
 */
public class PlayerObserver implements Observer {

	private BioUpdateRequest bioUpdateRequest;
	private BlockedUpdateRequest blockedUpdateRequest;
	private EmailUpdateRequest emailUpdateRequest;
	private FriendInvitesUpdateRequest friendInvitesUpdateRequest;
	private FriendsUpdateRequest friendsUpdateRequest;
	private GamesUpdateRequest gamesUpdateRequest;
	private NameUpdateRequest nameUpdateRequest;
	private ProgramUpdateRequest programUpdateRequest;
	private UsernameUpdateRequest usernameUpdateRequest;
    private LibraryStyleUpdateRequest libraryStyleUpdateRequest;
	private AgeUpdateRequest ageUpdateRequest;

	private static NetworkClient networkClient;

	/**
	 * Initialises the Observer.
	 * 
	 * @param nc
	 *            The NetworkClient used to communicate with the server.
	 */
	public static void initialise(NetworkClient nc) {
		networkClient = nc;

	}

	@Override
	public void update(Observable observable, Object object) {

		/*
		 * Checking if observable is an instance of Player
		 */
		if (observable instanceof Player) {
			Player p = (Player) observable;

			/*
			 * Here we are checking if the object is an instance of any of the
			 * classes that we expect to change in Player. We then encapsulate
			 * the changes in a request, and send it to the server.
			 */
			if (object instanceof Friends) {
				Friends f = (Friends) object;
				/*
				 * Encapsulating Player change data for transmission for server.
				 */
				friendsUpdateRequest.setPlayerID(p.getID());
				friendsUpdateRequest.setFriendID(f.getUpdatedID());
				friendsUpdateRequest.setAdd(f.getAdded());
				networkClient.sendNetworkObject(friendsUpdateRequest);

			} else if (object instanceof FriendInvites) {
				FriendInvites fi = (FriendInvites) object;
				/*
				 * Encapsulating Player change data for transmission for server.
				 */
				friendInvitesUpdateRequest.setPlayerID(p.getID());
				friendInvitesUpdateRequest.setFriendID(fi.getUpdatedID());
				friendInvitesUpdateRequest.setAdd(fi.getAdded());
				networkClient.sendNetworkObject(friendInvitesUpdateRequest);

			} else if (object instanceof Games) {
				Games g = (Games) object;
				/*
				 * Encapsulating Player change data for transmission for server.
				 */
				gamesUpdateRequest.setPlayerID(p.getID());
				gamesUpdateRequest.setGameID(g.getUpdatedID());
				gamesUpdateRequest.setAdd(g.getAdded());
				networkClient.sendNetworkObject(gamesUpdateRequest);

			} else if (object instanceof Blocked) {
				Blocked b = (Blocked) object;
				/*
				 * Encapsulating Player change data for transmission for server.
				 */
				blockedUpdateRequest.setPlayerID(p.getID());
				blockedUpdateRequest.setPlayerID2(b.getUpdatedID());
				blockedUpdateRequest.setAdd(b.getAdded());
				networkClient.sendNetworkObject(blockedUpdateRequest);

			} else if (object instanceof Field) {
				Field field = (Field) object;
				String value = field.getValue();
				switch (field.getID()) {

				case Player.USERNAME_ID:
					/*
					 * Encapsulating Player change data for transmission for
					 * server.
					 */
					usernameUpdateRequest.setPlayerID(p.getID());
					usernameUpdateRequest.setUsername(value);
					networkClient.sendNetworkObject(usernameUpdateRequest);

				case Player.NAME_ID:
					/*
					 * Encapsulating Player change data for transmission for
					 * server.
					 */
					nameUpdateRequest.setPlayerID(p.getID());
					nameUpdateRequest.setName(value);
					networkClient.sendNetworkObject(nameUpdateRequest);

				case Player.EMAIL_ID:
					/*
					 * Encapsulating Player change data for transmission for
					 * server.
					 */
					emailUpdateRequest.setPlayerID(p.getID());
					emailUpdateRequest.setEmail(value);
					networkClient.sendNetworkObject(emailUpdateRequest);

				case Player.PROGRAM_ID:
					/*
					 * Encapsulating Player change data for transmission for
					 * server.
					 */
					programUpdateRequest.setPlayerID(p.getID());
					programUpdateRequest.setProgram(value);
					networkClient.sendNetworkObject(programUpdateRequest);

				case Player.BIO_ID:
					/*
					 * Encapsulating Player change data for transmission for
					 * server.
					 */
					bioUpdateRequest.setPlayerID(p.getID());
					bioUpdateRequest.setBio(value);
					networkClient.sendNetworkObject(bioUpdateRequest);
				
				case Player.AGE_ID:
					/*
					 * Encapsulating Player change data for transmission for
					 * server.
					 */
					ageUpdateRequest.setPlayerID(p.getID());
					ageUpdateRequest.setAge(value);
					networkClient.sendNetworkObject(ageUpdateRequest);
				}

			} else if (object instanceof LibraryStyle) {
                LibraryStyle libraryStyle = (LibraryStyle) object;

                libraryStyleUpdateRequest = new LibraryStyleUpdateRequest();
                libraryStyleUpdateRequest.setColour(libraryStyle.getColourScheme());
                libraryStyleUpdateRequest.setStyle(libraryStyle.getLayout());
                networkClient.sendNetworkObject(libraryStyleUpdateRequest);

            }
		}

	}

}
