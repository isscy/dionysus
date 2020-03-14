package cn.ff.dionysus.common.security.component;

import cn.ff.dionysus.common.security.exception.DefaultAuthException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

/**
 * 权限相关的异常格式化
 *
 * @author fengfan 2020/2/25
 */
public class AuthExceptionSerializer extends StdSerializer<DefaultAuthException> {

    public AuthExceptionSerializer() {
        super(DefaultAuthException.class);
    }

    @Override
    @SneakyThrows
    public void serialize(DefaultAuthException value, JsonGenerator gen, SerializerProvider provider) {
        gen.writeStartObject();
        gen.writeObjectField("code", value.getErrorCode());
        gen.writeStringField("message", value.getMessage());
        gen.writeStringField("EXT", value.getEXT());
        gen.writeEndObject();
    }
}
