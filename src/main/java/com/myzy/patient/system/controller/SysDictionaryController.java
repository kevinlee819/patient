package com.myzy.patient.system.controller;

import com.myzy.patient.core.entity.Result;
import com.myzy.patient.system.entity.SysDictionary;
import com.myzy.patient.system.entity.dictionary.CreateDictionaryVO;
import com.myzy.patient.system.entity.dictionary.QueryDictionaryVO;
import com.myzy.patient.system.entity.dictionary.TreeDictionaryVO;
import com.myzy.patient.system.entity.dictionary.UpdateDictionaryVO;
import com.myzy.patient.system.service.SysDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author leekejin
 * @since 2020-07-09 10:58:36
 */
@RestController
@RequestMapping("sysDictionary")
@Api(tags = "字典管理")
public class SysDictionaryController {

    @Resource
    private SysDictionaryService sysDictionaryService;

    @ApiOperation(value = "查询字典列表-返回树形数据")
    @GetMapping
    public List<TreeDictionaryVO> treeDictionary(QueryDictionaryVO entity) {
        return sysDictionaryService.treeDictionary(entity);
    }

    @ApiOperation(value = "查询子字典列表")
    @GetMapping("/getChildByCode")
    public List<SysDictionary> getChildByCode(String code) {
        return sysDictionaryService.getChildByCode(code);
    }

    @ApiOperation(value = "通过主键查询单条数据")
    @GetMapping("{id}")
    public SysDictionary queryOne(@PathVariable Serializable id) {
        return sysDictionaryService.getById(id);
    }

    @ApiOperation(value = "新增数据")
    @PostMapping
    public Result add(@Valid @RequestBody CreateDictionaryVO entity) {
        sysDictionaryService.add(entity);
        return Result.ok();
    }

    @ApiOperation(value = "修改数据")
    @PutMapping
    public Result update(@Valid @RequestBody UpdateDictionaryVO entity) {
        sysDictionaryService.update(entity);
        return Result.ok();
    }

    @ApiOperation(value = "删除数据")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        sysDictionaryService.delete(ids);
        return Result.ok();
    }

}