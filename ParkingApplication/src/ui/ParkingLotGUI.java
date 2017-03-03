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
 * A Panel that contains all the Item related functionality to 
 * list the items, search the items, add a new item, modify values within the item.
 * @author John Bui.
 *
 */

public class ParkingLotGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private List<ParkingLots> mList;

	private String[] mItemColumnNames = { "name", "location", "capacity",
			"numOfFloors" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[4];
	private JTextField[] txfField = new JTextField[4];
	private JButton btnAddParking;

	/**
	 * Use this for Item administration. Add components that contain the list,
	 * search and add to this.
	 */
	public ParkingLotGUI() {
		setLayout(new BorderLayout());
		mList = getData();
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}

	/*
	 * Returns the data (2d) to use in the list as well as the search panels.
	 * 
	 * @param title
	 * 
	 * @return
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

	/*
	 * Create the three panels to add to this GUI. One for list, one for search,
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
	 * @author Loc Bui
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
	 * Action perform response when a button is clicked.
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
	/*
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
		int capacity = Integer.valueOf(capacitystr);
		if (capacity <= 0) {
			JOptionPane.showMessageDialog(null, "Enter a valid capacity");
			txfField[2].setFocusable(true);
			return;
		}
		
		String floorstr = txfField[3].getText();
			int floor = Integer.valueOf(floorstr);
		if (floor < 0) {
			JOptionPane.showMessageDialog(null, "Enter a valid floor number");
			txfField[3].setFocusable(true);
			return;
		}
		//Create parking object and add to database.
		ParkingLots parking;
		parking = new ParkingLots(name,location, capacity, floor);
		try {
			ParkingLotDB.addParkingLot(parking);
			JOptionPane.showMessageDialog(null, "Added Succesfully");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "Error adding");
		}
		// Clear all text fields.
		for (int i = 0; i < txfField.length; i++) {
			if (txfField[i].getText().length() != 0) {
				txfField[i].setText("");
			}
		}
	}
}
