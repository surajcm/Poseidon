package com.poseidon.user.dao;

import com.poseidon.init.specs.SearchCriteria;
import com.poseidon.init.specs.SearchOperation;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.UserRepository;
import com.poseidon.user.dao.spec.UserSpecification;
import com.poseidon.user.domain.UserVO;
//import com.poseidon.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

@Repository
@SuppressWarnings("unused")
public class UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

    private final UserRepository userRepository;

    public UserDAO(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * LOG in.
     *
     * @param userVO userVO
     * @return user instance from database
     * @throws UserException on error
     */
    /*public UserVO logIn(final UserVO userVO) throws UserException {
        Optional<User> user = sneak(() -> userRepository.findByEmail(userVO.getEmail()));
        if (user.isPresent()) {
            LOG.info(" user details fetched successfully,for user name {}", user.get().getName());
            if (!userVO.getPassword().equalsIgnoreCase(user.get().getPassword())) {
                throw new UserException(UserException.INCORRECT_PASSWORD);
            }
            LOG.info(" Password matched successfully, user details are {}", user);
            return convertToUserVO(user.get());
        } else {
            throw new UserException(UserException.UNKNOWN_USER);
        }
    }*/

    /**
     * getAllUserDetails to list all user details.
     *
     * @return List of user
     */
    public List<User> getAllUserDetails(final String companyCode) {
        return sneak(() -> userRepository.findByCompanyCode(companyCode));
    }

    /**
     * save to add a new user.
     *
     * @param user user
     */
    public void save(final User user, final String currentLoggedInUser) {
        sneak(() -> userRepository.save(user));
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
     * @param userVO user
     */
    public void updateUser(final UserVO userVO, final String loggedInUser) {
        var optionalUser = sneak(() -> userRepository.findById(userVO.getId()));
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            user.setName(userVO.getName());
            user.setEmail(userVO.getEmail());
            user.setPassword(userVO.getPassword());
            user.setRoles(userVO.getRoles());
            user.setModifiedBy(loggedInUser);
            sneak(() -> userRepository.save(user));
        }
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

    public void expireUser(final Long id) {
        var user = sneak(() -> userRepository.findById(id));
        if (user.isPresent()) {
            user.get().setEnabled(true);
            sneak(() -> userRepository.save(user.get()));
        }
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
        return sneak(() -> searchAllUsers(searchUser, startsWith, includes));
    }

    /**
     * search for all the user details from the database.
     *
     * @param searchUser UserVO
     * @return List of users
     * @throws DataAccessException on error
     */
    private List<User> searchAllUsers(final User searchUser,
                                        final boolean startsWith,
                                        final boolean includes) {
        var userSpec = new UserSpecification();
        var search = populateSearchOperation(startsWith, includes);
        if (StringUtils.hasText(searchUser.getName())) {
            userSpec.add(new SearchCriteria("name", searchUser.getName(), search));
        }
        if (StringUtils.hasText(searchUser.getEmail())) {
            userSpec.add(new SearchCriteria("email", searchUser.getEmail(), search));
        }
        return userRepository.findAll(userSpec);
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
}
