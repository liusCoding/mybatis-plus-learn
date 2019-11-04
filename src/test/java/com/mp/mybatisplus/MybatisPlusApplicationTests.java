package com.mp.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mp.mybatisplus.dao.UserMapper;
import com.mp.mybatisplus.entity.User;
import com.mysql.cj.util.StringUtils;
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
     * 2.需求描述 名字中包含雨并且年龄大于等于20小于等于40并且email不为空
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
     * 3.需求描述 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照ID升序排列
     * 
     *  name like '王%' or age>=25 order by age desc,id asc
     * @return
     */
    @Test
    public void selectByWrapper3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        QueryWrapper<User> queryMapper = queryWrapper.likeRight("name", "王")
                .or().ge("age", 25)
                .orderByDesc("age")
                .orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /*
     * 4.创建日期为2019年2月14日并且直属上级名字为王姓
     *
     * date_format(create_time,'%Y-%m-%d') and manager_id in(select id from user where name like '王%')
     *
     *
     * @return
     */
    @Test
    public void selectByWrapper4(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14")
                    .inSql("manager_id","select id from user where name like '王%'");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /*
     * 5.名字为王姓并且年龄小于40或邮箱不为空
     *
     *  name like '王%' and （age <40 or email is not null）
     *
     *
     * @return
     */
    @Test
    public void selectByWrapper5(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.likeRight("name","王").and(wq -> wq.le("age",40).or().isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /*
     * 6.名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     *
     *  name like '王%' or  (age<40 and age >20 and email is not null)
     *
     *
     * @return
     */
    @Test
    public void selectByWrapper6(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.likeRight("name","王")
                .or(wq -> wq.lt("age",40).gt("age",20).isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /*
     * 7.(年龄小于40或邮箱不为空) 并且名字为王姓
     * (age <40 or email is not null) and name like '王%'
     *
     *
     *
     * @return
     */
    @Test
    public void selectByWrapper7(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.nested(wq -> wq.lt("age",40).or().isNotNull("email")).likeRight("name","王");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /*
     * 8.年龄为30、31、34、35
     *
     * age in(30,31,34,35)
     *
     *
     *
     * @return
     */
    @Test
    public void selectByWrapper8(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("age",Arrays.asList(30,31,34,35));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /*
     * 9.只返回满足条件的其中一条语句即可
     *
     *
     *
     * limit 1
     *
     * @return
     */
    @Test
    public void selectByWrapper9(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /*
     * 10.需求描述 名字中包含雨并且年龄小于40  //只返回字段id和name
     *  name like '%雨%' and  age<40
     * @return
     */
    @Test
    public void selectByWrapperSupper1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name").like("name","雨").lt("age",40);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /*
     * 11.需求描述 名字中包含雨并且年龄小于40  //不需要 create_time 和 manager_id字段
     *  name like '%雨%' and  age<40
     * @return
     */
    @Test
    public void selectByWrapperSupper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40)
                .select(User.class,info -> !info.getColumn().equals("create_time")&&!info.getColumn().equals("manager_id"));


        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void testCondition(){
        String name = "l";
        String email ="";

        condition(name,email);
    }


    private void condition(String name,String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //原来写法
//        if(StringUtils.isNullOrEmpty(name)){
//            queryWrapper.like("name",name);
//        }
//        if(StringUtils.isNullOrEmpty(email)){
//            queryWrapper.like("email",email);
//        }

        queryWrapper.like(!StringUtils.isNullOrEmpty(name),"name",name)
                .like(!StringUtils.isNullOrEmpty(email),"email", email);

        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(System.out::println);

    }


    // 实体作为条件构造器构造方法的参数
    @Test
    public void selectByWrapperEntity(){

        User whereUser = new User();
        whereUser.setName("liushuai");
        whereUser.setAge(20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
//        queryWrapper.like("name","雨").lt("age",40)
//                .select(User.class,info -> !info.getColumn().equals("create_time")&&!info.getColumn().equals("manager_id"));


        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    // 实体作为条件构造器构造方法的参数  字段不等值
    //@TableField(condition = SqlCondition.LIKE)
    @Test
    public void selectByWrapperEntity1(){

        User whereUser = new User();
        whereUser.setName("liushuai");
        whereUser.setAge(20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
//        queryWrapper.like("name","雨").lt("age",40)
//                .select(User.class,info -> !info.getColumn().equals("create_time")&&!info.getColumn().equals("manager_id"));


        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void selectByWrapperALLEq1(){


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String,Object> parms = new HashMap<>();
        parms.put("name","王天风");
        parms.put("age",null);

        queryWrapper.allEq(parms,false);//值为false,会忽略字段为null的
        //queryWrapper.allEq((k,v)->!k.equals(""name),params);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    //测试selectMaps
    @Test
    public void selectByWrapperMaps(){


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();


        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
