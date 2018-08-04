package com.poseidon.invoice.dao.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.dao.entities.Invoice;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * user: Suraj
 * Date: 7/26/12
 * Time: 10:39 PM
 */
@Repository
public class InvoiceDAOImpl implements InvoiceDAO {
    private SimpleJdbcInsert insertInvoice;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvoiceRepository invoiceRepository;

    private final String GET_TODAYS_INVOICE_SQL = "SELECT id,customerName,tagNo,description,serialNo,amount from invoice ";

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
            invoiceVOs = getTodaysInvoices(tagNumbers);
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
            invoiceVO = new InvoiceVO();
            invoiceVO.setId(invoice.getInvoiceId());
            invoiceVO.setCustomerName(invoice.getCustomerName());
            invoiceVO.setTagNo(invoice.getTagNumber());
            invoiceVO.setDescription(invoice.getDescription());
            invoiceVO.setSerialNo(invoice.getSerialNumber());
            invoiceVO.setAmount(Double.valueOf(invoice.getAmount()));
            invoiceVO.setQuantity(Integer.valueOf(invoice.getQuantity()));
            invoiceVO.setRate(Double.valueOf(invoice.getRate()));
        }
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
        StringBuffer SEARCH_INVOICE_QUERY = new StringBuffer();
        SEARCH_INVOICE_QUERY.append("SELECT id,")
                .append(" customerName,")
                .append(" tagNo,")
                .append(" description,")
                .append(" serialNo,")
                .append(" amount ")
                .append(" FROM invoice ");
        Boolean isWhereAdded = Boolean.FALSE;
        if (searchInvoiceVO.getTagNo() != null && searchInvoiceVO.getTagNo().trim().length() > 0) {
            SEARCH_INVOICE_QUERY.append(" where ");
            isWhereAdded = Boolean.TRUE;
            if (searchInvoiceVO.getIncludes()) {
                SEARCH_INVOICE_QUERY.append(" tagNo like '%").append(searchInvoiceVO.getTagNo()).append("%'");
            } else if (searchInvoiceVO.getStartsWith()) {
                SEARCH_INVOICE_QUERY.append(" tagNo like '").append(searchInvoiceVO.getTagNo()).append("%'");
            } else {
                SEARCH_INVOICE_QUERY.append(" tagNo like '").append(searchInvoiceVO.getTagNo()).append("'");
            }
        }

        if (searchInvoiceVO.getSerialNo() != null && searchInvoiceVO.getSerialNo().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_INVOICE_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_INVOICE_QUERY.append(" and ");
            }
            if (searchInvoiceVO.getIncludes()) {
                SEARCH_INVOICE_QUERY.append(" serialNo like '%").append(searchInvoiceVO.getSerialNo()).append("%'");
            } else if (searchInvoiceVO.getStartsWith()) {
                SEARCH_INVOICE_QUERY.append(" serialNo like '").append(searchInvoiceVO.getSerialNo()).append("%'");
            } else {
                SEARCH_INVOICE_QUERY.append(" serialNo like '").append(searchInvoiceVO.getSerialNo()).append("'");
            }
        }

        if (searchInvoiceVO.getDescription() != null && searchInvoiceVO.getDescription().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_INVOICE_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_INVOICE_QUERY.append(" and ");
            }
            if (searchInvoiceVO.getIncludes()) {
                SEARCH_INVOICE_QUERY.append(" description like '%").append(searchInvoiceVO.getDescription()).append("%'");
            } else if (searchInvoiceVO.getStartsWith()) {
                SEARCH_INVOICE_QUERY.append(" description like '").append(searchInvoiceVO.getDescription()).append("%'");
            } else {
                SEARCH_INVOICE_QUERY.append(" description like '").append(searchInvoiceVO.getDescription()).append("'");
            }
        }

        if (searchInvoiceVO.getId() != null && searchInvoiceVO.getId() > 0) {
            if (!isWhereAdded) {
                SEARCH_INVOICE_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_INVOICE_QUERY.append(" and ");
            }
            SEARCH_INVOICE_QUERY.append(" id = ").append(searchInvoiceVO.getId());
        }

        if (searchInvoiceVO.getAmount() != null && searchInvoiceVO.getAmount() > 0) {
            if (!isWhereAdded) {
                SEARCH_INVOICE_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_INVOICE_QUERY.append(" and ");
            }
            if (searchInvoiceVO.getGreater() && !searchInvoiceVO.getLesser()) {
                SEARCH_INVOICE_QUERY.append(" amount >= ").append(searchInvoiceVO.getAmount());

            } else if (searchInvoiceVO.getLesser() && !searchInvoiceVO.getGreater()) {
                SEARCH_INVOICE_QUERY.append(" amount <= ").append(searchInvoiceVO.getAmount());
            } else if (!searchInvoiceVO.getLesser() && !searchInvoiceVO.getGreater()) {
                SEARCH_INVOICE_QUERY.append(" amount = ").append(searchInvoiceVO.getAmount());
            }
        }

        log.info("query created is " + SEARCH_INVOICE_QUERY.toString());
        return (List<InvoiceVO>) jdbcTemplate.query(SEARCH_INVOICE_QUERY.toString(), new InvoiceRowMapper());
    }

    private List<InvoiceVO> getTodaysInvoices(List<String> tagNumbers) throws DataAccessException {
        String query = GET_TODAYS_INVOICE_SQL + createWhereClause(tagNumbers);
        log.info("Query generated is " + query);
        return (List<InvoiceVO>) jdbcTemplate.query(query, new InvoiceRowMapper());
    }

    private String createWhereClause(List<String> tagNumbers) {
        if (tagNumbers.size() > 0) {
            String query = " Where tagNo in (";
            List<String> quotedTagNo = new ArrayList<>();
            for (String tagNo: tagNumbers) {
                quotedTagNo.add("'" + tagNo + "'");
            }
            query += StringUtils.collectionToCommaDelimitedString(quotedTagNo);
            query = query + ") ";
            return query;
        }
        return "";
    }

    private void saveInvoice(InvoiceVO currentInvoiceVO) throws DataAccessException {
        insertInvoice = new SimpleJdbcInsert(jdbcTemplate).withTableName("invoice").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tagNo", currentInvoiceVO.getTagNo())
                .addValue("description", currentInvoiceVO.getDescription())
                .addValue("serialNo", currentInvoiceVO.getSerialNo())
                .addValue("amount", currentInvoiceVO.getAmount())
                .addValue("quantity", currentInvoiceVO.getQuantity())
                .addValue("rate", currentInvoiceVO.getRate())
                .addValue("customerId", currentInvoiceVO.getCustomerId())
                .addValue("customerName", currentInvoiceVO.getCustomerName())
                .addValue("createdOn", currentInvoiceVO.getCreatedDate())
                .addValue("modifiedOn", currentInvoiceVO.getModifiedDate())
                .addValue("createdBy", currentInvoiceVO.getCreatedBy())
                .addValue("modifiedBy", currentInvoiceVO.getModifiedBy());
        Number newId = insertInvoice.executeAndReturnKey(parameters);
        log.info(" the queryForInt resulted in  " + newId.longValue());
        currentInvoiceVO.setId(newId.longValue());
        // update the InvoiceId
        String invoiceId = "INV" + newId.longValue();
        String query = "update invoice set transactionId = '" + invoiceId + "' where id =" + newId.longValue();
        jdbcTemplate.update(query);
    }

    /**
     * Row mapper as inner class
     */
    private class InvoiceRowMapper implements RowMapper {

        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            InvoiceVO invoiceVO = new InvoiceVO();
            invoiceVO.setId(resultSet.getLong("id"));
            invoiceVO.setCustomerName(resultSet.getString("customerName"));
            invoiceVO.setTagNo(resultSet.getString("tagNo"));
            invoiceVO.setDescription(resultSet.getString("description"));
            invoiceVO.setSerialNo(resultSet.getString("serialNo"));
            invoiceVO.setAmount(resultSet.getDouble("amount"));
            return invoiceVO;
        }

    }
}
