package com.fly4j.flie.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.common.core.domain.FileInfo;
import com.fly4j.common.core.domain.Result;
import com.fly4j.flie.utils.FastDFSUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by XianRui on 2020-03-03 17:16
 **/
@Api(tags = "文件接口")
@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @PostMapping
    @ApiOperation(
            value = "文件上传",
            httpMethod = "POST"
    )
    @ApiResponses({
            @ApiResponse(code = 0, message = "上传成功!"),
            @ApiResponse(code = 500, message = "上传失败!")
    })

    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public R<FileInfo> upload(
            MultipartFile file
    ) throws IOException {
        if (file == null) {
            return R.failed("上传文件为空");
        }
        FileInfo fileInfo = fastDFSUtil.upload(file);
        return R.ok(fileInfo);
    }

    @DeleteMapping()
    @ApiOperation(
            value = "文件删除",
            httpMethod = "DELETE"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", value = "文件路径", required = true, paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "删除成功!"),
            @ApiResponse(code = 500, message = "删除失败!")
    })
    public R deleteFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return R.failed("上传文件为空");
        }
        fastDFSUtil.deleteFile(filePath);
        return R.ok(null);
    }

    @PostMapping("/image")
    @ApiOperation(
            value = "图片上传",
            httpMethod = "POST"
    )
    @ApiResponses({
            @ApiResponse(code = 0, message = "上传成功!"),
            @ApiResponse(code = 500, message = "上传失败!")
    })

    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public R<FileInfo> uploadImage(
            MultipartFile file
    ) throws IOException {
        if (file == null) {
            return R.failed("上传图片文件为空");
        }
        FileInfo fileInfo = fastDFSUtil.uploadImage(file);
        return R.ok(fileInfo);
    }


    @PostMapping("/imageWithThumb")
    @ApiOperation(
            value = "图片上传(带缩略图)",
            httpMethod = "POST"
    )
    @ApiResponses({
            @ApiResponse(code = 0, message = "上传成功!"),
            @ApiResponse(code = 500, message = "上传失败!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public R<FileInfo> uploadImageWithThumb(
            MultipartFile file
    ) throws IOException {
        if (file == null) {
            return R.failed("上传图片文件为空");
        }
        FileInfo fileInfo = fastDFSUtil.uploadImage(file, true);
        return R.ok(fileInfo);
    }

}