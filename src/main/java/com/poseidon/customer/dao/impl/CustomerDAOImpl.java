package com.poseidon.customer.dao.impl;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public class CustomerDAOImpl implements CustomerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAOImpl.class);
    private static final String MOBILE = "mobile";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * list all customer details.
     *
     * @return list customer vo
     * @throws CustomerException on error
     */
    @Override
    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        List<CustomerVO> customerVOs;
        try {
            customerVOs = convertToCustomerVO(customerRepository.findAll());
        } catch (DataAccessException ex) {
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return customerVOs;
    }

    /**
     * save customer.
     *
     * @param currentCustomerVo currentCustomerVo
     * @return long
     * @throws CustomerException on error
     */
    @Override
    public long saveCustomer(final CustomerVO currentCustomerVo) throws CustomerException {
        Long id;
        Customer customer = convertToSingleCustomer(currentCustomerVo);
        try {
            Customer newCustomer = customerRepository.save(customer);
            id = newCustomer.getCustomerId();
            CustomerAdditionalDetails additionalDetails = convertToCustomerAdditionalDetails(
                    newCustomer.getCustomerId(), currentCustomerVo.getCustomerAdditionalDetailsVO());
            customerAdditionalDetailsRepository.save(additionalDetails);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return id;
    }

    /**
     * get customer from id.
     *
     * @param id of customer
     * @return customer vo
     * @throws CustomerException on error
     */
    @Override
    public CustomerVO getCustomerFromId(final Long id) throws CustomerException {
        CustomerVO customerVO;
        try {
            Customer customer = customerRepository.getOne(id);
            customerVO = convertToSingleCustomerVO(customer);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return customerVO;
    }

    /**
     * delete a customer from id.
     *
     * @param id of customer
     * @throws CustomerException on error
     */
    @Override
    public void deleteCustomerFromId(final Long id) throws CustomerException {
        try {
            customerRepository.deleteById(id);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
    }

    /**
     * update customer.
     *
     * @param currentCustomerVo currentCustomerVo
     * @throws CustomerException on error
     */
    @Override
    public void updateCustomer(final CustomerVO currentCustomerVo) throws CustomerException {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(
                    currentCustomerVo.getCustomerId());
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                updateCustomerWithCustomerVo(currentCustomerVo, customer);
                customerRepository.save(customer);
            }
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
    }

    private void updateCustomerWithCustomerVo(final CustomerVO currentCustomerVo, final Customer customer) {
        customer.setName(currentCustomerVo.getCustomerName());
        customer.setAddress1(currentCustomerVo.getAddress1());
        customer.setAddress2(currentCustomerVo.getAddress2());
        customer.setPhone(currentCustomerVo.getPhoneNo());
        customer.setMobile(currentCustomerVo.getMobile());
        customer.setEmail(currentCustomerVo.getEmail());
        customer.setContactPerson1(currentCustomerVo.getContactPerson1());
        customer.setContactPhone1(currentCustomerVo.getContactMobile1());
        customer.setContactPerson2(currentCustomerVo.getContactPerson2());
        customer.setContactPhone2(currentCustomerVo.getContactMobile2());
        customer.setNote(currentCustomerVo.getNotes());
        customer.setModifiedBy(currentCustomerVo.getModifiedBy());
    }

    /**
     * search customer.
     *
     * @param searchCustomerVo searchCustomerVo
     * @return list of customer vo
     * @throws CustomerException on error
     */
    @Override
    public List<CustomerVO> searchCustomer(final CustomerVO searchCustomerVo) throws CustomerException {
        List<CustomerVO> customerVOs;
        try {
            customerVOs = searchCustomerInDetail(searchCustomerVo);
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return customerVOs;
    }

    private CustomerVO convertToSingleCustomerVO(final Customer customer) {
        CustomerVO customerVO = new CustomerVO();
        customerVO.setCustomerId(customer.getCustomerId());
        customerVO.setCustomerName(customer.getName());
        customerVO.setAddress1(customer.getAddress1());
        customerVO.setAddress2(customer.getAddress2());
        customerVO.setPhoneNo(customer.getPhone());
        customerVO.setMobile(customer.getMobile());
        customerVO.setEmail(customer.getEmail());
        customerVO.setContactPerson1(customer.getContactPerson1());
        customerVO.setContactMobile1(customer.getContactPhone1());
        customerVO.setContactPerson2(customer.getContactPerson2());
        customerVO.setContactMobile2(customer.getContactPhone2());
        customerVO.setNotes(customer.getNote());
        customerVO.setCreatedBy(customer.getCreatedBy());
        customerVO.setModifiedBy(customer.getModifiedBy());
        return customerVO;
    }

    private Customer convertToSingleCustomer(final CustomerVO currentCustomerVO) {
        Customer customer = new Customer();
        customer.setName(currentCustomerVO.getCustomerName());
        customer.setAddress1(currentCustomerVO.getAddress1());
        customer.setAddress2(currentCustomerVO.getAddress2());
        customer.setPhone(currentCustomerVO.getPhoneNo());
        customer.setMobile(currentCustomerVO.getMobile());
        customer.setEmail(currentCustomerVO.getEmail());
        customer.setContactPerson1(currentCustomerVO.getContactPerson1());
        customer.setContactPhone1(currentCustomerVO.getContactMobile1());
        customer.setContactPerson2(currentCustomerVO.getContactPerson2());
        customer.setContactPhone2(currentCustomerVO.getContactMobile2());
        customer.setNote(currentCustomerVO.getNotes());
        customer.setCreatedBy(currentCustomerVO.getCreatedBy());
        customer.setModifiedBy(currentCustomerVO.getModifiedBy());
        return customer;
    }

    private List<CustomerVO> convertToCustomerVO(final List<Customer> customers) {
        List<CustomerVO> customerVOS = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setCustomerId(customer.getCustomerId());
            customerVO.setCustomerName(customer.getName());
            customerVO.setAddress1(customer.getAddress1());
            customerVO.setAddress2(customer.getAddress2());
            customerVO.setPhoneNo(customer.getPhone());
            customerVO.setMobile(customer.getMobile());
            customerVO.setEmail(customer.getEmail());
            customerVO.setContactPerson1(customer.getContactPerson1());
            customerVO.setContactMobile1(customer.getContactPhone1());
            customerVO.setContactPerson2(customer.getContactPerson2());
            customerVO.setContactMobile2(customer.getContactPhone2());
            customerVO.setNotes(customer.getNote());
            customerVO.setCreatedBy(customer.getCreatedBy());
            customerVO.setModifiedBy(customer.getModifiedBy());
            customerVOS.add(customerVO);
        }
        return customerVOS;
    }

    private List<CustomerVO> searchCustomerInDetail(final CustomerVO searchVO) {
        CriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        Root<Customer> customerRoot = criteria.from(Customer.class);
        criteria.select(customerRoot);
        if (searchVO.getIncludes().booleanValue()) {
            if (searchVO.getCustomerId() != null && searchVO.getCustomerId() > 0) {
                criteria.where(builder.like(customerRoot.get("id"), "%" + searchVO.getCustomerId() + "%"));
            }
            if (!StringUtils.isEmpty(searchVO.getCustomerName())) {
                criteria.where(builder.like(customerRoot.get("name"), "%" + searchVO.getCustomerName() + "%"));
            }
            if (!StringUtils.isEmpty(searchVO.getMobile())) {
                criteria.where(builder.like(customerRoot.get(MOBILE), "%" + searchVO.getMobile() + "%"));
            }
        } else if (searchVO.getStartsWith().booleanValue()) {
            if (searchVO.getCustomerId() != null && searchVO.getCustomerId() > 0) {
                criteria.where(builder.like(customerRoot.get("id"), searchVO.getCustomerId() + "%"));
            }
            if (!StringUtils.isEmpty(searchVO.getCustomerName())) {
                criteria.where(builder.like(customerRoot.get("name"), searchVO.getCustomerName() + "%"));
            }
            if (!StringUtils.isEmpty(searchVO.getMobile())) {
                criteria.where(builder.like(customerRoot.get(MOBILE), searchVO.getMobile() + "%"));
            }
        } else {
            if (searchVO.getCustomerId() != null && searchVO.getCustomerId() > 0) {
                criteria.where(builder.equal(customerRoot.get("id"), searchVO.getCustomerId()));
            }
            if (!StringUtils.isEmpty(searchVO.getCustomerName())) {
                criteria.where(builder.equal(customerRoot.get("name"), searchVO.getCustomerName()));
            }
            if (!StringUtils.isEmpty(searchVO.getMobile())) {
                criteria.where(builder.equal(customerRoot.get(MOBILE), searchVO.getMobile()));
            }
        }
        List<Customer> resultList = em.unwrap(Session.class).createQuery(criteria).getResultList();
        return convertToCustomerVO(resultList);
    }

    private CustomerAdditionalDetails convertToCustomerAdditionalDetails(
            final Long customerId, final CustomerAdditionalDetailsVO customerAdditionalDetailsVO) {
        CustomerAdditionalDetails additionalDetails = new CustomerAdditionalDetails();
        additionalDetails.setCustomerId(customerId);
        if (customerAdditionalDetailsVO != null) {
            additionalDetails.setContactPerson1(customerAdditionalDetailsVO.getContactPerson1());
            additionalDetails.setContactPerson2(customerAdditionalDetailsVO.getContactPerson2());
            additionalDetails.setContactPhone1(customerAdditionalDetailsVO.getContactMobile1());
            additionalDetails.setContactPhone2(customerAdditionalDetailsVO.getContactMobile2());
            additionalDetails.setNote(customerAdditionalDetailsVO.getNotes());
            additionalDetails.setCreatedBy(customerAdditionalDetailsVO.getCreatedBy());
            additionalDetails.setModifiedBy(customerAdditionalDetailsVO.getModifiedBy());
        }
        return additionalDetails;
    }
}
