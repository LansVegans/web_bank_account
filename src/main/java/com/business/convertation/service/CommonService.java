package com.business.convertation.service;

import com.business.convertation.entities.User;

import java.text.ParseException;
import java.util.List;


public interface CommonService {

    User openAccount(User user);
    User getInfo(Long accountID);
    User getTransactionID(Long accountID, User currentAction);
    User getTransactionInfo (Long accountID, Long transactionID);
    List<User> getTransactionList (String dateFrom, String dateTo, String currency, Long accountId) throws ParseException;

}
