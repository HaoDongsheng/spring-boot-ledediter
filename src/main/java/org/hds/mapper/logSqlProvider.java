package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.log;

public class logSqlProvider {

    public String insertSelective(log record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_log");
        
        if (record.getLogtime() != null) {
            sql.VALUES("logtime", "#{logtime,jdbcType=VARCHAR}");
        }
        
        if (record.getAdminid() != null) {
            sql.VALUES("adminid", "#{adminid,jdbcType=INTEGER}");
        }
        
        if (record.getFunctionname() != null) {
            sql.VALUES("functionName", "#{functionname,jdbcType=VARCHAR}");
        }
        
        if (record.getOperatetype() != null) {
            sql.VALUES("operateType", "#{operatetype,jdbcType=INTEGER}");
        }
        
        if (record.getOperate() != null) {
            sql.VALUES("operate", "#{operate,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }
}