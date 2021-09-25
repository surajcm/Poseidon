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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    @RequestMapping(value = "/make/ModelList.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView modelList(final MakeForm makeForm) {
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.info("Inside List method of MakeController, form details are  {}",
                sanitizedMakeForm);
        var makeAndModelVOs = makeService.listAllMakesAndModels();
        if (!makeAndModelVOs.isEmpty()) {
            makeAndModelVOs.forEach(makeAndModelVO -> LOG.info(" makeAndModelVO is {}", makeAndModelVO));
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
        return new ModelAndView("make/ModelList", MAKE_FORM, makeForm);
    }

    /**
     * list out makes.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/MakeList.htm")
    public ModelAndView makeList(final MakeForm makeForm) {
        LOG.info(" listMake List method of MakeController ");
        var makeAndModelVOs = makeService.listAllMakes();
        if (!makeAndModelVOs.isEmpty()) {
            makeAndModelVOs.forEach(makeAndModelVO -> LOG.debug(" makeAndModelVO is {}", makeAndModelVO));
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
        return new ModelAndView("make/MakeList", MAKE_FORM, makeForm);
    }

    @GetMapping("/make/getForEdit.htm")
    public @ResponseBody
    String getForEdit(@ModelAttribute("id") final String id,
                      final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        LOG.info("getForEdit method of make controller {}}", sanitizedId);
        String response = null;
        var makeVO = makeService.getModelFromId(Long.valueOf(id));
        if (makeVO != null) {
            response = parseMakeAndModelVO(Map.of(makeVO.getMakeId(), makeVO.getModelName()));
        }
        return response;
    }


    @GetMapping("/make/makeForEdit.htm")
    public @ResponseBody
    String makeForEdit(@ModelAttribute("id") final String id,
                      final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id);
        LOG.info("makeForEdit method of make controller {}", sanitizedId);
        String response = null;
        var makeVO = makeService.getMakeFromId(Long.valueOf(id));
        if (makeVO != null) {
            response = parseMakeVO(Map.of(makeVO.getMakeName(), makeVO.getDescription()));
        }
        return response;
    }

    /**
     * delete a make.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/deleteMake.htm")
    @SuppressWarnings("unused")
    public ModelAndView deleteMake(final MakeForm makeForm) {
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
        return makeList(makeForm);
    }

    /**
     * delete model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/deleteModel.htm")
    @SuppressWarnings("unused")
    public ModelAndView deleteModel(final MakeForm makeForm) {
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
        return modelList(makeForm);
    }

    /**
     * update a make.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/updateMake.htm")
    public ModelAndView updateMake(final MakeForm makeForm) {
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.info("updateMake method of MakeController, makeForm instance to add to database  {}",
                sanitizedMakeForm);
        if (makeForm.getCurrentMakeAndModeVO() != null) {
            var userName = findLoggedInUsername();
            makeForm.getCurrentMakeAndModeVO().setModifiedBy(userName);
        }
        makeService.updateMake(makeForm.getCurrentMakeAndModeVO());
        makeForm.setStatusMessage("Updated the make successfully");
        makeForm.setStatusMessageType(SUCCESS);
        return makeList(makeForm);
    }

    /**
     * update a model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/updateModel.htm")
    public ModelAndView updateModel(final MakeForm makeForm) {
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.debug(" updateModel method of MakeController , makeForm instance to add to database {}",
                sanitizedMakeForm);
        if (makeForm.getCurrentMakeAndModeVO() != null) {
            var userName = findLoggedInUsername();
            makeForm.getCurrentMakeAndModeVO().setModifiedBy(userName);
        }
        makeService.updateModel(makeForm.getCurrentMakeAndModeVO());
        makeForm.setStatusMessage("Updated the Model successfully");
        makeForm.setStatusMessageType(SUCCESS);
        return modelList(makeForm);
    }

    /**
     * save a model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/saveModel.htm")
    public ModelAndView saveModel(final MakeForm makeForm) {
        var sanitizedMakeForm = CommonUtils.sanitizedString(makeForm.toString());
        LOG.info("SaveModel makeForm instance to add to database {}",
                sanitizedMakeForm);
        if (makeForm.getCurrentMakeAndModeVO() != null) {
            var userName = findLoggedInUsername();
            makeForm.getCurrentMakeAndModeVO().setCreatedBy(userName);
            makeForm.getCurrentMakeAndModeVO().setModifiedBy(userName);
        }
        makeService.addNewModel(makeForm.getCurrentMakeAndModeVO());
        makeForm.setStatusMessage("Saved the new Model successfully");
        makeForm.setStatusMessageType(SUCCESS);
        return modelList(makeForm);
    }

    /**
     * search for a model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/searchModel.htm")
    public ModelAndView searchModel(final MakeForm makeForm) {
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
            searchMakeVOs.forEach(searchMakeVO -> LOG.debug(" searchMakeVO is {}", searchMakeVO));
            makeForm.setMakeVOs(searchMakeVOs);
        }
        var userName = findLoggedInUsername();
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", MAKE_FORM, makeForm);
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
     * save make via ajax.
     *
     * @param selectMakeName selectMakeName
     * @param selectMakeDesc selectMakeDesc
     * @param result         BindingResult
     * @return as json
     */
    @PostMapping("/make/saveMakeAjax.htm")
    public @ResponseBody
    String saveMakeAjax(@ModelAttribute("selectMakeName") final String selectMakeName,
                        @ModelAttribute("selectMakeDesc") final String selectMakeDesc,
                        final BindingResult result) {
        LOG.info("SaveMakeAjax1 method of MakeController ");
        var responseString = new StringBuilder();
        if (!result.hasErrors()) {
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
            var makeForm = new MakeForm();
            makeForm.setCurrentMakeAndModeVO(makeAndModelVO);
            makeService.addNewMake(makeForm.getCurrentMakeAndModeVO());
            var makes = makeService.fetchMakes();
            responseString.append(fetchJsonMakeList(makes));
        } else {
            LOG.info("errors {}", result);
        }
        return responseString.toString();
    }

    /**
     * saveModelAjax.
     *
     * @param selectMakeId    selectMakeId
     * @param selectModelName selectModelName
     * @param result          result
     * @return json string
     */
    @PostMapping("/make/saveModelAjax.htm")
    public @ResponseBody
    String saveModelAjax(@ModelAttribute("selectMakeId") final Long selectMakeId,
                         @ModelAttribute("selectModelName") final String selectModelName,
                         final BindingResult result) {
        LOG.info("SaveModelAjax method of MakeController ");
        var responseString = new StringBuilder();
        if (!result.hasErrors()) {
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
            makeService.addNewModel(makeAndModelVO);
            var makeAndModelVOs = makeService.listAllMakesAndModels();
            responseString.append(fetchJsonModelList(makeAndModelVOs));
        } else {
            LOG.info("errors {}", result);
        }
        return responseString.toString();
    }

    @GetMapping("/make/getAllMakeIdsAndNames.htm")
    public @ResponseBody
    String getAllMakeIdsAndNames() {
        LOG.info("GetAllMakeIdsAndNames method of MakeController ");
        return fetchJsonAllMakes(makeService.fetchMakes());
    }

    @PutMapping("/make/updateModelAjax.htm")
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

    @PutMapping("/make/updateMakeAjax.htm")
    public @ResponseBody
    String updateMakeAjax(@ModelAttribute("id") final Long id,
                           @ModelAttribute("makeName") final String makeName,
                           @ModelAttribute("description") final String description,
                           final BindingResult result) {
        var sanitizedId = CommonUtils.sanitizedString(id.toString());
        var sanitizedMakeName = CommonUtils.sanitizedString(makeName);
        var sanitizedDescription = CommonUtils.sanitizedString(description);
        LOG.info("updateMakeAjax method of make controller with id {}, makeName {}, description {}",
                sanitizedId, sanitizedMakeName, sanitizedDescription);
        var responseString = new StringBuilder();
        var makeModelVO = buildMakeModelVO(id, makeName, description);
        makeService.updateMake(makeModelVO);
        var makes = makeService.fetchMakes();
        return responseString.append(fetchJsonMakeList(makes)).toString();
    }

    private MakeAndModelVO buildMakeModelVO(final Long id, final String makeName, final String description) {
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
        String username = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User user) {
                username = user.getUsername();
            }
        }
        return username;
    }

}
