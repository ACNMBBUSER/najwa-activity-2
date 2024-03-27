package com.account.account.repository;

import com.account.account.repository.entity.Account;
import com.account.account.repository.entity.AccountRelation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, AccountRelation> {

    //Account findById (Long id);

    Account findByAccountId(Long accountId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Account a WHERE a.accountId = :accountId")
    void deleteAccount(@Param("accountId") Long accountId);
}
