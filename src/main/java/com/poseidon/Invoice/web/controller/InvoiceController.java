package com.poseidon.Invoice.web.controller;

import com.poseidon.Invoice.delegate.InvoiceDelegate;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Invoice.web.form.InvoiceForm;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Transaction.delegate.TransactionDelegate;
import com.poseidon.Transaction.domain.TransactionReportVO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;
import com.poseidon.Transaction.web.form.TransactionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 9:56 PM
 */
public class InvoiceController extends MultiActionController {
    private final Log log = LogFactory.getLog(InvoiceController.class);

    private InvoiceDelegate invoiceDelegate;

    private TransactionDelegate transactionDelegate;

    private MakeDelegate makeDelegate;

    public InvoiceDelegate getInvoiceDelegate() {
        return invoiceDelegate;
    }

    public void setInvoiceDelegate(InvoiceDelegate invoiceDelegate) {
        this.invoiceDelegate = invoiceDelegate;
    }

    public TransactionDelegate getTransactionDelegate() {
        return transactionDelegate;
    }

    public void setTransactionDelegate(TransactionDelegate transactionDelegate) {
        this.transactionDelegate = transactionDelegate;
    }

    public MakeDelegate getMakeDelegate() {
        return makeDelegate;
    }

    public void setMakeDelegate(MakeDelegate makeDelegate) {
        this.makeDelegate = makeDelegate;
    }

