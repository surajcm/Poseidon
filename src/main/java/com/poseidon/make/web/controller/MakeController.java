package com.poseidon.make.web.controller;

import com.poseidon.init.util.CommonUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @PostMapping("make/MakeList")
    public String makeListPage(final MakeForm makeForm, final Model model) {
        logger.info("ListMake List method of MakeController ");
        var makeVOs = makeService.fetchMakes();
        if (!makeVOs.isEmpty()) {
            makeVOs.forEach(makeVO -> logger.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeVOs(makeVOs);
        }
        model.addAttribute(MAKE_FORM, makeForm);
        return MAKE_LIST_PAGE;
    }

    /**
     * delete a make.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/deleteMake")
    @SuppressWarnings("unused")
    public String deleteMake(final MakeForm makeForm, final Model model) {
        logger.debug("DeleteMake method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        logger.debug(MAKE_FORM_IS, sanitizedMakeForm);
        try {
            makeService.deleteMake(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Make");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            makeForm.setStatusMessage("Error occurred during deletion");
            makeForm.setStatusMessageType(DANGER);
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return makeListPage(makeForm, model);
    }



    @GetMapping("/make/getForEdit")
    public @ResponseBody
    Map<Long, String> getForEdit(@ModelAttribute("id") final String id,
                                 final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("getForEdit method of make controller {}}", sanitizedId);
        var makeVO = makeService.getModelFromId(Long.valueOf(id));
        return makeVO.map(vo -> Map.of(vo.getMakeId(), vo.getModelName())).orElse(Collections.emptyMap());
    }

    @GetMapping("/make/makeForEdit")
    public @ResponseBody
    Map<String, String> makeForEdit(@ModelAttribute("id") final String id,
                                    final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        logger.info("makeForEdit method of make controller {}", sanitizedId);
        var makeVO = makeService.getMakeFromId(Long.valueOf(id));
        return makeVO.map(vo -> Map.of(vo.getMakeName(), vo.getDescription())).orElse(Collections.emptyMap());
    }

    /**
     * search for a model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/searchModel")
    public String searchModel(final MakeForm makeForm, final Model model) {
        loggingFromSearch(makeForm);
        var makeVOs = makeService.searchMakeVOs(makeForm.getSearchMakeAndModelVO());
        makeForm.setStatusMessage("Found " + makeVOs.size() + " Models");
        makeForm.setStatusMessageType("info");
        if (!makeVOs.isEmpty()) {
            makeVOs.forEach(makeVO -> logger.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        var searchMakeVOs = makeService.fetchMakes();
        if (searchMakeVOs != null) {
            searchMakeVOs.forEach(searchMakeVO -> logger.debug("SearchMakeVO is {}", searchMakeVO));
            makeForm.setMakeVOs(searchMakeVOs);
        }
        var userName = findLoggedInUsername();
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        model.addAttribute(MAKE_FORM, makeForm);
        return MODEL_LIST_PAGE;
    }

    /**
     * search for a model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/searchMake")
    public String searchMake(final MakeForm makeForm, final Model model) {
        loggingFromSearch(makeForm);
        var searchMakeVOs = makeService.searchMakes(makeForm.getSearchMakeAndModelVO().getMakeName());
        makeForm.setStatusMessage("Found " + searchMakeVOs.size() + " Models");
        makeForm.setStatusMessageType("info");
        searchMakeVOs.forEach(searchMakeVO -> logger.debug("searchMakeVO is {}", searchMakeVO));
        makeForm.setMakeVOs(searchMakeVOs);
        model.addAttribute(MAKE_FORM, makeForm);
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
        makeForm.setCurrentMakeAndModeVO(populateMakeVO(selectMakeName, selectMakeDesc));
        makeService.addNewMake(makeForm.getCurrentMakeAndModeVO());
        return makeService.fetchMakes();
    }

    /**
     * saveModel.
     *
     * @param selectMakeId    selectMakeId
     * @param selectModelName selectModelName
     * @param result          result
     * @return json string
     */
    @PostMapping("/make/saveModel")
    public @ResponseBody
    List<MakeAndModelVO> saveModel(@ModelAttribute("selectMakeId") final Long selectMakeId,
                                   @ModelAttribute("selectModelName") final String selectModelName,
                                   final BindingResult result) {
        logger.info("SaveModel method of MakeController ");
        if (!result.hasErrors()) {
            makeService.addNewModel(populateModelVO(selectMakeId, selectModelName));
        } else {
            logger.info("errors {}", result);
        }
        return makeService.listAllMakesAndModels();
    }

    @GetMapping("/make/getAllMakeIdsAndNames")
    public @ResponseBody
    Map<Long, String> getAllMakeIdsAndNames() {
        logger.info("GetAllMakeIdsAndNames method of MakeController ");
        var makeVOS = makeService.fetchMakes();
        return makeVOS.stream()
                .collect(Collectors.toMap(MakeVO::getId, MakeVO::getMakeName, (a, b) -> b));
    }

    @PutMapping("/make/updateModel")
    public @ResponseBody
    List<MakeAndModelVO> updateModel(@ModelAttribute("id") final Long id,
                                     @ModelAttribute("modalMakeName") final Long makeId,
                                     @ModelAttribute("modalModelName") final String modalModelName,
                                     final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id.toString());
        var sanitizedMakeId = CommonUtils.sanitizedString(makeId.toString());
        var sanitizedModelName = CommonUtils.sanitizedString(modalModelName);
        logger.info("UpdateModel method of make controller with id {}, makeId {}, modalModelName {}",
                sanitizedId, sanitizedMakeId, sanitizedModelName);
        makeService.updateModel(id, makeId, modalModelName);
        return makeService.listAllMakesAndModels();
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
        return makeService.fetchMakes();
    }

    private void loggingFromSearch(final MakeForm makeForm) {
        logger.debug("SearchModel method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        logger.debug("MakeForm instance to search {}", sanitizedMakeForm);
        if (makeForm.getSearchMakeAndModelVO() != null) {
            var sanitizedSearchModel = CommonUtils.sanitizedString(
                    makeForm.getSearchMakeAndModelVO().toString());
            logger.debug("SearchVO instance to search {}", sanitizedSearchModel);
        }
    }

    private MakeAndModelVO populateMakeVO(final String selectMakeName, final String selectMakeDesc) {
        var sanitizedSelectMakeName = CommonUtils.sanitizedString(selectMakeName);
        var sanitizedSelectMakeDesc = CommonUtils.sanitizedString(selectMakeDesc);
        logger.info("selectMakeName : {}", sanitizedSelectMakeName);
        logger.info("selectMakeDesc : {}", sanitizedSelectMakeDesc);
        var userName = findLoggedInUsername();
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeName(selectMakeName);
        makeAndModelVO.setDescription(selectMakeDesc);
        makeAndModelVO.setCreatedBy(userName);
        makeAndModelVO.setModifiedBy(userName);
        return makeAndModelVO;
    }

    private MakeAndModelVO populateModelVO(final Long selectMakeId, final String selectModelName) {
        var sanitizedSelectMakeId = CommonUtils.sanitizedString(selectMakeId.toString());
        var sanitizedSelectModelName = CommonUtils.sanitizedString(selectModelName);
        logger.info("selectMakeId : {}", sanitizedSelectMakeId);
        logger.info("selectModelName : {}", sanitizedSelectModelName);
        var userName = findLoggedInUsername();
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(selectMakeId);
        makeAndModelVO.setModelName(selectModelName);
        makeAndModelVO.setCreatedBy(userName);
        makeAndModelVO.setModifiedBy(userName);
        return makeAndModelVO;
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

}
