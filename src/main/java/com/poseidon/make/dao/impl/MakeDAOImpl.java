package com.poseidon.make.dao.impl;

import com.poseidon.make.dao.MakeDAO;
import com.poseidon.make.dao.entities.Make;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 7:28:10 PM
 */
@Repository
public class MakeDAOImpl implements MakeDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MakeDAOImpl.class);
    private static final String DELETE_MODEL_BY_ID_SQL = " delete from model where id = ? ";
    private static final String UPDATE_MODEL_SQL = " update model set makeId = ?, modelName = ? , modifiedOn = ? , " +
            "modifiedBy = ? where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MakeRepository makeRepository;

    public List<MakeAndModelVO> listAllMakesAndModels() throws MakeException {
        List<MakeAndModelVO> makeVOs;
        try {
            makeVOs = getMakeAndModels();
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> listAllMakes() throws MakeException {
        List<MakeAndModelVO> makeVOs;
        try {
            List<Make> makes = makeRepository.findAll();
            makeVOs = convertMakeToMakeAndModelVOs(makes);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public void addNewMake(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            Make make = convertToMake(currentMakeVO);
            makeRepository.save(make);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    private Make convertToMake(MakeAndModelVO currentMakeVO) {
        Make make = new Make();
        make.setMakeName(currentMakeVO.getMakeName());
        make.setDescription(currentMakeVO.getDescription());
        make.setCreatedBy(currentMakeVO.getCreatedBy());
        make.setCreatedOn(currentMakeVO.getCreatedDate());
        make.setModifiedBy(currentMakeVO.getModifiedBy());
        make.setModifiedOn(currentMakeVO.getModifiedDate());
        return make;
    }

    public void updateMake(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            Make make = convertToMake(currentMakeVO);
            Optional<Make> optionalMake = makeRepository.findById(currentMakeVO.getMakeId().intValue());
            if (optionalMake.isPresent()) {
                Make newMake = optionalMake.get();
                newMake.setMakeName(make.getMakeName());
                newMake.setDescription(make.getDescription());
                newMake.setModifiedOn(new Date());
                newMake.setModifiedBy("app");
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
            Optional<Make> optionalMake = makeRepository.findById(makeId.intValue());
            if (optionalMake.isPresent()) {
                makeVO = getMakeVOFromMake(optionalMake.get());
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVO;
    }

    private MakeAndModelVO getMakeVOFromMake(Make make) {
        MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
        makeAndModelVO.setMakeId(Long.valueOf(make.getId()));
        makeAndModelVO.setMakeName(make.getMakeName());
        makeAndModelVO.setDescription(make.getDescription());
        return makeAndModelVO;
    }

    public void deleteMake(Long makeId) throws MakeException {
        try {
            makeRepository.deleteById(makeId.intValue());
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public MakeAndModelVO getModelFromId(Long modelId) {
        String GET_SINGLE_MODEL_SQL = "SELECT m.id, m.modelName,m.makeId,ma.makeName FROM model m inner " +
                "join make ma on m.makeId=ma.id and m.id = ?; ";
        return (MakeAndModelVO) jdbcTemplate.queryForObject(GET_SINGLE_MODEL_SQL, new Object[]{modelId}, new
                MakeAndModelListRowMapper());
    }

    public void deleteModel(Long modelId) throws MakeException {
        try {
            Object[] parameters = new Object[]{modelId};
            jdbcTemplate.update(DELETE_MODEL_BY_ID_SQL, parameters);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public void addNewModel(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            saveNewModel(currentMakeVO);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public void updateModel(MakeAndModelVO currentMakeVO) throws MakeException {
        Object[] parameters = new Object[]{
                currentMakeVO.getMakeId(),
                currentMakeVO.getModelName(),
                new Date(),
                currentMakeVO.getModifiedBy(),
                currentMakeVO.getModelId()};

        try {
            jdbcTemplate.update(UPDATE_MODEL_SQL, parameters);
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
        List<MakeAndModelVO> makeVOs;
        try {
            makeVOs = fetchModelsForId(makeId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    private List<MakeAndModelVO> fetchModelsForId(Long makeId) {
        StringBuffer FETCH_MODEL_QUERY = new StringBuffer("SELECT m.id, m.modelName, m.makeId, ma.makeName ")
                .append(" FROM model m inner join make ma on m.makeId=ma.id and ma.id = ").append(makeId);
        LOG.info("The query generated is " + FETCH_MODEL_QUERY);
        return (List<MakeAndModelVO>) jdbcTemplate.query(FETCH_MODEL_QUERY.toString(), new MakeAndModelListRowMapper());
    }

    private List<MakeVO> convertMakeToMakeVO(List<Make> makes) {
        List<MakeVO> makeVOS = new ArrayList<>();
        for (Make make : makes) {
            MakeVO makeVO = new MakeVO();
            makeVO.setId(Long.valueOf(make.getId()));
            makeVO.setMakeName(make.getMakeName());
            makeVO.setDescription(make.getDescription());
            makeVO.setCreatedBy(make.getCreatedBy());
            makeVO.setCreatedOn(make.getCreatedOn());
            makeVO.setModifiedBy(make.getModifiedBy());
            makeVO.setModifiedOn(make.getModifiedOn());
            makeVOS.add(makeVO);
        }
        return makeVOS;
    }

    private List<MakeAndModelVO> searchModels(MakeAndModelVO searchMakeVO) {
        StringBuffer DYNAMIC_MODEL_SEARCH_QUERY = new StringBuffer();
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
        }

        LOG.info(" The query generated for search is " + DYNAMIC_MODEL_SEARCH_QUERY.toString());
        return (List<MakeAndModelVO>) jdbcTemplate.query(DYNAMIC_MODEL_SEARCH_QUERY.toString(), new
                MakeAndModelListRowMapper());
    }


    private void saveNewModel(MakeAndModelVO currentMakeVO) {
        Object[] parameters =
                new Object[]{currentMakeVO.getModelName(),
                        currentMakeVO.getMakeId(),
                        currentMakeVO.getCreatedDate(),
                        currentMakeVO.getModifiedDate(),
                        currentMakeVO.getCreatedBy(),
                        currentMakeVO.getModifiedBy()};
        String INSERT_NEW_MODEL_SQL = "insert into model( modelName, makeId, createdOn, modifiedOn, " +
                "createdBy, modifiedBy ) values (?, ?, ?, ?, ?, ?); ";
        jdbcTemplate.update(INSERT_NEW_MODEL_SQL, parameters);
    }

    private List<MakeAndModelVO> convertMakeToMakeAndModelVOs(List<Make> makes) {
        List<MakeAndModelVO> makeAndModelVOS = new ArrayList<>();
        makes.forEach(make -> {
            MakeAndModelVO makeAndModelVO = new MakeAndModelVO();
            makeAndModelVO.setMakeId(Long.valueOf(make.getId()));
            makeAndModelVO.setMakeName(make.getMakeName());
            makeAndModelVO.setDescription(make.getDescription());
            makeAndModelVO.setCreatedBy(make.getCreatedBy());
            makeAndModelVO.setCreatedDate(make.getCreatedOn());
            makeAndModelVO.setModifiedBy(make.getModifiedBy());
            makeAndModelVO.setModifiedDate(make.getModifiedOn());
            makeAndModelVOS.add(makeAndModelVO);
        });
        return makeAndModelVOS;
    }

    private List<MakeAndModelVO> getMakeAndModels() {
        String GET_MAKE_AND_MODEL_SQL = "SELECT m.id, m.modelName,m.makeId,ma.makeName FROM model m inner " +
                "join make ma on m.makeId=ma.id order by m.modifiedOn;";
        return (List<MakeAndModelVO>) jdbcTemplate.query(GET_MAKE_AND_MODEL_SQL, new MakeAndModelListRowMapper());
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
