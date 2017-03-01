package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import parking.CompanyParking;

public class CompanyParkingDB {
	/**
	 * static variable for connection.
	 */
	private static Connection mConnection;
	/**
	 * static variable for parking.
	 */
	private static List<CompanyParking>  mParkingList;
	
	/**
	 * Retrieves all spaces
	 * 
	 * @return list of ParkingLot
	 * @throws SQLException
	 */
	public static List<CompanyParking> getParkingSpaces() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select Member.memName, CompanyParking.*  from Member join CompanyParking on "
				+ "Member.memberNo = CompanyParking.memberNo";

		mParkingList = new ArrayList<CompanyParking>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("memName");
				String memberNo = rs.getString("memberNo");
				String spaceNo = rs.getString("spaceNo");
				String status = rs.getString("status");
				Double monthlyRate = rs.getDouble("monthlyRate");
				CompanyParking parkingLots = null;
				parkingLots = new CompanyParking(memberNo, spaceNo, status, monthlyRate);
				parkingLots.setMemberName(name);
				mParkingList.add(parkingLots);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return mParkingList;
	}
	/**
	 * Returns all parking lots that contain the search keyword in the status
	 * @param status status of parking space
	 * @return list of items that match.
	 * @throws SQLException
	 */
	public List<CompanyParking > getCompanyParkingLots(String status) throws SQLException {
		List<CompanyParking > filterList = new ArrayList<CompanyParking>();
		if (mParkingList == null) {
			getParkingSpaces();
		}
		status = status.toLowerCase();
		for (CompanyParking  parkingLots : mParkingList) {
			if (parkingLots.getmStatus().toLowerCase().contains(status)) {
				filterList.add(parkingLots);
			}
		}
		return filterList;
	}
	/**
	 * Assign staff member to parking space.
	 * @param Company parking lots
	 * @return Returns "Assign Item Successfully" or pop-up a Joptionpane for warning.
	 */
	public static String assignMember(CompanyParking lots) {
		String sql = "insert into CompanyParking(memberNo, spaceNo, `status`, monthlyRate) values "
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
			preparedStatement.setString(1, lots.getmMemberNo());
			preparedStatement.setString(2, lots.getmSpaceNo());
			preparedStatement.setString(3, lots.getmStatus());
			preparedStatement.setDouble(4,lots.getmMonthlyRate());
			preparedStatement.executeUpdate();
			return "Assign Member Successfully";
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "Fail to assign Member";
		}
	}
}
