package com.myzy.patient.patient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.patient.entity.PastMedicalHistory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (PastMedicalHistory)表服务接口
 *
 * @author leekejin
 * @since 2020-08-04 17:16:06
 */
public interface PastMedicalHistoryService extends IService<PastMedicalHistory> {

    /**
     * 根据对象获取属性名数组
     * @param o 对象
     * @return 属性字符串数组
     */
    public static String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            //System.out.println(fields[i].getType());
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }


    /**
     * 根据属性名获取属性值
     * @param fieldName 属性名
     * @param object 对象
     * @return 属性值，字符串类型
     */
    public static String getFieldValueByFieldName(String fieldName,Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //对private的属性的访问
            field.setAccessible(true);
            if (field.get(object) != null){
                if (field.get(object) instanceof Date){
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                    return format.format((Date)field.get(object));
                }
                return field.get(object).toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据字段名设置字段值
     * @param fieldName 字段名
     * @param fieldVal 字段值
     * @param object 对象
     */
    public static void setValueByName(String fieldName,String fieldVal,Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //对private的属性的访问
            field.setAccessible(true);
            field.set(object,fieldVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}