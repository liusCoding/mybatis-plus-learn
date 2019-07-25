package com.mp.mybatisplus.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: mybatis-plus->User
 * @description: User实体类
 * @author: liushuai
 * @create: 2019-07-24 17:16
 **/

@Data
//@TableName("xxx")  实体对应的数据的表
public class User {

    //主键ID   实体id和数据库表id不一致  可以使用@TableId注解
    private Long id;
    //  实体字段与数据库表字段不一致  可以使用@TableField("XXX")
    //姓名
    private String name;
    //年龄
    private Integer age;
    //邮箱
    private String email;
    //直属上级
    private Long  managerId;
    //创建时间
    private LocalDateTime createTime;

    //备注
    private transient String remark;

}
