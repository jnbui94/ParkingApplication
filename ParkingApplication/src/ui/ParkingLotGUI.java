package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 * A Panel that contains all the Item related functionality to 
 * list the items, search the items, add a new item, modify values within the item.
 * @author mabraham
 *
 */

public class ParkingLotGUI extends JPanel {
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnAdd;
	private JPanel pnlButtons, pnlContent;
//	private List<Item> mList;

	private String[] mItemColumnNames = { "name", "location", "capacity",
			"numOfFloors" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[4];
	private JTextField[] txfField = new JTextField[4];
	private JButton btnAddItem;

	/**
	 * Use this for Item administration. Add components that contain the list,
	 * search and add to this.
	 */
	public ParkingLotGUI() {
		setLayout(new BorderLayout());
//		mList = getData(null);
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
//	private List<Item> getData(String searchKey) {
//
//		if (searchKey != null)
//			mList = ItemCollection.search(searchKey);
//		else
//			mList = ItemCollection.getItems();
//
//		if (mList != null) {
//			mData = new Object[mList.size()][mItemColumnNames.length];
//			for (int i = 0; i < mList.size(); i++) {
//				mData[i][0] = mList.get(i).getName();
//				mData[i][1] = mList.get(i).getDescription();
//				mData[i][2] = mList.get(i).getPrice();
//				mData[i][3] = mList.get(i).getCondition();
//				mData[i][4] = mList.get(i).getItemCategory().getCategory();
//			}
//		}
//
//		return mList;
//	}

	/*
	 * Create the three panels to add to this GUI. One for list, one for search,
	 * one for add.
	 */
	private void createComponents() {
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();
		btnList = new JButton("Parking Lot List");


		btnAdd = new JButton("Add Parking Lot");

		pnlButtons.add(btnList);
		pnlButtons.add(btnAdd);
		add(pnlButtons, BorderLayout.NORTH);

//		addListPanel();
		
		//I create a new method for the add panel in order to refresh the category ComboBox.
		// @author Loc Bui
//		addPanel();

//		add(pnlContent, BorderLayout.CENTER);

	}
	
//	public void addListPanel() {
//		// List Panel
//		pnlContent = new JPanel();
//		table = new JTable(mData, mItemColumnNames);
//		scrollPane = new JScrollPane(table);
//		pnlContent.add(scrollPane);
//	}
	
	/**
	 * This method create the add panel.
	 * @author Loc Bui
	 */
	public void addPanel() {
		// Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(4, 0));
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
		btnAddItem = new JButton("Add");
		panel.add(btnAddItem);
		pnlAdd.add(panel);
	}

//	/**
//	 * Allows to add an Item. Only name is required.
//	 */
//	private void performAddItem() {
//
//		String name = txfField[0].getText();
//		if (name.length() == 0) {
//			JOptionPane.showMessageDialog(null, "Enter an item name");
//			txfField[0].setFocusable(true);
//			return;
//		}
//		String desc = txfField[1].getText();
//		if (desc.length() == 0) {
//			desc = null;
//		}
//		String priceStr = txfField[2].getText();
//		double price = 0.0;
//		if (priceStr.length() != 0) {
//			try {
//				price = Double.parseDouble(priceStr);
//			} catch (NumberFormatException e) {
//				JOptionPane.showMessageDialog(null, "Enter price as decimal");
//				txfField[2].setText("");
//				txfField[2].setFocusable(true);
//				return;
//			}
//		}
//		String condition = txfField[3].getText();
//		if (condition.length() == 0) {
//			condition = null;
//		}
//		String category = (String) cmbCategories.getSelectedItem();
//		Item item;
//		if (desc == null) {
//			item = new Item(name, new ItemCategory(category));
//		} else {
//			item = new Item(name, desc, price, condition, new ItemCategory(
//					category));
//		}
//		String message = "Item add failed";
//		if (ItemCollection.add(item)) {
//			message = "Item added";
//		}
//		JOptionPane.showMessageDialog(null, message);
//
//		// Clear all text fields.
//		for (int i = 0; i < txfField.length; i++) {
//			if (txfField[i].getText().length() != 0) {
//				txfField[i].setText("");
//			}
//		}
//	}

//	/**
//	 * Listen to the cell changes on the table. 
//	 */
//	@Override
//	public void tableChanged(TableModelEvent e) {
//		int row = e.getFirstRow();
//		int column = e.getColumn();
//		TableModel model = (TableModel) e.getSource();
//		String columnName = model.getColumnName(column);
//		Object data = model.getValueAt(row, column);
//		if (data != null && ((String) data).length() != 0) {
//			Item item = mList.get(row);
//			if (!ItemCollection.update(item, columnName, data)) {
//				JOptionPane.showMessageDialog(null, "Update failed");
//			}
//		}
//	}

}
