package com.mama.dandy.dao;

import com.mama.dandy.bo.VerifyCodeBo;
import com.mama.dandy.domain.VerifyCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VerifyDao extends BaseDao<VerifyCode> {


    public void batchSave(List<VerifyCode> list){
        final List<VerifyCode> templist = list;
        String sql="insert into shuoma_verify_code(agentCode,verifyCode,isValid,createTime,operator)" +
                " values(?,?,?,?,?)";
        this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return templist.size();
            }
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                ps.setString(1, templist.get(i).getAgentCode());
                ps.setString(2, templist.get(i).getVerifyCode());
                ps.setInt(3, templist.get(i).getIsValid());
                ps.setLong(4, templist.get(i).getCreateTime());
                ps.setString(5, templist.get(i).getOperator());
                //ps.setLong(5, templist.get(i).getVerifyTime());
            }
        });
    };

    public List<String> findByCodes(List<String> strList) {
        StringBuilder sb = new StringBuilder("select verifyCode from shuoma_verify_code where verifyCode in (");
        for(String code : strList){
            sb.append("'").append(code).append("'").append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");

        List<String> results = getJdbcTemplate().queryForList(sb.toString(),String.class);
        return results;
    }

    public VerifyCode findByCode(String code){
        String sql = "select * from shuoma_verify_code where verifyCode = ?";
        return this.getJdbcTemplate().query(sql, new ResultSetExtractor<VerifyCode>() {
            @Override
            public VerifyCode extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if (resultSet.next()){
                    VerifyCode verifyCode = new VerifyCode();
                    verifyCode.setVerifyCode(resultSet.getString("verifyCode"));
                    verifyCode.setCreateTime(resultSet.getLong("createTime"));
                    verifyCode.setAgentCode(resultSet.getString("agentCode"));
                    verifyCode.setIsValid(resultSet.getInt("isValid"));
                    return verifyCode;
                }
                return null;
            }
        },code);
    }

    public void updateStatus(VerifyCode verifyCode) {
        String sql = "update shuoma_verify_code set isValid=?,verifyTime=? where verifyCode=?";
        this.getJdbcTemplate().update(sql,verifyCode.getIsValid(),verifyCode.getVerifyTime(),verifyCode.getVerifyCode());
    }

    public int countCodes(VerifyCodeBo bo) {
        String sql = "SELECT count(1) FROM shuoma_verify_code a where 1=1 ";
        List<Object> params= new ArrayList<Object>();
        if(bo.getIsValid()!=null){
            sql +=" AND a.isValid = ? ";
            params.add(bo.getIsValid());
        }
        if(StringUtils.isNoneEmpty(bo.getAgentCode())){
            sql += " AND a.agentCode = ? ";
            params.add(bo.getAgentCode());
        }
        if(bo.getStartTime()!=null){
            sql += " AND a.verifyTime >? ";
            params.add(bo.getStartTime());
        }
        if(bo.getEndTime()!=null){
            sql += " AND a.verifyTime <? ";
            params.add(bo.getEndTime());
        }
        if(bo.getCreateStartTime()!=null){
            sql += " AND a.createTime >? ";
            params.add(bo.getCreateStartTime());
        }
        if(bo.getCreateEndTime()!=null){
            sql += " AND a.createTime <? ";
            params.add(bo.getCreateEndTime());
        }
        return getJdbcTemplate().queryForObject(sql, params.toArray(),Integer.class);
    }

    public List<VerifyCode> listCodes(VerifyCodeBo bo) {

        String sql = "SELECT *  FROM shuoma_verify_code a where 1=1 ";
        List<Object> params= new ArrayList<Object>();
        if(bo.getIsValid()!=null){
            sql +=" AND a.isValid = ? ";
            params.add(bo.getIsValid());
        }
        if(StringUtils.isNoneEmpty(bo.getAgentCode())){
            sql += " AND a.agentCode = ?";
            params.add(bo.getAgentCode());
        }
        if(bo.getStartTime()!=null){
            sql += " AND a.verifyTime >? ";
            params.add(bo.getStartTime());
        }
        if(bo.getEndTime()!=null){
            sql += " AND a.verifyTime <? ";
            params.add(bo.getEndTime());
        }
        if(bo.getCreateStartTime()!=null){
            sql += " AND a.createTime >? ";
            params.add(bo.getCreateStartTime());
        }
        if(bo.getCreateEndTime()!=null){
            sql += " AND a.createTime <? ";
            params.add(bo.getCreateEndTime());
        }
        sql +=" ORDER BY a.id asc";
        if(bo.getRows()!=null && bo.getPage()!=null){
            sql +=" LIMIT ?,?";
            params.add((bo.getPage()-1)*bo.getRows());
            params.add(bo.getRows());
        }
        return findList(sql, VerifyCode.class, params.toArray());
    }

    public void delete(VerifyCodeBo bo) {
        String sql = "DELETE FROM shuoma_verify_code WHERE id in ("+bo.getIds()+")";
        this.getJdbcTemplate().update(sql);
    }
}
