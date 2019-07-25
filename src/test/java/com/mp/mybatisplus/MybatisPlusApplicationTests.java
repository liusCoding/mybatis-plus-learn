package com.mp.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mp.mybatisplus.dao.UserMapper;
import com.mp.mybatisplus.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void testSelect() {
        List<User> users = userMapper.selectList(null);

        users.forEach(System.out::println);
    }


    @Test
    public void testInsert(){
        User user = new User();
        user.setName("hs");
        user.setAge(20);
        user.setCreateTime(LocalDateTime.now());
        user.setManagerId(1088248166370832385l);
        user.setRemark("我是备注");
        int rows = userMapper.insert(user);

        System.out.println("影响的行数："+rows);


    }

    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1153965870079303681L);
        System.out.println(user);
    }

    @Test
    public void testSelectIds(){
        List<Long> longs = Arrays.asList(1087982257332887553L, 1088248166370832385L, 1088250446457389058L);

        List<User> users = userMapper.selectBatchIds(longs);

        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){

        //相当于where name="ls" and age =20
        Map<String,Object> map = new HashMap<>();
        //Map中key 是数据库表中的字段名
       // map.put("name","liushuai");
        map.put("age",20);

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);

    }


    /*
     * 1.需求描述 名字中包含雨并且年龄小于40
     *  name like '%雨%' and  age<40
     * @return
     */
    @Test
    public void selectByWrapper1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }



    /*
     * 1.需求描述 名字中包含雨并且年龄大于等于20小于等于40并且email不为空
     *  name like '%雨%' and age between 20 and 40 and email is not null
     * @return
     */
    @Test
    public void selectByWrapper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").between("age",20,40).isNotNull("email");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /*
     * 1.需求描述 名字为王姓或者年龄
     *  name like '%雨%' and age between 20 and 40 and email is not null
     * @return
     */
    @Test
    public void selectByWrapper3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").between("age",20,40).isNotNull("email");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
