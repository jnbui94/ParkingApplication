package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import data.CompanyParkingDB;
import data.MemberDB;
import data.ParkingLotDB;
import data.ParkingSpaceDB;
import parking.CompanyParking;
import parking.ParkingLots;
import StaffMember.StaffMember;


/**
 * This GUI allows user to assign a parking slot to a member. 
 * @author Loc Bui
 *
 */

public class CompanyParkingGUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1779520078061383929L;
	/**
	 * All the buttons
	 */
	private JButton btnList, btnAssign, btnAddMember;
	/**
	 * All the panels
	 */
	private JPanel pnlButtons, pnlContent, pnlAdd, comboPanel;
	/**
	 * The list
	 */
	private List<CompanyParking> mList;
	/**
	 * The column names.
	 */
	private String[] mItemColumnNames = { "name", "memberNo", "spaceNo",
			"status", "monthlyRate" };
	/**
	 * The data object.
	 */
	private Object[][] mData;
	/**
	 * The table.
	 */
	private JTable table;
	/**
	 * The scroll panel
	 */
	private JScrollPane scrollPane;
	/**
	 * All the combo boxes.
	 */
	private JComboBox<String> myMemberComboBox, myLotComboBox, mySpaceComboBox, myEmptyComboBox;
	/**
	 * All the arrays for combo boxes.
	 */
	private String[] myMemberArrays, myLotArrays, mySpaceArrays;
	/**
	 * The label.
	 */
	private JLabel txfLabel;
	/**
	 * The text field.
	 */
	private JTextField txfField;

	/**
	 * Use this for CompanyParkingGUI administration. Add components that contain the list,
	 * assign slot to members.
	 */
	public CompanyParkingGUI() {
		setLayout(new BorderLayout());
		mList = getData();
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}

	/**
	 * Returns the data (2d) to use in the list as well as the search panels.
	 * @return the list.
	 */
	private List<CompanyParking> getData() {
		
		try {
			mList = CompanyParkingDB.getParkingSpaces();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (mList != null) {
			mData = new Object[mList.size()][mItemColumnNames.length];
			for (int i = 0; i < mList.size(); i++) {
				mData[i][0] = mList.get(i).getMemberName();
				mData[i][1] = mList.get(i).getmMemberNo();
				mData[i][2] = mList.get(i).getmSpaceNo();
				mData[i][3] = mList.get(i).getmStatus();
				mData[i][4] = mList.get(i).getmMonthlyRate();
			}
		}

		return mList;
	}

	/**
	 * Create the three panels to add to this GUI. One for list, one for assign.
	 */
	private void createComponents() {
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();
		btnList = new JButton("Slot Assignment List");
		btnList.addActionListener(this);


		btnAssign = new JButton("Assign Parking Slot");
		btnAssign.addActionListener(this);

		pnlButtons.add(btnList);
		pnlButtons.add(btnAssign);
		add(pnlButtons, BorderLayout.NORTH);

		addListPanel();
		
		addPanel();

		add(pnlContent, BorderLayout.CENTER);

	}
	
	/**
	 * This method creates the list panel.
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
		pnlAdd.setLayout(new GridLayout(4, 0));
		
		
		try {
			List<StaffMember> listMember = MemberDB.getMembers();
			myMemberArrays = new String[listMember.size()];
			
			comboPanel = new JPanel();
	        comboPanel.setLayout(new GridLayout(4, 0));
			
	        for (int i = 0; i < listMember.size(); i++) {
	        	myMemberArrays[i] = listMember.get(i).getName() + " - " +listMember.get(i).getId();
	        }

	        myMemberComboBox = new JComboBox<>(myMemberArrays);
	        comboPanel.add(new JLabel("Choose a staff member: "));
	        comboPanel.add(myMemberComboBox);
	        
			List<ParkingLots> listLots = ParkingLotDB.getParkingLots();
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
	        
	        JPanel textFieldPnl = new JPanel();
	        txfLabel = new JLabel("Enter Monthly Rate:");
	        txfField = new JTextField(25);
	        
	        textFieldPnl.add(txfLabel);
	        textFieldPnl.add(txfField);
	        
	        pnlAdd.add(textFieldPnl);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}



		JPanel panel = new JPanel();
		btnAddMember = new JButton("Add");
		btnAddMember.addActionListener(this);
		panel.add(btnAddMember);
		pnlAdd.add(panel);
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
	 * Makes the buttons work.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAssign) {
			pnlContent.removeAll();
			addPanel();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAddMember) {
			performAssignMember();
		} else if (e.getSource() == btnList) {
			mList = getData();
			pnlContent.removeAll();
			table = new JTable(mData, mItemColumnNames);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			pnlContent.setVisible(true);
			this.repaint();
		}
	}

	/**
	 * This method assign the space for the member.
	 */
	private void performAssignMember() {
		String nameAndNoStr = myMemberComboBox.getSelectedItem().toString();
		int firstIndex = nameAndNoStr.indexOf("-");
		String name = nameAndNoStr.substring(0, firstIndex - 1);
		
		String memNumber = nameAndNoStr.substring(firstIndex + 2);
		
		String lotName = myLotComboBox.getSelectedItem().toString();
		
		String spaceNo = mySpaceComboBox.getSelectedItem().toString();
		
		int secondIndex = spaceNo.indexOf("-");
		String spaceName = spaceNo.substring(0, secondIndex);
		
		if (!spaceName.equals(lotName)) {
			JOptionPane.showMessageDialog(null, "You selected wrong parking space");
		} else {
			String rateStr = txfField.getText();
			if (rateStr.length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a monthly rate!");
				txfField.setFocusable(true);
				return;
			}
			double monthlyRate = 0.0;
			if (rateStr.length() != 0) {
				try {
					monthlyRate = Double.parseDouble(rateStr);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Enter price as decimal");
					return;
				}
			}
	
			String status = "Taken";
			
			CompanyParking cp = new CompanyParking(memNumber, spaceNo, status, monthlyRate);
			cp.setMemberName(name);
			
			if (CompanyParkingDB.assignMember(cp).equals("Assign Member Successfully")) {
				JOptionPane.showMessageDialog(null, "Successfully added");
			}
			// Clear all text fields.
			if (txfField.getText().length() != 0) {
				txfField.setText("");
			}
			
		}
	}
}
