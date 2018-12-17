package xin.yangshuai.spring.tx.xml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import xin.yangshuai.spring.tx.xml.service.BookShopService;
import xin.yangshuai.spring.tx.xml.service.Cashier;

import java.util.List;

/**
 * CashierImpl
 *
 * @author shuai
 * @date 2018/12/17
 */
public class CashierImpl implements Cashier {

	@Autowired
	private BookShopService bookShopService;

    public void setBookShopService(BookShopService bookShopService) {
        this.bookShopService = bookShopService;
    }

    @Override
	public void checkout(String username, List<String> isbns) {
		for (String isbn : isbns) {
			bookShopService.purchase(username,isbn);
		}
	}
}
