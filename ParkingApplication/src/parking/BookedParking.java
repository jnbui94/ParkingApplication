package parking;

import java.sql.Date;

/**
 * This class is for visitor parking
 * @author John Bui
 *
 */
public class BookedParking {
	/**
	 * Parking lot Name
	 */
	private String mLot;
	/**
	 * Space number
	 */
	private String mSpaceNum;
	/**
	 * License No
	 */
	private String mVisistorLicense;
	/**
	 * Space available.
	 */
	private int mVisistorAvailable;
	/**
	 * Date reserve.
	 */
	private Date mBookedDate;
	
	/**
	 * Constructor for booked parking
	 */
	public BookedParking(String theLot, String theSpaceNum, String theLicense, int theVisistor,
			Date theBookedDate) {
		mLot = theLot;
		mBookedDate = theBookedDate;
		mSpaceNum = theSpaceNum;
		mVisistorLicense =theLicense;
		mVisistorAvailable = theVisistor;
	}

	/**
	 * @return the mVisistorLicense
	 */
	public String getmVisistorLicense() {
		return mVisistorLicense;
	}

	/**
	 * @param mVisistorLicense the mVisistorLicense to set
	 */
	public void setmVisistorLicense(String mVisistorLicense) {
		this.mVisistorLicense = mVisistorLicense;
	}

	/**
	 * @return the mLot
	 */
	public String getmLot() {
		return mLot;
	}

	/**
	 * @param mLot the mLot to set
	 */
	public void setmLot(String mLot) {
		this.mLot = mLot;
	}

	/**
	 * @return the mSpaceNum
	 */
	public String getmSpaceNum() {
		return mSpaceNum;
	}

	/**
	 * @param mSpaceNum the mSpaceNum to set
	 */
	public void setmSpaceNum(String mSpaceNum) {
		this.mSpaceNum = mSpaceNum;
	}

	/**
	 * @return the mVisistorAvailable
	 */
	public int getmVisistorAvailable() {
		return mVisistorAvailable;
	}

	/**
	 * @param mVisistorAvailable the mVisistorAvailable to set
	 */
	public void setmVisistorAvailable(int mVisistorAvailable) {
		this.mVisistorAvailable = mVisistorAvailable;
	}

	/**
	 * @return the mBookedDate
	 */
	public Date getmBookedDate() {
		return mBookedDate;
	}

	/**
	 * @param mBookedDate the mBookedDate to set
	 */
	public void setmBookedDate(Date mBookedDate) {
		this.mBookedDate = mBookedDate;
	}
}
