package com.poseidon.user.service.impl;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import com.poseidon.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 2:38:15 PM
 */
@Service
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String EXCEPTION_TYPE_IN_SERVICE_IMPL = "Exception type in service impl ";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    /**
     * to dao layer
     *
     * @param user user
     * @return user instance from database
     * @throws UserException on error
     */
    public UserVO logIn(UserVO user) throws UserException {
        UserVO realUser = null;
        try {
            LOG.info(" Inside user service impl");
            realUser = userDAO.logIn(user);
        } catch (UserException e) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getMessage());
        }
        return realUser;
    }

    /**
     * getAllUserDetails to list all user details
     *
     * @return List of user
     */
    public List<UserVO> getAllUserDetails() throws UserException {
        List<UserVO> userList = null;
        try {
            userList = userDAO.getAllUserDetails();
        } catch (UserException e) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getMessage());
        }
        return userList;
    }

    /**
     * create new user
     *
     * @param user user
     * @throws UserException on error
     */
    public void save(UserVO user) throws UserException {
        try {
            user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
            userDAO.save(user);
        } catch (UserException e) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getMessage());
        }
    }

    /**
     * getUserDetailsFromId to get the single user details from its id
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    public UserVO getUserDetailsFromId(Long id) throws UserException {
        UserVO userVO = null;
        try {
            userVO = userDAO.getUserDetailsFromId(id);
        } catch (UserException e) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getMessage());
        }
        return userVO;
    }


    /**
     * updates the current user details
     *
     * @param user user
     */
    public void updateUser(UserVO user) {
        try {
            userDAO.updateUser(user);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * deletes the selected user
     *
     * @param id id of the user
     */
    public void deleteUser(Long id) {
        try {
            userDAO.deleteUser(id);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * search for a list of users
     *
     * @param searchUser UserVO
     * @return List of user
     * @throws UserException on error
     */
    public List<UserVO> searchUserDetails(UserVO searchUser) throws UserException {
        List<UserVO> userList = null;
        try {
            userList = userDAO.searchUserDetails(searchUser);
        } catch (UserException e) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + e.getExceptionType());
            throw new UserException(e.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getMessage());
        }
        return userList;
    }

}
