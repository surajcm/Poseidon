package com.poseidon.user.dao.impl;

import com.poseidon.user.dao.UserDAO;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author : Suraj Muraleedharan
 *         Date: Nov 27, 2010
 *         Time: 12:43:13 PM
 */
@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);
    private SimpleJdbcInsert insertUser;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
     * @param user user
     * @return user instance from database
     * @throws UserException on error
     */
    public UserVO logIn(UserVO user) throws UserException {
        UserVO currentUser;
        try {
            currentUser = getUserByLogInId(user.getLoginId());
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }

        if (currentUser != null) {
            LOG.info(" user details fetched successfully,for user name " + currentUser.getName());
            // check whether the password given is correct or not
            if (!user.getPassword().equalsIgnoreCase(currentUser.getPassword())) {
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
     * @throws UserException
     */
    public List<UserVO> getAllUserDetails() throws UserException {
        List<UserVO> userList;
        try {
            userList = getAllUsers();
        } catch (DataAccessException e) {
            throw new UserException(UserException.DATABASE_ERROR);
        }
        return userList;
    }

    /**
     * save to add a new user
     *
     * @param user user
     * @throws UserException on error
     */
    public void save(UserVO user) throws UserException {
        try {
            saveUser(user);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
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
     * get all the user details from the database
     *
     * @return List of users
     * @throws DataAccessException on error
     */
    @SuppressWarnings("unchecked")
    public List<UserVO> getAllUsers() throws DataAccessException {
        return jdbcTemplate.query(GET_ALL_USERS_SQL, new UserRowMapper());
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
     * @param user user
     */
    public void updateUser(UserVO user) throws UserException {
        Object[] parameters = new Object[]{
                user.getName(),
                user.getLoginId(),
                user.getPassword(),
                user.getRole(),
                new Date(),
                user.getLastModifiedBy(),
                user.getId()};

        try {
            jdbcTemplate.update(UPDATE_USER_SQL, parameters);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new UserException(UserException.DATABASE_ERROR);
        }
    }

    /**
     * save user
     *
     * @param user user
     * @throws DataAccessException on error
     */
    public void saveUser(final UserVO user) throws DataAccessException {
        insertUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("user").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("logInId", user.getLoginId())
                .addValue("password", user.getPassword())
                .addValue("role", user.getRole())
                .addValue("createdOn", user.getCreatedDate())
                .addValue("modifiedOn", user.getModifiedDate())
                .addValue("createdBy", user.getCreatedBy())
                .addValue("modifiedBy", user.getLastModifiedBy());
        Number newId = insertUser.executeAndReturnKey(parameters);
        LOG.info(" the queryForInt resulted in  " + newId.longValue());
        user.setId(newId.longValue());

    }

    /**
     * delete user
     *
     * @param id id
     */
    @SuppressWarnings("unused")
    public void deleteUser(Long id) throws UserException {
        try {
            Object[] parameters = new Object[]{id};
            jdbcTemplate.update(DELETE_BY_ID_SQL, parameters);
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
