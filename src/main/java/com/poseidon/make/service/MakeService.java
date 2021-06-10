package com.poseidon.make.service;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.exception.MakeException;

import java.util.List;

/**
 * user: Suraj.
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

    void addNewModel(MakeAndModelVO currentMakeVO) throws MakeException;

    void updateModel(MakeAndModelVO currentMakeVO);

    void updateModel(Long id, Long makeId, String modalModelName);

    List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO);

    List<MakeVO> fetchMakes();

    List<MakeAndModelVO> getAllModelsFromMakeId(Long id);
}
