package com.poseidon.Make.delegate;

import com.poseidon.Make.service.MakeService;
import com.poseidon.Make.domain.MakeVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:26:42 PM
 */
public class MakeDelegate {
    /**
     * user service instance
     */
    private MakeService makeService;

    public MakeService getMakeService() {
        return makeService;
    }

    public void setMakeService(MakeService makeService) {
        this.makeService = makeService;
    }

    public List<MakeVO> listAllMakesAndModels() {
        return makeService.listAllMakesAndModels();
    }
}
