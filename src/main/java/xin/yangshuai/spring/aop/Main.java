package xin.yangshuai.spring.aop;

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

        ApplicationContext context = new ClassPathXmlApplicationContext("xin/yangshuai/spring/aop/applicationContext.xml");
        ArithmeticCalculator bean = context.getBean(ArithmeticCalculator.class);
        int add = bean.add(1, 2);
        System.out.println("result : " + add);

    }
}
