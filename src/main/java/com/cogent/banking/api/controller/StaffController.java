package com.cogent.banking.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.banking.api.exception.AccountNotFoundException;
import com.cogent.banking.api.exception.AccountsNotUniqueException;
import com.cogent.banking.api.exception.CustomerNotFoundException;
import com.cogent.banking.api.exception.InsufficentBalanceException;
import com.cogent.banking.api.exception.StatusNotChangedException;
import com.cogent.banking.api.exception.UserNameNotUniqueException;
import com.cogent.banking.api.model.Account;
import com.cogent.banking.api.model.Admin;
import com.cogent.banking.api.model.Beneficiary;
import com.cogent.banking.api.model.Customer;
import com.cogent.banking.api.model.Staff;
import com.cogent.banking.api.model.Transaction;
import com.cogent.banking.api.service.StaffService;

@RestController
public class StaffController {
	
	private StaffService staffService;
	
	
	public StaffController(StaffService staffService) {
		super();
		this.staffService = staffService;
	}


	@GetMapping("/api/staff/beneficiary")
	public ResponseEntity<List<Beneficiary>> getAllBeneficiaryNeedingApproval(){
		List<Beneficiary> allBeneficiaryNeedingApproval = staffService.getAllBeneficiaryNeedingApproval();
		return new ResponseEntity<List<Beneficiary>>(allBeneficiaryNeedingApproval, HttpStatus.OK);
	}
	
	@PutMapping("/api/staff/beneficiary")
	public ResponseEntity<Beneficiary> approveBeneficiary(@RequestBody Beneficiary beneficiary) throws StatusNotChangedException{
		Beneficiary approveBeneficiary = staffService.approveBeneficiary(beneficiary);
		return new ResponseEntity<Beneficiary>(approveBeneficiary, HttpStatus.OK);
	}

	@GetMapping("/api/staff/account/approve")
	public ResponseEntity<List<Account>> getAllAccountNeedingApproval(){
		List<Account> allAccountNeedingApproval = staffService.getAllAccountNeedingApproval();
		return new ResponseEntity<List<Account>>(allAccountNeedingApproval, HttpStatus.OK);
	}
	
	@PutMapping("/api/staff/account/approve")
	public ResponseEntity<Account> approveAccount(@RequestBody Account account) throws StatusNotChangedException{
		Account approveAccount = staffService.approveAccount(account);
		return new ResponseEntity<Account>(approveAccount, HttpStatus.OK);
	}
	
	@GetMapping("/api/staff/account/{accountNo}")
	public ResponseEntity<Account> getAccountByAccountNo(@PathVariable int accountNo) throws AccountNotFoundException{
		Account account = staffService.getAccountByAccountNumber(accountNo);
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	
	@GetMapping("/api/staff/customer")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		List<Customer> customers = staffService.getAllCustomers();
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
	
	@PutMapping("/api/staff/customer")
	public ResponseEntity<Customer> enableCustomer(@RequestBody Customer customer) throws StatusNotChangedException{
		Customer enableCustomer = staffService.enableCustomer(customer);
		return new ResponseEntity<Customer>(enableCustomer, HttpStatus.OK);
	}
	
	@GetMapping("/api/staff/customer/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId) throws CustomerNotFoundException{
		Customer customer = staffService.getCustomerById(customerId);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@PutMapping("/api/staff/transfer")
	public ResponseEntity<Transaction> transferFunds(@RequestBody Transaction transaction) throws InsufficentBalanceException, AccountNotFoundException, AccountsNotUniqueException{
		Transaction transactionToAdd = staffService.transferFunds(transaction);
		return new ResponseEntity<Transaction>(transactionToAdd, HttpStatus.CREATED);
	}
	
	@PostMapping("/api/admin/staff")
	public ResponseEntity<Object> addStaff(@RequestBody Staff staff) throws UserNameNotUniqueException {
		staffService.addStaff(staff);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@GetMapping("/api/admin/staff")
	public ResponseEntity<List<Staff>> getAllStaff(){
		List<Staff> allStaff = staffService.getAllStaff();
		return new ResponseEntity<List<Staff>>(allStaff, HttpStatus.OK);
	}
	
	@PutMapping("/api/admin/staff")
	public ResponseEntity<String> enableStaff(@RequestBody Staff staff) throws StatusNotChangedException{
		String message = staffService.enableStaff(staff);
		return new ResponseEntity<String>(message, HttpStatus.OK);	
	}
	
	@PostMapping("/api/staff")
	public ResponseEntity<String> addAdmin(@RequestBody Admin admin) throws UserNameNotUniqueException {
		staffService.addAdmin(admin);
		return new ResponseEntity<String>(HttpStatus.CREATED);	
	}
	
	//staff controller
	@GetMapping("/loginStaff/{username}/{password}")
	public ResponseEntity<Staff> loginStaff(@PathVariable("username") String username, @PathVariable("password") String password) {
		return new ResponseEntity<Staff> (staffService.loginStaff(username, password), HttpStatus.OK);
	}
	
	@GetMapping("/loginAdmin/{username}/{password}")
	public ResponseEntity<Admin> loginAdmin(@PathVariable("username") String username, @PathVariable("password") String password) {
		return new ResponseEntity<Admin> (staffService.loginAdmin(username, password), HttpStatus.OK);
	}
	
	@PutMapping("/api/staffForgot")
	public ResponseEntity<String> staffForgotPassword(@RequestBody Staff staff) {
		return new ResponseEntity<String> (staffService.matchStaff(staff), HttpStatus.OK);
	}
	
	@PutMapping("/api/adminForgot")
	public ResponseEntity<String> adminForgotPassword(@RequestBody Admin admin) {
		return new ResponseEntity<String> (staffService.matchAdmin(admin), HttpStatus.OK);
	}
	

	@GetMapping("/api/admin/{username}")
	public ResponseEntity<Admin> findAdminByUsername(@PathVariable("username") String username) {
		return new ResponseEntity<Admin> (staffService.getAdminByUsername(username), HttpStatus.OK);
	}
	
	
}
