package com.myzy.patient.patient.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myzy.patient.patient.entity.ReturnVisit;
import com.myzy.patient.patient.entity.patientInfo.*;
import com.myzy.patient.patient.service.FieldInfoService;
import com.myzy.patient.patient.service.PatientInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * (PatientInfo)表控制层
 *
 * @author leekejin
 * @since 2020-08-03 09:56:10
 */
@RestController
@RequestMapping("patientInfo")
@Api(tags = "患者信息管理")
public class PatientInfoController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private PatientInfoService patientInfoService;

    @Resource
    private FieldInfoService fieldInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id查询结果")
    @GetMapping("{id}")
    public UpdatePatientInfoVO selectOne(@PathVariable Serializable id) {
        return patientInfoService.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param createPatientInfoVO 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation(value = "插入一条新的患者信息")
    public int insert(@RequestBody CreatePatientInfoVO createPatientInfoVO) {
        return patientInfoService.saveVO(createPatientInfoVO);
    }

    /**
     * 分页查询
     *
     * @param queryPatientInfoVO 查询实体
     * @return
     */
    @GetMapping()
    @ApiOperation(value = "组合条件查询+分页")
    public Page<PatientAllInfoVO> selectAll(QueryPatientInfoVO queryPatientInfoVO){
        return patientInfoService.pageQueryAll(queryPatientInfoVO);
    }

    /**
     * 修改数据
     *
     * @param updatePatientInfoVO 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation(value = "根据id更新患者信息")
    public int update(@RequestBody UpdatePatientInfoVO updatePatientInfoVO) {
        return patientInfoService.updatePatientInfoById(updatePatientInfoVO);
    }


    /**
     * 获取字段名和中文属性名和分组
     *
     * @return 所有的字段名、中文名和相应分组
     */
    @GetMapping("/fieldInfo")
    @ApiOperation(value = "获取字段名和中文属性名和分组")
    public List<FieldInfoVO> getField(){
        return fieldInfoService.selectAll();
    }


    /**
     * 生成Excel
     *
     * @param queryPatientInfoVO 查询实体
     * @param response HttpServletResponse
     * @return 成功或失败信息
     */
    @GetMapping("/genExcel")
    @ApiOperation(value = "生成Excel")
    public String genExcel(QueryPatientInfoVO queryPatientInfoVO, HttpServletResponse response){
        try{
            patientInfoService.exportExcel(queryPatientInfoVO, response);
            return "success";
        } catch(Exception e){
            e.printStackTrace();
        }
        return "error";
    }


    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @DeleteMapping
    public boolean delete(@RequestParam("idList") List<Integer> idList) {
        return patientInfoService.deleteListByIds(idList);
    }


    /**
     * 得到选项
     *
     * @return 返回所有可以选择的字段的选项
     */
    @GetMapping("/getOption")
    @ApiOperation(value = "获得选项")
    public OptionsVO getOption(){
        return fieldInfoService.selectOption();
    }

}