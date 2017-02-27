package ui;

import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * The main UI that has tabbed panes for the different parts of the 
 * system. It consists of tabs for Items, Categories, Clients and Transactions. 
 * @author mabraham
 *
 */
public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 5089396467589468183L;

		public static void main(String[] args) {
			new MainGUI();
		}
		
		/**
		 * Launches the GUI.
		 */
		public MainGUI() {
			super("Parking Application");
			createComponents();
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			setSize(700, 700);
		}


		/**
		 * Create the tabs for each part of the system. 
		 * TODO - Categories, Clients, Transactions
		 */
		private void createComponents()
		{
			JTabbedPane tabbedPane = new JTabbedPane();
			JComponent parkingLotPanel = makeTextPanel("Parking Lots");
			tabbedPane.addTab("Parking Lots", parkingLotPanel);
			JComponent memberPanel = makeTextPanel("Members");
			tabbedPane.addTab("Members", memberPanel);
//			JComponent clientPanel = makeTextPanel("Clients");
//			tabbedPane.addTab("Clients", clientPanel);
//			JComponent transPanel = makeTextPanel("Transactions");
//			tabbedPane.addTab("Transactions", transPanel);
			add(tabbedPane);
			
		}
		
		/**
		 * Create the particular part to add to the tab based on the type.
		 * @param type of the system.
		 * @return the panel
		 *  
		 */
		private JComponent makeTextPanel(String type) {
			JPanel panel = new JPanel();
			if (type.equalsIgnoreCase("Parking Lots")) {
				panel.add(new ParkingLotGUI());
			} else if (type.equalsIgnoreCase("Members")) {
				panel.add(new MemberGUI());
			} 
//				else if (type.equalsIgnoreCase("Clients")) {
//				panel.add(new ClientGUI());
//			} else if (type.equalsIgnoreCase("Transactions")) {
//			    panel.add(new TransactionGUI());
//			}
			return panel;
		}
		
}
