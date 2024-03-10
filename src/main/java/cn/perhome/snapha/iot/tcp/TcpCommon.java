package cn.perhome.snapha.iot.tcp;

import cn.perhome.snapha.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public  class TcpCommon {

    static Map<String, String >  mapConnection = new HashMap<>();

    public static Boolean isAliveDevice(String connection_id) {
        AbstractServerConnectionFactory connFactory = (AbstractServerConnectionFactory) SpringContextUtils.getApplicationContext().getBean("sty.auto.serverConnectionFactory");
        List<String> openConns = connFactory.getOpenConnectionIds();

        if(openConns.isEmpty()){
            mapConnection.clear();
            return false;
        }
        if (openConns.contains(connection_id)) return true;

        for (Map.Entry<String, String> entry : mapConnection.entrySet()) {
            if (connection_id.equals(entry.getValue())) {
                mapConnection.remove(entry.getKey());
            }
        }
        return false;
    }

}
