package com.poseidon.customer.dao.impl;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import com.poseidon.customer.dao.spec.CustomerSpecification;
import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.dataaccess.specs.SearchCriteria;
import com.poseidon.dataaccess.specs.SearchOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
            List<Customer> customers = new ArrayList<>();
            customerRepository.findAll().forEach(customers::add);
            customerVOs = convertToCustomerVO(customers);
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
            //todo: this should move to controller, and finally to page
            setAdditionalDetailsToVO(currentCustomerVo);
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
        CustomerVO customerVO = null;
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isPresent()) {
                customerVO = convertToSingleCustomerVO(customer.get());
                Optional<CustomerAdditionalDetails> additionalDetails =
                        customerAdditionalDetailsRepository.findByCustomerId(customer.get().getCustomerId());
                if (additionalDetails.isPresent()) {
                    updateCustomerWithAdditionalDetails(customerVO, additionalDetails.get());
                }
            }
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
            Optional<CustomerAdditionalDetails> additionalDetails =
                    customerAdditionalDetailsRepository.findByCustomerId(id);
            additionalDetails.ifPresent(customerAdditionalDetails ->
                    customerAdditionalDetailsRepository.deleteById(customerAdditionalDetails.getId()));
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
                if (isAdditionalDetailsPresent(currentCustomerVo)) {
                    Optional<CustomerAdditionalDetails> additionalDetails =
                            customerAdditionalDetailsRepository.findByCustomerId(customer.getCustomerId());
                    CustomerAdditionalDetails customerAdditionalDetails;
                    if (additionalDetails.isPresent()) {
                        customerAdditionalDetails = additionalDetails.get();
                    } else {
                        customerAdditionalDetails = new CustomerAdditionalDetails();
                        customerAdditionalDetails.setCustomerId(customer.getCustomerId());
                    }
                    updateAdditionalDetails(currentCustomerVo, customerAdditionalDetails);
                    customerAdditionalDetailsRepository.save(customerAdditionalDetails);
                }
            }
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
    }

    private void updateAdditionalDetails(final CustomerVO currentCustomerVo,
                                         final CustomerAdditionalDetails customerAdditionalDetails) {
        customerAdditionalDetails.setContactPerson(currentCustomerVo.getContactPerson1());
        customerAdditionalDetails.setContactPhone(currentCustomerVo.getContactMobile1());
        customerAdditionalDetails.setNote(currentCustomerVo.getNotes());
    }

    private void updateCustomerWithAdditionalDetails(final CustomerVO customerVO,
                                                     final CustomerAdditionalDetails customerAdditionalDetails) {
        customerVO.setContactPerson1(customerAdditionalDetails.getContactPerson());
        customerVO.setContactMobile1(customerAdditionalDetails.getContactPhone());
        customerVO.setNotes(customerAdditionalDetails.getNote());

    }

    private boolean isAdditionalDetailsPresent(final CustomerVO currentCustomerVo) {
        return currentCustomerVo.getContactPerson1() != null || currentCustomerVo.getContactPerson2() != null
                || currentCustomerVo.getContactMobile1() != null || currentCustomerVo.getContactMobile2() != null
                || currentCustomerVo.getNotes() != null;
    }


    private void updateCustomerWithCustomerVo(final CustomerVO currentCustomerVo, final Customer customer) {
        customer.setName(currentCustomerVo.getCustomerName());
        customer.setAddress1(currentCustomerVo.getAddress1());
        customer.setAddress2(currentCustomerVo.getAddress2());
        customer.setPhone(currentCustomerVo.getPhoneNo());
        customer.setMobile(currentCustomerVo.getMobile());
        customer.setEmail(currentCustomerVo.getEmail());
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
            customerVO.setCreatedBy(customer.getCreatedBy());
            customerVO.setModifiedBy(customer.getModifiedBy());
            customerVOS.add(customerVO);
        }
        return customerVOS;
    }

    private List<CustomerVO> searchCustomerInDetail(final CustomerVO searchVO) {
        CustomerSpecification customerSpec = new CustomerSpecification();
        SearchOperation search = populateSearchOperation(searchVO);
        if (!StringUtils.isEmpty(searchVO.getCustomerId())) {
            customerSpec.add(new SearchCriteria("customerId", searchVO.getCustomerId(), search));
        }
        if (!StringUtils.isEmpty(searchVO.getCustomerName())) {
            customerSpec.add(new SearchCriteria("name", searchVO.getCustomerName(), search));
        }
        if (!StringUtils.isEmpty(searchVO.getMobile())) {
            customerSpec.add(new SearchCriteria(MOBILE, searchVO.getMobile(), search));
        }
        List<Customer> resultCustomers = customerRepository.findAll(customerSpec);
        return convertToCustomerVO(resultCustomers);
    }

    private SearchOperation populateSearchOperation(final CustomerVO searchVO) {
        SearchOperation searchOperation;
        if (searchVO.getIncludes() != null && searchVO.getIncludes().booleanValue()) {
            searchOperation = SearchOperation.MATCH;
        } else if (searchVO.getStartsWith() != null && searchVO.getStartsWith().booleanValue()) {
            searchOperation = SearchOperation.MATCH_START;
        } else {
            searchOperation = SearchOperation.EQUAL;
        }
        return searchOperation;
    }

    private CustomerAdditionalDetails convertToCustomerAdditionalDetails(
            final Long customerId, final CustomerAdditionalDetailsVO customerAdditionalDetailsVO) {
        CustomerAdditionalDetails additionalDetails = new CustomerAdditionalDetails();
        additionalDetails.setCustomerId(customerId);
        if (customerAdditionalDetailsVO != null) {
            additionalDetails.setContactPerson(customerAdditionalDetailsVO.getContactPerson1());
            additionalDetails.setContactPhone(customerAdditionalDetailsVO.getContactMobile1());
            additionalDetails.setNote(customerAdditionalDetailsVO.getNotes());
            additionalDetails.setCreatedBy(customerAdditionalDetailsVO.getCreatedBy());
            additionalDetails.setModifiedBy(customerAdditionalDetailsVO.getModifiedBy());
        }
        return additionalDetails;
    }

    private void setAdditionalDetailsToVO(final CustomerVO currentCustomerVo) {
        CustomerAdditionalDetailsVO additionalDetails = new CustomerAdditionalDetailsVO();
        additionalDetails.setContactPerson1(currentCustomerVo.getContactPerson1());
        additionalDetails.setContactPerson2(currentCustomerVo.getContactPerson2());
        additionalDetails.setContactMobile1(currentCustomerVo.getContactMobile1());
        additionalDetails.setContactMobile2(currentCustomerVo.getContactMobile2());
        additionalDetails.setNotes(currentCustomerVo.getNotes());
        additionalDetails.setCreatedBy(currentCustomerVo.getCreatedBy());
        additionalDetails.setModifiedBy(currentCustomerVo.getModifiedBy());
        currentCustomerVo.setCustomerAdditionalDetailsVO(additionalDetails);
    }
}
