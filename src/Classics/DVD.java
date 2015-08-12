// This program creates a Swing Interface for sorting a DVD collection

package Classics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class DVD extends JFrame implements ActionListener
{
	// construct components
	JLabel sortPrompt = new JLabel("Sort by:");
	JComboBox fieldCombo = new JComboBox();
	JTextPane textPane = new JTextPane();
	
	// initialize data in arrays
	String title[] = {"Casablanca", "Citizen Kane", "Singin' in the Rain", "The Wizard of Oz"};
	String studio[] = {"Warner Brothers", "RKO Pictures", "MGM", "MGM"};
	String year[] = {"1942", "1941", "1952", "1939"};
	
	// construct instance of DVD
	public DVD()
	{
		super("Classics on DVD");
	}
	
	// create the menu system
	public JMenuBar createMenuBar()
	{
		// create an instance of the menu
		JMenuBar mnuBar = new JMenuBar();
		setJMenuBar(mnuBar);
		
		// construct and populate the File menu
		JMenu mnuFile = new JMenu("File", true);
			mnuFile.setMnemonic(KeyEvent.VK_F);
			mnuFile.setDisplayedMnemonicIndex(0);
			mnuBar.add(mnuFile);
			
		JMenuItem mnuFileExit = new JMenuItem("Exit");
			mnuFileExit.setMnemonic(KeyEvent.VK_X);
			mnuFileExit.setDisplayedMnemonicIndex(1);
			mnuFile.add(mnuFileExit);
			mnuFileExit.setActionCommand("Exit");
			mnuFileExit.addActionListener(this);
			
		// construct and populate the Edit menu
		JMenu mnuEdit = new JMenu("Edit", true);
			mnuEdit.setMnemonic(KeyEvent.VK_E);
			mnuEdit.setDisplayedMnemonicIndex(0);
			mnuBar.add(mnuEdit);
			
		JMenuItem mnuEditInsert = new JMenuItem("Insert New DVD");
			mnuEditInsert.setMnemonic(KeyEvent.VK_I);
			mnuEditInsert.setDisplayedMnemonicIndex(0);
			mnuEdit.add(mnuEditInsert);
			mnuEditInsert.setActionCommand("Insert");
			mnuEditInsert.addActionListener(this);
			
		JMenuItem mnuEditSearch = new JMenu("Search");
			mnuEditSearch.setMnemonic(KeyEvent.VK_R);
			mnuEditSearch.setDisplayedMnemonicIndex(3);
			mnuEdit.add(mnuEditSearch);
			
		JMenuItem mnuEditSearchByTitle = new JMenuItem("by Title");
			mnuEditSearchByTitle.setMnemonic(KeyEvent.VK_T);
			mnuEditSearchByTitle.setDisplayedMnemonicIndex(3);
			mnuEditSearch.add(mnuEditSearchByTitle);
			mnuEditSearchByTitle.setActionCommand("title");
			mnuEditSearchByTitle.addActionListener(this);	
			
		JMenuItem mnuEditSearchByStudio = new JMenuItem("by Studio");
			mnuEditSearchByStudio.setMnemonic(KeyEvent.VK_S);
			mnuEditSearchByStudio.setDisplayedMnemonicIndex(3);
			mnuEditSearch.add(mnuEditSearchByStudio);
			mnuEditSearchByStudio.setActionCommand("studio");
			mnuEditSearchByStudio.addActionListener(this);
			
		JMenuItem mnuEditSearchByYear = new JMenuItem("by Year");
			mnuEditSearchByYear.setMnemonic(KeyEvent.VK_Y);
			mnuEditSearchByYear.setDisplayedMnemonicIndex(3);
			mnuEditSearch.add(mnuEditSearchByYear);
			mnuEditSearchByYear.setActionCommand("year");
			mnuEditSearchByYear.addActionListener(this);
			
		return mnuBar;
	} // end createMenuBar()
	
	// create the content pane
	public Container createContentPane()
	{
		// populate the JComboBox
		fieldCombo.addItem("Title");
		fieldCombo.addItem("Studio");
		fieldCombo.addItem("Year");
		fieldCombo.addActionListener(this);
		fieldCombo.setToolTipText("Click the drop-down arrow to display sort fields.");
		
		// construct and populate the north panel
		JPanel northPanel = new JPanel();
			northPanel.setLayout(new FlowLayout());
			northPanel.add(sortPrompt);
			northPanel.add(fieldCombo);
			
		// create the JText Pane and center panel
		JPanel centerPanel = new JPanel();
			setTabsAndStyles(textPane);
			textPane = addTextToTextPane();
			JScrollPane scrollPane = new JScrollPane(textPane);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPane.setPreferredSize(new Dimension(500, 200));
			centerPanel.add(scrollPane);
			
		// create Container and set attributes
		Container c = getContentPane();
			c.setLayout(new BorderLayout(10,10));
			c.add(northPanel, BorderLayout.NORTH);
			c.add(centerPanel, BorderLayout.CENTER);
			
		return c;
	} // end createContentPane()
	
	// method to create tab stops and set font styles
	protected void setTabsAndStyles(JTextPane textPane)
	{
		// create Tab Stops
		TabStop[] tabs = new TabStop[2];
			tabs[0] = new TabStop(200, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
			tabs[1] = new TabStop(300, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
		TabSet tabset = new TabSet(tabs);
		
		// set Tab Style
		StyleContext tabStyle = StyleContext.getDefaultStyleContext();
		AttributeSet aset = tabStyle.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabset);
		textPane.setParagraphAttributes(aset, false);
		
		// set Font Style
		Style fontStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		
		Style regular = textPane.addStyle("regular", fontStyle);
		StyleConstants.setFontFamily(fontStyle, "SansSerif");
		
		Style s = textPane.addStyle("italic", regular);
		StyleConstants.setItalic(s, true);
		
		s = textPane.addStyle("bold", regular);
		StyleConstants.setBold(s, true);
		
		s = textPane.addStyle("large", regular);
		StyleConstants.setFontSize(s, 16);
	} // end setTabsAndStyles()
	
	// method to add new text to the JTextPane
	public JTextPane addTextToTextPane()
	{
		Document doc = textPane.getDocument();
		try
		{
			// clear previous text
			doc.remove(0, doc.getLength());
			
			// insert title
			doc.insertString(0, "TITLE\tSTUDIO\tYEAR\n", textPane.getStyle("large"));
			
			// insert detail
			for (int j = 0; j < title.length; j++)
			{
				doc.insertString(doc.getLength(), title[j] + "\t",
				textPane.getStyle("bold"));
				doc.insertString(doc.getLength(), studio[j] + "\t",
				textPane.getStyle("italic"));
				doc.insertString(doc.getLength(), year[j] + "\n",
				textPane.getStyle("regular"));
			}
		}
		catch (BadLocationException ble)
		{
			System.err.println("Couldn't insert text.");
		}
		
		return textPane;
	} // end addTextToTextPane()
	
	// event to process user clicks
	public void actionPerformed(ActionEvent e)
	{
		String arg = e.getActionCommand();
		
		// user clicks the sort by combo box
		if (e.getSource() == fieldCombo)
		{
			switch (fieldCombo.getSelectedIndex())
			{
				case 0:
					sort(title);
					break;
				case 1:
					sort(studio);
					break;
				case 2:
					sort(year);
					break;
			}
		}
		
		// user clicks Exit on the File menu
		if (arg == "Exit")
		{
			System.exit(0);
		}
		
		// user clicks Insert New DVD on the Edit menu
		if (arg == "Insert")
		{
			// accept new data
			String newTitle = JOptionPane.showInputDialog(null, "Please enter the new movie's title");
			String newStudio = JOptionPane.showInputDialog(null, "Please enter the studio for " + newTitle);
			String newYear = JOptionPane.showInputDialog(null, "Please enter the year for " + newTitle);
			
			// enlarge arrays
			title = enlargeArray(title);
			studio = enlargeArray(studio);
			year = enlargeArray(year);
			
			// add new data to arrays
			title[title.length - 1] = newTitle;
			studio[studio.length - 1] = newStudio;
			year[year.length - 1] = newYear;
			
			// call sort method
			sort(title);
			fieldCombo.setSelectedIndex(0);
		}
		
		// user clicks Title on the Search submenu
		if (arg == "title")
		{
			search(arg, title);
		}
		
		// user clicks Studio on the Search submenu
		if (arg == "studio")
		{
			search(arg, studio);
		}
		
		// user clicks Year on the Search submenu
		if (arg == "year")
		{
			search(arg, year);
		}
	} // end actionPerformed()
	
	// method to enlarge array by 1
	public String[] enlargeArray(String[] currentArray)
	{
		String[] newArray = new String[currentArray.length + 1];
		for (int i = 0; i < currentArray.length; i++)
		{
			newArray[i] = currentArray[i];
		}
		
		return newArray;
	} // end enlargeArray()
	
	// method to sort arrays
	public void sort(String tempArray[])
	{
		// loop to control number of passes
		for (int pass = 1; pass < tempArray.length; pass++)
		{
			for (int element = 0; element < tempArray.length - 1; element++)
			{
				if (tempArray[element].compareTo(tempArray[element + 1]) > 0)
				{
					swap(title, element, element + 1);
					swap(studio, element, element + 1);
					swap(year, element, element + 1);
				}
			}
		}
		
		addTextToTextPane();
	} // end sort()
	
	// method to swap two elements
	public void swap(String swapArray[], int first, int second)
	{
		String hold;	// temporary holding area for swap
		hold = swapArray[first];
		swapArray[first] = swapArray[second];
		swapArray[second] = hold;
	} // end swap()
	
	// method to search
	public void search(String searchField, String searchArray[])
	{
		try
		{
			Document doc = textPane.getDocument(); // assign text to document object
			doc.remove(0, doc.getLength()); // clear previous text
			
			// display column titles
			doc.insertString(0, "TITLE\tSTUDIO\tYEAR\n", textPane.getStyle("large"));
			
			// prompt user for search data
			String search = JOptionPane.showInputDialog(null, "Please enter the " + searchField);
			boolean found = false;
			
			// search arrays
			for (int i = 0; i < title.length; i++)
			{
				if (search.compareTo(searchArray[i]) == 0)
				{
					doc.insertString(doc.getLength(), title[i] + "\t", textPane.getStyle("bold"));
					doc.insertString(doc.getLength(), studio[i] + "\t", textPane.getStyle("italic"));
					doc.insertString(doc.getLength(), year[i] + "\n", textPane.getStyle("regular"));
					
					found = true;
				}
			}
			
			if (found == false)
			{
				JOptionPane.showMessageDialog(null, "Your search produced no results", "No results found", JOptionPane.INFORMATION_MESSAGE);
				sort(title);
			}
		}
		catch (BadLocationException ble)
		{
			System.err.println("Couldn't insert text.");
		}
	} // end search()
	
	// main method executes at run time
	public static void main(String args[])
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		DVD f = new DVD();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setJMenuBar(f.createMenuBar());
		f.setContentPane(f.createContentPane());
		f.setSize(600, 375);
		f.setVisible(true);
	} // end main()
} // end DVD class
