package com.itheima.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mapper.UserMapper;
import com.itheima.po.User;
import com.itheima.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p></p>
 *
 * @Description:
 */
@SpringBootTest
public class MPTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testQueryById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /*
     * 要求：添加名为小明的user数据
     * sql语句：insert into tb_user(xx,xx,xx,...) values (xx,xx,xx,....)
     * */
    @Test
    public void testAddUesrById() {

        // 使用类的对象来构建Uesr
        // User user = new User();
        // user.setUserName("小明");
        // user.setAge(18);
        // user.setPassword("8384250");

        // 使用设计模式来构建类对象
        User user = User.builder().userName("小明")
                .age(18)
                .password("123456")
                .build();


        // 影响行数
        // mp对于对象创建时，会讲对象中属性不为null数据进行添加
        int result = userMapper.insert(user);

        // mp会对添加对象里的id进行回填
        System.out.println("uesrid:"+user.getId());


        System.out.println("result:"+result);

    }

    /*
     * 要求：删除id为 16 的用户
     * sql语句：DELETE FROM tb_user WHERE id=?
     * */
    @Test
    public void testDelUesrById() {

        int result = userMapper.deleteById(16L);
        System.out.println("result:"+result);

    }

    /*
     * 要求：删除id为 15、14 的用户
     * sql语句：DELETE FROM tb_user WHERE id IN ( ? , ? )
     * */
    @Test
    public void testDelUesrByIds() {

        List<Long> ids = Arrays.asList(14L, 15L);

        int result = userMapper.deleteBatchIds(ids);
        System.out.println("result:"+result);

    }

    /*
     * 要求：删除user_name为王八衰 age 为22 的用户
     * sql语句：DELETE FROM tb_user WHERE user_name = ? AND age = ?
     * */
    @Test
    public void testDelUesrByCondition() {

        HashMap<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("user_name", "王八衰");
        conditionMap.put("age", 22);

        int result = userMapper.deleteByMap(conditionMap);
        System.out.println("result:"+result);
    }

    /*
     * 修改用户id为 4 的username修改为 李催
     * sql语句：UPDATE tb_user SET user_name=? WHERE id=?
     */
    @Test
    public void testUpdate(){

        User user = User.builder().id(4L)
                .userName("李催")
                .build();

        int result = userMapper.updateById(user);
        System.out.println("result:"+result);
    }

    /*
     * 要求：分页查询user数据，每页5条数据,查询第一页数据
     * sql语句：SELECT * FROM tb_user LIMIT 0,5
     * sql语句：SELECT * FROM tb_user LIMIT 5 （简写形式）
     */
    @Test
    public void testPage(){

        Page<User> page = new Page<>(1,5);

        Page<User> resultPage = userMapper.selectPage(page, Wrappers.emptyWrapper());

        // 获得当前页的集合数据
        List<User> records = resultPage.getRecords();

        // 获得数据库中的总条数
        long total = resultPage.getTotal();

        System.out.println(records);
        System.out.println(total);

    }


    /*
     * 要求：查询用户中姓名包含"伤"，密码为"123456",且年龄为19或者25或者29，查询结果按照年龄降序排序；
     * sql语句：SELECT * FROM tb_user WHERE (user_name LIKE ? AND password = ? AND age IN (?,?,?)) ORDER BY age DESC
     */
    @Test
    public void testConditions1Query(){

        // QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // queryWrapper.like("user_name","伤");
        // queryWrapper.eq("password", "123456");
        // queryWrapper.in("age", 19, 25, 29);
        // queryWrapper.orderByDesc("age");

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getUserName,"伤");
        queryWrapper.eq(User::getPassword, "123456");
        queryWrapper.in(User::getAge, 19, 25, 29);
        queryWrapper.orderByDesc(User::getAge);


        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }


    /*
     * 要求：查询用户中姓名包含"伤"，密码为"123456",且年龄为19或者25或者29，查询结果按照年龄降序排序；
     * sql语句：SELECT user_name,age FROM tb_user WHERE (user_name LIKE ? AND password = ? AND age IN (?,?,?)) ORDER BY age DESC
     */
    @Test
    public void testConditions2Query(){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getUserName,"伤");
        queryWrapper.eq(User::getPassword, "123456");
        queryWrapper.in(User::getAge, 19, 25, 29);
        queryWrapper.orderByDesc(User::getAge);

        queryWrapper.select(User::getUserName,User::getAge);

        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);

    }

    /*
     * 要求：查询用户密码为"123456"的数据，并且要求每页显示5条，查询第二页的数据；
     * sql语句：SELECT user_name,age FROM tb_user WHERE  password = ? limit 5,5
     */
    @Test
    public void testConditionsPageQuery(){

        Page<User> userPage = new Page<>(2,5);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPassword, "123456");


        Page<User> resultPage = userMapper.selectPage(userPage, queryWrapper);

        // 获得当前页的集合数据
        List<User> records = resultPage.getRecords();

        // 获得数据库中的总条数
        long total = resultPage.getTotal();

        System.out.println(records);
        System.out.println(total);
    }


    /*
     * 要求：查询用户密码为"123456",或者 age 为 20、21、22
     * sql语句：SELECT user_name,age FROM tb_user WHERE  password = ? or age IN (?,?,?)
     */
    @Test
    public void testConditionsOrQuery(){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPassword, "123456")
                .or()
                .in(User::getAge, 20, 21, 22)
        ;

        List<User> list = userMapper.selectList(queryWrapper);
        System.out.println(list);

    }

    @Autowired
    private UserService userService;

    /*
     * 要求：查询用户密码为"123456",或者 age 为 20、21、22
     * sql语句：SELECT user_name,age FROM tb_user WHERE  password = ? or age IN (?,?,?)
     */
    @Test
    public void testConditions2Service(){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPassword, "123456")
                .or()
                .in(User::getAge, 20, 21, 22)
        ;

        List<User> list = userService.list(queryWrapper);
        System.out.println(list);

    }

}


