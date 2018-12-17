package xin.yangshuai.spring.tx.xml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xin.yangshuai.spring.tx.xml.dao.BookShopDao;
import xin.yangshuai.spring.tx.xml.service.BookShopService;
import xin.yangshuai.spring.tx.xml.service.Cashier;

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
		context = new ClassPathXmlApplicationContext("xin/yangshuai/spring/tx/xml/applicationContext.xml");
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

}
