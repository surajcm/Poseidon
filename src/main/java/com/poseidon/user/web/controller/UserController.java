package com.poseidon.user.web.controller;

import com.poseidon.user.domain.UserVO;
import com.poseidon.user.service.SecurityService;
import com.poseidon.user.service.UserService;
import com.poseidon.user.web.form.UserForm;
import com.poseidon.init.util.CommonUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.poseidon.user.web.form.Role.populateRoles;


@Controller
@SuppressWarnings("unused")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String USER_FORM = "userForm";
    private static final String USER_LOG_IN = "login";
    private static final String DANGER = "danger";
    private static final String SUCCESS = "success";
    private static final String USER_LIST = "user/UserList";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String DB_ERROR = " An error occurred while fetching data from database. !! ";
    private static final String EXCEPTION_IN_CONTROLLER = " Exception type in controller {}";

    private final UserService userService;

    private final SecurityService securityService;

    public UserController(final UserService userService, final SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    /**
     * Used in automatic redirect to Log in screen.
     *
     * @return ModelAndView to render
     */
    @GetMapping("/")
    public ModelAndView index() {
        logger.info("Index method of user controller ");
        var userForm = new UserForm();
        userForm.setUser(new UserVO());
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
    public String login(final Model model, final String error, final String logout) {
        if (error != null) {
            model.addAttribute(DANGER, "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return USER_LOG_IN;
    }

    /**
     * Screen to home.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/ToHome.htm")
    public ModelAndView toHome(final UserForm userForm) {
        logger.info("Inside ToHome method of user controller");
        userForm.setLoggedInUser(userForm.getLoggedInUser());
        userForm.setLoggedInRole(userForm.getLoggedInRole());
        return new ModelAndView("MainPage", USER_FORM, userForm);
    }

    /**
     * Screen to log out.
     *
     * @return ModelAndView to render
     */
    @PostMapping("/user/LogMeOut.htm")
    public ModelAndView logMeOut(final HttpServletRequest request) {
        logger.info("Inside LogMeOut method of user controller");
        var session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView(USER_LOG_IN, USER_FORM, new UserForm());
    }

    /**
     * Used to list all users (can be done only by admin user).
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/ListAll.htm")
    public ModelAndView listAll(final UserForm userForm) {
        logger.info("ListAll method of user controller ");
        List<UserVO> userList = userService.getAllUserDetails();
        if (userList.isEmpty()) {
            userForm.setStatusMessage("No user found");
            userForm.setStatusMessageType(DANGER);
        }
        userForm.setUserVOs(userList);
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
    public @ResponseBody
    String saveUserAjax(@ModelAttribute("selectName") final String selectName,
                        @ModelAttribute("selectLogin") final String selectLogin,
                        @ModelAttribute("selectRole") final String selectRole,
                        final BindingResult result) {
        logger.info("SaveUserAjax method of user controller ");
        var ajaxUserVo = populateUserVO(selectName, selectLogin, selectRole);
        userService.save(ajaxUserVo, currentLoggedInUser());
        logger.info("Successfully saved user");
        return allUsers();
    }

    /**
     * Screen to search for a user.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/SearchUser.htm")
    public ModelAndView searchUser(final UserForm userForm) {
        logger.info(" Inside SearchUser method of user controller ");
        List<UserVO> userList = userService.searchUserDetails(userForm.getSearchUser(),
                userForm.isStartsWith(),
                userForm.isIncludes());
        if (!userList.isEmpty()) {
            userForm.setStatusMessage("Successfully fetched " + userList.size() + " Users");
            userForm.setStatusMessageType("info");
            userList.stream().map(UserVO::toString).forEach(logger::info);
        } else {
            userForm.setStatusMessage("No results found");
            userForm.setStatusMessageType(DANGER);
        }
        userForm.setUserVOs(userList);
        userForm.setRoleList(populateRoles());
        return new ModelAndView(USER_LIST, USER_FORM, userForm);
    }

    @PostMapping("/user/passwordExpire.htm")
    public @ResponseBody
    Boolean passwordExpiry(@ModelAttribute("id") final String id,
                           final BindingResult result) {
        userService.expireUser(Long.valueOf(id));
        return Boolean.TRUE;
    }

    @GetMapping("/user/getForEdit.htm")
    public @ResponseBody
    String getForEdit(@ModelAttribute("id") final String id,
                      final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("getForEdit method of user controller : {}", sanitizedId);
        String response = null;
        var userVO = userService.getUserDetailsFromId(Long.valueOf(id));
        if (userVO.isPresent()) {
            response = parseUserVO(populateUserEditMap(userVO.get()));
        }
        return response;
    }

    @PutMapping("/user/updateUserAjax.htm")
    public @ResponseBody
    String updateUserAjax(@ModelAttribute("id") final String id,
                          @ModelAttribute("name") final String name,
                          @ModelAttribute("email") final String email,
                          @ModelAttribute("role") final String role,
                          final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        var sanitizedName = CommonUtils.sanitizedString(name);
        var sanitizedEmail = CommonUtils.sanitizedString(email);
        var sanitizedRole = CommonUtils.sanitizedString(role);
        logger.info("updateUserAjax method of user controller with id {}, name {}, email {}, role {}",
                sanitizedId, sanitizedName,
                sanitizedEmail, sanitizedRole);
        try {
            var userVO = userService.getUserDetailsFromId(Long.valueOf(id));
            if (userVO.isPresent()) {
                userVO.get().setName(name);
                userVO.get().setEmail(email);
                userVO.get().setRole(role);
                userService.updateUser(userVO.get(), currentLoggedInUser());
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return allUsers();
    }

    @PostMapping("/user/PasswordReset.htm")
    public ModelAndView reset(final UserForm userForm) {
        logger.info("Password reset view of user controller");
        return new ModelAndView("user/PasswordReset", USER_FORM, userForm);
    }

    @PostMapping("/user/changePasswordAndSaveIt.htm")
    public @ResponseBody
    String changePass(@ModelAttribute("current") final String current,
                      @ModelAttribute("newPass") final String newPass,
                      final BindingResult result) {
        var sanitizedCurrent = CommonUtils.sanitizedString(current);
        var sanitizedPass = CommonUtils.sanitizedString(newPass);
        logger.info("ChangePass of user controller from {} to {}", sanitizedCurrent,
                sanitizedPass);
        String message;
        var auth = SecurityContextHolder.getContext().getAuthentication();
        // get the user info
        var userList = userService.searchUserDetails(formSearch(auth.getName()), true, true);
        if (userService.comparePasswords(current, userList.get(0).getPassword())) {
            var userVO = userList.get(0);
            userService.updateWithNewPassword(userVO, newPass, currentLoggedInUser());
            message = messageJSON(SUCCESS, "The password has been reset !!");
        } else {
            message = messageJSON("message", "The password didnt match with the one already saved !!");
        }
        return message;
    }

    /**
     * delete the user.
     *
     * @param userForm userForm instance
     * @return ModelAndView to render
     */
    @PostMapping("/user/DeleteUser.htm")
    public ModelAndView deleteUser(final UserForm userForm) {
        logger.info(" Inside DeleteUser method of user controller ");
        try {
            userService.deleteUser(userForm.getId());
            userForm.setStatusMessage("Successfully deleted the user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            userForm.setStatusMessage("Error occurred during deletion");
            userForm.setStatusMessageType(DANGER);
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return listAll(userForm);
    }


    private UserVO populateUserVO(final String selectName,
                                  final String selectLogin,
                                  final String selectRole) {
        var ajaxUserVo = new UserVO();
        ajaxUserVo.setName(selectName);
        ajaxUserVo.setEmail(selectLogin);
        ajaxUserVo.setRole(selectRole);
        ajaxUserVo.setPassword("password");
        return ajaxUserVo;
    }

    private String currentLoggedInUser() {
        String username = "";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            username = auth.getName();
        }
        return username;
    }

    private String allUsers() {
        List<UserVO> userList = userService.getAllUserDetails();
        // todo: return a map instead
        userList.forEach(u -> u.setPassword(""));
        return fetchJsonUserList(userList);
    }

    /**
     * fetch user list as json.
     *
     * @param userList List of UserVO
     * @return String
     */
    private String fetchJsonUserList(final List<UserVO> userList) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(userList);
        } catch (IOException ex) {
            response = DANGER;
            logger.error("error parsing to json : {}", ex.getMessage());
        }
        logger.info("user list json : {}", response);
        return response;
    }


    private Map<String, String> populateUserEditMap(final UserVO userVO) {
        Map<String, String> userEditMap = new HashMap<>();
        userEditMap.put("id", String.valueOf(userVO.getId()));
        userEditMap.put("name", userVO.getName());
        userEditMap.put("email", userVO.getEmail());
        userEditMap.put("role", userVO.getRole());
        return userEditMap;
    }

    private String parseUserVO(final Map<String, String> userEditMap) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(userEditMap);
        } catch (IOException ex) {
            response = DANGER;
            logger.error("Error parsing to json : {}", ex.getMessage());
        }
        logger.info("User list json : {}", response);
        return response;
    }

    private UserVO formSearch(final String name) {
        var userVO = new UserVO();
        userVO.setName(name);
        return userVO;
    }

    private String messageJSON(final String type, final String message) {
        String response;
        Map<String, String> messageMap = Map.of(type, message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(messageMap);
        } catch (IOException ex) {
            response = DANGER;
            logger.error("Error parsing to json : {}", ex.getMessage());
        }
        return response;
    }
}
