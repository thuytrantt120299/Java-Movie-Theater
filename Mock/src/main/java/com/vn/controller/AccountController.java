package com.vn.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.config.FileUploadUtil;
import com.vn.entity.Account;
import com.vn.model.InvoiceDto;
import com.vn.service.AccountService;
import com.vn.service.InvoiceService;
import com.vn.service.auth.CustomAccountDetails;

@Controller
@RequestMapping(value = "/accountMNG")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private InvoiceService invoiceService;

    // Update User
    @GetMapping("/information")
    public String updateForm(@AuthenticationPrincipal CustomAccountDetails loggedAccount, Model model) {

        String username = loggedAccount.getUsername();
        Account account = accountService.findByUsername(username);

        model.addAttribute("account", account);

        return "accountMNG/accountMNG-information";
    }

    @PostMapping("/information")
    public String saveDetails(@ModelAttribute("account") Account account, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal CustomAccountDetails loggedAccount,
                              @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        Account oldAccount = accountService.findById(account.getAccountId());
        if (oldAccount != null) {
            if (!StringUtils.isBlank(account.getPassword())) {
                account.setPassword(accountService.encodePassword(account.getPassword()));
            } else {
                account.setPassword(oldAccount.getPassword());
            }
        }
        if (!multipartFile.isEmpty()) {
            String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
            account.setImage(fileName);
            String uploadDir = "account-images/" + account.getAccountId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (account.getImage().isEmpty()) account.setImage(null);
        }
        accountService.save(account);
        String message = String.format("Update account successful");
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/accountMNG/information";
    }

    //Booked ticket
    @GetMapping("/bookedTicket")
    public String showBookedTickets(Model model, @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "2") Integer size) {

        //get user object
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Account account = accountService.findByUsername(username);

        Page<InvoiceDto> pageData = null;

        if (name == null || "".equals(name)) {
            pageData = invoiceService.findByAccount(account, page - 1, size);
            model.addAttribute("list", pageData);

        } else {
            pageData = invoiceService.findByAccountAndMovieNameLike(account, name, page - 1, size);
            model.addAttribute("list", pageData);
        }
        if (size != null) {
            model.addAttribute("size", size);
        }
        model.addAttribute("name", name);
        model.addAttribute("account", account);


        return "accountMNG/accountMNG-bookedticket";
    }

    //History
    @GetMapping("/history")
    public String showHistory(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                              @RequestParam(value = "fromDate", required = false) String fromDate,
                              @RequestParam(value = "toDate", required = false) String toDate,
                              @RequestParam(value = "typeScore", required = false) String typeScore) throws ParseException {
        //get user object
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        Date fDate = null;
        Date tDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isBlank(fromDate)) {
            fDate = simpleDateFormat.parse(fromDate);
        }
        if (!StringUtils.isBlank(toDate)) {
            tDate = simpleDateFormat.parse(toDate);
        }
        Account account = accountService.findByUsername(username);
        Page<InvoiceDto> pageData = invoiceService.getHistory(account, fDate, tDate, typeScore, page - 1, size);
        model.addAttribute("list", pageData);
        if (StringUtils.isBlank(typeScore) || typeScore.equals("A")) {
            model.addAttribute("isAddScore", true);
        } else {
            model.addAttribute("isAddScore", false);
        }
        model.addAttribute("account", account);
        return "accountMNG/accountMNG-history";
    }
}
