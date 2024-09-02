package com.jinsu.aspectj.declaringadvice.dao;

import com.jinsu.aspectj.declaringadvice.model.Account;

public interface AccountDao {
	Account findAccountById(String id);
	void updateAccount(Account account);
}