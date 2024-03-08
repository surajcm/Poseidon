package com.poseidon.user.web.controller;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.init.util.FileUploadUtil;
import com.poseidon.user.dao.entities.Role;
import com.poseidon.user.dao.entities.User;
import com.poseidon.user.service.UserService;
import com.poseidon.user.web.form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.poseidon.init.Constants.PAGE_SIZE;

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
    private static final String ALL_ROLES = "allRoles";

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }


    /**
     * Used to list all users (can be done only by admin user).
     *
     * @return to user list screen
     */
    @RequestMapping(value = "/user/listAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String listFirstPage(final Model model) {
        return listByPage(1, model);
    }

    @RequestMapping("/user/page/{pageNumber}")
    public String listByPage(final @PathVariable(name = "pageNumber") int pageNumber,
                             final Model model) {
        logger.info("ListByPage method of user controller ");
        var page = userService.getAllUsers(pageNumber, activeCompanyCode());
        var startCount = (pageNumber - 1) * PAGE_SIZE + 1;
        long endCount = (long) startCount + PAGE_SIZE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute(ALL_ROLES, fullRoleMap());
        model.addAttribute("users", page.getContent());
        model.addAttribute(USER_FORM, new UserForm());
        return USER_LIST;
    }

    /**
     * save user.
     *
     * @param thumbnail          thumbnail image
     * @param name               String
     * @param email              String
     * @param roles              Set of String
     * @param redirectAttributes RedirectAttributes
     * @return String
     */
    @PostMapping("/user/saveUser")
    public @ResponseBody
    Set<User> saveUser(
            @RequestParam(value = "thumbnail", required = false) final MultipartFile thumbnail,
            @RequestParam("selectName") final String name,
            @RequestParam("selectLogin") final String email,
            @RequestParam("selectRole") final Set<Long> roles,
            final RedirectAttributes redirectAttributes) throws IOException {
        logger.info("SaveUser method of user controller ");
        logger.info("inputs are : name {}, email {}, role {}", name, email, roles);
        var user = populateUser(name, email, roles);
        if (thumbnail != null && thumbnail.getOriginalFilename() != null) {
            logger.info("Image file name is, {}", thumbnail.getOriginalFilename());
            var fileName = StringUtils.cleanPath(thumbnail.getOriginalFilename());
            user.setPhoto(fileName);
            var savedUser = userService.save(user);
            var uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, thumbnail);
        } else {
            logger.info("Thumbnail is null");
            userService.save(user);
        }
        logger.info("Successfully saved user");
        return allUsers();
    }

    private User populateUser(final String name,
                              final String email,
                              final Set<Long> roles) {
        var companyCode = activeCompanyCode();
        var currentLoggedInUser = currentLoggedInUser();
        var user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword("password");
        user.setEnabled(false);
        user.setRoles(roles.stream().map(Role::new).collect(Collectors.toSet()));
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
        var searcher = userForm.getSearchUser();
        var users = new HashSet<>(userService.searchUserDetails(searcher,
                userForm.isStartsWith(),
                userForm.isIncludes()));
        if (!users.isEmpty()) {
            userForm.setStatusMessage("Successfully fetched " + users.size() + " Users");
            userForm.setStatusMessageType("info");
            users.stream().map(User::toString).forEach(logger::info);
        } else {
            userForm.setStatusMessage("No results found");
            userForm.setStatusMessageType(DANGER);
        }
        model.addAttribute("users", users);
        model.addAttribute(ALL_ROLES, fullRoleMap());
        model.addAttribute(USER_FORM, userForm);
        return USER_LIST;
    }

    @GetMapping("/user/getForEdit")
    public @ResponseBody
    User getForEdit(@ModelAttribute("id") final String id,
                    final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("getForEdit method of user controller : {}", sanitizedId);
        return userService.getUserDetailsFromId(Long.valueOf(id))
                .map(this::removePassword)
                .orElse(null);
    }

    private User removePassword(final User user) {
        user.setPassword("");
        return user;
    }

    @PutMapping("/user/updateUser")
    public @ResponseBody
    Set<User> updateUser(@RequestParam(value = "thumbnail", required = false) final MultipartFile thumbnail,
                         @RequestParam("id") final String id,
                         @RequestParam("name") final String name,
                         @RequestParam("email") final String email,
                         @RequestParam("roles") final Set<Long> roles,
                         final RedirectAttributes redirectAttributes) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        var sanitizedName = CommonUtils.sanitizedString(name);
        var sanitizedEmail = CommonUtils.sanitizedString(email);
        logger.info("updateUser method of user controller with id {}, name {}, email {}, roles {}",
                sanitizedId, sanitizedName,
                sanitizedEmail, roles);
        try {
            var user = userService.getUserDetailsFromId(Long.valueOf(id));
            if (user.isPresent()) {
                var newUser = user.get();
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setRoles(roles.stream().map(Role::new).collect(Collectors.toSet()));
                var loggedInUser = currentLoggedInUser();
                if (thumbnail != null && thumbnail.getOriginalFilename() != null) {
                    logger.info("Image file name is, {}", thumbnail.getOriginalFilename());
                    var fileName = StringUtils.cleanPath(thumbnail.getOriginalFilename());
                    newUser.setPhoto(fileName);
                    var savedUser = userService.updateUser(newUser, loggedInUser);
                    var uploadDir = "user-photos/" + savedUser.getId();
                    FileUploadUtil.cleanDir(uploadDir);
                    FileUploadUtil.saveFile(uploadDir, fileName, thumbnail);
                } else {
                    logger.info("Thumbnail is null");
                    userService.updateUser(newUser, loggedInUser);
                }
                userService.updateUser(newUser, loggedInUser);
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return allUsers();
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
        var searcher = formSearch(currentLoggedInUser);
        var userList = userService.searchUserDetails(searcher, true, true)
                .stream().toList();
        AbstractMap.SimpleEntry<String, String> entry;
        if (userService.comparePasswords(current, userList.get(0).getPassword())) {
            var user = userList.get(0);
            userService.updateWithNewPassword(user, newPass, currentLoggedInUser());
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
     * @param id id
     * @return to user list screen
     */
    @GetMapping("/user/deleteUser/{id}")
    public String deleteUser(final @PathVariable(name = "id") Long id,
                             final Model model,
                             final RedirectAttributes redirectAttributes) {
        logger.info("Inside DeleteUser method of user controller with id {}", id);
        try {
            userService.deleteUser(id);
            model.addAttribute(ALL_ROLES, fullRoleMap());
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return "redirect:/user/listAll";
    }

    private String currentLoggedInUser() {
        var username = "";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            username = auth.getName();
        }
        return username;
    }

    private Set<User> allUsers() {
        var users = userService.getAllUserDetails(activeCompanyCode());
        logger.info("users are  of size {}", users.size());
        users.forEach(u -> u.setPassword(""));
        return users;
    }

    private User formSearch(final String name) {
        var user = new User();
        user.setName(name);
        return user;
    }

    private String activeCompanyCode() {
        var currentLoggedInUser = currentLoggedInUser();
        var user = userService.findUserFromName(currentLoggedInUser);
        return user.getCompanyCode();
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String enableUser(final @PathVariable(name = "id") Long id,
                             final @PathVariable(name = "status") boolean status,
                             final RedirectAttributes redirectAttributes) {
        userService.enableUser(id, status);
        var responseStatus = status ? "enabled" : "disabled";
        var message = "The user has been " + responseStatus;
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return "redirect:/user/listAll";
    }

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(final @Param("email") String email) {
        return userService.isEmailUnique(email) ? "OK" : "DUP";
    }
}
