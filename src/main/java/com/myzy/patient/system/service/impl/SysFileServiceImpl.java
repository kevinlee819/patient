package com.myzy.patient.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.system.service.SysFileService;
import com.myzy.patient.core.UserContext;
import com.myzy.patient.core.entity.TokenEntity;
import com.myzy.patient.core.exception.BusinessException;
import com.myzy.patient.core.exception.HttpStatusCodeEnum;
import com.myzy.patient.system.entity.SysFile;
import com.myzy.patient.system.entity.file.QueryRelationFileVO;
import com.myzy.patient.system.entity.file.RelationFileVO;
import com.myzy.patient.system.mapper.SysFileMapper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 文件信息表(SysFile)表服务实现类
 *
 * @author leekejin
 * @since 2020-07-13 09:10:14
 */
@Slf4j
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Value("${uploadDir}")
    private String uploadDir;
    @Value("${thumbWidth:200}")
    private Integer thumbWidth;
    @Value("${thumbHeight:200}")
    private Integer thumbHeight;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> upload(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new BusinessException("上传文件不存在", HttpStatusCodeEnum.BAD_REQUEST);
        }

        TokenEntity tokenEntity = UserContext.getUser();
        List<String> result = new ArrayList<>();
        for (MultipartFile file : files) {
            String key = UUID.randomUUID().toString().replace("-", "");
            String prefix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);

            SysFile sysFile = new SysFile();
            sysFile.setOriginalName(file.getOriginalFilename());
            sysFile.setFileType(file.getContentType());
            sysFile.setNewName(String.format("%s.%s", key, prefix));
            sysFile.setCreateTime(new Date());
            if (tokenEntity != null) {
                sysFile.setCreateUser(tokenEntity.getUserId());
            }
            try {
                // 图片类型的生成缩略图
                if (sysFile.getFileType().indexOf("image/") == 0) {
                    BufferedImage bufferedImage = Thumbnails.of(file.getInputStream())
                            .size(thumbWidth, thumbHeight)
                            .asBufferedImage();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, prefix, bos);
                    String imageBase64 = Base64.encodeBase64String(bos.toByteArray())
                            .replace("\r", "")
                            .replace("\n", "");
                    sysFile.setThumb(String.format("data:image/png;base64,%s", imageBase64));
                }
            } catch (IOException e) {
                throw new BusinessException(String.format("生成缩略图失败：%s", e.getMessage()));
            }

            // 保存文件到本地
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/%s", uploadDir, sysFile.getNewName()));
                FileCopyUtils.copy(file.getBytes(), fileOutputStream);
            } catch (IOException e) {
                throw new BusinessException(String.format("保存文件失败：%s", e.getMessage()));
            }
            // 保存文件到数据库
            boolean saveFlag = super.save(sysFile);
            if (!saveFlag) {
                throw new BusinessException(String.format("保存失败：%s", sysFile.getOriginalName()));
            }
            // 返回内容
            result.add(sysFile.getNewName());
        }
        return result;
    }

    @Override
    public void relation(RelationFileVO entity) {
        if (entity.getFileNames() != null && !entity.getFileNames().isEmpty()) {
            super.update(Wrappers.lambdaUpdate(SysFile.class)
                    .in(SysFile::getNewName, entity.getFileNames())
                    .set(SysFile::getRelationId, entity.getRelationId())
                    .set(SysFile::getRelationType, entity.getRelationType())
                    .set(SysFile::getRelationChildType, entity.getRelationChildType())
            );
        }
    }

    @Override
    public void coverRelation(RelationFileVO entity) {
        // 把关联数据清空
        LambdaUpdateWrapper<SysFile> updateWrapper = Wrappers.lambdaUpdate(SysFile.class)
                .set(SysFile::getRelationId, null)
                .set(SysFile::getRelationType, null)
                .set(SysFile::getRelationChildType, null)
                .eq(SysFile::getRelationId, entity.getRelationId())
                .eq(SysFile::getRelationType, entity.getRelationType());
        if (entity.getRelationChildType() != null) {
            updateWrapper.eq(SysFile::getRelationChildType, entity.getRelationChildType());
        }
        super.update(updateWrapper);
        // 关联文件数据
        relation(entity);
    }

    @Override
    public List<SysFile> getRelationFiles(QueryRelationFileVO entity) {
        LambdaQueryWrapper<SysFile> queryWrapper = Wrappers.lambdaQuery(SysFile.class)
                .eq(SysFile::getRelationId, entity.getRelationId())
                .eq(SysFile::getRelationType, entity.getRelationType());
        if (entity.getRelationChildType() != null) {
            queryWrapper.eq(SysFile::getRelationChildType, entity.getRelationChildType());
        }
        return super.list(queryWrapper);
    }

}