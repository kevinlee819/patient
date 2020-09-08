package com.myzy.patient.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.patient.entity.FieldInfo;
import com.myzy.patient.patient.entity.patientInfo.FieldInfoVO;
import com.myzy.patient.patient.entity.patientInfo.OptionVO;
import com.myzy.patient.patient.entity.patientInfo.OptionsVO;
import com.myzy.patient.patient.mapper.FieldInfoMapper;
import com.myzy.patient.patient.service.FieldInfoService;
import com.myzy.patient.system.entity.SysDictionary;
import com.myzy.patient.system.service.SysDictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * (FieldInfo)表服务实现类
 *
 * @author leekejin
 * @since 2020-08-04 11:59:02
 */
@Service("fieldInfoService")
public class FieldInfoServiceImpl extends ServiceImpl<FieldInfoMapper, FieldInfo> implements FieldInfoService {
    @Resource
    private FieldInfoMapper fieldInfoMapper;

    @Resource
    private SysDictionaryService sysDictionaryService;

    @Override
    public List<FieldInfoVO> selectAll(){
        List<FieldInfo> fieldInfoList =  fieldInfoMapper.selectList(new QueryWrapper<FieldInfo>());
        List<FieldInfoVO> fieldInfoVOS = new ArrayList<>(fieldInfoList.size());
        for (FieldInfo field: fieldInfoList) {
            FieldInfoVO fieldInfoVO = new FieldInfoVO();
            BeanUtils.copyProperties(field,fieldInfoVO);
            fieldInfoVO.setFieldName(FieldInfoService.camelCaseName(fieldInfoVO.getFieldName()));
            fieldInfoVOS.add(fieldInfoVO);
        }
        return fieldInfoVOS;
    }

    @Override
    public OptionsVO selectOption(){
        List<SysDictionary> optionDict = sysDictionaryService.getChildByCode("option");
        Set<String> fields = new HashSet<>();
        for (SysDictionary dict: optionDict) {
           fields.add(dict.getName());
        }
        OptionsVO optionsVO = new OptionsVO();
        HashMap<String, List<OptionVO>> map = new HashMap<>(32);
        for (String field: fields) {
            List<OptionVO> optionVOS = new ArrayList<>();
            for (SysDictionary dict: optionDict) {
                if (field.equals(dict.getName())){
                   OptionVO optionVO = new OptionVO();
                   optionVO.setDescription(dict.getDescription());
                   optionVO.setValue(dict.getValue());
                   optionVOS.add(optionVO);
                }
            }
            map.put(FieldInfoService.camelCaseName(field),optionVOS);
        }
        optionsVO.setOptions(map);
        return optionsVO;
    }
}