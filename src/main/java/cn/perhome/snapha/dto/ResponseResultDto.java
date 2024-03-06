package cn.perhome.snapha.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class ResponseResultDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3556321234564L;

    public static final Integer SUCCESS_STATUS = 0;

    public static final String SUCCESS = "success";

    private Integer status;

    private String error;

    private Object data;

    /////////////////////////////////////////////////////////////////

    public static ResponseResultDto success(Object data) {
        return new ResponseResultDto(SUCCESS, SUCCESS_STATUS, data);
    }

    public static ResponseResultDto failed(Integer errorCode, String message) {
        return new ResponseResultDto(message, errorCode);
    }
    public static ResponseResultDto failed(Integer errorCode, String message, Object data) {
        return new ResponseResultDto(message, errorCode, data);
    }

    /////////////////////////////////////////////////////////////////

    public ResponseResultDto(String message, Integer code) {
        this.error = message;
        this.status = code;
    }

    public ResponseResultDto(String message, Integer code, Object data) {
        this.error = message;
        this.status = code;
        this.data = data;
    }

}
