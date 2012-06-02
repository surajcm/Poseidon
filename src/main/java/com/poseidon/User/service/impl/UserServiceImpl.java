package com.poseidon.User.service.impl;

import com.poseidon.User.dao.UserDAO;
import com.poseidon.User.domain.UserVO;
import com.poseidon.User.exception.UserException;
import com.poseidon.User.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @author : Suraj Muraleedharan
 *         Date: Nov 27, 2010
 *         Time: 2:38:15 PM
 */
public class UserServiceImpl implements UserService {

    /**
     * user service instance
     */
    private UserDAO userDAO;

    private final Log log = LogFactory.getLog(UserServiceImpl.class);

    /**
     * spring setter for user dao
     *
     * @param userDAO userDAO instance
     */
    @SuppressWarnings("unused")
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * to dao layer
     *
     * @param user user
     * @return User instance from database
     * @throws UserException on error
     */
    public UserVO logIn(UserVO user) throws UserException {
        UserVO realUser = null;
        try {
            log.info(" Inside user service impl");
            realUser = userDAO.logIn(user);
        } catch (UserException e) {
            log.error(" Exception type in service impl " + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return realUser;
    }

    /**
     * getAllUserDetails to list all user details
     *
     * @return List of User
     */
    public List<UserVO> getAllUserDetails() throws UserException {
        List<UserVO> userList = null;
        try {
            userList = userDAO.getAllUserDetails();
        } catch (UserException e) {
            log.error(" Exception type in service impl " + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return userList;
    }

    /**
     * create new user
     *
     * @param user user
     * @throws UserException on error
     */
    public void addNewUser(UserVO user) throws UserException {
        try {
            userDAO.addNewUser(user);
        } catch (UserException e) {
            log.error(" Exception type in service impl " + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * getUserDetailsFromID to get the single user details from its id
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    public UserVO getUserDetailsFromID(Long id) throws UserException {
        UserVO userVO = null;
        try {
            userVO = userDAO.getUserDetailsFromID(id);
        } catch (UserException e) {
            log.error(" Exception type in service impl " + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return userVO;
    }


    /**
     * updates the current user details
     *
     * @param user user
     * @throws UserException on error
     */
    public void UpdateUser(UserVO user) throws UserException {
        try {
            userDAO.updateUser(user);
        } catch (UserException e) {
            log.error(" Exception type in service impl " + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * deletes the selected user
     *
     * @param id id of the user
     * @throws UserException on error
     */
    public void deleteUser(Long id) throws UserException {
        try {
            userDAO.deleteUser(id);
        } catch (UserException e) {
            log.error(" Exception type in service impl " + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * search for a list of users
     *
     * @param searchUser UserVO
     * @return List of User
     * @throws UserException on error
     */
    public List<UserVO> searchUserDetails(UserVO searchUser) throws UserException {
        List<UserVO> userList = null;
        try {
            userList = userDAO.searchUserDetails(searchUser);
        } catch (UserException e) {
            log.error(" Exception type in service impl " + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return userList;
    }

}
