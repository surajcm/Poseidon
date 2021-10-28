package com.poseidon.customer.web.controller;

import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.service.CustomerService;
import com.poseidon.customer.web.form.CustomerForm;
import com.poseidon.init.util.CommonUtils;
import com.poseidon.transaction.web.form.TransactionForm;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;


@Controller
//@RequestMapping("/customer")
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
    @PostMapping("/customer/List.htm")
    public ModelAndView list(final CustomerForm customerForm) {
        logIncoming(customerForm);
        List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
        if (!customerVOs.isEmpty()) {
            customerVOs.forEach(customerVO -> logger.info("customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setSearchCustomerVO(new CustomerVO());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", CUSTOMER_FORM, customerForm);
    }

    private void logIncoming(final CustomerForm customerForm) {
        logger.info("Inside list method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        logger.info("Form details are {}", sanitizedForm);
    }

    /**
     * edit a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/editCust.htm")
    public ModelAndView editCustomer(final CustomerForm customerForm) {
        logIncomingEdit(customerForm);
        var customerVO = getCustomerVOFromId(customerForm.getId());
        if (customerVO.isPresent()) {
            logger.info(" customerVO details are {}", customerVO.get());
            customerForm.setCurrentCustomerVO(customerVO.get());
        } else {
            logger.error(" No details found for current makeVO !!");
        }
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        return new ModelAndView("customer/EditCustomer", CUSTOMER_FORM, customerForm);
    }

    @GetMapping("/customer/getForEdit.htm")
    public @ResponseBody
    String getForEdit(@ModelAttribute("id") final String id,
                      final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("getForEdit method of user controller : {}", sanitizedId);
        String response = null;
        var customerVO = getCustomerVOFromId(Long.valueOf(id));
        if (customerVO.isPresent()) {
            response = convertToJson(customerVO.get());
        }
        return response;
    }

    private void logIncomingEdit(final CustomerForm customerForm) {
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        logger.info("EditCustomer method of CustomerController ");
        logger.info("customerForm is {}", sanitizedForm);
        if (customerForm.getCurrentCustomerVO() != null) {
            var sanitizedCustomerVO = CommonUtils.sanitizedString(
                    customerForm.getCurrentCustomerVO().toString());
            logger.info("customerVO is {}", sanitizedCustomerVO);
        }
    }

    /**
     * edit a customer.
     *
     * @param transactionForm transactionForm
     * @return view
     */
    @PostMapping("/customer/editCustomer.htm")
    public ModelAndView editCustomerOnTransaction(final TransactionForm transactionForm) {
        logger.info("EditCustomer method of TransactionController ");
        var sanitizedForm = CommonUtils.sanitizedString(transactionForm.toString());
        logger.info("TransactionForm values are {}", sanitizedForm);
        var customerForm = new CustomerForm();
        if (transactionForm.getCustomerVO() != null && transactionForm.getCustomerVO().getCustomerId() > 0) {
            customerForm.setId(transactionForm.getCustomerVO().getCustomerId());
            return editCustomer(customerForm);
        } else {
            return new ModelAndView("ErrorPage", "userForm", customerForm);
        }
    }

    /**
     * delete a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/deleteCust.htm")
    public ModelAndView deleteCustomer(final CustomerForm customerForm) {
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
        return list(customerForm);
    }

    /**
     * save the customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/saveCustomer.htm")
    public ModelAndView saveCustomer(final CustomerForm customerForm) {
        logger.info("SaveCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        logger.info(CUSTOMER_FORM_IS, sanitizedForm);
        try {
            var customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setCreatedBy(customerForm.getLoggedInUser());
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            //populate customer additional details
            //todo: this need to be corrected at ui level later
            customerVO.setCustomerAdditionalDetailsVO(populateAdditionalDetails(customerVO));
            customerService.saveCustomer(customerVO);
            customerForm.setStatusMessage("Added the new customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to add the new customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            logger.error(ex.getLocalizedMessage());
        }
        return list(customerForm);
    }

    @PostMapping("/customer/saveCustomerAjax.htm")
    public @ResponseBody
    String saveCustomerAjax(@ModelAttribute("modalCustomerName") final String modalCustomerName,
                            @ModelAttribute("modalAddress") final String modalAddress,
                            @ModelAttribute("modalPhone") final String modalPhone,
                            @ModelAttribute("modalMobile") final String modalMobile,
                            @ModelAttribute("modalEmail") final String modalEmail,
                            @ModelAttribute("modalContact") final String modalContact,
                            @ModelAttribute("modalContactMobile") final String modalContactMobile,
                            @ModelAttribute("modalNotes") final String modalNotes) {
        var response = "";
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
            List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
            response = convertCustomerVosToString(customerVOs);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return response;
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

    /**
     * update a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/updateCustomer.htm")
    public ModelAndView updateCustomer(final CustomerForm customerForm) {
        logger.info("UpdateCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        logger.info(CUSTOMER_FORM_IS, sanitizedForm);
        try {
            var customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            customerService.updateCustomer(customerVO);
            customerForm.setStatusMessage("Updated the selected customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to update the selected customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            logger.error(ex.getLocalizedMessage());
        }
        return list(customerForm);
    }

    /**
     * search customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/searchCustomer.htm")
    public ModelAndView searchCustomer(final CustomerForm customerForm) {
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
        return new ModelAndView("customer/CustomerList", CUSTOMER_FORM, customerForm);
    }

    @PostMapping("/customer/viewCustomer.htm")
    public @ResponseBody
    String viewCustomer(@ModelAttribute("customerId") final String customerId) {
        String response = null;
        var id = Long.parseLong(customerId);
        var customerVO = getCustomerVOFromId(id);
        if (customerVO.isPresent()) {
            response = convertToJson(customerVO.get());
        }
        return response;
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
