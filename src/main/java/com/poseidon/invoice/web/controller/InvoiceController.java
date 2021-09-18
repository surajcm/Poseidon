package com.poseidon.invoice.web.controller;

import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.invoice.web.form.InvoiceForm;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.web.form.TransactionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/invoice")
@SuppressWarnings("unused")
public class InvoiceController {
    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);
    private static final String ERROR = "error";
    private static final String LIST_INVOICE = "invoice/ListInvoice";
    private static final String INVOICE_FORM = "invoiceForm";
    private static final String INVOICE_FORM_DETAILS = "Invoice Form details are {}";
    private static final String SUCCESS = "success";

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
     * @return ModelAndView
     */
    @PostMapping("/invoice/ListInvoice.htm")
    public ModelAndView listInvoice(final InvoiceForm invoiceForm) {
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
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    /**
     * add Invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/addInvoice.htm")
    public ModelAndView addInvoice(final InvoiceForm invoiceForm) {
        log.info("Inside addInvoice method of InvoiceController ");
        var vo = new InvoiceVO();
        vo.setQuantity(1);
        invoiceForm.setCurrentInvoiceVo(vo);
        return new ModelAndView("invoice/AddInvoice", INVOICE_FORM, invoiceForm);
    }

    /**
     * save invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/saveInvoice.htm")
    public ModelAndView saveInvoice(final InvoiceForm invoiceForm) {
        log.info("Inside saveInvoice method of InvoiceController ");
        log.info(INVOICE_FORM_DETAILS, invoiceForm);
        if (invoiceForm.getCurrentInvoiceVo() != null) {
            invoiceForm.getCurrentInvoiceVo().setCreatedBy(invoiceForm.getLoggedInUser());
            invoiceForm.getCurrentInvoiceVo().setModifiedBy(invoiceForm.getLoggedInUser());
        }
        var searchTransactionVo = new TransactionVO();
        if (invoiceForm.getCurrentInvoiceVo() != null) {
            searchTransactionVo.setTagNo(invoiceForm.getCurrentInvoiceVo().getTagNo());
        }
        searchTransactionVo.setStartswith(Boolean.TRUE);
        searchTransactionVo.setIncludes(Boolean.TRUE);
        var transactionVo = fetchTransactionVO(searchTransactionVo);
        if (transactionVo.isPresent()) {
            invoiceForm.getCurrentInvoiceVo().setTransactionId(transactionVo.get().getId());
            invoiceForm.getCurrentInvoiceVo().setSerialNo(transactionVo.get().getSerialNo());
            invoiceForm.getCurrentInvoiceVo().setCustomerId(transactionVo.get().getCustomerId());
            invoiceForm.getCurrentInvoiceVo().setCustomerName(transactionVo.get().getCustomerName());
            invoiceService.addInvoice(invoiceForm.getCurrentInvoiceVo());
            log.info("Successfully saved the new invoice Detail");
            invoiceForm.setStatusMessage("Successfully saved the new invoice Detail");
            invoiceForm.setStatusMessageType(SUCCESS);
            //update the transaction
            var status = "INVOICED";
            transactionService.updateTransactionStatus(transactionVo.get().getId(), status);
        } else {
            log.error("Transaction not found !!!!");
            invoiceForm.setStatusMessage("Transaction not found !!!!");
            invoiceForm.setStatusMessageType(ERROR);
        }
        log.info("fetching invoice for listing....");
        List<InvoiceVO> invoiceVOs = fetchInvoices(invoiceForm);
        invoiceForm.setSearchInvoiceVo(new InvoiceVO());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    private List<InvoiceVO> fetchInvoices(final InvoiceForm invoiceForm) {
        List<InvoiceVO> invoiceVOs = null;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVos(invoiceVOs);
            }
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return invoiceVOs;
    }

    private Optional<TransactionVO> fetchTransactionVO(final TransactionVO searchTransactionVo) {
        List<TransactionVO> transactionVOs = transactionService.searchTransactions(searchTransactionVo);
        log.info("Found transactions :  {}" , transactionVOs.size());
        Optional<TransactionVO> transactionVo = Optional.empty();
        if (!transactionVOs.isEmpty()) {
            transactionVo = Optional.of(transactionVOs.get(0));
        }
        return transactionVo;
    }

    /**
     * edit invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/EditInvoice.htm")
    public ModelAndView editInvoice(final InvoiceForm invoiceForm) {
        log.info("Inside editInvoice method of InvoiceController ");
        log.info(INVOICE_FORM_DETAILS, invoiceForm);
        Optional<InvoiceVO> invoiceVo = invoiceService.fetchInvoiceVOFromId(invoiceForm.getId());
        invoiceVo.ifPresent(invoiceForm::setCurrentInvoiceVo);
        return new ModelAndView("invoice/EditInvoice", INVOICE_FORM, invoiceForm);
    }

    /**
     * delete invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/DeleteInvoice.htm")
    public ModelAndView deleteInvoice(final InvoiceForm invoiceForm) {
        log.info("Inside deleteInvoice method of InvoiceController ");
        log.info(INVOICE_FORM_DETAILS, invoiceForm);
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
        List<InvoiceVO> invoiceVOs = fetchInvoices(invoiceForm);
        invoiceForm.setSearchInvoiceVo(new InvoiceVO());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    /**
     * search invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/SearchInvoice.htm")
    public ModelAndView searchInvoice(final InvoiceForm invoiceForm) {
        log.info("Inside searchInvoice method of InvoiceController ");
        log.info(INVOICE_FORM_DETAILS, invoiceForm);
        var invoiceVOs = invoiceService.findInvoices(invoiceForm.getSearchInvoiceVo());
        if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
            invoiceForm.setInvoiceVos(invoiceVOs);
            invoiceForm.setStatusMessage("Found " + invoiceVOs.size() + " invoice details");
        }
        invoiceForm.setStatusMessageType("info");
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    /**
     * update invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/updateInvoice.htm")
    public ModelAndView updateInvoice(final InvoiceForm invoiceForm) {
        log.info("Inside updateInvoice method of InvoiceController ");
        log.info(INVOICE_FORM_DETAILS, invoiceForm);
        if (invoiceForm.getCurrentInvoiceVo() != null) {
            invoiceForm.getCurrentInvoiceVo().setModifiedBy(invoiceForm.getLoggedInUser());
            invoiceForm.getCurrentInvoiceVo().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
        }
        try {
            invoiceService.updateInvoice(invoiceForm.getCurrentInvoiceVo());
            invoiceForm.setStatusMessage("Successfully updated the new invoice Detail");
            invoiceForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            invoiceForm.setStatusMessage("Unable to update the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            log.error(ex.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs = fetchInvoices(invoiceForm);
        invoiceForm.setSearchInvoiceVo(new InvoiceVO());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    /**
     * invoice the transaction.
     *
     * @param transactionForm TransactionForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/InvoiceTxn.htm")
    public ModelAndView invoiceTxn(final TransactionForm transactionForm) {
        log.info("Inside invoiceTxn method of InvoiceController ");
        var makeName = "";
        var modelName = "";
        TransactionVO transactionVo = transactionService.fetchTransactionFromId(transactionForm.getId());
        if (transactionVo != null && transactionVo.getMakeId() != null && transactionVo.getMakeId() > 0) {
            log.info("tag no is {}" , transactionVo.getTagNo());
            makeName = transactionVo.getMakeName();
            modelName = transactionVo.getModelName();
        }
        var invoiceForm = new InvoiceForm();
        invoiceForm.setLoggedInUser(transactionForm.getLoggedInUser());
        invoiceForm.setLoggedInRole(transactionForm.getLoggedInRole());
        var invoiceVo = new InvoiceVO();
        if (transactionVo != null) {
            invoiceVo.setTagNo(transactionVo.getTagNo());
            invoiceVo.setDescription("SERVICE CHARGES FOR " + makeName + " " + modelName);
        }
        invoiceForm.setCurrentInvoiceVo(invoiceVo);
        // create an invoice VO object and se that to the form
        return new ModelAndView("invoice/AddInvoice", INVOICE_FORM, invoiceForm);
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
}
