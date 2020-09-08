package com.myzy.patient.patient.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.patient.entity.FieldInfo;
import com.myzy.patient.patient.entity.patientInfo.FieldInfoVO;
import com.myzy.patient.patient.entity.patientInfo.OptionsVO;

import java.util.List;

/**
 * (FieldInfo)表服务接口
 *
 * @author leekejin
 * @since 2020-08-04 11:59:01
 */
public interface FieldInfoService extends IService<FieldInfo> {

    /**
     * 获取所有字段的信息
     * @return 字段信息
     */
    public List<FieldInfoVO> selectAll();

    /**
     * 下划线转驼峰命名(lowerCamelCase)
     * @param underscoreName 下划线命名
     * @return 驼峰命名
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();

    }

    /**
     * 获取选项
     * @return 选项
     */
    public OptionsVO selectOption();

}