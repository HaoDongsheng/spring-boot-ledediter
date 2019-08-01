package org.hds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.user;

public interface userMapper {
    @Delete({
        "delete from t_user",
        "where adminID = #{adminid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer adminid);
    
    @Delete({
        "delete from t_user",
        "where projectid = #{0}"
    })
    int deleteByprojectid(String projectid);

    @Insert({
        "insert into t_user (adminID, adminName, ",
        "adminPwd, adminStatus, ",
        "expDate, adminPermission, ",
        "projectid, groupids, adminLevel, ",
        "parentId, inherit, ",
        "GrpCount, adminCount, ",
        "isSuperuser, DelIndex)",
        "values (#{adminid,jdbcType=INTEGER}, #{adminname,jdbcType=VARCHAR}, ",
        "#{adminpwd,jdbcType=VARCHAR}, #{adminstatus,jdbcType=INTEGER}, ",
        "#{expdate,jdbcType=VARCHAR}, #{adminpermission,jdbcType=VARCHAR}, ",
        "#{projectid,jdbcType=VARCHAR}, #{groupids,jdbcType=VARCHAR}, #{adminlevel,jdbcType=INTEGER}, ",
        "#{parentid,jdbcType=INTEGER}, #{inherit,jdbcType=INTEGER}, ",
        "#{grpcount,jdbcType=INTEGER}, #{admincount,jdbcType=INTEGER}, ",
        "#{issuperuser,jdbcType=INTEGER}, #{delindex,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true, keyProperty="adminid",keyColumn="adminid")//添加该行，product中的id将被自动添加
    int insert(user record);

    @InsertProvider(type=userSqlProvider.class, method="insertSelective")
    int insertSelective(user record);

    @Select({
        "select",
        "adminID, adminName, adminPwd, adminStatus, expDate, adminPermission, projectid, groupids,",
        "adminLevel, parentId, inherit, GrpCount, adminCount, isSuperuser, DelIndex",
        "from t_user",
        "where adminID = #{adminid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="adminID", property="adminid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="adminName", property="adminname", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPwd", property="adminpwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminStatus", property="adminstatus", jdbcType=JdbcType.INTEGER),
        @Result(column="expDate", property="expdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPermission", property="adminpermission", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupids", property="groupids", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminLevel", property="adminlevel", jdbcType=JdbcType.INTEGER),
        @Result(column="parentId", property="parentid", jdbcType=JdbcType.INTEGER),
        @Result(column="inherit", property="inherit", jdbcType=JdbcType.INTEGER),
        @Result(column="GrpCount", property="grpcount", jdbcType=JdbcType.INTEGER),
        @Result(column="adminCount", property="admincount", jdbcType=JdbcType.INTEGER),
        @Result(column="isSuperuser", property="issuperuser", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    user selectByPrimaryKey(Integer adminid);

    @Select({
        "select",
        "adminID, adminName, adminPwd, adminStatus, expDate, adminPermission, projectid, groupids,",
        "adminLevel, parentId, inherit, GrpCount, adminCount, isSuperuser, DelIndex",
        "from t_user",
        "where (parentId = #{parentId,jdbcType=INTEGER} or adminID=#{parentId,jdbcType=INTEGER}) and DelIndex=0"
    })
    @Results({
        @Result(column="adminID", property="adminid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="adminName", property="adminname", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPwd", property="adminpwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminStatus", property="adminstatus", jdbcType=JdbcType.INTEGER),
        @Result(column="expDate", property="expdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPermission", property="adminpermission", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupids", property="groupids", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminLevel", property="adminlevel", jdbcType=JdbcType.INTEGER),
        @Result(column="parentId", property="parentid", jdbcType=JdbcType.INTEGER),
        @Result(column="inherit", property="inherit", jdbcType=JdbcType.INTEGER),
        @Result(column="GrpCount", property="grpcount", jdbcType=JdbcType.INTEGER),
        @Result(column="adminCount", property="admincount", jdbcType=JdbcType.INTEGER),
        @Result(column="isSuperuser", property="issuperuser", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    List<user> selectByParentid(Integer parentId);
    
    @Select({
        "select",
        "adminID, adminName, adminPwd, adminStatus, expDate, adminPermission, projectid, groupids,",
        "adminLevel, parentId, inherit, GrpCount, adminCount, isSuperuser, DelIndex",
        "from t_user",
        "where projectid = #{0} and DelIndex=0"
    })
    @Results({
        @Result(column="adminID", property="adminid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="adminName", property="adminname", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPwd", property="adminpwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminStatus", property="adminstatus", jdbcType=JdbcType.INTEGER),
        @Result(column="expDate", property="expdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPermission", property="adminpermission", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupids", property="groupids", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminLevel", property="adminlevel", jdbcType=JdbcType.INTEGER),
        @Result(column="parentId", property="parentid", jdbcType=JdbcType.INTEGER),
        @Result(column="inherit", property="inherit", jdbcType=JdbcType.INTEGER),
        @Result(column="GrpCount", property="grpcount", jdbcType=JdbcType.INTEGER),
        @Result(column="adminCount", property="admincount", jdbcType=JdbcType.INTEGER),
        @Result(column="isSuperuser", property="issuperuser", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    List<user> selectByprojectid(String projectid);
    
    @Select({
        "select",
        "adminID, adminName, adminPwd, adminStatus, expDate, adminPermission, projectid, groupids,",
        "adminLevel, parentId, inherit, GrpCount, adminCount, isSuperuser, DelIndex",
        "from t_user",
        "where adminName = #{adminName,jdbcType=VARCHAR}"
    })
    @Results({
    	@Result(column="adminID", property="adminid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="adminName", property="adminname", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPwd", property="adminpwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminStatus", property="adminstatus", jdbcType=JdbcType.INTEGER),
        @Result(column="expDate", property="expdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPermission", property="adminpermission", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupids", property="groupids", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminLevel", property="adminlevel", jdbcType=JdbcType.INTEGER),
        @Result(column="parentId", property="parentid", jdbcType=JdbcType.INTEGER),
        @Result(column="inherit", property="inherit", jdbcType=JdbcType.INTEGER),
        @Result(column="GrpCount", property="grpcount", jdbcType=JdbcType.INTEGER),
        @Result(column="adminCount", property="admincount", jdbcType=JdbcType.INTEGER),
        @Result(column="isSuperuser", property="issuperuser", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    user selectByPrimaryName(String adminName);
    
    @Select({
        "select",
        "count(*)",
        "from t_user",
        "where adminName = #{0} and DelIndex=0"
    })    
    int selectCountByName(String adminName);
    
    @Select({
        "select",
        "count(*)",
        "from t_user",
        "where adminName = #{0} and adminID <> #{1}"
    })    
    int selectCountByNameid(String adminName,int adminid);
    
    @Select({
        "select",
        "adminID, adminName, adminPwd, adminStatus, expDate, adminPermission, projectid, groupids,",
        "adminLevel, parentId, inherit, GrpCount, adminCount, isSuperuser, DelIndex",
        "from t_user",
        "where DelIndex = 0"
    })
    @Results({
        @Result(column="adminID", property="adminid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="adminName", property="adminname", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPwd", property="adminpwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminStatus", property="adminstatus", jdbcType=JdbcType.INTEGER),
        @Result(column="expDate", property="expdate", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminPermission", property="adminpermission", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="groupids", property="groupids", jdbcType=JdbcType.VARCHAR),
        @Result(column="adminLevel", property="adminlevel", jdbcType=JdbcType.INTEGER),
        @Result(column="parentId", property="parentid", jdbcType=JdbcType.INTEGER),
        @Result(column="inherit", property="inherit", jdbcType=JdbcType.INTEGER),
        @Result(column="GrpCount", property="grpcount", jdbcType=JdbcType.INTEGER),
        @Result(column="adminCount", property="admincount", jdbcType=JdbcType.INTEGER),
        @Result(column="isSuperuser", property="issuperuser", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    List<user> selectAll();
    
    @UpdateProvider(type=userSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(user record);

    @Update({
        "update t_user",
        "set adminName = #{adminname,jdbcType=VARCHAR},",
          "adminPwd = #{adminpwd,jdbcType=VARCHAR},",
          "adminStatus = #{adminstatus,jdbcType=INTEGER},",
          "expDate = #{expdate,jdbcType=VARCHAR},",
          "adminPermission = #{adminpermission,jdbcType=VARCHAR},",
          "projectid = #{projectid,jdbcType=VARCHAR},",
          "groupids = #{groupids,jdbcType=VARCHAR},",
          "adminLevel = #{adminlevel,jdbcType=INTEGER},",
          "parentId = #{parentid,jdbcType=INTEGER},",
          "inherit = #{inherit,jdbcType=INTEGER},",
          "GrpCount = #{grpcount,jdbcType=INTEGER},",
          "adminCount = #{admincount,jdbcType=INTEGER},",
          "isSuperuser = #{issuperuser,jdbcType=INTEGER},",
          "DelIndex = #{delindex,jdbcType=INTEGER}",
        "where adminID = #{adminid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(user record);
}