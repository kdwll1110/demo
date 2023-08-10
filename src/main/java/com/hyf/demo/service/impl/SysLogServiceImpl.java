package com.hyf.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.SysLog;
import com.hyf.demo.service.ISysLogService;
import com.hyf.demo.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements ISysLogService {

}




