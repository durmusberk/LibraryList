package listeleme505;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class main {
	

	public static void main(String[] args) {
		
		JPanel topPanel = new JPanel ();
        topPanel.setBorder ( new TitledBorder ( new EtchedBorder (), "programmed by Durmus Berk" ) );
        topPanel.setLayout(new BorderLayout());
        
        
        JTextField field = new JTextField(70);
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 8));
        resetButton.setMargin(new Insets(0, 0, 0, 0));
        resetButton.setPreferredSize(new Dimension(30,30) );
        
        
        
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        

        JTextArea display = new JTextArea ( 10, 30 );
        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        
       
        
        
        
        topPanel.add(field,BorderLayout.CENTER);
        topPanel.add(resetButton,BorderLayout.EAST);
        middlePanel.add ( scroll);
        
      
       
        
        
        
        
        field.addActionListener(new ActionListener(){
        	   public void actionPerformed(ActionEvent ae){
        	      String text = field.getText();
        	      
        	      field.setText("");
        	      
        	      if (text.substring(text.length()-1).equals(".")) {
        				text = text.substring(0,text.length()-1);
        			}
        			
        	      if (!text.substring(text.length()-3).equals(".--") ) {
        				text = text + ".--";
        			}
        	      
        	      getInterval(text,display);
        	      
        	      
        	   }
        	});
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				display.setText("");
				
			}
		});
		
		
        JFrame frame = new JFrame ("Listeleme505");
        frame.setPreferredSize(new Dimension(900,700));
        frame.setLayout(new BorderLayout());
        frame.add(topPanel,BorderLayout.NORTH);
	    frame.add ( middlePanel, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack ();
	    frame.setLocationRelativeTo ( null );
	    frame.setVisible ( true );
	    
	    
	    

	}
	
	public static void getInterval(String text, JTextArea display) {
		int first = 0;
		int last = 0;
		String temp = "";
		
		for (int i = 0; i < text.length(); i++) {
			temp = temp + text.charAt(i);
			if (temp.length() >= 2 && temp.substring(temp.length()-2).equals("|t")) {
				first = i-1;
				break;
			}
		}
		temp = "";
		for (int i = first; i < text.length(); i++) {
			temp = temp + text.charAt(i);
			if (temp.length() >= 3 && temp.substring(temp.length()-3).equals(".--")) {
				last = i+1;
				break;
			}
			
		}
		if (eauthor(text.substring(first,last)) != null) {
			display.append(eauthor(text.substring(first,last)) + "\n");
		}
		
		
		if (text.substring(last).length() > 5) {
			getInterval(text.substring(last),display);
		}
		
		
		
		
	}
	public static String eauthor(String text) {
		
		int first = 0;
		int last = 0 ;
		String temp = "";
		String authors = "";
		String title = "";
		
		if (!text.contains("/|r")) {
			return null;
		}
		
		for (int i = 0; i < text.length(); i++) {
			temp = temp + text.charAt(i);
			
			if (temp.length() >= 3 && temp.substring(temp.length()-3).equals("/|r")) {
				authors = text.substring(i+1, text.length()-3);
				last = i-2;
				break;
			}
		}
		title = text.substring(first,last);
		if (authors.contains(",")) {
			ArrayList<Integer> commaArray = new ArrayList<Integer>();
			for (int i = 0; i < authors.length(); i++) {
				if (authors.substring(i, i+1).equals(",")) {
					commaArray.add(i);
					
				}
			}
			String state = "";
			int begin = 0;
			for (int i = 0; i < commaArray.size(); i++) {
				state = state + surnameName(authors.substring(begin,commaArray.get(i))) + ",|eauthor." + title + "\n";
				begin = commaArray.get(i)+2 ;
				
			}
			state = state + surnameName(authors.substring(commaArray.get(commaArray.size()-1)+2)) + ",|eauthor." + title ;
			return state ;
			
		}
		else {
			String eauthor = surnameName(authors) + ",|eauthor." + title;
			return eauthor;
		}
		
		
		
		
		
	}
	public static String surnameName(String input) {
		int wordCount = 0;
		if (input == null || input.isEmpty()) {
		      wordCount = 0;
		      return null;
		    }
		else {
			String[] words = input.split("\\s+");
			ArrayList<String>wordsArray = new ArrayList<String>();
			for (int i = 0; i < words.length; i++) {
				wordsArray.add(words[i]);
			}
		    
		    ArrayList<String>toBeRemoved = new ArrayList<String>();
		    String[] removed = {"Dr.","Prof.","Arş.","Gör.","Doç.","Hakim","Av.","Öğr.","Gör.","Arb.","Assoc.","Lecturer","Res.","Asst.","Atty.","Member","Mediator","Judge"};
		    for (int i = 0; i < removed.length; i++) {
				toBeRemoved.add(removed[i]);
			}
		    wordsArray.removeAll(toBeRemoved);
		    wordCount = wordsArray.size();
		    if (wordCount > 2) {
		    	String returnedInput = "";
		    	returnedInput = returnedInput + wordsArray.get(0);
		    	for (int i = 1; i < wordsArray.size(); i++) {
		    		returnedInput = returnedInput + " " + wordsArray.get(i);
				}
				return "=>" + returnedInput;
			}
		    else if(wordCount == 1) {
		    	return wordsArray.get(0);
		    }
			else {
				return wordsArray.get(1) + ", " + wordsArray.get(0);
			}
		}
		
	}
		
}
	


