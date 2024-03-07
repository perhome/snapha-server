package cn.perhome.snapha.controller.api.v1;


import cn.perhome.snapha.dto.ResponseFileUploadDto;
import cn.perhome.snapha.dto.ResponseResultDto;

import cn.perhome.snapha.utils.MinioUtils;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Tag(name = "文件上传")
@Slf4j
@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {

    private final MinioUtils minioUtils;

    @Operation(summary = "默认上传方式")
    @RequestMapping(value="", method = RequestMethod.POST, produces="application/json;charset=utf-8;")
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(
            @RequestParam("directory") String directory,
            @RequestParam(value = "hitDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date hitDate,
            @RequestParam("file") MultipartFile file) throws Exception {
        Map<String, Object> ret = new HashMap<>();
        ResponseResultDto responseResultDto = ResponseResultDto.failed(403, "上传文件不规范");
        if (file.getSize() > 0) {
            switch (directory) {
                case "farmland":
                    break;
                case "livestock":
                    break;
                case "reality":
                    break;
                default:
                    directory = "test";
            }
            ResponseFileUploadDto responseFileUploadDto = minioUtils.uploadFile(file, directory);
            ret.put("name", file.getOriginalFilename());
            ret.put("url", responseFileUploadDto.getUrlHttp());
            responseResultDto = ResponseResultDto.success(ret);
        }
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @Operation(summary = "已上传文档")
    @RequestMapping(value = "/processed", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseResultDto> processed() {
        var data = new HashMap<String, Object>();
        data.put("info", "这是一条认证过的信息");
        return new ResponseEntity<>(ResponseResultDto.success(data), HttpStatus.OK);
    }
}
