package com.poseidon.user.dao.impl;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author : Suraj Muraleedharan
 *         Date: Nov 27, 2010
 *         Time: 12:43:13 PM
 */
@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    private static final String GET_ALL_USERS_SQL = " select * from user order by modifiedOn";

    private static final String SEARCH_USERS_SQL = " select * from user ";

    private static final String GET_SINGLE_USER_SQL = " select * from user where id = ? ";

    private static final String GET_SINGLE_USER = " select * from user where name = ? ";

    private static final String GET_USER_BY_LOGINID_SQL = " select * from user where logInId = ? ";

    private static final String UPDATE_USER_SQL = " update user set name = ?, logInId = ? ,password = ?, Role = ?, modifiedOn = ? , modifiedBy = ? where id = ?";

    private static final String DELETE_BY_ID_SQL = " delete from user where id = ? ";

    /**
     * LOG in
     *
     * @param userVO userVO
     * @return user instance from database
     * @throws UserException on error
     */
    public UserVO logIn(UserVO userVO) throws UserException {
        UserVO currentUser;
        try {
            //User user = userRepository.findByLogInId(userVO.getLoginId());
            currentUser = getUserByLogInId(userVO.getLoginId());
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }

        if (currentUser != null) {
            LOG.info(" user details fetched successfully,for user name " + currentUser.getName());
            // check whether the password given is correct or not
            if (!userVO.getPassword().equalsIgnoreCase(currentUser.getPassword())) {
                throw new UserException(UserException.INCORRECT_PASSWORD);
            }
            LOG.info(" Password matched successfully, user details are " + currentUser.toString());
        } else {
            // invalid user
            throw new UserException(UserException.UNKNOWN_USER);
        }
        return currentUser;
    }

    /**
     * getAllUserDetails to list all user details
     *
     * @return List of user
     * @throws UserException on db error
     */
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
        for (User user: users) {
            UserVO userVO = new UserVO();
            userVO.setId(Long.valueOf(user.getId()));
            userVO.setName(user.getName());
            userVO.setLoginId(user.getLogInId());
            userVO.setPassword(user.getPassword());
            userVO.setRole(user.getRole());
            userVO.setCreatedDate(user.getCreatedOn());
            userVO.setCreatedBy(user.getCreatedBy());
            userVO.setModifiedDate(user.getModifiedOn());
            userVO.setLastModifiedBy(user.getModifiedBy());
            userVOS.add(userVO);
        }
        return userVOS;
    }

    /**
     * save to add a new user
     *
     * @param userVO user
     * @throws UserException on error
     */
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
        user.setCreatedOn(userVO.getCreatedDate());
        user.setModifiedBy(userVO.getLastModifiedBy());
        user.setModifiedOn(userVO.getModifiedDate());
        return user;
    }

    /**
     * getUserDetailsFromID to get the single user details from its id
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    public UserVO getUserDetailsFromID(Long id) throws UserException {
        UserVO userVO;
        try {
            userVO = getUserById(id);
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
    public List<UserVO> searchAllUsers(UserVO searchUser) throws DataAccessException {
        StringBuilder dynamicQuery = new StringBuilder(SEARCH_USERS_SQL);
        Boolean isWhereAppended = Boolean.FALSE;
        if (searchUser.getName() != null && searchUser.getName().trim().length() > 0) {
            dynamicQuery.append(" where ");
            isWhereAppended = Boolean.TRUE;
            if (searchUser.getIncludes()) {
                dynamicQuery.append(" name like '%").append(searchUser.getName()).append("%'");
            } else if (searchUser.getStartsWith()) {
                dynamicQuery.append(" name like '").append(searchUser.getName()).append("%'");
            } else {
                dynamicQuery.append(" name like '").append(searchUser.getName()).append("'");
            }
        }

        if (searchUser.getLoginId() != null && searchUser.getLoginId().trim().length() > 0) {
            if (!isWhereAppended) {
                dynamicQuery.append(" where ");
            } else {
                dynamicQuery.append(" and ");
            }
            if (searchUser.getIncludes()) {
                dynamicQuery.append(" logInId like '%").append(searchUser.getLoginId()).append("%'");
            } else if (searchUser.getStartsWith()) {
                dynamicQuery.append(" logInId like '").append(searchUser.getLoginId()).append("%'");
            } else {
                dynamicQuery.append(" logInId like '").append(searchUser.getLoginId()).append("'");
            }
        }

        if (searchUser.getRole() != null && searchUser.getRole().length() > 0) {
            if (!isWhereAppended) {
                dynamicQuery.append(" where ");
            } else {
                dynamicQuery.append(" and ");
            }
            if (searchUser.getIncludes()) {
                dynamicQuery.append(" role like '%").append(searchUser.getRole()).append("%'");
            } else if (searchUser.getStartsWith()) {
                dynamicQuery.append(" role like '").append(searchUser.getRole()).append("%'");
            } else {
                dynamicQuery.append(" role like '").append(searchUser.getRole()).append("'");
            }
        }

        LOG.info("Query generated is " + dynamicQuery);
        return (List<UserVO>) jdbcTemplate.query(dynamicQuery.toString(), new UserRowMapper());
    }

    /**
     * getUserById
     *
     * @param id id
     * @return user
     */
    @SuppressWarnings("unchecked")
    public UserVO getUserById(Long id) {
        return (UserVO) jdbcTemplate.queryForObject(GET_SINGLE_USER_SQL, new Object[]{id}, new UserRowMapper());

    }

    /**
     * getUserById
     *
     * @param loginId loginId
     * @return user
     * @throws DataAccessException on error
     */
    @SuppressWarnings("unchecked")
    public UserVO getUserByLogInId(String loginId) throws DataAccessException {
        return (UserVO) jdbcTemplate.queryForObject(GET_USER_BY_LOGINID_SQL, new Object[]{loginId}, new UserRowMapper());
    }

    /**
     * updateUser
     *
     * @param userVO user
     */
    public void updateUser(UserVO userVO) throws UserException {
        try {
            Optional<User> optionalUser = userRepository.findById(userVO.getId().intValue());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setName(userVO.getName());
                user.setLogInId(userVO.getLoginId());
                user.setPassword(userVO.getPassword());
                user.setRole(userVO.getRole());
                user.setModifiedOn(new Date());
                user.setModifiedBy(userVO.getLastModifiedBy());
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    /**
     * delete user
     *
     * @param id id
     */
    @SuppressWarnings("unused")
    public void deleteUser(Long id) throws UserException {
        try {
            userRepository.deleteById(id.intValue());
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    /**
     * search for a list of users
     *
     * @param searchUser UserVO
     * @return List of user
     * @throws UserException on error
     */
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
        return (UserVO) jdbcTemplate.queryForObject(GET_SINGLE_USER, new Object[]{username}, new UserRowMapper());
    }

    /**
     * Row mapper as inner class
     */
    private class UserRowMapper implements RowMapper {

        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            UserVO user = new UserVO();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLoginId(resultSet.getString("logInId"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            user.setCreatedBy(resultSet.getString("createdBy"));
            user.setCreatedDate(resultSet.getDate("createdOn"));
            user.setLastModifiedBy(resultSet.getString("modifiedBy"));
            user.setModifiedDate(resultSet.getDate("modifiedOn"));

            return user;
        }

    }
}
