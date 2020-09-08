package com.myzy.patient.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.patient.entity.TreatmentInfo;
import com.myzy.patient.patient.mapper.TreatmentInfoMapper;
import com.myzy.patient.patient.service.TreatmentInfoService;
import org.springframework.stereotype.Service;


/**
 * (TreatmentInfo)表服务实现类
 *
 * @author leekejin
 * @since 2020-08-04 08:52:26
 */
@Service("treatmentInfoService")
public class TreatmentInfoServiceImpl extends ServiceImpl<TreatmentInfoMapper, TreatmentInfo> implements TreatmentInfoService {

}