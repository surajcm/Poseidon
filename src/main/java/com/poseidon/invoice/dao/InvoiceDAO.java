package com.poseidon.invoice.dao;

import com.poseidon.invoice.dao.entities.Invoice;
import com.poseidon.invoice.dao.repo.InvoiceRepository;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;

@SuppressWarnings("unused")
@Service
public class InvoiceDAO {
    private static final String TAG_NO = "tagno";
    private static final String SERIAL_NO = "serialno";
    private static final String DESCRIPTION = "description";
    private static final String AMOUNT = "amount";
    private final InvoiceRepository invoiceRepository;

    @PersistenceContext
    private EntityManager em;

    private static final Logger log = LoggerFactory.getLogger(InvoiceDAO.class);

    public InvoiceDAO(final InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * add invoice.
     *
     * @param currentInvoiceVO currentInvoiceVO
     */
    public void addInvoice(final InvoiceVO currentInvoiceVO) {
        sneak(() -> invoiceRepository.save(convertInvoiceVOToInvoice(currentInvoiceVO)));
    }

    /**
     * fetch Invoice For List Of Transactions.
     *
     * @param tagNumbers tagNumbers as list
     * @return list of invoice vo
     */
    public List<InvoiceVO> fetchInvoiceForListOfTransactions(final List<String> tagNumbers) {
        var invoices = sneak(() -> invoiceRepository.fetchTodaysInvoices(tagNumbers));
        return invoices.stream().map(this::getInvoiceVoFromInvoice).toList();
    }

    /**
     * fetch invoice vo from id.
     *
     * @param id of invoice vo
     * @return invoice vo
     */
    public Optional<InvoiceVO> fetchInvoiceVOFromId(final Long id) {
        var optionalInvoice = sneak(() -> invoiceRepository.findById(id));
        return optionalInvoice.map(this::getInvoiceVoFromInvoice);
    }

    /**
     * fetch invoice vo from tagNo.
     *
     * @param tagNo of invoice vo
     * @return invoice vo
     */
    public Optional<InvoiceVO> fetchInvoiceVOFromTagNo(final String tagNo) {
        var optionalInvoice = sneak(() -> invoiceRepository.findByTagNumber(tagNo));
        return optionalInvoice.map(this::getInvoiceVoFromInvoice);
    }

    /**
     * delete Invoice.
     *
     * @param id of invoice
     * @throws InvoiceException on error
     */
    public void deleteInvoice(final Long id) throws InvoiceException {
        try {
            invoiceRepository.deleteById(id);
        } catch (DataAccessException ex) {
            log.error(ex.getLocalizedMessage());
            throw new InvoiceException(ex);
        }
    }

    /**
     * update an invoice.
     *
     * @param currentInvoiceVO currentInvoiceVO
     */
    public void updateInvoice(final InvoiceVO currentInvoiceVO) {
        var optionalInvoice = sneak(() -> invoiceRepository.findById(currentInvoiceVO.getId()));
        if (optionalInvoice.isPresent()) {
            var invoice = getInvoice(currentInvoiceVO, optionalInvoice.get());
            sneak(() -> invoiceRepository.save(invoice));
        }
    }

    /**
     * search for invoices.
     *
     * @param searchInvoiceVO searchInvoiceVO
     * @return list of invoice
     * @throws InvoiceException on error
     */
    public List<InvoiceVO> findInvoices(final InvoiceVO searchInvoiceVO) throws InvoiceException {
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = searchInvoice(searchInvoiceVO);
        } catch (DataAccessException ex) {
            log.error(ex.getLocalizedMessage());
            throw new InvoiceException(ex);
        }
        return invoiceVOs;
    }

