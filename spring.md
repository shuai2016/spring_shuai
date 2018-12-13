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

   1. 基于注解配置AOP
      1. 前置通知
      2. 后置通知
      3. 返回通知
      4. 异常通知
      5. 环绕通知
      6. 切面优先级
      7. 重用切点表达式
   2. 基于配置文件配置AOP

4. Spring使用JdbcTemplate和JdbcDaoSupport

5. Spring使用NamedParameterJdbcTemplate

6. Spring事务

   1. 声明式事务
   2. 事务的传播行为
   3. 事务的其他属性
      1. 基于注解配置事务
         1. 隔离级别
         2. 回滚
         3. 只读
         4. 过期
      2. 基于配置文件配置事务

7. Spring整合Hibernate

   1. 基本配置
   2. 不使用Hibernate配置文件

8. 在WEB中使用Spring的基本思路

9. Spring集成Struts2