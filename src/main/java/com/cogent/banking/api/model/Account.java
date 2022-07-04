package com.cogent.banking.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ACCOUNTS")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	private String customerName;
	private int accountNo;
	private double accountBalance;
	private String accountType;
	private boolean isApproved;
	private String staffUsername = "";
	private Date dateCreated = new Date();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "acc_ben_tbl", joinColumns = @JoinColumn(name = "accountId"))
	private List<Beneficiary> beneficiaries = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "acc_trn_tbl", joinColumns = @JoinColumn(name = "accountId"), inverseJoinColumns = @JoinColumn(name="transaction"))
	private List<Transaction> transactions = new ArrayList<>();
	
	public void addTransactions(Transaction transaction) {
		transactions.add(transaction);
	}

	public void removeTransactions(Transaction transaction) {
		transactions.remove(transaction);
	}
	
	public void addBeneficiaries(Beneficiary beneficiary) {
		beneficiaries.add(beneficiary);
	}

	public void removeBeneficiaries(Beneficiary beneficiary) {
		beneficiaries.remove(beneficiary);
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getStaffUsername() {
		return staffUsername;
	}

	public void setStaffUsername(String staffUsername) {
		this.staffUsername = staffUsername;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


	



	
	
}
