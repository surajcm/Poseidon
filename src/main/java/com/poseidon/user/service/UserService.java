package com.poseidon.user.service;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.RoleRepository;
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
     * @return Set of user
     */
    public Set<User> getAllUserDetails(final String companyCode) {
        return userDAO.getAllUserDetails(companyCode);
    }

    public User save(final User user) {
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        return userDAO.save(user);
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
    public void updateUser(final User user, final String loggedInUser) {
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
    public List<User> searchUserDetails(final User searchUser,
                                          final boolean startsWith,
                                          final boolean includes) {
        return userDAO.searchUserDetails(searchUser, startsWith, includes);
    }

    public boolean comparePasswords(final String passedIn, final String currentUserPass) {
        return bcryptPasswordEncoder.matches(passedIn, currentUserPass);
    }

    public void updateWithNewPassword(final User user,
                                      final String newPass,
                                      final String currentLoggedInUser) {
        user.setPassword(bcryptPasswordEncoder.encode(newPass));
        userDAO.updateUser(user, currentLoggedInUser);
    }

    public User findUserFromName(final String name) {
        return userDAO.findUserFromName(name);
    }

    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    public void enableUser(final Long id, final boolean enabled) {
        userDAO.enableUser(id, enabled);
    }

}
