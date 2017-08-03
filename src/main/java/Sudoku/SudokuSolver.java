package Sudoku;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.*;


public class SudokuSolver implements ActionListener{
	
	static JFrame frame= new JFrame("SudokuSolver");
	static JPanel panel = new JPanel();
	static JPanel panel1 = new JPanel();
	static JButton solveButton= new JButton("Solve");
	static String[] numbers={" ", "1","2","3","4","5","6","7","8","9"};
	@SuppressWarnings("unchecked")
	static JComboBox<String>[][] jboxes= new JComboBox[9][9]; 
	static Box[][] boxes = new Box[9][9];
	static SudokuSolver world = new SudokuSolver();
	public static Comparator<Box> sizeComparator = new Comparator<Box>(){
		
		//@Override
		public int compare(Box c1, Box c2) {
            return c1.size()-c2.size();
        }
	};
	static PriorityQueue<Box> pq = new PriorityQueue<Box>(81,sizeComparator);
	
	
	public static void main (String args[]){
		panel.setLayout(new GridLayout(9,9,0,0));
		panel1.setLayout(new FlowLayout());
		frame.setSize(600, 600);
		for(int i=0; i<9;i++){
			for(int j=0; j<9; j++){
				jboxes[i][j]=new JComboBox<String>(numbers);
				boxes[i][j]=new Box(i,j);
				panel.add(jboxes[i][j]);
				pq.add(boxes[i][j]);
			}
		}
		panel1.add(solveButton);
		solveButton.addActionListener(world);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().getClass()==solveButton.getClass()){
			initializeBoard();
			solveBoard();
			displayBoard();
			solveButton.removeActionListener(world);
		}		
	}
	public void initializeBoard(){
		for(int i=0; i<9;i++){
			for(int j=0; j<9; j++){
				String number=(String) jboxes[i][j].getSelectedItem();
				if(!number.equals(" ")){
					//System.out.println(i+" "+j);
					boxes[i][j].setValue(new Integer(number));
					boxes[i][j].removeAllItems();
					boxes[i][j].add(new Integer(number));
					removeOthers(i,j,new Integer(number),new LinkedList<Box>());
					pq.remove(boxes[i][j]);
				}
			}
		}
	}
	public boolean solveBoard(){
		if(pq.size()==0){
			return true;
		}
		Box b=pq.remove();
		boolean retval=false;
		if(b.size()==0){
			return false;
		}
		else{
			LinkedList<Integer> numbers2= new LinkedList<Integer>();
			for(int q:b.getPossible_digits()){
				numbers2.add(q);
			}
			LinkedList<Integer> numbers= new LinkedList<Integer>();
			for(int q:b.getPossible_digits()){
				numbers.add(q);
			}
			for(int index=0;index<b.size();){
				int digit= b.getPossible_digits().get(index);
				b.setValue(digit);
				b.removeAllItems();
				b.add(digit);
				LinkedList<Box> boxesModified= new LinkedList<Box>();
				removeOthers(b.get_i(),b.get_j(),digit,boxesModified);
				retval=solveBoard();
				if(retval){
					return retval;
				}
				b.setValue(0);
				b.setDone(false);
				numbers.remove(new Integer(digit));
				b.setPossible_digits(numbers);
				for(Box bm:boxesModified){
					bm.add(digit);
				}
			}
			b.setPossible_digits(numbers2);
			pq.add(b);
		}
		return false;
	}
	public void displayBoard(){
		for(int i=0; i<9;i++){
			for(int j=0; j<9; j++){
				jboxes[i][j].removeAllItems();
				jboxes[i][j].addItem(""+boxes[i][j].getValue());
			}
		}
	}
	public void removeOthers(int i, int j, int number, LinkedList<Box> boxesModified){
		for(int k=0; k<9; k++){
			if(k==j){
				continue;
			}
			if(boxes[i][k].getPossible_digits().contains(number)){
				boxesModified.add(boxes[i][k]);
				pq.remove(boxes[i][k]);
				boxes[i][k].remove(number);
				if(!boxes[i][k].isDone()){
					pq.add(boxes[i][k]);
				}
			}
		}
		for(int k=0; k<9; k++){
			if(k==i){
				continue;
			}
			if(boxes[k][j].getPossible_digits().contains(number)){
				//System.out.println("box "+boxes[k][j].get_i()+" "+boxes[k][j].get_j()+" has "+number);
				pq.remove(boxes[k][j]);
				boxesModified.add(boxes[k][j]);
				boxes[k][j].remove(number);
				if(!boxes[k][j].isDone()){
					pq.add(boxes[k][j]);
				}
			}
		}
		for(int k=i-(i%3); k<(i-(i%3))+3; k++){
			for(int l=j-(j%3); l<(j-(j%3))+3; l++){
				if(k==i && l==j){
					continue;
				}
				if(boxes[k][l].getPossible_digits().contains(number)){
					pq.remove(boxes[k][l]);
					boxesModified.add(boxes[k][l]);
					boxes[k][l].remove(number);
					if(!boxes[k][l].isDone()){
						pq.add(boxes[k][l]);
					}
				}	
			}
		}
	}
}
