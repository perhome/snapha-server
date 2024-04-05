package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.dto.form.FormCfrProductDto;
import cn.perhome.snapha.entity.CfrProductEntity;
import cn.perhome.snapha.entity.CfrWorkspaceEntity;
import cn.perhome.snapha.mapper.CfrProductMapper;
import cn.perhome.snapha.mapper.CfrWorkspaceMapper;
import cn.perhome.snapha.mapper.WorkspaceMapper;
import cn.perhome.snapha.service.CfrProductService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CfrProductServiceImpl extends ServiceImpl<CfrProductMapper, CfrProductEntity>
        implements CfrProductService {

    private final CfrProductMapper   cfrProductMapper;
    private final CfrWorkspaceMapper cfrWorkspaceMapper;
    private final WorkspaceMapper    workspaceMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean cfrCreate(FormCfrProductDto form) {
        boolean isSuccess = this.save(form);
        var cpid = form.getCpid();
        for(var wid : form.getWorkspaceIds()) {
            var workspace = this.workspaceMapper.selectOneById(wid);
            var entity = new CfrWorkspaceEntity();
            entity.setCfrProductId(cpid);
            entity.setWorkspaceId(wid);
            entity.setStartDate(form.getStartDate());
            entity.setHarvestDate(form.getHarvestDate());
            entity.setEndDate(form.getEndDate());
            entity.setArea(workspace.getArea());
            this.cfrWorkspaceMapper.insert(entity);
        }
        return true;
    }
}

