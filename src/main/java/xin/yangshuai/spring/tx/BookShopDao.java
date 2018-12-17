package xin.yangshuai.spring.tx;

/**
 * BookShopDao
 *
 * @author shuai
 * @date 2018/12/17
 */
public interface BookShopDao {
	/**
	 * 根据书号获取书的单价
	 * @param isbn 书号
	 * @return
	 */
	int findBookPriceByIsbn(String isbn);

	/**
	 * 更新书的库存，使书号对应的库存 -1
	 * @param isbn 书号
	 */
	void updateBookStock(String isbn);

	/**
	 * 更新用户的账户余额：使username的balance - price
	 * @param username 用户名
	 * @param price 书的价格
	 */
	void updateUserAccount(String username,int price);
}
