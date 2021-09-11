package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@SuppressWarnings("unused")
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final String EXCEPTION_TYPE_IN_SERVICE_IMPL = "Exception type in service impl {}";

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public UserService(final UserDAO userDAO, final BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    /**
     * getAllUserDetails to list all user details.
     *
     * @return List of user
     */
    public List<UserVO> getAllUserDetails() {
        return userDAO.getAllUserDetails();
    }

    /**
     * create new user.
     *
     * @param user user
     */
    public void save(final UserVO user, final String currentLoggedInUser) {
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        user.setExpired(false);
        userDAO.save(user, currentLoggedInUser);
    }

    /**
     * getUserDetailsFromId to get the single user details from its id.
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    public UserVO getUserDetailsFromId(final Long id) throws UserException {
        UserVO userVO;
        try {
            userVO = userDAO.getUserDetailsFromId(id);
        } catch (UserException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new UserException(ex.getExceptionType());
        }
        return userVO;
    }


    /**
     * updates the current user details.
     *
     * @param user user
     */
    public void updateUser(final UserVO user, final String loggedInUser) {
        try {
            userDAO.updateUser(user, loggedInUser);
        } catch (UserException ex) {
            LOG.error(ex.getMessage());
        }
    }

    /**
     * deletes the selected user.
     *
     * @param id id of the user
     */
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
    public List<UserVO> searchUserDetails(final UserVO searchUser,
                                          final boolean startswith,
                                          final boolean includes) throws UserException {
        List<UserVO> userList;
        try {
            userList = userDAO.searchUserDetails(searchUser, startswith, includes);
        } catch (UserException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new UserException(ex.getExceptionType());
        }
        return userList;
    }

    public void expireUser(final Long id) {
        try {
            userDAO.expireUser(id);
        } catch (UserException ex) {
            LOG.error(ex.getMessage());
        }
    }

    public boolean comparePasswords(final String passedIn, final String currentUserPass) {
        return bcryptPasswordEncoder.matches(passedIn, currentUserPass);
    }

    public void updateWithNewPassword(final UserVO userVO, final String newPass, final String currentLoggedInUser) {
        userVO.setPassword(bcryptPasswordEncoder.encode(newPass));
        try {
            userDAO.updateUser(userVO, currentLoggedInUser);
        } catch (UserException ex) {
            LOG.error(ex.getMessage());
        }
    }

}
