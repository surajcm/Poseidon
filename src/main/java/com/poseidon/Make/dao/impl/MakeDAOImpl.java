package com.poseidon.Make.dao.impl;

import com.poseidon.Make.dao.MakeDAO;
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
    private final String GET_MAKE_AND_MODEL_SQL = "SELECT m.Id, m.ModelName,m.makeId,ma.MakeName FROM model m inner join make ma on m.makeId=ma.Id;";
    private final String GET_MAKE_SQL = "SELECT Id,MakeName,Description FROM make ;";
    private final String INSERT_NEW_MAKE_SQL = "insert into make( MakeName, Description, createdOn, modifiedOn, createdBy, modifiedBy ) values (?, ?, ?, ?, ?, ?); ";
    private final String INSERT_NEW_MODEL_SQL = "insert into model( ModelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy ) values (?, ?, ?, ?, ?, ?); ";
    private final String GET_SINGLE_MAKE_SQL = "select * from make where Id = ?";
    private final String GET_SINGLE_MODEL_SQL = "SELECT m.Id, m.ModelName,m.makeId,ma.MakeName FROM model m inner join make ma on m.makeId=ma.Id and m.Id = ?; ";
    private static final String DELETE_MAKE_BY_ID_SQL = " delete from make where id = ? ";
    private static final String DELETE_MODEL_BY_ID_SQL = " delete from model where id = ? ";
    private static final String UPDATE_MAKE_SQL = " update make set MakeName = ?, Description = ? , modifiedOn = ? , modifiedBy = ? where Id = ?";
    private static final String UPDATE_MODEL_SQL = " update model set makeId = ?, ModelName = ? , modifiedOn = ? , modifiedBy = ? where Id = ?";

    public List<MakeVO> listAllMakesAndModels() throws MakeException {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeAndModels();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public List<MakeVO> listAllMakes() throws MakeException {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMake();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    public void addNewMake(MakeVO currentMakeVO) throws MakeException {
        try {
            saveNewMake(currentMakeVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public MakeVO getMakeFromId(Long makeId) throws MakeException {
        MakeVO makeVO;
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

    public MakeVO getModelFromId(Long modelId) throws MakeException {
        return (MakeVO) getJdbcTemplate().queryForObject(GET_SINGLE_MODEL_SQL, new Object[]{modelId}, new MakeAndModelListRowMapper());
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

    public void updateMake(MakeVO currentMakeVO) throws MakeException {
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

    public void addNewModel(MakeVO currentMakeVO) throws MakeException {
        try {
            saveNewModel(currentMakeVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
    }

    public void updateModel(MakeVO currentMakeVO) throws MakeException {
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

    private void saveNewModel(MakeVO currentMakeVO) {
        Object[] parameters =
                new Object[]{currentMakeVO.getModelName(),
                        currentMakeVO.getMakeId(),
                        currentMakeVO.getCreatedDate(),
                        currentMakeVO.getModifiedDate(),
                        currentMakeVO.getCreatedBy(),
                        currentMakeVO.getModifiedBy()};
        getJdbcTemplate().update(INSERT_NEW_MODEL_SQL, parameters);
    }


    private MakeVO getMakeById(Long makeId) {
        return (MakeVO) getJdbcTemplate().queryForObject(GET_SINGLE_MAKE_SQL, new Object[]{makeId}, new MakeRowMapper());
    }

    private void saveNewMake(MakeVO currentMakeVO) throws DataAccessException {
        Object[] parameters =
                new Object[]{currentMakeVO.getMakeName(),
                        currentMakeVO.getDescription(),
                        currentMakeVO.getCreatedDate(),
                        currentMakeVO.getModifiedDate(),
                        currentMakeVO.getCreatedBy(),
                        currentMakeVO.getModifiedBy()};
        getJdbcTemplate().update(INSERT_NEW_MAKE_SQL, parameters);
    }

    private List<MakeVO> getMake() {
        return (List<MakeVO>) getJdbcTemplate().query(GET_MAKE_SQL, new MakeRowMapper());
    }

    private List<MakeVO> getMakeAndModels() {
        return (List<MakeVO>) getJdbcTemplate().query(GET_MAKE_AND_MODEL_SQL, new MakeAndModelListRowMapper());
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
            MakeVO makeVO = new MakeVO();
            makeVO.setModelId(resultSet.getLong("Id"));
            makeVO.setModelName(resultSet.getString("ModelName"));
            makeVO.setMakeId(resultSet.getLong("makeId"));
            makeVO.setMakeName(resultSet.getString("MakeName"));

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
            MakeVO makeVO = new MakeVO();
            makeVO.setMakeId(resultSet.getLong("Id"));
            makeVO.setMakeName(resultSet.getString("MakeName"));
            makeVO.setDescription(resultSet.getString("Description"));
            return makeVO;
        }
    }
}
