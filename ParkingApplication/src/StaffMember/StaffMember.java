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
	 * @param thePhone the Phone to set
	 */
	public void setmPhone(String thePhone) {
		this.mPhone = thePhone;
	}

	/**
	 * @return the mLicense
	 */
	public String getLicense() {
		return mLicense;
	}

	/**
	 * @param theLicense the mLicense to set
	 */
	public void setmLicense(String theLicense) {
		this.mLicense = theLicense;
	}
	
}
