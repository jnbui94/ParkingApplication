package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import data.MemberDB;
import StaffMember.StaffMember;


/**
 * A Panel that contains all the Item related functionality to 
 * list the items, search the items, add a new item, modify values within the item.
 * @author Loc Bui
 *
 */

public class MemberGUI extends JPanel implements ActionListener, TableModelListener{
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private List<StaffMember> mList;

	private String[] mItemColumnNames = { "name", "memberNo", "phone",
			"vehicleLicense" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[4];
	private JTextField[] txfField = new JTextField[4];
	private JButton btnAddMember;

	/**
	 * Use this for Item administration. Add components that contain the list,
	 * search and add to this.
	 */
	public MemberGUI() {
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
	 * Create the three panels to add to this GUI. One for list, one for search,
	 * one for add.
	 */
	private void createComponents() {
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();
		btnList = new JButton("Staff Member List");
		btnList.addActionListener(this);


		btnAdd = new JButton("Add Staff Member");
		btnAdd.addActionListener(this);

		pnlButtons.add(btnList);
		pnlButtons.add(btnAdd);
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
		pnlAdd.setLayout(new GridLayout(8, 0));
		String labelNames[] = { "Enter Name:", "Enter Member Number: ",
				"Enter Phone: ", "Enter Vehicle License: " };
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
		btnAddMember = new JButton("Add");
		btnAddMember.addActionListener(this);
		panel.add(btnAddMember);
		pnlAdd.add(panel);
	}

@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnAdd) {
		pnlContent.removeAll();
		addPanel();
		pnlContent.add(pnlAdd);
		pnlContent.revalidate();
		this.repaint();
	} else if (e.getSource() == btnAddMember) {
		performAddMember();
	} else if (e.getSource() == btnList) {
		mList = getData();
		pnlContent.removeAll();
		table = new JTable(mData, mItemColumnNames);
		table.getModel().addTableModelListener(this);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		pnlContent.revalidate();
		pnlContent.setVisible(true);
		this.repaint();
	}
}

private void performAddMember() {
	String name = txfField[0].getText();
	if (name.length() == 0) {
		JOptionPane.showMessageDialog(null, "Enter an member name");
		txfField[0].setFocusable(true);
		return;
	}
	
	String memNo = txfField[1].getText();
	if (memNo.length() == 0) {
		JOptionPane.showMessageDialog(null, "Enter an member number");
		txfField[1].setFocusable(true);
		return;
	}
	
	String phone = txfField[2].getText();
	if (phone.length() == 0) {
		JOptionPane.showMessageDialog(null, "Enter an member phone number");
		txfField[2].setFocusable(true);
		return;
	}
	
	String vehicleLicense = txfField[3].getText();
	if (vehicleLicense.length() == 0) {
		JOptionPane.showMessageDialog(null, "Enter an member vehicle license");
		txfField[3].setFocusable(true);
		return;
	}
	
	StaffMember member;
	
	member = new StaffMember(memNo, name, phone, vehicleLicense);

	if (MemberDB.addMember(member).equals("Added Member Successfully")) {
		JOptionPane.showMessageDialog(null, "Successfully added");
	}
	// Clear all text fields.
	for (int i = 0; i < txfField.length; i++) {
		if (txfField[i].getText().length() != 0) {
			txfField[i].setText("");
		}
	}
}

	/**
	 * Listen to the cell changes on the table. 
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);
		
		if (!columnName.matches("phone") && !columnName.matches("vehicleLicense")) {
            JOptionPane.showMessageDialog(null,
                    "Update failed, " + columnName + " CANNOT BE EDITED!!!");
		} else if (data != null && ((String) data).length() != 0) {
			StaffMember member = mList.get(row);
			if (MemberDB.updateMember(member, columnName, data).startsWith("Error")) {
				JOptionPane.showMessageDialog(null, "Update failed");
			}
		}
	}
}
