package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import parking.BookedParking;
import parking.ParkingLots;

public class BookedParkingDB {
	/**
	 * static variable for connection.
	 */
	private static Connection mConnection;
	/**
	 * static variable for parking.
	 */
	private static List<BookedParking>  mParkingList;
	
	/**
	 * Retrieves all parking lot from parking table.
	 * 
	 * @return list of ParkingLot
	 * @throws SQLException
	 */
	public static List<BookedParking> getParkingLots() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from ParkingLot";

		mParkingList = new ArrayList<BookedParking>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String spaceNo = rs.getString("SpaceNo");
				String licenseNo = rs.getString("visistorLicenseNo");
				String available = rs.getString("visistorAvailable");
				Date bookedDate = rs.getDate("bookedDate");
				BookedParking parkingLots = null;
				parkingLots = new BookedParking(name,spaceNo,licenseNo,available,bookedDate);
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
	 * Returns all parking lots that contain the search keyword in the name or
	 * @param name name of parking lots
	 * @return list of items that match.
	 * @throws SQLException
	 */
	public List<BookedParking > getBookedParkingLots(String name) throws SQLException {
		List<BookedParking > filterList = new ArrayList<BookedParking >();
		if (mParkingList == null) {
			getParkingLots();
		}
		name = name.toLowerCase();
		for (BookedParking  parkingLots : mParkingList) {
			if (parkingLots.getmLot().toLowerCase().contains(name)) {
				filterList.add(parkingLots);
			}
		}
		return filterList;
	}
	/**
	 * Adds a new parking lots to parking lots table. 
	 * @param member
	 * @return Returns "Added Item Successfully" or pop-up a Joptionpane for warning.
	 */
	public static String addBookedParkingLot(BookedParking lots) {
		String sql = "insert into BookedVisistorParking(name, spaceNo,visistorLicenseNo,visistorAvailable,bookedDate) values "
				+ "(?, ?, ?, ?,?); ";

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
			preparedStatement.setString(1, lots.getmLot());
			preparedStatement.setString(2, lots.getmSpaceNum());
			preparedStatement.setString(3,lots.getmVisistorLicense());
			preparedStatement.setString(4, lots.getmVisistorAvailable());
			preparedStatement.setDate(5, lots.getmBookedDate());
			preparedStatement.executeUpdate();
			return "Added Member Successfully";
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "fail";
		}
	}
}
