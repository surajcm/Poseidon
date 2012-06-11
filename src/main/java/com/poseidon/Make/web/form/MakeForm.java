package com.poseidon.Make.web.form;

import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:24:42 PM
 */
public class MakeForm {
    private List<MakeAndModelVO> makeAndModelVOs;
    private MakeAndModelVO currentMakeAndModeVO;
    private MakeAndModelVO searchMakeAndModelVO;
    private String LoggedInRole;
    private String LoggedInUser;
    private Long Id;
    private MakeVO makeVO;
    private List<MakeVO> makeVOs;
    private String statusMessage;
    
    public List<MakeAndModelVO> getMakeAndModelVOs() {
        return makeAndModelVOs;
    }

    public void setMakeAndModelVOs(List<MakeAndModelVO> makeAndModelVOs) {
        this.makeAndModelVOs = makeAndModelVOs;
    }

    public MakeAndModelVO getCurrentMakeAndModeVO() {
        return currentMakeAndModeVO;
    }

    public void setCurrentMakeAndModeVO(MakeAndModelVO currentMakeAndModeVO) {
        this.currentMakeAndModeVO = currentMakeAndModeVO;
    }

    public MakeAndModelVO getSearchMakeAndModelVO() {
        return searchMakeAndModelVO;
    }

    public void setSearchMakeAndModelVO(MakeAndModelVO searchMakeAndModelVO) {
        this.searchMakeAndModelVO = searchMakeAndModelVO;
    }

    public String getLoggedInRole() {
        return LoggedInRole;
    }

    public void setLoggedInRole(String loggedInRole) {
        LoggedInRole = loggedInRole;
    }

    public String getLoggedInUser() {
        return LoggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        LoggedInUser = loggedInUser;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public MakeVO getMakeVO() {
        return makeVO;
    }

    public void setMakeVO(MakeVO makeVO) {
        this.makeVO = makeVO;
    }

    public List<MakeVO> getMakeVOs() {
        return makeVOs;
    }

    public void setMakeVOs(List<MakeVO> makeVOs) {
        this.makeVOs = makeVOs;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "MakeForm{" +
                "makeAndModelVOs=" + makeAndModelVOs +
                ", currentMakeAndModeVO=" + currentMakeAndModeVO +
                ", searchMakeAndModelVO=" + searchMakeAndModelVO +
                ", LoggedInRole='" + LoggedInRole + '\'' +
                ", LoggedInUser='" + LoggedInUser + '\'' +
                ", Id=" + Id +
                ", makeVO=" + makeVO +
                ", makeVOs=" + makeVOs +
                '}';
    }
}
