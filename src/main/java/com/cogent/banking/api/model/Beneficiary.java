package com.cogent.banking.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cogent.banking.api.enums.Status;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="beneficiaryId")
@Entity
@Table(name="BENEFICIARIES")
public class Beneficiary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int beneficiaryId;
	private int beneficiaryNo;
	private String beneficiaryName;
	private Date dateCreated = new Date();
	private Status status;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ben_acc_tbl",
		joinColumns = {@JoinColumn(name ="fk_beneficiary")},
		inverseJoinColumns = {@JoinColumn(name = "fk_account")})
	private List<Account> accounts = new ArrayList<Account>();
	
	
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public void addToAccounts(Account account) {
		this.accounts.add(account);
	}
	
	public void removeFromAccounts(Account account) {
		this.accounts.remove(account);
	}
	
	public int getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public int getBeneficiaryNo() {
		return beneficiaryNo;
	}
	public void setBeneficiaryNo(int beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
