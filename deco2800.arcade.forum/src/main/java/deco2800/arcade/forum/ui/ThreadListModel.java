package deco2800.arcade.forum.ui;

import deco2800.arcade.model.forum.*;
import deco2800.arcade.protocol.forum.ParentThreadProtocol;

import java.util.*;

public class ThreadListModel {
	private String category;
	private int size;
	private List<ParentThread> list;
	private ChildListModel childs;
	
	//assume cat var is between 1-4
	public ThreadListModel(int cat) {
		set_category(cat);
		this.childs = new ChildListModel();
		this.list = new ArrayList<ParentThread>();
	}
	
	public void child_thread_load(ChildThread[] c) {
		this.childs.thread_load(c);
	}
	
	public int get_child_size() {
		return this.childs.get_size();
	}
	
	public ChildThread get_child_thread(int index) {
		return this.childs.get_thread(index);
	}
	
	public void clear_child_threads() {
		this.childs.clear_threads();
	}
	
	
	/**
	 * Load Parent threads from given Array into this.List
	 * @param threads
	 * @return int, amount of Parent Threads loaded.
	 */
	public void thread_load(ParentThread[] threads) {
		int i;
		this.list = new ArrayList<ParentThread>();
		for (i = 0; i < threads.length; i++) {
			this.list.add(threads[i]);
			System.out.println(threads[i].getTopic());
			System.out.println(threads[i].getId());
		}
		this.size = i;
	}
	
	public ParentThread get_thread(int index) {
		return this.list.get(index);
	}
	
	public int get_size() {
		return this.size;
	}
	
	public void clear_threads() {
		this.list = new ArrayList<ParentThread>();
	}
	
	public String get_category() {
		return this.category;
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
