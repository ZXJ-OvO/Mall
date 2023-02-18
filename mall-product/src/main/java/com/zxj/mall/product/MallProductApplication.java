package com.zxj.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 *
 * 2、逻辑删除
 *  1）、配置全局的逻辑删除规则（省略）
 *  2）、配置逻辑删除的组件Bean（省略）
 *  3）、给Bean加上逻辑删除注解@TableLogic
 *
 * 3、JSR303
 *   1）、给Bean添加校验注解:javax.validation.constraints，并定义自己的message提示
 *   2)、开启校验功能@Valid
 *      效果：校验错误以后会有默认的响应；
 *   3）、给校验的bean后紧跟一个BindingResult，就可以获取到校验的结果
 *   4）、分组校验（多场景的复杂校验）
 *         1)、	@NotBlank(message = "品牌名必须提交",groups = {AddGroup.class,UpdateGroup.class})
 *          给校验注解标注什么情况需要进行校验
 *         2）、@Validated({AddGroup.class})
 *         3)、默认没有指定分组的校验注解@NotBlank，在分组校验情况@Validated({AddGroup.class})下不生效，只会在@Validated生效；
 *
 *   5）、自定义校验
 *      1）、编写一个自定义的校验注解
 *      2）、编写一个自定义的校验器 ConstraintValidator
 *      3）、关联自定义的校验器和自定义的校验注解
 *      @Documented
 *      @Constraint(validatedBy = { ListValueConstraintValidator.class【可以指定多个不同的校验器，适配不同类型的校验】 })
 *      @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
 *      @Retention(RUNTIME)
 * public @interface ListValue {
 *
 * 4、统一的异常处理
 * @ControllerAdvice
 *  1）、编写异常处理类，使用@ControllerAdvice。
 *  2）、使用@ExceptionHandler标注方法可以处理的异常。
 */
@SpringBootApplication  // 配置为启动类
@MapperScan("com.zxj.mall.product.dao")  // 指定要变成实现类的接口所在的dao包，然后包下面的所有接口在编译之后都会生成相应的实现类
@EnableDiscoveryClient  // 开启本模块在Nacos上的服务注册与发现功能
public class MallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProductApplication.class, args);
    }
/*
    JavaEE项目常见包结构说明：
    pojo：Plain Ordinary Java Object
        简单的无规则的可转化的对象，是以下所有对象类型的统称
    domain：
        domain是范围，用来划分作用域，例如常用来存放dao，则其下全是dao文件
    Entity：Plain Ordinary Java Object
        与数据库表中的字段一一对应，用于接收表中的数据对象。xxxEntity用于实体类
    DTO ：Data Transfer Object
        数据传输对象。接口之间传输时的数据封装
    DAO：Data Access Object
        数据访问对象（实际上存放各种dao接口）。是对数据的访问而不是对数据库的访问，数据库访问应该是连接对象
    DO：Data Object
        用于封装DAO中的对象，通过DAO向上传输数据源对象。例如xxDO封装联表查询的对象
    VO：Value Object
        显示层值对象。xxxVO通常用于封装后端给前端返回（DTO）的出参对象，同时入参可以封装为xxxParam
    PO：Persistent Object
        持久层对象，理解为数据库中的一条记录，即将一条数据封装
    BO：Business Object
        业务对象，将业务逻辑封装成了对象，形象的描述为一个对象的行为和操作

    Controller：代表控制层
    View：代表视图层

    SPU：标准产品单位，商品信息聚合的最小单位，即具体到单个商品：YOGA 14s
    SKU：库存量单位，物理上不可分割的最小存货单元，即具体的属性：容量，尺寸
 */
}
