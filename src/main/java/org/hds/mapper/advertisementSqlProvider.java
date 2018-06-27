package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.advertisement;

public class advertisementSqlProvider {

    public String insertSelective(advertisement record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_advertisement");
        
        if (record.getId() != null) {
            sql.VALUES("Id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getAdvname() != null) {
            sql.VALUES("advName", "#{advname,jdbcType=VARCHAR}");
        }
        
        if (record.getPubid() != null) {
            sql.VALUES("pubid", "#{pubid,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(advertisement record) {
        SQL sql = new SQL();
        sql.UPDATE("t_advertisement");
        
        if (record.getAdvname() != null) {
            sql.SET("advName = #{advname,jdbcType=VARCHAR}");
        }
        
        if (record.getPubid() != null) {
            sql.SET("pubid = #{pubid,jdbcType=INTEGER}");
        }
        
        sql.WHERE("Id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}