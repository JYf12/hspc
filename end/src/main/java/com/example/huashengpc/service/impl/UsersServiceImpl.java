package com.example.huashengpc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huashengpc.mapper.UsersMapper;
import com.example.huashengpc.models.domain.Users;
import com.example.huashengpc.service.UsersService;
import org.springframework.stereotype.Service;

/**
* @author 70724
* @description 针对表【users】的数据库操作Service实现
* @createDate 2023-03-29 15:39:27
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {

}




