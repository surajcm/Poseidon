package com.poseidon.Make.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.web.form.MakeForm;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

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
    private final Log log = LogFactory.getLog(MakeController.class);

    public MakeDelegate getMakeDelegate() {
        return makeDelegate;
    }

    public void setMakeDelegate(MakeDelegate makeDelegate) {
        this.makeDelegate = makeDelegate;
    }

    @RequestMapping(value = "/make/ModelList.htm", method = RequestMethod.POST)
    public ModelAndView ModelList(MakeForm makeForm) {
        log.debug(" Inside List method of MakeController ");
        log.debug(" form details are " + makeForm);

        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = getMakeDelegate().listAllMakesAndModels();
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable to list the Models due to an error");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        if (makeAndModelVOs != null) {
            for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                log.debug(" makeAndModelVO is " + makeAndModelVO);
            }
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.debug(" makeVO is " + makeVO);
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
        log.debug(" listMake List method of MakeController ");
        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable to list the Makes due to an error");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        if (makeAndModelVOs != null) {
            for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                log.debug(" makeAndModelVO is " + makeAndModelVO);
            }
            makeForm.setMakeAndModelVOs(makeAndModelVOs);
        }

        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.debug(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/MakeList", "makeForm", makeForm);

    }

    @RequestMapping(value = "/make/addMake.htm", method = RequestMethod.POST)
    public ModelAndView addMake(MakeForm makeForm) {
        log.debug(" listMake addMake method of MakeController ");
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return new ModelAndView("make/MakeAdd", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/editMake.htm", method = RequestMethod.POST)
    public ModelAndView editMake(MakeForm makeForm) {
        log.debug(" listMake editMake method of MakeController ");
        log.debug(" makeForm is " + makeForm.toString());
        MakeAndModelVO makeVO = null;
        try {
            makeVO = getMakeDelegate().getMakeFromId(makeForm.getId());
        } catch (MakeException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            log.error(" An Unknown Error has been occurred !!");
        }

        if (makeVO == null) {
            log.error(" No details found for current makeVO !!");
        } else {
            log.debug(" makeVO details are " + makeVO);
        }

        makeForm.setCurrentMakeAndModeVO(makeVO);

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/MakeEdit", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/deleteMake.htm", method = RequestMethod.POST)
    public ModelAndView deleteMake(MakeForm makeForm) {
        log.debug("  deleteMake method of MakeController ");
        log.debug(" makeForm is " + makeForm.toString());
        try {
            getMakeDelegate().deleteMake(makeForm.getId());
        } catch (MakeException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            log.error(" An Unknown Error has been occurred !!");

        }

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return MakeList(makeForm);
    }

    @RequestMapping(value = "/make/addModel.htm", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView addModel( MakeForm makeForm) {
        log.debug("  addModel method of MakeController ");
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeAndModelVO makeVO : makeVOs) {
                log.debug(" makeVO is " + makeVO);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        return new ModelAndView("make/ModelAdd", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/editModel.htm", method = RequestMethod.POST)
    public ModelAndView editModel(MakeForm makeForm) {
        log.debug(" editModel method of MakeController ");

        log.debug(" makeForm is " + makeForm.toString());
        MakeAndModelVO makeVO = null;
        try {
            makeVO = getMakeDelegate().getModelFromId(makeForm.getId());
        } catch (MakeException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            log.error(" An Unknown Error has been occurred !!");
        }

        if (makeVO == null) {
            log.error(" No details found for current makeVO !!");
        } else {
            log.info(" makeVO details are " + makeVO);
        }

        makeForm.setCurrentMakeAndModeVO(makeVO);
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeAndModelVO makeVONew : makeVOs) {
                log.debug(" makeVO is " + makeVONew);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/ModelEdit", "makeForm", makeForm);
    }

    @RequestMapping(value = "/make/deleteModel.htm", method = RequestMethod.POST)
    public ModelAndView deleteModel(MakeForm makeForm) {
        log.debug(" listMake deleteModel method of MakeController ");

        log.debug(" makeForm is " + makeForm.toString());
        try {
            getMakeDelegate().deleteModel(makeForm.getId());
            makeForm.setStatusMessage("Successfully deleted the selected Model");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to delete the selected Model due to a Data base error");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to delete the selected Model");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.debug(" An Unknown Error has been occurred !!");

        }

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return ModelList(makeForm);
    }

    @RequestMapping(value = "/make/saveMake.htm", method = RequestMethod.POST)
    public ModelAndView saveMake(MakeForm makeForm) {
        log.debug(" listMake saveMake method of MakeController ");
        log.debug(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeAndModeVO().setCreatedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setCreatedBy(makeForm.getLoggedInUser());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().addNewMake(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Successfully saved the new Make Detail");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to save the Make Detail due to a data base error");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the Make Detail due to an error");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }
        return MakeList(makeForm);
    }

    @RequestMapping(value = "/make/updateMake.htm", method = RequestMethod.POST)
    public ModelAndView updateMake(MakeForm makeForm) {
        log.info(" updateMake method of MakeController ");
        log.info(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().updateMake(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the Make successfully");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to update the selected Make due to a Data base error");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Make");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.error(" An Unknown Error has been occurred !!");

        }
        return MakeList(makeForm);

    }

    @RequestMapping(value = "/make/updateModel.htm", method = RequestMethod.POST)
    public ModelAndView updateModel(MakeForm makeForm) {
        log.debug(" updateModel method of MakeController ");
        log.debug(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().updateModel(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the Model successfully");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to update the selected Model due to a Data base error");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Model");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.error(" An Unknown Error has been occurred !!");

        }
        return ModelList(makeForm);

    }

    @RequestMapping(value = "/make/saveModel.htm", method = RequestMethod.POST)
    public ModelAndView saveModel(MakeForm makeForm) {
        log.debug(" saveModel method of MakeController ");
        log.debug(" makeForm instance to add to database " + makeForm.toString());
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
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.error(" An error occurred while fetching data from database. !! ");
            } else {
                log.error(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the new Model");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.error(" An Unknown Error has been occurred !!");

        }
        return ModelList(makeForm);
    }

    @RequestMapping(value = "/make/searchModel.htm", method = RequestMethod.POST)
    public ModelAndView searchModel(MakeForm makeForm) {
        log.debug(" searchModel method of MakeController ");
        log.debug(" makeForm instance to search " + makeForm.toString());
        log.debug(" searchVO instance to search " + makeForm.getSearchMakeAndModelVO());
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().searchMakeVOs(makeForm.getSearchMakeAndModelVO());
            makeForm.setStatusMessage("Found "+ makeVOs.size() +" Models");
            makeForm.setStatusMessageType("info");
        } catch (Exception e) {
            makeForm.setStatusMessage("Unable get the Model");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeAndModelVO makeVO : makeVOs) {
                log.debug(" makeVO is " + makeVO);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        List<MakeVO> searchMakeVOs = null;
        try {
            searchMakeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO searchMakeVO : searchMakeVOs) {
                log.debug(" searchMakeVO is " + searchMakeVO);
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
