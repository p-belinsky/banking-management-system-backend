package com.cogent.banking.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

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
import com.cogent.banking.api.repo.AccountRepository;
import com.cogent.banking.api.repo.BeneficiaryRepository;
import com.cogent.banking.api.repo.CustomerRepository;
import com.cogent.banking.api.repo.TransactionRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private TransactionRepository transactionRepository;
	private AccountRepository accountRepository;
	private BeneficiaryRepository beneficiaryRepository;
	

	public CustomerServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository,
			AccountRepository accountRepository, BeneficiaryRepository beneficiaryRepository) {
		super();
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.beneficiaryRepository = beneficiaryRepository;
	}



	@Override
	public Customer addCustomer(Customer customer) {
		Customer customerToAdd = customerRepository.save(customer);
		
		return customerToAdd;
	}



	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		
		try {
		Customer customer = customerRepository.findById(customerId).get();
		if(customer != null)
		return customer;
		} catch(NoSuchElementException e) {
			throw new CustomerNotFoundException();
		}
		return null;
		
		
	}



	@Override
	public Customer updateCustomerById(int customerId, Customer customer) throws CustomerNotFoundException {
		Customer customerToUpdate = getCustomerById(customerId);
		customerToUpdate.setFullname(customer.getFullname());
		customerToUpdate.setUsername(customer.getUsername());
		customerToUpdate.setPassword(customer.getPassword());
		customerToUpdate.setMobile(customer.getMobile());
	
	
		
		customerRepository.save(customerToUpdate);
		
		return customerToUpdate;
	}



	@Override
	public String deleteCustomerById(int customerId) {
		
		customerRepository.deleteById(customerId);
		
		return "Customer with id " + customerId + " was deleted successfully";
	}



	@Override
	public List<Customer> getAllCustomers() {
		List<Customer>customers = customerRepository.findAll();
		return customers;
	}



	@Override
	public Transaction transferFunds(Transaction transaction)
			throws InsufficentBalanceException, AccountNotFoundException, AccountsNotUniqueException {

		Transaction transactionToAdd = null;
		Account fromAccount = null;
		Account toAccount = null;
		int fromAccountNo = 0;
		int toAccountNo = 0;
		
	try {
		 fromAccountNo = transaction.getFromAccountNo();
		 toAccountNo = transaction.getToAccountNo();
		 if(fromAccountNo == toAccountNo) {
			 throw new Exception();
		 }
		 
	}catch(Exception e) {
		throw new AccountsNotUniqueException();
	}
		
		
	try {

		
			
		fromAccount = getAccountByAccountNumber(fromAccountNo);
		toAccount = getAccountByAccountNumber(toAccountNo);
		
		if(fromAccountNo != fromAccount.getAccountNo() || toAccountNo != toAccount.getAccountNo()) {
			throw new Exception();
		}
	}
	catch(Exception e) {
		throw new AccountNotFoundException();
	}
	
	try {
		
		double fromAccountBalance = fromAccount.getAccountBalance();
		double toAccountBalance = toAccount.getAccountBalance();
		double amount = transaction.getAmount();
		if(fromAccountBalance < amount) {
			throw new Exception();
		}
		fromAccountBalance = fromAccountBalance - amount;
		toAccountBalance = toAccountBalance + amount;	
		fromAccount.setAccountBalance(fromAccountBalance);
		toAccount.setAccountBalance(toAccountBalance);
		transactionToAdd = transactionRepository.save(transaction);
		Transaction transaction2 = transactionRepository.findById(transactionToAdd.getTransactionId()).get();
		
		fromAccount.addTransactions(transaction2);
		toAccount.addTransactions(transaction2);
		
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);

	}catch(Exception e) {
		throw new InsufficentBalanceException();
	}

		return transactionToAdd;
	
	}



	@Override
	public Account getAccountByAccountNumber(int accountNo) throws AccountNotFoundException {
		
		List<Account> accounts = accountRepository.findAll();
		Account account = null;

		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountNo() == accountNo) {
				account = accounts.get(i);
			}

		}
		try {
			if (account == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new AccountNotFoundException();
		}

		return account;
	}
	
	@Override
	public Beneficiary addBeneficiaryToAccount(int customerId, Beneficiary beneficiary) throws CustomerNotFoundException, BeneficiaryNotAddedException {
		
		Customer customer = getCustomerById(customerId);
		List<Account> accounts = customer.getAccounts();
		Account account = null;
		Beneficiary beneficiaryToAdd = null;
		for(int i =0; i < accounts.size(); i++) {
			if(accounts.get(i).getAccountNo() == beneficiary.getBeneficiaryNo()) {
				account = accounts.get(i);
				account.addBeneficiaries(beneficiary);
				beneficiaryToAdd = beneficiaryRepository.save(beneficiary);
				accountRepository.save(account);
			}
		}
		try {
			if(account == null) {
				throw new Exception();
			}
		}catch(Exception e) {
			throw new BeneficiaryNotAddedException();
		}
		
		
		return beneficiaryToAdd;
	}



	@Override
	public List<Beneficiary> getAllBeneficiaryByCustomerId(int customerId) throws CustomerNotFoundException {
		Customer customer = getCustomerById(customerId);
		List<Account> accounts = customer.getAccounts();
		List<Beneficiary> beneficiaries = null;
		List<Beneficiary> beneficiaries2 = new ArrayList<>();
		
		for(int i = 0; i < accounts.size(); i++) {
			 beneficiaries = accounts.get(i).getBeneficiaries();
			 for(int j = 0; j < beneficiaries.size(); j++) {
				 beneficiaries2.add(beneficiaries.get(j));
			 }
		}
		
		
		return beneficiaries2;
		
	}



	@Override
	public String deleteBeneficiaryByBeneficiaryIdAndCustomerId(int customerId, int beneficiaryId)
			throws CustomerNotFoundException {
		
		Customer customer = getCustomerById(customerId);
		List<Account> accounts = customer.getAccounts();
		List<Beneficiary> beneficiaries = null;
		Account account;
		Beneficiary beneficiary;
		for(int i = 0; i < accounts.size(); i++) {
			   account = accounts.get(i);
			   beneficiaries = accounts.get(i).getBeneficiaries();
			 for(int j = 0; j < beneficiaries.size(); j++) {
				 if(beneficiaries.get(j).getBeneficiaryId() == beneficiaryId) {
					 beneficiary = beneficiaries.get(j);
					 account.removeBeneficiaries(beneficiary);
					 beneficiaryRepository.deleteById(beneficiaryId);
					 accountRepository.save(account);
					 return "Beneficiary Deleted Successfully";
				 }
			 }
		}
	
		return "Beneficiary Not Deleted";
		
		
		
		
		
	}



	@Override
	public Account addAccountToCustomer(int customerId, Account account) throws CustomerNotFoundException, AccountNotCreatedException {
		try {
		Customer customer = getCustomerById(customerId);
		List<Account> accounts = accountRepository.findAll();
		for(int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getAccountNo() == account.getAccountNo()) {
				throw new Exception();
			}
		}
		
		customer.addAccount(account);
		Account accountToAdd = accountRepository.save(account);
		customerRepository.save(customer);
		return accountToAdd;
		}
		catch(Exception e) {
			throw new AccountNotCreatedException();
		}
		
		
	}



	@Override
	public List<Account> getAllAccountsByCustomerId(int customerId) throws CustomerNotFoundException {

		Customer customer = getCustomerById(customerId);
		List<Account> accounts = customer.getAccounts();
		
		return accounts;
		
	}



	@Override
	public Account getAccountByCustomerIdAndAccountId(int customerId, int accountId) throws CustomerNotFoundException, AccountNotFoundException {

		Customer customer = getCustomerById(customerId);
		List<Account> accounts = customer.getAccounts();
		Account account = null;
		
		for(int i = 0; i <accounts.size(); i ++) {
			if(accounts.get(i).getAccountId() == accountId) {
				account = accounts.get(i);
			}
		}
		
		try {
			if (account == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new AccountNotFoundException();
		}
		
		return account;
		
	}



	@Override
	public List<Account> getAllAccounts() {
	
		List<Account> accounts = accountRepository.findAll();
		
		return accounts;
	}



	@Override
	public Account approveAccount(int customerId, int accountNo, Account account)
			throws CustomerNotFoundException, AccountNotFoundException {

		Account accountToUpdate = getAccountByCustomerIdAndAccountNo(customerId, accountNo);
		accountToUpdate.setApproved(account.isApproved());
		accountRepository.save(accountToUpdate);
	return accountToUpdate;
		
	}
	
	@Override
	public Account getAccountByCustomerIdAndAccountNo(int customerId, int accountNo) throws CustomerNotFoundException, AccountNotFoundException {
		Customer customer = getCustomerById(customerId);
		List<Account> accounts = customer.getAccounts();
		Account account = null;
		for(int i =0; i < accounts.size(); i++) {
			if(accounts.get(i).getAccountNo() == accountNo) {
				account = accounts.get(i);
			}
		}
		
		try {
			if(account == null) {
				throw new Exception();
			}
		}
		catch(Exception e) {
			throw new AccountNotFoundException();
		}
		
		return account;
	}


}
