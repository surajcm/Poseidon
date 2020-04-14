package com.poseidon.make.web.form;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;

import java.util.List;
import java.util.StringJoiner;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:24:42 PM
 */
public class MakeForm {
    private List<MakeAndModelVO> makeAndModelVOs;
    private MakeAndModelVO currentMakeAndModeVO;
    private MakeAndModelVO searchMakeAndModelVO;
    private String loggedInRole;
    private String loggedInUser;
    private Long id;
    private MakeVO makeVO;
    private List<MakeVO> makeVOs;
    private String statusMessage;
    private String statusMessageType;

    public List<MakeAndModelVO> getMakeAndModelVOs() {
        return makeAndModelVOs;
    }

    public void setMakeAndModelVOs(final List<MakeAndModelVO> makeAndModelVOs) {
        this.makeAndModelVOs = makeAndModelVOs;
    }

    public MakeAndModelVO getCurrentMakeAndModeVO() {
        return currentMakeAndModeVO;
    }

    public void setCurrentMakeAndModeVO(final MakeAndModelVO currentMakeAndModeVO) {
        this.currentMakeAndModeVO = currentMakeAndModeVO;
    }

    public MakeAndModelVO getSearchMakeAndModelVO() {
        return searchMakeAndModelVO;
    }

    public void setSearchMakeAndModelVO(final MakeAndModelVO searchMakeAndModelVO) {
        this.searchMakeAndModelVO = searchMakeAndModelVO;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(final String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(final String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public MakeVO getMakeVO() {
        return makeVO;
    }

    public void setMakeVO(final MakeVO makeVO) {
        this.makeVO = makeVO;
    }

    public List<MakeVO> getMakeVOs() {
        return makeVOs;
    }

    public void setMakeVOs(final List<MakeVO> makeVOs) {
        this.makeVOs = makeVOs;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessageType() {
        return statusMessageType;
    }

    public void setStatusMessageType(final String statusMessageType) {
        this.statusMessageType = statusMessageType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MakeForm.class.getSimpleName() + "[", "]")
                .add("makeAndModelVOs=" + makeAndModelVOs)
                .add("currentMakeAndModeVO=" + currentMakeAndModeVO)
                .add("searchMakeAndModelVO=" + searchMakeAndModelVO)
                .add("loggedInRole='" + loggedInRole + "'")
                .add("loggedInUser='" + loggedInUser + "'")
                .add("id=" + id)
                .add("makeVO=" + makeVO)
                .add("makeVOs=" + makeVOs)
                .add("statusMessage='" + statusMessage + "'")
                .add("statusMessageType='" + statusMessageType + "'")
                .toString();
    }
}
