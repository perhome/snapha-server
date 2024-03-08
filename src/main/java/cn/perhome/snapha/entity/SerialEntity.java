package cn.perhome.snapha.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SerialEntity implements Serializable {
    private String name;
    private Integer value;
}