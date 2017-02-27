package parking;
/**
 * Parking Space class. Identify parking space.
 * @author jnbui
 *
 */
public class ParkingSpace {
	private String mLot;
	private int mSpaceNum;
	
	
	/**
	 * Constructor to create ParkingSpace object.
	 * @param theLot: Parking lot name.
	 * @param theSpaceNum: Space Number
	 */
	public ParkingSpace(String theLot, int theSpaceNum) {
		mLot = theLot;
		mSpaceNum = theSpaceNum;
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
	public void setSpaceNum(int theSpaceNum) {
		mSpaceNum = theSpaceNum;
	}
	
	
}
