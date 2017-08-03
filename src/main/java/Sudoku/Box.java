package Sudoku;

import java.util.*;

public class Box {
	private boolean done;
	private int value;
	private LinkedList<Integer> possible_digits;
	private int index_i;
	private int index_j;
	
	public Box(int i, int j){
		done=false;
		value=0;
		possible_digits=new LinkedList<Integer>();
		for(int x=1;x<10;x++){
			possible_digits.add(x);
			index_i=i;
			index_j=j;
		}
		
		
	}
	public void add(int digit){
		possible_digits.add(digit);
	}
	public void removeAllItems(){
		possible_digits.removeAll(possible_digits);
	}
	public void remove(int digit){
		possible_digits.remove(new Integer(digit));
	}
	public int size(){
		return possible_digits.size();
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
		done=true;
		//this.removeAllItems();
		//this.add(value);
	}
	public LinkedList<Integer> getPossible_digits() {
		return possible_digits;
	}
	public void setPossible_digits(LinkedList<Integer> possible_digits) {
		this.possible_digits = possible_digits;
	}
	public int get_i(){
		return index_i;
	}
	public int get_j(){
		return index_j;
	}
}
