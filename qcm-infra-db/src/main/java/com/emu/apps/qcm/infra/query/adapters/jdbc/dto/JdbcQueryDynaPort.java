package com.emu.apps.qcm.infra.query.adapters.jdbc.dto;

import com.emu.apps.qcm.infra.query.QueryPort;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaBeanPropertyMapDecorator;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class JdbcQueryDynaPort implements QueryPort {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcQueryDynaPort(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Results executeQuery(String name, String sql) {

        List<DynaBean> beans = new ArrayList <>() ;
        namedParameterJdbcTemplate.query(sql, resultSet ->   {
            RowSetDynaClass rsdc = new RowSetDynaClass(resultSet, true);
            beans.addAll(rsdc.getRows());
        });

        return new Results(name, beans);
    }
}
