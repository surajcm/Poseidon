package com.poseidon.Invoice.web.form;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:39 PM
 */
public class InvoiceForm {
    private String loggedInUser;
    private String loggedInRole;

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }
}
