package com.poseidon.user.web.form;

import com.poseidon.user.domain.UserVO;

import java.util.Collection;
import java.util.List;

/**
 * UserForm form for user.
 *
 * @author : Suraj Muraleedharan
 * Date: Feb 1, 2011
 * Time: 10:21:09 PM
 */
@SuppressWarnings("unused")
public class UserForm {
    private Long id;
    private String name;
    private String loginId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserVO getUser() {
        return user;
    }

    /**
     * for equivalent vo of form.
     *
     * @return user vo instance
     */
    public UserVO getCurrentUser() {
        UserVO userVo = new UserVO();
        userVo.setName(getName());
        userVo.setLoginId(getLoginId());
        userVo.setPassword(getPassword());
        userVo.setRole(getRole());
        return userVo;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public Collection<UserVO> getUserVOs() {
        return userVOs;
    }

    public void setUserVOs(Collection<UserVO> userVOs) {
        this.userVOs = userVOs;
    }

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

    public UserVO getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(UserVO searchUser) {
        this.searchUser = searchUser;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessageType() {
        return statusMessageType;
    }

    public void setStatusMessageType(String statusMessageType) {
        this.statusMessageType = statusMessageType;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserForm{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", loginId='"
                + loginId
                + '\''
                + ", password='"
                + password
                + '\''
                + ", role='"
                + role
                + '\''
                + ", message='"
                + message
                + '\''
                + ", user="
                + user
                + ", userVOs="
                + userVOs
                + ", loggedInUser='"
                + loggedInUser
                + '\''
                + ", loggedInRole='"
                + loggedInRole
                + '\''
                + ", searchUser="
                + searchUser
                + '}';
    }
}
