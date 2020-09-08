package com.myzy.patient.patient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myzy.patient.patient.entity.TreatmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (TreatmentInfo)表数据库访问层
 *
 * @author leekejin
 * @since 2020-08-04 08:52:22
 */
@Mapper
public interface TreatmentInfoMapper extends BaseMapper<TreatmentInfo> {
        /**
         * 获取就诊信息
         * @param patientId 患者id
         * @return 就诊信息
         */
        public TreatmentInfo selectByPatientId(@Param("patientId") Integer patientId);
}