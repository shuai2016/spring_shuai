package xin.yangshuai.spring.tx.xml.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import xin.yangshuai.spring.tx.xml.exception.BookStockException;
import xin.yangshuai.spring.tx.xml.exception.UserAccountException;
import xin.yangshuai.spring.tx.xml.dao.BookShopDao;

/**
 * BookShopDaoImpl
 *
 * @author shuai
 * @date 2018/12/17
 */
public class BookShopDaoImpl implements BookShopDao {

	private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
	public int findBookPriceByIsbn(String isbn) {
		String sql = "SELECT price FROM book WHERE isbn = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, isbn);
	}

	@Override
	public void updateBookStock(String isbn) {
		//检查书的库存是否足够，若不够，则抛出异常
		String sql2 = "SELECT stock FROM book_stock WHERE isbn = ?";
		Integer stock = jdbcTemplate.queryForObject(sql2, Integer.class, isbn);
		if (stock == 0) {
			throw new BookStockException("库存不足！");
		}
		String sql = "UPDATE book_stock SET stock = stock - 1 WHERE isbn = ?";
		jdbcTemplate.update(sql, isbn);
	}

	@Override
	public void updateUserAccount(String username, int price) {
		//验证余额是否足够，若不足，则抛出异常
		String sql2 = "SELECT balance FROM account WHERE username = ?";
		Integer balance = jdbcTemplate.queryForObject(sql2, Integer.class, username);
		if (balance < price) {
			throw new UserAccountException("余额不足！");
		}
		String sql = "UPDATE account SET balance = balance - ? WHERE username = ?";
		jdbcTemplate.update(sql, price, username);
	}
}
