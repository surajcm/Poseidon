package com.poseidon.user.web.controller;

import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import com.poseidon.user.service.UserService;
import com.poseidon.user.web.form.UserForm;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : Suraj Muraleedharan
 *         Date: Nov 27, 2010
 *         Time: 2:38:15 PM
 */
@Controller
//@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Used in automatic redirect to Log in screen
     *
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = {"/user/Index.htm", "/"}, method = RequestMethod.GET)
    public ModelAndView Index() {
        logger.info(" Inside Index method of user Controller ");
        UserForm userForm = new UserForm();
        UserVO userVO = new UserVO();
        userForm.setUser(userVO);
        return new ModelAndView("user/logIn", "userForm", userForm);
    }

    /**
     * controller for first log in action
     *
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/user/LogIn.htm", method = RequestMethod.POST)
    public ModelAndView LogIn(UserForm userForm) {
        logger.info(" Inside LogIn method of user Controller ");
        UserVO realUser;
        try {
            if (userService == null) {
                throw new Exception("A configuration error has been occurred ");
            }
            realUser = userService.logIn(userForm.getCurrentUser());
        } catch (UserException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.UNKNOWN_USER)) {
                userForm.setMessage(" Invalid user Credentials, No user Found !!");
            } else if (e.getExceptionType().equalsIgnoreCase(UserException.INCORRECT_PASSWORD)) {
                userForm.setMessage(" Incorrect Password. !! ");
            } else if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                userForm.setMessage(" An error occurred while fetching data from database. !! ");
            } else {
                userForm.setMessage(" An Unknown Error has been occurred !!");
            }
            return new ModelAndView("user/logIn", "userForm", userForm);
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            userForm.setMessage(" An Unknown Error has been occurred !!");
            return new ModelAndView("user/logIn", "userForm", userForm);
        }
        if (realUser != null && realUser.getRole() != null) {
            /*if(realUser.getRole().equalsIgnoreCase("ADMIN")){
                userForm.setLoggedInUser(realUser.getName());
                userForm.setLoggedInRole(realUser.getRole());
                log.info("Logged in successfully");
                return ListAll(request, response, userForm);
            }*/
             userForm.setLoggedInUser(realUser.getName());
             userForm.setLoggedInRole(realUser.getRole());
             return ToHome(userForm);
            
        } else {
            userForm.setMessage(" An Unknown Error has been occurred !!");
            return new ModelAndView("user/logIn", "userForm", userForm);
        }
    }

    /**
     * Used to list all users (can be done only by admin user)
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/ListAll.htm", method = RequestMethod.POST)
    public ModelAndView ListAll(UserForm userForm) {
        logger.info(" Inside ListAll method of user Controller ");
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to list the Users due to an error");
            userForm.setStatusMessageType("error");
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        if (userList != null) {
            for (UserVO userIteration : userList) {
                logger.info(" user detail " + userIteration.toString());
            }
        }
        userForm.setUserVOs(userList);
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setSearchUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/UserList", "userForm", userForm);
    }

    /**
     * populateRoles
     *
     * @return List of String
     */
    private List<String> populateRoles() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ADMIN");
        roleList.add("GUEST");
        return roleList;
    }

    /**
     * Screen to add a new user
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/AddUser.htm", method = RequestMethod.POST)
    public ModelAndView AddUser(UserForm userForm) {
        logger.info(" Inside AddUser method of user Controller ");
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/userAdd", "userForm", userForm);
    }

    /**
     * add a new user to database
     *
     * @param userForm user instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/SaveUser.htm", method = RequestMethod.POST)
    public ModelAndView SaveUser(UserForm userForm) {
        logger.info(" Inside SaveUser method of user Controller ");
        logger.info(" user instance to add to database " + userForm.toString());
        try {
            userForm.getUser().setCreatedDate(new Date());
            userForm.getUser().setModifiedDate(new Date());
            userForm.getUser().setCreatedBy(userForm.getLoggedInUser());
            userForm.getUser().setLastModifiedBy(userForm.getLoggedInUser());
            userService.addNewUser(userForm.getUser());
            userForm.setStatusMessage("Successfully saved the new user");
            userForm.setStatusMessageType("success");
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to save the user due to a database error");
            userForm.setStatusMessageType("error");
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to save the user due to an error");
            userForm.setStatusMessageType("error");
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to list the Users due to an error");
            userForm.setStatusMessageType("error");
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        if (userList != null) {
            for (UserVO userIteration : userList) {
                logger.info(" user detail " + userIteration.toString());
            }
        }
        userForm.setUserVOs(userList);
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setSearchUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/UserList", "userForm", userForm);
    }

    @RequestMapping(value = "/user/saveUserAjax.htm", method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public void saveUserAjax(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse) {
        logger.debug("saveUserAjax method of user Controller ");

        StringBuilder responseString = new StringBuilder();

        String selectName = httpServletRequest.getParameter("selectName");
        String selectLogin = httpServletRequest.getParameter("selectLogin");
        String selectRole = httpServletRequest.getParameter("selectRole");

        logger.info(" At saveUserAjax, selectName is : s selectLogin : s : selectRole"+ selectName+ selectLogin+selectRole);
        UserVO ajaxUserVO = new UserVO();
        ajaxUserVO.setName(selectName);
        ajaxUserVO.setLoginId(selectName);
        ajaxUserVO.setRole(selectRole);
        ajaxUserVO.setPassword("password");
        ajaxUserVO.setCreatedDate(new Date());
        ajaxUserVO.setModifiedDate(new Date());
        ajaxUserVO.setCreatedBy("-ajax-");
        ajaxUserVO.setLastModifiedBy("-ajax-");


        try {
            userService.addNewUser(ajaxUserVO);
        } catch (UserException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");
        }
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        responseString.append(fetchJSONUserList(userList));
        // get a id-name combination, which is splittable by js
        httpServletResponse.setContentType("text/plain");
        PrintWriter out;
        try {
            out = httpServletResponse.getWriter();
            out.print(responseString.toString());
            out.flush();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private String fetchJSONUserList(List<UserVO> userList) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(userList);
        } catch (IOException e) {
            response = "error";
            logger.error(e.getMessage());
        }
        logger.info(response);
        return response;
    }

    /**
     * Screen to add a new user
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/EditUser.htm", method = RequestMethod.POST)
    public ModelAndView EditUser(UserForm userForm) {
        logger.info(" Inside EditUser method of user Controller ");
        logger.info(" user is " + userForm.toString());
        UserVO userVO = null;
        try {
            userVO = userService.getUserDetailsFromID(userForm.getId());
        } catch (UserException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");
        }
        if (userVO == null) {
            logger.error(" No details found for current user !!");
        } else {
            logger.info(" user details are " + userVO);
        }
        userForm.setUser(userVO);
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/userEdit", "userForm", userForm);
    }

    /**
     * updates the user
     *
     * @param userForm user instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/UpdateUser.htm", method = RequestMethod.POST)
    public ModelAndView UpdateUser(UserForm userForm) {
        logger.info(" Inside UpdateUser method of user Controller ");
        try {
            userForm.getUser().setLastModifiedBy(userForm.getLoggedInUser());
            userForm.getUser().setModifiedDate(new Date());
            logger.info(" user instance to update " + userForm.getUser().toString());
            userService.UpdateUser(userForm.getUser());
            userForm.setStatusMessage("Successfully updated the user");
            userForm.setStatusMessageType("success");
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to update the user due to a database error");
            userForm.setStatusMessageType("error");
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to update the user due to an error");
            userForm.setStatusMessageType("error");
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        return ListAll(userForm);
    }

    /**
     * delete the user
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/DeleteUser.htm", method = RequestMethod.POST)
    public ModelAndView DeleteUser(UserForm userForm) {
        logger.info(" Inside DeleteUser method of user Controller ");
        logger.info(" user is " + userForm.toString());
        try {
            userService.deleteUser(userForm.getId());
            userForm.setStatusMessage("Successfully deleted the user");
            userForm.setStatusMessageType("success");
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to delete the user due to a database error");
            userForm.setStatusMessageType("error");
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to delete the user due to an error");
            userForm.setStatusMessageType("error");
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }

        return ListAll(userForm);
    }

    /**
     * Screen to home
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/ToHome.htm", method = RequestMethod.POST)
    public ModelAndView ToHome(UserForm userForm) {
        logger.info(" Inside ToHome method of user Controller ");
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        return new ModelAndView("MainPage", "userForm", userForm);
    }

    /**
     * Screen to home
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/LogMeOut.htm", method = RequestMethod.POST)
    public ModelAndView LogMeOut(UserForm userForm) {
        logger.info(" Inside LogMeOut method of user Controller ");
        return new ModelAndView("user/logIn", "userForm", new UserForm());
    }

    /**
     * Screen to search for a user
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "user/SearchUser.htm", method = RequestMethod.POST)
    public ModelAndView SearchUser(UserForm userForm) {
        logger.info(" Inside SearchUser method of user Controller ");
        logger.info(" user Details are " + userForm.getSearchUser().toString());
        List<UserVO> userList = null;
        try {
            userList = userService.searchUserDetails(userForm.getSearchUser());
            if(userList != null && userList.size()> 0){
                userForm.setStatusMessage("Successfully fetched "+userList.size()+ " Users");
                userForm.setStatusMessageType("info");
            }
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to search due to a database error");
            userForm.setStatusMessageType("error");
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to search due to an error");
            userForm.setStatusMessageType("error");
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        if (userList != null) {
            for (UserVO userIteration : userList) {
                logger.info(" user detail " + userIteration.toString());
            }
        }
        userForm.setUserVOs(userList);
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/UserList", "userForm", userForm);
    }

}
