package com.poseidon.Make.web.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.web.form.MakeForm;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Date;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:24:14 PM
 */
@Controller
public class MakeController {
    /**
     * MakeDelegate instance
     */
    private MakeDelegate makeDelegate;

    /**
     * logger for MakeController
     */
    private final Logger LOG = LoggerFactory.getLogger(MakeController.class);

    public MakeDelegate getMakeDelegate() {
        return makeDelegate;
    }

    public void setMakeDelegate(MakeDelegate makeDelegate) {
        this.makeDelegate = makeDelegate;
    }

    @RequestMapping(value = "/make/ModelList.htm", method = RequestMethod.POST)
    public ModelAndView ModelList(MakeForm makeForm) {
        LOG.debug(" Inside List method of MakeController ");
        LOG.debug(" form details are  " + makeForm);

        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = getMakeDelegate().listAllMakesAndModels();
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable to list the Models due to an error");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        if (makeAndModelVOs != null) {
            for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                LOG.debug(" makeAndModelVO is " + makeAndModelVO);
            }
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.debug(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/MakeList.htm", method = RequestMethod.POST)
    public ModelAndView MakeList(MakeForm makeForm) {
        LOG.debug(" listMake List method of MakeController ");
        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable to list the Makes due to an error");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        if (makeAndModelVOs != null) {
            for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                LOG.debug(" makeAndModelVO is " + makeAndModelVO);
            }
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }

        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.debug(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/MakeList", "makeForm", makeForm);

    }

