package com.myzy.patient.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.patient.entity.PresentIllnessHistory;
import com.myzy.patient.patient.mapper.PresentIllnessHistoryMapper;
import com.myzy.patient.patient.service.PresentIllnessHistoryService;
import org.springframework.stereotype.Service;



/**
 * (PresentIllnessHistory)表服务实现类
 *
 * @author leekejin
 * @since 2020-08-04 17:52:08
 */
@Service("presentIllnessHistoryService")
public class PresentIllnessHistoryServiceImpl extends ServiceImpl<PresentIllnessHistoryMapper, PresentIllnessHistory> implements PresentIllnessHistoryService {

}