package com.vn.repository;

import com.vn.entity.Account;
import com.vn.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByAccount(Account account, Pageable pageable);

    Page<Invoice> findByAccountAndMovieNameLike(Account account, String movieName, Pageable pageable);

    Page<Invoice> findByAccountAndBookingDateBetween(Account account, Date fromDate, Date toDate, Pageable pageable);
}
