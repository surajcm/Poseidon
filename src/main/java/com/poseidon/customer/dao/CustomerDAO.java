package com.poseidon.customer.dao;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import com.poseidon.customer.dao.repo.CustomerAdditionalDetailsRepository;
import com.poseidon.customer.dao.repo.CustomerRepository;
import com.poseidon.customer.dao.spec.CustomerSpecification;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.init.specs.SearchCriteria;
import com.poseidon.init.specs.SearchOperation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.poseidon.init.Constants.PAGE_SIZE;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

@Service
@SuppressWarnings("unused")
public class CustomerDAO {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAO.class);
    private static final String MOBILE = "mobile";

    private final CustomerRepository customerRepository;

    private final CustomerAdditionalDetailsRepository detailsRepository;

    @PersistenceContext
    private EntityManager em;

    public CustomerDAO(final CustomerRepository customerRepository,
                       final CustomerAdditionalDetailsRepository detailsRepository) {
        this.customerRepository = customerRepository;
        this.detailsRepository = detailsRepository;
    }

    /**
     * list all customer details.
     *
     * @return list customer vo
     */
    public List<Customer> listAllCustomerDetails() {
        return allCustomers();
    }

    private List<Customer> allCustomers() {
        var iterable = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        iterable.forEach(customers::add);
        return customers;
    }

    public Page<Customer> listAll(final int pageNumber) {
        var pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return customerRepository.findAll(pageable);
    }

    /**
     * get customer from id.
     *
     * @param id of customer
     * @return customer vo
     */
    public Optional<CustomerVO> getCustomerFromId(final Long id) {
        var customer = sneak(() -> customerRepository.findById(id));
        Optional<CustomerVO> customerVOWithDetails = Optional.empty();
        var customerVO = customer.map(this::convertToSingleCustomerVO);
        if (customer.isPresent()) {
            var additionalDetails = getAdditionalDetailsOfCustomerId(customer.get().getId());
            additionalDetails.ifPresent(
                    customerAdditionalDetails ->
                            updateCustomerWithAdditionalDetails(customerVO.get(), customerAdditionalDetails));
            customerVOWithDetails = customerVO;
        }
        return customerVOWithDetails;
    }

    /**
     * save customer.
     *
     * @param customer             Customer
     * @param newAdditionalDetails CustomerAdditionalDetails
     * @return Customer
     */
    public Customer saveCustomer(final Customer customer,
                                 final CustomerAdditionalDetails newAdditionalDetails) {
        var newCustomer = sneak(() -> customerRepository.save(customer));
        newAdditionalDetails.setCustomerId(newCustomer.getId());
        saveAdditionalDetails(newAdditionalDetails);
        return newCustomer;
    }

    /**
     * search customer.
     *
     * @param searchCustomerVo searchCustomerVo
     * @return list of customer vo
     */
    public List<CustomerVO> searchCustomer(final CustomerVO searchCustomerVo) {
        var customerSpec = new CustomerSpecification();
        var search = populateSearchOperation(searchCustomerVo);
        if (!ObjectUtils.isEmpty(searchCustomerVo.getCustomerId())) {
            customerSpec.add(new SearchCriteria("id", searchCustomerVo.getCustomerId(), SearchOperation.EQUAL));
        }
        if (!searchCustomerVo.getCustomerName().isBlank()) {
            customerSpec.add(new SearchCriteria("name", searchCustomerVo.getCustomerName(), search));
        }
        if (!searchCustomerVo.getMobile().isBlank()) {
            customerSpec.add(new SearchCriteria(MOBILE, searchCustomerVo.getMobile(), search));
        }
        var resultCustomers = customerRepository.findAll(customerSpec);
        return convertToCustomerVO(resultCustomers);
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
                sneak(() -> detailsRepository.save(customerAdditionalDetails));
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
        return sneak(() -> detailsRepository.findByCustomerId(id));
    }

    private void saveAdditionalDetails(final CustomerAdditionalDetails newAdditionalDetails) {
        sneak(() -> detailsRepository.save(newAdditionalDetails));
    }


    private void deleteAdditionalDetails(final Long id) {
        var additionalDetails = getAdditionalDetailsOfCustomerId(id);
        additionalDetails.ifPresent(details ->
                detailsRepository.deleteById(details.getId()));
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
}
