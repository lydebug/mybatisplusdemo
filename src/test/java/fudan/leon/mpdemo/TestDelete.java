package fudan.leon.mpdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import fudan.leon.mpdemo.dao.UserMapper;
import fudan.leon.mpdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liyang27
 * @Date: 2020/6/21 12:22
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDelete {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        int rows = userMapper.deleteById(1274178931280932866L);
        System.out.println("rows = " + rows);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", 1274174512501895170L);
        int rows = userMapper.deleteByMap(map);
        System.out.println("rows = " + rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAge, 35);
        int rows = userMapper.delete(lambdaQueryWrapper);
        System.out.println("rows = " + rows);
    }


}
