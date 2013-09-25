package deco2800.arcade.guesstheword.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyValues  {
	
	public static final int A = 29;
	public static final int B = 30;
	public static final int C = 31;
	public static final int D = 32;
	public static final int E = 33;
	public static final int F = 34;
	public static final int G = 35;
	public static final int H = 36;
	public static final int I = 37;
	public static final int J = 38;
	public static final int K = 39;
	public static final int L = 40;
	public static final int M = 41;
	public static final int N = 42;
	public static final int O = 43;
	public static final int P = 44;
	public static final int Q = 45;
	public static final int R = 46;
	public static final int S = 47;
	public static final int T = 48;
	public static final int U = 49;
	public static final int V = 50;
	public static final int W = 51;
	public static final int X = 52;
	public static final int Y = 53;
	public static final int Z = 54;
	
//	private static ArrayList<Integer> keys;
	private static HashMap<Integer, String> keysMap;
	
	public KeyValues(){
//		keys = new ArrayList<Integer>();
		keysMap =  new HashMap<Integer, String>();
		keysMap.put(A, "A");
		keysMap.put(B, "B");
		keysMap.put(C, "C");
		keysMap.put(D, "D");
		keysMap.put(E, "E");
		keysMap.put(F, "F");
		keysMap.put(G, "G");
		keysMap.put(H, "H");
		keysMap.put(I, "I");
		keysMap.put(J, "J");
		keysMap.put(K, "K");
		keysMap.put(L, "L");
		keysMap.put(M, "M");
		keysMap.put(N, "N");
		keysMap.put(O, "O");
		keysMap.put(P, "P");
		keysMap.put(Q, "Q");
		keysMap.put(R, "R");
		keysMap.put(S, "S");
		keysMap.put(T, "T");
		keysMap.put(U, "U");
		keysMap.put(V, "V");
		keysMap.put(W, "W");
		keysMap.put(X, "X");
		keysMap.put(Y, "Y");
		keysMap.put(Z, "Z");
	}
	
	public HashMap<Integer, String> getKeys(){
		return keysMap;
	}
	
	
}
