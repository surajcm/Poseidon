package com.poseidon.user.dao;

import com.poseidon.dataaccess.specs.SearchCriteria;
import com.poseidon.dataaccess.specs.SearchOperation;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.repo.UserRepository;
import com.poseidon.user.dao.spec.UserSpecification;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;

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
    public UserVO logIn(final UserVO userVO) throws UserException {
        User user = sneak(() -> userRepository.findByEmail(userVO.getEmail()));

        if (user != null) {
            LOG.info(" user details fetched successfully,for user name {}", user.getName());
            if (!userVO.getPassword().equalsIgnoreCase(user.getPassword())) {
                throw new UserException(UserException.INCORRECT_PASSWORD);
            }
            LOG.info(" Password matched successfully, user details are {}", user);
        } else {
            // invalid user
            throw new UserException(UserException.UNKNOWN_USER);
        }
        return convertToUserVO(user);
    }


    /**
     * getAllUserDetails to list all user details.
     *
     * @return List of user
     */
    public List<UserVO> getAllUserDetails() {
        return sneak(() -> userRepository.findAll().stream().map(this::convertToUserVO).toList());
    }

    /**
     * save to add a new user.
     *
     * @param userVO user
     */
    public void save(final UserVO userVO, final String currentLoggedInUser) {
        var user = convertToUser(userVO, currentLoggedInUser);
        sneak(() -> userRepository.save(user));
    }

    /**
     * getUserDetailsFromId to get the single user details from its id.
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    public UserVO getUserDetailsFromId(final Long id) throws UserException {
        UserVO userVO = null;
        try {
            var optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                var user = optionalUser.get();
                userVO = convertToUserVO(user);
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
        return userVO;
    }

    /**
     * updateUser.
     *
     * @param userVO user
     */
    public void updateUser(final UserVO userVO, final String loggedInUser)
            throws UserException {
        try {
            var optionalUser = userRepository.findById(userVO.getId());
            if (optionalUser.isPresent()) {
                var user = optionalUser.get();
                user.setName(userVO.getName());
                user.setEmail(userVO.getEmail());
                user.setPassword(userVO.getPassword());
                user.setRole(userVO.getRole());
                user.setModifiedBy(loggedInUser);
                userRepository.save(user);
            }
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    /**
     * delete user.
     *
     * @param id id
     */
    public void deleteUser(final Long id) throws UserException {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    public UserVO findByEmail(final String email) throws UserException {
        try {
            var user = userRepository.findByEmail(email);
            return convertToUserVO(user);
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    public void expireUser(final Long id) throws UserException {
        try {
            var user = userRepository.findById(id);
            if (user.isPresent()) {
                user.get().setExpired(true);
                userRepository.save(user.get());
            }
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
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
            userList = searchAllUsers(searchUser, startswith, includes);
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
        return userList;
    }

    /**
     * search for all the user details from the database.
     *
     * @param searchUser UserVO
     * @return List of users
     * @throws DataAccessException on error
     */
    //@SuppressWarnings("unchecked")
    private List<UserVO> searchAllUsers(final UserVO searchUser, final boolean startswith, final boolean includes) {
        var userSpec = new UserSpecification();
        var search = populateSearchOperation(searchUser, startswith, includes);
        if (StringUtils.hasText(searchUser.getName())) {
            userSpec.add(new SearchCriteria("name", searchUser.getName(), search));
        }
        if (StringUtils.hasText(searchUser.getEmail())) {
            userSpec.add(new SearchCriteria("email", searchUser.getEmail(), search));
        }
        if (StringUtils.hasText(searchUser.getRole())) {
            userSpec.add(new SearchCriteria("role", searchUser.getRole(), search));
        }
        List<User> resultUsers = userRepository.findAll(userSpec);
        return convertUsersToUserVOs(resultUsers);
    }

    private List<UserVO> convertUsersToUserVOs(final List<User> users) {
        return users.stream().map(this::convertToUserVO).toList();
    }

    private User convertToUser(final UserVO userVO, final String currentLoggedInUser) {
        var user = new User();
        user.setName(userVO.getName());
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setExpired(userVO.getExpired());
        user.setRole(userVO.getRole());
        user.setCreatedBy(currentLoggedInUser);
        user.setModifiedBy(currentLoggedInUser);
        return user;
    }

    private UserVO convertToUserVO(final User user) {
        var userVO = new UserVO();
        userVO.setId(user.getUserId());
        userVO.setName(user.getName());
        userVO.setEmail(user.getEmail());
        userVO.setPassword(user.getPassword());
        userVO.setRole(user.getRole());
        userVO.setExpired(user.getExpired());
        return userVO;
    }

    private SearchOperation populateSearchOperation(final UserVO searchUser,
                                                    final boolean startswith,
                                                    final boolean includes) {
        SearchOperation searchOperation;
        if (includes) {
            searchOperation = SearchOperation.MATCH;
        } else if (startswith) {
            searchOperation = SearchOperation.MATCH_START;
        } else {
            searchOperation = SearchOperation.EQUAL;
        }
        return searchOperation;
    }
}
