package deco2800.arcade.guesstheword.gameplay;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * The word shuffler class. The word will be shuffled and the length of 
 * each word is 10. 
 * 
 * @author Li Zixuan Tony*/
public class WordShuffler {
	public WordShuffler(){}
	
	public String getHint(char[] s){
		Random r = new Random();
		return "" + s[r.nextInt(s.length)];
	}
	
	public String[] breakWord(String s){
		Random r = new Random();
		
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		
		int charsNeeded = 10 - s.length();
	    
	    //loop for fill up 10 characters
	    for (int i=0; i<charsNeeded;i++)
	    {
	       char randomadd = (char)(r.nextInt(26) + 'A');
	       sb.append(randomadd);
	    }
	    //split string into string array without empty element
	    List<String> tempArray = Arrays.asList(sb.toString().split("(?!^)"));
	    String[] solutionArray = tempArray.toArray(new String[0]);
        //call shuffleArray method
	    shuffleWord(solutionArray);
		 return solutionArray;
	}
	
	  // Implementing Fisher-Yates shuffle
	  private void shuffleWord(String[] ar)
	  {
	    Random rnd = new Random();
	    // i is start from last element([ar.length-1]) to first element([0])
	    for (int i = ar.length - 1; i >= 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      String a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
}
