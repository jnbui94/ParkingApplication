package data;


import StaffMember.StaffMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * This class contains methods to access Item and ItemCategory tables data.
 * @author Loc Bui
 *
 */
public class MemberDB {

	private static Connection mConnection;
	private static List<StaffMember> mMemberList;

	/**
	 * Retrieves all items from the Item table.
	 * 
	 * @return list of items
	 * @throws SQLException
	 */
	public static List<StaffMember> getMembers() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Member ";

		mMemberList = new ArrayList<StaffMember>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("memName");
				String memNumberStr = rs.getString("memberNo");
				String phone = rs.getString("phone");
				String vehicleLicense = rs.getString("vehicleLicense");
				StaffMember member = null;
				member = new StaffMember(memNumberStr, name, phone, vehicleLicense);
				mMemberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return mMemberList;
	}

	/**
	 * Returns all members that contain the search keyword in the name or
	 * @param name
	 * @return list of items that match.
	 * @throws SQLException
	 */
	public List<StaffMember> getMembers(String name) throws SQLException {
		List<StaffMember> filterList = new ArrayList<StaffMember>();
		if (mMemberList == null) {
			getMembers();
		}
		name = name.toLowerCase();
		for (StaffMember item : mMemberList) {
			if (item.getName().toLowerCase().contains(name)) {
				filterList.add(item);
			}
		}
		return filterList;
	}

	/**
	 * Adds a new item to the Item table. 
	 * @param member
	 * @return Returns "Added Item Successfully" or "Error adding item: " with the sql exception.
	 */
	public static String addMember(StaffMember member) {
		String sql = "insert into Member(memName, memberNo, phone, vehicleLicense) values "
				+ "(?, ?, ?, ?); ";

		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.setString(1, member.getName());
			preparedStatement.setString(2, member.getId());
			preparedStatement.setString(3, member.getPhone());
			preparedStatement.setString(4, member.getLicense());
			preparedStatement.executeUpdate();
			return "Added Member Successfully";
		} catch (SQLException e) {
//			e.printStackTrace();
//			return "Error adding member: " + e.getMessage();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "fail";
		}
		//return "Added Member Successfully";
	}

	/**
	 * Modifies the data on an Item - Only description, price and condition can be modified.
	 * @param row
	 * @param columnName
	 * @param data
	 * @return Returns a message with success or failure.
	 */
	public static String updateMember(StaffMember member, String columnName, Object data) {
		
		String name = member.getName();
		String memNo = member.getId();
		String sql = "update Member set `" + columnName
				+ "` = ?  where memName= ? and memberNo = ? ";
		// For debugging - System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data); 
			else if (data instanceof Double)
				preparedStatement.setDouble(1, (Double) data);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, memNo);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating member: " + e.getMessage();
		}
		return "Updated Member Successfully";
	
	}
}
