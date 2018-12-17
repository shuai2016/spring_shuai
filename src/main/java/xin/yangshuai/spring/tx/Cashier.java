package xin.yangshuai.spring.tx;

import java.util.List;

/**
 * Cashier
 *
 * @author shuai
 * @date 2018/12/17
 */
public interface Cashier {
	void checkout(String username, List<String> isbns);
}
