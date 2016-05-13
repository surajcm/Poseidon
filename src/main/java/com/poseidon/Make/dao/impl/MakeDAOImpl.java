package com.poseidon.Make.dao.impl;

import com.poseidon.Make.dao.MakeDAO;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

import java.util.List;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:28:10 PM
 */
public class MakeDAOImpl extends JdbcDaoSupport implements MakeDAO {
    //logger
    private final Log log = LogFactory.getLog(MakeDAOImpl.class);
    private final String GET_MAKE_AND_MODEL_SQL = "SELECT m.id, m.modelName,m.makeId,ma.makeName FROM model m inner join make ma on m.makeId=ma.id order by m.modifiedOn;";
    private final String GET_MAKE_SQL = "SELECT id,makeName,description,createdOn,modifiedOn,createdBy,modifiedBy FROM make order by modifiedOn;";
    private final String INSERT_NEW_MAKE_SQL = "insert into make( makeName, description, createdOn, modifiedOn, createdBy, modifiedBy ) values (?, ?, ?, ?, ?, ?); ";
    private final String INSERT_NEW_MODEL_SQL = "insert into model( modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy ) values (?, ?, ?, ?, ?, ?); ";
    private final String GET_SINGLE_MAKE_SQL = "select * from make where id = ?";
    private final String GET_SINGLE_MODEL_SQL = "SELECT m.id, m.modelName,m.makeId,ma.makeName FROM model m inner join make ma on m.makeId=ma.id and m.id = ?; ";
    private static final String DELETE_MAKE_BY_ID_SQL = " delete from make where id = ? ";
    private static final String DELETE_MODEL_BY_ID_SQL = " delete from model where id = ? ";
    private static final String UPDATE_MAKE_SQL = " update make set makeName = ?, description = ? , modifiedOn = ? , modifiedBy = ? where id = ?";
    private static final String UPDATE_MODEL_SQL = " update model set makeId = ?, modelName = ? , modifiedOn = ? , modifiedBy = ? where id = ?";

