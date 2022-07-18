package com.cogent.banking.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cogent.banking.api.enums.Status;
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
import com.cogent.banking.api.repo.AccountRepository;
import com.cogent.banking.api.repo.AdminRepository;
import com.cogent.banking.api.repo.BeneficiaryRepository;
import com.cogent.banking.api.repo.CustomerRepository;
import com.cogent.banking.api.repo.StaffRepository;
import com.cogent.banking.api.repo.TransactionRepository;

@Service
public class StaffServiceImpl implements StaffService {

	private StaffRepository staffRepository;
	private BeneficiaryRepository beneficiaryRepository;
	private AccountRepository accountRepository;
	private CustomerRepository customerRepository;
	private TransactionRepository transactionRepository;
	private AdminRepository adminRepository;
	private PasswordEncoder passwordEncoder;





	public StaffServiceImpl(StaffRepository staffRepository, BeneficiaryRepository beneficiaryRepository,
			AccountRepository accountRepository, CustomerRepository customerRepository,
			TransactionRepository transactionRepository, AdminRepository adminRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.staffRepository = staffRepository;
		this.beneficiaryRepository = beneficiaryRepository;
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void addStaff(Staff staff) throws UserNameNotUniqueException {

		List<Staff> allStaff = staffRepository.findAll();
		for(int i = 0; i < allStaff.size(); i++) {
			try {
			if(allStaff.get(i).getUsername().equalsIgnoreCase(staff.getUsername())) {
				throw new Exception();
			}
			}catch (Exception e) {
				
				throw new UserNameNotUniqueException();
			}
			
			}

		staff.setPassword(passwordEncoder.encode(staff.getPassword()));
		staffRepository.save(staff);

	}

	@Override
	public List<Beneficiary> getAllBeneficiaryNeedingApproval() {

		List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
		List<Beneficiary> needApproval = new ArrayList<>();
		for (int i = 0; i < beneficiaries.size(); i++) {
			if (beneficiaries.get(i).getStatus() == Status.DISABLE) {
				needApproval.add(beneficiaries.get(i));
			}
		}

		return needApproval;
	}

	@Override
	public Beneficiary approveBeneficiary(Beneficiary beneficiary) throws StatusNotChangedException {
		
		try {
		Beneficiary beneficiaryToApprove = beneficiaryRepository.findById(beneficiary.getBeneficiaryId()).get();
		beneficiaryToApprove.setStatus(beneficiary.getStatus());
		beneficiaryRepository.save(beneficiaryToApprove);
		return beneficiaryToApprove;
		}
		catch(Exception e) {
			throw new StatusNotChangedException();
		}
	}

	@Override
	public List<Account> getAllAccountNeedingApproval() {
		List<Account> accounts = accountRepository.findAll();
		List<Account> needApproval = new ArrayList<>();
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountStaus() == Status.DISABLE) {
				needApproval.add(accounts.get(i));
			}
		}

		return needApproval;
	}

	@Override
	public Account approveAccount(Account account) throws StatusNotChangedException {
		
		try {
		Account accountToApprove = accountRepository.findById(account.getAccountId()).get();
		accountToApprove.setAccountStaus(account.getAccountStaus());
		accountToApprove.setStaffUsername(account.getStaffUsername());
		accountRepository.save(accountToApprove);
		return accountToApprove;
		}
		catch(Exception e) {
			throw new StatusNotChangedException();
		}
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();

		return customers;
	}

	@Override
	public Customer enableCustomer(Customer customer) throws StatusNotChangedException {
		
		try {
		Customer customerToEnable = customerRepository.findById(customer.getUserId()).get();
		customerToEnable.setStatus(customer.getStatus());
		customerRepository.save(customerToEnable);
		return customerToEnable;
		}
		catch(Exception e) {
			throw new StatusNotChangedException();
		}
	}

	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {

		try {
			Customer customer = customerRepository.findById(customerId).get();
			if (customer != null)
				return customer;
		} catch (NoSuchElementException e) {
			throw new CustomerNotFoundException();
		}
		return null;
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
			if (fromAccountNo == toAccountNo) {
				throw new Exception();
			}

		} catch (Exception e) {
			throw new AccountsNotUniqueException();
		}

		try {

			fromAccount = getAccountByAccountNumber(fromAccountNo);
			toAccount = getAccountByAccountNumber(toAccountNo);

			if (fromAccountNo != fromAccount.getAccountNo() || toAccountNo != toAccount.getAccountNo()) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new AccountNotFoundException();
		}

		try {

			double fromAccountBalance = fromAccount.getAccountBalance();
			double toAccountBalance = toAccount.getAccountBalance();
			double amount = transaction.getAmount();
			if (fromAccountBalance < amount) {
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

		} catch (Exception e) {
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
	public List<Staff> getAllStaff() {
		List<Staff> allStaff = staffRepository.findAll();
		return allStaff;
	}

	@Override
	public String enableStaff(Staff staff) throws StatusNotChangedException {
		
		try {
			Staff staffToUpdate = staffRepository.findById(staff.getUserId()).get();
			staffToUpdate.setStatus(staff.getStatus());
			staffRepository.save(staffToUpdate);	
			return staffToUpdate.getUsername().toUpperCase()+ " Status Changed";
		} catch(Exception e) {
			throw new StatusNotChangedException();
		}
	
		

	}

	@Override
	public void addAdmin(Admin admin) throws UserNameNotUniqueException {
		List<Admin> allAdmin = adminRepository.findAll();
		for(int i = 0; i < allAdmin.size(); i++) {
			try {
			if(allAdmin.get(i).getUsername().equalsIgnoreCase(admin.getUsername())) {
				throw new Exception();
			}
			}catch (Exception e) {
				
				throw new UserNameNotUniqueException();
			}
			
			}
		
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			adminRepository.save(admin);
		
	}

}
