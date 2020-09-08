package com.myzy.patient.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.core.UserContext;
import com.myzy.patient.core.exception.BusinessException;
import com.myzy.patient.patient.entity.*;
import com.myzy.patient.patient.entity.patientInfo.*;
import com.myzy.patient.patient.mapper.*;
import com.myzy.patient.patient.service.FieldInfoService;
import com.myzy.patient.patient.service.PastMedicalHistoryService;
import com.myzy.patient.patient.service.PatientInfoService;
import com.myzy.patient.system.service.SysUserService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (PatientInfo)表服务实现类
 *
 * @author leekejin
 * @since 2020-08-03 09:56:08
 */
@Service("patientInfoService")
public class PatientInfoServiceImpl extends ServiceImpl<PatientInfoMapper, PatientInfo> implements PatientInfoService {
    @Resource
    private PatientInfoMapper patientInfoMapper;

    @Resource
    private TreatmentInfoMapper treatmentInfoMapper;

    @Resource
    private ReturnVisitMapper returnVisitMapper;

    @Resource
    private MedicationInfoMapper medicationInfoMapper;

    @Resource
    private PastMedicalHistoryMapper pastMedicalHistoryMapper;

    @Resource
    private PresentIllnessHistoryMapper presentIllnessHistoryMapper;

    @Resource
    private FieldInfoService fieldInfoService;

