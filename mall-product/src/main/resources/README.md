**项目结构**

```
mall-product：商品服务
├─config
│  ├─MyBatisConfig：MybatisPlus配置类：分页插件
├─controller
│  ├─AttrAttrgroupRelationController
│  ├─AttrController
│  ├─AttrGroupController
│  ├─BrandController
│  ├─CategoryBrandRelationController
│  ├─CategoryController
│  ├─CommentReplayController
│  ├─ProductAttrValueController
│  ├─SkuImagesController
│  ├─SkuInfoController
│  ├─SkuSaleAttrValueController
│  ├─SpuCommentController
│  ├─SpuImagesController
│  ├─SpuInfoController
│  ├─SpuInfoDescController
├─dao
│  ├─AttrAttrgroupRelationDao
│  ├─AttrDao
│  ├─AttrGroupDao
│  ├─BrandDao
│  ├─CategoryBrandRelationDao
│  ├─CategoryDao
│  ├─CommentReplayDao
│  ├─ProductAttrValueDao
│  ├─SkuImagesDao
│  ├─SkuInfoDao
│  ├─SkuSaleAttrValueDao
│  ├─SpuCommentDao
│  ├─SpuImagesDao
│  ├─SpuInfoDao
│  ├─SpuInfoDescDao
├─entity
│  ├─AttrAttrgroupRelationEntity：属性关联属性分组
│  ├─AttrEntity：属性
│  ├─AttrGroupEntity：属性分组
│  ├─BrandEntity：品牌
│  ├─CategoryBrandRelationEntity：品牌关联分类
│  ├─CategoryEntity：商品三级分类
│  ├─CommentReplayEntity：商品评价回复关系
│  ├─ProductAttrValueEntity：SPU属性值
│  ├─SkuImagesEntity：SKU图片
│  ├─SkuInfoEntity：SKU信息
│  ├─SkuSaleAttrValueEntity：SKU销售属性值
│  ├─SpuCommentEntity：商品评价
│  ├─SpuImagesEntity：SPU图片
│  ├─SpuInfoEntity：SPU信息
│  ├─SpuInfoDescEntity：SPU信息介绍
│ 
├─exception
│  ├─MallExceptionControllerAdvice：集中处理controller包下的所有异常
├──service
│  ├─AttrAttrgroupRelationServiceImpl
│  ├─AttrServiceImpl
│  ├─AttrGroupServiceImpl
│  ├─BrandServiceImpl
│  ├─CategoryBrandRelationServiceImpl
│  ├─CategoryServiceImpl
│  ├─CommentReplayServiceImpl
│  ├─ProductAttrValueServiceImpl
│  ├─SkuImagesServiceImpl
│  ├─SkuInfoServiceImpl
│  ├─SkuSaleAttrValueServiceImpl
│  ├─SpuCommentServiceImpl
│  ├─SpuImagesServiceImpl
│  ├─SpuInfoServiceImpl
│  ├─SpuInfoDescServiceImpl
├──vo
│  ├─AttrRespVo：用于响应的值对象
│  ├─AttrVo：封装后端传给前端的商品属性
├──MallProductApplication：启动类

```
