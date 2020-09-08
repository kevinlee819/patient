package com.myzy.patient.patient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myzy.patient.patient.entity.PresentIllnessHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PresentIllnessHistory)表数据库访问层
 *
 * @author leekejin
 * @since 2020-08-04 17:52:08
 */
@Mapper
public interface PresentIllnessHistoryMapper extends BaseMapper<PresentIllnessHistory> {
    /**
     * 获取现病史
     * @param patientId 患者id
     * @return 现病史列表
     */
    List<PresentIllnessHistory> selectByPatientId(@Param("patientId") Integer patientId);

}