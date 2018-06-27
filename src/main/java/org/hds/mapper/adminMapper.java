package org.hds.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.admin;

public interface adminMapper {
    @Delete({
        "delete from t_admin",
        "where adminID = #{adminid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer adminid);

    @Insert({
        "insert into t_admin (adminID, adminName, ",
        "adminPwd, adminPermission, ",
        "adminGrps, isSuperuser, ",
        "DelIndex)",
        "values (#{adminid,jdbcType=INTEGER}, #{adminname,jdbcType=VARCHAR}, ",
        "#{adminpwd,jdbcType=VARCHAR}, #{adminpermission,jdbcType=VARCHAR}, ",
        "#{admingrps,jdbcType=VARCHAR}, #{issuperuser,jdbcType=INTEGER}, ",
        "#{delindex,jdbcType=INTEGER})"
    })
    int insert(admin record);

    @InsertProvider(type=adminSqlProvider.class, method="insertSelective")
    int insertSelective(admin record);

    @Select({
        "select",
        "adminID, adminName, adminPwd, adminPermission, adminGrps, isSuperuser, DelIndex",
        "from t_admin",
        "where adminName = #{adminName,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="adminID", property="adminid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="adminName", property="adminname", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPwd", property="adminpwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPermission", property="adminpermission", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminGrps", property="admingrps", jdbcType=JdbcType.VARCHAR),
        @Result(column="isSuperuser", property="issuperuser", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    admin selectByPrimaryName(String adminName);

    @UpdateProvider(type=adminSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(admin record);

    @Update({
        "update t_admin",
        "set adminName = #{adminname,jdbcType=VARCHAR},",
          "adminPwd = #{adminpwd,jdbcType=VARCHAR},",
          "adminPermission = #{adminpermission,jdbcType=VARCHAR},",
          "adminGrps = #{admingrps,jdbcType=VARCHAR},",
          "isSuperuser = #{issuperuser,jdbcType=INTEGER},",
          "DelIndex = #{delindex,jdbcType=INTEGER}",
        "where adminID = #{adminid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(admin record);
}