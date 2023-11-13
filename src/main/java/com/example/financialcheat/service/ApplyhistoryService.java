package com.example.financialcheat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialcheat.model.entity.Applyhistory;
import com.example.financialcheat.model.vo.ApplyVo.applyListVo;

import java.util.List;

/**
* @author 86185
* @description 针对表【ApplyHistory】的数据库操作Service
* @createDate 2023-11-06 21:51:10
*/
public interface ApplyhistoryService extends IService<Applyhistory> {


    /**
     * 添加团队申请
     * @param projectId
     * @param userId
     * @param userName
     */
    Boolean addApplyToProject(long projectId,long userId,String userName);


    /**
     * 获取某个项目下的所有申请
     * @param projectId
     * @return
     */
    List<applyListVo> applyList(long projectId);

    /**
     * 同意或者拒接申请
     * @param projectId
     * @param applyId
     * @param isApply
     * @return
     */
    Boolean passOrRejectApply(long projectId, long applyId, int isApply);
}
