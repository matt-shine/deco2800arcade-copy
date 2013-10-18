package deco2800.arcade.forum.ui;

import deco2800.arcade.model.forum.*;
import java.util.*;

public class ThreadPageModel {
	private String category;
	private int size;
	private List<ParentThread> list;
	private final int MAX_THREAD = 10;
	
	//assume page var is between 1-4
	public ThreadPageModel(int page) {
		set_category(page);
		this.list = new ArrayList<ParentThread>();
		this.size = 0;
	}
	
	/**
	 * Load Parent threads from given Array into this.List
	 * @param threads
	 * @return int, amount of Parent Threads loaded.
	 */
	public void thread_load(ParentThread[] threads) {
		int i;
		for (i = 0; i < threads.length; i++) {
			this.list.add(threads[i]);
		}
		this.size = i;
	}
	
	public ParentThread get_thread(int index) {
		return this.list.get(index);
	}
	
	public int get_size() {
		return this.size;
	}
	
	private void set_category(int page) {
		//"General_Discussion", "Report_Bug", "Tutorial", "Others"
		switch(page) {
		case 1:
			this.category = "General_Discussion";
			break;
		case 2:
			this.category = "Report_Bug";
			break;
		case 3:
			this.category = "Tutorial";
			break;
		case 4:
			this.category = "Others";
			break;
		}
	}
}
