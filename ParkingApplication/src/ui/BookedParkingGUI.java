package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import data.BookedParkingDB;
import data.ParkingLotDB;
import parking.BookedParking;
import parking.ParkingLots;

public class BookedParkingGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private List<BookedParking> mList;

	private String[] mItemColumnNames = { "name", "location", "capacity",
			"numOfFloors" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[4];
	private JTextField[] txfField = new JTextField[4];
	private JButton btnAddParking;
	private JComboBox cmbCategories;
	private JButton btnAddItem;
	List<ParkingLots> categories;
	
	public BookedParkingGUI() {
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
	private List<BookedParking> getData() {
		try {
			mList = BookedParkingDB.getParkingLots();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		if (mList != null) {
			mData = new Object[mList.size()][mItemColumnNames.length];
			for (int i = 0; i < mList.size(); i++) {
				mData[i][0] = mList.get(i).getmLot();
				mData[i][1] = mList.get(i).getmSpaceNum();
				mData[i][2] = mList.get(i).getmVisistorLicense();
				mData[i][3] = mList.get(i).getmVisistorAvailable();
				mData[i][4] = mList.get(i).getmBookedDate();
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
		btnList = new JButton("Booked Parking Lot List");
		btnList.addActionListener(this);

		btnAdd = new JButton("Assign Parking Lot");
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
	 * This method create the add panel
	 * @throws SQLException 
	 */
	public void addPanel() {
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = { "Enter Visistor License Number:", "Enter Visistor Available: ",
				"Enter Date mm//dd/yyyy: "};
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}

		// Get categories to display in the combo box
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(1, 1));
		try {
		categories = ParkingLotDB.getParkingLots();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		if (categories != null) {
			cmbCategories = new JComboBox(categories);
			cmbCategories.setSelectedIndex(0);
			comboPanel.add(new JLabel("Select Lot:"));
			comboPanel.add(cmbCategories);
			pnlAdd.add(comboPanel);
		}

		JPanel panel = new JPanel();
		btnAddItem = new JButton("Add");
		btnAddItem.addActionListener(this);
		panel.add(btnAddItem);
		pnlAdd.add(panel);

		add(pnlContent, BorderLayout.CENTER);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
