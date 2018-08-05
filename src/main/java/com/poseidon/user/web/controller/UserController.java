package com.poseidon.user.web.controller;

import com.poseidon.user.domain.UserVO;
import com.poseidon.user.exception.UserException;
import com.poseidon.user.service.UserService;
import com.poseidon.user.service.impl.SecurityService;
import com.poseidon.user.web.form.UserForm;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : Suraj Muraleedharan
 *         Date: Nov 27, 2010
 *         Time: 2:38:15 PM
 */
@Controller
@SuppressWarnings("unused")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String USER_FORM = "userForm";
    private static final String USER_LOG_IN = "login";
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;


    /**
     * Used in automatic redirect to Log in screen
     *
     * @return ModelAndView to render
     */
    @GetMapping(value = {"/"})
    public ModelAndView index() {
        logger.info(" Inside Index method of user Controller ");
        UserForm userForm = new UserForm();
        UserVO userVO = new UserVO();
        userForm.setUser(userVO);
        return new ModelAndView("MainPage", USER_FORM, userForm);
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute(ERROR, "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return USER_LOG_IN;
    }


    /**
     * Used to list all users (can be done only by admin user)
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/ListAll.htm")
    public ModelAndView listAll(UserForm userForm) {
        logger.info(" Inside ListAll method of user Controller ");
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to list the Users due to an error");
            userForm.setStatusMessageType(ERROR);
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
            userList.stream().map(userIteration -> " user detail " + userIteration.toString()).forEach(logger::info);
        }
        userForm.setUserVOs(userList);
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setSearchUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/UserList", USER_FORM, userForm);
    }

    /**
     * populateRoles
     *
     * @return List of String
     */
    private List<String> populateRoles() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_ADMIN");
        roleList.add("ROLE_GUEST");
        return roleList;
    }

    /**
     * Screen to add a new user
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/AddUser.htm")
    public ModelAndView addUser(UserForm userForm) {
        logger.info(" Inside AddUser method of user Controller ");
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/userAdd", USER_FORM, userForm);
    }

    /**
     * add a new user to database
     *
     * @param userForm user instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/SaveUser.htm")
    public ModelAndView saveUser(UserForm userForm) {
        logger.info(" Inside SaveUser method of user Controller ");
        logger.info(" user instance to add to database " + userForm.toString());
        try {
            userForm.getUser().setCreatedDate(new Date());
            userForm.getUser().setModifiedDate(new Date());
            userForm.getUser().setCreatedBy(userForm.getLoggedInUser());
            userForm.getUser().setLastModifiedBy(userForm.getLoggedInUser());
            userService.save(userForm.getUser());
            userForm.setStatusMessage("Successfully saved the new user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to save the user due to a database error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to save the user due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to list the Users due to an error");
            userForm.setStatusMessageType(ERROR);
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
        return new ModelAndView("user/UserList", USER_FORM, userForm);
    }

    @PostMapping(value = "/user/saveUserAjax.htm")
    public String saveUserAjax(@ModelAttribute(value = "selectName") String  selectName,
                             @ModelAttribute(value = "selectLogin") String  selectLogin,
                             @ModelAttribute(value = "selectRole") String  selectRole,
                             BindingResult result) {
        logger.info("saveUserAjax method of user Controller ");

        StringBuilder responseString = new StringBuilder();
        logger.info(" At saveUserAjax, selectName is : "+ selectName);
        logger.info(" At saveUserAjax, selectLogin : "+ selectLogin);
        logger.info(" At saveUserAjax, selectRole : "+ selectRole);
        UserVO ajaxUserVO = new UserVO();
        ajaxUserVO.setName(selectName);
        ajaxUserVO.setLoginId(selectName);
        ajaxUserVO.setRole(selectRole);
        //todo : find out a way to get current user
        ajaxUserVO.setPassword("password");
        ajaxUserVO.setCreatedDate(new Date());
        ajaxUserVO.setModifiedDate(new Date());
        ajaxUserVO.setCreatedBy("-ajax-");
        ajaxUserVO.setLastModifiedBy("-ajax-");

        try {
            userService.save(ajaxUserVO);
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
        return responseString.toString();
    }

    private String fetchJSONUserList(List<UserVO> userList) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(userList);
        } catch (IOException e) {
            response = ERROR;
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
    @PostMapping(value = "/user/EditUser.htm")
    public ModelAndView editUser(UserForm userForm) {
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
        return new ModelAndView("user/userEdit", USER_FORM, userForm);
    }

    /**
     * updates the user
     *
     * @param userForm user instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/UpdateUser.htm")
    public ModelAndView updateUser(UserForm userForm) {
        logger.info(" Inside UpdateUser method of user Controller ");
        try {
            userForm.getUser().setLastModifiedBy(userForm.getLoggedInUser());
            userForm.getUser().setModifiedDate(new Date());
            logger.info(" user instance to update " + userForm.getUser().toString());
            userService.UpdateUser(userForm.getUser());
            userForm.setStatusMessage("Successfully updated the user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to update the user due to a database error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to update the user due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        return listAll(userForm);
    }

    /**
     * delete the user
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/DeleteUser.htm")
    public ModelAndView deleteUser(UserForm userForm) {
        logger.info(" Inside DeleteUser method of user Controller ");
        logger.info(" user is " + userForm.toString());
        try {
            userService.deleteUser(userForm.getId());
            userForm.setStatusMessage("Successfully deleted the user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to delete the user due to a database error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to delete the user due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }

        return listAll(userForm);
    }

    /**
     * Screen to home
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/ToHome.htm")
    public ModelAndView toHome(UserForm userForm) {
        logger.info(" Inside ToHome method of user Controller ");
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        return new ModelAndView("MainPage", USER_FORM, userForm);
    }

    /**
     * Screen to home
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/LogMeOut.htm")
    public ModelAndView logMeOut(UserForm userForm) {
        logger.info(" Inside LogMeOut method of user Controller ");
        return new ModelAndView(USER_LOG_IN, USER_FORM, new UserForm());
    }

    /**
     * Screen to search for a user
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping(value = "/user/SearchUser.htm")
    public ModelAndView searchUser(UserForm userForm) {
        logger.info(" Inside SearchUser method of user Controller ");
        logger.info(" user Details are " + userForm.getSearchUser().toString());
        List<UserVO> userList = null;
        try {
            userList = userService.searchUserDetails(userForm.getSearchUser());
            if(userList != null && !userList.isEmpty()){
                userForm.setStatusMessage("Successfully fetched "+userList.size()+ " Users");
                userForm.setStatusMessageType("info");
            }
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to search due to a database error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(" Exception type in controller {}" , e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(" An error occurred while fetching data from database. !! ");
            } else {
                logger.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to search due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(" An Unknown Error has been occurred !!");

        }
        if (userList != null) {
            userList.stream().map(userIteration -> " user detail " + userIteration.toString()).forEach(logger::info);
        }
        userForm.setUserVOs(userList);
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/UserList", USER_FORM, userForm);
    }

}
