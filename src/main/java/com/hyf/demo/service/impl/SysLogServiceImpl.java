package com.hyf.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.SysLog;
import com.hyf.demo.service.SysLogService;
import com.hyf.demo.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 胡杨烽
* @description 针对表【sys_log(日志表)】的数据库操作Service实现
* @createDate 2023-07-06 22:18:41
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService{

}




