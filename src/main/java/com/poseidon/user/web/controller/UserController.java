package com.poseidon.user.web.controller;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.service.SecurityService;
import com.poseidon.user.service.UserService;
import com.poseidon.user.web.form.UserForm;
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

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    private static final String MESSAGE = "message";

    private final UserService userService;

    private final SecurityService securityService;

    public UserController(final UserService userService, final SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    /**
     * Used in automatic redirect to Log in screen.
     *
     * @return MainPage screen
     */
    @GetMapping("/")
    public String welcome() {
        return "MainPage";
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
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute(DANGER, "Your username or password is invalid.");
        }
        if (logout != null) {
            model.addAttribute(MESSAGE, "You have been logged out successfully.");
        }
        return USER_LOG_IN;
    }

    /**
     * Screen to home.
     *
     * @return to home screen
     */
    @PostMapping("/home")
    public String toHome() {
        logger.info("Inside ToHome method of user controller");
        return "MainPage";
    }

    /**
     * Screen to log out.
     *
     * @return to logout screen
     */
    @PostMapping("/logMeOut")
    public String logMeOut(final HttpServletRequest request) {
        logger.info("Inside LogMeOut method of user controller");
        var session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        return USER_LOG_IN;
    }

    /**
     * Used to list all users (can be done only by admin user).
     *
     * @param userForm userForm instance
     * @return to user list screen
     */
    @PostMapping("/user/listAll")
    public String listAllUsers(@ModelAttribute final UserForm userForm,
                               final Model model,
                               final RedirectAttributes redirectAttributes) {
        logger.info("ListAll method of user controller ");
        var userList = new HashSet<>(allUsers());
        if (userList.isEmpty()) {
            userForm.setStatusMessage("No user found");
            userForm.setStatusMessageType(DANGER);
        }
        userForm.setUserVOs(userList);
        model.addAttribute("allRoles", fullRoleMap());
        model.addAttribute(USER_FORM, userForm);
        return USER_LIST;
    }

    private UserVO convertToUserVO(final User user) {
        var userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setEmail(user.getEmail());
        userVO.setPassword(user.getPassword());
        userVO.setRoles(user.getRoles());
        userVO.setCompanyCode(user.getCompanyCode());
        userVO.setEnabled(user.getEnabled());
        return userVO;
    }

    /**
     * save user.
     *
     * @param name  String
     * @param email String
     * @param role  String
     * @param result      BindingResult
     * @return String
     */
    @PostMapping("/user/saveUser")
    public @ResponseBody
    List<UserVO> saveUser(@ModelAttribute("selectName") final String name,
                          @ModelAttribute("selectLogin") final String email,
                          @ModelAttribute("selectRole") final String role,
                          final BindingResult result) {
        logger.info("SaveUser method of user controller ");
        logger.info("inputs are : name {}, email {}, role {}", name, email, role);
        var user = populateUser(name, email, role);
        userService.save(user);
        logger.info("Successfully saved user");
        return allUsers();
    }

    private User populateUser(final String name,
                                  final String email,
                                  final String role) {
        var companyCode = activeCompanyCode();
        var currentLoggedInUser = currentLoggedInUser();
        var user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword("password");
        user.setEnabled(false);
        user.addRole(new Role(Long.valueOf(role)));
        user.setCompanyCode(companyCode);
        user.setCreatedBy(currentLoggedInUser);
        user.setModifiedBy(currentLoggedInUser);
        return user;
    }

    /**
     * Screen to search for a user.
     *
     * @param userForm userForm instance
     * @return to user list screen
     */
    @PostMapping("/user/searchUser")
    public String searchUser(final UserForm userForm, final Model model) {
        logger.info(" Inside SearchUser method of user controller ");
        var searcher = convertToUser(userForm.getSearchUser(), "");
        var users = userService.searchUserDetails(searcher,
                userForm.isStartsWith(),
                userForm.isIncludes())
                .stream().map(this::convertToUserVO)
                .collect(Collectors.toSet());
        if (!users.isEmpty()) {
            userForm.setStatusMessage("Successfully fetched " + users.size() + " Users");
            userForm.setStatusMessageType("info");
            users.stream().map(UserVO::toString).forEach(logger::info);
        } else {
            userForm.setStatusMessage("No results found");
            userForm.setStatusMessageType(DANGER);
        }
        userForm.setUserVOs(users);
        userForm.setRoles(userService.getAllRoles());
        model.addAttribute(USER_FORM, userForm);
        return USER_LIST;
    }

    @PostMapping("/user/passwordReset")
    public String reset() {
        logger.info("Password reset view of user controller");
        return "user/PasswordReset";
    }

    @PostMapping("/user/passwordExpire")
    public @ResponseBody
    Boolean passwordExpiry(@ModelAttribute("id") final String id,
                           final BindingResult result) {
        userService.expireUser(Long.valueOf(id));
        return Boolean.TRUE;
    }

    @GetMapping("/user/getForEdit")
    public @ResponseBody
    UserVO getForEdit(@ModelAttribute("id") final String id,
                      final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("getForEdit method of user controller : {}", sanitizedId);
        return userService.getUserDetailsFromId(Long.valueOf(id))
                .map(this::convertToUserVO)
                .map(this::removePassword)
                .orElse(null);
    }

    private UserVO removePassword(final UserVO userVO) {
        userVO.setPassword("");
        return userVO;
    }

    @PutMapping("/user/updateUser")
    public @ResponseBody
    List<UserVO> updateUser(@ModelAttribute("id") final String id,
                            @ModelAttribute("name") final String name,
                            @ModelAttribute("email") final String email,
                            @ModelAttribute("role") final String role,
                            final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        var sanitizedName = CommonUtils.sanitizedString(name);
        var sanitizedEmail = CommonUtils.sanitizedString(email);
        var sanitizedRole = CommonUtils.sanitizedString(role);
        logger.info("updateUser method of user controller with id {}, name {}, email {}, role {}",
                sanitizedId, sanitizedName,
                sanitizedEmail, sanitizedRole);
        try {
            var user = userService.getUserDetailsFromId(Long.valueOf(id));
            if (user.isPresent()) {
                var newUser = user.get();
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.addRole(new Role(Long.valueOf(role)));
                var loggedInUser = currentLoggedInUser();
                userService.updateUser(newUser, loggedInUser);
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return allUsers();
    }


    private User convertToUser(final UserVO userVO, final String currentLoggedInUser) {
        var user = new User();
        user.setName(userVO.getName());
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setEnabled(userVO.getEnabled());
        user.setRoles(userVO.getRoles());
        user.setCompanyCode(userVO.getCompanyCode());
        user.setCreatedBy(currentLoggedInUser);
        user.setModifiedBy(currentLoggedInUser);
        return user;
    }

    @PostMapping("/user/changePasswordAndSaveIt")
    public @ResponseBody
    AbstractMap.SimpleEntry<String, String> changePass(@ModelAttribute("current") final String current,
                                                       @ModelAttribute("newPass") final String newPass,
                                                       final BindingResult result) {
        var sanitizedCurrent = CommonUtils.sanitizedString(current);
        var sanitizedPass = CommonUtils.sanitizedString(newPass);
        logger.info("ChangePass of user controller from {} to {}", sanitizedCurrent,
                sanitizedPass);
        var currentLoggedInUser = currentLoggedInUser();
        var searcher = convertToUser(formSearch(currentLoggedInUser), currentLoggedInUser);
        var userList = userService.searchUserDetails(searcher, true, true)
                .stream().map(this::convertToUserVO).toList();
        AbstractMap.SimpleEntry<String, String> entry;

        if (userService.comparePasswords(current, userList.get(0).getPassword())) {
            var userVO = userList.get(0);
            var originalUser = convertToUser(userVO, currentLoggedInUser);
            userService.updateWithNewPassword(originalUser, newPass, currentLoggedInUser());
            entry = new AbstractMap.SimpleEntry<>(SUCCESS, "The password has been reset !!");
        } else {
            entry = new AbstractMap.SimpleEntry<>(MESSAGE,
                    "The password didnt match with the one already saved !!");
        }
        return entry;
    }

    @GetMapping("/roles/")
    public @ResponseBody Map<Long, String> allRoles() {
        logger.info("Inside allRoles method of user controller ");
        return fullRoleMap();
    }

    private Map<Long, String> fullRoleMap() {
        return userService.getAllRoles().stream().collect(Collectors.toMap(Role::getId, Role::getName));
    }

    /**
     * delete the user.
     *
     * @param userForm userForm instance
     * @return to user list screen
     */
    @PostMapping("/user/deleteUser")
    public String deleteUser(final UserForm userForm,
                             final Model model,
                             final RedirectAttributes redirectAttributes) {
        logger.info("Inside DeleteUser method of user controller ");
        try {
            userService.deleteUser(userForm.getId());
            userForm.setStatusMessage("Successfully deleted the user");
            userForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            userForm.setStatusMessage("Error occurred during deletion");
            userForm.setStatusMessageType(DANGER);
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return listAllUsers(userForm, model, redirectAttributes);
    }

    private String currentLoggedInUser() {
        var username = "";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            username = auth.getName();
        }
        return username;
    }

    private List<UserVO> allUsers() {
        var userList = userService.getAllUserDetails(activeCompanyCode())
                .stream().map(this::convertToUserVO).toList();
        logger.info("user list is {}", userList);
        userList.forEach(u -> u.setPassword(""));
        return userList;
    }

    private UserVO formSearch(final String name) {
        var userVO = new UserVO();
        userVO.setName(name);
        return userVO;
    }

    private String activeCompanyCode() {
        var currentLoggedInUser = currentLoggedInUser();
        var user = userService.findUserFromName(currentLoggedInUser);
        return user.getCompanyCode();
    }
}
