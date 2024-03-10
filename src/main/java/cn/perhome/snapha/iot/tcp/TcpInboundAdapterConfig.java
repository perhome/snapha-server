package cn.perhome.snapha.iot.tcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import cn.perhome.snapha.utils.StringUtils;


@Component
@Slf4j
public class TcpInboundAdapterConfig {

    /* 针对不同的传感器发送相应的问询帧 */
    @InboundChannelAdapter(channel = "outboundChannel", poller = @Poller(fixedRate = "5000"))
    public Message<byte[]> pushCommandToOutboundChannel_1(){
        String device = "snapha--0001";
        String connection_id = TcpCommon.mapConnection.get(device);
        if (connection_id == null || !TcpCommon.isAliveDevice( connection_id ) ) return null;
        return MessageBuilder
                .withPayload(StringUtils.fromHexString("01 03 00 00 00 02 c4 0b"))
                .setHeader(IpHeaders.CONNECTION_ID, connection_id)
                .build();
    }

}
