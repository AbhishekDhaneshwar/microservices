package com.example.bank.service.impl;

import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import com.example.bank.constants.AccountsConstants;
import com.example.bank.dto.AccountsDto;
import com.example.bank.dto.CustomerDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.Customer;
import com.example.bank.exceptions.CustomerAlreadyExistsException;
import com.example.bank.exceptions.ResourceNotFoundException;
import com.example.bank.mapper.AccountsMapper;
import com.example.bank.mapper.Customermapper;
import com.example.bank.repository.AccountsRepository;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.service.IAccounsService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccounsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    // save new account, create a new customer
    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = Customermapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    // create new account
    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        long newAccountNumberCreated = 10000000000l + new Random().nextInt(900000000);
        account.setAccountNumber(newAccountNumberCreated);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }

    // get accounts details with mobile number
    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAcoount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = Customermapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or
     *         not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Account account = accountsRepository.findByCustomerId(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account ", "AccountNumber ",
                            accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccount(accountsDto, account);
            account = accountsRepository.save(account);
            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));
            Customermapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;

        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Mobile Number ", "Mobile Number  ",
                        mobileNumber));
        accountsRepository.deleteById(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
