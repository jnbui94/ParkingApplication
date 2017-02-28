package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import data.MemberDB;
import data.ParkingLotDB;
import data.ParkingSpaceDB;
import parking.ParkingLots;
import StaffMember.StaffMember;


/**
 * This GUI allows user to assign a parking slot to a member. 
 * @author Loc Bui
 *
 */

public class CompanyParkingGUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnAssign;
	private JPanel pnlButtons, pnlContent;
	private List<StaffMember> mList;

	private String[] mItemColumnNames = { "name", "memberNo", "phone",
			"vehicleLicense" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;

	private JPanel pnlAdd, comboPanel;
	private JComboBox<String> myMemberComboBox, myLotComboBox, mySpaceComboBox;
	private JButton btnAddMember;
	private String[] myMemberArrays, myLotArrays, mySpaceArrays;

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

	/*
	 * Returns the data (2d) to use in the list as well as the search panels.
	 * 
	 * @return
	 */
	private List<StaffMember> getData() {
		
		try {
			mList = MemberDB.getMembers();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (mList != null) {
			mData = new Object[mList.size()][mItemColumnNames.length];
			for (int i = 0; i < mList.size(); i++) {
				mData[i][0] = mList.get(i).getName();
				mData[i][1] = mList.get(i).getId();
				mData[i][2] = mList.get(i).getPhone();
				mData[i][3] = mList.get(i).getLicense();
			}
		}

		return mList;
	}

	/*
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
		
		//I create a new method for the add panel in order to refresh the category ComboBox.
		// @author Loc Bui
		addPanel();

		add(pnlContent, BorderLayout.CENTER);

	}
	
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
	        comboPanel.add(new JComboBox<String>());
	        pnlAdd.add(comboPanel);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}



		JPanel panel = new JPanel();
		btnAddMember = new JButton("Add");
		btnAddMember.addActionListener(this);
		panel.add(btnAddMember);
		pnlAdd.add(panel);
	}
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAssign) {
			pnlContent.removeAll();
			addPanel();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAddMember) {
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
}
