package com.poseidon.user.web.form;

import com.poseidon.user.dao.entities.User;

import java.util.StringJoiner;

@SuppressWarnings("unused")
public class UserForm {
    private Long id;
    private String loggedInUser;
    private String loggedInRole;
    private User searchUser;
    private String statusMessage;
    private String statusMessageType;
    private boolean startsWith;
    private boolean includes;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(final String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(final String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public User getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(final User searchUser) {
        this.searchUser = searchUser;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessageType() {
        return statusMessageType;
    }

    public void setStatusMessageType(final String statusMessageType) {
        this.statusMessageType = statusMessageType;
    }

    public boolean isStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final boolean startsWith) {
        this.startsWith = startsWith;
    }

    public boolean isIncludes() {
        return includes;
    }

    public void setIncludes(final boolean includes) {
        this.includes = includes;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", UserForm.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("loggedInUser='" + loggedInUser + "'").add("loggedInRole='" + loggedInRole + "'")
                .add("searchUser=" + searchUser).add("statusMessage='" + statusMessage + "'")
                .add("statusMessageType='" + statusMessageType + "'")
                .toString();
    }
}
