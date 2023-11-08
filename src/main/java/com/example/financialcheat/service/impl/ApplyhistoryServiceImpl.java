package com.example.financialcheat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.model.entity.Applyhistory;
import com.example.financialcheat.mapper.ApplyhistoryMapper;
import com.example.financialcheat.model.vo.ApplyVo.applyListVo;
import com.example.financialcheat.service.ApplyhistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<applyListVo> applyList(long projectId) {
        //定义输出格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<applyListVo> applyListVoList = new ArrayList<>();
        QueryWrapper<Applyhistory> applyhistoryQueryWrapper = new QueryWrapper<>();
        applyhistoryQueryWrapper.eq("projectId",projectId);
        List<Applyhistory> applyhistoryList = applyhistoryMapper.selectList(applyhistoryQueryWrapper);
        if(applyhistoryList == null){
            return applyListVoList;
        }
        applyhistoryList.forEach(applyhistory -> {
            applyListVo applyListVo = new applyListVo();
            applyListVo.setApplyId(applyhistory.getId());
            applyListVo.setUserName(applyhistory.getUserName());
            applyListVo.setStatus(applyhistory.getStatus());
            applyListVo.setTime(outputFormat.format(applyhistory.getCreateTime()));
            applyListVoList.add(applyListVo);
        });
        return applyListVoList;
    }

    @Override
    public Boolean passOrRejectApply(long projectId, long applyId, int isApply) {
        QueryWrapper<Applyhistory> applyhistoryQueryWrapper = new QueryWrapper<>();
        applyhistoryQueryWrapper.eq("id",applyId);
        applyhistoryQueryWrapper.eq("projectId",projectId);
        Applyhistory applyhistory = applyhistoryMapper.selectOne(applyhistoryQueryWrapper);
        if(applyhistory==null){
            return false;
        }
        applyhistory.setStatus(isApply);
        int i = applyhistoryMapper.updateById(applyhistory);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }
}




