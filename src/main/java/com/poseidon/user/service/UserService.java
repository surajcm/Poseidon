package com.poseidon.user.service;

import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;

import java.util.List;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 2:38:15 PM
 */
public interface UserService {
    /**
     * log in service
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
    void addNewUser(UserVO user) throws UserException;

    /**
     * getUserDetailsFromID to get the single user details from its id
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    UserVO getUserDetailsFromID(Long id) throws UserException;

    /**
     * updates the current user
     *
     * @param user user
     * @throws UserException on error
     */
    void UpdateUser(UserVO user) throws UserException;

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
}
