package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.item;

public class itemSqlProvider {

    public String insertSelective(item record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_item");
        
        if (record.getAdid() != null) {
            sql.VALUES("adid", "#{adid,jdbcType=INTEGER}");
        }
        
        if (record.getPageid() != null) {
            sql.VALUES("pageid", "#{pageid,jdbcType=INTEGER}");
        }
        
        if (record.getItemid() != null) {
            sql.VALUES("itemid", "#{itemid,jdbcType=INTEGER}");
        }
        
        if (record.getItemleft() != null) {
            sql.VALUES("itemleft", "#{itemleft,jdbcType=INTEGER}");
        }
        
        if (record.getItemtop() != null) {
            sql.VALUES("itemtop", "#{itemtop,jdbcType=INTEGER}");
        }
        
        if (record.getItemwidth() != null) {
            sql.VALUES("itemwidth", "#{itemwidth,jdbcType=INTEGER}");
        }
        
        if (record.getItemheight() != null) {
            sql.VALUES("itemheight", "#{itemheight,jdbcType=VARCHAR}");
        }
        
        if (record.getItembackcolor() != null) {
            sql.VALUES("itembackcolor", "#{itembackcolor,jdbcType=VARCHAR}");
        }
        
        if (record.getItemtype() != null) {
            sql.VALUES("itemtype", "#{itemtype,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.VALUES("delindex", "#{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getItemcontext() != null) {
            sql.VALUES("itemContext", "#{itemcontext,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(item record) {
        SQL sql = new SQL();
        sql.UPDATE("t_item");
        
        if (record.getItemleft() != null) {
            sql.SET("itemleft = #{itemleft,jdbcType=INTEGER}");
        }
        
        if (record.getItemtop() != null) {
            sql.SET("itemtop = #{itemtop,jdbcType=INTEGER}");
        }
        
        if (record.getItemwidth() != null) {
            sql.SET("itemwidth = #{itemwidth,jdbcType=INTEGER}");
        }
        
        if (record.getItemheight() != null) {
            sql.SET("itemheight = #{itemheight,jdbcType=VARCHAR}");
        }
        
        if (record.getItembackcolor() != null) {
            sql.SET("itembackcolor = #{itembackcolor,jdbcType=VARCHAR}");
        }
        
        if (record.getItemtype() != null) {
            sql.SET("itemtype = #{itemtype,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.SET("delindex = #{delindex,jdbcType=INTEGER}");
        }
        
        if (record.getItemcontext() != null) {
            sql.SET("itemContext = #{itemcontext,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("adid = #{adid,jdbcType=INTEGER}");
        sql.WHERE("pageid = #{pageid,jdbcType=INTEGER}");
        sql.WHERE("itemid = #{itemid,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}