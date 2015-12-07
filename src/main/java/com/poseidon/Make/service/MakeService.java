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

    List<MakeAndModelVO> listAllMakesAndModels();

    List<MakeAndModelVO> listAllMakes();

    void addNewMake(MakeAndModelVO currentMakeVO);

    MakeAndModelVO getMakeFromId(Long makeId);

    void deleteMake(Long makeId);

    MakeAndModelVO getModelFromId(Long modelId);

    void deleteModel(Long modelId);

    void updateMake(MakeAndModelVO currentMakeVO);

    void addNewModel(MakeAndModelVO currentMakeVO);

    void updateModel(MakeAndModelVO currentMakeVO);

    List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO);

    List<MakeVO> fetchMakes();

    List<MakeAndModelVO> getAllModelsFromMakeId(Long id);
}
