package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.entity.SerialEntity;
import cn.perhome.snapha.mapper.SerialMapper;
import cn.perhome.snapha.service.SerialService;
import jakarta.annotation.Resource;

public class SerialServiceImpl  implements SerialService {
    @Resource
    private SerialMapper serialMapper;

    @Override
    public int getActionId() {
        SerialEntity serial = this.serialMapper.getValue("action_id_seq");
        return serial.getValue();
    }

    @Override
    public int getValue(String serialName) {
        SerialEntity serial = this.serialMapper.getValue(serialName);
        return serial.getValue();
    }

    @Override
    public int getValue(String serialName, int offsetNum) {
        SerialEntity serial = this.serialMapper.getValue(serialName);
        int ret = serial.getValue() + offsetNum;
        return ret;
    }
}
