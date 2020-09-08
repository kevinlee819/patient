package com.myzy.patient.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.patient.entity.PastMedicalHistory;
import com.myzy.patient.patient.mapper.PastMedicalHistoryMapper;
import com.myzy.patient.patient.service.PastMedicalHistoryService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;


/**
 * (PastMedicalHistory)表服务实现类
 *
 * @author makejava
 * @since 2020-08-04 17:16:07
 */
@Service("pastMedicalHistoryService")
public class PastMedicalHistoryServiceImpl extends ServiceImpl<PastMedicalHistoryMapper, PastMedicalHistory> implements PastMedicalHistoryService {

}