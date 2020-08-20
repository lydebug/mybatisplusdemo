package fudan.leon.mpdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fudan.leon.mpdemo.dao.UserMapper;
import fudan.leon.mpdemo.entity.User;
import org.junit.Assert;
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

/**
 * @Author: liyang27
 * @Date: 2020/6/20 10:14
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMP {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        List<User> users = userMapper.selectList(null);
        Assert.assertEquals(5, users.size());
        users.forEach(System.out::println);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setAge(25);
        user.setEmail("xd@wangyi.com");
        user.setCreateTime(LocalDateTime.now());
        user.setManagerId(2L);
        user.setRealName("向东");
        user.setRemark("备注");
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
    }

    @Test
    public void selectBatchIds() {
        List<User> list = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        list.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "leon");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper() {
        /**
         *
         * 功能描述: name like %雨% and age<40;
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:49
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper2() {
        /**
         *
         * 功能描述: name like %雨% and age between 20 and 40 and email is not null;
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:49
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").between("age", 20, 40).isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper3() {
        /**
         *
         * 功能描述: name like '王%' or age>=40 order by age desc,user_id asc;
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:50
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").or().ge("age", 25)
                .orderByDesc("age").orderByAsc("user_id");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper4() {
        /**
         *
         * 功能描述: 创建日期为2019年2月14日并且直属上级为名字为王姓
         * date_format(create_time,'%Y-%m-%d') and manager_id in (select user_id from mp_user where name like '王%）
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:50
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-11")
                .inSql("manager_id", "select user_id from mp_user where name like '王%'");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper5() {
        /**
         *
         * 功能描述: 名字为王姓并且（年龄小于40或邮箱不为空）
         * name like '王%' and (age<40 or email is not null)
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:50
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").
                and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper6() {
        /**
         *
         * 功能描述: 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
         * name like '王%' or (age between 20 and 40 and email is not null)
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:50
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王")
                .or(wq -> wq.lt("age", 40)
                        .gt("age", 20)
                        .isNotNull("email"));
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper7() {
        /**
         *
         * 功能描述: 名字为王姓并且（年龄小于40或邮箱不为空）
         * name like '王%' and (age < 40 or email is not null)
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:50
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        /*queryWrapper.likeRight("name","王")
                .and(wq->wq.lt("age",40)
                        .or()
                        .isNotNull("email"));*/
        queryWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper8() {
        /**
         *
         * 功能描述: age为30,31,34,35中1条
         * age in (30,31,34,35) limit 1
         *
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:50
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35))
                .last("limit 1");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper9() {
        /**
         *
         * 功能描述: name like %雨% and age<40;
         *select  id ,name from mp_user where name like %雨% and age<40;
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:49
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id", "name")
                .like("name", "雨").lt("age", 40);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper10() {
        /**
         *
         * 功能描述:
         *      select  id ,name,age,email from mp_user where name like %雨% and age<40;
         * @param: []
         * @return: void
         * @auther: liyang27
         * @date: 2020/6/20 11:49
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(User.class, info -> !info.getColumn().equals("create_time") &&
                !info.getColumn().equals("manager_id"))
                .like("name", "雨").lt("age", 40);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByEntity() {
        User whereUser = new User();
        whereUser.setRealName("leon");
        whereUser.setAge(30);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByAllEq() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "leon");
        map.put("age", null);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq((k, v) -> !k.equals("name"), map);
//        queryWrapper.allEq((k,v)->v!=null,map);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id", "name").like("name", "雨")
                .lt("age", 40);

        List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperMaps2() {
        /**
         * 按照直属上级分组，查询每组的平均年龄，最大年龄，最小年龄。并且只取年龄总和小于500的分组
         * select avg(age) avg_age,min(age) min_age,max(age) max_age from mp_user
         * group by manager_id
         * having sum(age)<500;
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id")
                .having("sum(age)<{0}", 500);

        List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperObjs() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id", "name")
                .like("name", "雨")
                .lt("age", 40);

        List<Object> list = userMapper.selectObjs(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperCount() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name", "雨")
                .lt("age", 40);

        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println("count = " + count);
    }

    @Test
    public void selectByWrapperOne() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name", "雨")
                .lt("age", 40);

        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void selectLambda() {
        //这三种都可以
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambdaQuery = new QueryWrapper<User>().lambda();
        LambdaQueryWrapper<User> lambda = Wrappers.lambdaQuery();

        lambda.like(User::getRealName, "雨")
                .lt(User::getAge, 40);
        List<User> list = userMapper.selectList(lambda);
        list.forEach(System.out::println);

    }

    @Test
    public void selectLambda2() {
        new LambdaQueryChainWrapper<User>(userMapper)
                .like(User::getRealName, "雨")
                .ge(User::getAge, 20)
                .list()
                .forEach(System.out::println);

    }

    @Test
    public void selectMy() {
        LambdaQueryWrapper<User> lambda = Wrappers.lambdaQuery();

        lambda.like(User::getRealName, "雨")
                .lt(User::getAge, 40);
        List<User> list = userMapper.selectAll(lambda);
        list.forEach(System.out::println);

    }

    @Test
    public void selectPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 26);

        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        System.out.println("总页数： " + iPage.getPages());
        System.out.println("总记录数： " + iPage.getTotal());
        List<User> users = iPage.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void selectMapPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 26);

        IPage<Map<String, Object>> page = new Page<>(1, 2);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);
        System.out.println("总页数： " + iPage.getPages());
        System.out.println("总记录数： " + iPage.getTotal());
        List<Map<String, Object>> users = iPage.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void selectUserPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 26);

        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = userMapper.selectUserPage(page, queryWrapper);
        System.out.println("总页数： " + iPage.getPages());
        System.out.println("总记录数： " + iPage.getTotal());
        List<User> users = iPage.getRecords();
        users.forEach(System.out::println);
    }


}
