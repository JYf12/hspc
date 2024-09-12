package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Users;
import generator.service.UsersService;
import generator.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author 70724
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-06-03 20:33:24
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




