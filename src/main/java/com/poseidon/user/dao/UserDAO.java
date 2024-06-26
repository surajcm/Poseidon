package com.poseidon.user.dao;

import com.poseidon.init.specs.SearchCriteria;
import com.poseidon.init.specs.SearchOperation;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.UserRepository;
import com.poseidon.user.dao.spec.UserSpecification;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.poseidon.init.Constants.PAGE_SIZE;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

@Repository
@Transactional
@SuppressWarnings("unused")
public class UserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

    private final UserRepository userRepository;

    public UserDAO(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(final int pageNumber, final String companyCode) {
        var pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        if (companyCode == null) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findByCompanyCode(pageable, companyCode);
    }

    /**
     * getAllUserDetails to list all user details.
     *
     * @return Set of user
     */
    public Set<User> getAllUserDetails(final String companyCode) {
        return new HashSet<>(sneak(() -> userRepository.findByCompanyCode(companyCode)));
    }

    /**
     * save to add a new user.
     *
     * @param user user
     */
    public User save(final User user) {
        return userRepository.save(user);
    }

    /**
     * getUserDetailsFromId to get the single user details from its id.
     *
     * @param id id
     * @return UserVO
     */
    public Optional<User> getUserDetailsFromId(final Long id) {
        return sneak(() -> userRepository.findById(id));
    }

    /**
     * updateUser.
     *
     * @param userToUpdate user
     */
    public User updateUser(final User userToUpdate, final String loggedInUser) {
        userToUpdate.setModifiedBy(loggedInUser);
        return userRepository.save(userToUpdate);
    }

    /**
     * delete user.
     *
     * @param id id
     */
    public void deleteUser(final Long id) {
        var consumer = sneaked(userRepository::deleteById);
        consumer.accept(id);
    }

    public Optional<User> findByEmail(final String email) {
        return sneak(() -> userRepository.findByEmail(email));
    }

    /**
     * search for a list of users.
     *
     * @param searchUser UserVO
     * @return List of user
     */
    public Page<User> searchUserDetails(final User searchUser,
                                        final boolean startsWith,
                                        final boolean includes,
                                        final int pageNumber) {
        return sneak(() -> searchAllUsers(searchUser, startsWith, includes, pageNumber));
    }

    /**
     * search for all the user details from the database.
     *
     * @param searchUser UserVO
     * @return List of users
     * @throws DataAccessException on error
     */
    private Page<User> searchAllUsers(final User searchUser,
                                      final boolean startsWith,
                                      final boolean includes,
                                      final int pageNumber) {
        var userSpec = new UserSpecification();
        var search = populateSearchOperation(startsWith, includes);
        if (StringUtils.hasText(searchUser.getName())) {
            userSpec.add(new SearchCriteria("name", searchUser.getName(), search));
        }
        if (StringUtils.hasText(searchUser.getEmail())) {
            userSpec.add(new SearchCriteria("email", searchUser.getEmail(), search));
        }
        var pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return userRepository.findAll(userSpec, pageable);
    }

    private SearchOperation populateSearchOperation(final boolean startsWith,
                                                    final boolean includes) {
        SearchOperation searchOperation;
        if (includes) {
            searchOperation = SearchOperation.MATCH;
        } else if (startsWith) {
            searchOperation = SearchOperation.MATCH_START;
        } else {
            searchOperation = SearchOperation.EQUAL;
        }
        return searchOperation;
    }

    public User findUserFromName(final String name) {
        return userRepository.findByName(name);
    }

    public void enableUser(final Long id, final boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }
}
