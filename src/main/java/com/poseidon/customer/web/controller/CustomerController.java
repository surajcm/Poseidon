package com.poseidon.customer.web.controller;

import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.service.CustomerService;
import com.poseidon.customer.web.form.CustomerForm;
import com.poseidon.init.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
@SuppressWarnings("unused")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final String CUSTOMER_FORM = "customerForm";
    private static final String DB_ERROR_OCCURRED =
            " An error occurred while fetching data from database. !! ";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String EXCEPTION_TYPE_IN_CONTROLLER = " Exception type in controller {}";
    private static final String CUSTOMER_FORM_IS = " CustomerForm is {}";

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * list the customers.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/List")
    public String listAllCustomers(final CustomerForm customerForm, final Model model) {
        logIncoming(customerForm);
        List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
        if (!customerVOs.isEmpty()) {
            customerVOs.forEach(customerVO -> logger.info("customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setSearchCustomerVO(new CustomerVO());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return "customer/CustomerList";
    }

    private void logIncoming(final CustomerForm customerForm) {
        logger.info("Inside list method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        logger.info("Form details are {}", sanitizedForm);
    }

    @GetMapping("/customer/getForEdit")
    public @ResponseBody
    String getForEdit(@ModelAttribute("id") final String id,
                      final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("getForEdit method of user controller : {}", sanitizedId);
        return getCustomerVOFromId(Long.valueOf(id)).map(this::convertToJson).orElse("");
    }

    /**
     * delete a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/deleteCustomer")
    public String deleteCustomer(final CustomerForm customerForm, final Model model) {
        logger.info("DeleteCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        logger.info(CUSTOMER_FORM_IS, sanitizedForm);
        try {
            customerService.deleteCustomerFromId(customerForm.getId());
            customerForm.setStatusMessage("Deleted the customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to delete the selected customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            logger.error(ex.getLocalizedMessage());
        }
        return listAllCustomers(customerForm, model);
    }

    @PostMapping("/customer/saveCustomer")
    public @ResponseBody
    String saveCustomer(@ModelAttribute("modalCustomerName") final String modalCustomerName,
                            @ModelAttribute("modalAddress") final String modalAddress,
                            @ModelAttribute("modalPhone") final String modalPhone,
                            @ModelAttribute("modalMobile") final String modalMobile,
                            @ModelAttribute("modalEmail") final String modalEmail,
                            @ModelAttribute("modalContact") final String modalContact,
                            @ModelAttribute("modalContactMobile") final String modalContactMobile,
                            @ModelAttribute("modalNotes") final String modalNotes) {
        var customerVO = new CustomerVO();
        customerVO.setCustomerName(modalCustomerName);
        customerVO.setAddress(modalAddress);
        customerVO.setPhoneNo(modalPhone);
        customerVO.setMobile(modalMobile);
        customerVO.setEmail(modalEmail);
        customerVO.setContactPerson(modalContact);
        customerVO.setContactMobile(modalContactMobile);
        customerVO.setNotes(modalNotes);
        var userName = findLoggedInUsername();
        customerVO.setCreatedBy(userName);
        customerVO.setModifiedBy(userName);
        try {
            customerService.saveCustomer(customerVO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return listCustomersAsString();
    }

    private String listCustomersAsString() {
        List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
        return convertCustomerVosToString(customerVOs);
    }

    public String findLoggedInUsername() {
        String username = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User user) {
                username = user.getUsername();
            }
        }
        return username;
    }

    private CustomerAdditionalDetailsVO populateAdditionalDetails(final CustomerVO customerVO) {
        var customerAdditionalDetailsVO = new CustomerAdditionalDetailsVO();
        customerAdditionalDetailsVO.setContactPerson(customerVO.getContactPerson());
        customerAdditionalDetailsVO.setContactMobile(customerVO.getContactMobile());
        customerAdditionalDetailsVO.setNotes(customerVO.getNotes());
        customerAdditionalDetailsVO.setCreatedBy(customerVO.getCreatedBy());
        customerAdditionalDetailsVO.setModifiedBy(customerVO.getCreatedBy());
        return customerAdditionalDetailsVO;
    }

    @PutMapping("/customer/updateCustomer")
    public @ResponseBody
    String updateCustomer(@ModelAttribute("id") final String id,
                          @ModelAttribute("modalCustomerName") final String modalCustomerName,
                          @ModelAttribute("modalAddress") final String modalAddress,
                          @ModelAttribute("modalPhone") final String modalPhone,
                          @ModelAttribute("modalMobile") final String modalMobile,
                          @ModelAttribute("modalEmail") final String modalEmail,
                          @ModelAttribute("modalContact") final String modalContact,
                          @ModelAttribute("modalContactMobile") final String modalContactMobile,
                          @ModelAttribute("modalNotes") final String modalNotes,
                          final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        var sanitizedName = CommonUtils.sanitizedString(modalCustomerName);
        var sanitizedAddress = CommonUtils.sanitizedString(modalAddress);
        var sanitizedEmail = CommonUtils.sanitizedString(modalEmail);
        logger.info("updateCustomer method of customer controller with " +
                        "id {}, name {}, email {}, address {}",
                sanitizedId, sanitizedName, sanitizedEmail, sanitizedAddress);
        try {
            var customerVO = getCustomerVOFromId(Long.valueOf(id));
            if (customerVO.isPresent()) {
                var updatedCustomer = customerVO.get();
                updatedCustomer.setCustomerName(modalCustomerName);
                updatedCustomer.setAddress(modalAddress);
                updatedCustomer.setPhoneNo(modalPhone);
                updatedCustomer.setMobile(modalMobile);
                updatedCustomer.setEmail(modalEmail);
                updatedCustomer.setContactPerson(modalContact);
                updatedCustomer.setContactMobile(modalContactMobile);
                updatedCustomer.setNotes(modalNotes);
                customerService.updateCustomer(updatedCustomer);
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        }
        return listCustomersAsString();
    }

    /**
     * search customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/searchCustomer")
    public String searchCustomer(final CustomerForm customerForm, final Model model) {
        logger.info("SearchCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        logger.info(CUSTOMER_FORM_IS, sanitizedForm);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerService.searchCustomer(customerForm.getSearchCustomerVO());
            customerForm.setStatusMessage("Found " + customerVOs.size() + " customer details");
            customerForm.setStatusMessageType("info");
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to fetch customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            logger.error(ex.getLocalizedMessage());
        }
        if (customerVOs != null) {
            customerVOs.forEach(customerVO -> logger.info(" customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return "customer/CustomerList";
    }


    @PostMapping("/customer/searchFromTransaction")
    public @ResponseBody
    List<CustomerVO> searchFromTransaction(@ModelAttribute("searchCustomerId") final String searchCustomerId,
                          @ModelAttribute("searchCustomerName") final String searchCustomerName,
                          @ModelAttribute("searchMobile") final String searchMobile,
                          final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(searchCustomerId);
        var sanitizedName = CommonUtils.sanitizedString(searchCustomerName);
        var sanitizedMobile = CommonUtils.sanitizedString(searchMobile);
        logger.info("searchFromTransaction method of customer controller with " +
                        "id {}, name {}, mobile {}",
                sanitizedId, sanitizedName, sanitizedMobile);
        var requestCustomerVO = createCustomerVO(sanitizedId, sanitizedName, sanitizedMobile);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerService.searchCustomer(requestCustomerVO);
            logger.info("Found " + customerVOs.size() + " customer details");
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        }
        if (customerVOs != null) {
            customerVOs.forEach(customerVO -> logger.info(" customerVO is {}", customerVO));
        }
        return customerVOs;
    }

    private CustomerVO createCustomerVO(final String id,
                                        final String name,
                                        final String mobile) {
        var search = new CustomerVO();
        if (StringUtils.isNotBlank(id)) {
            search.setCustomerId(Long.valueOf(id));
        }
        if (StringUtils.isNotBlank(name)) {
            search.setCustomerName(name);
        } else {
            search.setCustomerName("");
        }
        if (StringUtils.isNotBlank(mobile)) {
            search.setMobile(mobile);
        } else {
            search.setMobile("");
        }
        search.setIncludes(true);
        search.setStartsWith(true);
        return search;
    }

    @PostMapping("/customer/viewCustomer")
    public @ResponseBody
    String viewCustomer(@ModelAttribute("customerId") final String customerId) {
        var id = Long.parseLong(customerId);
        return getCustomerVOFromId(id).map(this::convertToJson).orElse("");
    }

    private String convertToJson(final CustomerVO customerVO) {
        String response;
        var mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(customerVO);
        } catch (IOException ex) {
            response = ERROR;
            logger.error(ex.getMessage());
        }
        logger.info(response);
        return response;
    }

    private String convertCustomerVosToString(final List<CustomerVO> customerVOS) {
        String response = null;
        var mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(customerVOS);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        logger.info(response);
        return response;
    }

    private Optional<CustomerVO> getCustomerVOFromId(final Long id) {
        return customerService.getCustomerFromId(id);
    }

}
