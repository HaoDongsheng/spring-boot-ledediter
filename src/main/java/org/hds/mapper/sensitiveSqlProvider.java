package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.sensitive;

public class sensitiveSqlProvider {

    public String insertSelective(sensitive record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_sensitive");
        
        if (record.getId() != null) {
            sql.VALUES("Id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("projectid", "#{projectid,jdbcType=VARCHAR}");
        }
        
        if (record.getSensitivestring() != null) {
            sql.VALUES("sensitiveString", "#{sensitivestring,jdbcType=VARCHAR}");
        }
        
        if (record.getDelindex() != null) {
            sql.VALUES("delIndex", "#{delindex,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(sensitive record) {
        SQL sql = new SQL();
        sql.UPDATE("t_sensitive");
        
        if (record.getProjectid() != null) {
            sql.SET("projectid = #{projectid,jdbcType=VARCHAR}");
        }
        
        if (record.getSensitivestring() != null) {
            sql.SET("sensitiveString = #{sensitivestring,jdbcType=VARCHAR}");
        }
        
        if (record.getDelindex() != null) {
            sql.SET("delIndex = #{delindex,jdbcType=INTEGER}");
        }
        
        sql.WHERE("Id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}