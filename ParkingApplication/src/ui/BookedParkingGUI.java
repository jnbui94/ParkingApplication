package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import StaffMember.StaffMember;
import data.BookedParkingDB;
import data.MemberDB;
import data.ParkingLotDB;
import data.ParkingSpaceDB;
import parking.BookedParking;
import parking.ParkingLots;
/**
 * This is a GUI class that will display bookedParking.
 * @author John Bui, Loc Bui.
 *
 */
public class BookedParkingGUI extends JPanel implements ActionListener {
	/**
	 * Auto Generate Serial Number.
	 */
	private static final long serialVersionUID = 1779520078061383929L;
	/**
	 * JButton for List panel.
	 */
	private JButton btnList;
	/**
	 * JButton for Add panel.
	 */
	private JButton btnAdd;
	/**
	 * JPanel for Buttons.
	 */
	private JPanel pnlButtons;
	/**
	 * JPanel for Content.
	 */
	private JPanel  pnlContent;
	/**
	 * JPanel for combobutton.
	 */
	private JPanel  comboPanel;
	/**
	 * List of BookedParking.
	 */
	private List<BookedParking> mList;
	/**
	 * Columns Variables for display
	 */
	private String[] mItemColumnNames = { "name", "SpaceNo", "VisitorLiencse Number", "Visistor Available",
			"BookedDate", "StaffMemberNo" };
	/**
	 * 2D array to display data
	 */
	private Object[][] mData;
	/**
	 * JTable to display data
	 */
	private JTable table;
	/**
	 * scrollPane.
	 */
	private JScrollPane scrollPane;
	/**
	 * Panel Add
	 */
	private JPanel pnlAdd;
	/**
	 * Label for textfield.
	 */
	private JLabel txfLabel;
	/**
	 * Text field to add data.
	 */
	private JTextField txfField;
	/**
	 * Combo boxes for adding data.
	 */
	private JComboBox<String> myLotComboBox, mySpaceComboBox, myEmptyComboBox, myMemberComboBox, 
								mDayComboBox, mMonthComboBox, mYearComboBox;
	/**
	 * Add button.
	 */
	private JButton btnAddItem;
	/**
	 * String array for comboboxes.
	 */
	private String[] myLotArrays, mySpaceArrays, myMemberArrays, mDays, mMonths, mYears;
	/**
	 * Public contructor to create GUI
	 */
	public BookedParkingGUI() {
		setLayout(new BorderLayout());
		mList = getData();
		createComponents();
		setVisible(true);
		setSize(500, 500);
		
	}
	/**
	 * Returns the data (2d) to use in the list
	 * 
	 * @return the list of booked parking.
	 */
	private List<BookedParking> getData() {
		try {
			BookedParkingDB.deleteOldReservation();
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
				mData[i][5] = mList.get(i).getMemNo();
			}
		}
		return mList;
	}
	/**
	 * Create the three panels to add to this GUI. One for list, one for search,
	 * one for add.
	 */
	private void createComponents() {
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();
		btnList = new JButton("Booked Parking Lot List");
		btnList.addActionListener(this);

		btnAdd = new JButton("Book Parking Lot");
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
		pnlAdd.setLayout(new GridLayout(4, 0));
		// Get Lots name to display in the combo box
		comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(4, 0));
		
		List<StaffMember> listMember = null;
		try {
			listMember = MemberDB.getMembers();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		myMemberArrays = new String[listMember.size()];
		
        for (int i = 0; i < listMember.size(); i++) {
        	myMemberArrays[i] = listMember.get(i).getName() + " - " +listMember.get(i).getId();
        }

        myMemberComboBox = new JComboBox<>(myMemberArrays);
        comboPanel.add(new JLabel("Choose a staff member: "));
        comboPanel.add(myMemberComboBox);
		
		List<ParkingLots> listLots = null;
		try {
			listLots = ParkingLotDB.getParkingLots();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		myLotArrays = new String[listLots.size()];
		
        for (int i = 0; i < listLots.size(); i++) {
        	myLotArrays[i] = listLots.get(i).getName();
        }

        myLotComboBox = new JComboBox<>(myLotArrays);
        
        JButton updateBtn = new JButton("Click here!!!!");
        updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedLotName = myLotComboBox.getSelectedItem().toString();
				updateSpace(selectedLotName);
			}
        });
        
        comboPanel.add(new JLabel("Choose a parking lot: "));
        comboPanel.add(myLotComboBox);
        comboPanel.add(new JLabel("Click here to get parking spaces"));
        comboPanel.add(updateBtn);

        comboPanel.add(new JLabel("Choose a parking space: "));
        myEmptyComboBox = new JComboBox<String>();
        comboPanel.add(myEmptyComboBox);
        
        pnlAdd.add(comboPanel);
		
		mDays = new String[31];
		for (int i = 0; i <= 30; i++) {
			int date = i + 1;
			if (date < 10) {
				mDays[i] = "0" + Integer.toString(date);
			} else {
				mDays[i] = Integer.toString(date);
			}
		}
		
		JPanel dateComboPanel = new JPanel();
		
		mDayComboBox = new JComboBox<>(mDays);
		dateComboPanel.add(new JLabel("Choose a date"));
		dateComboPanel.add(mDayComboBox);
		
		mMonths = new String[12];
		for (int i = 0; i <= 11; i++) {
			int month = i + 1;
			if (month < 10) {
				mMonths[i] = "0" + Integer.toString(month);
			} else {
				mMonths[i] = Integer.toString(month);
			}
		}
		
		mMonthComboBox = new JComboBox<>(mMonths);
		dateComboPanel.add(new JLabel("Choose a month"));
		dateComboPanel.add(mMonthComboBox);
		
		mYears = new String[10];
		for (int i = 0; i <= 9; i++) {
			mYears[i] = "20" + Integer.toString(i + 17);
		}
		
		mYearComboBox = new JComboBox<>(mYears);
		dateComboPanel.add(new JLabel("Choose a year"));
		dateComboPanel.add(mYearComboBox);
		
		pnlAdd.add(dateComboPanel);
		
		JPanel textFieldPnl = new JPanel();
        txfLabel = new JLabel("Enter Visitor Vehicle License:");
        txfField = new JTextField(25);
        
        textFieldPnl.add(txfLabel);
        textFieldPnl.add(txfField);
        
        pnlAdd.add(textFieldPnl);
        
		JPanel panel = new JPanel();
		btnAddItem = new JButton("Add");
		btnAddItem.addActionListener(this);
		panel.add(btnAddItem);
		pnlAdd.add(panel);
		add(pnlContent, BorderLayout.CENTER);
	}
	
	/**
	 * Update the parking space based on the chosen parking lot.
	 * @param theLotName the parking lot name.
	 */
	private void updateSpace(String theLotName) {
		comboPanel.remove(7);
		List<String> listSpace = new ArrayList<String>();
		try {
			listSpace = ParkingSpaceDB.getParkingSpaces(theLotName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mySpaceArrays = new String[listSpace.size()];
		
        for (int i = 0; i < listSpace.size(); i++) {
        	mySpaceArrays[i] = listSpace.get(i);
        }

        mySpaceComboBox = new JComboBox<>(mySpaceArrays);
        comboPanel.add(mySpaceComboBox);
        comboPanel.revalidate();
	}
	/**
	 * Override method to handle click listener
	 */
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
		int available = 19 - BookedParkingDB.getAvailable();
		if (available >= 0) {
			if (mySpaceComboBox == null) {
				JOptionPane.showMessageDialog(null, "Please choose a parking space");
				return;
			} else {
				String nameAndNoStr = myMemberComboBox.getSelectedItem().toString();
				int firstIndex = nameAndNoStr.indexOf("-");
				String memNumber = nameAndNoStr.substring(firstIndex + 2);
				
				String license =  txfField.getText();
				if (txfField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Enter a visitor license number");
					txfField.setFocusable(true);
					return;
				}
				String temp = mMonthComboBox.getSelectedItem().toString() + "/" + mDayComboBox.getSelectedItem().toString() +
						"/" + mYearComboBox.getSelectedItem().toString();
				java.sql.Date sql = null;
				
				try {
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			        java.util.Date parsed =  format.parse(temp);
			        sql = new java.sql.Date(parsed.getTime());
			       
				} catch (ParseException e) {

					e.printStackTrace();
				}
				
				String name = myLotComboBox.getSelectedItem().toString();
				

				String space = mySpaceComboBox.getSelectedItem().toString();

				
				int secondIndex = space.indexOf("-");
				String spaceName = space.substring(0, secondIndex);
				
				if (!spaceName.equals(name)) {
					JOptionPane.showMessageDialog(null, "You selected wrong parking space");
				} else {
					BookedParking bookedParking = new BookedParking(name, space, license, available, sql, memNumber);
					if (BookedParkingDB.addBookedParkingLot(bookedParking).equals("Added Member Successfully")) {
						JOptionPane.showMessageDialog(null, "Successfully added");
					}
					txfField.setText("");
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Added Parking Failed! Parking lot is full");
		}
	}
}
