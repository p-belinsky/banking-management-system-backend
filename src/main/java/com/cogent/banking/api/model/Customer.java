package com.cogent.banking.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

	private String mobile;
	private String role = "CUSTOMER";

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cus_acc_tbl", joinColumns = @JoinColumn(name = "customerId"))
	private List<Account> accounts = new ArrayList<>();

	public void addAccount(Account account) {
		accounts.add(account);

	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public String getRole() {
		return role;
	}



}