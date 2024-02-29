package com.example.banking.mapper;

import com.example.banking.dto.AccountDto;
import com.example.banking.entity.Account;

public class AccountMapper {
    //map dto to entity
    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(
              accountDto.getId(),
              accountDto.getAccountHolderName(),
              accountDto.getBalance()
        );
        return account;
    }
    //entity  to dto
    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }
}
