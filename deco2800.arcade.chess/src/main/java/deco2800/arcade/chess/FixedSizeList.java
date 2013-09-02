package deco2800.arcade.chess;

import java.util.ArrayList;


public class FixedSizeList<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 180705617929972350L;
	private final int maxSize = 8;

	public FixedSizeList() {
		super();
	}
	
	@Override
	public void add(int index, T item) {
		if(size() >= maxSize) {
			remove(index);
		}
		super.add(index, item);
	}
	
}
