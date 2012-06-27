package com.poseidon.Make.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.web.form.MakeForm;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Date;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:24:14 PM
 */
public class MakeController extends MultiActionController {
    /**
     * MakeDelegate instance
     */
    private MakeDelegate makeDelegate;

    /**
     * logger for user controller
     */
    private final Log log = LogFactory.getLog(MakeController.class);

    public MakeDelegate getMakeDelegate() {
        return makeDelegate;
    }

    public void setMakeDelegate(MakeDelegate makeDelegate) {
        this.makeDelegate = makeDelegate;
    }

    public ModelAndView ModelList(HttpServletRequest request,
                             HttpServletResponse response, MakeForm makeForm) {
        log.info(" Inside List method of MakeController ");
        log.info(" form details are " + makeForm);

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
                log.info(" makeAndModelVO is " + makeAndModelVO);
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
                log.info(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", "makeForm", makeForm);
    }

    public ModelAndView MakeList(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake List method of MakeController ");
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
                log.info(" makeAndModelVO is " + makeAndModelVO);
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
                log.info(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeAndModelVO(new MakeAndModelVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/MakeList", "makeForm", makeForm);

    }

    public ModelAndView addMake(HttpServletRequest request,
                                HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake addMake method of MakeController ");
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return new ModelAndView("make/MakeAdd", "makeForm", makeForm);
    }

    public ModelAndView editMake(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake editMake method of MakeController ");
        log.info(" makeForm is " + makeForm.toString());
        log.info(" makeForm is " + makeForm.getCurrentMakeAndModeVO());
        MakeAndModelVO makeVO = null;
        try {
            makeVO = getMakeDelegate().getMakeFromId(makeForm.getId());
        } catch (MakeException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }

        if (makeVO == null) {
            log.error(" No details found for current makeVO !!");
        } else {
            log.info(" makeVO details are " + makeVO);
        }

        makeForm.setCurrentMakeAndModeVO(makeVO);

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/MakeEdit", "makeForm", makeForm);
    }

    public ModelAndView deleteMake(HttpServletRequest request,
                                   HttpServletResponse response, MakeForm makeForm) {
        log.info("  deleteMake method of MakeController ");
        log.info(" makeForm is " + makeForm.toString());
        try {
            getMakeDelegate().deleteMake(makeForm.getId());
        } catch (MakeException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return MakeList(request,response,makeForm);
    }

    public ModelAndView addModel(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info("  addModel method of MakeController ");
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
                log.info(" makeVO is " + makeVO);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        return new ModelAndView("make/ModelAdd", "makeForm", makeForm);
    }

    public ModelAndView editModel(HttpServletRequest request,
                                  HttpServletResponse response, MakeForm makeForm) {
        log.info(" editModel method of MakeController ");

        log.info(" makeForm is " + makeForm.toString());
        MakeAndModelVO makeVO = null;
        try {
            makeVO = getMakeDelegate().getModelFromId(makeForm.getId());
        } catch (MakeException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
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
                log.info(" makeVO is " + makeVONew);
            }
            makeForm.setMakeAndModelVOs(makeVOs);
        }
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/ModelEdit", "makeForm", makeForm);
    }

    public ModelAndView deleteModel(HttpServletRequest request,
                                    HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake deleteModel method of MakeController ");

        log.info(" makeForm is " + makeForm.toString());
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
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to delete the selected Model");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeAndModeVO(new MakeAndModelVO());
        return ModelList(request, response, makeForm);
    }

    public ModelAndView saveMake(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake saveMake method of MakeController ");
        log.info(" makeForm instance to add to database " + makeForm.toString());
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
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the Make Detail due to an error");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }
        return MakeList(request, response, makeForm);
    }

    public ModelAndView updateMake(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake saveMake method of MakeController ");
        log.info(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeAndModeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeAndModeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().updateMake(makeForm.getCurrentMakeAndModeVO());
            makeForm.setStatusMessage("Updated the Make succeessfully");
            makeForm.setStatusMessageType("success");
        } catch (MakeException e) {
            makeForm.setStatusMessage("Unable to update the selected Make due to a Data base error");
            makeForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(MakeException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Make");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }
        return MakeList(request, response, makeForm);

    }

    public ModelAndView updateModel(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" updateModel method of MakeController ");
        log.info(" makeForm instance to add to database " + makeForm.toString());
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
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to update the selected Model");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }
        return ModelList(request, response, makeForm);

    }

    public ModelAndView saveModel(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" saveModel method of MakeController ");
        log.info(" makeForm instance to add to database " + makeForm.toString());
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
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            makeForm.setStatusMessage("Unable to save the new Model");
            makeForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }
        return ModelList(request, response, makeForm);
    }

    public ModelAndView searchModel(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" searchModel method of MakeController ");
        log.info(" makeForm instance to search " + makeForm.toString());
        log.info(" searchVO instance to search " + makeForm.getSearchMakeAndModelVO());
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
                log.info(" makeVO is " + makeVO);
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
                log.info(" searchMakeVO is " + searchMakeVO);
            }
            makeForm.setMakeVOs(searchMakeVOs);
        }
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/ModelList", "makeForm", makeForm);
        
    }
}
