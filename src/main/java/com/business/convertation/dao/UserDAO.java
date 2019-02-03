package com.business.convertation.dao;

import com.business.convertation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface UserDAO extends JpaRepository<User,Long> {

    @Query(value = "select u from User u where  u.accountId = :accountID")
    List<User> findByID (@Param("accountID") Long accountID);

    @Query(value = "select u from User u where u.accountId = :accountID and u.id = :transactionID")
    User findTransactionByID(@Param("accountID") Long accountID, @Param("transactionID") Long transactionID );


//    @Query(value = "select u from User u where u.date >= :dateFrom and u.date <= :dateTo")
//    List<User> findTransactionByDate(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
