package com.poseidon.Make.dao;

import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

import java.util.List;

/**
 * User: Suraj
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
