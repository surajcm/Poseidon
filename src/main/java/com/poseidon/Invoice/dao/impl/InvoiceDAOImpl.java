package com.poseidon.Invoice.dao.impl;

import com.poseidon.Invoice.dao.InvoiceDAO;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Transaction.domain.TransactionVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:39 PM
 */
public class InvoiceDAOImpl  extends JdbcDaoSupport implements InvoiceDAO {
    private SimpleJdbcInsert insertInvoice;
    private final String GET_TODAYS_INVOICE_SQL = "SELECT id,customerName,tagNo,description,serialNo,amount from invoice ";
    private final String GET_SINGLE_INVOICE_SQL = "SELECT id,customerName,tagNo,description,serialNo,amount,quantity,rate from invoice where id = ?";
    private final String DELETE_INVOICE_BY_ID_SQL = " delete from invoice where id = ?  ";
    private final String UPDATE_INVOICE_SQL = " update invoice set tagNo = ? ,description = ? ,serialNo = ? ,amount = ?," +
            " quantity = ?,rate = ?, customerName = ? ,customerId = ?, modifiedOn = ?,modifiedBy = ? where id = ?  ";

    private final Log log = LogFactory.getLog(InvoiceDAOImpl.class);
    public void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        try {
            saveInvoice(currentInvoiceVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
    }

    public List<InvoiceVO> fetchInvoiceForListOfTransactions(List<String> tagNumbers) throws InvoiceException {
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = getTodaysInvoices(tagNumbers);
        } catch (DataAccessException e) {
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
        return invoiceVOs;
    }

    public InvoiceVO fetchInvoiceVOFromId(Long id)  throws InvoiceException {
        return (InvoiceVO) getJdbcTemplate().queryForObject(GET_SINGLE_INVOICE_SQL, new Object[]{id}, new InvoiceFullRowMapper());
    }

    public void deleteInvoice(Long id) throws InvoiceException {
        try {
            Object[] parameters = new Object[]{id};
            getJdbcTemplate().update(DELETE_INVOICE_BY_ID_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
    }

    public void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        Object[] parameters = new Object[]{
                currentInvoiceVO.getTagNo(),
                currentInvoiceVO.getDescription(),
                currentInvoiceVO.getSerialNo(),
                currentInvoiceVO.getAmount(),
                currentInvoiceVO.getQuantity(),
                currentInvoiceVO.getRate(),
                currentInvoiceVO.getCustomerName(),
                currentInvoiceVO.getCustomerId(),
                currentInvoiceVO.getModifiedDate(),
                currentInvoiceVO.getModifiedBy(),
                currentInvoiceVO.getId()};

        try {
            getJdbcTemplate().update(UPDATE_INVOICE_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new InvoiceException(InvoiceException.DATABASE_ERROR);
        }
    }

    public List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO) throws InvoiceException {
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = searchInvoice(searchInvoiceVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
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

        if (searchInvoiceVO.getAmount() > 0) {
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
            } else if(!searchInvoiceVO.getLesser() && !searchInvoiceVO.getGreater()) {
                SEARCH_INVOICE_QUERY.append(" amount = ").append(searchInvoiceVO.getAmount());
            }
        }

        log.info("query created is " + SEARCH_INVOICE_QUERY.toString());
        return (List<InvoiceVO>) getJdbcTemplate().query(SEARCH_INVOICE_QUERY.toString(), new InvoiceRowMapper());
    }

    private List<InvoiceVO> getTodaysInvoices(List<String> tagNumbers) throws DataAccessException {
        String query = GET_TODAYS_INVOICE_SQL + createWhereClause(tagNumbers);
        log.info("Query generated is "+query);
        return (List<InvoiceVO>) getJdbcTemplate().query(query, new InvoiceRowMapper());
    }

    private String createWhereClause(List<String> tagNumbers) {
        if(tagNumbers.size() > 0){
            String query = " Where tagNo in (";
            List<String> quotedTagNo = new ArrayList<String>();
            for(String tagNo :tagNumbers){
                quotedTagNo.add("'"+tagNo+"'");
            }
            query += StringUtils.collectionToCommaDelimitedString(quotedTagNo);
            query = query + ") ";
            return query;
        }
        return "";
    }

    private void saveInvoice(InvoiceVO currentInvoiceVO) throws DataAccessException{
        insertInvoice = new SimpleJdbcInsert(getDataSource()).withTableName("invoice").usingGeneratedKeyColumns("id");
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
        getJdbcTemplate().update(query);
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
            InvoiceVO  invoiceVO = new InvoiceVO();
            invoiceVO.setId(resultSet.getLong("id"));
            invoiceVO.setCustomerName(resultSet.getString("customerName"));
            invoiceVO.setTagNo(resultSet.getString("tagNo"));
            invoiceVO.setDescription(resultSet.getString("description"));
            invoiceVO.setSerialNo(resultSet.getString("serialNo"));
            invoiceVO.setAmount(resultSet.getDouble("amount"));
            return invoiceVO;
        }

    }

    /**
     * Row mapper as inner class
     */
    private class InvoiceFullRowMapper implements RowMapper {

        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            InvoiceVO  invoiceVO = new InvoiceVO();
            invoiceVO.setId(resultSet.getLong("id"));
            invoiceVO.setCustomerName(resultSet.getString("customerName"));
            invoiceVO.setTagNo(resultSet.getString("tagNo"));
            invoiceVO.setDescription(resultSet.getString("description"));
            invoiceVO.setSerialNo(resultSet.getString("serialNo"));
            invoiceVO.setAmount(resultSet.getDouble("amount"));
            invoiceVO.setQuantity(resultSet.getInt("quantity"));
            invoiceVO.setRate(resultSet.getInt("rate"));
            return invoiceVO;
        }

    }
}
