package com.poseidon.Reports.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Reports.delegate.ReportsDelegate;
import com.poseidon.Reports.web.form.ReportsForm;
import com.poseidon.Reports.domain.ReportsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:47 AM
 */
public class ReportsController extends MultiActionController {
    private ReportsDelegate reportsDelegate;
    private final Log log = LogFactory.getLog(ReportsController.class);

    public ReportsDelegate getReportsDelegate() {
        return reportsDelegate;
    }

    public void setReportsDelegate(ReportsDelegate reportsDelegate) {
        this.reportsDelegate = reportsDelegate;
    }

    public ModelAndView List(HttpServletRequest request,
                             HttpServletResponse response, ReportsForm reportsForm) {
        log.info(" Inside List method of ReportsController ");
        log.info(" form details are" + reportsForm);

        List<ReportsVO> reportsVOs = null;
        try {
            reportsVOs = getReportsDelegate().generateDailyReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reportsVOs != null) {
            for (ReportsVO reportsVO : reportsVOs) {
                log.info(" reportsVO is " + reportsVO);
            }
            reportsForm.setReportsVOs(reportsVOs);
        }
        reportsForm.setSearchReports(new ReportsVO());
        reportsForm.setLoggedInRole(reportsForm.getLoggedInRole());
        reportsForm.setLoggedInUser(reportsForm.getLoggedInUser());
        return new ModelAndView("reports/List", "reportsForm", reportsForm);
    }
}
