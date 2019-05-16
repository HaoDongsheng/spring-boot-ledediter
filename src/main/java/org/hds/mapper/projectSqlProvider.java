package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.project;

public class projectSqlProvider {

    public String insertSelective(project record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_project");
        
        if (record.getProjectid() != null) {
            sql.VALUES("projectId", "#{projectid,jdbcType=VARCHAR}");
        }
        
        if (record.getProjectname() != null) {
            sql.VALUES("projectName", "#{projectname,jdbcType=VARCHAR}");
        }
        
        if (record.getAutoGroupTo() != null) {
            sql.VALUES("AutoGroupTo", "#{AutoGroupTo,jdbcType=INTEGER}");
        }
        
        if (record.getIsOurModule() != null) {
            sql.VALUES("IsOurModule", "#{IsOurModule,jdbcType=INTEGER}");
        }
        
        if (record.getConnectParameters() != null) {
            sql.VALUES("ConnectParameters", "#{ConnectParameters,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(project record) {
        SQL sql = new SQL();
        sql.UPDATE("t_project");
        
        if (record.getProjectname() != null) {
            sql.SET("projectName = #{projectname,jdbcType=VARCHAR}");
        }
        
        if (record.getAutoGroupTo() != null) {
            sql.SET("AutoGroupTo = #{AutoGroupTo,jdbcType=INTEGER}");
        }
        
        if (record.getIsOurModule() != null) {
            sql.SET("IsOurModule = #{IsOurModule,jdbcType=INTEGER}");
        }
        
        if (record.getConnectParameters() != null) {
            sql.SET("ConnectParameters = #{ConnectParameters,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("projectId = #{projectid,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}