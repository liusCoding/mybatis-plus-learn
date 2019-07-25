#通用Mapper

## 新增（create）

- 新增方法
- 常用注解
    @TableName("XXX")
    @TableId
    @TableField("XXX")
    
- 排除非表字段的三种方式
>- 1.使用transient关键字
>- 2.使用static关键字
>- 3.使用注解@TableField(exsit=false)

