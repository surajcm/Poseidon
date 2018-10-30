package com.poseidon.invoice.dao.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.dao.entities.Invoice;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * user: Suraj
 * Date: 7/26/12
 * Time: 10:39 PM
 */
@SuppressWarnings("unused")
@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

    private SimpleJdbcInsert insertInvoice;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PersistenceContext
    private EntityManager em;

    private static final Logger log = LoggerFactory.getLogger(InvoiceDAOImpl.class);

    public void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        try {
            saveInvoice(currentInvoiceVO);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
    }

    public List<InvoiceVO> fetchInvoiceForListOfTransactions(List<String> tagNumbers) throws InvoiceException {
        List<InvoiceVO> invoiceVOs;
        try {
            List<Invoice> invoices = invoiceRepository.fetchTodaysInvoices(tagNumbers);
            invoiceVOs = invoices.stream().map(this::getInvoiceVOFromInvoice).collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
        return invoiceVOs;
    }

    public InvoiceVO fetchInvoiceVOFromId(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id.intValue());
        InvoiceVO invoiceVO = null;
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoiceVO = getInvoiceVOFromInvoice(invoice);
        }
        return invoiceVO;
    }

    private InvoiceVO getInvoiceVOFromInvoice(Invoice invoice) {
        InvoiceVO invoiceVO;
        invoiceVO = new InvoiceVO();
        invoiceVO.setId(invoice.getInvoiceId());
        invoiceVO.setCustomerName(invoice.getCustomerName());
        invoiceVO.setTagNo(invoice.getTagNumber());
        invoiceVO.setDescription(invoice.getDescription());
        invoiceVO.setSerialNo(invoice.getSerialNumber());
        invoiceVO.setAmount(Double.valueOf(invoice.getAmount()));
        invoiceVO.setQuantity(Integer.valueOf(invoice.getQuantity()));
        invoiceVO.setRate(Double.valueOf(invoice.getRate()));
        return invoiceVO;
    }

    public void deleteInvoice(Long id) throws InvoiceException {
        try {
            invoiceRepository.deleteById(id.intValue());
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
    }

    public void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        try {
            Optional<Invoice> optionalInvoice = invoiceRepository.findById(currentInvoiceVO.getId().intValue());
            if (optionalInvoice.isPresent()) {
                Invoice invoice = optionalInvoice.get();
                invoice.setTagNumber(currentInvoiceVO.getTagNo());
                invoice.setDescription(currentInvoiceVO.getDescription());
                invoice.setSerialNumber(currentInvoiceVO.getSerialNo());
                invoice.setAmount(currentInvoiceVO.getAmount().toString());
                invoice.setQuantity(String.valueOf(currentInvoiceVO.getQuantity()));
                invoice.setRate(currentInvoiceVO.getRate().toString());
                invoice.setCustomerName(currentInvoiceVO.getCustomerName());
                invoice.setCustomerId(currentInvoiceVO.getCustomerId());
                invoice.setModifiedOn(currentInvoiceVO.getModifiedDate());
                invoice.setModifiedBy(currentInvoiceVO.getModifiedBy());
                invoiceRepository.save(invoice);
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
    }

    public List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO) throws InvoiceException {
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = searchInvoice(searchInvoiceVO);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
        return invoiceVOs;
    }

    private List<InvoiceVO> searchInvoice(InvoiceVO searchInvoiceVO) {
        CriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
        Root<Invoice> invoiceRoot = criteria.from(Invoice.class);
        criteria.select(invoiceRoot);

        if (searchInvoiceVO.getIncludes()) {
            if(!StringUtils.isEmpty(searchInvoiceVO.getTagNo())) {
                criteria.where(builder.like(invoiceRoot.get("tagno"), "%"+searchInvoiceVO.getTagNo()+"%"));
            }
            if(!StringUtils.isEmpty(searchInvoiceVO.getSerialNo())) {
                criteria.where(builder.like(invoiceRoot.get("serialno"), "%"+searchInvoiceVO.getSerialNo()+"%"));
            }
            if(!StringUtils.isEmpty(searchInvoiceVO.getDescription())) {
                criteria.where(builder.like(invoiceRoot.get("description"), "%"+searchInvoiceVO.getDescription()+"%"));
            }
            if(searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
                criteria.where(builder.like(invoiceRoot.get("id"), "%"+searchInvoiceVO.getId()+"%"));
            }

        } else if (searchInvoiceVO.getStartsWith()) {
            if(!StringUtils.isEmpty(searchInvoiceVO.getTagNo())) {
                criteria.where(builder.like(invoiceRoot.get("tagno"), searchInvoiceVO.getTagNo()+"%"));
            }
            if(!StringUtils.isEmpty(searchInvoiceVO.getSerialNo())) {
                criteria.where(builder.like(invoiceRoot.get("serialno"), searchInvoiceVO.getSerialNo()+"%"));
            }
            if(!StringUtils.isEmpty(searchInvoiceVO.getDescription())) {
                criteria.where(builder.like(invoiceRoot.get("description"), searchInvoiceVO.getDescription()+"%"));
            }
            if(searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
                criteria.where(builder.like(invoiceRoot.get("id"), searchInvoiceVO.getId()+"%"));
            }
        } else {
            if(!StringUtils.isEmpty(searchInvoiceVO.getTagNo())) {
                criteria.where(builder.like(invoiceRoot.get("tagno"), searchInvoiceVO.getTagNo()));
            }
            if(!StringUtils.isEmpty(searchInvoiceVO.getSerialNo())) {
                criteria.where(builder.like(invoiceRoot.get("serialno"), searchInvoiceVO.getSerialNo()));
            }
            if(!StringUtils.isEmpty(searchInvoiceVO.getDescription())) {
                criteria.where(builder.like(invoiceRoot.get("description"), searchInvoiceVO.getDescription()));
            }
            if(searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
                criteria.where(builder.equal(invoiceRoot.get("id"), searchInvoiceVO.getId()));
            }
        }
        if(searchInvoiceVO.getAmount() != null && searchInvoiceVO.getAmount() > 0) {
            if (searchInvoiceVO.getGreater() && !searchInvoiceVO.getLesser()) {
                criteria.where(builder.greaterThanOrEqualTo(invoiceRoot.get("amount"), searchInvoiceVO.getAmount()));
            } else if (searchInvoiceVO.getLesser() && !searchInvoiceVO.getGreater()) {
                criteria.where(builder.lessThanOrEqualTo(invoiceRoot.get("amount"), searchInvoiceVO.getAmount()));
            } else if (!searchInvoiceVO.getLesser() && !searchInvoiceVO.getGreater()) {
                criteria.where(builder.equal(invoiceRoot.get("amount"), searchInvoiceVO.getAmount()));
            }
        }

        List<Invoice> resultList = em.unwrap(Session.class).createQuery(criteria).getResultList();
        return resultList.stream().map(this::convertInvoiceToInvoiceVO).collect(Collectors.toList());
    }

    private InvoiceVO convertInvoiceToInvoiceVO(Invoice invoice) {
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setId(invoice.getInvoiceId());
        invoiceVO.setCustomerName(invoice.getCustomerName());
        invoiceVO.setTagNo(invoice.getTagNumber());
        invoiceVO.setDescription(invoice.getDescription());
        invoiceVO.setSerialNo(invoice.getSerialNumber());
        invoiceVO.setAmount(Double.valueOf(invoice.getAmount()));
        return invoiceVO;
    }

    private void saveInvoice(InvoiceVO currentInvoiceVO) {
        Invoice invoice = convertInvoiceVOToInvoice(currentInvoiceVO);
        Invoice newInvoice = invoiceRepository.save(invoice);
        log.info(" the queryForInt resulted in  " + newInvoice.getInvoiceId());
        currentInvoiceVO.setId(newInvoice.getInvoiceId());
        // update the InvoiceId
        String invoiceId = "INV" + newInvoice.getInvoiceId();
        newInvoice.setTransactionId(invoiceId);
        invoiceRepository.save(newInvoice);
    }

    private Invoice convertInvoiceVOToInvoice(InvoiceVO currentInvoiceVO) {
        Invoice invoice = new Invoice();
        invoice.setTagNumber(currentInvoiceVO.getTagNo());
        invoice.setDescription(currentInvoiceVO.getDescription());
        invoice.setSerialNumber(currentInvoiceVO.getSerialNo());
        //todo: clean up
        invoice.setAmount(currentInvoiceVO.getAmount().toString());
        //todo: clean up
        invoice.setQuantity(String.valueOf(currentInvoiceVO.getQuantity()));
        //todo: clean up
        invoice.setRate(currentInvoiceVO.getRate().toString());
        invoice.setCustomerId(currentInvoiceVO.getCustomerId());
        invoice.setCustomerName(currentInvoiceVO.getCustomerName());
        return invoice;
    }
}
