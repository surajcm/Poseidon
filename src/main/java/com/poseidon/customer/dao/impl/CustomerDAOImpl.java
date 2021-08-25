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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class CustomerDAOImpl implements CustomerDAO {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAOImpl.class);
    private static final String MOBILE = "mobile";

    private final CustomerRepository customerRepository;

    private final CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository;

    @PersistenceContext
    private EntityManager em;

    public CustomerDAOImpl(final CustomerRepository customerRepository,
                           final CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository) {
        this.customerRepository = customerRepository;
        this.customerAdditionalDetailsRepository = customerAdditionalDetailsRepository;
    }

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
        var customer = convertToSingleCustomer(currentCustomerVo);
        try {
            var newCustomer = customerRepository.save(customer);
            id = newCustomer.getCustomerId();
            //todo: this should move to controller, and finally to page
            setAdditionalDetailsToVO(currentCustomerVo);
            var additionalDetails = convertToCustomerAdditionalDetails(
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
            var customer = customerRepository.findById(id);
            if (customer.isPresent()) {
                customerVO = convertToSingleCustomerVO(customer.get());
                var additionalDetails =
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
            var additionalDetails =
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
            var optionalCustomer = customerRepository.findById(
                    currentCustomerVo.getCustomerId());
            if (optionalCustomer.isPresent()) {
                var customer = optionalCustomer.get();
                updateCustomerWithCustomerVo(currentCustomerVo, customer);
                customerRepository.save(customer);
                if (isAdditionalDetailsPresent(currentCustomerVo)) {
                    var additionalDetails =
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
        customerAdditionalDetails.setContactPerson(currentCustomerVo.getContactPerson());
        customerAdditionalDetails.setContactPhone(currentCustomerVo.getContactMobile());
        customerAdditionalDetails.setNote(currentCustomerVo.getNotes());
    }

    private void updateCustomerWithAdditionalDetails(final CustomerVO customerVO,
                                                     final CustomerAdditionalDetails customerAdditionalDetails) {
        customerVO.setContactPerson(customerAdditionalDetails.getContactPerson());
        customerVO.setContactMobile(customerAdditionalDetails.getContactPhone());
        customerVO.setNotes(customerAdditionalDetails.getNote());

    }

    private boolean isAdditionalDetailsPresent(final CustomerVO currentCustomerVo) {
        return currentCustomerVo.getContactPerson() != null || currentCustomerVo.getContactMobile() != null
                || currentCustomerVo.getNotes() != null;
    }


    private void updateCustomerWithCustomerVo(final CustomerVO currentCustomerVo, final Customer customer) {
        customer.setName(currentCustomerVo.getCustomerName());
        customer.setAddress(currentCustomerVo.getAddress());
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
        var customerVO = new CustomerVO();
        customerVO.setCustomerId(customer.getCustomerId());
        customerVO.setCustomerName(customer.getName());
        customerVO.setAddress(customer.getAddress());
        customerVO.setPhoneNo(customer.getPhone());
        customerVO.setMobile(customer.getMobile());
        customerVO.setEmail(customer.getEmail());
        customerVO.setCreatedBy(customer.getCreatedBy());
        customerVO.setModifiedBy(customer.getModifiedBy());
        return customerVO;
    }

    private Customer convertToSingleCustomer(final CustomerVO currentCustomerVO) {
        var customer = new Customer();
        customer.setName(currentCustomerVO.getCustomerName());
        customer.setAddress(currentCustomerVO.getAddress());
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
            var customerVO = new CustomerVO();
            customerVO.setCustomerId(customer.getCustomerId());
            customerVO.setCustomerName(customer.getName());
            customerVO.setAddress(customer.getAddress());
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
        var customerSpec = new CustomerSpecification();
        var search = populateSearchOperation(searchVO);
        if (!ObjectUtils.isEmpty(searchVO.getCustomerId())) {
            customerSpec.add(new SearchCriteria("customerId", searchVO.getCustomerId(), search));
        }
        if (!StringUtils.hasText(searchVO.getCustomerName())) {
            customerSpec.add(new SearchCriteria("name", searchVO.getCustomerName(), search));
        }
        if (!StringUtils.hasText(searchVO.getMobile())) {
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
        var additionalDetails = new CustomerAdditionalDetails();
        additionalDetails.setCustomerId(customerId);
        if (customerAdditionalDetailsVO != null) {
            additionalDetails.setContactPerson(customerAdditionalDetailsVO.getContactPerson());
            additionalDetails.setContactPhone(customerAdditionalDetailsVO.getContactMobile());
            additionalDetails.setNote(customerAdditionalDetailsVO.getNotes());
            additionalDetails.setCreatedBy(customerAdditionalDetailsVO.getCreatedBy());
            additionalDetails.setModifiedBy(customerAdditionalDetailsVO.getModifiedBy());
        }
        return additionalDetails;
    }

    private void setAdditionalDetailsToVO(final CustomerVO currentCustomerVo) {
        var additionalDetails = new CustomerAdditionalDetailsVO();
        additionalDetails.setContactPerson(currentCustomerVo.getContactPerson());
        additionalDetails.setContactMobile(currentCustomerVo.getContactMobile());
        additionalDetails.setNotes(currentCustomerVo.getNotes());
        additionalDetails.setCreatedBy(currentCustomerVo.getCreatedBy());
        additionalDetails.setModifiedBy(currentCustomerVo.getModifiedBy());
        currentCustomerVo.setCustomerAdditionalDetailsVO(additionalDetails);
    }
}
