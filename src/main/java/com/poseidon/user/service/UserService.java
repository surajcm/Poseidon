package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
     */
    public Optional<UserVO> getUserDetailsFromId(final Long id) {
        return userDAO.getUserDetailsFromId(id);
    }


    /**
     * updates the current user details.
     *
     * @param user user
     */
    public void updateUser(final UserVO user, final String loggedInUser) {
        userDAO.updateUser(user, loggedInUser);
    }

    /**
     * deletes the selected user.
     *
     * @param id id of the user
     */
    public void deleteUser(final Long id) {
        userDAO.deleteUser(id);
    }

    /**
     * search for a list of users.
     *
     * @param searchUser UserVO
     * @return List of user
     */
    public List<UserVO> searchUserDetails(final UserVO searchUser,
                                          final boolean startsWith,
                                          final boolean includes) {
        return userDAO.searchUserDetails(searchUser, startsWith, includes);
    }

    public void expireUser(final Long id) {
        userDAO.expireUser(id);
    }

    public boolean comparePasswords(final String passedIn, final String currentUserPass) {
        return bcryptPasswordEncoder.matches(passedIn, currentUserPass);
    }

    public void updateWithNewPassword(final UserVO userVO, final String newPass, final String currentLoggedInUser) {
        userVO.setPassword(bcryptPasswordEncoder.encode(newPass));
        userDAO.updateUser(userVO, currentLoggedInUser);
    }

}
