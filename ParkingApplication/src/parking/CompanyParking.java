package parking;

/**
 * This class is for company parking which is for staff members.
 * @author Loc Bui
 *
 */
public class CompanyParking {
	
	/**
	 * Member name
	 */
	private String mMemName;
	/**
	 * Member number
	 */
	private String mMemberNo;
	/**
	 * Space number
	 */
	private String mSpaceNo;
	/**
	 * Parking slot status
	 */
	private String mStatus;
	/**
	 * Monthly rate
	 */
	private double mMonthlyRate;
	
	/**
	 * Constructor for company parking
	 */
	public CompanyParking(String theMemberNo, String theSpaceNo, String theStatus, double theMonthlyRate) {
		mMemberNo = theMemberNo;
		mSpaceNo = theSpaceNo;
		mStatus = theStatus;
		mMonthlyRate = theMonthlyRate;
	}
	
	/**
	 * @return mMemName
	 */
	public String getmMemName() {
		return mMemName;
	}
	
	/**
	 * @return mMemberNo
	 */
	public String getmMemberNo() {
		return mMemberNo;
	}

	/**
	 * @return the mSpaceNo
	 */
	public String getmSpaceNo() {
		return mSpaceNo;
	}
	

	/**
	 * @return the mStatus
	 */
	public String getmStatus() {
		return mStatus;
	}
	
	/**
	 * Setter to set the status for a space
	 * @param theStatus
	 */
	public void setmStatus(String theStatus) {
		mStatus = theStatus;
	}

	/**
	 * @return the mMonthlyRate
	 */
	public double getmMonthlyRate() {
		return mMonthlyRate;
	}
	
	/**
	 * Set the member name
	 * @param theMemName
	 */
	public void setMemberName(String theMemName) {
		mMemName = theMemName;
	}
	
	/**
	 * @return mMemName
	 */
	public String getMemberName() {
		return mMemName;
	}
	
	
}
