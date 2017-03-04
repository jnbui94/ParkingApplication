package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import data.ParkingLotDB;
import parking.ParkingLots;


/**
 * ParkingLot GUI, use to display Parking Lot and add more parking lots.
 * @author John Bui.
 *
 */

public class ParkingLotGUI extends JPanel implements ActionListener {
	/**
	 * Auto Generate Id.
	 */
	private static final long serialVersionUID = 1779520078061383929L;
	/**
	 * List button.
	 */
	private JButton btnList;
	/**
	 * Add Button.
	 */
	private JButton btnAdd;
	/**
	 * Buttons Panel
	 */
	private JPanel pnlButtons;
	/**
	 * Content Panel
	 */
	private JPanel pnlContent;
	/**
	 * List of Parking Lots.
	 */
	private List<ParkingLots> mList;
	/**
	 * Array of Columns Names.
	 */
	private String[] mItemColumnNames = { "name", "location", "capacity",
			"numOfFloors" };
	/**
	 * 2D array for displaying data.
	 */
	private Object[][] mData;
	/**
	 * JTable for displaying data
	 */
	private JTable table;
	/**
	 * ScroolPane
	 */
	private JScrollPane scrollPane;
	/**
	 * Add Panel.
	 */
	private JPanel pnlAdd;
	/**
	 * Labels for textfield
	 */
	private JLabel[] txfLabel = new JLabel[4];
	/**
	 * JTextField
	 */
	private JTextField[] txfField = new JTextField[4];
	/**
	 * Add Parking button
	 */
	private JButton btnAddParking;

	/**
	 * This creates the GUI.
	 */
	public ParkingLotGUI() {
		setLayout(new BorderLayout());
		mList = getData();
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}

	/**
	 * Returns the data (2d) to use in the list.
	 * 
	 * @return the parking lots list
	 */
	private List<ParkingLots> getData() {
		try {
			mList = ParkingLotDB.getParkingLots();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		if (mList != null) {
			mData = new Object[mList.size()][mItemColumnNames.length];
			for (int i = 0; i < mList.size(); i++) {
				mData[i][0] = mList.get(i).getName();
				mData[i][1] = mList.get(i).getLocation();
				mData[i][2] = mList.get(i).getCapacity();
				mData[i][3] = mList.get(i).getFloor();
			}
		}
		return mList;
		
	}

	/**
	 * Create the three panels to add to this GUI. One for list,
	 * one for add.
	 */
	private void createComponents() {
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();
		btnList = new JButton("Parking Lot List");
		btnList.addActionListener(this);

		btnAdd = new JButton("Add Parking Lot");
		btnAdd.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnAdd);
		add(pnlButtons, BorderLayout.NORTH);

		addListPanel();
		addPanel();
		add(pnlContent, BorderLayout.CENTER);

	}
	/**
	 * This method add listPanel of parking lot.
	 */
	public void addListPanel() {
		// List Panel
		pnlContent = new JPanel();
		table = new JTable(mData, mItemColumnNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
	}
	
	/**
	 * This method create the add panel.
	 */
	public void addPanel() {
		// Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = { "Enter Name:", "Enter Location: ",
				"Enter Capacity: ", "Enter Number Of Floors: " };
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}


		JPanel panel = new JPanel();
		btnAddParking = new JButton("Add");
		btnAddParking.addActionListener(this);
		panel.add(btnAddParking);
		pnlAdd.add(panel);
	}
	/**
	 * Make the buttons work.
	 */
	@Override
	public void actionPerformed(ActionEvent thEvent) {
		if(thEvent.getSource() == btnAdd) {
			pnlContent.removeAll();
			addPanel();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
		} else if (thEvent.getSource() == btnAddParking) {
			performAddParking();
		} else if (thEvent.getSource() == btnList) {
			mList = getData();
			pnlContent.removeAll();
			table = new JTable(mData,mItemColumnNames);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			pnlContent.setVisible(true);
			this.repaint();
		}
		
	}
	/**
	 * Perform adding a parking lot.
	 */
	private void performAddParking() {
		String name = txfField[0].getText();
		if (name.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter a parking Lot name");
			txfField[0].setFocusable(true);
			return;
		}
		
		String location = txfField[1].getText();
		if (location.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter a location");
			txfField[1].setFocusable(true);
			return;
		}
		
		String capacitystr =  txfField[2].getText();
		int capacity = 0;
		if (capacitystr.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter a capacity");
			txfField[2].setFocusable(true);
			return;
		}
		
		if (capacitystr.length() != 0) {
			try {
				capacity = Integer.valueOf(capacitystr);
				if (capacity <= 0) {
					JOptionPane.showMessageDialog(null, "Enter capacity as a positive decimal");
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter capacity as a positive decimal");
				return;
			}
		}
		
		String floorstr = txfField[3].getText();
		int floor = 0;
		if (floorstr.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter number of floor");
			txfField[2].setFocusable(true);
			return;
		}
		
		floor = Integer.valueOf(floorstr);
		if (floorstr.length() != 0) {
			try {
				floor = Integer.valueOf(floorstr);
				if (floor <= 0) {
					JOptionPane.showMessageDialog(null, "Enter number of floor as a positive decimal");
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter number of floor as a positive decimal");
				return;
			}
		}
		
		ParkingLots parking;
		try {
			parking = new ParkingLots(name,location, capacity, floor);
			if (ParkingLotDB.addParkingLot(parking).equals("Added Member Successfully")) {
				JOptionPane.showMessageDialog(null, "Successfully added");
				// Clear all text fields.
				for (int i = 0; i < txfField.length; i++) {
					if (txfField[i].getText().length() != 0) {
						txfField[i].setText("");
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "Error adding");
		}
	}
}