    @Resource
    private SysUserService sysUserService;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public int saveVO(CreatePatientInfoVO createPatientInfoVO) {
        int count = 0;
        try {
            //存基本信息
            PatientInfo patientInfo = new PatientInfo();
            BeanUtils.copyProperties(createPatientInfoVO,patientInfo);
            patientInfo.setCreateDate(new Date());
            patientInfo.setCreatePeople(UserContext.getUser().getUserName());
            patientInfo.setId(null);
            patientInfoMapper.insertOne(patientInfo);
            Integer patientId = patientInfo.getId();
            //存就诊信息
            TreatmentInfo treatmentInfo = new TreatmentInfo();
            BeanUtils.copyProperties(createPatientInfoVO,treatmentInfo);
            treatmentInfo.setId(null);
            treatmentInfo.setPatientId(patientId);
            count += treatmentInfoMapper.insert(treatmentInfo);

            //存回访信息
            if(createPatientInfoVO.getReturnVisits()!=null && !createPatientInfoVO.getReturnVisits().isEmpty()) {
                for (ReturnVisit returnVisit: createPatientInfoVO.getReturnVisits()) {
                    BeanUtils.copyProperties(createPatientInfoVO,returnVisit);
                    returnVisit.setId(null);
                    returnVisit.setPatientId(patientId);
                    count += returnVisitMapper.insert(returnVisit);
                }
            }

            //存用药信息
            MedicationInfo medicationInfo = new MedicationInfo();
            BeanUtils.copyProperties(createPatientInfoVO,medicationInfo);
            medicationInfo.setId(null);
            medicationInfo.setPatientId(patientId);
            count += medicationInfoMapper.insert(medicationInfo);

            //存既往史
            PastHistoryVO pastHistoryVO = new PastHistoryVO();
            BeanUtils.copyProperties(createPatientInfoVO, pastHistoryVO);
            String[] pastFieldNames = PastMedicalHistoryService.getFiledName(pastHistoryVO);
            PastMedicalHistory record1 = new PastMedicalHistory();
            for (String fieldName : pastFieldNames) {
                if("patientId".equals(fieldName)) {
                    continue;
                }
                record1.setId(null);
                record1.setPatientId(patientId);
                record1.setFieldValue(fieldName);
                record1.setDetails(PastMedicalHistoryService.getFieldValueByFieldName(fieldName,pastHistoryVO));
                count += pastMedicalHistoryMapper.insert(record1);
            }

            //存现病史
            PresentHistoryVO presentHistoryVO = new PresentHistoryVO();
            BeanUtils.copyProperties(createPatientInfoVO, presentHistoryVO);
            String[] preFieldNames = PastMedicalHistoryService.getFiledName(presentHistoryVO);
            PresentIllnessHistory record2 = new PresentIllnessHistory();
            for (String fieldName : preFieldNames) {
                if("patientId".equals(fieldName)) {
                    continue;
                }
                record2.setFieldValue(fieldName);
                record2.setDetails(PastMedicalHistoryService.getFieldValueByFieldName(fieldName,presentHistoryVO));
                record2.setId(null);
                record2.setPatientId(patientId);
                count += presentIllnessHistoryMapper.insert(record2);
            }
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return count;
    }


    @Override
    public UpdatePatientInfoVO queryById(Serializable id) {
        PatientInfo patientInfo = patientInfoMapper.selectById(id);
        if(sysUserService.currentUser().getAuth() != 1 && !patientInfo.getCreatePeople().equals(sysUserService.currentUser().getUserName())){
            throw new BusinessException("无权限");
        }
        Integer patientId = patientInfo.getId();
        //传递基本信息
        UpdatePatientInfoVO updatePatientInfoVO = new UpdatePatientInfoVO();
        BeanUtils.copyProperties(patientInfo, updatePatientInfoVO);
        //传递就诊信息
        if (treatmentInfoMapper.selectByPatientId(patientId) != null) {
            BeanUtils.copyProperties(treatmentInfoMapper.selectByPatientId(patientId),updatePatientInfoVO);
        }
        //传递用药信息
        if (medicationInfoMapper.selectByPatientId(patientId) != null) {
            BeanUtils.copyProperties(medicationInfoMapper.selectByPatientId(patientId),updatePatientInfoVO);
        }
        //传递回访信息
        QueryWrapper<ReturnVisit> returnVisitQueryWrapper = new QueryWrapper<>();
        returnVisitQueryWrapper.eq("patient_id", patientId);
        List<ReturnVisit> returnVisits = returnVisitMapper.selectList(returnVisitQueryWrapper);
        updatePatientInfoVO.setReturnVisits(returnVisits);
        //传递现病史
        List<PresentIllnessHistory> presentIllnessHistories = presentIllnessHistoryMapper.selectByPatientId(patientId);
        for (PresentIllnessHistory pih : presentIllnessHistories) {
            PastMedicalHistoryService.setValueByName(pih.getFieldValue(),pih.getDetails(),updatePatientInfoVO);
        }
        //传递既往史
        List<PastMedicalHistory> pastMedicalHistories = pastMedicalHistoryMapper.selectByPatientId(patientId);
        for (PastMedicalHistory pmh : pastMedicalHistories) {
            PastMedicalHistoryService.setValueByName(pmh.getFieldValue(),pmh.getDetails(),updatePatientInfoVO);
        }
        updatePatientInfoVO.setId(patientId);
        return updatePatientInfoVO;
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public int updatePatientInfoById(UpdatePatientInfoVO updatePatientInfoVO) {
        int count = 0;
        if(sysUserService.currentUser().getAuth() != 1 ){
            throw new BusinessException("无权限");
        }
        Integer patientId = updatePatientInfoVO.getId();
        try {
            //更新基本信息
            PatientInfo patientInfo = new PatientInfo();
            BeanUtils.copyProperties(updatePatientInfoVO, patientInfo);
            patientInfoMapper.updateById(patientInfo);

            //更新就诊信息
            TreatmentInfo treatmentInfo = new TreatmentInfo();
            BeanUtils.copyProperties(updatePatientInfoVO, treatmentInfo);
            treatmentInfo.setPatientId(patientId);
            treatmentInfo.setId(null);
            UpdateWrapper<TreatmentInfo> treatmentInfoUpdateWrapper = new UpdateWrapper<>();
            treatmentInfoUpdateWrapper.eq("patient_id", patientId);
            count += treatmentInfoMapper.update(treatmentInfo, treatmentInfoUpdateWrapper);

            //更新用药信息
            MedicationInfo medicationInfo = new MedicationInfo();
            BeanUtils.copyProperties(updatePatientInfoVO, medicationInfo);
            medicationInfo.setPatientId(patientId);
            medicationInfo.setId(null);
            UpdateWrapper<MedicationInfo> medicationInfoUpdateWrapper = new UpdateWrapper<>();
            medicationInfoUpdateWrapper.eq("patient_id", patientId);
            count += medicationInfoMapper.update(medicationInfo, medicationInfoUpdateWrapper);

            //更新回访信息
            List<ReturnVisit> returnVisits = updatePatientInfoVO.getReturnVisits();
            for (ReturnVisit returnVisit : returnVisits) {
                int tmp = 0;
                tmp = returnVisitMapper.updateById(returnVisit);
                if (tmp == 0){
                    returnVisit.setId(null);
                    returnVisit.setPatientId(patientId);
                    count += returnVisitMapper.insert(returnVisit);
                } else {
                    count += tmp;
                }
            }

            //更新现病史
            PresentHistoryVO presentHistoryVO = new PresentHistoryVO();
            BeanUtils.copyProperties(updatePatientInfoVO, presentHistoryVO);
            String[] preFieldNames = PastMedicalHistoryService.getFiledName(presentHistoryVO);
            for (String fieldName : preFieldNames) {
                PresentIllnessHistory record2 = new PresentIllnessHistory();
                if ("patientId".equals(fieldName)) {
                    continue;
                }
                record2.setPatientId(patientId);
                record2.setFieldValue(fieldName);
                record2.setDetails(PastMedicalHistoryService.getFieldValueByFieldName(fieldName, presentHistoryVO));
                UpdateWrapper<PresentIllnessHistory> presentIllnessHistoryUpdateWrapper = new UpdateWrapper<>();
                presentIllnessHistoryUpdateWrapper.and(wrapper -> wrapper.eq("patient_id", record2.getPatientId()).eq("field_value", record2.getFieldValue()));
                count += presentIllnessHistoryMapper.update(record2, presentIllnessHistoryUpdateWrapper);
            }

            //更新既往史
            PastHistoryVO pastHistoryVO = new PastHistoryVO();
            BeanUtils.copyProperties(updatePatientInfoVO, pastHistoryVO);
            String[] pastFieldNames = PastMedicalHistoryService.getFiledName(pastHistoryVO);
            for (String fieldName : pastFieldNames) {
                PastMedicalHistory record1 = new PastMedicalHistory();
                if ("patientId".equals(fieldName)) {
                    continue;
                }
                record1.setPatientId(patientId);
                record1.setFieldValue(fieldName);
                record1.setDetails(PastMedicalHistoryService.getFieldValueByFieldName(fieldName, pastHistoryVO));
                UpdateWrapper<PastMedicalHistory> pastMedicalHistoryUpdateWrapper = new UpdateWrapper<>();
                pastMedicalHistoryUpdateWrapper.and(wrapper -> wrapper.eq("patient_id", record1.getPatientId()).eq("field_value", record1.getFieldValue()));
                count += pastMedicalHistoryMapper.update(record1, pastMedicalHistoryUpdateWrapper);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return count;
    }


    @Override
    public Page<PatientInfo> pageQuery(QueryPatientInfoVO entity) {
        Page page = entity.genPage().addOrder(OrderItem.desc("id"));
        return baseMapper.queryPage(page, entity);

    }


    @Override
    public Page<PatientAllInfoVO> pageQueryAll(QueryPatientInfoVO entity) {
        Page page = entity.genPage().addOrder(OrderItem.desc("id"));
        if(sysUserService.currentUser().getAuth() == 1){
            page = baseMapper.queryPage(page,entity);
        } else {
            page = baseMapper.queryPageByCreater(page,entity,sysUserService.currentUser().getUserName());
        }
        List<PatientInfo> patientInfos = page.getRecords();
        page.setRecords(queryAllByBasic(patientInfos));
        return page;
    }

    /**
     * 根据基本信息查询其他信息,私有方法
     * @param patientInfos
     * @return
     */
    private List<PatientAllInfoVO> queryAllByBasic(List<PatientInfo> patientInfos){
        //noinspection AlibabaLowerCamelCaseVariableNaming
        if(patientInfos != null && patientInfos.size() != 0){
            List<PatientAllInfoVO> patientAllInfoVOS = new ArrayList<PatientAllInfoVO>(patientInfos.size());
            for (PatientInfo patientInfo: patientInfos) {
                PatientAllInfoVO patientAllInfoVO = new PatientAllInfoVO();
                //传递基本信息
                BeanUtils.copyProperties(patientInfo, patientAllInfoVO);
                //传递就诊信息
                if (treatmentInfoMapper.selectByPatientId(patientInfo.getId()) != null) {
                    BeanUtils.copyProperties(treatmentInfoMapper.selectByPatientId(patientInfo.getId()),patientAllInfoVO);
                }
                //传递用药信息
                if (medicationInfoMapper.selectByPatientId(patientInfo.getId()) != null) {
                    BeanUtils.copyProperties(medicationInfoMapper.selectByPatientId(patientInfo.getId()),patientAllInfoVO);
                }
                //传递既往史
                List<PastMedicalHistory> pastMedicalHistories = pastMedicalHistoryMapper.selectByPatientId(patientInfo.getId());
                for (PastMedicalHistory pmh : pastMedicalHistories) {
                    PastMedicalHistoryService.setValueByName(pmh.getFieldValue(),pmh.getDetails(),patientAllInfoVO);
                }
                //传递现病史
                List<PresentIllnessHistory> presentIllnessHistories = presentIllnessHistoryMapper.selectByPatientId(patientInfo.getId());
                for (PresentIllnessHistory pih : presentIllnessHistories) {
                    PastMedicalHistoryService.setValueByName(pih.getFieldValue(),pih.getDetails(),patientAllInfoVO);
                }
                patientAllInfoVO.setId(patientInfo.getId());
                patientAllInfoVOS.add(patientAllInfoVO);
            }
            return patientAllInfoVOS;
        }
        return null;
    }


    @Override
    public List<PatientAllInfoVO> listQueryAll(QueryPatientInfoVO entity){
        List<PatientInfo> patientInfos = null;
        if(sysUserService.currentUser().getAuth() == 1){
            patientInfos = patientInfoMapper.queryPatientBasicInfo(entity);
        } else {
            patientInfos = patientInfoMapper.queryPatientBasicInfoByCreater(entity,sysUserService.currentUser().getUserName());
        }
        return queryAllByBasic(patientInfos);
    }


    @Override
    public void exportExcel(QueryPatientInfoVO queryPatientInfoVO, HttpServletResponse response) throws Exception {
        ServletOutputStream out = response.getOutputStream();
        try{
            //创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            //在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = workbook.createSheet("患者信息表");
            //创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置自动换行
            style.setWrapText(false);
            // 设置水平对齐的样式为居中对齐；
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            List<FieldInfoVO> fields =  fieldInfoService.selectAll();


            @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming") List<PatientAllInfoVO> patientAllInfoVOS = listQueryAll(queryPatientInfoVO);

            if(patientAllInfoVOS == null || patientAllInfoVOS.size() == 0){
                HSSFRow row0 = sheet.createRow(0);
                row0.createCell(0).setCellValue("没有数据！");
            } else{
                // 表头
                if (fields != null && fields.size() != 0){
                    HSSFRow row0 = sheet.createRow(0);
                    for (int i = 0; i < fields.size(); i++) {
                        HSSFCell cell = row0.createCell(i);
                        cell.setCellValue(fields.get(i).getShowName());
                        sheet.setColumnWidth(cell.getColumnIndex(),(fields.get(i).getShowName().length()+15)*256);
                    }
                }
                // 表身
                HSSFRow[] rows = new HSSFRow[patientAllInfoVOS.size()];
                for (int i = 0; i < patientAllInfoVOS.size(); i++) {
                    rows[i] = sheet.createRow(i + 1);
                    PatientAllInfoVO patientAllInfoVO = patientAllInfoVOS.get(i);
                    for (int j = 0; j < fields.size(); j++) {
                        rows[i].createCell(j).setCellValue(PastMedicalHistoryService.getFieldValueByFieldName(fields.get(j).getFieldName(),patientAllInfoVO));
                    }
                }
            }

            //将文件输出到客户端浏览器
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("Patient_Information_"+ new SimpleDateFormat
                    ("yyyy-MM-dd").format(new Date()), "UTF-8") + ".xls");

            try {
                workbook.write(out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("导出信息失败！");
        }

    }


    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean deleteListByIds(List<Integer> idList) {
        boolean flag = true;
        try {
            for (Integer id: idList) {
                // 删除基本信息
                patientInfoMapper.deleteById(id);
                //删除就诊信息
                QueryWrapper<TreatmentInfo> treatmentInfoQueryWrapper = new QueryWrapper<>();
                treatmentInfoQueryWrapper.eq("patient_id",id);
                treatmentInfoMapper.delete(treatmentInfoQueryWrapper);
                //删除用药信息
                QueryWrapper<MedicationInfo> medicationInfoQueryWrapper = new QueryWrapper<>();
                medicationInfoQueryWrapper.eq("patient_id",id);
                medicationInfoMapper.delete(medicationInfoQueryWrapper);
                //删除回访信息
                QueryWrapper<ReturnVisit> returnVisitQueryWrapper = new QueryWrapper<>();
                returnVisitQueryWrapper.eq("patient_id",id);
                returnVisitMapper.delete(returnVisitQueryWrapper);
                //删除既往史
                QueryWrapper<PastMedicalHistory> pastMedicalHistoryQueryWrapper = new QueryWrapper<>();
                pastMedicalHistoryQueryWrapper.eq("patient_id",id);
                pastMedicalHistoryMapper.delete(pastMedicalHistoryQueryWrapper);
                //删除现病史
                QueryWrapper<PresentIllnessHistory> presentIllnessHistoryQueryWrapper = new QueryWrapper<>();
                presentIllnessHistoryQueryWrapper.eq("patient_id",id);
            }
        } catch (Exception e){
            e.printStackTrace();
            flag = false;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }
}