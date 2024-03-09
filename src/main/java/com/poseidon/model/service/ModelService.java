package com.poseidon.model.service;

import com.poseidon.make.dao.MakeDao;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
public class ModelService {
    private final MakeDao makeDAO;

    public ModelService(final MakeDao makeDAO) {
        this.makeDAO = makeDAO;
    }
}
