package com.poseidon.Make.dao.impl;

import com.poseidon.Make.dao.MakeDAO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;

import java.util.List;
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

    public List<MakeVO> listAllMakesAndModels() throws MakeException {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeAndModels();
        } catch (DataAccessException e) {
            throw new MakeException(MakeException.DATABASE_ERROR);
        }
        return makeVOs;
    }

    private List<MakeVO> getMakeAndModels() {
        return (List<MakeVO>) getJdbcTemplate().query(GET_MAKE_AND_MODEL_SQL, new MakeListRowMapper());
    }

    private class MakeListRowMapper implements RowMapper {
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
}
