package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SignatureException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
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
import javax.xml.stream.events.StartDocument;

import data.BookedParkingDB;
import data.ParkingLotDB;
import data.ParkingSpaceDB;
import parking.BookedParking;
import parking.ParkingLots;
import parking.ParkingSpace;

public class BookedParkingGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private List<BookedParking> mList;

	private String[] mItemColumnNames = { "name", "SpaceNo", "VisitorLiencse Number", "Visistor Available",
			"BookedDate" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;

	private JPanel pnlAdd, mLotPanel;
	private JLabel[] txfLabel = new JLabel[4];
	private JTextField[] txfField = new JTextField[4];
	private JButton btnAddParking;
	private JComboBox<String> mNameLot, mSpaceNo;
	private JButton btnAddItem;
	private List<ParkingLots> mListLot;
	private List<ParkingSpace> mSpaceLot;
	private String[] mArrayLot, mArrayNum;
	private String mTemp;
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
		//Add panel to add BookedParking.
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 2));
		// Get Lots name to display in the combo box
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(2, 1));
		
		try {
		mListLot= ParkingLotDB.getParkingLots();
		mArrayLot = new String[mListLot.size()];
		for(int i = 0; i < mListLot.size(); i++) {
			mArrayLot[i] = mListLot.get(i).getName();
		}
		
		mNameLot = new JComboBox<>(mArrayLot);
		comboPanel.add(new JLabel("Select Parking Lot"));
		comboPanel.add(mNameLot);
		mNameLot.addActionListener(	
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String selectedName = mNameLot.getSelectedItem().toString();
						mTemp = selectedName;
						btnAdd.doClick();
					}
				});
		System.out.println(mTemp);
		List<String> listSpace = ParkingSpaceDB.getParkingSpaces(mTemp);
		mArrayNum = new String[listSpace.size()];
		for(int i = 0; i<listSpace.size(); i++) {
			mArrayNum[i] = listSpace.get(i);
		}
		
		mSpaceNo = new JComboBox<>(mArrayNum);
		comboPanel.add(new JLabel("Choose a Space"));
		comboPanel.add(mSpaceNo);
		
		pnlAdd.add(comboPanel);
		String labelNames[] = { "Enter Visistor License Number:", "Enter Visistor Available: ",
		"Enter Date yyyyMMdd: "};
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			panel.setPreferredSize(getMinimumSize());
			pnlAdd.add(panel);	
		}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		JPanel panel = new JPanel();
		btnAddItem = new JButton("Add");
		btnAddItem.addActionListener(this);
		panel.add(btnAddItem);
		pnlAdd.add(panel);
		add(pnlContent, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent thEvent) {
		if (thEvent.getSource() == btnAdd) {
			pnlContent.removeAll();
			addPanel();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
		} else if (thEvent.getSource() == btnList) {
			mList = getData();
			pnlContent.removeAll();
			table = new JTable(mData,mItemColumnNames);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			pnlContent.setVisible(true);
			this.repaint();
		} else if (thEvent.getSource() == btnAddItem) {
			performBooking();
		}
	}
	/**
	 * This method will add DB class to add data to database.
	 */
	public void performBooking() {
		String license =  txfField[0].getText();
		String availablestr =  txfField[1].getText();
		int available = Integer.valueOf(availablestr);
		String temp =  txfField[2].getText();
		java.sql.Date sql = null;
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	        java.util.Date parsed =  format.parse(temp);
	        sql = new java.sql.Date(parsed.getTime());
	       
		} catch (ParseException e) {

			e.printStackTrace();
		}
		
		String name = mNameLot.getSelectedItem().toString();
		String space = mSpaceNo.getSelectedItem().toString();
		BookedParking bookedParking = new BookedParking(name, space, license, available,sql);
		try {
			BookedParkingDB.addBookedParkingLot(bookedParking);
			JOptionPane.showMessageDialog(null, "Added Successfully");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Added Parking Failed");
		}
	}
}
