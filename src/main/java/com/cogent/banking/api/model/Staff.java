package com.cogent.banking.api.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("STAFF")
@Table(name="STAFF")
public class Staff extends User {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int staffId;
//	private String staffFullName;
//	private String staffUserName;
//	private String staffPassword;
	private String mobile;
	private boolean isEnabled;
	

	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	

}
