package parking;
/**
 * Parking Space class. Identify parking space.
 * @author jnbui
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
	private int mSpaceNum;
	
	/**
	 * Space available.
	 */
	private boolean mAvailable;
	/**
	 * Under cover status.
	 */
	private boolean mUndercover;
	/**
	 * Monthly rate for Staff
	 */
	private double mRate;
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
	 * Constructor to create ParkingSpace object with undercover status.
	 * @param theLot: Parking lot name.
	 * @param theSpaceNum: Space Number
	 */
	public ParkingSpace(String theLot, int theSpaceNum, boolean theStatus) {
		mLot = theLot;
		mSpaceNum = theSpaceNum;
		mUndercover = theStatus;
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
	/**
	 * return status of a space.
	 * @return
	 */
	public boolean getAvailable() { return mAvailable; }
	
	/**
	 * Setting a Space status
	 */
	public void setAvailable(boolean theStatus) {
		mAvailable = theStatus;
	}
	
	/**
	 * Getting under cover status.
	 */
	public boolean getUndercover() { return mUndercover; }
	
	/**
	 * setting under cover status.	
	 */
	public void setUndercover(boolean theStatus) {
		mUndercover = theStatus;
	}
	
	/**
	 * Getting monthly rate
	 */
	public double getRate() { return mRate; }
	/**
	 * Set monthly Rate
	 */
	public void setRate(double theRate) {
		mRate = theRate;
	}
}
