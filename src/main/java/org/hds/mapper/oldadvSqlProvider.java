package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.oldadv;

public class oldadvSqlProvider {

    public String insertSelective(oldadv record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_oldadv");
        
        if (record.getOldadvsn() != null) {
            sql.VALUES("oldadvsn", "#{oldadvsn,jdbcType=INTEGER}");
        }
        
        if (record.getInfosn() != null) {
            sql.VALUES("infoSN", "#{infosn,jdbcType=INTEGER}");
        }
        
        if (record.getAdvname() != null) {
            sql.VALUES("advName", "#{advname,jdbcType=VARCHAR}");
        }
        
        if (record.getGroupid() != null) {
            sql.VALUES("groupid", "#{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getLifeact() != null) {
            sql.VALUES("lifeAct", "#{lifeact,jdbcType=VARCHAR}");
        }
        
        if (record.getLifedie() != null) {
            sql.VALUES("lifeDie", "#{lifedie,jdbcType=VARCHAR}");
        }
        
        if (record.getAdvtype() != null) {
            sql.VALUES("advType", "#{advtype,jdbcType=VARCHAR}");
        }
        
        if (record.getInfostate() != null) {
            sql.VALUES("infoState", "#{infostate,jdbcType=INTEGER}");
        }
        
        if (record.getCreater() != null) {
            sql.VALUES("creater", "#{creater,jdbcType=INTEGER}");
        }
        
        if (record.getCreatedate() != null) {
            sql.VALUES("createDate", "#{createdate,jdbcType=VARCHAR}");
        }
        
        if (record.getAuditor() != null) {
            sql.VALUES("auditor", "#{auditor,jdbcType=VARCHAR}");
        }
        
        if (record.getAuditdate() != null) {
            sql.VALUES("auditDate", "#{auditdate,jdbcType=VARCHAR}");
        }
        
        if (record.getPublisher() != null) {
            sql.VALUES("publisher", "#{publisher,jdbcType=INTEGER}");
        }
        
        if (record.getPublishdate() != null) {
            sql.VALUES("publishDate", "#{publishdate,jdbcType=VARCHAR}");
        }
        
        if (record.getDeleter() != null) {
            sql.VALUES("deleter", "#{deleter,jdbcType=VARCHAR}");
        }
        
        if (record.getDeletedate() != null) {
            sql.VALUES("deleteDate", "#{deletedate,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaymode() != null) {
            sql.VALUES("playMode", "#{playmode,jdbcType=VARCHAR}");
        }
        
        if (record.getPubid() != null) {
            sql.VALUES("pubid", "#{pubid,jdbcType=INTEGER}");
        }
        
        if (record.getPlaytimelength() != null) {
            sql.VALUES("playTimelength", "#{playtimelength,jdbcType=INTEGER}");
        }
        
        if (record.getBackgroundstyle() != null) {
            sql.VALUES("BackgroundStyle", "#{backgroundstyle,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getItemlist() != null) {
            sql.VALUES("itemlist", "#{itemlist,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(oldadv record) {
        SQL sql = new SQL();
        sql.UPDATE("t_oldadv");
        
        if (record.getInfosn() != null) {
            sql.SET("infoSN = #{infosn,jdbcType=INTEGER}");
        }
        
        if (record.getAdvname() != null) {
            sql.SET("advName = #{advname,jdbcType=VARCHAR}");
        }
        
        if (record.getGroupid() != null) {
            sql.SET("groupid = #{groupid,jdbcType=INTEGER}");
        }
        
        if (record.getLifeact() != null) {
            sql.SET("lifeAct = #{lifeact,jdbcType=VARCHAR}");
        }
        
        if (record.getLifedie() != null) {
            sql.SET("lifeDie = #{lifedie,jdbcType=VARCHAR}");
        }
        
        if (record.getAdvtype() != null) {
            sql.SET("advType = #{advtype,jdbcType=VARCHAR}");
        }
        
        if (record.getInfostate() != null) {
            sql.SET("infoState = #{infostate,jdbcType=INTEGER}");
        }
        
        if (record.getCreater() != null) {
            sql.SET("creater = #{creater,jdbcType=INTEGER}");
        }
        
        if (record.getCreatedate() != null) {
            sql.SET("createDate = #{createdate,jdbcType=VARCHAR}");
        }
        
        if (record.getAuditor() != null) {
            sql.SET("auditor = #{auditor,jdbcType=VARCHAR}");
        }
        
        if (record.getAuditdate() != null) {
            sql.SET("auditDate = #{auditdate,jdbcType=VARCHAR}");
        }
        
        if (record.getPublisher() != null) {
            sql.SET("publisher = #{publisher,jdbcType=INTEGER}");
        }
        
        if (record.getPublishdate() != null) {
            sql.SET("publishDate = #{publishdate,jdbcType=VARCHAR}");
        }
        
        if (record.getDeleter() != null) {
            sql.SET("deleter = #{deleter,jdbcType=VARCHAR}");
        }
        
        if (record.getDeletedate() != null) {
            sql.SET("deleteDate = #{deletedate,jdbcType=VARCHAR}");
        }
        
        if (record.getPlaymode() != null) {
            sql.SET("playMode = #{playmode,jdbcType=VARCHAR}");
        }
        
        if (record.getPubid() != null) {
            sql.SET("pubid = #{pubid,jdbcType=INTEGER}");
        }
        
        if (record.getPlaytimelength() != null) {
            sql.SET("playTimelength = #{playtimelength,jdbcType=INTEGER}");
        }
        
        if (record.getBackgroundstyle() != null) {
            sql.SET("BackgroundStyle = #{backgroundstyle,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getItemlist() != null) {
            sql.SET("itemlist = #{itemlist,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("oldadvsn = #{oldadvsn,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}