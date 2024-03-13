package cn.perhome.snapha.controller.api.v1.user;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormUserGroupDto;
import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.service.UserGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@Tag(name = "用户组别管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserMapper       userMapper;
    private final UserGroupService userGroupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormUserGroupDto form) {
        boolean isSuccess = this.userGroupService.save(form);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@RequestBody FormUserGroupDto form) {
        boolean isSuccess = this.userGroupService.removeById(form);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}
