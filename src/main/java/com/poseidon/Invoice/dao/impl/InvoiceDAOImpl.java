package com.poseidon.Invoice.dao.impl;

import com.poseidon.Invoice.dao.InvoiceDAO;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
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
    private final String GET_TODAYS_INVOICE_SQL ="SELECT id,customerName,tagNo,description,serialNo,amount from invoice ";
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
}
