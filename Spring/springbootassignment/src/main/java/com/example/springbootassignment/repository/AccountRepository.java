package com.example.springbootassignment.repository;

import com.example.springbootassignment.entity.Account;
import com.example.springbootassignment.entity.myenum.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);

    Page<Account> findAllByFirstNameOrLastNameOrAddressOrEmailOrPhoneOrUsernameContains(String search, Pageable pageable);

    Page<Account> findAllByStatusEquals(AccountStatus status, Pageable pageable);

    Page<Account> findAllByRoleEquals(String role, Pageable pageable);

//    Page<Account> findAllByAddressContains(String address, Pageable pageable);
//
//    Account findByPhone(String phone);
//
//    Account findByEmail(String email);

//    Page<Account> findAllByCreateAt(LocalDateTime createdAt, Pageable pageable);
//
//    Page<Account> findAllByUpdateAt(LocalDateTime updatedAt, Pageable pageable);
//
//    Page<Account> findAllByDeleteAt(LocalDateTime deletedAt, Pageable pageable);

    Page<Account> findAllByCreateAtBetween(LocalDateTime min, LocalDateTime max, Pageable pageable);

    Page<Account> findAllByUpdateAtBetween(LocalDateTime min, LocalDateTime max, Pageable pageable);

    Page<Account> findAllByDeleteAtBetween(LocalDateTime min, LocalDateTime max, Pageable pageable);

    Page<Account> findAll(Pageable pageable);
}
