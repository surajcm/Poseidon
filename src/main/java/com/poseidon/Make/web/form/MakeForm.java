package com.poseidon.Make.web.form;

import com.poseidon.Make.domain.MakeVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:24:42 PM
 */
public class MakeForm {
    private List<MakeVO> makeVOs;
    private MakeVO currentMakeVO;
    private MakeVO searchMakeVO;
    private String LoggedInRole;
    private String LoggedInUser;
    private Long Id;
    public List<MakeVO> getMakeVOs() {
        return makeVOs;
    }

    public void setMakeVOs(List<MakeVO> makeVOs) {
        this.makeVOs = makeVOs;
    }

    public MakeVO getCurrentMakeVO() {
        return currentMakeVO;
    }

    public void setCurrentMakeVO(MakeVO currentMakeVO) {
        this.currentMakeVO = currentMakeVO;
    }

    public MakeVO getSearchMakeVO() {
        return searchMakeVO;
    }

    public void setSearchMakeVO(MakeVO searchMakeVO) {
        this.searchMakeVO = searchMakeVO;
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

    @Override
    public String toString() {
        return "MakeForm{" +
                "makeVOs=" + makeVOs +
                ", currentMakeVO=" + currentMakeVO +
                ", searchMakeVO=" + searchMakeVO +
                ", LoggedInRole='" + LoggedInRole + '\'' +
                ", LoggedInUser='" + LoggedInUser + '\'' +
                ", Id=" + Id +
                '}';
    }
}
