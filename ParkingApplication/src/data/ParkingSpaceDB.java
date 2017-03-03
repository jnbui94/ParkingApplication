package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class access to ParkingLot tables to retrieve parking spaces.
 * @author Loc Bui
 *
 */
public class ParkingSpaceDB {
	/**
	 * static variable for connection.
	 */
	private static Connection mConnection;
	/**
	 * static variable for parking.
	 */
	private static List<String>  mSpaceList;

	/**
	 * Retrieves all parking lot from parking table.
	 * 
	 * @return list of ParkingLot
	 * @throws SQLException
	 */
	public static List<String> getParkingSpaces(String theLotName) throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select spaceNo from ParkingSpace where name = " + "'" + theLotName + "'" + ";";


		mSpaceList = new ArrayList<String>();
		
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String spaceNo = rs.getString("spaceNo");
				mSpaceList.add(spaceNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return mSpaceList;
	}
}
