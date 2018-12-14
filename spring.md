1. Spring是什么
   1. 简述
      1. 开源框架
      2. 为简化企业级应用开发而生，使用Spring可以使用简单的JavaBean实现以前只有EJB才能实现的功能
      3. IOC（DI）和AOP容器框架
   2. 具体描述
      1. 轻量级：Spring是非侵入性的，基于Spring开发的应用中的对象不依赖于Spring的API
         1. 用Spring的时候不需要实现Spring的接口，不需要继承Spring的父类，而可以是享用Spring提供的功能。
      2. 依赖注入（DI - dependency injection、IOC）
      3. 面向切面编程（AOP - aspect oriented programming）
      4. 容器：Spring是一个容器，因为它包含并且管理应用对象的生命周期
      5. 一站式的框架：
         1. 简单的组件配置组合成一个复杂的应用
         2. 在Spring中可以使用XML和Java注解组合这些对象
         3. 在IOC和AOP的基础上可以整合各种企业应用的框架和优秀的第三方类库（实际上Spring自身也提供了展示层的SpringMVC和持久层的Spring JDBC）
            1. 整合Mybatis，Struts2，Hibernate，...
   3. Spring模块

2. IOC（DI）

   1. Hello world

   2. IOC（DI）概述

   3. 配置bean

      1. 通过工厂方法
      2. 通过FactoryBean
      3. 通过注解
      4. 泛型依赖注入

   4. 属性配置细节

   5. 自动装配

   6. Spring Bean之间关系

      1. 继承（配置上继承），Spring允许继承bean的配置，被继承的Bean称为父Bean，继承这个父Bean的Bean称为子Bean

         1. 子Bean使用parent属性表示继承，继承属性配置

            ```xml
            <bean id="address" class="xin.yangshuai.spring.beans.Address"
                  p:city="BeiJing" p:street="WuDaoKou"></bean>
            
            <bean id="address1" class="xin.yangshuai.spring.beans.Address"
                  parent="address" p:street="XiErQi"></bean>
            ```

         2. 子Bean可以覆盖父Bean的配置

         3. 父Bean可以作为Bean实例，也可以作为模板（抽象Bean）。作为模板，设置bean中属性abstract为true，这样Spring将不会实例化这个bean。

            ```xml
            <bean id="address" class="xin.yangshuai.spring.beans.Address"
                  p:city="BeiJing" p:street="WuDaoKou" abstract="true"></bean>
            ```

         4. 并不是bean的所有属性都会被继承，比如autowire，abstract

         5. 父Bean作为模板时（abstract属性为true），子Bean可以忽略父Bean的class属性，指定自己的类，而共享相同的属性配置

      2. 依赖

         1. Spring允许用户通过depends-on属性设定Bean前置依赖的Bean，前置依赖的Bean或者本Bean实例化之前创建好

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello">
                <property name="name" value="hello"/>
            </bean>
            <bean id="address" class="xin.yangshuai.spring.beans.Address"
                  p:city="BeiJing" p:street="WuDaoKou" depends-on="hello"></bean>
            ```

         2. 前置依赖多个Bean，可以通过逗号，空格的方式配送Bean的名称

   7. Spring Bean作用域

      使用bean的scope属性配置bean的作用域

      1. 单例singleton，使用同一个实例，默认，容器初始化时就创建好了bean。

         1. 配置文件方式配置

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello" scope="singleton">
                <property name="name" value="hello"/>
            </bean>
            ```

         2. 注解方式

      2. 原型prototype，每次调用创建一个新的实例，容器初始化时不创建bean，每次调用时再创建。

         1. 配置文件方式配置

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello" scope="prototype">
                <property name="name" value="hello"/>
            </bean>
            ```

         2. 注解方式

         3. 使用场景

            1. Spring与Struts2整合的时候，为Struts2的Action指定作用域类型为prototype

      3. request

      4. session

   8. Spring使用外部属性文件

   9. Spring spEL

   10. Spring 管理Bean的生命周期

      Spring IOC 容器可以管理Bean的生命周期，Spring允许在Bean声明周期的特定点执行定制的任务。

      1. Spring IOC 容器对Bean的生命周期进行管理的过程
         1. 通过构造器或工厂方法创建Bean实例

         2. 为Bean的属性设置值和对其他Bean的引用

         3. 调用Bean的初始化方法（bean的init-method属性的值对应的方法）

         4. Bean可以使用了

         5. 容器关闭时，调用Bean的销毁方法（bean的destroy-method属性的值对应的方法）

            ```xml
            <bean id="hello" class="xin.yangshuai.spring.beans.Hello" init-method="init2" destroy-method="destroy">
                <property name="name" value="hello"/>
            </bean>
            ```

      2. Bean的后置处理器

         Bean后置处理器允许在调用初始化方法前后对Bean进行额外的处理

         1. Bean后置处理器对IOC容器里的所有Bean实例逐一处理，而并非单一实例（执行契机是init-method前后，不配置init-method也执行），典型应用，检查Bean属性的正确性或根据特定的标准更改Bean的属性

         2. 对Bean后置处理器而言，需要实现`Interface BeanPostProcessor`接口，在初始化方法被调用前后，Spring将把每个Bean实例分别传递给上述接口的以下两个方法：

            1. public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
            2. public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException

         3. 将后置处理器添加到IOC容器中即可，不需要指定id，IOC容器自动识别这是一个BeanPostProcessor

            ```xml
            <bean class="xin.yangshuai.spring.beans.MyBeanPostPrpcessor"></bean>
            ```

         4. Bean的后置处理器可以产生“偷梁换柱”的效果，因为后置处理器两个方法返回值就是返回给用户的Bean

            ```java
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
               System.out.println("postProcessBeforeInitialization: " + bean + ", " + beanName);
               if("address".equals(beanName)){
                  bean = new Hello();
                  ((Hello) bean).setName("hi");
               }
               return bean;
            }
            ```

         5. 因为是处理所有Bean，所有使用时注意过滤

      3. 添加Bean后置处理器后Bean的生命周期

         1. 通过构造器或工厂方法创建Bean实例
         2. 为Bean的属性设置值和对其他Bean的引用
         3. 将Bean实例传递给Bean后置处理器的postProcessBeforeInitialization方法
         4. 调用Bean的初始化方法
         5. 将Bean实例传递给Bean后置处理器的postProcessAfterInitialization方法
         6. Bean可以使用了
         7. 容器关闭时，调用Bean的销毁方法

3. AOP

   1. 使用代理模拟AOP

      ```java
      public class ArithmeticCalculatorLoggingProxy {
      
         private ArithmeticCalculator target;
      
         public ArithmeticCalculatorLoggingProxy(ArithmeticCalculator target) {
            this.target = target;
         }
      
         public ArithmeticCalculator getLoggingProxy() {
            ArithmeticCalculator proxy = null;
      
            ClassLoader loader = target.getClass().getClassLoader();
      
            Class[] interfaces = new Class[]{ArithmeticCalculator.class};
      
            InvocationHandler h = new InvocationHandler() {
               @Override
               public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                  String methodName = method.getName();
                  System.out.println("The method " + methodName + " begin with " + Arrays.asList(args));
                  Object result = method.invoke(target, args);
                  System.out.println("The method " + methodName + " end with " + result);
                  return result;
               }
            };
            proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader, interfaces, h);
            return proxy;
         }
      }
      ```

   2. AOP简介

   3. AOP术语

   4. AspectJ

      1. Java社区里最完整最流行的AOP框架，在Spring2.0以上版本，可以使用基于AspectJ注解或基于XML配置的AOP
      2. Spring自身也有AOP框架的实现，但是AspectJ更值得推荐和使用

   5. 基于注解配置AOP

      1. 使AspectJ注解生效（修改配置文件）

         1. 设置扫描包，扫描IOC容器的Bean

            ```xml
            <context:component-scan base-package="xin.yangshuai.spring.aop"></context:component-scan>
            ```

         2. 使AspectJ注解生效

            ```xml
            <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
            ```

      2. 声明切面（创建一个类）

         ```java
         @Aspect
         @Component
         public class LoggingAspect {
         }
         ```

         1. 声明为IOC容器中的实例，使用`@Component`注解
         2. 声明为切面，使用`@Aspect`注解

      3. 前置通知

         ```java
         @Before("execution(public int xin.yangshuai.spring.aop.ArithmeticCalculator.add(int, int))")
         public void beforeMethod(JoinPoint joinPoint){
            String methodName = joinPoint.getSignature().getName();
            List<Object> args = Arrays.asList(joinPoint.getArgs());
            System.out.println("The method "+ methodName +" begins with " + args);
         }
         ```

         1. 切面类的一个方法，加入注解`@Before`表示前置通知
         2. 将切入点表达式的值作为注解值`execution(public int xin.yangshuai.spring.aop.ArithmeticCalculator.add(int, int))`

      4. 后置通知

      5. 返回通知

      6. 异常通知

      7. 环绕通知

      8. 切点表达式进一步抽象

         ```java
         @Before("execution(* xin.yangshuai.spring.aop.*.*(..))")
         ```

         1. 匹配任意修饰符，任意返回类型：*
         2. 匹配包里面的所有类：*
         3. 匹配任意方法：*
         4. 匹配任意包和子包：..
         5. 匹配任意数量和类型的参数：..

      9. JoinPoint参数

         1. 在通知方法中声明一个类型为JoinPoint的参数，然后就能访问连接细节，如方法名称和参数值。

      10. 切面优先级

         1. 使用`@Order(2)`注解指定切面的优先级，值越小优先级越高

      11. 重用切点表达式

          1. 声明切入点表达式

             ```java
             @Pointcut("execution(* xin.yangshuai.spring.aop.*.*(..))")
             public void declareJointPointExpression(){}
             ```

             1. 定义一个方法，使用`@Pointcut`注解，切入点表达式的值作为注解值，一般方法中不需要添入其它代码

          2. 引用

             ```java
             @Before("xin.yangshuai.spring.aop.LoggingAspect.declareJointPointExpression()")
             ```

             1. 通知直接使用方法名来引用当前的切入点表达式
             2. 不同类中引用使用全类名加方法名

   6. 基于配置文件配置AOP

      ```xml
      <bean id="loggingAspect" class="xin.yangshuai.spring.aop.xml.LoggingAspect"></bean>
      
      <bean id="validationAspect" class="xin.yangshuai.spring.aop.xml.ValidationAspect"></bean>
      
      <aop:config>
          <aop:pointcut id="pointcut" expression="execution(* xin.yangshuai.spring.aop..*.*(..))"></aop:pointcut>
          <aop:aspect ref="loggingAspect" order="2">
              <aop:before method="beforeMethod" pointcut-ref="pointcut"/>
          </aop:aspect>
          <aop:aspect ref="validationAspect" order="1">
              <aop:before method="validateArgs" pointcut-ref="pointcut"/>
          </aop:aspect>
      </aop:config>
      ```

4. Spring使用JdbcTemplate

   1. 简介
      1. 为了使JDBC更加易于使用，Spring在JDBC API上定义了一个抽象层，以此建立一个JDBC存取框架
      2. 作为Spring JDBC框架的核心，JDBC模板的设计目的是为不同类型的JDBC操作提供模板方法。每个模板方法都能控制整个过程，并允许覆盖过程中的特定任务。通过这种方式，可以在尽可能保留灵活性的情况下，将数据库存取的工作量降到最低

5. Spring使用JdbcDaoSupport

6. Spring使用NamedParameterJdbcTemplate

7. Spring事务

   1. 声明式事务
   2. 事务的传播行为
   3. 事务的其他属性
      1. 基于注解配置事务
         1. 隔离级别
         2. 回滚
         3. 只读
         4. 过期
      2. 基于配置文件配置事务

8. Spring整合Hibernate

   1. 基本配置
   2. 不使用Hibernate配置文件

9. 在WEB中使用Spring的基本思路

10. Spring集成Struts2