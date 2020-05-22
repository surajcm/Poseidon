package com.poseidon.make.web.controller;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.make.web.form.MakeForm;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:24:14 PM
 */
@Controller
@SuppressWarnings("unused")
public class MakeController {
    private static final Logger LOG = LoggerFactory.getLogger(MakeController.class);
    private static final String ERROR = "error";
    private static final String MAKE_FORM = "makeForm";
    private static final String SUCCESS = "success";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String MAKE_FORM_IS = " makeForm is {}";
    private static final String MAKE_VO_IS = " makeVO is {}";

    @Autowired
    private MakeService makeService;

    /**
     * .
     * list all models
     *
     * @param makeForm makeForm
     * @return view
     */
    @RequestMapping(value = "/make/ModelList.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView modelList(final MakeForm makeForm) {
        LOG.info(" Inside List method of MakeController, form details are  {}", makeForm);

        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = makeService.listAllMakesAndModels();
        } catch (Exception ex) {
            makeForm.setStatusMessage("Unable to list the Models due to an error");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        if (makeAndModelVOs != null) {
            for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                LOG.debug(" makeAndModelVO is {}", makeAndModelVO);
            }
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (makeVOs != null) {
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
        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = makeService.listAllMakes();
        } catch (Exception ex) {
            makeForm.setStatusMessage("Unable to list the Makes due to an error");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        if (makeAndModelVOs != null) {
            makeAndModelVOs.forEach(makeAndModelVO -> LOG.debug(" makeAndModelVO is {}", makeAndModelVO));
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }

        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (makeVOs != null) {
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/MakeList", MAKE_FORM, makeForm);

    }

    /**
     * edit a make.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/editMake.htm")
    public ModelAndView editMake(final MakeForm makeForm) {
        LOG.debug(" editMake method of MakeController ");
        LOG.debug(MAKE_FORM_IS, makeForm);
        MakeAndModelVO makeVO = null;
        try {
            makeVO = makeService.getMakeFromId(makeForm.getId());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR);
        }

        if (makeVO == null) {
            LOG.error(" No details found for current makeVO !!");
        } else {
            LOG.debug(" makeVO details are {}", makeVO);
        }
        makeForm.setCurrentMakeAndModeVO(makeVO);
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/MakeEdit", MAKE_FORM, makeForm);
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
        LOG.debug("deleteMake method of MakeController ");
        LOG.debug(MAKE_FORM_IS, makeForm);
        try {
            makeService.deleteMake(makeForm.getId());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return makeList(makeForm);
    }

    /**
     * edit model.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/editModel.htm")
    @SuppressWarnings("unused")
    public ModelAndView editModel(final MakeForm makeForm) {
        LOG.debug(" editModel method of MakeController ");
        LOG.debug(MAKE_FORM_IS, makeForm);
        MakeAndModelVO makeVO = null;
        try {
            makeVO = makeService.getModelFromId(makeForm.getId());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR);
        }
        if (makeVO == null) {
            LOG.error(" No details found for current makeVO !!");
        } else {
            LOG.info(" makeVO details are {}", makeVO);
        }

        makeForm.setCurrentMakeAndModeVO(makeVO);
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeService.listAllMakes();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (makeVOs != null) {
            makeVOs.forEach(makeVONew -> LOG.debug(MAKE_VO_IS, makeVONew));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/ModelEdit", MAKE_FORM, makeForm);
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
        LOG.debug("deleteModel method of MakeController ");
        LOG.debug(MAKE_FORM_IS, makeForm);
        try {
            makeService.deleteModel(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Model");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to delete the selected Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.debug(UNKNOWN_ERROR);
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
        LOG.info("updateMake method of MakeController, makeForm instance to add to database  {}", makeForm);
        if (makeForm.getCurrentMakeAndModeVO() != null) {
            makeForm.getCurrentMakeAndModeVO().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        }
        try {
            makeService.updateMake(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the make successfully");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected make");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR);
        }
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
        LOG.debug(" updateModel method of MakeController , makeForm instance to add to database {}", makeForm);
        if (makeForm.getCurrentMakeAndModeVO() != null) {
            makeForm.getCurrentMakeAndModeVO().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        }
        try {
            makeService.updateModel(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the Model successfully");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR);
        }
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
        LOG.info(" at saveModel makeForm instance to add to database {}", makeForm);
        if (makeForm.getCurrentMakeAndModeVO() != null) {
            makeForm.getCurrentMakeAndModeVO().setCreatedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeForm.getCurrentMakeAndModeVO().setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeForm.getCurrentMakeAndModeVO().setCreatedBy(makeForm.getLoggedInUser());
            makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        }
        try {
            makeService.addNewModel(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Saved the new Model successfully");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the new Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR);
        }
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
        LOG.debug(" searchModel method of MakeController ");
        LOG.debug(" makeForm instance to search {}", makeForm);
        LOG.debug(" searchVO instance to search {}", makeForm.getSearchMakeAndModelVO());
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeService.searchMakeVOs(makeForm.getSearchMakeAndModelVO());
            makeForm.setStatusMessage("Found " + makeVOs.size() + " Models");
            makeForm.setStatusMessageType("info");
        } catch (Exception ex) {
            makeForm.setStatusMessage("Unable get the Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        if (makeVOs != null) {
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        List<MakeVO> searchMakeVOs = null;
        try {
            searchMakeVOs = makeService.fetchMakes();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (searchMakeVOs != null) {
            searchMakeVOs.forEach(searchMakeVO -> LOG.debug(" searchMakeVO is {}", searchMakeVO));
            makeForm.setMakeVOs(searchMakeVOs);
        }
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", MAKE_FORM, makeForm);
    }

    /**
     * print make.
     *
     * @param makeForm makeForm
     * @return view
     */
    @PostMapping("/make/printMake.htm")
    public ModelAndView printMake(final MakeForm makeForm) {
        return null;
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
        LOG.info("saveMakeAjax1 method of MakeController ");
        StringBuilder responseString = new StringBuilder();
        if (!result.hasErrors()) {
            LOG.info("selectMakeName : {}", selectMakeName);
            LOG.info("selectMakeDesc : {}", selectMakeDesc);
            // todo: how to get this ??
            //makeForm.getCurrentMakeAndModeVO().setCreatedBy(makeForm.getLoggedInUser());
            //makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setMakeName(selectMakeName);
            makeAndModelVO.setDescription(selectMakeDesc);
            makeAndModelVO.setCreatedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeAndModelVO.setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeAndModelVO.setCreatedBy("-ajax-");
            makeAndModelVO.setModifiedBy("-ajax-");

            MakeForm makeForm = new MakeForm();
            makeForm.setCurrentMakeAndModeVO(makeAndModelVO);
            try {
                makeService.addNewMake(makeForm.getCurrentMakeAndModeVO());
                makeForm.setStatusMessage("Successfully saved the new make Detail");
                makeForm.setStatusMessageType(SUCCESS);
            } catch (Exception e1) {
                makeForm.setStatusMessage("Unable to save the make Detail due to an error");
                makeForm.setStatusMessageType(ERROR);
                LOG.error(e1.getLocalizedMessage());
                LOG.info(UNKNOWN_ERROR);
            }
            try {
                //get all the make and pass it as a json object
                List<MakeVO> makes = makeService.fetchMakes();
                responseString.append(fetchJsonMakeList(makes));
            } catch (Exception e1) {
                LOG.error(e1.getMessage());
            }

        } else {
            LOG.info("errors {}", result);
        }
        return responseString.toString();
    }

    private String fetchJsonMakeList(final List<MakeVO> makeVOS) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(makeVOS);
        } catch (IOException ex) {
            response = ERROR;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
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
        LOG.info("saveModelAjax method of MakeController ");
        StringBuilder responseString = new StringBuilder();
        if (!result.hasErrors()) {
            LOG.info("selectMakeId : {}", selectMakeId);
            LOG.info("selectModelName : {}", selectModelName);
            // todo: how to get this ??
            //makeForm.getCurrentMakeAndModeVO().setCreatedBy(makeForm.getLoggedInUser());
            //makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setMakeId(selectMakeId);
            makeAndModelVO.setModelName(selectModelName);
            makeAndModelVO.setCreatedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeAndModelVO.setModifiedDate(OffsetDateTime.now(ZoneId.systemDefault()));
            makeAndModelVO.setCreatedBy("-ajax-");
            makeAndModelVO.setModifiedBy("-ajax-");
            MakeForm makeForm = new MakeForm();
            makeForm.setCurrentMakeAndModeVO(makeAndModelVO);
            try {
                makeService.addNewModel(makeForm.getCurrentMakeAndModeVO());
                makeForm.setStatusMessage("Successfully saved the new make Detail");
                makeForm.setStatusMessageType(SUCCESS);
            } catch (Exception e1) {
                makeForm.setStatusMessage("Unable to save the make Detail due to an error");
                makeForm.setStatusMessageType(ERROR);
                LOG.error(e1.getLocalizedMessage());
                LOG.info(UNKNOWN_ERROR);
            }
            try {
                //get all the make and pass it as a json object
                List<MakeAndModelVO> makeAndModelVOs = makeService.listAllMakesAndModels();
                responseString.append(fetchJsonModelList(makeAndModelVOs));
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
            }

        } else {
            LOG.info("errors {}", result);
        }
        return responseString.toString();
    }

    private String fetchJsonModelList(final List<MakeAndModelVO> makeAndModelVOs) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(makeAndModelVOs);
        } catch (IOException ex) {
            response = ERROR;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
    }
}
