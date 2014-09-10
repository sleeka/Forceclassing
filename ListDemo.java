
import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ListDemo extends JPanel
                      implements ListSelectionListener {
    
    FlowLayout layout = new FlowLayout();
    GridLayout gLayout = new GridLayout(0,3);
    private static int updateSeconds = 60;
    private JComboBox updateSecs;
    private static final String[] seconds = {"30", "45", "60", "75", "90", "105", "120", "135", "150", "165", "180", "195", "210"};
    private static final JLabel listLabel = new JLabel("Saved Files:");
    private static final JLabel useLabel = new JLabel("Files to Run:");
    private static final JLabel comboLabel = new JLabel("Update Random Files Every (Seconds):");
    private static final JLabel textLabel = new JLabel("Add an Admin Message upon Execution:");
    public JList list, use, admin;
    private boolean running = false;
    private DefaultListModel listModel, useModel, adminModel;
    private static final String delString = "Delete";
    private static final String newString = "New";
    private static final String useString = "-->";
    private static final String remString = "<--";
    private static final String runString = "Start";
    private static final String stopString = "Stop";
    private static final String textString = "Add";
    private JButton useButton, remButton, delButton, newButton, addTextButton, runButton;
    private JTextField employeeName, textMessage;
    private ArrayList files = new ArrayList();
    private JCheckBox[] checkBox = new JCheckBox[25];
    private static String[] weapons = {"M16 (R)",
    								   "M16A2 with M203 (G)",
    								   "M4 without mods (M)",
    								   "M4 with mods (M4M)",
    								   "Ranger M4A1 (M4A1 Auto)",
    								   "M4A1 modded (SF)",
    								   "RPK (RPK)",
    								   "M249 Saw (AR)",
    								   "M24 Sniper (S24)",
    								   "M82 Sniper (S)",
    								   "Special Purpose Rifle (SPR)",
    								   "Mosin-Nagant Sniper (MOS)",
    								   "Dragunov Sniper (SVD)",
    								   "VSS Vintorez (V)",
    								   "M9 Pistol (M9)",
    								   "PSO (PSO)",
    								   "RPG7 (RPG)",
    								   "RPG9 (RPG9)",
    								   "Bunker Defense Munition (BDM)",
    								   "AT4 Anti-Tank (AT4)",
    								   "Javelin (J)",
    								   "AK47 (AK)",
    								   "AK with GP (GP)",
    								   "AK74 (AK74SU)",    								   
    								   "Recruit - No Weapon (RCT)"};

    public ListDemo() {
        super(new BorderLayout());
        
        adminModel = new DefaultListModel();
		admin = new JList (adminModel);
		admin.setFixedCellWidth(500);
		admin.addListSelectionListener(this);
        admin.setVisibleRowCount(3);
        JScrollPane listScrollPane3 = new JScrollPane(admin);

		useModel = new DefaultListModel();

		use = new JList (useModel);		
		use.setFixedCellWidth(200);
		//use.setSelectedIndex(0);
        use.addListSelectionListener(this);
        use.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(use);
		
        listModel = new DefaultListModel();
        //listModel.addElement("Debbie Scott");
        //listModel.addElement("Scott Hommel");
        //listModel.addElement("Sharon Zakhour");
        //listModel.addElement("Sharon Zakhour");
        //listModel.addElement("Sharon Zakhour");
        //listModel.addElement("Sharon Zakhour");

		try
		{
			BufferedReader in = new BufferedReader(new FileReader("save/fileslist.txt"));
			
			String line = in.readLine();
			while (line!=null){
			//	if (line.charAt(0)!=' '){
				files.add(line);
				listModel.addElement(line);
				line = in.readLine();
			//	}			
			}
		}
		catch (IOException e){}

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        //list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        list.setFixedCellWidth(200);
        JScrollPane listScrollPane2 = new JScrollPane(list);

		runButton = new JButton (runString);
		runButton.addActionListener(new runListener());
				
		updateSecs = new JComboBox(seconds);
		updateSecs.setEditable(true);
		updateSecs.addActionListener(new comboBoxListener());
		updateSecs.setSelectedIndex(2);		

        newButton = new JButton(newString);
        HireListener newListener = new HireListener(newButton);
        newButton.setActionCommand(newString);
        newButton.addActionListener(newListener);
        newButton.setEnabled(false);

		ActListener actListener = new ActListener();
        
        useButton = new JButton(useString);
        useButton.setActionCommand(useString);
        useButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        useButton.addActionListener(actListener);
        
        remButton = new JButton(remString);
        remButton.setActionCommand(remString);
        remButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        remButton.addActionListener(actListener);

		delButton = new JButton(delString);
		delButton.setActionCommand(delString);
		delButton.addActionListener(actListener);

		TextListener messageListener = new TextListener(addTextButton);
		addTextButton = new JButton (textString);
		addTextButton.addActionListener(messageListener);
		addTextButton.setEnabled(false);

        textMessage = new JTextField(10);
        textMessage.addActionListener(messageListener);
        textMessage.getDocument().addDocumentListener(messageListener);
        
        employeeName = new JTextField(10);
        employeeName.addActionListener(newListener);
        employeeName.getDocument().addDocumentListener(newListener);
       // String name = listModel.getElementAt(
       //                       list.getSelectedIndex()).toString();
                
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                                           BoxLayout.LINE_AXIS));
        buttonPane.add(delButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(employeeName);
        buttonPane.add(newButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(Box.createVerticalStrut(50));
		pane.add(useButton);
		pane.add(remButton);
		
		JPanel pane2 = new JPanel();
        pane2.setLayout(new BoxLayout(pane2,BoxLayout.LINE_AXIS));
        pane2.add(runButton);
        pane2.add(Box.createHorizontalStrut(5));
        pane2.add(new JSeparator(SwingConstants.VERTICAL));
        pane2.add(Box.createHorizontalStrut(5));
        pane2.add(textMessage);
        pane2.add(addTextButton);
        pane2.add(Box.createHorizontalStrut(30));
        //pane2.add(listScrollPane3);
        pane2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JPanel checkBoxesPane = new JPanel();
		checkBoxesPane.setLayout(gLayout);
        checkBoxesPane.setPreferredSize(new Dimension(650,275));
		CheckBoxListener checkListener = new CheckBoxListener();
		for (int i=0; i<25; i++){
	       	checkBox[i] = new JCheckBox(weapons[i]);
	       	checkBox[i].addActionListener(checkListener);
		}
	    for (int i=0; i<25; i++)
	    	checkBoxesPane.add(checkBox[i]);
	    
		if (listModel.size() == 0)
			for (int i=0; i<25; i++)
                	checkBox[i].setEnabled(false);

        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(layout);
        flowPanel.add(listLabel);
        flowPanel.add (listScrollPane2);
        flowPanel.add(pane);
        flowPanel.add(useLabel);
        flowPanel.add(listScrollPane);
        flowPanel.add(comboLabel);
        flowPanel.add(updateSecs);
        flowPanel.add(buttonPane);
        flowPanel.add(checkBoxesPane);
        flowPanel.add(pane2);
        flowPanel.add(textLabel);
        flowPanel.add(listScrollPane3);
        
        add(flowPanel);
    }
    
    public void run(){
    	
    	ArrayList randomList = new ArrayList();
    		
    		for (int i=0; i<useModel.size(); i++){
    			
    			if (countDiffWeapons((String)useModel.get(i)) <= 1)
    				createSingleForceclass((String)useModel.get(i));
    			else 
    				randomList.add(i); 
    			
    		}
    	
    	while (running){
    		
    		long c = System.currentTimeMillis();
    		
    		while (System.currentTimeMillis()<(c + updateSeconds)){}
    		
    		for (int i=0; i<randomList.size(); i++){
    			
    			String tmp = (String)randomList.get(i);
    			createRandomForceclass((String)useModel.get(Integer.parseInt(tmp)));
    			
    		}    				
    	}
    }
    
    public int countDiffWeapons(String file){
    	
    	int c = 0;
    	
		try{
			BufferedReader in = new BufferedReader(new FileReader("save/"+file+".txt"));
    	 
    	 String line = in.readLine();
    	 while (line!=null){
    	 	if (line.length()<3)
    	 		c++;
    	 	line = in.readLine();
    	 }
		}
		catch(IOException exc){}
    	 
    	 return c;
    	 
    }
    
    public void createSingleForceclass(String file){
    	
    	FileOutputStream out;
    	
    	try{
    		out = new FileOutputStream ("C:/Program Files/Americas Army/System/"+file+".txt");
			PrintStream out1 = new PrintStream(out);
			
			BufferedReader in = new BufferedReader(new FileReader("save/"+file+".txt"));
			
			String line = in.readLine();
			while (line!=null){
				if (line.length()<3){
					int n = Integer.parseInt(line);
					out1.println("admin forceclass * " + weapons[n].substring(weapons[n].indexOf('('),weapons[n].indexOf(')')));
				}
				else
					out1.println(line);
			}			
    		
    		out.close();
    	}
    	
    	catch (IOException exc){}
    }
    
    public void createRandomForceclass(String file){
    	
    	ArrayList tmpList = new ArrayList();
    	FileOutputStream out;
    	Random rand = new Random();
    	
    	try{
    		out = new FileOutputStream ("C:/Program Files/Americas Army/System/"+file+".txt");
			PrintStream out1 = new PrintStream(out);
			
			BufferedReader in = new BufferedReader(new FileReader("save/"+file+".txt"));
			
			String line = in.readLine();
			while (line!=null){
				if (line.length()<3){
					int n = Integer.parseInt(line);
					tmpList.add(n);
				}
				else
					out1.println(line);
			}
						
    		for (int i=1; i<=1000; i++){
    			int n = rand.nextInt(tmpList.size());
    			out1.println("admin forceclass " + i + weapons[n].substring(weapons[n].indexOf('('),weapons[n].indexOf(')')));
    		}
    	
    	out.close();	
    	}
    	
    	catch (IOException exc){}    	
    }
    
    class ActListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.	            
            
            if (b.equals(delButton)){
            	int index = list.getSelectedIndex();
				files.remove((String) listModel.get(index));
				listModel.remove(index);
				 
				FileOutputStream out;
				
				try {
					
					out = new FileOutputStream ("save/fileslist.txt");
					PrintStream out1 = new PrintStream(out);
					
					for (int i=0; i<files.size(); i++)
						out1.println(files.get(i));
					
					out.close();
				}
				
				catch (IOException exc){}
				
				if (index == listModel.getSize())
	            	index--;
	            	
	            list.setSelectedIndex(index);
	            list.ensureIndexIsVisible(index);
	                       	
            }            
            
            if (b.equals(useButton)){
            	
	            int index = list.getSelectedIndex();
	            useModel.addElement(listModel.get(index));
	            listModel.remove(index);
	            
	            if (index == listModel.getSize())
	                    index--;
	            
	            list.setSelectedIndex(index);
	            list.ensureIndexIsVisible(index);
            
            }
            
            if (b.equals(remButton)){
            	int index = use.getSelectedIndex();
            	listModel.addElement(useModel.get(index));
	            useModel.remove(index);
	            
	            if (index == useModel.getSize())
	                    index--;
	            
	            
	            use.setSelectedIndex(index);
	                use.ensureIndexIsVisible(index);
            
            }
            
            listModel.getSize();
            useModel.getSize();

            if (listModel.getSize() == 0){ //Nobody's left, disable firing.
                useButton.setEnabled(false);
                delButton.setEnabled(false);
                
                for (int i=0; i<25; i++)
                	checkBox[i].setSelected(false);
                }
            else {
            	useButton.setEnabled(true);
            	delButton.setEnabled(true);
            	
            	for (int i=0; i<25; i++)
                	checkBox[i].setEnabled(true);
            }
            if (useModel.getSize() == 0)
            	remButton.setEnabled(false);
            else remButton.setEnabled(true);
            }
        }

    //This listener is shared by the text field and the hire button.
    class HireListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public HireListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = employeeName.getText();
			
			//User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                employeeName.requestFocusInWindow();
                employeeName.selectAll();
                return;
            }
			
			files.add(name);
			
			FileOutputStream out;
			ArrayList tmpList = new ArrayList();
			
			try {				
					out = new FileOutputStream ("save/" + name + ".txt");
					PrintStream out1 = new PrintStream(out);
				    
				    // Close our output stream
				    out.close();
				    
				    BufferedReader in = new BufferedReader(new FileReader("save/fileslist.txt"));
				
					String line = in.readLine();
					
					while (line!=null){
						tmpList.add(line);
						line = in.readLine();
					}
				    
				    out = new FileOutputStream ("save/fileslist.txt");
					out1 = new PrintStream(out);
					
					for (int i=0; i<tmpList.size(); i++)
						out1.println(tmpList.get(i));
						
					out1.println(name);
					
					out.close();
		}
		
		catch (IOException exc){}

			delButton.setEnabled(true);

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(employeeName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            employeeName.requestFocusInWindow();
            employeeName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }
	   
	class CheckBoxListener implements ActionListener{
	    /** Listens to the check boxes. */
    public void actionPerformed(ActionEvent e) {
        
        JCheckBox cb = (JCheckBox)e.getSource();
        
        for (int i=0; i<25; i++){
        	if (cb.equals(checkBox[i])){
        	
        		if (list.getSelectedIndex()!=-1){
        	
        	if (!checkBox[i].isSelected()){
        	
        	FileOutputStream out;		
			
				try
				{
					ArrayList tmpList = new ArrayList();
					
					BufferedReader in = new BufferedReader(new FileReader("save/"+((String)listModel.getElementAt(list.getSelectedIndex()))+".txt"));
					
					
					String line = in.readLine();
					while (line!=null){
						tmpList.add(line);
						line = in.readLine();
					}
	
				    // Open an output stream
				    out = new FileOutputStream ("save/" + ((String)listModel.getElementAt(list.getSelectedIndex())) + ".txt");
					PrintStream out1 = new PrintStream(out);
					
					for (int j=0; j<tmpList.size(); j++)
						out1.println(tmpList.get(j));
				    
				    out1.println(i);
				    
				    // Close our output stream
				    out.close();		
				}
				// Catches any error conditions
				catch (IOException exc){}
        		
        		}
        		
        		else {
        			
        			FileOutputStream out;		
			
				try
				{
					ArrayList tmpList = new ArrayList();
					
					BufferedReader in = new BufferedReader(new FileReader("save/"+((String)listModel.getElementAt(list.getSelectedIndex()))+".txt"));
					
					
					String line = in.readLine();
					while (line!=null){
						tmpList.add(line);
						line = in.readLine();
					}

					String str = "" + i;

					tmpList.remove(str);
	
				    // Open an output stream
				    out = new FileOutputStream ("save/" + ((String)listModel.getElementAt(list.getSelectedIndex())) + ".txt");
					PrintStream out1 = new PrintStream(out);
					
					if (tmpList.size()>0)
					for (int j=0; j<tmpList.size(); j++)
						out1.println(tmpList.get(j));
				    
				    // Close our output stream
				    out.close();		
				}
				// Catches any error conditions
				catch (IOException exc){}
        			
        		}
        		
        		}        		
        	}
        }
        
    	}
	}
	
	class comboBoxListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
			updateSeconds = Integer.parseInt((String)updateSecs.getSelectedItem());
			//System.out.println(updateSeconds);
		}
		
	}
	
	class runListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
		JButton b = (JButton)e.getSource();
		if (b.equals(runButton)){
			if (running==false){
				running=true;
				run();
				runButton.setText(stopString);
			}
			if (running==true){
				running=false;
				runButton.setText(runString);
			}
		}
		
		}	
	}
	
	    //This listener is shared by the text field and the add button.
    class TextListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public TextListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = textMessage.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                textMessage.requestFocusInWindow();
                textMessage.selectAll();
                return;
            }
			
			FileOutputStream out;
			ArrayList tmpList = new ArrayList();
			
			try {
				    
				    BufferedReader in = new BufferedReader(new FileReader("save/fileslist.txt"));
				
					String line = in.readLine();
					
					while (line!=null){
						tmpList.add(line);
						line = in.readLine();
					}
				    
				    out = new FileOutputStream ("save/" + ((String)listModel.get(list.getSelectedIndex())) + ".txt");
					PrintStream out1 = new PrintStream(out);
					
					for (int i=0; i<tmpList.size(); i++)
						out1.println(tmpList.get(i));
						
					out1.println(name);
					
					out.close();
		}
		
		catch (IOException exc){}

            int index = admin.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            adminModel.insertElementAt(textMessage.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            textMessage.requestFocusInWindow();
            textMessage.setText("");

            //Select the new item and make it visible.
            admin.setSelectedIndex(index);
            admin.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return adminModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
        
        private boolean handleEmptyTextField(DocumentEvent e) {
            if ((e.getDocument()).getLength() < 1) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }
        
	
    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        
        if (listModel.size()==0 || list.getSelectedIndex()==-1){
        	for (int i=0; i<25; i++)
        		checkBox[i].setEnabled(false);
        }
        else{
        	for (int i=0; i<25; i++)
        		checkBox[i].setEnabled(true);
        }
        
        if (e.getValueIsAdjusting() == false) {

            if (admin.getSelectedIndex() == -1 || list.getSelectedIndex()==-1) {
            //No selection, disable use button.
                addTextButton.setEnabled(false);

            } else {
            //Selection, enable the use button.
                addTextButton.setEnabled(true);
            }
        }        
        
      //  if (adminModel.contains(adminModel.get(admin.getSelectedIndex())) && (admin.getSelectedIndex()<=adminModel.size())){
      else{
      	
        //clear the combo boxes
        for (int i=0; i<25; i++)
        	checkBox[i].setSelected(false);
        
        //clear the admin messages
        for (int i=0; i<adminModel.getSize(); i++)
        	adminModel.remove(i);
        
				try
				{
					
					BufferedReader in = new BufferedReader(new FileReader("save/"+((String)listModel.getElementAt(list.getSelectedIndex()))+".txt"));
					
					String line = in.readLine();
					while (line!=null){
						//load the combo boxes
						if (line.length()<3)
							checkBox[Integer.parseInt(line)].setSelected(true);
						//load the admin messages
						else
							adminModel.addElement(line);
						line = in.readLine();
					}
				}
				catch (IOException exc){}       
        }
        
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(" ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setPreferredSize(new Dimension(800,700));
       // if ("aa server\icon.jpg"!=null)
        frame.setIconImage(new ImageIcon(ListDemo.class.getResource("icon.jpg")).getImage());
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
