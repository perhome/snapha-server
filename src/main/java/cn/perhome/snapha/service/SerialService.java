package cn.perhome.snapha.service;

import org.springframework.stereotype.Service;

public interface SerialService {

    int getActionId();

    int getValue(String serialName);

    int getValue(String serialName, int offsetNum);
}
