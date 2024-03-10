package cn.perhome.snapha.websocket.service;


import com.googlecode.jsonrpc4j.JsonRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@JsonRpcService("websocket.api")
public class SystemApiService {

    public String ping() {
        return "PONG";
    }
}
