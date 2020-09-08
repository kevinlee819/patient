package com.myzy.patient.patient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myzy.patient.patient.entity.PastMedicalHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PastMedicalHistory)表数据库访问层
 *
 * @author leekejin
 * @since 2020-08-04 17:16:05
 */
@Mapper
public interface PastMedicalHistoryMapper extends BaseMapper<PastMedicalHistory> {
    /**
     * 获取既往史
     * @param patientId 患者id
     * @return 既往史列表
     */
    List<PastMedicalHistory> selectByPatientId(@Param("patientId") Integer patientId);
}