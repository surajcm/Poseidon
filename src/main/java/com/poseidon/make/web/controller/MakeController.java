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
import java.util.Date;
import java.util.List;

/**
 * user: Suraj
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
    private static final String UNKNOWN_ERROR_HAS_BEEN_OCCURRED = " An Unknown Error has been occurred !!";
    private static final String MAKE_FORM_IS = " makeForm is {}";
    private static final String MAKE_VO_IS = " makeVO is {}";

    @Autowired
    private MakeService makeService;

    @RequestMapping(value = "/make/ModelList.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView modelList(MakeForm makeForm) {
        LOG.info(" Inside List method of MakeController ");
        LOG.info(" form details are  {}", makeForm);

        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = makeService.listAllMakesAndModels();
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable to list the Models due to an error");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
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
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.debug(MAKE_VO_IS, makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", MAKE_FORM, makeForm);
    }

    @PostMapping(value = "/make/MakeList.htm")
    public ModelAndView makeList(MakeForm makeForm) {
        LOG.info(" listMake List method of MakeController ");
        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = makeService.listAllMakes();
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable to list the Makes due to an error");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
        }
        if (makeAndModelVOs != null) {
            makeAndModelVOs.forEach(makeAndModelVO -> LOG.debug(" makeAndModelVO is {}", makeAndModelVO));
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }

        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
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

    @PostMapping(value = "/make/editMake.htm")
    public ModelAndView editMake(MakeForm makeForm) {
        LOG.debug(" editMake method of MakeController ");
        LOG.debug(MAKE_FORM_IS, makeForm);
        MakeAndModelVO makeVO = null;
        try {
            makeVO = makeService.getMakeFromId(makeForm.getId());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
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

    @PostMapping(value = "/make/deleteMake.htm")
    @SuppressWarnings("unused")
    public ModelAndView deleteMake(MakeForm makeForm) {
        LOG.debug("  deleteMake method of MakeController ");
        LOG.debug(MAKE_FORM_IS, makeForm);
        try {
            makeService.deleteMake(makeForm.getId());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);

        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return makeList(makeForm);
    }

    @RequestMapping(value = "/make/addModel.htm", method = {RequestMethod.GET, RequestMethod.POST})
    @SuppressWarnings("unused")
    public ModelAndView addModel(MakeForm makeForm) {
        LOG.debug("  addModel method of MakeController ");
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeService.listAllMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        return new ModelAndView("make/ModelAdd", MAKE_FORM, makeForm);
    }

    @PostMapping(value = "/make/editModel.htm")
    @SuppressWarnings("unused")
    public ModelAndView editModel(MakeForm makeForm) {
        LOG.debug(" editModel method of MakeController ");

        LOG.debug(MAKE_FORM_IS, makeForm);
        MakeAndModelVO makeVO = null;
        try {
            makeVO = makeService.getModelFromId(makeForm.getId());

        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
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
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            makeVOs.forEach(makeVONew -> LOG.debug(MAKE_VO_IS, makeVONew));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/ModelEdit", MAKE_FORM, makeForm);
    }

    @PostMapping(value = "/make/deleteModel.htm")
    @SuppressWarnings("unused")
    public ModelAndView deleteModel(MakeForm makeForm) {
        LOG.debug(" listMake deleteModel method of MakeController ");
        LOG.debug(MAKE_FORM_IS, makeForm);
        try {
            makeService.deleteModel(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Model");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to delete the selected Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.debug(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return modelList(makeForm);
    }

    @PostMapping(value = "/make/saveMakeAjax.htm")
    public @ResponseBody
    String saveMakeAjax(@ModelAttribute(value = "selectMakeName") String selectMakeName,
                        @ModelAttribute(value = "selectMakeDesc") String selectMakeDesc,
                        BindingResult result) {
        LOG.info("saveMakeAjax1 method of MakeController ");
        StringBuilder responseString = new StringBuilder();
        if (!result.hasErrors()) {
            LOG.info("selectMakeName : {}", selectMakeName);
            LOG.info("selectMakeDesc : {}", selectMakeDesc);
            MakeForm makeForm = new MakeForm();
            // todo: how to get this ??
            //makeForm.getCurrentMakeAndModeVO().setCreatedBy(makeForm.getLoggedInUser());
            //makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setMakeName(selectMakeName);
            makeAndModelVO.setDescription(selectMakeDesc);
            makeAndModelVO.setCreatedDate(new Date());
            makeAndModelVO.setModifiedDate(new Date());
            makeAndModelVO.setCreatedBy("-ajax-");
            makeAndModelVO.setModifiedBy("-ajax-");

            makeForm.setCurrentMakeAndModeVO(makeAndModelVO);
            try {
                makeService.addNewMake(makeForm.getCurrentMakeAndModeVO());
                makeForm.setStatusMessage("Successfully saved the new make Detail");
                makeForm.setStatusMessageType(SUCCESS);
            } catch (Exception e1) {
                makeForm.setStatusMessage("Unable to save the make Detail due to an error");
                makeForm.setStatusMessageType(ERROR);
                LOG.error(e1.getLocalizedMessage());
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
            //get all the make and pass it as a json object
            List<MakeVO> makes = makeService.fetchMakes();
            responseString.append(fetchJSONMakeList(makes));

        } else {
            LOG.info("errors {}", result);
        }
        return responseString.toString();
    }

    private String fetchJSONMakeList(List<MakeVO> makeVOS) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(makeVOS);
        } catch (IOException e) {
            response = ERROR;
            LOG.error(e.getMessage());
        }
        LOG.info(response);
        return response;
    }

    @PostMapping(value = "/make/updateMake.htm")
    public ModelAndView updateMake(MakeForm makeForm) {
        LOG.info(" updateMake method of MakeController ");
        LOG.info(" makeForm instance to add to database  {}", makeForm);
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            makeService.updateMake(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the make successfully");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected make");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);

        }
        return makeList(makeForm);

    }

    @PostMapping(value = "/make/updateModel.htm")
    public ModelAndView updateModel(MakeForm makeForm) {
        LOG.debug(" updateModel method of MakeController ");
        LOG.debug(" makeForm instance to add to database {}", makeForm);
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            makeService.updateModel(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the Model successfully");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);

        }
        return modelList(makeForm);

    }

    @PostMapping(value = "/make/saveModel.htm")
    public ModelAndView saveModel(MakeForm makeForm) {
        LOG.info(" at saveModel makeForm instance to add to database {}", makeForm);
        makeForm.getCurrentMakeAndModeVO().setCreatedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setCreatedBy(makeForm.getLoggedInUser());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            makeService.addNewModel(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Saved the new Model successfully");
            makeForm.setStatusMessageType(SUCCESS);
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the new Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.error(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);

        }
        return modelList(makeForm);
    }

    @PostMapping(value = "/make/searchModel.htm")
    public ModelAndView searchModel(MakeForm makeForm) {
        LOG.debug(" searchModel method of MakeController ");
        LOG.debug(" makeForm instance to search {}", makeForm);
        LOG.debug(" searchVO instance to search {}", makeForm.getSearchMakeAndModelVO());
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = makeService.searchMakeVOs(makeForm.getSearchMakeAndModelVO());
            makeForm.setStatusMessage("Found " + makeVOs.size() + " Models");
            makeForm.setStatusMessageType("info");
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable get the Model");
            makeForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO_IS, makeVO));
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        List<MakeVO> searchMakeVOs = null;
        try {
            searchMakeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (searchMakeVOs != null) {
            searchMakeVOs.forEach(searchMakeVO -> LOG.debug(" searchMakeVO is {}", searchMakeVO));
            makeForm.setMakeVOs(searchMakeVOs);
        }
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", MAKE_FORM, makeForm);

    }

    @PostMapping(value = "/make/printMake.htm")
    public ModelAndView printMake(MakeForm makeForm) {
        return null;
    }
}
