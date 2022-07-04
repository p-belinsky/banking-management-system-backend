package com.cogent.banking.api.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("ADMIN")
@Table(name="ADMIN")
public class Admin extends User {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int adminId;
//	private String adminUserName;
//	private String adminPassword;
	
	private boolean isEnabled = true;

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	

	
	

}
