package com.myzy.patient.utils;

import com.myzy.patient.core.exception.BusinessException;
import com.myzy.patient.core.exception.HttpStatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 文件信息表创建实体
 *
 * @author leekejin
 * @since 2020-07-13 09:10:13
 */
@Slf4j
public class FileUtil {

    /**
     * 从src\main\resources\template目录下载模板文件
     *
     * @param templateName 模板名称
     * @param fileName     下载文件名称
     * @param response     请求Response
     */
    public static void exportFile(String templateName, String fileName, HttpServletResponse response) {
        InputStream inputStream;
        try {
            inputStream = new ClassPathResource(String.format("template/%s", templateName)).getInputStream();
        } catch (IOException e) {
            log.error("模板不存在。错误信息{}", e.getMessage());
            throw new BusinessException(templateName + "模板文件不存在", HttpStatusCodeEnum.NOT_FOUND);
        }
        //转码防止乱码
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
        try {
            //定义每次读取字节的大小与保存字节的数据
            byte[] bs = new byte[1024];
            //定义每次读取的长度
            int len;
            //循环读取数据，如果读取的数据为-1，说明已经读取了末尾
            while ((len = inputStream.read(bs)) != -1) {
                // 输出
                response.getOutputStream().write(bs, 0, len);
            }
        } catch (IOException e) {
            throw new BusinessException("数据处理异常");
        }
    }

}
