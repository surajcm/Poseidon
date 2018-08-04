package com.poseidon.make.dao.impl;

import com.poseidon.make.dao.MakeDAO;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.mapper.MakeAndModelEntityConverter;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.exception.MakeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 7:28:10 PM
 */
@Repository
@SuppressWarnings("unused")
public class MakeDAOImpl implements MakeDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MakeDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MakeRepository makeRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private MakeAndModelEntityConverter makeAndModelEntityConverter;

    public List<MakeAndModelVO> listAllMakesAndModels() throws MakeException {
        List<MakeAndModelVO> makeAndModelVOS;
        try {
            List<Model> models = modelRepository.findAll();
            //todo: better MakeAndModelVO to render things in a better way
            makeAndModelVOS = makeAndModelEntityConverter.convertModelsToMakeAndModelVOs(models);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeAndModelVOS;
    }

    public List<MakeAndModelVO> listAllMakes() throws MakeException {
        List<MakeAndModelVO> makeVOs;
        try {
            List<Make> makes = makeRepository.findAll();
            makeVOs = makeAndModelEntityConverter.convertMakeToMakeAndModelVOs(makes);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public void addNewMake(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            Make make = makeAndModelEntityConverter.convertToMake(currentMakeVO);
            makeRepository.save(make);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public void updateMake(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            Make make = makeAndModelEntityConverter.convertToMake(currentMakeVO);
            Optional<Make> optionalMake = makeRepository.findById(currentMakeVO.getMakeId());
            if (optionalMake.isPresent()) {
                Make newMake = optionalMake.get();
                newMake.setMakeName(make.getMakeName());
                newMake.setDescription(make.getDescription());
                newMake.setModifiedBy(make.getModifiedBy());
                makeRepository.save(newMake);
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public MakeAndModelVO getMakeFromId(Long makeId) throws MakeException {
        MakeAndModelVO makeVO = null;
        try {
            Optional<Make> optionalMake = makeRepository.findById(makeId);
            if (optionalMake.isPresent()) {
                makeVO = makeAndModelEntityConverter.getMakeVOFromMake(optionalMake.get());
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVO;
    }

    public void deleteMake(Long makeId) throws MakeException {
        try {
            makeRepository.deleteById(makeId);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public MakeAndModelVO getModelFromId(Long modelId) {
        MakeAndModelVO makeAndModelVO = null;
        Optional<Model> optionalModel = modelRepository.findById(modelId);
        if (optionalModel.isPresent()) {
            makeAndModelVO = convertModelToMakeAndModelVO(optionalModel.get());
        }
        return makeAndModelVO;
    }

    private MakeAndModelVO convertModelToMakeAndModelVO(Model model) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setModelId(model.getModelId());
        makeAndModelVO.setModelName(model.getModelName());
        makeAndModelVO.setMakeId(model.getMake().getMakeId());
        makeAndModelVO.setMakeName(model.getMake().getMakeName());
        return makeAndModelVO;
    }

    public void deleteModel(Long modelId) throws MakeException {
        try {
            modelRepository.deleteById(modelId);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public void addNewModel(MakeAndModelVO makeAndModelVO) throws MakeException {
        try {
            Model model = convertMakeAndModelVOToModel(makeAndModelVO);
            modelRepository.save(model);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    private Model convertMakeAndModelVOToModel(MakeAndModelVO makeAndModelVO) {
        Model model = new Model();
        model.setModelName(makeAndModelVO.getModelName());
        model.setMakeId(makeAndModelVO.getMakeId());
        model.setCreatedBy(makeAndModelVO.getCreatedBy());
        model.setModifiedBy(makeAndModelVO.getModifiedBy());
        Optional<Make> optionalMake = makeRepository.findById(makeAndModelVO.getMakeId());
        optionalMake.ifPresent(model::setMake);
        return model;
    }

    public void updateModel(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            Optional<Model> optionalModel = modelRepository.findById(currentMakeVO.getId());
            if(optionalModel.isPresent()) {
                Model model = optionalModel.get();
                model.setModelName(currentMakeVO.getModelName());
                Optional<Make> optionalMake = makeRepository.findById(currentMakeVO.getMakeId());
                optionalMake.ifPresent(model::setMake);
                modelRepository.save(model);
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO) throws MakeException {
        List<MakeAndModelVO> makeVOs;
        try {
            makeVOs = searchModels(searchMakeVO);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public List<MakeVO> fetchMakes() throws MakeException {
        List<MakeVO> makeVOs;
        try {
            List<Make> makes = makeRepository.findAll();
            makeVOs = convertMakeToMakeVO(makes);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> getAllModelsFromMakeId(Long makeId) throws MakeException {
        List<MakeAndModelVO> makeVOs = new ArrayList<>();
        try {
            Optional<Make> optionalMake = makeRepository.findById(makeId);
            if(optionalMake.isPresent()) {
                Make make = optionalMake.get();
                Set<Model> models = make.getModels();
                if(!models.isEmpty()) {
                    for(Model model:models) {
                        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
                        makeAndModelVO.setModelId(model.getModelId());
                        makeAndModelVO.setModelName(model.getModelName());
                        makeAndModelVO.setMakeId(model.getMakeId());
                        makeAndModelVO.setMakeName(make.getMakeName());
                        makeVOs.add(makeAndModelVO);
                    }
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    private List<MakeVO> convertMakeToMakeVO(List<Make> makes) {
        List<MakeVO> makeVOS = new ArrayList<>();
        for (Make make : makes) {
            MakeVO makeVO = new MakeVO();
            makeVO.setId(make.getMakeId());
            makeVO.setMakeName(make.getMakeName());
            makeVO.setDescription(make.getDescription());
            makeVO.setCreatedBy(make.getCreatedBy());
            makeVO.setModifiedBy(make.getModifiedBy());
            makeVOS.add(makeVO);
        }
        return makeVOS;
    }

    private List<MakeAndModelVO> searchModels(MakeAndModelVO searchMakeVO) {
        /*StringBuffer DYNAMIC_MODEL_SEARCH_QUERY = new StringBuffer();
        DYNAMIC_MODEL_SEARCH_QUERY.append(" SELECT m.id, m.modelName,m.makeId,ma.makeName ")
                .append(" FROM model m inner join make ma on m.makeId = ma.id ");
        Boolean isWhereAdded = Boolean.FALSE;
        if (searchMakeVO.getMakeId() != null && searchMakeVO.getMakeId() > 0) {
            DYNAMIC_MODEL_SEARCH_QUERY.append(" where ");
            isWhereAdded = Boolean.TRUE;
            DYNAMIC_MODEL_SEARCH_QUERY.append(" ma.id = ").append(searchMakeVO.getMakeId());
        }
        if (searchMakeVO.getModelName() != null && searchMakeVO.getModelName().trim().length() > 0) {
            if (!isWhereAdded) {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" and ");
            }
            if (searchMakeVO.getIncludes()) {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" m.modelName like '%").append(searchMakeVO.getModelName()).append
                        ("%'");
            } else if (searchMakeVO.getStartswith()) {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" m.modelName like '").append(searchMakeVO.getModelName()).append
                        ("%'");
            } else {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" m.modelName like '").append(searchMakeVO.getModelName()).append
                        ("'");
            }
        }*/

        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        if (searchMakeVO.getMakeId() != null && searchMakeVO.getMakeId() > 0) {
            Optional<Make> optionalMake = makeRepository.findById(searchMakeVO.getMakeId());
            if (optionalMake.isPresent()) {
                Make make = optionalMake.get();
                Set<Model> models = make.getModels();
                for(Model model:models) {
                    MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
                    makeAndModelVO.setModelId(model.getModelId());
                    makeAndModelVO.setModelName(model.getModelName());
                    makeAndModelVO.setMakeId(model.getMakeId());
                    makeAndModelVO.setMakeName(make.getMakeName());
                    makeAndModelVOS.add(makeAndModelVO);
                }
            }
        }

        if (searchMakeVO.getModelName() != null && searchMakeVO.getModelName().trim().length() > 0) {
            String modelName = searchMakeVO.getModelName();
            List<Model> models;
            if(searchMakeVO.getIncludes()) {
                models = modelRepository.findByModelNameIncldue(modelName);
            } else if (searchMakeVO.getStartswith()) {
                models = modelRepository.findByModelNameStartsWith(modelName);
            } else {
                models = modelRepository.findByModelName(modelName);
            }
            for(Model model:models) {
                MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
                makeAndModelVO.setModelId(model.getModelId());
                makeAndModelVO.setModelName(model.getModelName());
                makeAndModelVO.setMakeId(model.getMakeId());
                makeAndModelVO.setMakeName(model.getMake().getMakeName());
                if (!makeAndModelVOS.contains(makeAndModelVO)) {
                    makeAndModelVOS.add(makeAndModelVO);
                }
            }
        }



        //LOG.info(" The query generated for search is " + DYNAMIC_MODEL_SEARCH_QUERY.toString());
        return makeAndModelVOS;
    }

    private class MakeAndModelListRowMapper implements RowMapper {
        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            MakeAndModelVO makeVO = new MakeAndModelVO();
            makeVO.setModelId(resultSet.getLong("id"));
            makeVO.setModelName(resultSet.getString("modelName"));
            makeVO.setMakeId(resultSet.getLong("makeId"));
            makeVO.setMakeName(resultSet.getString("makeName"));
            return makeVO;
        }
    }
}
