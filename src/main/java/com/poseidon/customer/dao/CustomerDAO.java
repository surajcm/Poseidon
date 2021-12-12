package com.poseidon.customer.dao;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import com.poseidon.customer.dao.repo.CustomerAdditionalDetailsRepository;
import com.poseidon.customer.dao.repo.CustomerRepository;
import com.poseidon.customer.dao.spec.CustomerSpecification;
import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.init.specs.SearchCriteria;
import com.poseidon.init.specs.SearchOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

@Service
@SuppressWarnings("unused")
public class CustomerDAO {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAO.class);
    private static final String MOBILE = "mobile";

    private final CustomerRepository customerRepository;

    private final CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository;

    @PersistenceContext
    private EntityManager em;

    public CustomerDAO(final CustomerRepository customerRepository,
                       final CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository) {
        this.customerRepository = customerRepository;
        this.customerAdditionalDetailsRepository = customerAdditionalDetailsRepository;
    }

    /**
     * list all customer details.
     *
     * @return list customer vo
     */
    public List<CustomerVO> listAllCustomerDetails() {
        var customers = sneak(() ->
                StreamSupport.stream(customerRepository.findAll().spliterator(), true)
                        .toList());
        return convertToCustomerVO(customers);
    }

    /**
     * get customer from id.
     *
     * @param id of customer
     * @return customer vo
     */
    public Optional<CustomerVO> getCustomerFromId(final Long id) {
        return sneak(() -> customerRepository.findById(id))
                .map(this::convertToSingleCustomerVO)
                .map(this::getAdditionalDetailsToVO);
    }

    /**
     * save customer.
     *
     * @param currentCustomerVo currentCustomerVo
     * @return long
     */
    public CustomerVO saveCustomer(final CustomerVO currentCustomerVo) {
        var customer = convertToSingleCustomer(currentCustomerVo);
        var newCustomer = sneak(() -> customerRepository.save(customer));
        saveAdditionalDetails(currentCustomerVo, newCustomer);
        return convertToSingleCustomerVO(newCustomer);
    }

    /**
     * search customer.
     *
     * @param searchCustomerVo searchCustomerVo
     * @return list of customer vo
     */
    public List<CustomerVO> searchCustomer(final CustomerVO searchCustomerVo) {
        return searchCustomerInDetail(searchCustomerVo);
    }

    /**
     * update customer.
     *
     * @param currentCustomerVo currentCustomerVo
     * @throws CustomerException on error
     */
    public void updateCustomer(final CustomerVO currentCustomerVo) throws CustomerException {
        var optionalCustomer = getCustomer(currentCustomerVo.getCustomerId());
        if (optionalCustomer.isPresent()) {
            var customer = optionalCustomer.get();
            updateCustomerWithCustomerVo(currentCustomerVo, customer);
            sneak(() -> customerRepository.save(customer));
            if (isAdditionalDetailsPresent(currentCustomerVo)) {
                CustomerAdditionalDetails customerAdditionalDetails = populateDetails(customer);
                updateAdditionalDetails(currentCustomerVo, customerAdditionalDetails);
                sneak(() -> customerAdditionalDetailsRepository.save(customerAdditionalDetails));
            }
        }
    }

    /**
     * delete a customer from id.
     *
     * @param id of customer
     */
    public void deleteCustomerFromId(final Long id) {
        deleteAdditionalDetails(id);
        var consumer = sneaked(customerRepository::deleteById);
        consumer.accept(id);
    }

    private Optional<CustomerAdditionalDetails> getAdditionalDetailsOfCustomerId(final Long id) {
        return sneak(() -> customerAdditionalDetailsRepository.findByCustomerId(id));
    }

    private CustomerVO getAdditionalDetailsToVO(final CustomerVO customerVO) {
        var additionalDetails =
                getAdditionalDetailsOfCustomerId(customerVO.getCustomerId());
        additionalDetails.ifPresent(customerAdditionalDetails ->
                updateCustomerWithAdditionalDetails(customerVO, customerAdditionalDetails));
        return customerVO;
    }

    private void saveAdditionalDetails(final CustomerVO currentCustomerVo, final Customer newCustomer) {
        var additionalDetails = setAdditionalDetailsToVO(currentCustomerVo);
        currentCustomerVo.setCustomerAdditionalDetailsVO(additionalDetails);
        var newAdditionalDetails = convertToCustomerAdditionalDetails(
                newCustomer.getId(), currentCustomerVo.getCustomerAdditionalDetailsVO());
        sneak(() -> customerAdditionalDetailsRepository.save(newAdditionalDetails));
    }


    private void deleteAdditionalDetails(final Long id) {
        var additionalDetails = getAdditionalDetailsOfCustomerId(id);
        additionalDetails.ifPresent(details ->
                customerAdditionalDetailsRepository.deleteById(details.getId()));
    }

    private Optional<Customer> getCustomer(final Long id) {
        return sneak(() -> customerRepository.findById(id));
    }

    private CustomerAdditionalDetails populateDetails(final Customer customer) {
        var additionalDetails =
                getAdditionalDetailsOfCustomerId(customer.getId());
        return additionalDetails.orElseGet(() -> this.buildNew(customer.getId()));
    }

    private CustomerAdditionalDetails buildNew(final Long id) {
        CustomerAdditionalDetails customerAdditionalDetails = new CustomerAdditionalDetails();
        customerAdditionalDetails.setCustomerId(id);
        return customerAdditionalDetails;
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

    private CustomerVO convertToSingleCustomerVO(final Customer customer) {
        var customerVO = new CustomerVO();
        customerVO.setCustomerId(customer.getId());
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
        return customers.stream().map(this::convertToSingleCustomerVO).toList();
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
        List<Customer> resultCustomers = sneak(() -> customerRepository.findAll(customerSpec));
        return convertToCustomerVO(resultCustomers);
    }

    private SearchOperation populateSearchOperation(final CustomerVO searchVO) {
        SearchOperation searchOperation;
        if (searchVO.getIncludes() != null && Boolean.TRUE.equals(searchVO.getIncludes())) {
            searchOperation = SearchOperation.MATCH;
        } else if (searchVO.getStartsWith() != null && Boolean.TRUE.equals(searchVO.getStartsWith())) {
            searchOperation = SearchOperation.MATCH_START;
        } else {
            searchOperation = SearchOperation.EQUAL;
        }
        return searchOperation;
    }

    private CustomerAdditionalDetails convertToCustomerAdditionalDetails(
            final Long customerId,
            final CustomerAdditionalDetailsVO customerAdditionalDetailsVO) {
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

    private CustomerAdditionalDetailsVO setAdditionalDetailsToVO(final CustomerVO currentCustomerVo) {
        var additionalDetails = new CustomerAdditionalDetailsVO();
        additionalDetails.setContactPerson(currentCustomerVo.getContactPerson());
        additionalDetails.setContactMobile(currentCustomerVo.getContactMobile());
        additionalDetails.setNotes(currentCustomerVo.getNotes());
        additionalDetails.setCreatedBy(currentCustomerVo.getCreatedBy());
        additionalDetails.setModifiedBy(currentCustomerVo.getModifiedBy());
        return additionalDetails;
    }
}
