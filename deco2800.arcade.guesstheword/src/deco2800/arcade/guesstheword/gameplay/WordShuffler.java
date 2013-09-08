package deco2800.arcade.guesstheword.gameplay;

import java.util.Random;

public class WordShuffler {
	public WordShuffler(){}
	
	public String[] breakWord(String s){
		Random r = new Random();
		
		//Characters needed, bounded at 10. 
		int charsNeeded = 10 - s.length();
		
		 for (int i = 0; i < charsNeeded; i++ ){
			 char addWord = (char)(r.nextInt(26) + 'A');
			 s += addWord;
		  }
		 
		//split string into string array
		 String[] solutionArray = s.split("");
		 
		 //call shuffleArray method
		 shuffleWord(solutionArray);
		 
		 return solutionArray;
	}
	
	  // Implementing FisherÐYates shuffle
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
