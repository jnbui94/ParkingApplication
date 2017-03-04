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
/**
 * This class will connect to Database and handle all connection with BookedParking 
 * table.
 * @author John Bui
 *
 */
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
	public static List<BookedParking> getParkingLots(){
		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Unable to connect to DB");
			}
		}
		Statement stmt = null;
		String query = "select * " + "from BookedVisitorParking";

		mParkingList = new ArrayList<BookedParking>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String spaceNo = rs.getString("spaceNo");
				String licenseNo = rs.getString("visitorLicenseNo");
				int available = rs.getInt("visitorAvailable");
				Date bookedDate = rs.getDate("bookedDate");
				String memNo = rs.getString("memberNo");
				BookedParking parkingLots = null;
				parkingLots = new BookedParking(name,spaceNo,licenseNo,available,bookedDate, memNo);
				mParkingList.add(parkingLots);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e + "BookedParkingDB GetParkingLots()");
			System.out.print("DB");
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mParkingList;
	}
	/**
	 * This method will get how many book-able available parking there are in the database.
	 */
	public static int getAvailable(){
		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Unable to connect to DB");
			}
		}
		Statement stmt = null;
		String query = "SELECT COUNT(spaceNo) 'count' FROM BookedVisitorParking;";
		int available = 0;
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				available = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e + "BookedParkingDB GetParkingLots()");
			System.out.print("DB");
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return available;
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
		String sql = "insert into BookedVisitorParking(name, spaceNo,visitorLicenseNo,visitorAvailable,bookedDate, memberNo) values "
				+ "(?, ?, ?, ?, ?, ?); ";

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
			preparedStatement.setInt(4, lots.getmVisistorAvailable());
			preparedStatement.setDate(5, lots.getmBookedDate());
			preparedStatement.setString(6, lots.getMemNo());
			preparedStatement.executeUpdate();
			return "Added Member Successfully";
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "Fail";
		}
	}
	
	/**
	 * This method will delete old data.
	 */
	public static void deleteOldReservation(){
		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Unable to connect to DB");
			}
		}
		PreparedStatement preparedStatement = null;
		String query = "DELETE FROM BookedVisitorParking WHERE DATEDIFF(bookedDate, CURDATE()) < 0;";
		try {
			preparedStatement = mConnection.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
