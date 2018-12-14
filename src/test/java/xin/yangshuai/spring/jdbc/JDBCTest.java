package xin.yangshuai.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBCTest
 *
 * @author shuai
 * @date 2018/12/14
 */
public class JDBCTest {

	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate;
	{
		ctx = new ClassPathXmlApplicationContext("xin/yangshuai/spring/jdbc/applicationContext.xml");
		jdbcTemplate = ctx.getBean(JdbcTemplate.class);
	}

	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}

	/**
	 * 从数据库中获取一条记录,实际得到对应的一个对象
	 * 不是调用queryForObject(String sql, Class<T> requiredType, Object... args)方法!
	 *  而是调用queryForObject(String sql, RowMapper<T> rowMapper, Object... args)方法
	 *  1. RowMapper指定如何去映射结果集的行,常用的实现类为BeanPropertyRowMapper
	 *  2. 使用SQL 中列的别名完成列名和类的属性的映射,例如: last_name lastName
	 *  3. 不支持级联是属性, JdbcTemplate 到底是一个JDBC 的小工具,而不是 ORM 框架
	 */
	@Test
	public void testQueryForObject(){
		String sql = "SELECT id,last_name lastName,email,dept_id FROM employees WHERE id = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 6);
		System.out.println(employee);
	}

	/**
	 * 执行批量更新: 批量的 INSERT, UPDATE, DELETE
	 * 最后一个参数是Object[]的List类型: 因为修改一条记录需要一个Object的数组
	 */
	@Test
	public void testBatchUpdate(){
		String sql = "INSERT INTO employees(last_name,email,dept_id) VALUES (?,?,?)";
		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[]{"AA","aa@qq.com",1});
		batchArgs.add(new Object[]{"BB","bb@qq.com",2});
		batchArgs.add(new Object[]{"CC","cc@qq.com",3});
		batchArgs.add(new Object[]{"DD","dd@qq.com",4});

		jdbcTemplate.batchUpdate(sql,batchArgs);
	}

	/**
	 * 执行 INSERT, UPDATE, DELETE
	 */
	@Test
	public void testUpdate(){
		String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
		jdbcTemplate.update(sql,"Jack",5);
	}
}
