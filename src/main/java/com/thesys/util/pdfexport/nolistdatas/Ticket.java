package com.thesys.util.pdfexport.nolistdatas;

public class Ticket {

	private String ticketId;
	private String homesId;
	private String ticketCreateTime;
	private String ticketCompany;
	private String sysName;
	private String moneyLittle;
	private String moneyBig;
	private String accountCompany;
	private String bedNumber;
	private String username;
	private String password;

	public Ticket() {
		super();
	}

	public Ticket(String ticketId, String homesId, String ticketCreateTime,
			String ticketCompany, String sysName, String moneyLittle,
			String moneyBig, String accountCompany, String bedNumber,
			String username, String password) {
		this.ticketId = ticketId;
		this.homesId = homesId;
		this.ticketCreateTime = ticketCreateTime;
		this.ticketCompany = ticketCompany;
		this.sysName = sysName;
		this.moneyLittle = moneyLittle;
		this.moneyBig = moneyBig;
		this.accountCompany = accountCompany;
		this.bedNumber = bedNumber;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * @param ticketId
	 *            the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @return the homesId
	 */
	public String getHomesId() {
		return homesId;
	}

	/**
	 * @param homesId
	 *            the homesId to set
	 */
	public void setHomesId(String homesId) {
		this.homesId = homesId;
	}

	/**
	 * @return the ticketCreateTime
	 */
	public String getTicketCreateTime() {
		return ticketCreateTime;
	}

	/**
	 * @param ticketCreateTime
	 *            the ticketCreateTimeto set
	 */
	public void setTicketCreateTime(String ticketCreateTime) {
		this.ticketCreateTime = ticketCreateTime;
	}

	/**
	 * @return the ticketCompany
	 */
	public String getTicketCompany() {
		return ticketCompany;
	}

	/**
	 * @param ticketCompany
	 *            the ticketCompany toset
	 */
	public void setTicketCompany(String ticketCompany) {
		this.ticketCompany = ticketCompany;
	}

	/**
	 * @return the sysName
	 */
	public String getSysName() {
		return sysName;
	}

	/**
	 * @param sysName
	 *            the sysName to set
	 */
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	/**
	 * @return the moneyLittle
	 */
	public String getMoneyLittle() {
		return moneyLittle;
	}

	/**
	 * @param moneyLittle
	 *            the moneyLittle to set
	 */
	public void setMoneyLittle(String moneyLittle) {
		this.moneyLittle = moneyLittle;
	}

	/**
	 * @return the moneyBig
	 */
	public String getMoneyBig() {
		return moneyBig;
	}

	/**
	 * @param moneyBig
	 *            the moneyBig to set
	 */
	public void setMoneyBig(String moneyBig) {
		this.moneyBig = moneyBig;
	}

	/**
	 * @return the accountCompany
	 */
	public String getAccountCompany() {
		return accountCompany;
	}

	/**
	 * @param accountCompany
	 *            the accountCompany toset
	 */
	public void setAccountCompany(String accountCompany) {
		this.accountCompany = accountCompany;
	}

	/**
	 * @return the bedNumber
	 */
	public String getBedNumber() {
		return bedNumber;
	}

	/**
	 * @param bedNumber
	 *            the bedNumber to set
	 */
	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
