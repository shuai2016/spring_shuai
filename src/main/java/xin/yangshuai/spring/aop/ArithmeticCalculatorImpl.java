package xin.yangshuai.spring.aop;

import org.springframework.stereotype.Component;

/**
 * ArithmeticCalculatorImpl
 *
 * @author shuai
 * @date 2018/12/13
 */
@Component
public class ArithmeticCalculatorImpl implements ArithmeticCalculator {
    @Override
    public int add(int i, int j) {
        int result  = i + j;
        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result  = i - j;
        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result  = i * j;
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result  = i / j;
        return result;
    }
}
