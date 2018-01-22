package com.poseidon.user.dao;

import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;

import java.util.List;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 12:42:40 PM
 */
@SuppressWarnings("unused")
public interface UserDAO {

    /**
     * log in dao
     *
     * @param user user
     * @return user instance from database
     * @throws UserException on error
     */
    UserVO logIn(UserVO user) throws UserException;

    /**
     * getAllUserDetails to list all user details
     *
     * @return List of user
     * @throws UserException on error
     */
    List<UserVO> getAllUserDetails() throws UserException;

    /**
     * create new user
     *
     * @param user user
     * @throws UserException on error
     */
    void save(UserVO user) throws UserException;

    /**
     * getUserDetailsFromID to get the single user details from its id
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    UserVO getUserDetailsFromID(Long id) throws UserException;

    /**
     * updates the current user details
     *
     * @param user user
     * @throws UserException on error
     */
    void updateUser(UserVO user) throws UserException;

    /**
     * deletes the selected user
     *
     * @param id id of the user
     * @throws UserException on error
     */
    void deleteUser(Long id) throws UserException;

    /**
     * search for a list of users
     *
     * @param searchUser UserVO
     * @return List of user
     * @throws UserException on error
     */
    List<UserVO> searchUserDetails(UserVO searchUser)throws UserException;

    UserVO findByUsername(String username);
}
