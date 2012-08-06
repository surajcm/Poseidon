package com.poseidon.Invoice.web.controller;

import com.poseidon.Invoice.delegate.InvoiceDelegate;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Invoice.web.form.InvoiceForm;
import com.poseidon.Transaction.exception.TransactionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private InvoiceDelegate invoiceDelegate;
    private final Log log = LogFactory.getLog(InvoiceController.class);

    public InvoiceDelegate getInvoiceDelegate() {
        return invoiceDelegate;
    }

    public void setInvoiceDelegate(InvoiceDelegate invoiceDelegate) {
        this.invoiceDelegate = invoiceDelegate;
    }

    public ModelAndView ListInvoice(HttpServletRequest request,
                                    HttpServletResponse response){
        log.info(" Inside ListInvoice method of InvoiceController ");
        InvoiceForm invoiceForm = new InvoiceForm();
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
            getInvoiceDelegate().addInvoice(invoiceForm.getCurrentInvoiceVO());
            invoiceForm.setStatusMessage("Successfully saved the new Invoice Detail");
            invoiceForm.setStatusMessageType("success");
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
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView DeleteInvoice(HttpServletRequest request,
                                      HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside DeleteInvoice method of InvoiceController ");
        log.info(" Invoice Form details are " + invoiceForm);
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }

    public ModelAndView SearchInvoice(HttpServletRequest request,
                                      HttpServletResponse response,InvoiceForm invoiceForm){
        log.info(" Inside SearchInvoice method of InvoiceController ");
        log.info(" Invoice Form details are " + invoiceForm);
        invoiceForm.setSearchInvoiceVO(new InvoiceVO());
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }
}