    public List<MakeAndModelVO> listAllMakesAndModels() throws MakeException {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMakeAndModels();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> listAllMakes() throws MakeException {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = getMake();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public void addNewMake(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            saveNewMake(currentMakeVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public MakeAndModelVO getMakeFromId(Long makeId) throws MakeException {
        MakeAndModelVO makeVO;
        try {
            makeVO = getMakeById(makeId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVO;
    }

    public void deleteMake(Long makeId) throws MakeException {
        try {
            Object[] parameters = new Object[]{makeId};
            getJdbcTemplate().update(DELETE_MAKE_BY_ID_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public MakeAndModelVO getModelFromId(Long modelId) throws MakeException {
        return (MakeAndModelVO) getJdbcTemplate().queryForObject(GET_SINGLE_MODEL_SQL, new Object[]{modelId}, new MakeAndModelListRowMapper());
    }

    public void deleteModel(Long modelId) throws MakeException {
        try {
            Object[] parameters = new Object[]{modelId};
            getJdbcTemplate().update(DELETE_MODEL_BY_ID_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public void updateMake(MakeAndModelVO currentMakeVO) throws MakeException {
        Object[] parameters = new Object[]{
                currentMakeVO.getMakeName(),
                currentMakeVO.getDescription(),
                new Date(),
                currentMakeVO.getModifiedBy(),
                currentMakeVO.getMakeId()};

        try {
            getJdbcTemplate().update(UPDATE_MAKE_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public void addNewModel(MakeAndModelVO currentMakeVO) throws MakeException {
        try {
            saveNewModel(currentMakeVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
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
            getJdbcTemplate().update(UPDATE_MODEL_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public List<MakeAndModelVO> searchMakeVOs(MakeAndModelVO searchMakeVO) throws MakeException {
        List<MakeAndModelVO> makeVOs = null;
        try {
            makeVOs = searchModels(searchMakeVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public List<MakeVO> fetchMakes() throws MakeException {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = fetchAllMakes();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public List<MakeAndModelVO> getAllModelsFromMakeId(Long makeId) throws MakeException {
        List<MakeAndModelVO> makeVOs = null;
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
        log.info("The query generated is " + FETCH_MODEL_QUERY);
        return (List<MakeAndModelVO>) getJdbcTemplate().query(FETCH_MODEL_QUERY.toString(), new MakeAndModelListRowMapper());
    }

    private List<MakeVO> fetchAllMakes() {
        return (List<MakeVO>) getJdbcTemplate().query(GET_MAKE_SQL, new MakeOnlyRowMapper());
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
        if(searchMakeVO.getModelName() != null && searchMakeVO.getModelName().trim().length() > 0){
            if (!isWhereAdded) {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" and ");
            }
            if(searchMakeVO.getIncludes()){
                DYNAMIC_MODEL_SEARCH_QUERY.append(" m.modelName like '%").append(searchMakeVO.getModelName()).append("%'");
            }else if (searchMakeVO.getStartswith()){
                DYNAMIC_MODEL_SEARCH_QUERY.append(" m.modelName like '").append(searchMakeVO.getModelName()).append("%'");
            }else {
                DYNAMIC_MODEL_SEARCH_QUERY.append(" m.modelName like '").append(searchMakeVO.getModelName()).append("'");
            }
        }

        log.info(" The query generated for search is " + DYNAMIC_MODEL_SEARCH_QUERY.toString());
        return (List<MakeAndModelVO>) getJdbcTemplate().query(DYNAMIC_MODEL_SEARCH_QUERY.toString(), new MakeAndModelListRowMapper());
    }


    private void saveNewModel(MakeAndModelVO currentMakeVO) {
        Object[] parameters =
                new Object[]{currentMakeVO.getModelName(),
                        currentMakeVO.getMakeId(),
                        currentMakeVO.getCreatedDate(),
                        currentMakeVO.getModifiedDate(),
                        currentMakeVO.getCreatedBy(),
                        currentMakeVO.getModifiedBy()};
        getJdbcTemplate().update(INSERT_NEW_MODEL_SQL, parameters);
    }


    private MakeAndModelVO getMakeById(Long makeId) {
        return (MakeAndModelVO) getJdbcTemplate().queryForObject(GET_SINGLE_MAKE_SQL, new Object[]{makeId}, new MakeRowMapper());
    }

    private void saveNewMake(MakeAndModelVO currentMakeVO) throws DataAccessException {
        Object[] parameters =
                new Object[]{currentMakeVO.getMakeName(),
                        currentMakeVO.getDescription(),
                        currentMakeVO.getCreatedDate(),
                        currentMakeVO.getModifiedDate(),
                        currentMakeVO.getCreatedBy(),
                        currentMakeVO.getModifiedBy()};
        getJdbcTemplate().update(INSERT_NEW_MAKE_SQL, parameters);
    }

    private List<MakeAndModelVO> getMake() {
        return (List<MakeAndModelVO>) getJdbcTemplate().query(GET_MAKE_SQL, new MakeRowMapper());
    }

    private List<MakeAndModelVO> getMakeAndModels() {
        return (List<MakeAndModelVO>) getJdbcTemplate().query(GET_MAKE_AND_MODEL_SQL, new MakeAndModelListRowMapper());
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

    private class MakeRowMapper implements RowMapper {
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
            makeVO.setMakeId(resultSet.getLong("id"));
            makeVO.setMakeName(resultSet.getString("makeName"));
            makeVO.setDescription(resultSet.getString("description"));
            return makeVO;
        }
    }

    private class MakeOnlyRowMapper implements RowMapper {
        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            MakeVO makeVO = new MakeVO();
            makeVO.setId(resultSet.getLong("id"));
            makeVO.setMakeName(resultSet.getString("makeName"));
            makeVO.setDescription(resultSet.getString("description"));
            makeVO.setCreatedOn(resultSet.getDate("createdOn"));
            makeVO.setModifiedOn(resultSet.getDate("modifiedOn"));
            makeVO.setCreatedBy(resultSet.getString("createdBy"));
            makeVO.setModifiedBy(resultSet.getString("modifiedBy"));
            return makeVO;
        }
    }
}
