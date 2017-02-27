package StaffMember;
/**.
 * Staff Member contains info about a staff
 * @author jnbui
 *
 */
public class StaffMember {
	/**
	 * Staff ID
	 */
	private int mId;
	/**
	 * Staff Name
	 */
	private String mName;
	/**
	 * Staff extension Number
	 */
	private String mPhone;
	/**
	 * Staff vehicle license
	 */
	private String mLicense;
	/**
	 * Member parking Space number
	 */
	private int mSpaceNum;
	
	/**
	 * Public constructor for StaffMember to create Staffmember Object
	 * @param theId
	 * @param theName
	 * @param thePhone
	 * @param theLicense
	 */
	public StaffMember(int theId, String theName, String thePhone, String theLicense) {
		mId = theId;
		mName = theName;
		mPhone = thePhone;
		mLicense = theLicense;
		
	}
	/**
	 * Public constructor for StaffMember with parking space to create Staff member Object.
	 * @param theId
	 * @param theName
	 * @param thePhone
	 * @param theLicense
	 */
	public StaffMember(int theId, String theName, String thePhone, String theLicense, int theSpaceNum) {
		mId = theId;
		mName = theName;
		mPhone = thePhone;
		mLicense = theLicense;
		mSpaceNum = theSpaceNum;
		
	}

	/**
	 * @return the mId
	 */
	public int getId() {
		return mId;
	}

	/**
	 * @param theId the Id to set
	 */
	public void setId(int theId) {
		this.mId = theId;
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param theName the Name to set
	 */
	public void setName(String theName) {
		this.mName = theName;
	}

	/**
	 * @return the Phone
	 */
	public String getPhone() {
		return mPhone;
	}

	/**
	 * @param thePhone the Phone to set.
	 */
	public void setmPhone(String thePhone) {
		this.mPhone = thePhone;
	}

	/**
	 * @return the mLicense.
	 */
	public String getLicense() {
		return mLicense;
	}
	
	/**
	 * @param theLicense the mLicense to set.
	 */
	public void setLicense(String theLicense) {
		this.mLicense = theLicense;
	}
	/**
	 * Setting space number for member.
	 */
	public void setSpace(int theSpace) {
		mSpaceNum = theSpace;
	}
	/**
	 * getting Space number for staff.
	 */
	public int getSpaceNum() { return mSpaceNum;}
	
}
