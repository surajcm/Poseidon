package com.poseidon.Make.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.web.form.MakeForm;
import com.poseidon.Make.domain.MakeVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
}
