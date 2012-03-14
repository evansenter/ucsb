




import java.awt.Dimension;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JButton;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;

import java.text.DecimalFormat;	
import java.io.*;

public class PhosphoGUI extends javax.swing.JFrame 
		implements ActionListener{
    
	public class ParameterBox extends JPanel {
		public String name;
		public Box InputBox;
		public JTextField Min;
		public JTextField Max;
		public JTextField Step;
		
		public ParameterBox(String name, java.awt.LayoutManager L) {
			this.name = name;
			setLayout(L);
			Min = new JTextField("Min");
			Max = new JTextField("Max");
			Step = new JTextField("Step");
			InputBox = Box.createHorizontalBox();
			InputBox.add(Min);
			InputBox.add(Max);
			InputBox.add(Step);
			setBorder(new TitledBorder(new EtchedBorder(), name));      
			add(InputBox, BorderLayout.CENTER);
			
		}
		
	}
	
	
	public class INTMODEHandler extends JPanel implements ActionListener {
		
	    private Box INTMODEBox;
	    private ButtonGroup INTMODEGroup;
	    private JRadioButton RELRANKButton;
	    private JRadioButton RELINTButton;
	    
	    public INTMODEHandler(java.awt.LayoutManager L) {
	    	setLayout(L);
	        INTMODEBox = Box.createVerticalBox();
	        INTMODEGroup = new javax.swing.ButtonGroup();
	        INTMODEGroup.add(RELRANKButton = new javax.swing.JRadioButton("RelativeRank"));
	        INTMODEGroup.add(RELINTButton = new javax.swing.JRadioButton("RelativeIntensity"));
	        INTMODEBox.add(RELRANKButton);
	        INTMODEBox.add(RELINTButton);
	        RELRANKButton.setActionCommand("Rank");
	        RELRANKButton.addActionListener(this);
	        RELINTButton.setActionCommand("Int");
	        RELINTButton.addActionListener(this);
            setBorder(new TitledBorder(new EtchedBorder(), "INTMODE"));      
            add(INTMODEBox, BorderLayout.CENTER);
	    }
		
        public void actionPerformed(ActionEvent e) {

      
        }
		
	}
	
	public class TOLERANCEHandler extends JPanel implements ActionListener {
		
	    private Box TOLERANCEBox;
	    private JTextField TOLERANCEText;
	    	    
	    public TOLERANCEHandler(java.awt.LayoutManager L) {
	    	setLayout(L);
	    	TOLERANCEBox = Box.createVerticalBox();
	    	TOLERANCEText = new JTextField("1");
	        TOLERANCEBox.add(TOLERANCEText);	        
            setBorder(new TitledBorder(new EtchedBorder(), "MATCH TOLERANCE"));      
            add(TOLERANCEBox, BorderLayout.CENTER);
	    }
		
        public void actionPerformed(ActionEvent e) {

      
        }
		
	}
	
	
	
	public class Gibbs extends JPanel implements ActionListener {
		
	    // Gibbs options
		private JPanel IterationsPanel;
		private Box IterationsBox;
		private JPanel SensitivityPanel;
		private Box SensitivityBox;
		private JRadioButton IterationsMax;
		private JRadioButton IterationsChange;
		private ButtonGroup IterationsGroup;
	    private JTextField IterationsMaxText;
	    private JTextField IterationsChangeText;
	    private JTextField SensitivityText;
	    private JCheckBox DscoreButton;
	    private JCheckBox ZscoreButton;
	    private JCheckBox DCdistanceButton;
	    private Box OptimizationBox;
	    private JPanel OptimizationPanel;
	    private JPanel GibbsPanel;
	    private Box GibbsBox;
		
		
		public Gibbs(java.awt.LayoutManager L) {
			setLayout(L);
			
			JPanel dummyPanel = new JPanel();
			dummyPanel.setPreferredSize(new Dimension(DefaultWidth/5, DefaultHeight/3));
	        // Add the Gibbs options, only looked at with gibbs
			DCdistanceButton = new javax.swing.JCheckBox("DCdistance");
	        DscoreButton = new javax.swing.JCheckBox("Dscore");
	        ZscoreButton = new javax.swing.JCheckBox("Zscore");	        
	        OptimizationBox = Box.createVerticalBox();
	        OptimizationBox.add(DscoreButton);
	        OptimizationBox.add(ZscoreButton);
	        OptimizationBox.add(DCdistanceButton);
	        OptimizationPanel = new JPanel(new BorderLayout());
	        OptimizationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Optimization Criteria"));  
	        OptimizationPanel.add(OptimizationBox);

	        SensitivityText = new JTextField("0.7", 5);
	        SensitivityBox = Box.createVerticalBox();
	        SensitivityBox.add(SensitivityText);
	        SensitivityPanel = new JPanel(new BorderLayout());
	        SensitivityPanel.setBorder(new TitledBorder(new EtchedBorder(), "Minimum Sensitivity"));
	        SensitivityPanel.add(SensitivityBox);
	        
	        IterationsGroup = new javax.swing.ButtonGroup();
	        IterationsGroup.add(IterationsMax = new javax.swing.JRadioButton("Max Iterations"));
	        IterationsGroup.add(IterationsChange = new javax.swing.JRadioButton("Iterate till no change after:"));
	        
	        IterationsMaxText = new JTextField("100", 5);
	        IterationsChangeText = new JTextField("20", 5);
	        
	        IterationsBox = Box.createVerticalBox();
	        
	        IterationsPanel = new JPanel(new BorderLayout());
	        IterationsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Termination Criteria"));
	       
	        IterationsBox.add(IterationsMax);
	        IterationsBox.add(IterationsMaxText);
	        IterationsBox.add(IterationsChange);
	        IterationsBox.add(IterationsChangeText);
	        IterationsPanel.add(IterationsBox);
	        GibbsBox = Box.createVerticalBox();
	        GibbsBox.add(OptimizationPanel);
	        GibbsBox.add(SensitivityPanel);
	        GibbsBox.add(IterationsPanel);
	        GibbsBox.add(dummyPanel);
	        DscoreButton.setActionCommand("Dscore");
	        DscoreButton.addActionListener(this);
	        ZscoreButton.setActionCommand("Zscore");
	        ZscoreButton.addActionListener(this);
	        DCdistanceButton.setActionCommand("DCdistance");
	        DCdistanceButton.addActionListener(this);
	        
	        setBorder(new TitledBorder(new EtchedBorder(), "Gibbs Sampling Options",
	        		javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
	        		new java.awt.Font(null, java.awt.Font.ITALIC, 14)));
	        add(GibbsBox);
		}
		
	    public void actionPerformed(ActionEvent e) {

	      
	    }
		
	}
	
	public class Confidence extends JPanel {
		public Box ConfBox;
		public JTextField DscoreConf;
		public JTextField ZscoreConf;
		public JTextField DCdistanceConf;
		public JPanel DscorePanel;
		public JPanel ZscorePanel;
		public JPanel DCdistancePanel;
		
		public Confidence(java.awt.LayoutManager L) {
		
			setLayout(L);

			DscorePanel = new JPanel();
			ZscorePanel = new JPanel();
			DCdistancePanel = new JPanel();
			DscorePanel.add(DscoreConf = new JTextField(5));
			ZscorePanel.add(ZscoreConf = new JTextField(5));
			DCdistancePanel.add(DCdistanceConf = new JTextField(5));
			DscorePanel.setBorder(new TitledBorder(new EtchedBorder(), "Dscore"));
			ZscorePanel.setBorder(new TitledBorder(new EtchedBorder(), "Zscore"));
			DCdistancePanel.setBorder(new TitledBorder(new EtchedBorder(), "DCdistance"));
	
			ConfBox = Box.createVerticalBox();
			ConfBox.add(DscorePanel);
			ConfBox.add(ZscorePanel);
			ConfBox.add(DCdistancePanel);
	        setBorder(new TitledBorder(new EtchedBorder(), "Confidence Cutoffs",
	        		javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
	        		new java.awt.Font(null, java.awt.Font.ITALIC, 14)));      
			add(ConfBox, BorderLayout.CENTER);
			
		}
		
	}
	
	
    class ProgramType extends JPanel implements ActionListener {
    	
    	
        private ButtonGroup ProgramTypeGroup;
        private Box ProgramTypeBox;
        private JRadioButton PhosphoScoreButton;
        private JRadioButton PhosphoGibbsButton;
    	
        public ProgramType(java.awt.LayoutManager L) {
        	
        	setLayout(L);
            // Add the program type:  regular run or gibbs sampling
            ProgramTypeBox = Box.createVerticalBox();
            ProgramTypeGroup = new javax.swing.ButtonGroup();
            ProgramTypeGroup.add(PhosphoScoreButton = new javax.swing.JRadioButton("PhosphoScore"));
            ProgramTypeGroup.add(PhosphoGibbsButton = new javax.swing.JRadioButton("PhosphoGibbs"));
            ProgramTypeBox.add(PhosphoGibbsButton);
            ProgramTypeBox.add(PhosphoScoreButton);
            //ProgramTypePanel = new JPanel(new BorderLayout());
            setBorder(new TitledBorder(new EtchedBorder(), "Program Type",
            		javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
            		new java.awt.Font(null, java.awt.Font.ITALIC, 14)));
            add(ProgramTypeBox, BorderLayout.CENTER);
            PhosphoScoreButton.setActionCommand("Score");
            PhosphoScoreButton.addActionListener(this);
            PhosphoGibbsButton.setActionCommand("Gibbs");
            PhosphoGibbsButton.addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e) {
        	if (e.getActionCommand().equals("Score")) {
        		ParametersBoxArray[2].Max.setEnabled(false);
        		ParametersBoxArray[2].Min.setEnabled(false);
        		ParametersBoxArray[2].Step.setEnabled(false);
        		GibbsPanel.DscoreButton.setEnabled(false);
        		GibbsPanel.ZscoreButton.setEnabled(false);
        		GibbsPanel.DCdistanceButton.setEnabled(false);
        		GibbsPanel.IterationsMaxText.setEnabled(false);
        		GibbsPanel.IterationsChangeText.setEnabled(false);
        		GibbsPanel.IterationsMax.setEnabled(false);
        		GibbsPanel.IterationsChange.setEnabled(false);
        	} else {
        		ParametersBoxArray[2].Max.setEnabled(true);
        		ParametersBoxArray[2].Min.setEnabled(true);
        		ParametersBoxArray[2].Step.setEnabled(true);
        		ParametersBoxArray[2].setEnabled(true);
        		GibbsPanel.DscoreButton.setEnabled(true);
        		GibbsPanel.ZscoreButton.setEnabled(true);
        		GibbsPanel.DCdistanceButton.setEnabled(true);
        		GibbsPanel.IterationsMaxText.setEnabled(true);
        		GibbsPanel.IterationsChangeText.setEnabled(true);
        		GibbsPanel.IterationsMax.setEnabled(true);
        		GibbsPanel.IterationsChange.setEnabled(true);
        	}
      
        }
    	
    	
    }
	
	
	// INTMODE not in here cause it uses only 2 options
	private static final String PARAMS[] =  {
		"THRESH", "TOTALWEIGHT", "MATCHWEIGHT", "INTWEIGHT", "INTEXP", "DEFAULTCOST"
	};
	
	private static final double[][] DEFAULTS = {		
		{0, 0, 0},			// THRESH
		{.5, 2, .05},		// Total Weight
		{.75, .75, .75}, 	// MATCHWEIGHT
		{1.25, 1.25, 1.25},	// INTWEIGHT
		{1, 1, 1},			// INTEXP
		{.1, 2, .01}	// DEFAULTCOST
	};
	
	private static ArrayList<String> ArgList;

	
    /** Creates new form NewJFrame */
    public PhosphoGUI() {
    	Globals.INTERACTIVE=true;
    	Init();
    }
    	
    private void Init() {
        java.awt.Dimension D = getToolkit().getScreenSize();
        setLocation(D.width/4, D.height/4);
    	setPreferredSize(new Dimension(DefaultWidth,DefaultHeight));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.setTitle("PhosphoScore for MSn-based Proteomics");
               
        ProgramTypePanel = new ProgramType(new BorderLayout());
        GibbsPanel = new Gibbs(new BorderLayout());
        ConfPanel = new Confidence(new BorderLayout());
        
        ParametersBox = Box.createVerticalBox();
        // Insert the rest of the options
        for (int i=0; i < PARAMS.length; i++) {
        	
        	ParametersBoxArray[i] = new ParameterBox(PARAMS[i], new BorderLayout());
        	ParametersBox.add(ParametersBoxArray[i]);
        	
        } 
        
        // Add the INTMODE option
        // Add the program type:  regular run or gibbs sampling

        INTMODEPanel = new INTMODEHandler(new BorderLayout());
        ParametersBox.add(INTMODEPanel);
        TOLERANCEPanel = new TOLERANCEHandler(new BorderLayout());
        ParametersBox.add(TOLERANCEPanel);
        
        ParametersPanel = new JPanel(new BorderLayout());
        ParametersPanel.setBorder(new TitledBorder(new EtchedBorder(), "Parameters",
        		javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
        		new java.awt.Font(null, java.awt.Font.ITALIC, 14)));
        ParametersPanel.add(ParametersBox);
        
        
        // Create the box that holds all the run options
        OptionsBox = Box.createHorizontalBox();
        OptionsBox.add(ProgramTypePanel);
        OptionsBox.add(ParametersPanel);
        OptionsBox.add(ConfPanel);
        OptionsBox.add(GibbsPanel);
        OptionsBox.add(Box.createHorizontalStrut(5));
       
        
        
        // Create the box that where the directory/file is entered
        RunDirText = new JTextField();
        RunDirText.setPreferredSize(new Dimension(DefaultWidth/2-5,50));
        OutputDirText = new JTextField();
        OutputDirText.setPreferredSize(new Dimension(DefaultWidth/2-5,50));
        RunDirPanel = new JPanel(new BorderLayout());
        RunDirPanel.setBorder(new TitledBorder(new EtchedBorder(), "Input Directory"));      
        RunDirPanel.add(RunDirText);
        OutputDirPanel = new JPanel(new BorderLayout());
        OutputDirPanel.setBorder(new TitledBorder(new EtchedBorder(), "Output Directory"));      
        OutputDirPanel.add(OutputDirText);
        DirPanel = Box.createHorizontalBox();
        DirPanel.add(RunDirPanel);
        DirPanel.add(OutputDirPanel);  
        
        
        // Create bottom row of buttons

        BottomButtonsPanel = new JPanel();
        //BottomButtonsPanel.setBorder(new CompoundBorder(
        //       BorderFactory.createLineBorder(Color.black, 1),         // Outer border
        //       BorderFactory.createBevelBorder(BevelBorder.RAISED)));  // Inner border

        //Border edge = BorderFactory.createRaisedBevelBorder();  // Button border   

        Dimension size = new Dimension(100,20);
        BottomButtonsPanel.add(RunButton = new JButton("Run"));
        RunButton.setPreferredSize(size);
        RunButton.setActionCommand("Run");
        RunButton.addActionListener(this);
        
        BottomButtonsPanel.add(DefaultsButton = new JButton("Defaults"));
        DefaultsButton.setPreferredSize(size);
        DefaultsButton.setActionCommand("Defaults");
        DefaultsButton.addActionListener(this);

        BottomButtonsPanel.add(ClearButton = new JButton("Clear"));
        ClearButton.setPreferredSize(size);
        ClearButton.setActionCommand("Clear");
        ClearButton.addActionListener(this);
        
        BottomButtonsPanel.add(HelpButton = new JButton("Help"));
        HelpButton.setPreferredSize(size);
        HelpButton.setActionCommand("Help");
        HelpButton.addActionListener(this);

        // Add all the top level components to the main frame
        Container content = getContentPane();    
        content.setLayout(new BorderLayout());     
        content.add(OptionsBox, BorderLayout.NORTH);
        content.add(DirPanel, BorderLayout.CENTER);
        content.add(BottomButtonsPanel, BorderLayout.SOUTH); 
        //BoxLayout box = new BoxLayout(content, BoxLayout.Y_AXIS); // Vertical for content pane
        //content.setLayout(box);                          // Set box layout manager
  
        pack();
        setVisible(true);
 
    }                        
    
    
    public void actionPerformed(ActionEvent e) {
        if ("Run".equals(e.getActionCommand())) {
        	RunPhospho();
        	
   
        } else if ("Defaults".equals(e.getActionCommand())) {
        	
        	InsertDefaults();
        	
        } else if ("Clear".equals(e.getActionCommand())) {
        	
        	Clear();
        	
        } else {

        	File F = new File("readme.txt");
        	char[] data = null;
        	
            try {
                FileReader fin = new FileReader (F);
                int filesize = (int)F.length();
                data = new char[filesize];
                fin.read (data, 0, filesize);
              } catch (FileNotFoundException exc) {
                String errorString = "File Not Found: readme.txt" ;
                data = errorString.toCharArray();
              } catch (IOException exc) {
                String errorString = "IOException: readme.txt";
                data = errorString.toCharArray();
              }
        
        	JFrame H = new javax.swing.JFrame();
            java.awt.Dimension D = getToolkit().getScreenSize();
        	H.setBounds(D.width/3, D.height/3, 640, 480);         
            JTextArea HelpTA = new JTextArea(30,60);
            //HelpTA.setPreferredSize(new Dimension(640, 480));
            JScrollPane scrollPane = new JScrollPane(HelpTA);
            HelpTA.setText(new String (data));
            HelpTA.setLineWrap(true);
            HelpTA.setCaretPosition(0);

            H.add(scrollPane);
            
            H.pack();
            H.setVisible(true);
            
        }
    }
    
    
    private void RunPhospho() {
      
    	final long timer_start = System.currentTimeMillis();
    	
    	 ArgList = new ArrayList<String>();
    	
 		if (OutputDirText.getText().equals("")) {
			OutputDirText.setText("." + File.separator);
		}
    	
		if (RunDirText.getText().equals("")) {
			DisplayError("Run directory/file required");
			return;
		} else if (!ProgramTypePanel.PhosphoScoreButton.isSelected() &&
				!ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
			DisplayError("A program type must be selected");
			return;
		} else if (!GibbsPanel.DscoreButton.isSelected() && !GibbsPanel.ZscoreButton.isSelected()
				&& !GibbsPanel.DCdistanceButton.isSelected() && ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
			DisplayError("An optimization option must be selected with Gibbs Sampling");
			return;
		} else if (GibbsPanel.SensitivityText.getText().equals("") && ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
			DisplayError("A minimum sensitivity must be specified");
			return;
		} else if (ProgramTypePanel.PhosphoGibbsButton.isSelected() && 
				((!GibbsPanel.IterationsMax.isSelected() && !GibbsPanel.IterationsChange.isSelected()) ||
					(GibbsPanel.IterationsMax.isSelected() && GibbsPanel.IterationsMaxText.getText().equals("")) ||
					(GibbsPanel.IterationsChange.isSelected() && GibbsPanel.IterationsChangeText.getText().equals("")))) {
			DisplayError("A termination criteria with a value must be selected with Gibbs Sampling");
			return;
		} else if (ParametersBoxArray[1].isEnabled() && !ParametersBoxArray[1].Min.getText().equals("Min") && 
				(!ParametersBoxArray[2].Min.getText().equals("Min") || !ParametersBoxArray[3].Min.getText().equals("Min"))) {
			DisplayError("If TOTALWEIGHT is selected, MATCHWEIGHT and INTWEIGHT cannot be used (set to 'Min')");
			return;
		} else {
			File f = new File(RunDirText.getText());
			if (!f.exists()) {
				DisplayError("Run directory/file does not exist!");
				return;
			}
			ArgList.add("-s"); 
			ArgList.add(RunDirText.getText());
			ArgList.add("-o"); 
			ArgList.add(OutputDirText.getText());
		}
    	

		
		try {
			if (!ConfPanel.DscoreConf.getText().equals("")) {
				ArgList.add("-CONFMODE");
				ArgList.add("dscore");
				ArgList.add(String.valueOf(Double.parseDouble(ConfPanel.DscoreConf.getText())));
			} else if (!ConfPanel.ZscoreConf.getText().equals("")) {
				ArgList.add("-CONFMODE");
				ArgList.add("zscore");
				ArgList.add(String.valueOf(Double.parseDouble(ConfPanel.ZscoreConf.getText())));
			} else if (!ConfPanel.DCdistanceConf.getText().equals("")) {
				ArgList.add("-CONFMODE");
				ArgList.add("dcdistance");
				ArgList.add(String.valueOf(Double.parseDouble(ConfPanel.DCdistanceConf.getText())));
			}
		} catch (NumberFormatException e) {
			DisplayError("Only numbers are allowed in the confidence values");
			return;
		}
		
		
		try {
			for (ParameterBox B : ParametersBoxArray) {
				if (!B.isEnabled() || B.Min.getText().equals("Min") || B.Min.getText().equals("")) {
					continue;
				}
				
				ArgList.add("-" + B.name);
				ArgList.add(String.valueOf(Double.parseDouble(B.Min.getText())));
				
				if (ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
					ArgList.add(String.valueOf(Double.parseDouble(B.Max.getText())));
					ArgList.add(String.valueOf(Double.parseDouble(B.Step.getText())));
				}
			
			}
			
			
			if (ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
				if (GibbsPanel.IterationsMax.isSelected()) {
					ArgList.add("-imax");
					ArgList.add(String.valueOf(Integer.parseInt(GibbsPanel.IterationsMaxText.getText())));
				} else {
					ArgList.add("-ichange");
					ArgList.add(String.valueOf(Integer.parseInt(GibbsPanel.IterationsChangeText.getText())));
				}				
				
				ArgList.add("-sensitivity");
				ArgList.add(String.valueOf(Double.parseDouble(GibbsPanel.SensitivityText.getText())));
				
				if (GibbsPanel.DscoreButton.isSelected()) {
					ArgList.add("-dscore");
				}
				if (GibbsPanel.ZscoreButton.isSelected()) {
					ArgList.add("-zscore");
				}
				if (GibbsPanel.DCdistanceButton.isSelected()) {
					ArgList.add("-dcdistance");
				}
				
				if (INTMODEPanel.RELINTButton.isSelected()) {
					ArgList.add("-INTMODE");
					ArgList.add("0.0");
					ArgList.add("0.0");
					ArgList.add("1.0");
				} else if (INTMODEPanel.RELRANKButton.isSelected()) {
					ArgList.add("-INTMODE");
					ArgList.add("1.0");
					ArgList.add("1.0");
					ArgList.add("1.0");
				}	
				if (TOLERANCEPanel.TOLERANCEText.getText().equals("")) {
					ArgList.add("-MATCHTOL");
					ArgList.add("1000");
					ArgList.add("1000");
					ArgList.add("1000");
				} 
				else {
					ArgList.add("-MATCHTOL");
					ArgList.add(TOLERANCEPanel.TOLERANCEText.getText());
					ArgList.add(TOLERANCEPanel.TOLERANCEText.getText());
					ArgList.add(TOLERANCEPanel.TOLERANCEText.getText());
				}
				
				
			} else {
				if (INTMODEPanel.RELINTButton.isSelected()) {
					ArgList.add("-INTMODE");
					ArgList.add("int");					
				}
				else {
					ArgList.add("-INTMODE");
					ArgList.add("rank");					
				} 
				if (TOLERANCEPanel.TOLERANCEText.getText().equals("")) {
					ArgList.add("-MATCHTOL");
					ArgList.add("1000");
				} 
				else {
					ArgList.add("-MATCHTOL");
					ArgList.add(TOLERANCEPanel.TOLERANCEText.getText());					
				}
			}
			

			
		} catch (NumberFormatException e) {
			DisplayError("A number must be entered in the parameter arguments (unless it is Min/Max/Step)");
			return;
		}
		
		
		Progress = new JFrame();
		java.awt.Dimension D = getToolkit().getScreenSize();
    	Progress.setLocation(D.width/3, D.height/3);
    	Progress.setPreferredSize(new Dimension(250,100));
		progressBar = new JProgressBar(0,100);
		progressBar.setStringPainted(true);
		//progressBar.setSize(100, 20); 
		progressBar.setPreferredSize(new Dimension(250,100));
		progressBar.setVisible(true);
		Progress.add(progressBar);
        Progress.pack();
        Progress.setVisible(true);
		
		new Thread(){
			public void run()
				{
				progressBar.setValue(0);
				System.out.println(ArgList.toString());
				if (ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
					Progress.setTitle("Running PhosphoGibbs...");
					PhosphoGibbs.main(ArgList.toArray(new String[ArgList.size()]));
					
				} else {
					Progress.setTitle("Running PhosphoScore...");
					System.out.println(ArgList.toString());
					PhosphoScore.main(ArgList.toArray(new String[ArgList.size()]));
					
				}
				
				}
			}.start();
		
		new Thread(){
			public void run()
			{
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignore) {}
				if (ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
					while(PhosphoGibbs.iter < PhosphoGibbs.iterations+1) {
						float p = (float) PhosphoGibbs.iterations/100;
						progressBar.setValue((int)((float) PhosphoGibbs.iter/p));
					}
				} else {
					while (true) {
						try {
							if (PhosphoScore.iterator < PhosphoScore.Interface.getLength()) {
								float p;
								p = (float) PhosphoScore.Interface.getLength()/100;							
								progressBar.setValue((int)((float) PhosphoScore.iterator/p));
							} else {
								break;
							}
						} catch (NullPointerException e) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException ignore) {}
						}
					}
				}
				Progress.setVisible(false);
				Progress.dispose();
		    	JOptionPane.showMessageDialog(new JFrame(), "PhosphoScoring Done in " + (System.currentTimeMillis() - timer_start) / 1000.0 + " seconds!  Results in " + OutputDirText.getText(),
		    			"PhosphoScore", JOptionPane.OK_OPTION);
			}
		}.start();
    		
    
    	
    }
    
  
    private void InsertDefaults() {
    	
    	DecimalFormat twoPlaces = new DecimalFormat("0.00");
    	
    	if (!ProgramTypePanel.PhosphoScoreButton.isSelected() &&
    			!ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
    		ProgramTypePanel.PhosphoGibbsButton.doClick();
    	}
    	
    	if (ProgramTypePanel.PhosphoScoreButton.isSelected()) {
    		for (int i=0; i < PARAMS.length; i++) {    			
    			if (DEFAULTS[i][0] == DEFAULTS[i][1]) {
    				ParametersBoxArray[i].Min.setText(twoPlaces.format(DEFAULTS[i][0]));
    			} else {
    				ParametersBoxArray[i].Min.setText(twoPlaces.format(
    						DEFAULTS[i][0]+(DEFAULTS[i][1]-DEFAULTS[i][0])/2));
    			}
    			ParametersBoxArray[i].Max.setText("Max");
    			ParametersBoxArray[i].Step.setText("Step");
    			if (ParametersBoxArray[i].name.equals("TOTALWEIGHT")) {
    				ParametersBoxArray[i].Min.setText("Min");
    				ParametersBoxArray[i].Max.setEnabled(false);
    				ParametersBoxArray[i].Min.setEnabled(false);
    				ParametersBoxArray[i].Min.setEnabled(false);
    			}
    		}
    		INTMODEPanel.RELRANKButton.doClick();
    		
    	} else if (ProgramTypePanel.PhosphoGibbsButton.isSelected()) {
    		for (int i=0; i < PARAMS.length; i++) {    			
    			if (DEFAULTS[i][0] == DEFAULTS[i][1]) {
    				ParametersBoxArray[i].Min.setText("Min");
        			ParametersBoxArray[i].Max.setText("Max");
        			ParametersBoxArray[i].Step.setText("Step");
    			} else {
    				ParametersBoxArray[i].Min.setText(twoPlaces.format(DEFAULTS[i][0]));
    				ParametersBoxArray[i].Max.setText(twoPlaces.format(DEFAULTS[i][1]));
    				ParametersBoxArray[i].Step.setText(twoPlaces.format(DEFAULTS[i][2]));
    			}

    		}
    		INTMODEPanel.RELRANKButton.doClick();
    		TOLERANCEPanel.TOLERANCEText.setText("1");
    		GibbsPanel.DCdistanceButton.doClick();
    		
    		GibbsPanel.IterationsMax.doClick();
    		GibbsPanel.IterationsMaxText.setText("100");
    		GibbsPanel.SensitivityText.setText("0.7");
    		
    	} 	
    	
    	ConfPanel.DscoreConf.setText(".01");
    	
    	
    }
    
    
    private void Clear() {
    	
		for (int i=0; i < PARAMS.length; i++) {    			
			ParametersBoxArray[i].Min.setText("Min");
			ParametersBoxArray[i].Max.setText("Max");
			ParametersBoxArray[i].Step.setText("Step");
		}
		GibbsPanel.ZscoreButton.setSelected(false);
		GibbsPanel.DscoreButton.setSelected(false);
		GibbsPanel.DCdistanceButton.setSelected(false);
		GibbsPanel.IterationsMaxText.setText("");
		GibbsPanel.IterationsChangeText.setText("");
		ConfPanel.DscoreConf.setText("");
		ConfPanel.ZscoreConf.setText("");
		ConfPanel.DCdistanceConf.setText("");
		RunDirText.setText("");
		OutputDirText.setText("");
		
		
    }
    
    
    private void DisplayError(String msg) {
      
    	//custom title, error icon
    	JOptionPane.showMessageDialog(this,
    	    msg,
    	    "Argument Error",
    	    JOptionPane.ERROR_MESSAGE);
    	
    }
    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PhosphoGUI G = new PhosphoGUI();
            }
        });
    }

    
    // GUI variables here
    
    
 // Boxes/Panels in the main frame
	private Box OptionsBox;
	private JPanel BottomButtonsPanel;
	private Box DirPanel;
	
	// Program Type
	private ProgramType ProgramTypePanel;
    private Gibbs GibbsPanel;
    private Confidence ConfPanel;    
    
    // Parameter Options
    private JPanel ParametersPanel;
    private Box ParametersBox;
    private ParameterBox[] ParametersBoxArray = new ParameterBox[PARAMS.length];
    private INTMODEHandler INTMODEPanel;
    private TOLERANCEHandler TOLERANCEPanel;
    
    // Bottom Buttons to run the program
    private JButton RunButton;
    private JButton DefaultsButton;
    private JButton ClearButton;
    private JButton HelpButton;
	
    // Text box for the run location
    private JTextField RunDirText;
    private JTextField OutputDirText;
    private JPanel RunDirPanel;
    private JPanel OutputDirPanel;
	
    private JProgressBar progressBar;
    private static JFrame Progress;

    private static int DefaultWidth = 810;
    private static int DefaultHeight = 600;
    
    
}