    public ModelAndView ListInvoice(HttpServletRequest request,
                                    HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside ListInvoice method of InvoiceController ");
        List<InvoiceVO> invoiceVOs;
        try{
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if(invoiceVOs != null && invoiceVOs.size() >0){
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        }catch (InvoiceException e){
            invoiceForm.setStatusMessage("Unable to list the Invoices due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        } catch (TransactionException e) {
            invoiceForm.setStatusMessage("Unable to list the Invoices due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        invoiceForm.setLoggedInUser(invoiceForm.getLoggedInUser());
        invoiceForm.setLoggedInRole(invoiceForm.getLoggedInRole());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView addInvoice(HttpServletRequest request,
                                    HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside addInvoice method of InvoiceController ");
        invoiceForm.setCurrentInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/AddInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView saveInvoice(HttpServletRequest request,
                                    HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside saveInvoice method of InvoiceController ");
        log.info(" Invoice Form details are " + invoiceForm);
        invoiceForm.getCurrentInvoiceVO().setCreatedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVO().setModifiedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVO().setCreatedDate(new Date());
        invoiceForm.getCurrentInvoiceVO().setModifiedDate(new Date());
        try{
            TransactionVO searchTransactionVO = new TransactionVO();
            searchTransactionVO.setTagNo(invoiceForm.getCurrentInvoiceVO().getTagNo());
            searchTransactionVO.setStartswith(Boolean.TRUE);
            searchTransactionVO.setIncludes(Boolean.TRUE);
            List<TransactionVO> transactionVOs = null;
            try {
                transactionVOs = getTransactionDelegate().searchTransactions(searchTransactionVO);
            }catch (TransactionException e){
                e.printStackTrace();
            }


            if(transactionVOs != null && transactionVOs.size() > 0){
                getInvoiceDelegate().addInvoice(invoiceForm.getCurrentInvoiceVO());
                invoiceForm.setStatusMessage("Successfully saved the new Invoice Detail");
                invoiceForm.setStatusMessageType("success");
                //update the transaction
                TransactionVO transactionVO = transactionVOs.get(0);
                String status = "INVOICED";
                getTransactionDelegate().updateTransactionStatus(transactionVO.getId(),status);
            }else{
                invoiceForm.setStatusMessage("Unable to find a transaction with tagNo " + invoiceForm.getCurrentInvoiceVO().getTagNo());
                invoiceForm.setStatusMessageType("error");
            }
        }catch (InvoiceException e){
            invoiceForm.setStatusMessage("Unable to save the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }catch (TransactionException e){
            invoiceForm.setStatusMessage("Unable to save the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }catch (Exception e){
            invoiceForm.setStatusMessage("Unable to save the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        List<InvoiceVO> invoiceVOs;
        try{
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if(invoiceVOs != null && invoiceVOs.size() >0){
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        }catch (InvoiceException e){
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView EditInvoice(HttpServletRequest request,
                                      HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside EditInvoice method of InvoiceController ");
        log.info(" Invoice Form details are " + invoiceForm);
        InvoiceVO invoiceVO;
        try {
            invoiceVO = getInvoiceDelegate().fetchInvoiceVOFromId(invoiceForm.getId());
            invoiceForm.setCurrentInvoiceVO(invoiceVO);
        }catch (InvoiceException e){
            e.printStackTrace();
        }
        return new ModelAndView("invoice/EditInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView DeleteInvoice(HttpServletRequest request,
                                      HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside DeleteInvoice method of InvoiceController ");
        log.info(" Invoice Form details are " + invoiceForm);
        try {
            InvoiceVO invoiceVO = getInvoiceDelegate().fetchInvoiceVOFromId(invoiceForm.getId());
            getInvoiceDelegate().deleteInvoice(invoiceForm.getId());
            TransactionReportVO reportVO = getTransactionDelegate().fetchTransactionFromTag(invoiceVO.getTagNo());
            //get the tag no of it
            // update the status to closed
            getTransactionDelegate().updateTransactionStatus(reportVO.getId(),"CLOSED");
            invoiceForm.setStatusMessage("Successfully deleted the new Invoice Detail");
            invoiceForm.setStatusMessageType("success");
        }catch (InvoiceException e){
            invoiceForm.setStatusMessage("Unable to delete the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }catch (Exception e){
            invoiceForm.setStatusMessage("Unable to delete the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        List<InvoiceVO> invoiceVOs;
        try{
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if(invoiceVOs != null && invoiceVOs.size() >0){
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        }catch (InvoiceException e){
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView SearchInvoice(HttpServletRequest request,
                                      HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside SearchInvoice method of InvoiceController ");
        log.info(" Invoice Form details are " + invoiceForm);
        List<InvoiceVO> invoiceVOs;
        try{
            invoiceVOs = getInvoiceDelegate().findInvoices(invoiceForm.getSearchInvoiceVO());
            if(invoiceVOs != null && invoiceVOs.size() >0){
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
            invoiceForm.setStatusMessage("Found " + invoiceVOs.size() + " Invoice details");
            invoiceForm.setStatusMessageType("info");
        }catch (InvoiceException e){
            invoiceForm.setStatusMessage("Unable to find the Invoices due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView updateInvoice(HttpServletRequest request,
                                      HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside updateInvoice method of InvoiceController ");
        log.info(" Invoice Form details are " + invoiceForm);
        invoiceForm.getCurrentInvoiceVO().setModifiedBy(invoiceForm.getLoggedInUser());
        invoiceForm.getCurrentInvoiceVO().setModifiedDate(new Date());
        try{
            getInvoiceDelegate().updateInvoice(invoiceForm.getCurrentInvoiceVO());
            invoiceForm.setStatusMessage("Successfully updated the new Invoice Detail");
            invoiceForm.setStatusMessageType("success");
        }catch (InvoiceException e){
            invoiceForm.setStatusMessage("Unable to update the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }catch (TransactionException e){
            invoiceForm.setStatusMessage("Unable to update the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }catch (Exception e){
            invoiceForm.setStatusMessage("Unable to update the invoice due to an error");
            invoiceForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        List<InvoiceVO> invoiceVOs;
        try{
            invoiceVOs = getInvoiceDelegate().listTodaysInvoice();
            if(invoiceVOs != null && invoiceVOs.size() >0){
                invoiceForm.setInvoiceVOs(invoiceVOs);
            }
        }catch (InvoiceException e){
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView InvoiceTxn(HttpServletRequest request,
                                   HttpServletResponse response, TransactionForm transactionForm){
        //get the id
        TransactionVO transactionVO = null;
        try {
            transactionVO = getTransactionDelegate().fetchTransactionFromId(transactionForm.getId());
            if (transactionVO != null && transactionVO.getMakeId() != null && transactionVO.getMakeId() > 0) {
                List<MakeVO> makeVOs = null;
                try {
                    makeVOs = getMakeDelegate().fetchMakes();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (makeVOs != null) {
                    for (MakeVO makeVO : makeVOs) {
                        log.info("make vo is" + makeVO);
                    }
                    transactionForm.setMakeVOs(makeVOs);
                }
                List<MakeAndModelVO> makeAndModelVOs = null;
                makeAndModelVOs = getMakeDelegate().getAllModelsFromMakeId(transactionVO.getMakeId());
                if (makeAndModelVOs != null) {
                    transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                    for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                        log.info("makeAndModel vo is" + makeAndModelVO);
                    }
                }
            }
        } catch (TransactionException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
        // find tag no  and.. thus the description
        InvoiceForm invoiceForm = new InvoiceForm();
        invoiceForm.setLoggedInUser(transactionForm.getLoggedInUser());
        invoiceForm.setLoggedInRole(transactionForm.getLoggedInRole());
        InvoiceVO invoiceVO = new InvoiceVO();
        if(transactionVO != null){
            invoiceVO.setTagNo(transactionVO.getTagNo());
            String makeName = "";
            String modelName = "";
            if(transactionForm.getMakeAndModelVOs() != null && transactionForm.getMakeAndModelVOs().size() > 0){
                makeName = transactionForm.getMakeAndModelVOs().get(0).getMakeName();
                modelName = transactionForm.getMakeAndModelVOs().get(0).getModelName();
            }
            invoiceVO.setDescription("SERVICE CHARGES FOR "+ makeName +" " +modelName);
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
