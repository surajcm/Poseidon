package com.poseidon.Make.dao;

import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:27:17 PM
 */
public interface MakeDAO {
    public List<MakeVO> listAllMakesAndModels() throws MakeException;

    public List<MakeVO> listAllMakes() throws MakeException;

    public void addNewMake(MakeVO currentMakeVO) throws MakeException;

    public MakeVO getMakeFromId(Long makeId) throws MakeException;

    public void deleteMake(Long makeId) throws MakeException;

    public MakeVO getModelFromId(Long modelId) throws MakeException;

    public void deleteModel(Long modelId) throws MakeException;

    public void updateMake(MakeVO currentMakeVO) throws MakeException;
}
