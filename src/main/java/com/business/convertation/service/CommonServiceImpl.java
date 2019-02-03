package com.business.convertation.service;

import com.business.convertation.dao.UserDAO;
import com.business.convertation.entities.Rates;
import com.business.convertation.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    UserDAO userDAO;

    private User getLastTransaction (Long accountID){

        List<User> listUsers = new ArrayList<>(userDAO.findByID(accountID));
        return  listUsers.get(listUsers.size()-1);
    }

    private Double getRate (User userTransaction){

        if (userTransaction.getCurrency().equals("RUB"))
            return userTransaction.getAmount();

        ResponseEntity<Rates> responseEntity = new RestTemplate().getForEntity(
                "http://iss.moex.com/iss/statistics/engines/currency/markets/selt/rates.json", Rates.class
        );

        Rates currentRates = responseEntity.getBody();

        List<String> data = currentRates.getCbrf().getData().get(0);
        List<String> columns = currentRates.getCbrf().getColumns();

        Double CBRF_CURRENCY_LAST = Double.parseDouble(data.get(columns.indexOf("CBRF_" + userTransaction.getCurrency() + "_LAST")));

        Double currentAmount = CBRF_CURRENCY_LAST * userTransaction.getAmount();

        return currentAmount;
    }

    @Override
    @Transactional
    public User openAccount(User user) {

        Long hashID =(long) user.hashCode();
        user.setAccountId(hashID);
        user.setAmount(user.getBalance());
        user.setCbrfRate(1);
        user.setCurrency("RUB");
        user.setDate(new Date());
        user.setType("deposit");
        userDAO.save(user);

        return user;
    }

    @Override
    @Transactional
    public User getInfo(Long accountID) {

        return  getLastTransaction(accountID);
    }

    @Override
    @Transactional
    public User getTransactionID(Long accountID, User userTransaction) {

        List<User> listUsers = new ArrayList<>(userDAO.findByID((long)accountID));
        User currentUser =  listUsers.get(listUsers.size()-1);

        userTransaction.setBalanceBefore(currentUser.getBalance());

        if (userTransaction.getType().equals("deposit"))
            userTransaction.setBalance(new BigDecimal(currentUser.getBalance() + getRate(userTransaction)).setScale(2, RoundingMode.UP).doubleValue());

        if (userTransaction.getType().equals("withdraw"))
            userTransaction.setBalance(new BigDecimal(currentUser.getBalance() - getRate(userTransaction)).setScale(2, RoundingMode.UP).doubleValue());

        userTransaction.setBalanceAfter(userTransaction.getBalance());
        userTransaction.setCbrfRate(getRate(userTransaction) / userTransaction.getAmount());
        userTransaction.setAccountId(accountID);
        userTransaction.setDate(new Date());
        userTransaction.setName(currentUser.getName());


        userDAO.save(userTransaction);

        return userTransaction;

    }

    @Override
    public User getTransactionInfo(Long accountID, Long transactionID) {

        return userDAO.findTransactionByID(accountID,transactionID);
    }
    @Override
    public List<User> getTransactionList(String dateFrom, String dateTo, String currency, Long accountId) throws ParseException {

        Date dateFromO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateFrom);
        Date dateToO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTo);

        List<User> allTransactions = userDAO.findByID(accountId);
        List<User> sortedList;
        //But works .compareTo() ...
        if (!currency.equals("ALL")) {
            sortedList = allTransactions
                    .stream()
                    .filter(u -> (u.getDate().compareTo(dateFromO) >= 0) && (u.getDate().compareTo(dateToO) <= 0) && (u.getCurrency().equals(currency)))
                    .collect(Collectors.toList());
        }
        else{
            sortedList = allTransactions
                    .stream()
                    .filter(u -> (u.getDate().compareTo(dateFromO) >= 0) && (u.getDate().compareTo(dateToO) <= 0))
                    .collect(Collectors.toList());
        }


        return sortedList ;
    }


}
