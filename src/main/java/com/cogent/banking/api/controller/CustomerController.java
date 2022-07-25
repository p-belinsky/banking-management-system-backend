package com.cogent.banking.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.banking.api.exception.AccountNotCreatedException;
import com.cogent.banking.api.exception.AccountNotFoundException;
import com.cogent.banking.api.exception.AccountsNotUniqueException;
import com.cogent.banking.api.exception.BeneficiaryNotAddedException;
import com.cogent.banking.api.exception.CustomerNotFoundException;
import com.cogent.banking.api.exception.InsufficentBalanceException;
import com.cogent.banking.api.model.Account;
import com.cogent.banking.api.model.Beneficiary;
import com.cogent.banking.api.model.Customer;
import com.cogent.banking.api.model.Staff;
import com.cogent.banking.api.model.Transaction;
import com.cogent.banking.api.service.CustomerService;
import com.cogent.banking.api.service.UserService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {
	
	
	private CustomerService customerService;


	
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@PostMapping("/register")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
		Customer customerToAdd = customerService.addCustomer(customer);
		return new ResponseEntity<Customer>(customerToAdd, HttpStatus.CREATED);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId) throws CustomerNotFoundException{
		Customer customer = customerService.getCustomerById(customerId);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomerById(@PathVariable int customerId, @RequestBody Customer customer) throws CustomerNotFoundException{
		Customer customerToUpdate = customerService.updateCustomerById(customerId, customer);
		return new ResponseEntity<Customer>(customerToUpdate, HttpStatus.OK);
	}
	
	@DeleteMapping("{customerId}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable int customerId){
		String message = customerService.deleteCustomerById(customerId);
		return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(){
		List<Customer>customers = customerService.getAllCustomers();
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
	
	@PutMapping("/transfer")
	public ResponseEntity<Transaction> transferFunds(@RequestBody Transaction transaction) throws InsufficentBalanceException, AccountNotFoundException, AccountsNotUniqueException{
		Transaction transactionToAdd = customerService.transferFunds(transaction);
		return new ResponseEntity<Transaction>(transactionToAdd, HttpStatus.CREATED);
	}
	
	@PostMapping("/{customerId}/beneficiary")
	public ResponseEntity<Beneficiary> addBeneficiaryToAccount(@PathVariable int customerId, @RequestBody Beneficiary benficiary) throws CustomerNotFoundException, BeneficiaryNotAddedException{
		Beneficiary addBeneficiaryToAccount = customerService.addBeneficiaryToAccount(customerId, benficiary);
		return new ResponseEntity<Beneficiary>(addBeneficiaryToAccount, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{customerId}/beneficiary")
	public ResponseEntity<List<Beneficiary>> getAllBeneficiaryByCustomerId(@PathVariable int customerId) throws CustomerNotFoundException{
		List<Beneficiary> allBeneficiaryByCustomerId = customerService.getAllBeneficiaryByCustomerId(customerId);
		return new ResponseEntity<List<Beneficiary>>(allBeneficiaryByCustomerId, HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}/beneficiary/{beneficiaryId}")
	public ResponseEntity<String> deleteBeneficiaryByBeneficiaryIdAndCustomerId(@PathVariable int customerId, @PathVariable int beneficiaryId) throws CustomerNotFoundException{
		String message = customerService.deleteBeneficiaryByBeneficiaryIdAndCustomerId(customerId, beneficiaryId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@PostMapping("/{customerId}/account")
	public ResponseEntity<Account> addAccountToCustomer(@PathVariable int customerId, @RequestBody Account account) throws CustomerNotFoundException, AccountNotCreatedException{
		Account accountToAdd = customerService.addAccountToCustomer(customerId, account);
		return new ResponseEntity<Account>(accountToAdd, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{customerId}/account")
	public ResponseEntity<List<Account>> getAllAccountsByCustomerId(@PathVariable int customerId) throws CustomerNotFoundException{
		List<Account> allAccountsByCustomerId = customerService.getAllAccountsByCustomerId(customerId);
		return new ResponseEntity<List<Account>>(allAccountsByCustomerId, HttpStatus.OK);
	}
	
	@GetMapping("/{customerId}/account/{accountId}")
	public ResponseEntity<Account> getAccountByCustomerIdAndAccountId(@PathVariable int customerId, @PathVariable int accountId) throws CustomerNotFoundException, AccountNotFoundException{
		
		Account account = customerService.getAccountByCustomerIdAndAccountId(customerId, accountId);
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	@GetMapping("/account")
	public ResponseEntity<List<Account>> getAllAccounts(){
		List<Account> allAccounts = customerService.getAllAccounts();
		return new ResponseEntity<List<Account>>(allAccounts, HttpStatus.OK);
	}
	
	@PutMapping("/{customerId}/account/{accountNo}")
	public ResponseEntity<Account> approveAccount(@PathVariable int customerId, @PathVariable int accountNo, @RequestBody Account account) throws CustomerNotFoundException, AccountNotFoundException{
		Account approveAccount = customerService.approveAccount(customerId, accountNo, account);
		return new ResponseEntity<Account>(approveAccount, HttpStatus.OK);
	}
	
	@GetMapping("/load/{username}")
	public ResponseEntity<Customer> loadCustomer(@PathVariable String username) throws CustomerNotFoundException{
		Customer customer = customerService.loadCustomerByUsername(username);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@GetMapping("/loginCustomer/username/{username}/{password}")
	public Customer loginCustomer(@PathVariable("username") String username, @PathVariable("password") String password) {
		return customerService.loginCustomer(username, password);
	}
	
	@PutMapping("/customerForgot")
	public ResponseEntity<String> customerForgotPassword(@RequestBody Customer customer) {
		return new ResponseEntity<String> (customerService.matchCustomer(customer), HttpStatus.OK);
	}
	
	@PutMapping("/api/resetPass/CUSTOMER/{username}")
	public ResponseEntity<Boolean> customerChangePass(@PathVariable("username")String username, @RequestBody String password){
		customerService.setPassword(username, password);
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
	

}
