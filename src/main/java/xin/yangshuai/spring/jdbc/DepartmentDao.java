package xin.yangshuai.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * DepartmentDao
 * 不推荐使用JdbcDaoSupport，而推荐直接使用JdbcTemplate作为Dao类的成员变量
 *
 * @author shuai
 * @date 2018/12/16
 */
@Component
public class DepartmentDao extends JdbcDaoSupport {

    @Autowired
    public void setDataSource2(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public Department get(Integer id) {
        String sql = "SELECT id, dept_name name From departments WHERE id = ?";
        BeanPropertyRowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
        return getJdbcTemplate().queryForObject(sql, rowMapper, id);
    }
}
