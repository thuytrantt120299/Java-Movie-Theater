package com.vn.service;

import com.vn.entity.Account;
import com.vn.model.InvoiceDto;
import org.springframework.data.domain.Page;

import java.util.Date;


public interface InvoiceService {

    Page<InvoiceDto> findByAccount(Account account, Integer page, Integer size);

    Page<InvoiceDto> findByAccountAndMovieNameLike(Account account, String movieName, Integer page, Integer size);

    InvoiceDto saveInvoice(InvoiceDto invoiceDto, Account account);

    Page<InvoiceDto> getHistory(Account account, Date fromDate, Date toDate, String typeScore, Integer page, Integer size);
}
