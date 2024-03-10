package cn.perhome.snapha.iot.tcp;


import cn.perhome.snapha.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@MessageEndpoint
@Slf4j
public class TcpMessageService {

    public String preDeviceId = "7374792d2d";

    @Transformer(inputChannel = "autoboundChannel", outputChannel = "outboundChannel")
    public Message<byte[]> saveDataFromDevice(Message<byte[]> message, @Header(IpHeaders.CONNECTION_ID) String connection_id) {
        byte[] byteMsg = message.getPayload();
        log.info("get message => {}", byteMsg);
        String hexMsg  = StringUtils.toHexString(byteMsg);
        log.info("get message => {}", hexMsg);

        assert hexMsg != null;
        if (hexMsg.startsWith(this.preDeviceId)) {
           TcpCommon.mapConnection.put(new String(byteMsg), connection_id);
           return null;
       }

       String hexTemperature = hexMsg.substring(10, 14);
       String hexHumidity = hexMsg.substring(6, 10);
       int intTemperature = Integer.parseInt(hexTemperature, 16);
       int intHumidity = Integer.parseInt(hexHumidity, 16);

       log.info("intTemperature => {}", intTemperature);
       log.info("intHumidity => {}", intHumidity);

        return MessageBuilder.withPayload("PONG".getBytes())
                .copyHeaders(message.getHeaders())
                .build();
    }

}