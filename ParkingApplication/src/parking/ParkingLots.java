package parking;
/**
 * This class contain parkinglot which is unique by name.
 * @author jnbui
 *
 */
public class ParkingLots {
	/**
	 * Name of parking Lot
	 */
	private String mName;
	/**
	 * location of parking Lot
	 */
	private String mLocation;
	/**
	 * Capacity of parking lot
	 */
	private int mCapacity;
	/**
	 * number of floor.
	 */
	private int mFloor;
	
	/**
	 * Public constructor to create ParkingLot.
	 * @param theName	Name of lot
	 * @param theLocation: location of lot
	 * @param theCapacity:	capacity of lot
	 * @param theFloor: number of floor.
	 */
	public ParkingLots(String theName, String theLocation, int theCapacity, int theFloor){
		mName = theName;
		mLocation = theLocation;
		mCapacity = theCapacity;
		mFloor = theFloor;
	}

	/**
	 * @param theName the mName to set
	 */
	public void setName(String theName) {
		this.mName = theName;
	}
	/**
	 * @param theLocation the Location to set
	 */
	public void setLocation(String theLocation) {
		this.mLocation = mLocation;
	}
	/**
	 * @param theCapacity the Capacity to set
	 */
	public void setCapacity(int theCapacity) {
		this.mCapacity = theCapacity;
	}
	/**
	 * @param theFloor the mFloor to set
	 */
	public void setFloor(int theFloor) {
		mFloor = theFloor;
	}
	/**
	 * @return the Name
	 */
	public String getName() { return mName;}
	/**
	 * @return the Location
	 */
	public String getLocation() { return mLocation;}
	/**
	 * @return the Capacity
	 */
	public int getCapacity() { return mCapacity;}
	/**
	 * @return the Floor
	 */
	public int getFloor() { return mFloor;}
	/**
	 * adding Space number.
	 */
	public void addSpace(int numofSpace){
		if(numofSpace > 0) {
			setCapacity(mCapacity + numofSpace);
		} else {
			throw new IllegalArgumentException("Not a Valid Number");
		}
	}
	
	
}
