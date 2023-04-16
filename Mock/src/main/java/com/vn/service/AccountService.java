package com.vn.service;


import com.vn.entity.Account;


public interface AccountService {


    Account registerDefaultAccount(Account account);

    Account save(Account account);

    Account findByUsername(String username);

    Account findById(Long id);

    boolean accountExists(String username);

    String encodePassword(String password);

}
