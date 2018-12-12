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
   3. Spring模块