    @RequestMapping(value = "/make/editMake.htm", method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ModelAndView editMake(MakeForm makeForm) {
        LOG.debug(" editMake method of MakeController ");
        LOG.debug(" makeForm is " + makeForm.toString());
        MakeAndModelVO makeVO = null;
        try {
            makeVO = getMakeDelegate().getMakeFromId(makeForm.getId());
        } catch (MakeException e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(" An Unknown Error has been occurred !!");
        }

        if (makeVO == null) {
            LOG.error(" No details found for current makeVO !!");
        } else {
            LOG.debug(" makeVO details are " + makeVO);
        }

        makeForm.setCurrentMakeAndModeVO(makeVO);

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/MakeEdit", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/deleteMake.htm", method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ModelAndView deleteMake(MakeForm makeForm) {
        LOG.debug("  deleteMake method of MakeController ");
        LOG.debug(" makeForm is " + makeForm.toString());
        try {
            getMakeDelegate().deleteMake(makeForm.getId());
        } catch (MakeException e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(" An Unknown Error has been occurred !!");

        }

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return MakeList(makeForm);
    }

    @RequestMapping(value = "/make/addModel.htm", method = { RequestMethod.GET, RequestMethod.POST })
    @SuppressWarnings("unused")
    public ModelAndView addModel( MakeForm makeForm) {
        LOG.debug("  addModel method of MakeController ");
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeAndModelVO makeVO : makeVOs) {
                LOG.debug(" makeVO is " + makeVO);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        return new ModelAndView("make/ModelAdd", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/editModel.htm", method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ModelAndView editModel(MakeForm makeForm) {
        LOG.debug(" editModel method of MakeController ");

        LOG.debug(" makeForm is " + makeForm.toString());
        MakeAndModelVO makeVO = null;
        try {
            makeVO = getMakeDelegate().getModelFromId(makeForm.getId());
        } catch (MakeException e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.error(" An Unknown Error has been occurred !!");
        }

        if (makeVO == null) {
            LOG.error(" No details found for current makeVO !!");
        } else {
            LOG.info(" makeVO details are " + makeVO);
        }

        makeForm.setCurrentMakeAndModeVO(makeVO);
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeAndModelVO makeVONew : makeVOs) {
                LOG.debug(" makeVO is " + makeVONew);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/ModelEdit", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/deleteModel.htm", method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ModelAndView deleteModel(MakeForm makeForm) {
        LOG.debug(" listMake deleteModel method of MakeController ");

        LOG.debug(" makeForm is " + makeForm.toString());
        try {
            getMakeDelegate().deleteModel(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Model");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to delete the selected Model due to a Data base error");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to delete the selected Model");
            makeForm.setStatusMessageType("error");
            LOG.error(e1.getLocalizedMessage());
            LOG.debug(" An Unknown Error has been occurred !!");

        }

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return ModelList(makeForm);
    }

    @RequestMapping(value = "/make/saveMakeAjax.htm", method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public void saveMakeAjax(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse) {
        LOG.debug("saveMakeAjax method of MakeController ");

        StringBuilder responseString = new StringBuilder();

        String selectMakeName = httpServletRequest.getParameter("selectMakeName");
        String selectMakeDesc = httpServletRequest.getParameter("selectMakeDesc");
        //get all the models for this make id
        LOG.info(" At saveMakeAjax, selectMakeName is : s selectMakeDesc : s"+ selectMakeName+ selectMakeDesc);
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
            getMakeDelegate().addNewMake(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Successfully saved the new Make Detail");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to save the Make Detail due to a data base error");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the Make Detail due to an error");
            makeForm.setStatusMessageType("error");
            LOG.error(e1.getLocalizedMessage());
            LOG.info(" An Unknown Error has been occurred !!");
        }

        //get all the make and pass it as a json object
        List<MakeVO> makes = getMakeDelegate().fetchMakes();
        responseString.append(fetchJSONMakeList(makes));
        // get a id-name combination, which is splittable by js
        httpServletResponse.setContentType("text/plain");
        PrintWriter out;
        try {
            out = httpServletResponse.getWriter();
            out.print(responseString.toString());
            out.flush();
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    private String fetchJSONMakeList(List<MakeVO> makeVOS) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(makeVOS);
        } catch (IOException e) {
            response = "error";
            LOG.error(e.getMessage());
        }
        LOG.info(response);
        return response;
    }

    @RequestMapping(value = "/make/updateMake.htm", method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ModelAndView updateMake(MakeForm makeForm) {
        LOG.info(" updateMake method of MakeController ");
        LOG.info(" makeForm instance to add to database  " + makeForm);
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().updateMake(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the Make successfully");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to update the selected Make due to a Data base error");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Make");
            makeForm.setStatusMessageType("error");
            LOG.error(e1.getLocalizedMessage());
            LOG.error(" An Unknown Error has been occurred !!");

        }
        return MakeList(makeForm);

    }

    @RequestMapping(value = "/make/updateModel.htm", method = RequestMethod.POST)
    public ModelAndView updateModel(MakeForm makeForm) {
        LOG.debug(" updateModel method of MakeController ");
        LOG.debug(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().updateModel(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the Model successfully");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to update the selected Model due to a Data base error");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Model");
            makeForm.setStatusMessageType("error");
            LOG.error(e1.getLocalizedMessage());
            LOG.error(" An Unknown Error has been occurred !!");

        }
        return ModelList(makeForm);

    }

    @RequestMapping(value = "/make/saveModel.htm", method = RequestMethod.POST)
    public ModelAndView saveModel(MakeForm makeForm) {
        LOG.debug(" saveModel method of MakeController ");
        LOG.debug(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeAndModeVO().setCreatedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setCreatedBy(makeForm.getLoggedInUser());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().addNewModel(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Saved the new Model successfully");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to save the new Model due to a data base error");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                LOG.error(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the new Model");
            makeForm.setStatusMessageType("error");
            LOG.error(e1.getLocalizedMessage());
            LOG.error(" An Unknown Error has been occurred !!");

        }
        return ModelList(makeForm);
    }

    @RequestMapping(value = "/make/searchModel.htm", method = RequestMethod.POST)
    public ModelAndView searchModel(MakeForm makeForm) {
        LOG.debug(" searchModel method of MakeController ");
        LOG.debug(" makeForm instance to search " + makeForm.toString());
        LOG.debug(" searchVO instance to search " + makeForm.getSearchMakeAndModelVO());
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().searchMakeVOs(makeForm.getSearchMakeAndModelVO());
            makeForm.setStatusMessage("Found "+ makeVOs.size() +" Models");
            makeForm.setStatusMessageType("info");
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable get the Model");
            makeForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeAndModelVO makeVO : makeVOs) {
                LOG.debug(" makeVO is " + makeVO);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        List<MakeVO> searchMakeVOs = null;
        try {
            searchMakeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO searchMakeVO : searchMakeVOs) {
                LOG.debug(" searchMakeVO is " + searchMakeVO);
            }
            makeForm.setMakeVOs(searchMakeVOs);
        }
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", "makeForm", makeForm);
        
    }

    @RequestMapping(value = "/make/printMake.htm", method = RequestMethod.POST)
    public  ModelAndView printMake(MakeForm makeForm){
        return null;
    }
}
