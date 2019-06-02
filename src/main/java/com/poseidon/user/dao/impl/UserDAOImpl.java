package com.poseidon.user.dao.impl;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 12:43:13 PM
 */
@Repository
@SuppressWarnings("unused")
public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * LOG in
     *
     * @param userVO userVO
     * @return user instance from database
     * @throws UserException on error
     */
    @Override
    public UserVO logIn(UserVO userVO) throws UserException {
        User user;
        try {
            user = userRepository.findByLogInId(userVO.getLoginId());
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
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
        return convertTOUserVO(user);
    }

    private UserVO convertTOUserVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getUserId());
        userVO.setName(user.getName());
        userVO.setLoginId(user.getLogInId());
        userVO.setPassword(user.getPassword());
        userVO.setRole(user.getRole());
        userVO.setCreatedBy(user.getCreatedBy());
        userVO.setLastModifiedBy(user.getModifiedBy());
        return userVO;
    }

    /**
     * getAllUserDetails to list all user details
     *
     * @return List of user
     * @throws UserException on db error
     */
    @Override
    public List<UserVO> getAllUserDetails() throws UserException {
        List<UserVO> userList;
        try {
            List<User> users = userRepository.findAll();
            userList = convertUsersToUserVOs(users);
        } catch (DataAccessException e) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
        return userList;
    }

    private List<UserVO> convertUsersToUserVOs(List<User> users) {
        List<UserVO> userVOS = new ArrayList<>();
        users.forEach(user -> {
            UserVO userVO = new UserVO();
            userVO.setId(user.getUserId());
            userVO.setName(user.getName());
            userVO.setLoginId(user.getLogInId());
            userVO.setPassword(user.getPassword());
            userVO.setRole(user.getRole());
            userVO.setCreatedBy(user.getCreatedBy());
            userVO.setLastModifiedBy(user.getModifiedBy());
            userVOS.add(userVO);
        });
        return userVOS;
    }

    /**
     * save to add a new user
     *
     * @param userVO user
     * @throws UserException on error
     */
    @Override
    public void save(UserVO userVO) throws UserException {
        User user = convertToUser(userVO);
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    private User convertToUser(UserVO userVO) {
        User user = new User();
        user.setName(userVO.getName());
        user.setLogInId(userVO.getLoginId());
        user.setPassword(userVO.getPassword());
        user.setRole(userVO.getRole());
        user.setCreatedBy(userVO.getCreatedBy());
        user.setModifiedBy(userVO.getLastModifiedBy());
        return user;
    }

    /**
     * getUserDetailsFromId to get the single user details from its id
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    @Override
    public UserVO getUserDetailsFromId(Long id) throws UserException {
        UserVO userVO = null;
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                userVO = convertTOUserVO(user);
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
        return userVO;
    }

    /**
     * search for all the user details from the database
     *
     * @param searchUser UserVO
     * @return List of users
     * @throws DataAccessException on error
     */
    @SuppressWarnings("unchecked")
    private List<UserVO> searchAllUsers(UserVO searchUser) {
        CriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot);

        if (searchUser.getIncludes()) {
            if (!StringUtils.isEmpty(searchUser.getName())) {
                criteria.where(builder.like(userRoot.get("name"), "%" + searchUser.getName() + "%"));
            }
            if (!StringUtils.isEmpty(searchUser.getLoginId())) {
                criteria.where(builder.like(userRoot.get("logInId"), "%" + searchUser.getLoginId() + "%"));
            }
            if (!StringUtils.isEmpty(searchUser.getRole())) {
                criteria.where(builder.like(userRoot.get("role"), "%" + searchUser.getRole() + "%"));
            }
        } else if (searchUser.getStartsWith()) {
            if (!StringUtils.isEmpty(searchUser.getName())) {
                criteria.where(builder.like(userRoot.get("name"), searchUser.getName() + "%"));
            }
            if (!StringUtils.isEmpty(searchUser.getLoginId())) {
                criteria.where(builder.like(userRoot.get("logInId"), searchUser.getLoginId() + "%"));
            }
            if (!StringUtils.isEmpty(searchUser.getRole())) {
                criteria.where(builder.like(userRoot.get("role"), searchUser.getRole() + "%"));
            }
        } else {
            if (!StringUtils.isEmpty(searchUser.getName())) {
                criteria.where(builder.equal(userRoot.get("name"), searchUser.getName()));
            }
            if (!StringUtils.isEmpty(searchUser.getLoginId())) {
                criteria.where(builder.equal(userRoot.get("logInId"), searchUser.getLoginId()));
            }
            if (!StringUtils.isEmpty(searchUser.getRole())) {
                criteria.where(builder.equal(userRoot.get("role"), searchUser.getRole()));
            }
        }
        List<User> resultUsers = em.unwrap(Session.class).createQuery(criteria).getResultList();
        return convertUsersToUserVOs(resultUsers);
    }

    /**
     * updateUser
     *
     * @param userVO user
     */
    @Override
    public void updateUser(UserVO userVO) {
        Optional<User> optionalUser = userRepository.findById(userVO.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userVO.getName());
            user.setLogInId(userVO.getLoginId());
            user.setPassword(userVO.getPassword());
            user.setRole(userVO.getRole());
            user.setModifiedBy(userVO.getLastModifiedBy());
        }
    }

    /**
     * delete user
     *
     * @param id id
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * search for a list of users
     *
     * @param searchUser UserVO
     * @return List of user
     * @throws UserException on error
     */
    @Override
    public List<UserVO> searchUserDetails(UserVO searchUser) throws UserException {
        List<UserVO> userList;
        try {
            userList = searchAllUsers(searchUser);
        } catch (DataAccessException e) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
        return userList;
    }

    @Override
    public UserVO findByUsername(String username) {
        User user = userRepository.findByName(username);
        return convertTOUserVO(user);
    }
}
