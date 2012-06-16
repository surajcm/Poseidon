package com.poseidon.Make.delegate;

import com.poseidon.Make.service.MakeService;
import com.poseidon.Make.domain.MakeAndModelVO;
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

    public List<MakeAndModelVO> listAllMakesAndModels() {
        return makeService.listAllMakesAndModels();
    }

    public List<MakeAndModelVO> listAllMakes() {
        return makeService.listAllMakes();
    }

    public void addNewMake(MakeAndModelVO currentMakeVO) throws MakeException {
         makeService.addNewMake(currentMakeVO);
    }

    public MakeAndModelVO getMakeFromId(Long makeId) throws MakeException{
        return makeService.getMakeFromId(makeId);
    }

    public void deleteMake(Long makeId) throws MakeException {
         makeService.deleteMake(makeId);
    }

    public MakeAndModelVO getModelFromId(Long modelId)throws MakeException {
        return makeService.getModelFromId(modelId);
    }

    public void deleteModel(Long modelId)throws MakeException {
        makeService.deleteModel(modelId);
    }

    public void updateMake(MakeAndModelVO currentMakeVO)throws MakeException {
        makeService.updateMake(currentMakeVO);
    }

    public void addNewModel(MakeAndModelVO currentMakeVO) throws MakeException {
        makeService.addNewModel(currentMakeVO);
    }

    public void updateModel(MakeAndModelVO currentMakeVO)  throws MakeException {
        makeService.updateModel(currentMakeVO);
    }

    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO) throws MakeException {
        return makeService.searchMakeVOs(searchMakeVO);
    }

    public List<MakeVO> fetchMakes() {
        return makeService.fetchMakes();
    }

    public List<MakeAndModelVO> getAllModelsFromMakeId(Long id) throws MakeException {
        return makeService.getAllModelsFromMakeId(id);
    }
}
