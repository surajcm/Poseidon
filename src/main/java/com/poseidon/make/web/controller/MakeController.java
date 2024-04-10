package com.poseidon.make.web.controller;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.make.web.form.MakeForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:24:14 PM
 */
@Controller
@SuppressWarnings("unused")
public class MakeController {
    private static final Logger logger = LoggerFactory.getLogger(MakeController.class);
    private static final String DANGER = "danger";
    private static final String MAKE_FORM = "makeForm";
    private static final String SUCCESS = "success";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String MAKE_FORM_IS = " makeForm is {}";
    private static final String MAKE_VO_IS = " makeVO is {}";
    private static final String MODEL_LIST_PAGE = "model/list";
    private static final String MAKE_LIST_PAGE = "make/list";
    private static final String MAKE_AND_MODEL_VO_IS = " MakeAndModelVO is {}";

    private final MakeService makeService;

    public MakeController(final MakeService makeService) {
        this.makeService = makeService;
    }

    /**
     * list out makes.
     *
     * @param makeForm makeForm
     * @return view
     */
    @RequestMapping(value = "make/MakeList", method = {RequestMethod.GET, RequestMethod.POST})
    public String makeListPage(final MakeForm makeForm, final Model model) {
        logger.info("ListMake List method of MakeController ");
        var makes = makeService.fetchMakes();
        model.addAttribute("makes", makes);
        model.addAttribute(MAKE_FORM, new MakeForm());
        return MAKE_LIST_PAGE;
    }

    /**
     * list out makes.
     *
     * @param pageNumber int
     * @return view
     */
    @GetMapping(value = "make/page/{pageNumber}")
    public String makeListByPage(final @PathVariable(name = "pageNumber") int pageNumber,
                                 final Model model) {
        logger.info("ListMake with page of MakeController ");
        var makes = makeService.fetchMakes();
        model.addAttribute("makes", makes);
        return MAKE_LIST_PAGE;
    }

    /**
     * delete the user.
     *
     * @param id id
     * @return to user list screen
     */
    @GetMapping("/make/delete/{id}")
    public String deleteMake(final @PathVariable(name = "id") Long id,
                             final Model model,
                             final RedirectAttributes redirectAttributes) {
        logger.info("Inside deleteMake method of make controller with id {}", id);
        try {
            makeService.deleteMake(id);
            redirectAttributes.addFlashAttribute("message", "Successfully deleted the selected Make");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "Error occurred during deletion");
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return "redirect:/make/MakeList";
    }

    @GetMapping("/make/makeForEdit")
    public @ResponseBody
    Map<String, String> makeForEdit(@ModelAttribute("id") final String id,
                                    final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("makeForEdit method of make controller {}", sanitizedId);
        var make = makeService.getMakeFromId(Long.valueOf(id));
        return make.map(item -> Map.of(item.getMakeName(), item.getDescription())).orElse(Collections.emptyMap());
    }

    /**
     * search for a model.
     *
     * @return view
     */
    @GetMapping("/make/search/{modelName}")
    public String searchMake(@PathVariable(name = "modelName") final String modelName, final Model model) {
        logger.info("SearchMake method of MakeController. Params are {}", modelName);
        var makes = makeService.searchMakes(modelName);
        makes.forEach(make -> logger.debug("make is {}", make));
        model.addAttribute("makes", makes);
        return MAKE_LIST_PAGE;
    }

    /**
     * save make.
     *
     * @param selectMakeName selectMakeName
     * @param selectMakeDesc selectMakeDesc
     * @param result         BindingResult
     * @return as json
     */
    @PostMapping("/make/saveMake")
    public @ResponseBody
    List<MakeVO> saveMake(@ModelAttribute("selectMakeName") final String selectMakeName,
                          @ModelAttribute("selectMakeDesc") final String selectMakeDesc,
                          final BindingResult result) {
        logger.info("SaveMake method of MakeController");
        if (result.hasErrors()) {
            logger.info("errors {}", result);
        }
        var makeForm = new MakeForm();
        var sanitizedSelectMakeName = CommonUtils.sanitizedString(selectMakeName);
        var sanitizedSelectMakeDesc = CommonUtils.sanitizedString(selectMakeDesc);
        logger.info("selectMakeName : {}", sanitizedSelectMakeName);
        logger.info("selectMakeDesc : {}", sanitizedSelectMakeDesc);
        var userName = findLoggedInUsername();
        makeService.addNewMake(sanitizedSelectMakeName, sanitizedSelectMakeDesc, userName);
        return convertMakeToMakeVO(makeService.fetchMakes());
    }

    @GetMapping("/make/getAllMakeIdsAndNames")
    public @ResponseBody
    Map<Long, String> getAllMakeIdsAndNames() {
        logger.info("GetAllMakeIdsAndNames method of MakeController ");
        var makeVOS = convertMakeToMakeVO(makeService.fetchMakes());
        return makeVOS.stream()
                .collect(Collectors.toMap(MakeVO::getId, MakeVO::getMakeName, (a, b) -> b));
    }

    @PutMapping("/make/updateMake")
    public @ResponseBody
    List<MakeVO> updateMake(@ModelAttribute("id") final Long id,
                            @ModelAttribute("makeName") final String makeName,
                            @ModelAttribute("description") final String description,
                            final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id.toString());
        var sanitizedMakeName = CommonUtils.sanitizedString(makeName);
        var sanitizedDescription = CommonUtils.sanitizedString(description);
        logger.info("updateMake method of make controller with id {}, makeName {}, description {}",
                sanitizedId, sanitizedMakeName, sanitizedDescription);
        var makeModelVO = buildMakeModelVO(id, makeName, description);
        makeService.updateMake(makeModelVO);
        return convertMakeToMakeVO(makeService.fetchMakes());
    }

    private void loggingFromSearch(final MakeForm makeForm) {
        logger.info("SearchMake method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        logger.info("MakeForm instance to search {}", sanitizedMakeForm);
        if (makeForm.getSearchMakeAndModelVO() != null) {
            var sanitizedSearchModel = CommonUtils.sanitizedString(
                    makeForm.getSearchMakeAndModelVO().toString());
            logger.info("SearchVO instance to search {}", sanitizedSearchModel);
        }
    }

    private MakeAndModelVO buildMakeModelVO(final Long id,
                                            final String makeName,
                                            final String description) {
        var vo = new MakeAndModelVO();
        vo.setMakeId(id);
        vo.setMakeName(makeName);
        vo.setDescription(description);
        vo.setModifiedBy(findLoggedInUsername());
        return vo;
    }

    public String findLoggedInUsername() {
        var username = "";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            username = auth.getName();
        }
        return username;
    }

    private List<MakeVO> convertMakeToMakeVO(final List<Make> makes) {
        return makes.stream().map(this::createMakeVO).toList();
    }

    private MakeVO createMakeVO(final Make make) {
        var makeVO = new MakeVO();
        makeVO.setId(make.getId());
        makeVO.setMakeName(make.getMakeName());
        makeVO.setDescription(make.getDescription());
        makeVO.setCreatedBy(make.getCreatedBy());
        makeVO.setModifiedBy(make.getModifiedBy());
        return makeVO;
    }

}
