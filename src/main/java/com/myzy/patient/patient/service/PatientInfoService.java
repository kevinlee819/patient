package com.myzy.patient.patient.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.patient.entity.PatientInfo;
import com.myzy.patient.patient.entity.patientInfo.*;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * (PatientInfo)表服务接口
 *
 * @author leekejin
 * @since 2020-08-03 09:56:04
 */
public interface PatientInfoService extends IService<PatientInfo> {

    /**
     *根据id查询患者所有信息
     * @param id id
     * @return 所有信息
     */
    public UpdatePatientInfoVO queryById(Serializable id);

    /**
     * 保存新增患者信息
     * @param createPatientInfoVO 新增患者信息
     * @return 改动的数据库的行数
     */
    public int saveVO(CreatePatientInfoVO createPatientInfoVO);

    /**
     * 根据id更新
     * @param updatePatientInfoVO 患者修改后的信息
     * @return 改动的数据库的行数
     */
    public int updatePatientInfoById(UpdatePatientInfoVO updatePatientInfoVO);

    /**
     * 高级查询,查询基本信息,分页
     * @param queryPatientInfoVO 查询实体
     * @return 查询后的基本信息分页结果
     */
    public Page<PatientInfo> pageQuery(QueryPatientInfoVO queryPatientInfoVO);

    /**
     * 高级查询，查找所有消息,分页
     * @param queryPatientInfoVO 查询实体
     * @return 查询后的所有信息分页结果
     */
    public Page<PatientAllInfoVO> pageQueryAll(QueryPatientInfoVO queryPatientInfoVO);

    /**
     *导出Excel表格
     * @param queryPatientInfoVO 查询实体
     * @param response  HttpServletResponse
     * @throws Exception
     */
    public void exportExcel(QueryPatientInfoVO queryPatientInfoVO, HttpServletResponse response) throws Exception;

    /**
     * 高级查询，查找所有消息,不分页
     * @param entity 查询实体
     * @return 所有信息
     */
    public List<PatientAllInfoVO> listQueryAll(QueryPatientInfoVO entity);

    /**
     * 根据id来进行批量删或单条删除
     * @param idList id列表
     * @return 是否删除成功
     */
    public boolean deleteListByIds(List<Integer> idList);

}