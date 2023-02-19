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
│  ├─AttrAttrgroupRelationDao：属性&属性分组关联
│  ├─AttrDao：商品属性
│  ├─AttrGroupDao：属性分组
│  ├─BrandDao：品牌
│  ├─CategoryBrandRelationDao：品牌分类关联
│  ├─CategoryDao：商品三级分类
│  ├─CommentReplayDao：商品评价回复关系
│  ├─ProductAttrValueDao：spu属性值
│  ├─SkuImagesDao：sku图片
│  ├─SkuInfoDao：sku信息
│  ├─SkuSaleAttrValueDao：sku销售属性&值
│  ├─SpuCommentDao：商品评价
│  ├─SpuImagesDao：spu图片
│  ├─SpuInfoDao：spu信息
│  ├─SpuInfoDescDao：spu信息介绍
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
