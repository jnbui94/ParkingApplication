package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import parking.ParkingLots;

/**
 * This class access to ParkingLot tables
 * @author John Bui
 *
 */
public class ParkingLotDB {
	/**
	 * static variable for connection.
	 */
	private static Connection mConnection;
	/**
	 * static variable for parking.
	 */
	private static List<ParkingLots>  mParkingList;

	/**
	 * Retrieves all parking lot from parking table.
	 * 
	 * @return list of ParkingLot
	 * @throws SQLException
	 */
	public static List<ParkingLots> getParkingLots() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from ParkingLot";

		mParkingList = new ArrayList<ParkingLots>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String location = rs.getString("location");
				int capacity = rs.getInt("capacity");
				int floor = rs.getInt("numOfFloors");
				ParkingLots parkingLots = null;
				parkingLots = new ParkingLots(name,location,capacity,floor);
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
	public List<ParkingLots> getParkingLots(String name) throws SQLException {
		List<ParkingLots> filterList = new ArrayList<ParkingLots>();
		if (mParkingList == null) {
			getParkingLots();
		}
		name = name.toLowerCase();
		for (ParkingLots parkingLots : mParkingList) {
			if (parkingLots.getName().toLowerCase().contains(name)) {
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
	public static String addParkingLot(ParkingLots lots) {
		String sql = "insert into ParkingLot(name, location, capacity, numOfFloors) values "
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
			preparedStatement.setString(1, lots.getName());
			preparedStatement.setString(2, lots.getLocation());
			preparedStatement.setInt(3, lots.getCapacity());
			preparedStatement.setInt(4, lots.getFloor());
			preparedStatement.executeUpdate();
			addParkingSpace(lots);
			addPublicParking(lots);
			return "Added Member Successfully";
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "fail";
		}
	}
	
	/**
	 * This method added all the spaces for a parking lot.
	 * @param lots the parking lot.
	 */
	public static void addParkingSpace(ParkingLots lots) {
		for (int i = 1; i <= lots.getCapacity(); i++) {
			String sql = "insert into ParkingSpace(name, spaceNo) values "
					+ "(?, ?); ";
			
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
				preparedStatement.setString(1, lots.getName());
				preparedStatement.setString(2, lots.getName() + "-" + i );
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	/**
	 * This method added all the spaces for the free parking lot.
	 * @param lots the parking lot.
	 */
	public static void addPublicParking(ParkingLots lots) {
		for (int i = 1; i <= lots.getCapacity(); i++) {
			String sql = "insert into PublicParking(spaceNo, available) values "
					+ "(?, ?); ";
			
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
				preparedStatement.setString(1, lots.getName() + "-" + i);
				preparedStatement.setBoolean(2, true);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
}
