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

import java.util.Date;
import java.util.List;

/**
 * user: Suraj
 * Date: 7/26/12
 * Time: 9:56 PM
 */
@Controller
//@RequestMapping("/invoice")
@SuppressWarnings("unused")
public class InvoiceController {
    private static final Log LOG = LogFactory.getLog(InvoiceController.class);
    private static final String ERROR = "error";
    private static final String LIST_INVOICE = "invoice/ListInvoice";
    private static final String INVOICE_FORM = "invoiceForm";

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MakeService makeService;

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setMakeService(MakeService makeService) {
        this.makeService = makeService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(value = "/invoice/ListInvoice.htm")
    public ModelAndView listInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside ListInvoice method of InvoiceController ");
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException e) {
            invoiceForm.setStatusMessage("Unable to list the Invoices due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        invoiceForm.setLoggedInUser(invoiceForm.getLoggedInUser());
        invoiceForm.setLoggedInRole(invoiceForm.getLoggedInRole());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    @PostMapping(value = "/invoice/addInvoice.htm")
    public ModelAndView addInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside addInvoice method of InvoiceController ");
        InvoiceVO vo = new InvoiceVO();
        vo.setQuantity(1);
        invoiceForm.setCurrentInvoiceVO(vo);
        return new ModelAndView("invoice/AddInvoice", INVOICE_FORM, invoiceForm);
    }

    @PostMapping(value = "/invoice/saveInvoice.htm")
    public ModelAndView saveInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside saveInvoice method of InvoiceController ");
        LOG.info(" invoice Form details are " + invoiceForm);
        invoiceForm.getCurrentInvoiceVO().setCreatedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVO().setModifiedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVO().setCreatedDate(new Date());
        invoiceForm.getCurrentInvoiceVO().setModifiedDate(new Date());
        try {
            TransactionVO searchTransactionVO = new TransactionVO();
            searchTransactionVO.setTagNo(invoiceForm.getCurrentInvoiceVO().getTagNo());
            searchTransactionVO.setStartswith(Boolean.TRUE);
            searchTransactionVO.setIncludes(Boolean.TRUE);
            List<TransactionVO> transactionVOs = null;
            try {
                transactionVOs = transactionService.searchTransactions(searchTransactionVO);
            } catch (TransactionException e) {
                LOG.error(e.getLocalizedMessage());
            }


            if (transactionVOs != null && !transactionVOs.isEmpty()) {
                invoiceService.addInvoice(invoiceForm.getCurrentInvoiceVO());
                invoiceForm.setStatusMessage("Successfully saved the new invoice Detail");
                invoiceForm.setStatusMessageType("success");
                //update the transaction
                TransactionVO transactionVO = transactionVOs.get(0);
                String status = "INVOICED";
                transactionService.updateTransactionStatus(transactionVO.getId(), status);
            } else {
                invoiceForm.setStatusMessage("Unable to find a transaction with tagNo " + invoiceForm.getCurrentInvoiceVO().getTagNo());
                invoiceForm.setStatusMessageType(ERROR);
            }
        } catch (Exception e) {
            invoiceForm.setStatusMessage("Unable to save the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    @PostMapping(value = "/invoice/EditInvoice.htm")
    public ModelAndView editInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside EditInvoice method of InvoiceController ");
        LOG.info(" invoice Form details are " + invoiceForm);
        InvoiceVO invoiceVO;
        try {
            invoiceVO = invoiceService.fetchInvoiceVOFromId(invoiceForm.getId());
            invoiceForm.setCurrentInvoiceVO(invoiceVO);
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return new ModelAndView("invoice/EditInvoice", INVOICE_FORM, invoiceForm);
    }

    @PostMapping(value = "/invoice/DeleteInvoice.htm")
    public ModelAndView deleteInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside DeleteInvoice method of InvoiceController ");
        LOG.info(" invoice Form details are " + invoiceForm);
        try {
            InvoiceVO invoiceVO = invoiceService.fetchInvoiceVOFromId(invoiceForm.getId());
            invoiceService.deleteInvoice(invoiceForm.getId());
            TransactionReportVO reportVO = transactionService.fetchTransactionFromTag(invoiceVO.getTagNo());
            //get the tag no of it
            // update the status to closed
            transactionService.updateTransactionStatus(reportVO.getId(), "CLOSED");
            invoiceForm.setStatusMessage("Successfully deleted the new invoice Detail");
            invoiceForm.setStatusMessageType("success");
        } catch (Exception e) {
            invoiceForm.setStatusMessage("Unable to delete the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    @PostMapping(value = "/invoice/SearchInvoice.htm")
    public ModelAndView searchInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside SearchInvoice method of InvoiceController ");
        LOG.info(" invoice Form details are " + invoiceForm);
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.findInvoices(invoiceForm.getSearchInvoiceVO());
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
            invoiceForm.setStatusMessage("Found " + invoiceVOs.size() + " invoice details");
            invoiceForm.setStatusMessageType("info");
        } catch (InvoiceException e) {
            invoiceForm.setStatusMessage("Unable to find the Invoices due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
        }
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    @PostMapping(value = "/invoice/updateInvoice.htm")
    public ModelAndView updateInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside updateInvoice method of InvoiceController ");
        LOG.info(" invoice Form details are " + invoiceForm);
        invoiceForm.getCurrentInvoiceVO().setModifiedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVO().setModifiedDate(new Date());
        try {
            invoiceService.updateInvoice(invoiceForm.getCurrentInvoiceVO());
            invoiceForm.setStatusMessage("Successfully updated the new invoice Detail");
            invoiceForm.setStatusMessageType("success");
        } catch (Exception e) {
            invoiceForm.setStatusMessage("Unable to update the invoice due to an error");
            invoiceForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceService.fetchInvoiceForListOfTransactions();
            if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView(LIST_INVOICE, INVOICE_FORM, invoiceForm);
    }

    @PostMapping(value = "/invoice/InvoiceTxn.htm")
    public ModelAndView invoiceTxn(TransactionForm transactionForm) {
        //get the id
        TransactionVO transactionVO = null;
        try {
            transactionVO = transactionService.fetchTransactionFromId(transactionForm.getId());
            if (transactionVO != null && transactionVO.getMakeId() != null && transactionVO.getMakeId() > 0) {
                List<MakeVO> makeVOs = null;
                try {
                    makeVOs = makeService.fetchMakes();
                } catch (Exception e) {
                    LOG.error(e.getLocalizedMessage());
                }
                if (makeVOs != null) {
                    for (MakeVO makeVO : makeVOs) {
                        LOG.info("make vo is" + makeVO);
                    }
                    transactionForm.setMakeVOs(makeVOs);
                }
                List<MakeAndModelVO> makeAndModelVOs;
                makeAndModelVOs = makeService.getAllModelsFromMakeId(transactionVO.getMakeId());
                if (makeAndModelVOs != null) {
                    transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                    for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                        LOG.info("makeAndModel vo is" + makeAndModelVO);
                    }
                }
            }
        } catch (TransactionException e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
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
        InvoiceVO invoiceVO = new InvoiceVO();
        if (transactionVO != null) {
            invoiceVO.setTagNo(transactionVO.getTagNo());
            String makeName = "";
            String modelName = "";
            if (transactionForm.getMakeAndModelVOs() != null && !transactionForm.getMakeAndModelVOs().isEmpty()) {
                makeName = transactionForm.getMakeAndModelVOs().get(0).getMakeName();
                modelName = transactionForm.getMakeAndModelVOs().get(0).getModelName();
            }
            invoiceVO.setDescription("SERVICE CHARGES FOR " + makeName + " " + modelName);
        }
        invoiceForm.setCurrentInvoiceVO(invoiceVO);
        // create a invoice VO object and se that to the form
        return new ModelAndView("invoice/AddInvoice", INVOICE_FORM, invoiceForm);
    }

    /**
     * This is for avoiding errors when entering double fields
     *
     * @param webDataBinder webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
    }
}
