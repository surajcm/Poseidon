package com.poseidon.Make.service;

import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:24:58 PM
 */
public interface MakeService {

    public List<MakeAndModelVO> listAllMakesAndModels();

    public List<MakeAndModelVO> listAllMakes();

    public void addNewMake(MakeAndModelVO currentMakeVO);

    public MakeAndModelVO getMakeFromId(Long makeId);

    public void deleteMake(Long makeId);

    public MakeAndModelVO getModelFromId(Long modelId);

    public void deleteModel(Long modelId);

    public void updateMake(MakeAndModelVO currentMakeVO);

    public void addNewModel(MakeAndModelVO currentMakeVO);

    public void updateModel(MakeAndModelVO currentMakeVO);

    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO);

    public List<MakeVO> fetchMakes();

    public List<MakeAndModelVO> getAllModelsFromMakeId(Long id);
}
