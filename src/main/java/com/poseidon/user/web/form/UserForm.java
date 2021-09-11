package com.poseidon.user.web.form;

import com.poseidon.user.domain.UserVO;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

@SuppressWarnings("unused")
public class UserForm {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String message;
    private UserVO user;
    private Collection<UserVO> userVOs;
    private String loggedInUser;
    private String loggedInRole;
    private UserVO searchUser;
    private String statusMessage;
    private String statusMessageType;
    private List<String> roleList;
    private boolean startsWith;
    private boolean includes;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(final UserVO user) {
        this.user = user;
    }

    public Collection<UserVO> getUserVOs() {
        return userVOs;
    }

    public void setUserVOs(final Collection<UserVO> userVOs) {
        this.userVOs = userVOs;
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

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(final List<String> roleList) {
        this.roleList = roleList;
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

    /**
     * for equivalent vo of form.
     *
     * @return user vo instance
     */
    public UserVO getCurrentUser() {
        UserVO userVo = new UserVO();
        userVo.setName(getName());
        userVo.setEmail(getEmail());
        userVo.setPassword(getPassword());
        userVo.setRole(getRole());
        return userVo;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserForm.class.getSimpleName() + "[", "]")
                .add("id=" + id).add("name='" + name + "'").add("email='" + email + "'")
                .add("password='" + password + "'").add("role='" + role + "'")
                .add("message='" + message + "'").add("user=" + user).add("userVOs=" + userVOs)
                .add("loggedInUser='" + loggedInUser + "'").add("loggedInRole='" + loggedInRole + "'")
                .add("searchUser=" + searchUser).add("statusMessage='" + statusMessage + "'")
                .add("statusMessageType='" + statusMessageType + "'").add("roleList=" + roleList)
                .toString();
    }
}
