package com.example.banking.service.impl;

import com.example.banking.dto.AccountDto;
import com.example.banking.entity.Account;
import com.example.banking.mapper.AccountMapper;
import com.example.banking.repository.AccountsRepository;
import com.example.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplementation implements AccountService {
    private final AccountsRepository accountsRepository;


    public AccountServiceImplementation(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount=accountsRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountsRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("account with given id "+id+" not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=accountsRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("account with given id "+id+" not found"));
        double total=account.getBalance() + amount;
        account.setBalance(total);
       Account savedAccount= accountsRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }
    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountsRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("account with given id "+id+" not found"));
        double currentBalance= account.getBalance();
        if (account.getBalance()<amount){
            throw new RuntimeException("insufficient balance for withdrawal request in your account.Your current balance is"+ " "+currentBalance+" "+ " as of now");
        }
        double total=account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount= accountsRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }
    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account>  accounts=accountsRepository.findAll();
      return  accounts.stream().map((account) ->
              AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());

    }
}
