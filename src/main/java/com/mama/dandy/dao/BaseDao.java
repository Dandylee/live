package com.mama.dandy.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mama.dandy.domain.LoginAccount;

@Repository
public class BaseDao<T> {
	
	@Autowired
	@Qualifier("liveJT")
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public T get(String sql,Class<T> requiredType,Object[] array){
		return (T) jdbcTemplate.queryForObject(sql, requiredType, array);
	}
	
	public List<T> findList(String sql,Class<T> elementType,Object[] array){
		//return jdbcTemplate.queryForList(sql, elementType, array);
		return jdbcTemplate.query(sql, array, new BeanPropertyRowMapper<T>(elementType));
	}
	
	public  T getObject(String sql,Class<T> elementType,Object... array){
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(elementType), array);
	}

}
