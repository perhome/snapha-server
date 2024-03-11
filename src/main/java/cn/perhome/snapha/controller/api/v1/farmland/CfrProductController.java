package cn.perhome.snapha.controller.api.v1.farmland;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "种植管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/cfr-product")
public class CfrProductController {
}
