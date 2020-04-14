package com.poseidon.invoice.web.controller;

import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.invoice.web.form.InvoiceForm;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.web.form.TransactionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
//@RequestMapping("/invoice")
@SuppressWarnings("unused")
public class InvoiceController {
    private static final Log LOG = LogFactory.getLog(InvoiceController.class);
    private static final String ERROR = "error";
    private static final String LIST_INVOICE = "invoice/ListInvoice";
    private static final String INVOICE_FORM = "invoiceForm";
    private static final String INVOICE_FORM_DETAILS =
            "Inside saveInvoice method of InvoiceController, invoice Form details are %s";

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MakeService makeService;

    public void setTransactionService(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setMakeService(final MakeService makeService) {
        this.makeService = makeService;
    }

    public void setInvoiceService(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * list invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/ListInvoice.htm")
    public ModelAndView listInvoice(final InvoiceForm invoiceForm) {
        LOG.info(" Inside ListInvoice method of InvoiceController ");
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVos(invoiceVOs);
            }
        } catch (InvoiceException ex) {
            invoiceForm.setStatusMessage("Unable to list the Invoices due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
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
        LOG.info(" Inside addInvoice method of InvoiceController ");
        InvoiceVO vo = new InvoiceVO();
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
        LOG.info(String.format(INVOICE_FORM_DETAILS, invoiceForm));
        invoiceForm.getCurrentInvoiceVo().setCreatedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVo().setModifiedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVo().setCreatedDate(OffsetDateTime.now(ZoneId.systemDefault()));
        invoiceForm.getCurrentInvoiceVo().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
        try {
            TransactionVO searchTransactionVo = new TransactionVO();
            searchTransactionVo.setTagNo(invoiceForm.getCurrentInvoiceVo().getTagNo());
            searchTransactionVo.setStartswith(Boolean.TRUE);
            searchTransactionVo.setIncludes(Boolean.TRUE);
            List<TransactionVO> transactionVOs = null;
            try {
                transactionVOs = transactionService.searchTransactions(searchTransactionVo);
            } catch (TransactionException ex) {
                LOG.error(ex.getLocalizedMessage());
            }

            if (transactionVOs != null && !transactionVOs.isEmpty()) {
                invoiceService.addInvoice(invoiceForm.getCurrentInvoiceVo());
                invoiceForm.setStatusMessage("Successfully saved the new invoice Detail");
                invoiceForm.setStatusMessageType("success");
                //update the transaction
                TransactionVO transactionVo = transactionVOs.get(0);
                String status = "INVOICED";
                transactionService.updateTransactionStatus(transactionVo.getId(), status);
            } else {
                invoiceForm.setStatusMessage("Unable to find a transaction with tagNo "
                        + invoiceForm.getCurrentInvoiceVo().getTagNo());
                invoiceForm.setStatusMessageType(ERROR);
            }
        } catch (Exception ex) {
            invoiceForm.setStatusMessage("Unable to save the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVos(invoiceVOs);
            }
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVo(new InvoiceVO());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    /**
     * edit invoice.
     *
     * @param invoiceForm InvoiceForm
     * @return ModelAndView
     */
    @PostMapping("/invoice/EditInvoice.htm")
    public ModelAndView editInvoice(final InvoiceForm invoiceForm) {
        LOG.info(String.format(INVOICE_FORM_DETAILS, invoiceForm));
        InvoiceVO invoiceVo;
        try {
            invoiceVo = invoiceService.fetchInvoiceVOFromId(invoiceForm.getId());
            invoiceForm.setCurrentInvoiceVo(invoiceVo);
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
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
        LOG.info(String.format(INVOICE_FORM_DETAILS, invoiceForm));
        try {
            InvoiceVO invoiceVo = invoiceService.fetchInvoiceVOFromId(invoiceForm.getId());
            invoiceService.deleteInvoice(invoiceForm.getId());
            TransactionReportVO reportVo = transactionService.fetchTransactionFromTag(invoiceVo.getTagNo());
            //get the tag no of it
            // update the status to closed
            transactionService.updateTransactionStatus(reportVo.getId(), "CLOSED");
            invoiceForm.setStatusMessage("Successfully deleted the new invoice Detail");
            invoiceForm.setStatusMessageType("success");
        } catch (Exception ex) {
            invoiceForm.setStatusMessage("Unable to delete the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVos(invoiceVOs);
            }
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
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
        LOG.info(String.format(INVOICE_FORM_DETAILS, invoiceForm));
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.findInvoices(invoiceForm.getSearchInvoiceVo());
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVos(invoiceVOs);
            }
            invoiceForm.setStatusMessage("Found " + invoiceVOs.size() + " invoice details");
            invoiceForm.setStatusMessageType("info");
        } catch (InvoiceException ex) {
            invoiceForm.setStatusMessage("Unable to find the Invoices due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
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
        LOG.info(String.format(INVOICE_FORM_DETAILS, invoiceForm));
        invoiceForm.getCurrentInvoiceVo().setModifiedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVo().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
        try {
            invoiceService.updateInvoice(invoiceForm.getCurrentInvoiceVo());
            invoiceForm.setStatusMessage("Successfully updated the new invoice Detail");
            invoiceForm.setStatusMessageType("success");
        } catch (Exception ex) {
            invoiceForm.setStatusMessage("Unable to update the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVos(invoiceVOs);
            }
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
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
        //get the id
        TransactionVO transactionVo = null;
        try {
            transactionVo = transactionService.fetchTransactionFromId(transactionForm.getId());
            if (transactionVo != null && transactionVo.getMakeId() != null && transactionVo.getMakeId() > 0) {
                List<MakeVO> makeVOs = null;
                try {
                    makeVOs = makeService.fetchMakes();
                } catch (Exception ex) {
                    LOG.error(ex.getLocalizedMessage());
                }
                if (makeVOs != null) {
                    makeVOs.stream().map(makeVo -> "make vo is" + makeVo).forEach(LOG::info);
                    transactionForm.setMakeVOs(makeVOs);
                }
                List<MakeAndModelVO> makeAndModelVOs;
                makeAndModelVOs = makeService.getAllModelsFromMakeId(transactionVo.getMakeId());
                if (makeAndModelVOs != null) {
                    transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                    makeAndModelVOs.stream()
                            .map(makeAndModelVo -> "makeAndModel vo is" + makeAndModelVo).forEach(LOG::info);
                }
            }
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage());
            LOG.error(" Exception type in controller " + ex.exceptionType);
            if (ex.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.info(" An Unknown Error has been occurred !!");
        }
        // find tag no  and.. thus the description
        InvoiceForm invoiceForm = new InvoiceForm();
        invoiceForm.setLoggedInUser(transactionForm.getLoggedInUser());
        invoiceForm.setLoggedInRole(transactionForm.getLoggedInRole());
        InvoiceVO invoiceVo = new InvoiceVO();
        if (transactionVo != null) {
            invoiceVo.setTagNo(transactionVo.getTagNo());
            String makeName = "";
            String modelName = "";
            if (transactionForm.getMakeAndModelVOs() != null && !transactionForm.getMakeAndModelVOs().isEmpty()) {
                makeName = transactionForm.getMakeAndModelVOs().get(0).getMakeName();
                modelName = transactionForm.getMakeAndModelVOs().get(0).getModelName();
            }
            invoiceVo.setDescription("SERVICE CHARGES FOR " + makeName + " " + modelName);
        }
        invoiceForm.setCurrentInvoiceVo(invoiceVo);
        // create a invoice VO object and se that to the form
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
