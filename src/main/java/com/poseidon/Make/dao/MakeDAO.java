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
    public List<MakeAndModelVO> listAllMakesAndModels() throws MakeException;

    public List<MakeAndModelVO> listAllMakes() throws MakeException;

    public void addNewMake(MakeAndModelVO currentMakeVO) throws MakeException;

    public MakeAndModelVO getMakeFromId(Long makeId) throws MakeException;

    public void deleteMake(Long makeId) throws MakeException;

    public MakeAndModelVO getModelFromId(Long modelId) throws MakeException;

    public void deleteModel(Long modelId) throws MakeException;

    public void updateMake(MakeAndModelVO currentMakeVO) throws MakeException;

    public void addNewModel(MakeAndModelVO currentMakeVO) throws MakeException;

    public void updateModel(MakeAndModelVO currentMakeVO) throws MakeException;

    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO) throws MakeException;

    public List<MakeVO> fetchMakes() throws MakeException;

    public List<MakeAndModelVO> getAllModelsFromMakeId(Long id) throws MakeException;
}
