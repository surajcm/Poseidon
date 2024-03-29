package com.poseidon.invoice.web.controller;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.invoice.web.form.InvoiceForm;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@SuppressWarnings("unused")
public class InvoiceController {
    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);
    private static final String ERROR = "error";
    private static final String LIST_INVOICE = "invoice/invoice";
    private static final String INVOICE_FORM = "invoiceForm";
    private static final String INVOICE_FORM_DETAILS = "Invoice Form details are {}";
    private static final String SUCCESS = "success";
    public static final String ERROR_OCCURRED = "Error occurred";
    public static final String ERROR_PARSING_TO_JSON = "Error parsing to json : {}";

    private final InvoiceService invoiceService;

    private final TransactionService transactionService;

    public InvoiceController(final InvoiceService invoiceService,
                             final TransactionService transactionService) {
        this.invoiceService = invoiceService;
        this.transactionService = transactionService;
    }

    /**
     * list invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return listInvoice screen
     */
    @PostMapping("/invoice/ListInvoice")
    public String listInvoice(final InvoiceForm invoiceForm, final Model model) {
        log.info("Inside ListInvoice method of InvoiceController ");
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVos(invoiceVOs);
            }
        } catch (Exception ex) {
            invoiceForm.setStatusMessage("Unable to list the Invoices due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            log.error(ex.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVo(new InvoiceVO());
        invoiceForm.setLoggedInUser(invoiceForm.getLoggedInUser());
        invoiceForm.setLoggedInRole(invoiceForm.getLoggedInRole());
        model.addAttribute(INVOICE_FORM, invoiceForm);
        return LIST_INVOICE;
    }

    @PostMapping("/invoice/saveInvoice")
    public @ResponseBody
    List<InvoiceVO> saveInvoice(@ModelAttribute("addTagNumber") final String addTagNumber,
                                @ModelAttribute("addDescription") final String addDescription,
                                @ModelAttribute("addQuantity") final String addQuantity,
                                @ModelAttribute("addRate") final String addRate,
                                @ModelAttribute("addAmount") final String addAmount,
                                final BindingResult result) {
        //todo:error handling
        log.info("saveInvoice method of invoice controller ");
        try {
            var invoiceVO = populateInvoiceVO(addTagNumber, addDescription, addQuantity, addRate, addAmount);
            var id = invoiceService.addInvoice(invoiceVO);
            //update the transaction
            var status = "INVOICED";
            transactionService.updateTransactionStatus(id, status);
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex);
        }
        return invoiceService.fetchInvoiceForListOfTransactions();
    }

    @PostMapping("/invoice/saveInvoiceForTxn")
    public @ResponseBody
    List<TransactionVO> saveInvoiceForTxn(@ModelAttribute("addTagNumber") final String addTagNumber,
                                          @ModelAttribute("addDescription") final String addDescription,
                                          @ModelAttribute("addQuantity") final String addQuantity,
                                          @ModelAttribute("addRate") final String addRate,
                                          @ModelAttribute("addAmount") final String addAmount,
                                          final BindingResult result) {
        //todo:error handling
        log.info("saveInvoiceForTxn method of invoice controller ");
        try {
            var invoiceVO = populateInvoiceVO(addTagNumber, addDescription, addQuantity, addRate, addAmount);
            var id = invoiceService.addInvoice(invoiceVO);
            //update the transaction
            var status = "INVOICED";
            transactionService.updateTransactionStatus(id, status);
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex);
        }
        return transactionService.listAllTransactions();
    }

    private InvoiceVO populateInvoiceVO(final String addTagNumber,
                                        final String addDescription,
                                        final String addQuantity,
                                        final String addRate,
                                        final String addAmount) {
        var invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo(addTagNumber);
        invoiceVO.setDescription(addDescription);
        invoiceVO.setQuantity(Integer.parseInt(addQuantity));
        invoiceVO.setRate(Double.valueOf(addRate));
        invoiceVO.setAmount(Double.valueOf(addAmount));
        var userName = findLoggedInUsername();
        invoiceVO.setCreatedBy(userName);
        invoiceVO.setModifiedBy(userName);
        return invoiceVO;
    }

    @GetMapping("/invoice/tagNumbers")
    public @ResponseBody
    List<String> tagNumbers() {
        return invoiceService.allTagNumbers();
    }

    @GetMapping("/invoice/getForEdit")
    public @ResponseBody
    InvoiceVO getForEdit(@ModelAttribute("id") final String id,
                         final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        log.info("getForEdit method of user controller : {}", sanitizedId);
        return invoiceService.fetchInvoiceVOFromId(
                Long.valueOf(id)).orElse(null);
    }

    /**
     * delete invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return listInvoice screen
     */
    @PostMapping("/invoice/DeleteInvoice")
    public String deleteInvoice(final InvoiceForm invoiceForm, final Model model) {
        log.info("Inside deleteInvoice method of InvoiceController ");
        var sanitizedForm = CommonUtils.sanitizedString(invoiceForm.toString());
        log.info(INVOICE_FORM_DETAILS, sanitizedForm);
        try {
            var invoiceVo = invoiceService.fetchInvoiceVOFromId(invoiceForm.getId());
            invoiceService.deleteInvoice(invoiceForm.getId());
            if (invoiceVo.isPresent()) {
                TransactionReportVO reportVo = transactionService.fetchTransactionFromTag(invoiceVo.get().getTagNo());
                //get the tag no of it
                // update the status to closed
                transactionService.updateTransactionStatus(reportVo.getId(), "CLOSED");
            }
            invoiceForm.setStatusMessage("Successfully deleted the new invoice Detail");
            invoiceForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            invoiceForm.setStatusMessage("Unable to delete the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            log.error(ex.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVo(new InvoiceVO());
        return listInvoice(invoiceForm, model);
    }

    /**
     * search invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return listInvoice screen
     */
    @PostMapping("/invoice/SearchInvoice")
    public String searchInvoice(final InvoiceForm invoiceForm, final Model model) {
        log.info("Inside searchInvoice method of InvoiceController ");
        var sanitizedForm = CommonUtils.sanitizedString(invoiceForm.toString());
        log.info(INVOICE_FORM_DETAILS, sanitizedForm);
        var invoiceVOs = invoiceService.findInvoices(invoiceForm.getSearchInvoiceVo());
        if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
            invoiceForm.setInvoiceVos(invoiceVOs);
            invoiceForm.setStatusMessage("Found " + invoiceVOs.size() + " invoice details");
        }
        invoiceForm.setStatusMessageType("info");
        model.addAttribute(INVOICE_FORM, invoiceForm);
        return LIST_INVOICE;
    }

    @PutMapping("/invoice/updateInvoice")
    public @ResponseBody
    List<InvoiceVO> updateInvoice(@ModelAttribute("id") final Long id,
                                  @ModelAttribute("addTagNumber") final String addTagNumber,
                                  @ModelAttribute("addDescription") final String addDescription,
                                  @ModelAttribute("addQuantity") final String addQuantity,
                                  @ModelAttribute("addRate") final String addRate,
                                  @ModelAttribute("addAmount") final String addAmount,
                                  final BindingResult result) {
        //todo:error handling
        log.info("updateInvoice method of invoice controller ");
        try {
            var invoiceVO = populateInvoiceVO(addTagNumber, addDescription, addQuantity, addRate, addAmount);
            invoiceVO.setId(id);
            invoiceService.updateInvoice(invoiceVO);
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex);
        }
        return invoiceService.fetchInvoiceForListOfTransactions();
    }

    @GetMapping("/invoice/addInvoice")
    public @ResponseBody
    InvoiceVO getInvoiceOfTransaction(final Long id) {
        var makeName = "";
        var modelName = "";
        var transactionVo = transactionService.fetchTransactionFromId(id);
        if (transactionVo != null && transactionVo.getMakeId() != null && transactionVo.getMakeId() > 0) {
            log.info("tag no is {}", transactionVo.getTagNo());
            makeName = transactionVo.getMakeName();
            modelName = transactionVo.getModelName();
        }
        var invoiceVo = new InvoiceVO();
        if (transactionVo != null) {
            invoiceVo.setTagNo(transactionVo.getTagNo());
            invoiceVo.setDescription("SERVICE CHARGES FOR " + makeName + " " + modelName);
        }
        return invoiceVo;
    }


    /**
     * This is for avoiding errors when entering double fields.
     *
     * @param webDataBinder webDataBinder
     */
    @InitBinder
    public void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
    }

    private String findLoggedInUsername() {
        String username = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User user) {
                username = user.getUsername();
            }
        }
        return username;
    }

    private List<InvoiceVO> fetchInvoices(final InvoiceForm invoiceForm) {
        var invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
        if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
            invoiceForm.setInvoiceVos(invoiceVOs);
        }
        return invoiceVOs;
    }

    private Optional<TransactionVO> fetchTransactionVO(final TransactionVO searchTransactionVo) {
        var transactionVOs = transactionService.searchTransactions(searchTransactionVo);
        log.info("Found transactions :  {}", transactionVOs.size());
        Optional<TransactionVO> transactionVo = Optional.empty();
        if (!transactionVOs.isEmpty()) {
            transactionVo = Optional.of(transactionVOs.get(0));
        }
        return transactionVo;
    }
}
