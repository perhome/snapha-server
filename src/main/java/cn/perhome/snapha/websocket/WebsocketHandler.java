package cn.perhome.snapha.websocket;

import cn.perhome.snapha.utils.JsonUtils;
import cn.perhome.snapha.websocket.service.SystemApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcBasicServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class WebsocketHandler implements WebSocketHandler {
    // 在线客户端列表
    private static final Map<String, WebSocketSession> clients;

    private final JsonRpcBasicServer server;
    static {
        clients = new HashMap<>();
    }

    private WebsocketHandler(SystemApiService systemApiService) {
        ObjectMapper       mapper = JsonUtils.getObjectMapper();
        server = new JsonRpcBasicServer(mapper, systemApiService);
    }

    // 新增socket
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String ID = session.getId();
        clients.put(ID, session);
        log.info("成功建立连接 => {}, 当前链接数 => {}", ID, clients.size());
    }

    //接收socket信息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

        OutputStream out = new ByteArrayOutputStream();
        byte[] bytes;
        if (webSocketMessage instanceof BinaryMessage binaryMessage) {
            bytes = binaryMessage.getPayload().array();
        } else {
            bytes = webSocketMessage.getPayload().toString().getBytes();
        }
        InputStream  in  = new ByteArrayInputStream(bytes);
        server.handleRequest(in, out);
        webSocketSession.sendMessage(new TextMessage(out.toString()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        clients.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);
        String ID = session.getId();
        clients.remove(ID);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取客户端标识
     *
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        try {
            return (String) session.getAttributes().get("WEBSOCKET_SESSION_ID");
        } catch (Exception e) {
            return null;
        }
    }
}
