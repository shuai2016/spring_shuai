package xin.yangshuai.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBCTest
 *
 * @author shuai
 * @date 2018/12/14
 */
public class JDBCTest {

    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate;
    private DepartmentDao departmentDao;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    {
        ctx = new ClassPathXmlApplicationContext("xin/yangshuai/spring/jdbc/applicationContext.xml");
        jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        departmentDao = ctx.getBean(DepartmentDao.class);
        namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    /**
     * 使用具名参数时，可以使用update(String sql, SqlParameterSource paramSource)方法进行更新操作
     * 1. SQL语句中的参数名和类的属性一致
     * 2. 使用SqlParameterSource的BeanPropertySqlParameterSource实现类做为参数。
     */
    @Test
    public void testNamedParameterJdbcTemplate2(){
        String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:lastName,:email,:deptId)";

        Employee employee = new Employee();
        employee.setLastName("XYZ");
        employee.setEmail("xyz@qq.com");
        employee.setDeptId(3);

        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
        namedParameterJdbcTemplate.update(sql,paramSource);
    }

    /**
     * 可以为参数起名字
     * 1. 好处：若有多个参数，则不用再去对应位置，直接对应参数名，便于维护
     * 2. 缺点：较为麻烦
     */
    @Test
    public void testNamedParameterJdbcTemplate(){
        String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:ln,:email,:deptId)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ln","FF");
        paramMap.put("email","ff@qq.com");
        paramMap.put("deptId",2);

        namedParameterJdbcTemplate.update(sql,paramMap);
    }

    @Test
    public void testDepartmentDao(){
        System.out.println(departmentDao.get(1));
    }

    /**
     * 获取单个列的值，或做统计查询
     * 使用queryForObject(String sql, Class<T> requiredType)方法
     */
    @Test
    public void testQueryForObject2() {
        String sql = "SELECT count(id) FROM employees";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println(count);
    }

    /**
     * 查询实体类的集合
     * 调用的不是queryForList方法
     */
    @Test
    public void testQueryForList() {
        String sql = "SELECT id,last_name lastName,email FROM employees WHERE id > ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employees = jdbcTemplate.query(sql, rowMapper, 1);
        System.out.println(employees);
    }

    /**
     * 从数据库中获取一条记录,实际得到对应的一个对象
     * 不是调用queryForObject(String sql, Class<T> requiredType, Object... args)方法!
     * 而是调用queryForObject(String sql, RowMapper<T> rowMapper, Object... args)方法
     * 1. RowMapper指定如何去映射结果集的行,常用的实现类为BeanPropertyRowMapper
     * 2. 使用SQL 中列的别名完成列名和类的属性的映射,例如: last_name lastName
     * 3. 不支持级联是属性, JdbcTemplate 到底是一个JDBC 的小工具,而不是 ORM 框架
     */
    @Test
    public void testQueryForObject() {
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
    public void testBatchUpdate() {
        String sql = "INSERT INTO employees(last_name,email,dept_id) VALUES (?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"AA", "aa@qq.com", 1});
        batchArgs.add(new Object[]{"BB", "bb@qq.com", 2});
        batchArgs.add(new Object[]{"CC", "cc@qq.com", 3});
        batchArgs.add(new Object[]{"DD", "dd@qq.com", 4});

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 执行 INSERT, UPDATE, DELETE
     */
    @Test
    public void testUpdate() {
        String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, "Jack", 5);
    }
}
