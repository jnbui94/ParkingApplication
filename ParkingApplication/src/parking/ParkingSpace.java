package parking;
/**
 * Parking Space class. Identify parking space.
 * @author John Bui
 *
 */
public class ParkingSpace {
	/**
	 * Parking lot Name
	 */
	private String mLot;
	/**
	 * Space number
	 */
	private String mSpaceNo;
	
	/**
	 * Constructor to create ParkingSpace object.
	 * @param theLot: Parking lot name.
	 * @param theSpaceNum: Space Number
	 */
	public ParkingSpace(String theLot, String theSpaceNo) {
		mLot = theLot;
		mSpaceNo = theSpaceNo;
	}

	/**
	 * Setter to set lot name.
	 * @param theLot lot name
	 */
	public void setLot(String theLot) {
		mLot = theLot;
	}
	
	/**
	 * setter to set SpaceNum.
	 * @param theSpaceNum spaceNum.
	 */
	public void setSpaceNum(String theSpaceNum) {
		mSpaceNo = theSpaceNum;
	}
	
	public String getLot() {
		return mLot;
	}
	
	public String getSpaceNo() {
		return mSpaceNo;
	}
}
