package cn.perhome.snapha.iot.tcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.IpHeaders;

import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.*;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@EnableIntegration
@Configuration
public class TcpServerConfig {

    @Value("${snapha.iot.server.port:8086}")
    private int PORT;

    @Bean(value = "snapha.iot.serverConnectionFactory")
    public AbstractServerConnectionFactory serverConnectionFactory() {
        TcpNetServerConnectionFactory serverConnectionFactory = new TcpNetServerConnectionFactory(PORT);
        serverConnectionFactory.setSoKeepAlive(true);
        serverConnectionFactory.setSoTcpNoDelay(true);
        // serverConnectionFactory.setSoTimeout(0);
        ByteArrayRawAutoSerializer rawAuto = new ByteArrayRawAutoSerializer(true);
        // init
        rawAuto.setMaxMessageSize(9);
        serverConnectionFactory.setSerializer(rawAuto);
        serverConnectionFactory.setDeserializer(rawAuto);

        return serverConnectionFactory;
    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outboundChannel() { return new DirectChannel();  }

    @Bean
    public MessageChannel autoboundChannel() { return new DirectChannel();  }

    @Bean
    public MessageChannel queueboundChannel() { return new QueueChannel();  }


    // Inbound channel adapter
    @Bean
    public TcpReceivingChannelAdapter receivingChannelAdapter(AbstractServerConnectionFactory serverConnectionFactory, MessageChannel autoboundChannel) {
        TcpReceivingChannelAdapter tcpReceivingChannelAdapter = new TcpReceivingChannelAdapter();
        tcpReceivingChannelAdapter.setConnectionFactory(serverConnectionFactory);
        tcpReceivingChannelAdapter.setOutputChannel(autoboundChannel);
        return tcpReceivingChannelAdapter;
    }

    // Outbound channel adapter
    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    public TcpSendingMessageHandler tcpSendingMessageHandler(AbstractServerConnectionFactory serverConnectionFactory) {
        TcpSendingMessageHandler tcpSendingMessageHandler = new TcpSendingMessageHandler();
        tcpSendingMessageHandler.setConnectionFactory(serverConnectionFactory);
        return tcpSendingMessageHandler;
    }

    // Send greeting -> now working
    @Bean
    public ApplicationListener<TcpConnectionEvent> listener(MessageChannel outboundChannel) {
        return tcpConnectionEvent -> {
            if (tcpConnectionEvent instanceof TcpConnectionOpenEvent) {
                Message<String> message = MessageBuilder
                        .withPayload("Hello IOT!")
                        .setHeader(IpHeaders.CONNECTION_ID, tcpConnectionEvent.getConnectionId())
                        .build();
                outboundChannel.send(message);
            }
        };
    }

}