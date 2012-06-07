package com.poseidon.Make.delegate;

import com.poseidon.Make.service.MakeService;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

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

    public List<MakeVO> listAllMakes() {
        return makeService.listAllMakes();
    }

    public void addNewMake(MakeVO currentMakeVO) throws MakeException {
         makeService.addNewMake(currentMakeVO);
    }

    public MakeVO getMakeFromId(Long makeId) throws MakeException{
        return makeService.getMakeFromId(makeId);
    }

    public void deleteMake(Long makeId) throws MakeException {
         makeService.deleteMake(makeId);
    }

    public MakeVO getModelFromId(Long modelId)throws MakeException {
        return makeService.getModelFromId(modelId);
    }

    public void deleteModel(Long modelId)throws MakeException {
        makeService.deleteModel(modelId);
    }

    public void updateMake(MakeVO currentMakeVO)throws MakeException {
        makeService.updateMake(currentMakeVO);
    }
}
