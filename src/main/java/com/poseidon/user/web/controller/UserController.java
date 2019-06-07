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
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Suraj Muraleedharan.
 * Date: Nov 27, 2010
 * Time: 2:38:15 PM
 */
@Controller
@SuppressWarnings("unused")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String USER_FORM = "userForm";
    private static final String USER_LOG_IN = "login";
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String USER_LIST = "user/UserList";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String DB_ERROR = " An error occurred while fetching data from database. !! ";
    private static final String EXCEPTION_IN_CONTROLLER = " Exception type in controller {}";

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    /**
     * Used in automatic redirect to Log in screen.
     *
     * @return ModelAndView to render
     */
    @GetMapping("/")
    public ModelAndView index() {
        logger.info(" Inside Index method of user controller ");
        UserForm userForm = new UserForm();
        UserVO userVo = new UserVO();
        userForm.setUser(userVo);
        return new ModelAndView("MainPage", USER_FORM, userForm);
    }

    /**
     * log in.
     *
     * @param model  Model
     * @param error  String
     * @param logout String
     * @return String
     */
    @GetMapping("/login")
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
     * Used to list all users (can be done only by admin user).
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/ListAll.htm")
    public ModelAndView listAll(UserForm userForm) {
        logger.info(" Inside ListAll method of user controller ");
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to list the Users due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(DB_ERROR);
            } else {
                logger.error(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        if (userList != null) {
            userList.forEach(userIteration -> logger.info(" user detail {}", userIteration));
        }
        userForm.setUserVOs(userList);
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setSearchUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView(USER_LIST, USER_FORM, userForm);
    }

    /**
     * populateRoles.
     *
     * @return List of String
     */
    private List<String> populateRoles() {
        return Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toList());
    }

    /**
     * Screen to add a new user.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/AddUser.htm")
    public ModelAndView addUser(UserForm userForm) {
        logger.info(" Inside AddUser method of user controller ");
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/userAdd", USER_FORM, userForm);
    }

    /**
     * add a new user to database.
     *
     * @param userForm user instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/SaveUser.htm")
    public ModelAndView saveUser(UserForm userForm) {
        logger.info(" Inside SaveUser method of user controller ");
        logger.info(" user instance to add to database {}", userForm);
        try {
            userForm.getUser().setCreatedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            userForm.getUser().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            userForm.getUser().setCreatedBy(userForm.getLoggedInUser());
            userForm.getUser().setLastModifiedBy(userForm.getLoggedInUser());
            userService.save(userForm.getUser());
            userForm.setStatusMessage("Successfully saved the new user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to save the user due to a database error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(DB_ERROR);
            } else {
                logger.info(UNKNOWN_ERROR);
            }

        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to save the user due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to list the Users due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(DB_ERROR);
            } else {
                logger.info(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        if (userList != null) {
            userList.forEach(userIteration -> logger.info(" user detail : {}", userIteration));
        }
        userForm.setUserVOs(userList);
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setSearchUser(new UserVO());
        userForm.setRoleList(populateRoles());
        return new ModelAndView(USER_LIST, USER_FORM, userForm);
    }

    /**
     * save user using ajax call.
     *
     * @param selectName  String
     * @param selectLogin String
     * @param selectRole  String
     * @param result      BindingResult
     * @return String
     */
    @PostMapping("/user/saveUserAjax.htm")
    public String saveUserAjax(@ModelAttribute(value = "selectName") String selectName,
                               @ModelAttribute(value = "selectLogin") String selectLogin,
                               @ModelAttribute(value = "selectRole") String selectRole,
                               BindingResult result) {
        logger.info("saveUserAjax method of user controller ");

        logger.info(" At saveUserAjax, selectName is : {}", selectName);
        logger.info(" At saveUserAjax, selectLogin : {}", selectLogin);
        logger.info(" At saveUserAjax, selectRole : {}", selectRole);
        UserVO ajaxUserVo = new UserVO();
        ajaxUserVo.setName(selectName);
        ajaxUserVo.setLoginId(selectName);
        ajaxUserVo.setRole(selectRole);
        //todo : find out a way to get current user
        ajaxUserVo.setPassword("password");
        ajaxUserVo.setCreatedDate(OffsetDateTime.now(ZoneId.systemDefault()));
        ajaxUserVo.setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
        ajaxUserVo.setCreatedBy("-ajax-");
        ajaxUserVo.setLastModifiedBy("-ajax-");
        try {
            userService.save(ajaxUserVo);
        } catch (UserException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(DB_ERROR);
            } else {
                logger.info(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        List<UserVO> userList = null;
        try {
            userList = userService.getAllUserDetails();
        } catch (UserException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(DB_ERROR);
            } else {
                logger.info(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        return fetchJsonUserList(userList);
    }

    /**
     * fetch user list as json.
     *
     * @param userList List of UserVO
     * @return String
     */
    private String fetchJsonUserList(List<UserVO> userList) {
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
     * Screen to add a new user.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/EditUser.htm")
    public ModelAndView editUser(UserForm userForm) {
        logger.info(" Inside EditUser method of user controller ");
        logger.info(" user is {}", userForm);
        UserVO userVo = null;
        try {
            userVo = userService.getUserDetailsFromId(userForm.getId());
        } catch (UserException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(DB_ERROR);
            } else {
                logger.info(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        if (userVo == null) {
            logger.error(" No details found for current user !!");
        } else {
            logger.info(" user details are {}", userVo);
        }
        userForm.setUser(userVo);
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        userForm.setRoleList(populateRoles());
        return new ModelAndView("user/userEdit", USER_FORM, userForm);
    }

    /**
     * updates the user.
     *
     * @param userForm user instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/updateUser.htm")
    public ModelAndView updateUser(UserForm userForm) {
        logger.info(" Inside updateUser method of user controller ");
        try {
            userForm.getUser().setLastModifiedBy(userForm.getLoggedInUser());
            userForm.getUser().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            logger.info(" user instance to update {}", userForm.getUser());
            userService.updateUser(userForm.getUser());
            userForm.setStatusMessage("Successfully updated the user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to update the user due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        return listAll(userForm);
    }

    /**
     * delete the user.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/DeleteUser.htm")
    public ModelAndView deleteUser(UserForm userForm) {
        logger.info(" Inside DeleteUser method of user controller ");
        logger.info(" user is {}", userForm);
        try {
            userService.deleteUser(userForm.getId());
            userForm.setStatusMessage("Successfully deleted the user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to delete the user due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        return listAll(userForm);
    }

    /**
     * Screen to home.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/ToHome.htm")
    public ModelAndView toHome(UserForm userForm) {
        logger.info(" Inside ToHome method of user controller ");
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        return new ModelAndView("MainPage", USER_FORM, userForm);
    }

    /**
     * Screen to log out.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/LogMeOut.htm")
    public ModelAndView logMeOut(UserForm userForm) {
        logger.info(" Inside LogMeOut method of user controller ");
        return new ModelAndView(USER_LOG_IN, USER_FORM, new UserForm());
    }

    /**
     * Screen to search for a user.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/SearchUser.htm")
    public ModelAndView searchUser(UserForm userForm) {
        logger.info(" Inside SearchUser method of user controller ");
        logger.info(" user Details are {}", userForm.getSearchUser());
        List<UserVO> userList = null;
        try {
            userList = userService.searchUserDetails(userForm.getSearchUser());
            if (userList != null && !userList.isEmpty()) {
                userForm.setStatusMessage("Successfully fetched " + userList.size() + " Users");
                userForm.setStatusMessageType("info");
            }
        } catch (UserException e) {
            userForm.setStatusMessage("Unable to search due to a database error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e.getLocalizedMessage());
            logger.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(UserException.DATABASE_ERROR)) {
                logger.info(DB_ERROR);
            } else {
                logger.info(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            userForm.setStatusMessage("Unable to search due to an error");
            userForm.setStatusMessageType(ERROR);
            logger.error(e1.getLocalizedMessage());
            logger.info(UNKNOWN_ERROR);
        }
        if (userList != null) {
            userList.stream().map(userIteration -> " user detail " + userIteration.toString()).forEach(logger::info);
        }
        userForm.setUserVOs(userList);
        userForm.setRoleList(populateRoles());
        return new ModelAndView(USER_LIST, USER_FORM, userForm);
    }

}
