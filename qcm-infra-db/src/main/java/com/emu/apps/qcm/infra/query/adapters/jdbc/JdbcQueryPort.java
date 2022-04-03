package com.emu.apps.qcm.infra.query.adapters.jdbc;

import com.emu.apps.qcm.infra.query.QueryPort;
import com.emu.apps.qcm.infra.query.adapters.jdbc.dto.Field;
import com.emu.apps.qcm.infra.query.adapters.jdbc.dto.Results;
import com.emu.apps.qcm.infra.query.adapters.jdbc.dto.Row;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class JdbcQueryPort implements QueryPort {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcQueryPort(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Results executeQuery(String name, String sql) {

        List <Row> rows = namedParameterJdbcTemplate.query(sql, new RowMapper <Row>() {
            @Override
            public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                Row row = new Row(columnCount);

                for (int index = 1; index <= columnCount; index++) {
                    try {
                        String column = JdbcUtils.lookupColumnName(metaData, index);
                        Object value = JdbcUtils.getResultSetValue(rs, index, Class.forName(metaData.getColumnClassName(index)));
                        row.getFields()[index - 1] = new Field(column, value);
                    } catch (ClassNotFoundException e) {
                    }
                }
                return row;
            }

        });

        return new Results(name, rows);
    }
}
