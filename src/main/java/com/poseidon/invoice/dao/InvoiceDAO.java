package com.poseidon.invoice.dao;

import com.poseidon.invoice.dao.entities.Invoice;
import com.poseidon.invoice.dao.repo.InvoiceRepository;
import com.poseidon.invoice.domain.InvoiceVO;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

@SuppressWarnings("unused")
@Service
public class InvoiceDAO {
    private static final String TAG_NO = "tagNumber";
    private static final String SERIAL_NO = "serialNumber";
    private static final String DESCRIPTION = "description";
    private static final String AMOUNT = "amount";
    private static final String ID = "id";
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
     * @param currentInvoice currentInvoice
     */
    public void addInvoice(final Invoice currentInvoice) {
        sneak(() -> invoiceRepository.save(currentInvoice));
    }

    /**
     * fetch Invoice For List Of Transactions.
     *
     * @param tagNumbers tagNumbers as list
     * @return list of invoice vo
     */
    public List<Invoice> fetchInvoiceForListOfTransactions(final List<String> tagNumbers) {
        return sneak(() -> invoiceRepository.fetchTodaysInvoices(tagNumbers));
    }

    /**
     * fetch invoice vo from id.
     *
     * @param id of invoice vo
     * @return invoice vo
     */
    public Optional<Invoice> fetchInvoiceVOFromId(final Long id) {
        return sneak(() -> invoiceRepository.findById(id));
    }

    /**
     * fetch invoice vo from tagNo.
     *
     * @param tagNo of invoice vo
     * @return invoice vo
     */
    public Optional<Invoice> fetchInvoiceVOFromTagNo(final String tagNo) {
        return sneak(() -> invoiceRepository.findByTagNumber(tagNo));
    }

    /**
     * delete Invoice.
     *
     * @param id of invoice
     */
    public void deleteInvoice(final Long id) {
        var consumer = sneaked(invoiceRepository::deleteById);
        consumer.accept(id);
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
     */
    public List<InvoiceVO> findInvoices(final InvoiceVO searchInvoiceVO) {
        return sneak(() -> searchInvoice(searchInvoiceVO));
    }

    private List<InvoiceVO> searchInvoice(final InvoiceVO searchInvoiceVO) {
        var builder = em.unwrap(Session.class).getCriteriaBuilder();
        var criteria = builder.createQuery(Invoice.class);
        var invoiceRoot = criteria.from(Invoice.class);
        criteria.select(invoiceRoot);
        var includes = searchInvoiceVO.getIncludes();
        var startsWith = searchInvoiceVO.getStartsWith();
        if (StringUtils.hasText(searchInvoiceVO.getTagNo())) {
            var tag = pattern(includes, startsWith, searchInvoiceVO.getTagNo());
            criteria.where(builder.like(invoiceRoot.get(TAG_NO), tag));
        }

        if (StringUtils.hasText(searchInvoiceVO.getSerialNo())) {
            var serial = pattern(includes, startsWith, searchInvoiceVO.getSerialNo());
            criteria.where(builder.like(invoiceRoot.get(SERIAL_NO), serial));
        }

        if (StringUtils.hasText(searchInvoiceVO.getDescription())) {
            var desc = pattern(includes, startsWith, searchInvoiceVO.getDescription());
            criteria.where(builder.like(invoiceRoot.get(DESCRIPTION), desc));
        }

        if (searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
            criteria.where(builder.equal(invoiceRoot.get(ID), searchInvoiceVO.getId()));
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

    private String pattern(final boolean includes, final boolean startsWith, final String element) {
        String patternString;
        if (includes) {
            patternString = "%" + element + "%";
        } else if (startsWith) {
            patternString = element + "%";
        } else {
            patternString = element;
        }
        return patternString;
    }

    private InvoiceVO getInvoiceVoFromInvoice(final Invoice invoice) {
        var invoiceVO = new InvoiceVO();
        invoiceVO.setId(invoice.getId());
        invoiceVO.setTransactionId(invoice.getTransactionId());
        invoiceVO.setTagNo(invoice.getTagNumber());
        invoiceVO.setCustomerId(invoice.getCustomerId());
        invoiceVO.setCustomerName(invoice.getCustomerName());
        invoiceVO.setDescription(invoice.getDescription());
        invoiceVO.setSerialNo(invoice.getSerialNumber());
        if (invoice.getQuantity() != null) {
            invoiceVO.setQuantity(invoice.getQuantity());
        }
        if (invoice.getRate() != null) {
            invoiceVO.setRate(Double.valueOf(invoice.getRate()));
        }
        if (invoice.getAmount() != null) {
            invoiceVO.setAmount(Double.valueOf(invoice.getAmount()));
        }
        return invoiceVO;
    }

    private InvoiceVO convertInvoiceToInvoiceVO(final Invoice invoice) {
        var invoiceVO = new InvoiceVO();
        invoiceVO.setId(invoice.getId());
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
        invoice.setTransactionId(currentInvoiceVO.getTransactionId());
        invoice.setModifiedBy(currentInvoiceVO.getModifiedBy());
        return invoice;
    }

    private Invoice convertInvoiceVOToInvoice(final InvoiceVO currentInvoiceVO) {
        var invoice = getInvoice(currentInvoiceVO, new Invoice());
        invoice.setCreatedBy(currentInvoiceVO.getCreatedBy());
        return invoice;
    }
}
