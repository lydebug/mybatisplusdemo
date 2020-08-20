package fudan.leon.mpdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import fudan.leon.mpdemo.dao.UserMapper;
import fudan.leon.mpdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: liyang27
 * @Date: 2020/6/20 19:21
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUpdate {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateById() {
        User user = new User();
        user.setUserId(1L);
        user.setRealName("liyang");
        int rows = userMapper.updateById(user);
        System.out.println("rows = " + rows);
    }

    @Test
    public void updateByWrapper() {
        User user = new User();
        user.setAge(26);
        user.setRealName("liyang27");
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("age", 40).eq("name", "liyang");
        int rows = userMapper.update(user, userUpdateWrapper);
        System.out.println("rows = " + rows);
    }

    @Test
    public void updateByWrapper2() {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("age", 26)
                .eq("name", "liyang27")
                .set("age", 27);
        int rows = userMapper.update(null, userUpdateWrapper);
        System.out.println("rows = " + rows);
    }

    @Test
    public void updateByWrapperlambda() {
        LambdaUpdateWrapper<User> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.eq(User::getRealName, "liyang27")
                .set(User::getAge, 25);
        int rows = userMapper.update(null, userUpdateWrapper);
        System.out.println("rows = " + rows);
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getRealName,"liyang27");
//        User user=userMapper.selectOne(queryWrapper);
//        System.out.println("user = " + user);
    }

    @Test
    public void updateByWrapperChain() {
        boolean rows = new LambdaUpdateChainWrapper<>(userMapper).eq(User::getRealName, "leon")
                .set(User::getAge, 21).update();
        System.out.println("rows = " + rows);
    }
}
