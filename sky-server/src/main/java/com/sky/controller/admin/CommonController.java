package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.MinioUtil;
import com.sky.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    private final MinioUtil minioUtil;

    public CommonController(MinioUtil minioUtil) {
        this.minioUtil = minioUtil;
    }

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取原始文件后缀名
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = UUID.randomUUID().toString() + substring;
        //文件的请求路径
        String filePath = minioUtil.upload(file, objectName);
        return Result.success(filePath);
    }

}
