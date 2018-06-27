package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.admin;

public class adminSqlProvider {

    public String insertSelective(admin record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_admin");
        
        if (record.getAdminid() != null) {
            sql.VALUES("adminID", "#{adminid,jdbcType=INTEGER}");
        }
        
        if (record.getAdminname() != null) {
            sql.VALUES("adminName", "#{adminname,jdbcType=VARCHAR}");
        }
        
        if (record.getAdminpwd() != null) {
            sql.VALUES("adminPwd", "#{adminpwd,jdbcType=VARCHAR}");
        }
        
        if (record.getAdminpermission() != null) {
            sql.VALUES("adminPermission", "#{adminpermission,jdbcType=VARCHAR}");
        }
        
        if (record.getAdmingrps() != null) {
            sql.VALUES("adminGrps", "#{admingrps,jdbcType=VARCHAR}");
        }
        
        if (record.getIssuperuser() != null) {
            sql.VALUES("isSuperuser", "#{issuperuser,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.VALUES("DelIndex", "#{delindex,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(admin record) {
        SQL sql = new SQL();
        sql.UPDATE("t_admin");
        
        if (record.getAdminname() != null) {
            sql.SET("adminName = #{adminname,jdbcType=VARCHAR}");
        }
        
        if (record.getAdminpwd() != null) {
            sql.SET("adminPwd = #{adminpwd,jdbcType=VARCHAR}");
        }
        
        if (record.getAdminpermission() != null) {
            sql.SET("adminPermission = #{adminpermission,jdbcType=VARCHAR}");
        }
        
        if (record.getAdmingrps() != null) {
            sql.SET("adminGrps = #{admingrps,jdbcType=VARCHAR}");
        }
        
        if (record.getIssuperuser() != null) {
            sql.SET("isSuperuser = #{issuperuser,jdbcType=INTEGER}");
        }
        
        if (record.getDelindex() != null) {
            sql.SET("DelIndex = #{delindex,jdbcType=INTEGER}");
        }
        
        sql.WHERE("adminID = #{adminid,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}