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
     * getUserDetailsFromId to get the single user details from its id
     *
     * @param id id
     * @return UserVO
     * @throws UserException on error
     */
    UserVO getUserDetailsFromId(Long id) throws UserException;

    /**
     * updates the current user
     *
     * @param user user
     */
    void updateUser(UserVO user);

    /**
     * deletes the selected user
     *
     * @param id id of the user
     */
    void deleteUser(Long id);

    /**
     * search for a list of users
     *
     * @param searchUser UserVO
     * @return List of user
     * @throws UserException on error
     */
    List<UserVO> searchUserDetails(UserVO searchUser) throws UserException;
}
