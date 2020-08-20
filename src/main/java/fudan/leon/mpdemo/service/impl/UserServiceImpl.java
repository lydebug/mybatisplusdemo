package fudan.leon.mpdemo.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fudan.leon.mpdemo.dao.UserMapper;
import fudan.leon.mpdemo.entity.User;
import fudan.leon.mpdemo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: liyang27
 * @Date: 2020/6/21 16:12
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
