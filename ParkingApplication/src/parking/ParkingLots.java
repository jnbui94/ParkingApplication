package parking;

public class ParkingLots {
	private String mName;
	private String mLocation;
	private String mCapacity;
	private int mFloor;
	
	public ParkingLots(String theName, String theLocation, String theCapacity, int theFloor){
		mName = theName;
		mLocation = theLocation;
		mCapacity = theCapacity;
		mFloor = theFloor;
	}
	
	public void setName(String theName) {
		mName = theName;
	}
	public void setLocation(String theLocation) {
		mLocation = theLocation;
	}
	public void setCapacity(String theCapacity) {
		mCapacity = theCapacity;
	}
	public void setFloor(int theFloor) {
		mFloor = theFloor;
	}
	public String getName() { return mName;}
	public String getLocation() { return mLocation;}
	public String getCapacity() { return mCapacity;}
	public int getFloor() { return mFloor;}
	
	
}
