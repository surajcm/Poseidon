package com.poseidon.make.web.controller;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.make.web.form.MakeForm;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
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
    private static final Logger LOG = LoggerFactory.getLogger(MakeController.class);
    private static final String DANGER = "danger";
    private static final String MAKE_FORM = "makeForm";
    private static final String SUCCESS = "success";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String MAKE_FORM_IS = " makeForm is {}";
    private static final String MAKE_VO_IS = " makeVO is {}";
    private static final String MODEL_LIST_PAGE = "make/ModelList";
    private static final String MAKE_LIST_PAGE = "make/MakeList";
    private static final String MAKE_AND_MODEL_VO_IS = " MakeAndModelVO is {}";

    private final MakeService makeService;

    public MakeController(final MakeService makeService) {
        this.makeService = makeService;
    }

    /**
     * .
     * list all models
     *
     * @param makeForm makeForm
     * @return view
     */
    @RequestMapping(value = MODEL_LIST_PAGE, method = {RequestMethod.POST, RequestMethod.GET})
    public String modelListPage(final MakeForm makeForm, final Model model) {
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.info("Inside List method of MakeController, form details are  {}",
                sanitizedMakeForm);
        var makeAndModelVOs = makeService.listAllMakesAndModels();
        if (!makeAndModelVOs.isEmpty()) {
            makeAndModelVOs.forEach(makeAndModelVO -> LOG.info(MAKE_AND_MODEL_VO_IS, makeAndModelVO));
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }
        var makeVOs = makeService.fetchMakes();
        if (!makeVOs.isEmpty()) {
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        model.addAttribute(MAKE_FORM, makeForm);
        return MODEL_LIST_PAGE;
    }

    /**
     * list out makes.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping(MAKE_LIST_PAGE)
    public String makeListPage(final MakeForm makeForm, final Model model) {
        LOG.info("ListMake List method of MakeController ");
        var makeAndModelVOs = makeService.listAllMakes();
        if (!makeAndModelVOs.isEmpty()) {
            makeAndModelVOs.forEach(makeAndModelVO -> LOG.debug(MAKE_AND_MODEL_VO_IS, makeAndModelVO));
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }
        var makeVOs = makeService.fetchMakes();
        if (!makeVOs.isEmpty()) {
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
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
        LOG.debug("DeleteMake method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.debug(MAKE_FORM_IS, sanitizedMakeForm);
        try {
            makeService.deleteMake(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Make");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            makeForm.setStatusMessage("Error occurred during deletion");
            makeForm.setStatusMessageType(DANGER);
            LOG.error(ex.getLocalizedMessage(), ex);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return makeListPage(makeForm, model);
    }

    /**
     * delete model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/deleteModel")
    @SuppressWarnings("unused")
    public String deleteModel(final MakeForm makeForm, final Model model) {
        LOG.debug("DeleteModel method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.debug(MAKE_FORM_IS, sanitizedMakeForm);
        try {
            makeService.deleteModel(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Model");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            makeForm.setStatusMessage("Error occurred during deletion");
            makeForm.setStatusMessageType(DANGER);
            LOG.error(ex.getLocalizedMessage(), ex);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return modelListPage(makeForm, model);
    }

    @GetMapping("/make/getForEdit")
    public @ResponseBody
    String getForEdit(@ModelAttribute("id") final String id,
                      final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        LOG.info("getForEdit method of make controller {}}", sanitizedId);
        var makeVO = makeService.getModelFromId(Long.valueOf(id));
        return makeVO.map(vo -> parseMakeAndModelVO(Map.of(vo.getMakeId(), vo.getModelName()))).orElse("");
    }

    @GetMapping("/make/makeForEdit")
    public @ResponseBody
    String makeForEdit(@ModelAttribute("id") final String id,
                       final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        LOG.info("makeForEdit method of make controller {}", sanitizedId);
        var makeVO = makeService.getMakeFromId(Long.valueOf(id));
        return makeVO.map(vo -> parseMakeVO(Map.of(vo.getMakeName(), vo.getDescription()))).orElse("");
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
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        var searchMakeVOs = makeService.fetchMakes();
        if (searchMakeVOs != null) {
            searchMakeVOs.forEach(searchMakeVO -> LOG.debug("SearchMakeVO is {}", searchMakeVO));
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
        var makeVOs = makeService.searchMake(makeForm.getSearchMakeAndModelVO());
        makeForm.setStatusMessage("Found " + makeVOs.size() + " Models");
        makeForm.setStatusMessageType("info");
        if (!makeVOs.isEmpty()) {
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        var searchMakeVOs = makeService.fetchMakes();
        if (searchMakeVOs != null) {
            searchMakeVOs.forEach(searchMakeVO -> LOG.debug("searchMakeVO is {}", searchMakeVO));
            makeForm.setMakeVOs(searchMakeVOs);
        }
        var userName = findLoggedInUsername();
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        model.addAttribute(MAKE_FORM, makeForm);
        return MAKE_LIST_PAGE;
    }

    private void loggingFromSearch(final MakeForm makeForm) {
        LOG.debug("SearchModel method of MakeController ");
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.debug("MakeForm instance to search {}", sanitizedMakeForm);
        if (makeForm.getSearchMakeAndModelVO() != null) {
            var sanitizedSearchModel = CommonUtils.sanitizedString(
                    makeForm.getSearchMakeAndModelVO().toString());
            LOG.debug("SearchVO instance to search {}", sanitizedSearchModel);
        }
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
    String saveMake(@ModelAttribute("selectMakeName") final String selectMakeName,
                    @ModelAttribute("selectMakeDesc") final String selectMakeDesc,
                    final BindingResult result) {
        LOG.info("SaveMake method of MakeController");
        var responseString = new StringBuilder();
        if (!result.hasErrors()) {
            var makeForm = new MakeForm();
            makeForm.setCurrentMakeAndModeVO(populateMakeVO(selectMakeName, selectMakeDesc));
            makeService.addNewMake(makeForm.getCurrentMakeAndModeVO());
            var makes = makeService.fetchMakes();
            responseString.append(fetchJsonMakeList(makes));
        } else {
            LOG.info("errors {}", result);
        }
        return responseString.toString();
    }

    private MakeAndModelVO populateMakeVO(final String selectMakeName, final String selectMakeDesc) {
        var sanitizedSelectMakeName = CommonUtils.sanitizedString(selectMakeName);
        var sanitizedSelectMakeDesc = CommonUtils.sanitizedString(selectMakeDesc);
        LOG.info("selectMakeName : {}", sanitizedSelectMakeName);
        LOG.info("selectMakeDesc : {}", sanitizedSelectMakeDesc);
        var userName = findLoggedInUsername();
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeName(selectMakeName);
        makeAndModelVO.setDescription(selectMakeDesc);
        makeAndModelVO.setCreatedBy(userName);
        makeAndModelVO.setModifiedBy(userName);
        return makeAndModelVO;
    }

    /**
     * saveModelAjax.
     *
     * @param selectMakeId    selectMakeId
     * @param selectModelName selectModelName
     * @param result          result
     * @return json string
     */
    @PostMapping("/make/saveModelAjax")
    public @ResponseBody
    String saveModelAjax(@ModelAttribute("selectMakeId") final Long selectMakeId,
                         @ModelAttribute("selectModelName") final String selectModelName,
                         final BindingResult result) {
        LOG.info("SaveModelAjax method of MakeController ");
        var responseString = new StringBuilder();
        if (!result.hasErrors()) {
            makeService.addNewModel(populateModelVO(selectMakeId, selectModelName));
            var makeAndModelVOs = makeService.listAllMakesAndModels();
            responseString.append(fetchJsonModelList(makeAndModelVOs));
        } else {
            LOG.info("errors {}", result);
        }
        return responseString.toString();
    }

    private MakeAndModelVO populateModelVO(final Long selectMakeId, final String selectModelName) {
        var sanitizedSelectMakeId = CommonUtils.sanitizedString(selectMakeId.toString());
        var sanitizedSelectModelName = CommonUtils.sanitizedString(selectModelName);
        LOG.info("selectMakeId : {}", sanitizedSelectMakeId);
        LOG.info("selectModelName : {}", sanitizedSelectModelName);
        var userName = findLoggedInUsername();
        var makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(selectMakeId);
        makeAndModelVO.setModelName(selectModelName);
        makeAndModelVO.setCreatedBy(userName);
        makeAndModelVO.setModifiedBy(userName);
        return makeAndModelVO;
    }

    @GetMapping("/make/getAllMakeIdsAndNames")
    public @ResponseBody
    String getAllMakeIdsAndNames() {
        LOG.info("GetAllMakeIdsAndNames method of MakeController ");
        return fetchJsonAllMakes(makeService.fetchMakes());
    }

    @PutMapping("/make/updateModelAjax")
    public @ResponseBody
    String updateModelAjax(@ModelAttribute("id") final Long id,
                           @ModelAttribute("modalMakeName") final Long makeId,
                           @ModelAttribute("modalModelName") final String modalModelName,
                           final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id.toString());
        var sanitizedMakeId = CommonUtils.sanitizedString(makeId.toString());
        var sanitizedModelName = CommonUtils.sanitizedString(modalModelName);
        LOG.info("UpdateModelAjax method of make controller with id {}, makeId {}, modalModelName {}",
                sanitizedId, sanitizedMakeId, sanitizedModelName);
        var responseString = new StringBuilder();
        makeService.updateModel(id, makeId, modalModelName);
        var makeAndModelVOs = makeService.listAllMakesAndModels();
        responseString.append(fetchJsonModelList(makeAndModelVOs));
        return responseString.toString();
    }

    @PutMapping("/make/updateMake")
    public @ResponseBody
    String updateMake(@ModelAttribute("id") final Long id,
                          @ModelAttribute("makeName") final String makeName,
                          @ModelAttribute("description") final String description,
                          final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id.toString());
        var sanitizedMakeName = CommonUtils.sanitizedString(makeName);
        var sanitizedDescription = CommonUtils.sanitizedString(description);
        LOG.info("updateMake method of make controller with id {}, makeName {}, description {}",
                sanitizedId, sanitizedMakeName, sanitizedDescription);
        var responseString = new StringBuilder();
        var makeModelVO = buildMakeModelVO(id, makeName, description);
        makeService.updateMake(makeModelVO);
        var makes = makeService.fetchMakes();
        return responseString.append(fetchJsonMakeList(makes)).toString();
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

    private String parseMakeAndModelVO(final Map<Long, String> modelEditMap) {
        String response;
        var mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(modelEditMap);
        } catch (IOException ex) {
            response = DANGER;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
    }

    private String parseMakeVO(final Map<String, String> modelEditMap) {
        String response;
        var mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(modelEditMap);
        } catch (IOException ex) {
            response = DANGER;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
    }

    private String fetchJsonMakeList(final List<MakeVO> makeVOS) {
        String response;
        var mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(makeVOS);
        } catch (IOException ex) {
            response = DANGER;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
    }

    private String fetchJsonModelList(final List<MakeAndModelVO> makeAndModelVOs) {
        String response;
        var mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(makeAndModelVOs);
        } catch (IOException ex) {
            response = DANGER;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
    }

    private String fetchJsonAllMakes(final List<MakeVO> makeVOS) {
        String response;
        var mapper = new ObjectMapper();
        Map<Long, String> idNameMap = makeVOS.stream()
                .collect(Collectors.toMap(MakeVO::getId, MakeVO::getMakeName, (a, b) -> b));
        try {
            response = mapper.writeValueAsString(idNameMap);
        } catch (IOException ex) {
            response = DANGER;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
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
