package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.statistic;

public class statisticSqlProvider {

    public String insertSelective(statistic record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_statistics");
                
        if (record.getRecordingtime() != null) {
            sql.VALUES("RecordingTime", "#{recordingtime,jdbcType=VARCHAR}");
        }        
        
        if (record.getTotal() != null) {
            sql.VALUES("Total", "#{total,jdbcType=INTEGER}");
        }
        
        if (record.getDtu() != null) {
            sql.VALUES("DTU", "#{dtu,jdbcType=INTEGER}");
        }
        
        if (record.getLed() != null) {
            sql.VALUES("LED", "#{led,jdbcType=INTEGER}");
        }
        
        if (record.getUpdated() != null) {
            sql.VALUES("Updated", "#{updated,jdbcType=INTEGER}");
        }
        
        if (record.getWaiting() != null) {
            sql.VALUES("Waiting", "#{waiting,jdbcType=INTEGER}");
        }
        
        if (record.getRenewable() != null) {
            sql.VALUES("Renewable", "#{renewable,jdbcType=INTEGER}");
        }
        
        if (record.getProjectid() != null) {
            sql.VALUES("projectid", "#{projectid,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateRate() != null) {
            sql.VALUES("UpdateRate", "#{UpdateRate,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}