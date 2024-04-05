package cn.perhome.snapha.service;

import cn.perhome.snapha.dto.form.FormCfrProductDto;
import cn.perhome.snapha.entity.CfrProductEntity;
import com.mybatisflex.core.service.IService;


public interface CfrProductService extends IService<CfrProductEntity> {
    boolean cfrCreate(FormCfrProductDto form);
}
