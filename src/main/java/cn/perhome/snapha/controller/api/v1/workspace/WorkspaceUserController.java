package cn.perhome.snapha.controller.api.v1.workspace;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.entity.WorkspaceUserEntity;
import cn.perhome.snapha.mapper.WorkspaceUserMapper;
import cn.perhome.snapha.service.WorkspaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "工作区管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/workspace-user")
@RequiredArgsConstructor
public class WorkspaceUserController {

    private final WorkspaceService    workspaceService;
    private final WorkspaceUserMapper workspaceUserMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(
            @RequestBody WorkspaceUserEntity form) {

        boolean isSuccess = workspaceService.setWorkspaceUserById(form);
        ResponseResultDto responseResultDto
                = isSuccess ? ResponseResultDto.success(true)
                : ResponseResultDto.failed(500, "failed to save");

        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }


    @RequestMapping(value = "group", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> postGroup(
            @RequestBody WorkspaceUserEntity form) {

        boolean isSuccess = workspaceService.setWorkspaceUserGroupById(form.getWorkspaceId(), form.getGroupId());
        ResponseResultDto responseResultDto
                = isSuccess ? ResponseResultDto.success(true)
                : ResponseResultDto.failed(500, "failed to save");

        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}
