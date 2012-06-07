package com.poseidon.Make.service;

import com.poseidon.Make.domain.MakeVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:24:58 PM
 */
public interface MakeService {

    public List<MakeVO> listAllMakesAndModels();

    public List<MakeVO> listAllMakes();

    public void addNewMake(MakeVO currentMakeVO);

    public MakeVO getMakeFromId(Long makeId);

    public void deleteMake(Long makeId);

    public MakeVO getModelFromId(Long modelId);

    public void deleteModel(Long modelId);

    public void updateMake(MakeVO currentMakeVO);

    public void addNewModel(MakeVO currentMakeVO);

    public void updateModel(MakeVO currentMakeVO);
}
