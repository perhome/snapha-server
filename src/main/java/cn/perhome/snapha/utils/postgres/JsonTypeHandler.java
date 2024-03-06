package cn.perhome.snapha.utils.postgres;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.perhome.snapha.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

@MappedTypes({ JsonNode.class })
@Slf4j
public class JsonTypeHandler extends BaseTypeHandler<JsonNode> {

    private static final PGobject jsonObject = new PGobject();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType)
            throws SQLException {
        if (ps != null) {
            jsonObject.setType("jsonb");
            jsonObject.setValue(parameter.toString());
            ps.setObject(i, jsonObject);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getObject(columnName) ==null) return null;
        try {
            if (rs.getObject(columnName) ==null) return null;
            return  JsonUtils.getObjectMapper().reader().readTree(rs.getObject(columnName).toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) ==null) return null;
        try {
            return JsonUtils.getObjectMapper().reader().readTree(rs.getObject(columnIndex).toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) ==null) return null;
        try {
            return JsonUtils.getObjectMapper().reader().readTree(cs.getObject(columnIndex).toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
