package com.vn.service;


import com.vn.entity.Account;
import com.vn.entity.Role;
import com.vn.repository.AccountRepository;
import com.vn.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Account registerDefaultAccount(Account account) {
        Role roleAccount = roleRepository.findByName("Member");
        account.setRole(roleAccount);
        account.setPassword(encodePassword(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account save(Account account) {
        //encodePassword(account);
        return accountRepository.save(account);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public boolean accountExists(String username) {

        if (findByUsername(username) == null) {
            return false;
        }

        return true;
    }

    @Override
    public Account findById(Long id) {

        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
