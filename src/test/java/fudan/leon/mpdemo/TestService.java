package fudan.leon.mpdemo;

import fudan.leon.mpdemo.entity.User;
import fudan.leon.mpdemo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: liyang27
 * @Date: 2020/6/21 16:19
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestService {

    @Autowired
    private UserService userService;

    @Test
    public void batch() {
        User user1 = new User();
        user1.setRealName("aa");
        user1.setAge(28);
        User user2 = new User();
        user2.setRealName("bb");
        user2.setAge(29);
        boolean f = userService.saveBatch(Arrays.asList(user1, user2));
        System.out.println("f = " + f);
    }

    @Test
    public void chain() {
        userService.lambdaQuery().gt(User::getAge, 25)
                .like(User::getRealName, "雨")
                .list().forEach(System.out::println);
    }

    @Test
    public void chainupdate() {
        userService.lambdaUpdate().eq(User::getAge, 25)
                .set(User::getAge, 26)
                .update();
    }
}
