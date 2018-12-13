package xin.yangshuai.spring.aop;

/**
 * Main
 *
 * @author shuai
 * @date 2018/12/13
 */
public class Main {
    public static void main(String[] args) {

        ArithmeticCalculator proxy = new ArithmeticCalculatorLoggingProxy(new ArithmeticCalculatorImpl()).getLoggingProxy();
        System.out.println(proxy.add(1, 2));

    }
}
