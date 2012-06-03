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
}
