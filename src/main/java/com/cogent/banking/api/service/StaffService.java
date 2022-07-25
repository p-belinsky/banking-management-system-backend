package com.cogent.banking.api.service;

import java.util.List;

import com.cogent.banking.api.exception.AccountNotFoundException;
import com.cogent.banking.api.exception.CustomerNotFoundException;
import com.cogent.banking.api.exception.InsufficentBalanceException;
import com.cogent.banking.api.exception.StatusNotChangedException;
import com.cogent.banking.api.exception.UserNameNotUniqueException;
import com.cogent.banking.api.exception.AccountsNotUniqueException;
import com.cogent.banking.api.model.Account;
import com.cogent.banking.api.model.Admin;
import com.cogent.banking.api.model.Beneficiary;
import com.cogent.banking.api.model.Customer;
import com.cogent.banking.api.model.Staff;
import com.cogent.banking.api.model.Transaction;

public interface StaffService {

	
	public List<Beneficiary> getAllBeneficiaryNeedingApproval();
	
	public Beneficiary approveBeneficiary(Beneficiary beneficiary) throws StatusNotChangedException;
	
	public List<Account> getAllAccountNeedingApproval();
	
	public Account approveAccount(Account account) throws StatusNotChangedException;
	
	public List<Customer> getAllCustomers();
	
	public Customer enableCustomer(Customer customer) throws StatusNotChangedException;
	
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException;
	
	public Transaction transferFunds(Transaction transaction) throws InsufficentBalanceException, AccountNotFoundException, AccountsNotUniqueException;

	public Account getAccountByAccountNumber(int accountNo) throws AccountNotFoundException;
	
	public Staff addStaff(Staff staff) throws UserNameNotUniqueException;
	
	public List<Staff> getAllStaff();
	
	public String enableStaff(Staff staff) throws StatusNotChangedException;
	
	public Admin addAdmin(Admin admin) throws UserNameNotUniqueException;
	
	public Staff loginStaff(String username, String password);
	
	public Staff getStaffByUsername(String username);
	
	public Admin loginAdmin(String username, String password);
	
	public Admin getAdminByUsername(String username);

	public String matchStaff(Staff staff);

	public String matchAdmin(Admin admin);

	public void setStaffPassword(String username, String password);

	public void setAdminPassword(String username, String password);

	
}
