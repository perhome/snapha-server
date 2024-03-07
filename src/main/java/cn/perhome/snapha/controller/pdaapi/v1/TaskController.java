package cn.perhome.snapha.controller.pdaapi.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('USER')")
@Tag(name = "任务单")
@Slf4j
@RestController
@RequestMapping("/pdaapi/v1/task")
@RequiredArgsConstructor
public class TaskController {

}
