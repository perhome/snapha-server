package cn.perhome.snapha.service;

public interface SerialService {

    int getActionId();

    int getValue(String serialName);

    int getValue(String serialName, int offsetNum);
}
