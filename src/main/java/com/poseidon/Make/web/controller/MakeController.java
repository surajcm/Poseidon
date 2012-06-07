package com.poseidon.Make.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.web.form.MakeForm;
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

    public ModelAndView List(HttpServletRequest request,
                             HttpServletResponse response, MakeForm makeForm) {
        log.info(" Inside List method of MakeController ");
        log.info(" form details are " + makeForm);

        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().listAllMakesAndModels();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeVO(new MakeVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/MakeList", "makeForm", makeForm);
    }

    public ModelAndView listMake(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake List method of MakeController ");
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        makeForm.setSearchMakeVO(new MakeVO());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        return new ModelAndView("make/MakeSimpleList", "makeForm", makeForm);

    }

    public ModelAndView addMake(HttpServletRequest request,
                                HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake addMake method of MakeController ");
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeVO(new MakeVO());
        return new ModelAndView("make/MakeAdd", "makeForm", makeForm);
    }

    public ModelAndView editMake(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake editMake method of MakeController ");
        log.info(" makeForm is " + makeForm.toString());
        log.info(" makeForm is " + makeForm.getCurrentMakeVO());
        MakeVO makeVO = null;
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

        makeForm.setCurrentMakeVO(makeVO);

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
        makeForm.setCurrentMakeVO(new MakeVO());
        return listMake(request,response,makeForm);
    }

    public ModelAndView addModel(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info("  addModel method of MakeController ");
        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        makeForm.setCurrentMakeVO(new MakeVO());
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info(" makeVO is " + makeVO);
            }
            makeForm.setMakeVOs(makeVOs);
        }
        return new ModelAndView("make/ModelAdd", "makeForm", makeForm);
    }

    public ModelAndView editModel(HttpServletRequest request,
                                  HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake editModel method of MakeController ");

        log.info(" makeForm is " + makeForm.toString());
        MakeVO makeVO = null;
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

        makeForm.setCurrentMakeVO(makeVO);

        makeForm.setLoggedInUser(makeForm.getLoggedInUser());
        makeForm.setLoggedInRole(makeForm.getLoggedInRole());
        return new ModelAndView("make/ModelEditt", "makeForm", makeForm);
    }

    public ModelAndView deleteModel(HttpServletRequest request,
                                    HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake deleteModel method of MakeController ");

        log.info(" makeForm is " + makeForm.toString());
        try {
            getMakeDelegate().deleteModel(makeForm.getCurrentMakeVO().getModelId());
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
        makeForm.setCurrentMakeVO(new MakeVO());
        return List(request,response,makeForm);
    }

    public ModelAndView saveMake(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake saveMake method of MakeController ");
        log.info(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeVO().setCreatedDate(new Date());
        makeForm.getCurrentMakeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeVO().setCreatedBy(makeForm.getLoggedInUser());
        makeForm.getCurrentMakeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().addNewMake(makeForm.getCurrentMakeVO());
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
        return listMake(request, response, makeForm);
    }

    public ModelAndView updateMake(HttpServletRequest request,
                                 HttpServletResponse response, MakeForm makeForm) {
        log.info(" listMake saveMake method of MakeController ");
        log.info(" makeForm instance to add to database " + makeForm.toString());
        makeForm.getCurrentMakeVO().setModifiedDate(new Date());
        makeForm.getCurrentMakeVO().setModifiedBy(makeForm.getLoggedInUser());
        try {
            getMakeDelegate().updateMake(makeForm.getCurrentMakeVO());
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
        return listMake(request, response, makeForm);

    }
}
