package com.vn.service;

import com.vn.entity.Account;
import com.vn.entity.Invoice;
import com.vn.model.InvoiceDto;
import com.vn.repository.InvoiceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;


    @Override
    public Page<InvoiceDto> findByAccount(Account account, Integer page, Integer size) {
        if (account == null) {
            return null;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("bookingDate"));

        Page<Invoice> entities = invoiceRepository.findByAccount(account, pageable);

        // convert page => page
        // transfer from pageEntity to pageDTO
        Page<InvoiceDto> result = entities.map(new java.util.function.Function<Invoice, InvoiceDto>() {

            @Override
            public InvoiceDto apply(Invoice t) {
                InvoiceDto dto = new InvoiceDto();
                BeanUtils.copyProperties(t, dto);
                return dto;
            }
        });
        return result;
    }

    @Override
    public Page<InvoiceDto> findByAccountAndMovieNameLike(Account account, String movieName, Integer page,
                                                          Integer size) {
        if (account == null) {
            return null;
        }

        String likeMovieName = "%" + movieName + "%";
        Pageable pageable = PageRequest.of(page, size, Sort.by("bookingDate"));
        Page<Invoice> pageEntity = invoiceRepository.findByAccountAndMovieNameLike(account, likeMovieName, pageable);

        // convert page => page
        // transfer from pageEntity to pageDTO

        Page<InvoiceDto> result = pageEntity.map(new java.util.function.Function<Invoice, InvoiceDto>() {

            @Override
            public InvoiceDto apply(Invoice t) {
                InvoiceDto dto = new InvoiceDto();
                BeanUtils.copyProperties(t, dto);
                return dto;
            }
        });

        return result;
    }

    @Override
    public InvoiceDto saveInvoice(InvoiceDto invoiceDto, Account account) {
        if (account == null) {
            return null;
        }
        Invoice invoice = new Invoice();

        invoice.setAddScore(invoiceDto.getAddScore());
        invoice.setBookingDate(invoiceDto.getBookingDate());
        invoice.setMovieName(invoiceDto.getMovieName());
        invoice.setScheduleShow(invoiceDto.getScheduleShow());
        invoice.setScheduleShowTime(invoiceDto.getScheduleShowTime());
        invoice.setStatus(invoiceDto.getStatus());
        invoice.setTotalMoney(invoiceDto.getTotalMoney());
        invoice.setUseScore(invoiceDto.getUseScore());
        invoice.setSeat(invoiceDto.getSeat());

        invoice.setAccount(account);

        invoiceRepository.save(invoice);

        System.out.println(invoice);
        return invoiceDto;
    }

    @Override
    public Page<InvoiceDto> getHistory(Account account, Date fromDate, Date toDate, String typeScore, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("bookingDate"));
        Page<Invoice> invoicePage = null;
        Page<InvoiceDto> result = null;
        if (fromDate != null || toDate != null) {
            invoicePage = invoiceRepository.findByAccountAndBookingDateBetween(account, fromDate, toDate, pageable);
        } else {
            invoicePage = invoiceRepository.findByAccount(account, pageable);
        }
        if (invoicePage != null) {
            result = invoicePage.map(new java.util.function.Function<Invoice, InvoiceDto>() {
                @Override
                public InvoiceDto apply(Invoice t) {
                    InvoiceDto dto = new InvoiceDto();
                    BeanUtils.copyProperties(t, dto);
                    return dto;
                }
            });
        }
        return result;
    }
}
