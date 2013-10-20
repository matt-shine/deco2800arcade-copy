package deco2800.arcade.model;
import java.util.*;

import javax.sql.rowset.*;
 
public class AccoladeContainer implements Iterable<Accolade> {
 
    //instantiate variables
    private Accolade head;
    private Accolade tail;
    private int gameID;
    private int size;
    private int playerID;
 
    /**
     * create an empty Accolade container
     */
    public AccoladeContainer() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    /**Retrieves all accolades achevied by player with playerID
     * TODO implmenet server communication and return non-dummydata
     */
    public void populateAccoladesPlayer(int playerID){
    	//TODO implement populateAccoladesPlayer
    	this.playerID = playerID;
    	BUILDDUMMYDATA();
    }
    
    /**Retrieves all accolades totals for game with gameID
     * TODO implmenet server communication and return non-dummydata
     */
    public void populateAccoladesGame(int gameID){
    	//TODO implement the populateAccoldesGame
    	this.gameID = gameID;
    	BUILDDUMMYDATA();
    }
    
    public AccoladeContainer setGameID(int gameID){
    	this.gameID =  gameID;
    	return this;
    }
    public int getGameID(){
    	return this.gameID;
    }
    
    public AccoladeContainer setPlayerID(int playerID){
    	this.playerID =  playerID;
    	return this;
    }    
    public int getPlayerID(){
    	return this.playerID;
    }
    
    /**Clears the list of accolades ready for repopulation
     */
    public void clearAccolades(){
    	/** TODO implement stub*/
    	this.size = 0;
    	head = null;
    	tail = null;
    	//current = null;
    }
 
    /**
     * this is probably going to be a private class since most of the Accolade
     * building will be internal and automated. The return allows it to easily have the value and id assigned
     * 
	 * @param ID The accolade ID.
	 * @param Value The progress of the accolade.
	 * @param Name The plain name identifier
	 * @param String The display string that will be used to make toString
	 * @param Unit The unit to be used as part of toString
	 * @param modifier This is to modify the accolade into something interesting,
	 * 			say grenades as tonnes of TNT or something similar
	 * @param tag Combined tag that is used as part of Global_Accolades.Table
	 * @param popup When the accolade is a multiple of this a message overlays on screen
	 * @param popupMessage The message to appear onscreeen
	 * @param image The location of the associated accolade image.
	 */
    public Accolade addAccolade(String name, String message, int popup, String popupMessage, 
			double modifier, String unit, String tag, String imagePath) {
        Accolade accolade = new Accolade(name, message, popup, popupMessage, 
    			 modifier, unit, tag, imagePath);
        if (!hasHead()) {
            //current for enumeration
            head = accolade;
        } else {
            tail.setNext(accolade);
        }
        this.tail = accolade;
        this.size++;
		return accolade;
    }
    /** TODO implement this sodding thing
    public WebRowSet toWebRowSet(){

    	return tnew rowSet;
    }
    **/
    /**
     * Add accolade into accolade container
     */
    public void add(Accolade a) {
        if (!hasHead()) {
            //current for enumeration
            head = a;
        } else {
            tail.setNext(a);
        }
        this.tail = a;
        this.size++;
    }
 
    /**
     * Testing stuff *
     */
    public void BUILDDUMMYDATA() {
    	//TODO REMOVE THIS
//Accolade accolade = new Accolade(ID,Value,Name,String,Unit,Modifier,Tag,Image);
    	//TODO Add in the dumb data stuff
    }
    
    
 
    public int size() {
        return this.size;
    }
 
    public boolean isEmpty() {
        return this.size == 0;
    }
 
    public boolean hasHead() {
        return head != null;
    }
 
    public boolean hasTail() {
        return tail != null;
    }
 
    public Iterator<Accolade> iterator() {
        return new Iterable();
    }
 
    public class Iterable implements Iterator<Accolade> {
 
        private Accolade current;
        private Accolade prev;
 
        public Iterable() {
            current = head;
        }
 
        @Override
        public boolean hasNext() {
            return prev != tail;
        }
 
        @Override
        public Accolade next() {
            // TODO Auto-generated method stub
            if (prev == tail) {
                throw new NoSuchElementException("There are no accolades"
                        + " after " + current.getName());
            }
            prev = current;
            current = prev.Next();
            return prev;
        }
 
        @Override
        public void remove() {
            // TODO Make this return an unsupported action exception 
        	//do not want people to be able to individually remove accolades
        }
    }
    /**
     * END ITERATOR STUFF YO *
     */
}


