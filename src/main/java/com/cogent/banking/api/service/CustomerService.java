package com.cogent.banking.api.service;

import java.util.List;

import com.cogent.banking.api.exception.AccountNotCreatedException;
import com.cogent.banking.api.exception.AccountNotFoundException;
import com.cogent.banking.api.exception.AccountsNotUniqueException;
import com.cogent.banking.api.exception.BeneficiaryNotAddedException;
import com.cogent.banking.api.exception.CustomerNotFoundException;
import com.cogent.banking.api.exception.InsufficentBalanceException;
import com.cogent.banking.api.model.Account;
import com.cogent.banking.api.model.Beneficiary;
import com.cogent.banking.api.model.Customer;
import com.cogent.banking.api.model.Transaction;

public interface CustomerService {

	public Customer addCustomer(Customer customer);
	
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException;
	
	public Customer updateCustomerById(int customerId, Customer customer) throws CustomerNotFoundException;
	
	public String deleteCustomerById(int customerId);
	
	public List<Customer> getAllCustomers();
	
	public Transaction transferFunds(Transaction transaction) throws InsufficentBalanceException, AccountNotFoundException, AccountsNotUniqueException;

	public Account getAccountByAccountNumber(int accountNo) throws AccountNotFoundException;

	public Beneficiary addBeneficiaryToAccount(int customerId, Beneficiary beneficiary) throws CustomerNotFoundException, BeneficiaryNotAddedException;
	
	public List<Beneficiary> getAllBeneficiaryByCustomerId(int customerId) throws CustomerNotFoundException;

	public String deleteBeneficiaryByBeneficiaryIdAndCustomerId(int customerId, int beneficiaryId) throws CustomerNotFoundException;

	public Account addAccountToCustomer(int customerId, Account account) throws CustomerNotFoundException, AccountNotCreatedException;

	public List<Account> getAllAccountsByCustomerId(int customerId) throws CustomerNotFoundException;

	public Account getAccountByCustomerIdAndAccountId(int customerId, int accountId) throws CustomerNotFoundException, AccountNotFoundException;

	public List<Account> getAllAccounts();
	
	public Account approveAccount(int customerId, int accountNo, Account account) throws CustomerNotFoundException, AccountNotFoundException;

	public Account getAccountByCustomerIdAndAccountNo(int customerId, int accountNo)
			throws CustomerNotFoundException, AccountNotFoundException;

}
