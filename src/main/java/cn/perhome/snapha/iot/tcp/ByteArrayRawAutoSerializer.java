package cn.perhome.snapha.iot.tcp;


import org.springframework.integration.ip.tcp.serializer.AbstractPooledBufferByteArraySerializer;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;

public class ByteArrayRawAutoSerializer extends AbstractPooledBufferByteArraySerializer {
    public static final org.springframework.integration.ip.tcp.serializer.ByteArrayRawSerializer INSTANCE = new org.springframework.integration.ip.tcp.serializer.ByteArrayRawSerializer();
    private final       boolean                                                                  treatTimeoutAsEndOfMessage;

    public ByteArrayRawAutoSerializer() {
        this(false);
    }

    public ByteArrayRawAutoSerializer(boolean treatTimeoutAsEndOfMessage) {
        this.treatTimeoutAsEndOfMessage = treatTimeoutAsEndOfMessage;
    }

    public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
        outputStream.write(bytes);
    }

    protected byte[] doDeserialize(InputStream inputStream, byte[] buffer) throws IOException {
        int n = 0;
        int available = inputStream.available();
        this.logger.debug(() -> {
            return "Available to read: " + available;
        });

        try {
            while(true) {
                int bite;
                try {
                    bite = inputStream.read();
                } catch (SocketTimeoutException var7) {
                    if (!this.treatTimeoutAsEndOfMessage) {
                        throw var7;
                    }

                    bite = -1;
                }

                if (bite < 0) {
                    if (n == 0) {
                        throw new SoftEndOfStreamException("Stream closed between payloads");
                    }

                    return this.copyToSizedArray(buffer, n);
                }

                buffer[n++] = (byte)bite;

                // 根据数据长度，决定是否返回数据包

                int maxMessageSize = this.getMaxMessageSize();
                if (n == maxMessageSize) {
                    return this.copyToSizedArray(buffer, n);
                }
            }
        } catch (SoftEndOfStreamException var8) {
            throw var8;
        } catch (RuntimeException | IOException var9) {
            this.publishEvent(var9, buffer, n);
            throw var9;
        }
    }
}