    private List<InvoiceVO> searchInvoice(final InvoiceVO searchInvoiceVO) {
        var builder = em.unwrap(Session.class).getCriteriaBuilder();
        var criteria = builder.createQuery(Invoice.class);
        var invoiceRoot = criteria.from(Invoice.class);
        criteria.select(invoiceRoot);

        if (Boolean.TRUE.equals(searchInvoiceVO.getIncludes())) {
            if (!StringUtils.hasText(searchInvoiceVO.getTagNo())) {
                criteria.where(builder.like(invoiceRoot.get(TAG_NO),
                        "%" + searchInvoiceVO.getTagNo() + "%"));
            }
            if (!StringUtils.hasText(searchInvoiceVO.getSerialNo())) {
                criteria.where(builder.like(invoiceRoot.get(SERIAL_NO),
                        "%" + searchInvoiceVO.getSerialNo() + "%"));
            }
            if (!StringUtils.hasText(searchInvoiceVO.getDescription())) {
                criteria.where(builder.like(invoiceRoot.get(DESCRIPTION),
                        "%" + searchInvoiceVO.getDescription() + "%"));
            }
            if (searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
                criteria.where(builder.like(invoiceRoot.get("id"), "%" + searchInvoiceVO.getId() + "%"));
            }

        } else if (Boolean.TRUE.equals(searchInvoiceVO.getStartsWith())) {
            if (!StringUtils.hasText(searchInvoiceVO.getTagNo())) {
                criteria.where(builder.like(invoiceRoot.get(TAG_NO), searchInvoiceVO.getTagNo() + "%"));
            }
            if (!StringUtils.hasText(searchInvoiceVO.getSerialNo())) {
                criteria.where(builder.like(invoiceRoot.get(SERIAL_NO),
                        searchInvoiceVO.getSerialNo() + "%"));
            }
            if (!StringUtils.hasText(searchInvoiceVO.getDescription())) {
                criteria.where(builder.like(invoiceRoot.get(DESCRIPTION),
                        searchInvoiceVO.getDescription() + "%"));
            }
            if (searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
                criteria.where(builder.like(invoiceRoot.get("id"), searchInvoiceVO.getId() + "%"));
            }
        } else {
            if (!StringUtils.hasText(searchInvoiceVO.getTagNo())) {
                criteria.where(builder.like(invoiceRoot.get(TAG_NO), searchInvoiceVO.getTagNo()));
            }
            if (!StringUtils.hasText(searchInvoiceVO.getSerialNo())) {
                criteria.where(builder.like(invoiceRoot.get(SERIAL_NO), searchInvoiceVO.getSerialNo()));
            }
            if (!StringUtils.hasText(searchInvoiceVO.getDescription())) {
                criteria.where(builder.like(invoiceRoot.get(DESCRIPTION), searchInvoiceVO.getDescription()));
            }
            if (searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
                criteria.where(builder.equal(invoiceRoot.get("id"), searchInvoiceVO.getId()));
            }
        }
        if (searchInvoiceVO.getAmount() != null && searchInvoiceVO.getAmount() > 0) {
            if (Boolean.TRUE.equals(searchInvoiceVO.getGreater())
                    && Boolean.FALSE.equals(searchInvoiceVO.getLesser())) {
                criteria.where(builder.greaterThanOrEqualTo(invoiceRoot.get(AMOUNT), searchInvoiceVO.getAmount()));
            } else if (Boolean.TRUE.equals(searchInvoiceVO.getLesser())
                    && Boolean.FALSE.equals(searchInvoiceVO.getGreater())) {
                criteria.where(builder.lessThanOrEqualTo(invoiceRoot.get(AMOUNT), searchInvoiceVO.getAmount()));
            } else if (Boolean.FALSE.equals(searchInvoiceVO.getLesser())
                    && Boolean.FALSE.equals(searchInvoiceVO.getGreater())) {
                criteria.where(builder.equal(invoiceRoot.get(AMOUNT), searchInvoiceVO.getAmount()));
            }
        }

        List<Invoice> resultList = em.unwrap(Session.class).createQuery(criteria).getResultList();
        return resultList.stream().map(this::convertInvoiceToInvoiceVO).toList();
    }

    private InvoiceVO getInvoiceVoFromInvoice(final Invoice invoice) {
        var invoiceVO = new InvoiceVO();
        invoiceVO.setId(invoice.getInvoiceId());
        invoiceVO.setCustomerName(invoice.getCustomerName());
        invoiceVO.setTagNo(invoice.getTagNumber());
        invoiceVO.setDescription(invoice.getDescription());
        invoiceVO.setSerialNo(invoice.getSerialNumber());
        if (invoice.getAmount() != null) {
            invoiceVO.setAmount(Double.valueOf(invoice.getAmount()));
        }
        if (invoice.getQuantity() != null) {
            invoiceVO.setQuantity(invoice.getQuantity());
        }
        if (invoice.getRate() != null) {
            invoiceVO.setRate(Double.valueOf(invoice.getRate()));
        }
        return invoiceVO;
    }

    private InvoiceVO convertInvoiceToInvoiceVO(final Invoice invoice) {
        var invoiceVO = new InvoiceVO();
        invoiceVO.setId(invoice.getInvoiceId());
        invoiceVO.setCustomerName(invoice.getCustomerName());
        invoiceVO.setTagNo(invoice.getTagNumber());
        invoiceVO.setDescription(invoice.getDescription());
        invoiceVO.setSerialNo(invoice.getSerialNumber());
        invoiceVO.setAmount(Double.valueOf(invoice.getAmount()));
        return invoiceVO;
    }

    private Invoice getInvoice(final InvoiceVO currentInvoiceVO, final Invoice invoice) {
        invoice.setTagNumber(currentInvoiceVO.getTagNo());
        invoice.setDescription(currentInvoiceVO.getDescription());
        invoice.setSerialNumber(currentInvoiceVO.getSerialNo());
        if (currentInvoiceVO.getAmount() != null) {
            invoice.setAmount(currentInvoiceVO.getAmount().longValue());
        }
        invoice.setQuantity(currentInvoiceVO.getQuantity());
        if (currentInvoiceVO.getRate() != null) {
            invoice.setRate(currentInvoiceVO.getRate().longValue());
        }
        invoice.setCustomerName(currentInvoiceVO.getCustomerName());
        invoice.setCustomerId(currentInvoiceVO.getCustomerId());
        invoice.setModifiedOn(currentInvoiceVO.getModifiedDate());
        invoice.setModifiedBy(currentInvoiceVO.getModifiedBy());
        return invoice;
    }

    private Invoice convertInvoiceVOToInvoice(final InvoiceVO currentInvoiceVO) {
        return getInvoice(currentInvoiceVO, new Invoice());
    }
}
