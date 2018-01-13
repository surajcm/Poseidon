package com.poseidon.Invoice.delegate;

import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Invoice.service.InvoiceService;
import com.poseidon.Transaction.domain.TransactionReportVO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;
import com.poseidon.Transaction.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:32 PM
 */
public class InvoiceDelegate {
    private InvoiceService invoiceService;
    private TransactionService transactionService;

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException, TransactionException {

        TransactionReportVO transactionReportVO = getTransactionService().fetchTransactionFromTag(currentInvoiceVO.getTagNo());
        currentInvoiceVO.setCustomerId(transactionReportVO.getCustomerId());
        currentInvoiceVO.setCustomerName(transactionReportVO.getCustomerName());
        currentInvoiceVO.setSerialNo(transactionReportVO.getSerialNo());
        getInvoiceService().addInvoice(currentInvoiceVO);
    }

    public List<InvoiceVO> listTodaysInvoice() throws InvoiceException, TransactionException {
        List<TransactionVO> transactionVOs = getTransactionService().listTodaysTransactions();
        List<String> tagNumbers = fetchTagNoFromListOfTransactionVOs(transactionVOs);
        return getInvoiceService().fetchInvoiceForListOfTransactions(tagNumbers);
    }

    private List<String> fetchTagNoFromListOfTransactionVOs(List<TransactionVO> transactionVOs) {
        List<String> tagNumbers = new ArrayList<>();
        for(TransactionVO transactionVO:transactionVOs){
            tagNumbers.add(transactionVO.getTagNo());
        }
        return tagNumbers;
    }

    public InvoiceVO fetchInvoiceVOFromId(Long id) throws InvoiceException {
        return getInvoiceService().fetchInvoiceVOFromId(id);
    }

    public void deleteInvoice(Long id) throws InvoiceException {
        getInvoiceService().deleteInvoice(id);
    }

    public void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException, TransactionException {
        TransactionReportVO transactionReportVO = getTransactionService().fetchTransactionFromTag(currentInvoiceVO.getTagNo());
        currentInvoiceVO.setCustomerId(transactionReportVO.getCustomerId());
        currentInvoiceVO.setCustomerName(transactionReportVO.getCustomerName());
        currentInvoiceVO.setSerialNo(transactionReportVO.getSerialNo());
        getInvoiceService().updateInvoice(currentInvoiceVO);
    }

    public List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO)throws InvoiceException {
        return getInvoiceService().findInvoices(searchInvoiceVO);
    }
}
