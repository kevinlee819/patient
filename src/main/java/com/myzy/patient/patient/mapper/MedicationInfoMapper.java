package com.myzy.patient.patient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myzy.patient.patient.entity.MedicationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (MedicationInfo)表数据库访问层
 *
 * @author leekejin
 * @since 2020-08-04 15:24:41
 */
@Mapper
public interface MedicationInfoMapper extends BaseMapper<MedicationInfo> {
    /**
     * 获取用药信息
     * @param patientId 患者id
     * @return 用药信息
     */
    public MedicationInfo selectByPatientId(@Param("patientId")Integer patientId);
}