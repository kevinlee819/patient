package com.myzy.patient.patient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myzy.patient.patient.entity.PatientInfo;
import com.myzy.patient.patient.entity.patientInfo.QueryPatientInfoPage;
import com.myzy.patient.patient.entity.patientInfo.QueryPatientInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PatientInfo)表数据库访问层
 *
 * @author leekejin
 * @since 2020-07-31 16:57:41
 */
@Mapper
public interface PatientInfoMapper extends BaseMapper<PatientInfo> {

    /**
    * 分页查询数据
    * @param page   统一分页信息
    * @return 分页数据
    */
    Page<PatientInfo> queryPage(Page<QueryPatientInfoVO> page, @Param("query") QueryPatientInfoVO query);

    /**
     * 根据创建人查询分页查询数据
     * @param page 统一分页信息
     * @param query 查询实体
     * @param createName 创建人
     * @return 分页数据
     */
    Page<PatientInfo> queryPageByCreater(Page<QueryPatientInfoVO> page, @Param("query") QueryPatientInfoVO query, @Param("createName") String createName);

    /**
     * 查询信息，以列表方式返回
     * @param query 实体
     * @return 列表
     */
    List<PatientInfo> queryPatientBasicInfo(@Param("query") QueryPatientInfoVO query);

    /**
     * 根据创建者查询信息，以列表方式返回
     * @param query 实体
     * @param createName 创建者
     * @return 列表
     */
    List<PatientInfo> queryPatientBasicInfoByCreater(@Param("query") QueryPatientInfoVO query, @Param("createName") String createName);

    /**
     * 插入一条数据并将主键存入 param 中
     * @param patient 患者信息
     * @return 改变的数据条数，而非主键
     */
    Integer insertOne(@Param("patient") PatientInfo patient);
}