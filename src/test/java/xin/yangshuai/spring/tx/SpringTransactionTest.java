package xin.yangshuai.spring.tx;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * SpringTransactionTest
 *
 * @author shuai
 * @date 2018/12/17
 */
public class SpringTransactionTest {

	private ApplicationContext context;
	private BookShopDao bookShopDao;
	private BookShopService bookShopService;
	private Cashier cashier;

	{
		context = new ClassPathXmlApplicationContext("xin/yangshuai/spring/tx/applicationContext.xml");
		bookShopDao = context.getBean(BookShopDao.class);
		bookShopService = context.getBean(BookShopService.class);
		cashier = context.getBean(Cashier.class);
	}

	@Test
	public void testTransactionPropagation() {
		cashier.checkout("AA",Arrays.asList("1001","1002"));
	}


	@Test
	public void testBookShopService() {
		bookShopService.purchase("AA","1001");
	}

	@Test
	public void testBookShopDaoUpdateUserAccount() {
		bookShopDao.updateUserAccount("AA", 100);
	}

	@Test
	public void testBookShopDaoUpdateBookStock() {
		bookShopDao.updateBookStock("1001");
	}

	@Test
	public void testBookShopDaoFindPriceByIsbn() {
		System.out.println(bookShopDao.findBookPriceByIsbn("1001"));
	}

}
