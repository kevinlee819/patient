package com.myzy.patient.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.patient.entity.MedicationInfo;
import com.myzy.patient.patient.mapper.MedicationInfoMapper;
import com.myzy.patient.patient.service.MedicationInfoService;
import org.springframework.stereotype.Service;



/**
 * (MedicationInfo)表服务实现类
 *
 * @author leekejin
 * @since 2020-08-04 15:24:45
 */
@Service("medicationInfoService")
public class MedicationInfoServiceImpl extends ServiceImpl<MedicationInfoMapper, MedicationInfo> implements MedicationInfoService {

}