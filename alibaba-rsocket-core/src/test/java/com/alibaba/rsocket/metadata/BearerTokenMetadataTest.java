package com.alibaba.rsocket.metadata;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.rsocket.metadata.security.AuthMetadataFlyweight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bearer token metadata test
 *
 * @author leijuan
 */
public class BearerTokenMetadataTest {

    @Test
    public void testEncodingAndDecoding() {
        String token = "123456";
        BearerTokenMetadata tokenMetadata = BearerTokenMetadata.jwt(token);
        Assertions.assertEquals(token.length() + 1, tokenMetadata.getContent().capacity());
        BearerTokenMetadata tokenMetadata1 = BearerTokenMetadata.from(tokenMetadata.getContent());
        Assertions.assertEquals(tokenMetadata1.getBearerToken(), token);
        ByteBuf byteBuf = AuthMetadataFlyweight.encodeBearerMetadata(ByteBufAllocator.DEFAULT, token.toCharArray());
        assertThat(toArrayString(tokenMetadata.getContent())).isEqualTo(toArrayString(byteBuf));
    }

    private String toArrayString(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.capacity()];
        byteBuf.readBytes(bytes);
        return Arrays.toString(bytes);
    }
}
