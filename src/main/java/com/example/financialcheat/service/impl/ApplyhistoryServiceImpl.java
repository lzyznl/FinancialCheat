package com.example.financialcheat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.model.entity.Applyhistory;
import com.example.financialcheat.mapper.ApplyhistoryMapper;
import com.example.financialcheat.service.ApplyhistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 86185
* @description 针对表【ApplyHistory】的数据库操作Service实现
* @createDate 2023-11-06 21:51:10
*/
@Service
public class ApplyhistoryServiceImpl extends ServiceImpl<ApplyhistoryMapper, Applyhistory>
    implements ApplyhistoryService{

    @Resource
    private ApplyhistoryMapper applyhistoryMapper;

    @Override
    public Boolean addApplyToProject(long projectId, long userId, String userName) {
        Applyhistory applyhistory = new Applyhistory();
        applyhistory.setUserId(userId);
        applyhistory.setProjectId(projectId);
        applyhistory.setUserName(userName);
        int insert = applyhistoryMapper.insert(applyhistory);
        if(insert>=1){
            return true;
        }else{
            return false;
        }
    }
}




