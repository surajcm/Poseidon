package com.poseidon.Invoice.web.controller;

import com.poseidon.Invoice.delegate.InvoiceDelegate;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Invoice.web.form.InvoiceForm;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.service.MakeService;
import com.poseidon.Transaction.domain.TransactionReportVO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;
import com.poseidon.Transaction.service.TransactionService;
import com.poseidon.Transaction.web.form.TransactionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 9:56 PM
 */
@Controller
public class InvoiceController {
    private final Log LOG = LogFactory.getLog(InvoiceController.class);

    private InvoiceDelegate invoiceDelegate;

    private TransactionService transactionService;

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    private MakeService makeService;

    public void setMakeService(MakeService makeService) {
        this.makeService = makeService;
    }

    public InvoiceDelegate getInvoiceDelegate() {
        return invoiceDelegate;
    }

    public void setInvoiceDelegate(InvoiceDelegate invoiceDelegate) {
        this.invoiceDelegate = invoiceDelegate;
    }

    @RequestMapping(value = "/invoice/ListInvoice.htm", method = RequestMethod.POST)
    public ModelAndView ListInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside ListInvoice method of InvoiceController ");
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if (invoiceVOs != null && invoiceVOs.size() > 0) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException | TransactionException e) {
            invoiceForm.setStatusMessage("Unable to list the Invoices due to an error");
            invoiceForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        invoiceForm.setLoggedInUser(invoiceForm.getLoggedInUser());
        invoiceForm.setLoggedInRole(invoiceForm.getLoggedInRole());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    @RequestMapping(value = "/invoice/addInvoice.htm", method = RequestMethod.POST)
    public ModelAndView addInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside addInvoice method of InvoiceController ");
        InvoiceVO vo = new InvoiceVO();
        vo.setQuantity(1);
        invoiceForm.setCurrentInvoiceVO(vo);
        return new ModelAndView("invoice/AddInvoice", "invoiceForm", invoiceForm);
    }

    @RequestMapping(value = "/invoice/saveInvoice.htm", method = RequestMethod.POST)
    public ModelAndView saveInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside saveInvoice method of InvoiceController ");
        LOG.info(" Invoice Form details are " + invoiceForm);
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


            if (transactionVOs != null && transactionVOs.size() > 0) {
                getInvoiceDelegate().addInvoice(invoiceForm.getCurrentInvoiceVO());
                invoiceForm.setStatusMessage("Successfully saved the new Invoice Detail");
                invoiceForm.setStatusMessageType("success");
                //update the transaction
                TransactionVO transactionVO = transactionVOs.get(0);
                String status = "INVOICED";
                transactionService.updateTransactionStatus(transactionVO.getId(), status);
            } else {
                invoiceForm.setStatusMessage("Unable to find a transaction with tagNo " + invoiceForm.getCurrentInvoiceVO().getTagNo());
                invoiceForm.setStatusMessageType("error");
            }
        } catch (Exception e) {
            invoiceForm.setStatusMessage("Unable to save the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if (invoiceVOs != null && invoiceVOs.size() > 0) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException | TransactionException e) {
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    @RequestMapping(value = "/invoice/EditInvoice.htm", method = RequestMethod.POST)
    public ModelAndView EditInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside EditInvoice method of InvoiceController ");
        LOG.info(" Invoice Form details are " + invoiceForm);
        InvoiceVO invoiceVO;
        try {
            invoiceVO = getInvoiceDelegate().fetchInvoiceVOFromId(invoiceForm.getId());
            invoiceForm.setCurrentInvoiceVO(invoiceVO);
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return new ModelAndView("invoice/EditInvoice", "invoiceForm", invoiceForm);
    }

    @RequestMapping(value = "/invoice/DeleteInvoice.htm", method = RequestMethod.POST)
    public ModelAndView DeleteInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside DeleteInvoice method of InvoiceController ");
        LOG.info(" Invoice Form details are " + invoiceForm);
        try {
            InvoiceVO invoiceVO = getInvoiceDelegate().fetchInvoiceVOFromId(invoiceForm.getId());
            getInvoiceDelegate().deleteInvoice(invoiceForm.getId());
            TransactionReportVO reportVO = transactionService.fetchTransactionFromTag(invoiceVO.getTagNo());
            //get the tag no of it
            // update the status to closed
            transactionService.updateTransactionStatus(reportVO.getId(), "CLOSED");
            invoiceForm.setStatusMessage("Successfully deleted the new Invoice Detail");
            invoiceForm.setStatusMessageType("success");
        } catch (Exception e) {
            invoiceForm.setStatusMessage("Unable to delete the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if (invoiceVOs != null && invoiceVOs.size() > 0) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException | TransactionException e) {
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    @RequestMapping(value = "/invoice/SearchInvoice.htm", method = RequestMethod.POST)
    public ModelAndView SearchInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside SearchInvoice method of InvoiceController ");
        LOG.info(" Invoice Form details are " + invoiceForm);
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = getInvoiceDelegate().findInvoices(invoiceForm.getSearchInvoiceVO());
            if (invoiceVOs != null && invoiceVOs.size() > 0) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
            invoiceForm.setStatusMessage("Found " + invoiceVOs.size() + " Invoice details");
            invoiceForm.setStatusMessageType("info");
        } catch (InvoiceException e) {
            invoiceForm.setStatusMessage("Unable to find the Invoices due to an error");
            invoiceForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    @RequestMapping(value = "/invoice/updateInvoice.htm", method = RequestMethod.POST)
    public ModelAndView updateInvoice(InvoiceForm invoiceForm) {
        LOG.info(" Inside updateInvoice method of InvoiceController ");
        LOG.info(" Invoice Form details are " + invoiceForm);
        invoiceForm.getCurrentInvoiceVO().setModifiedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVO().setModifiedDate(new Date());
        try {
            getInvoiceDelegate().updateInvoice(invoiceForm.getCurrentInvoiceVO());
            invoiceForm.setStatusMessage("Successfully updated the new Invoice Detail");
            invoiceForm.setStatusMessageType("success");
        } catch (Exception e) {
            invoiceForm.setStatusMessage("Unable to update the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if (invoiceVOs != null && invoiceVOs.size() > 0) {
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        } catch (InvoiceException | TransactionException e) {
            LOG.error(e.getLocalizedMessage());
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    @RequestMapping(value = "/invoice/InvoiceTxn.htm", method = RequestMethod.POST)
    public ModelAndView InvoiceTxn(TransactionForm transactionForm) {
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
            if (transactionForm.getMakeAndModelVOs() != null && transactionForm.getMakeAndModelVOs().size() > 0) {
                makeName = transactionForm.getMakeAndModelVOs().get(0).getMakeName();
                modelName = transactionForm.getMakeAndModelVOs().get(0).getModelName();
            }
            invoiceVO.setDescription("SERVICE CHARGES FOR " + makeName + " " + modelName);
        }
        invoiceForm.setCurrentInvoiceVO(invoiceVO);
        // create a invoice VO object and se that to the form
        return new ModelAndView("invoice/AddInvoice", "invoiceForm", invoiceForm);
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
