package deco2800.arcade.forum.ui;

import deco2800.arcade.model.forum.*;
import deco2800.arcade.protocol.forum.ParentThreadProtocol;

import java.util.*;

public class ChildListModel {
	private int size;
	private List<ChildThread> list;
	
	//assume cat var is between 1-4
	public ChildListModel() {
		this.list = new ArrayList<ChildThread>();
		this.size = 0;
	}
	
	/**
	 * Load Parent threads from given Array into this.List
	 * @param threads
	 * @return int, amount of Parent Threads loaded.
	 */
	public void thread_load(ChildThread[] threads) {
		int i;
		this.list = new ArrayList<ChildThread>();
		for (i = 0; i < threads.length; i++) {
			this.list.add(threads[i]);
		}
		this.size = i;
	}
	
	public ChildThread get_thread(int index) {
		return this.list.get(index);
	}
	
	public int get_size() {
		return this.size;
	}
	
	public void clear_threads() {
		this.list = new ArrayList<ChildThread>();
	}
	
}
