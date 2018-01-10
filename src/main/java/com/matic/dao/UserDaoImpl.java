package com.matic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.matic.vo.User;
@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("secondJdbcTemplate")
	private JdbcTemplate secondJdbcTemplate;
	
	@Override
	public List<User> getAllUser() throws Exception {
		List<User> list = null;
		list = jdbcTemplate.query("select * from user",new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int i) throws SQLException {
				User user = new User();
				user.setId(Integer.parseInt(rs.getString("id")));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
		return list;
	}

	@Override
	public User getOneById(Integer id) throws Exception {
		return secondJdbcTemplate.query("select * from user where id = ?",new Object[]{id},new ResultSetExtractor<User>() {
			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()){
					User user = new User();
					user.setId(Integer.parseInt(rs.getString("id")));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					return user;
				}else{
					return null;
				}
			}
		});
	}
}
