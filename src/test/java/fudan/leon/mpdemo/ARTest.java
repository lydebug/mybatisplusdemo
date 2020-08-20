package fudan.leon.mpdemo;

import fudan.leon.mpdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @Author: liyang27
 * @Date: 2020/6/21 14:21
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {
    @Test
    public void insert() {
        User user = new User();
        user.setAge(37);
        user.setEmail("xx@wangyi.com");
        user.setCreateTime(LocalDateTime.now());
        user.setManagerId(2L);
        user.setRealName("向西");
        user.setRemark("备注");
        boolean rows = user.insert();
        System.out.println("rows = " + rows);
    }
}
