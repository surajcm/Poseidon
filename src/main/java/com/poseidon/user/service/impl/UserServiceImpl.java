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
     * getAllUserDetails to list all user details.
     *
     * @return List of user
     */
    @Override
    public List<UserVO> getAllUserDetails() throws UserException {
        List<UserVO> userList;
        try {
            userList = userDAO.getAllUserDetails();
        } catch (UserException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + ex.getExceptionType());
            throw new UserException(ex.getExceptionType());
        }
        return userList;
    }

    /**
     * create new user.
     *
     * @param user user
     * @throws UserException on error
     */
    @Override
    public void save(final UserVO user) throws UserException {
        try {
            user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
            userDAO.save(user);
        } catch (UserException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + ex.getExceptionType());
            throw new UserException(ex.getExceptionType());
        }
    }

    /**
     * getUserDetailsFromId to get the single user details from its id.
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    @Override
    public UserVO getUserDetailsFromId(final Long id) throws UserException {
        UserVO userVO;
        try {
            userVO = userDAO.getUserDetailsFromId(id);
        } catch (UserException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + ex.getExceptionType());
            throw new UserException(ex.getExceptionType());
        }
        return userVO;
    }


    /**
     * updates the current user details.
     *
     * @param user user
     */
    @Override
    public void updateUser(final UserVO user) {
        try {
            userDAO.updateUser(user);
        } catch (UserException ex) {
            LOG.error(ex.getMessage());
        }
    }

    /**
     * deletes the selected user.
     *
     * @param id id of the user
     */
    @Override
    public void deleteUser(final Long id) {
        try {
            userDAO.deleteUser(id);
        } catch (UserException ex) {
            LOG.error(ex.getMessage());
        }
    }

    /**
     * search for a list of users.
     *
     * @param searchUser UserVO
     * @return List of user
     * @throws UserException on error
     */
    @Override
    public List<UserVO> searchUserDetails(final UserVO searchUser) throws UserException {
        List<UserVO> userList;
        try {
            userList = userDAO.searchUserDetails(searchUser);
        } catch (UserException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL + ex.getExceptionType());
            throw new UserException(ex.getExceptionType());
        }
        return userList;
    }

}
