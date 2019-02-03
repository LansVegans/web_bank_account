package com.business.convertation.controller;

import com.business.convertation.entities.User;
import com.business.convertation.entities.view.*;
import com.business.convertation.service.CommonService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;



@RestController
@RequestMapping("user")
public class Account {

    @Autowired
    CommonService commonService;


    @JsonView({AccountID.class})
    @PostMapping( consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> openAccount(@RequestBody User user){

        return new ResponseEntity<>(commonService.openAccount(user), HttpStatus.OK);
    }
    @JsonView({AccountInfo.class})
    @GetMapping(value="{accountId}")
    public ResponseEntity<User> accountInfo (@PathVariable Long accountId){

        return new ResponseEntity<>(commonService.getInfo(accountId), HttpStatus.OK);
    }

    @JsonView({TransactionID.class})
    @PostMapping(value ="{accountId}/transaction", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getTransaction (@PathVariable Long accountId, @RequestBody User user){

        return new ResponseEntity<>(commonService.getTransactionID( accountId, user), HttpStatus.OK);
    }


    @JsonView({TransactionList.class})
    @GetMapping(value = "{accountId}/transaction")
    public ResponseEntity<List<User>> getTransactionList (@PathVariable Long accountId,
                                                          @RequestParam("dateFrom") String dateFrom,
                                                          @RequestParam("dateTo") String dateTo,
                                                          @RequestParam(value = "currency", defaultValue = "ALL") String currency) throws ParseException {


        return new ResponseEntity<List<User>>(commonService.getTransactionList(dateFrom,dateTo,currency,accountId),HttpStatus.OK);
    }

    @JsonView({TransactionInfo.class})
    @GetMapping(value = "{accountId}/transaction/{transactionId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getTransactionInfo (@PathVariable Long accountId,@PathVariable Long transactionId){


        return new ResponseEntity<>(commonService.getTransactionInfo(accountId,transactionId),HttpStatus.OK);
    }
}
