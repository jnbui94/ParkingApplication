package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clients.Clients;
import item.Item;
import item.ItemCategory;
/**
 * This class contains methods to access Client table data.
 * @author John Bui
 *
 */
public class ClientDB { 
	private Connection mConnection;
	private List<Clients> mClientList;
	
	/**
	 * Retrieves all clients from the Client table.
	 * 
	 * @return list of items
	 * @throws SQLException
	 */
	public List<Clients> getClients() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Clients ";

		mClientList = new ArrayList<Clients>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("clientId");
				String name = rs.getString("name");
				String address = rs.getString("address");
				Clients client = null;
				if (name == null || address == null) {
					throw new IllegalArgumentException("Invalid information");
				} else {
					client = new Clients(name, address);

				}
				mClientList.add(client);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {				
				stmt.close();
			}
		}
		return mClientList;
	}
	/**
	 * Returns all clients that contain the search keyword in the name. 
	 * @param name
	 * @return list of items that match.
	 * @throws SQLException
	 */
	public List<Clients> getClients(String name) throws SQLException{
		List<Clients> filterList = new ArrayList<Clients>();
		if (mClientList == null) {
			getClients();
		}
		name = name.toLowerCase();
		for (Clients client : mClientList) {
			if (client.getName().toLowerCase().contains(name)) {
				filterList.add(client);
			}
		}
		return filterList;
	}
	/**
	 * Retrieve the client with the given id or null if not found.
	 * @param id
	 * @return client
	 * @throws SQLException
	 */
	public Clients getClient(String id) throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Clients where clientId = " + id;

		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String Id = rs.getString("clientId");
				return new Clients(name, address);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
	}
	/**
	 * Adds a new client to the CLient table. 
	 * @param client
	 * @return Returns "Added Item Successfully" or "Error adding item: " with the sql exception.
	 */
	public String addClient(Clients client) {
		String sql = "insert into Clients(`name`, address) values "
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
			preparedStatement.setString(1, client.getName());
			preparedStatement.setString(2, client.getAddress());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding client: " + e.getMessage();
		}
		return "Added Client Successfully";
	}
	
	/**
     * Modifies the data on an Client
     * @param client
     * @param columnName
     * @param data
     * @return Returns a message with success or failure.
     */
	public String updateClient(Clients client, String column, Object data) {
		String name = client.getName();
		String sql = "update Clients set `" + column
				+ "` = ?  where name= ?" /*and id = ?*/ ;
		// For debugging - System.out.println(sql);
		PreparedStatement preparedStatement = null; 
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data); 
			else if (data instanceof Double)
				preparedStatement.setDouble(1, (Double) data);
			preparedStatement.setString(2, name); // for name = ?
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating item: " + e.getMessage();
		}
		return "Updated Item Successfully";
	
	}
	
}