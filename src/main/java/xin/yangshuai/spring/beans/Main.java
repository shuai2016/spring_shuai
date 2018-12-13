package xin.yangshuai.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main
 *
 * @author shuai
 * @date 2018/12/13
 */
public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Hello hello = (Hello) context.getBean("hello");
		System.out.println(hello);
		Address address = (Address) context.getBean("address");
		System.out.println(address);
        ((ClassPathXmlApplicationContext) context).close();
	}
}
