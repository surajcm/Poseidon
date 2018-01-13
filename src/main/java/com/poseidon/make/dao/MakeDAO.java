package com.poseidon.make.dao;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.exception.MakeException;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 7:27:17 PM
 */
public interface MakeDAO {
    List<MakeAndModelVO> listAllMakesAndModels() throws MakeException;

    List<MakeAndModelVO> listAllMakes() throws MakeException;

    void addNewMake(MakeAndModelVO currentMakeVO) throws MakeException;

    MakeAndModelVO getMakeFromId(Long makeId) throws MakeException;

    void deleteMake(Long makeId) throws MakeException;

    MakeAndModelVO getModelFromId(Long modelId) throws MakeException;

    void deleteModel(Long modelId) throws MakeException;

    void updateMake(MakeAndModelVO currentMakeVO) throws MakeException;

    void addNewModel(MakeAndModelVO currentMakeVO) throws MakeException;

    void updateModel(MakeAndModelVO currentMakeVO) throws MakeException;

    List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO) throws MakeException;

    List<MakeVO> fetchMakes() throws MakeException;

    List<MakeAndModelVO> getAllModelsFromMakeId(Long id) throws MakeException;
}
