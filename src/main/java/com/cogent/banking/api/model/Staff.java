package com.cogent.banking.api.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("STAFF")
public class Staff extends User {
	

	private String mobile;
	private String role = "STAFF";

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRole() {
		return role;
	}

	
	
	

}
