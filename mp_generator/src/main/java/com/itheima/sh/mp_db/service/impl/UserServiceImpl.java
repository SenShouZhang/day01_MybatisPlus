package com.itheima.sh.mp_db.service.impl;

import com.itheima.sh.mp_db.pojo.User;
import com.itheima.sh.mp_db.mapper.UserMapper;
import com.itheima.sh.mp_db.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itheima
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
