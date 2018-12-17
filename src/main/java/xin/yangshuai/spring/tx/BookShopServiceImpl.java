package xin.yangshuai.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookShopServiceImpl
 *
 * @author shuai
 * @date 2018/12/17
 */
@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {

	@Autowired
	private BookShopDao bookShopDao;

	/**
	 * 添加事务注解
	 * 1. 使用propagation指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时
	 * 如何使用事务，默认取值为REQUIRED，即使用调用方法的事务
	 * REQUIRES_NEW：使用自己的事务，调用的事务方法的事务被挂起
	 * 2. 使用isolation 指定事务的隔离级别，最常用的取值为 READ_COMMITTED
	 * 3. 默认情况下Spring的声明式事务对所有的运行时异常进行回滚
	 * 也可以通过对应的属性进行设置，通常情况取默认值即可
	 * 4. 只读事务属性：表示这个事务只读取数据但不更新数据，
	 * 这样可以帮助数据库引擎优化事务，若真的是一个只读取数据库值的方法，应设置readOnly = true
	 * 5. 使用timeout指定强制回滚之前事务可以占用的时间
	 * @param username
	 * @param isbn
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW
			,isolation = Isolation.READ_COMMITTED
			,rollbackFor = {Exception.class}
			,noRollbackFor = {UserAccountException.class}
			,readOnly = false
			,timeout = 3
	)
	@Override
	public void purchase(String username, String isbn) {
        //1. 获取书的单价
		int price = bookShopDao.findBookPriceByIsbn(isbn);
		//2. 更新书的库存
		bookShopDao.updateBookStock(isbn);
		//3. 更新用户余额
		bookShopDao.updateUserAccount(username,price);
	}
}
