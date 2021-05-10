package com.poseidon.user.dao.impl;

import com.poseidon.dataaccess.specs.SearchCriteria;
import com.poseidon.dataaccess.specs.SearchOperation;
import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.dao.spec.UserSpecification;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("unused")
public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * LOG in.
     *
     * @param userVO userVO
     * @return user instance from database
     * @throws UserException on error
     */
    @Override
    public UserVO logIn(final UserVO userVO) throws UserException {
        User user;
        try {
            user = userRepository.findByEmail(userVO.getEmail());
        } catch (DataAccessException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }

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
     * @throws UserException on db error
     */
    @Override
    public List<UserVO> getAllUserDetails() throws UserException {
        List<UserVO> userList;
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll().forEach(users::add);
            userList = convertUsersToUserVOs(users);
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
        return userList;
    }

    private List<UserVO> convertUsersToUserVOs(final List<User> users) {
        return users.stream().map(this::convertToUserVO).collect(Collectors.toList());
    }

    private UserVO convertToUserVO(final User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getUserId());
        userVO.setName(user.getName());
        userVO.setEmail(user.getEmail());
        userVO.setPassword(user.getPassword());
        userVO.setRole(user.getRole());
        userVO.setExpired(user.getExpired());
        userVO.setCreatedBy(user.getCreatedBy());
        userVO.setLastModifiedBy(user.getModifiedBy());
        return userVO;
    }

    /**
     * save to add a new user.
     *
     * @param userVO user
     * @throws UserException on error
     */
    @Override
    public void save(final UserVO userVO) throws UserException {
        User user = convertToUser(userVO);
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    private User convertToUser(final UserVO userVO) {
        User user = new User();
        user.setName(userVO.getName());
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setExpired(userVO.getExpired());
        user.setRole(userVO.getRole());
        user.setCreatedBy(userVO.getCreatedBy());
        user.setModifiedBy(userVO.getLastModifiedBy());
        return user;
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
        UserVO userVO = null;
        try {
            var optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
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
    @Override
    public void updateUser(final UserVO userVO) throws UserException {
        try {
            var optionalUser = userRepository.findById(userVO.getId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setName(userVO.getName());
                user.setEmail(userVO.getEmail());
                user.setPassword(userVO.getPassword());
                user.setRole(userVO.getRole());
                user.setModifiedBy(userVO.getLastModifiedBy());
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
    @Override
    public void deleteUser(final Long id) throws UserException {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    @Override
    public UserVO findByEmail(final String email) throws UserException {
        try {
            User user = userRepository.findByEmail(email);
            return convertToUserVO(user);
        } catch (Exception ex) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    @Override
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
    @Override
    public List<UserVO> searchUserDetails(final UserVO searchUser) throws UserException {
        List<UserVO> userList;
        try {
            userList = searchAllUsers(searchUser);
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
    @SuppressWarnings("unchecked")
    private List<UserVO> searchAllUsers(final UserVO searchUser) {
        var userSpec = new UserSpecification();
        var search = populateSearchOperation(searchUser);
        if (!StringUtils.hasText(searchUser.getName())) {
            userSpec.add(new SearchCriteria("name", searchUser.getName(), search));
        }
        if (!StringUtils.hasText(searchUser.getEmail())) {
            userSpec.add(new SearchCriteria("email", searchUser.getEmail(), search));
        }
        if (!StringUtils.hasText(searchUser.getRole())) {
            userSpec.add(new SearchCriteria("role", searchUser.getRole(), search));
        }
        List<User> resultUsers = userRepository.findAll(userSpec);
        return convertUsersToUserVOs(resultUsers);
    }

    private SearchOperation populateSearchOperation(final UserVO searchUser) {
        SearchOperation searchOperation;
        if (searchUser.getIncludes() != null && searchUser.getIncludes().booleanValue()) {
            searchOperation = SearchOperation.MATCH;
        } else if (searchUser.getStartsWith() != null && searchUser.getStartsWith().booleanValue()) {
            searchOperation = SearchOperation.MATCH_START;
        } else {
            searchOperation = SearchOperation.EQUAL;
        }
        return searchOperation;
    }
}
