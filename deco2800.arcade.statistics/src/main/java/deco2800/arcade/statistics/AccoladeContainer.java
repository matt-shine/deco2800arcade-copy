package accolades;
 
import java.util.*;
 
public class AccoladeContainer implements Iterable<Accolade> {
 
    public static void main(String[] args) {
        AccoladeContainer container = new AccoladeContainer();
        container.BUILDDUMMYDATA();
        for (Accolade testAccolade : container) {
            System.out.println("HI: " + testAccolade.toString());
            
        }
    }
    //instantiate variables
    private Accolade head;
    private Accolade tail;
    private int gameID;
    private int size;
 
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
    	BUILDDUMMYDATA();
    }
    
    /**Retrieves all accolades totals for game with gameID
     * TODO implmenet server communication and return non-dummydata
     */
    public void populateAccoladesPlayer(int gameID){
    	BUILDDUMMYDATA();
    }
    
    /**Clears the list of accolades ready for repopulation
     */
    public void clearAccolades(){
    	/** TODO implement stub*/
    	head = null;
    	tail = null;
    	current = null;
    }
 
    /**
     * this is probably going to be a private class since most of the Accolade
     * building will be internal and automated.
     */
    public void addAccolade(int ID, int Value,
            String Name, String String, String Unit, int Modifier, String Tag,
            String Image) {
        Accolade accolade = new Accolade(ID, Value, Name, String, Unit, Modifier,
                Tag, Image);
        if (!hasHead()) {
            //current for enumeration
            head = accolade;
        } else {
            tail.setNext(accolade);
        }
        this.tail = accolade;
        size++;
    }
 
    /**
     * Testing stuff *
     */
    public void BUILDDUMMYDATA() {
    	
//Accolade accolade = new Accolade(ID,Value,Name,String,Unit,Modifier,Tag,Image);
        addAccolade(001, 100, "Grenades exploded",
                "The player has detonated $VALUE $UNIT of grenades.", "megatones", 4,
                "explosions", "/images/accolades/grandes.img");
        addAccolade(001, 100, "grenades Exploded",
                "The player has run a total of $VALUE $UNIT", "football fields", 13 ,
                "running", "/images/accolades/feet.img");
        addAccolade(002, 200, "grenades Exploded",
                "$VALUE $UNIT worth of narcodics have been snorted.", "grams", 10,
                "drugs", "/images/accolades/grandes.img");
        addAccolade(003, 300, "grenades Exploded",
                "The player has detonated $VALUE $UNIT", "grnades", 4,
                "explosions", "/images/accolades/grandes.img");
    }
 
    public int size() {
        return this.size;
    }
 
    public boolean isEmpty() {
        return size == 0;
    }
 
    private boolean hasHead() {
        return head != null;
    }
 
    private boolean hasTail() {
        return tail == null;
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
            // TODO Auto-generated method stub
        }
    }
    /**
     * END ITERATOR STUFF YO *
     */
}

