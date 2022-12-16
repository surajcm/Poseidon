package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.RoleRepository;
import com.poseidon.user.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@SuppressWarnings("unused")
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final String EXCEPTION_TYPE_IN_SERVICE_IMPL = "Exception type in service impl {}";

    private final UserDAO userDAO;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public UserService(final UserDAO userDAO,
                       final BCryptPasswordEncoder bcryptPasswordEncoder,
                       final RoleRepository roleRepository) {
        this.userDAO = userDAO;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * getAllUserDetails to list all user details.
     *
     * @return List of user
     */
    public List<User> getAllUserDetails(final String companyCode) {
        return userDAO.getAllUserDetails(companyCode);
    }

    private UserVO convertToUserVO(final User user) {
        var userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setEmail(user.getEmail());
        userVO.setPassword(user.getPassword());
        userVO.setRoles(user.getRoles());
        userVO.setCompanyCode(user.getCompanyCode());
        userVO.setEnabled(user.getEnabled());
        return userVO;
    }

    /**
     * create new user.
     *
     * @param userVO UserVO
     */
    public void save(final UserVO userVO, final String currentLoggedInUser) {
        userVO.setPassword(bcryptPasswordEncoder.encode(userVO.getPassword()));
        userVO.setEnabled(false);
        var user = convertToUser(userVO, currentLoggedInUser);
        userDAO.save(user);
    }

    private User convertToUser(final UserVO userVO, final String currentLoggedInUser) {
        var user = new User();
        user.setName(userVO.getName());
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setEnabled(userVO.getEnabled());
        user.setRoles(userVO.getRoles());
        user.setCompanyCode(userVO.getCompanyCode());
        user.setCreatedBy(currentLoggedInUser);
        user.setModifiedBy(currentLoggedInUser);
        return user;
    }

    /**
     * getUserDetailsFromId to get the single user details from its id.
     *
     * @param id id
     * @return UserVO
     */
    public Optional<User> getUserDetailsFromId(final Long id) {
        return userDAO.getUserDetailsFromId(id);
    }


    /**
     * updates the current user details.
     *
     * @param user user
     */
    public void updateUser(final UserVO user, final String loggedInUser) {
        var originalUser = convertToUser(user, loggedInUser);
        userDAO.updateUser(originalUser, loggedInUser);
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
    public List<User> searchUserDetails(final UserVO searchUser,
                                          final boolean startsWith,
                                          final boolean includes) {
        var searcher = convertToUser(searchUser, "");
        return userDAO.searchUserDetails(searcher, startsWith, includes);
    }

    public void expireUser(final Long id) {
        userDAO.expireUser(id);
    }

    public boolean comparePasswords(final String passedIn, final String currentUserPass) {
        return bcryptPasswordEncoder.matches(passedIn, currentUserPass);
    }

    public void updateWithNewPassword(final UserVO userVO, final String newPass, final String currentLoggedInUser) {
        userVO.setPassword(bcryptPasswordEncoder.encode(newPass));
        var originalUser = convertToUser(userVO, currentLoggedInUser);
        userDAO.updateUser(originalUser, currentLoggedInUser);
    }

    public User findUserFromName(final String name) {
        return userDAO.findUserFromName(name);
    }

    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

}
