package com.zxj.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication  // 配置为启动类
@MapperScan("com.zxj.mall.product.dao")  // 指定要变成实现类的接口所在的dao包，然后包下面的所有接口在编译之后都会生成相应的实现类
@EnableDiscoveryClient  // 开启本模块在Nacos上的服务注册与发现功能
public class MallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProductApplication.class, args);
    }
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
    VO：Value Object（或称View Object）
        显示层值对象。xxxVO通常用于封装后端给前端返回（DTO）的出参对象，同时入参可以封装为xxxParam
    PO：Persistent Object
        持久层对象，理解为数据库中的一条记录，即将一条数据封装，不包含任何对数据库的操作
    BO：Business Object
        业务对象，将业务逻辑封装成了对象，形象的描述为一个对象的行为和操作
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    SPU：标准产品单位，商品信息聚合的最小单位，即具体到单个商品：YOGA 14s
    SKU：库存量单位，物理上不可分割的最小存货单元，即具体的属性：容量，尺寸
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    校验注解
    1、值校验：
    @Null：被注解的元素必须为null
    @NotNull：不能为 null，但可以为 empty，
            一般用在 Integer 类型的基本数据类型的非空校验上，
            其标注的字段可以使用 @size、@Max、@Min 对字段数值进行大小的控制
    @NotEmpty：
            不能为 null，且长度必须大于 0，一般用在集合类上或者数组上
    @NotBlank：
            只能作用在接收String类型上，不能为null，而且调用trim()后，长度必须大于0
            即：必须有实际字符
    @AccessTrue：被注解的元素必须为true，即必须为布尔值
    @AccessFalse：被注解的元素必须为false，即必须为布尔值

    2、范围校验：
    @Min：被注解的元素必须大于等于属性值value，类型为Integer、Long、Float、Double
    @Max：被注解的元素必须小于等于属性值value，类型为Integer、Long、Float、Double
    @DecimalMin：被注解的元素必须大于等于属性值value，类型为BigDecimal
    @DecimalMax：被注解的元素必须小于等于属性值value，类型为BigDecimal
    @Range：验证注解的元素值在最大值和最小值之间，并且类型为BigDecimal、BigInteger、CharSequence、byte、short、int、long
    @Past：被注解的元素必须是一个过去的时间，类型为java.util.Date
    @Feature：被注解的元素必须是一个未来的时间，类型为java.util.Date

    3、长度校验
    @Size：被注解的元素必须在指定的长度之内，并且类型为String、Array、List、Map
    @Length：被注解的元素处于属性值min和max之间，并且类型为String

    4、格式校验
    @Email：被注解的元素必须是邮箱格式看，也可以使用regexp和flag属性自定义邮箱格式
    @Pattern：被注解的元素必须符合指定的正则表达式，并且类型为String
    @Digits：验证被注解元素的整数位上限和小数位上限，类型为Float、Double、BigDecimal
    @URL:被注解的元素必须是一串合法的url地址
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    序列化
    实体类对象实现Serializable的必要性：内存中的对象需要被持久化时需要序列化，则需要实现该接口，可在底层帮助序列化和反序列化
    序列化需要指定private static final long serialVersionUID = 1L，目的是在序列化和反序列化时做出校验，相同则校验通过，不同抛出序列化校验异常
    @JsonInclude(JsonInclude.Include.NON_EMPTY)用于指定被注解的属性被序列化时触发的策略
    ALWAYS：总是序列化所有属性
    NON_NULL：序列化非NULL
    NON_ABSENT：序列化非NULL或者引用类型缺省值
    NON_EMPTY：序列化非EMPTY
    NON_DEFAULT：仅包含与POJO属性默认值不同的值
    CUSTOM：不常用
    USE_DEFAULTS：不常用
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    Mybatis-plus
    mp为dao提供了大量的CRUD方法，只需要让dao接口继承BaseMapper<xxxEntity>
    mp同样为service提供了大量的常用方法，只需要让service接口继承IService<xxxEntity>
        然后让serviceImpl接口实现先继承ServiceImpl<xxxDao, xxxEntity>然后再去实现service接口
    查看ServiceImpl<xxxDao, xxxEntity>源码：
    public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T> {

        @Autowired
        protected M baseMapper;

        public ServiceImpl() {
        }

        public M getBaseMapper() {
            return this.baseMapper;
    }

    由此可知，baseMapper即就是xxxDao，使得不需要在xxxServiceImpl中使用对应自动装配的dao，而改为直接使用baseMapper去调用函数
    （此处的baseMapper不同于dao中继承的baseMapper<xxxEntity>  ）
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    JSR303校验

     1.给entity中需要校验的字段添加校验注解（参考上方的校验注解），按需添加自定义message提示
     2.在需要校验的Controller的参数列表里添加注解@Valid开启校验功能。效果：校验错误以后会有默认的响应；
        发生异常时返回的json不是我们的R对象，而是mvc的内置类，使用@Valid解决
     3.给校验的bean后紧跟一个BindingResult，就可以获取前一个对象的校验结果。BindingResult有一个方法result.hasErrors可以用来作为条件判断是否有error
        public R save(@Valid @RequestBody BrandEntity brand,BindingResult result)

    分组校验
     1.@NotBlank(message = "品牌名必须提交",groups = {AddGroup.class,UpdateGroup.class})给校验注解标注什么情况需要进行校验
     2.@Validated({AddGroup.class})
     3.默认没有指定分组的校验注解@NotBlank，在分组校验情况@Validated({AddGroup.class})下不生效，只会在@Validated生效，实际上没有指定分组的校验注解属于default分组
        此外还可以在实体类上标注@GroupSequece({A.class,B.class})指定校验顺序
        通过@GroupSequence指定验证顺序：先验证A分组，如果有错误立即返回而不会验证B分组，接着如果A分组验证通过了才去验证B分组

     自定义校验
     1.编写一个自定义的校验注解
     2.编写一个自定义的校验器 ConstraintValidator
     3.关联自定义的校验器和自定义的校验注解
     详见common-valid  如验证手机号格式，可以参考<https://blog.csdn.net/GAMEloft9/article/details/81699500>
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    @RequestBody、 @RequestParam 、 @PathVariable 、@Valid 注解的使用及区别

    - @RequestBody 主要用来接收前端传递给后端的 json 字符串中的数据(请求体中的数据)
      Get 方式无请求体，所以使用 @RequestBody 接收数据时，前端不能使用 Get 方式提交数据;而是使用 Post 方式进行提交的
      在后端的同一个接收方法里，@RequestBody 与 @RequestParam() 可以同时使用;
      一个请求，只有一个 RequestBody；一个请求，可以有多个 RequestParam
    - @RequestParam（）指定的参数可以是普通元素、 数组、集合、对象等等
      即：如果参数时放在请求体中，传入后台的话，那么后台要用 @RequestBody 才能接收到；
      如果不是放在请求体中的话，那么后台接收前台传过来的参数时，要用 @RequestParam 来接收，或则形参前什么也不写也能接收

    - @RequestParam 用于将请求参数区域的数据映射到控制层功能处理方法的参数上（读取参数并接收）
    - @RequestParam(value=“参数名”, required=“true/false”, defaultValue="")
        1、value：请求中传入参数的名称，如果不设置后台接口的 value 值，则会默认为该变量名。
        2、required：是否包含该参数，默认为 true，表示该请求路径中必须包含该参数，如果不包含就报404等错误。
        3、defaultValue：默认参数值，如果设置了该值，required=true 将失效，自动为 false,如果没有传该参数，就使用默认值。

    - @PathVariable接收请求路径中占位符的值（读取路径变量）
      @PathVariable 注解可以将 URL 中占位符参数绑定到控制器处理方法的入参中
      URL 中的 {xxx} 占位符可以通过 @PathVariable(“xxx“) 绑定到操作方法的入参中

      在Get请求中，不能使用@RequestBody。在Post请求，可以使用@RequestBody和@RequestParam，但是如果使用@RequestBody，对于参数转化的配置必须统一
      @RequestParam注解接收的参数是来自于请求头。都是用来获取请求路径url中的动态参数。也就是在url中，格式为xxx?username=123&password=456
      @RequestBody注解接收的参数则是来自于请求体中
      @RequestParam和@PathVariable 注解是用于从request中接收请求的，两个都可以接收参数，关键点不同的是@RequestParam是从request里面拿取值，而@PathVariable是从一个url模板里面来填充
      @RequestParam注解是获取静态url传入的参数
      @PathVariable是获取请求路径中的变量作为参数,需要和@RequestMapping(“item/{itemId}”)配合使用。
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————



 */
