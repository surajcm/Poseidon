package com.poseidon.user.web.form;

import com.poseidon.user.dao.entities.User;
import com.poseidon.user.domain.UserVO;

import java.util.Set;
import java.util.StringJoiner;

@SuppressWarnings("unused")
public class UserForm {
    private Long id;
    private UserVO user;
    private Set<UserVO> userVOs;
    private Set<User> users;
    private String loggedInUser;
    private String loggedInRole;
    private UserVO searchUser;
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

    public UserVO getUser() {
        return user;
    }

    public void setUser(final UserVO user) {
        this.user = user;
    }

    public Set<UserVO> getUserVOs() {
        return userVOs;
    }

    public void setUserVOs(final Set<UserVO> userVOs) {
        this.userVOs = userVOs;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(final Set<User> users) {
        this.users = users;
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

    public UserVO getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(final UserVO searchUser) {
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
                .add("id=" + id).add("user=" + user).add("userVOs=" + userVOs)
                .add("loggedInUser='" + loggedInUser + "'").add("loggedInRole='" + loggedInRole + "'")
                .add("searchUser=" + searchUser).add("statusMessage='" + statusMessage + "'")
                .add("statusMessageType='" + statusMessageType + "'")
                .toString();
    }
}
