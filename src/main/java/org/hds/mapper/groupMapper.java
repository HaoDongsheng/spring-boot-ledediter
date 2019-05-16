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
import org.hds.model.group;

public interface groupMapper {
    @Delete({
        "delete from t_group",
        "where groupid = #{groupid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer groupid);
    
    @Delete({
        "delete from t_group",
        "where projectid = #{0}"
    })
    int deleteByprojectid(String projectid);

    @Insert({
        "insert into t_group (groupid, groupName, maxPackLength,Para2_User,Para3_TimeLight,Para1_Basic,",
        "screenwidth,screenheight,projectid,pubid, plpubid, DelIndex)",
        "values (#{groupid,jdbcType=INTEGER}, #{groupname,jdbcType=VARCHAR}, #{maxPackLength,jdbcType=INTEGER}, #{Para2_User,jdbcType=VARCHAR}, #{Para3_TimeLight,jdbcType=VARCHAR}, #{Para1_Basic,jdbcType=VARCHAR},",
        "#{screenwidth,jdbcType=INTEGER},#{screenheight,jdbcType=INTEGER},#{projectid,jdbcType=VARCHAR},#{pubid,jdbcType=INTEGER},#{plpubid,jdbcType=INTEGER}, #{delindex,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true, keyProperty="groupid",keyColumn="groupid")//添加该行，product中的id将被自动添加
    int insert(group record);

    @InsertProvider(type=groupSqlProvider.class, method="insertSelective")
    int insertSelective(group record);

    @Select({
        "select",
        "groupid, groupName,maxPackLength,Para2_User,Para3_TimeLight,Para1_Basic,screenwidth,screenheight, projectid, pubid, plpubid, DelIndex",
        "from t_group",
        "where groupid = #{groupid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="groupid", property="groupid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="groupName", property="groupname", jdbcType=JdbcType.VARCHAR),
        @Result(column="maxPackLength", property="maxPackLength", jdbcType=JdbcType.INTEGER),
        @Result(column="Para2_User", property="Para2_User", jdbcType=JdbcType.VARCHAR),        
        @Result(column="Para3_TimeLight", property="Para3_TimeLight", jdbcType=JdbcType.VARCHAR),        
        @Result(column="Para1_Basic", property="Para1_Basic", jdbcType=JdbcType.VARCHAR),        
        @Result(column="screenwidth", property="screenwidth", jdbcType=JdbcType.INTEGER),
        @Result(column="screenheight", property="screenheight", jdbcType=JdbcType.INTEGER),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubid", property="pubid", jdbcType=JdbcType.INTEGER),
        @Result(column="plpubid", property="plpubid", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    group selectByPrimaryKey(Integer groupid);
    
    @Select({
        "select",
        "groupid, groupName,maxPackLength,Para2_User,Para3_TimeLight,Para1_Basic,screenwidth,screenheight,projectid, pubid, plpubid, DelIndex",
        "from t_group",
        "where delIndex = 0"
    })
    @Results({
        @Result(column="groupid", property="groupid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="groupName", property="groupname", jdbcType=JdbcType.VARCHAR),
        @Result(column="maxPackLength", property="maxPackLength", jdbcType=JdbcType.INTEGER),
        @Result(column="Para2_User", property="Para2_User", jdbcType=JdbcType.VARCHAR),        
        @Result(column="Para3_TimeLight", property="Para3_TimeLight", jdbcType=JdbcType.VARCHAR),        
        @Result(column="Para1_Basic", property="Para1_Basic", jdbcType=JdbcType.VARCHAR),    
        @Result(column="screenwidth", property="screenwidth", jdbcType=JdbcType.INTEGER),
        @Result(column="screenheight", property="screenheight", jdbcType=JdbcType.INTEGER),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubid", property="pubid", jdbcType=JdbcType.INTEGER),
        @Result(column="plpubid", property="plpubid", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    List<group> selectAll();
    
    @Select({
        "select",
        "groupid, groupName,maxPackLength,Para2_User,Para3_TimeLight,Para1_Basic,screenwidth,screenheight,projectid, pubid, plpubid, DelIndex",
        "from t_group",
        "where delIndex = 0 and projectid=#{0}"
    })
    @Results({
        @Result(column="groupid", property="groupid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="groupName", property="groupname", jdbcType=JdbcType.VARCHAR),
        @Result(column="maxPackLength", property="maxPackLength", jdbcType=JdbcType.INTEGER),
        @Result(column="Para2_User", property="Para2_User", jdbcType=JdbcType.VARCHAR),        
        @Result(column="Para3_TimeLight", property="Para3_TimeLight", jdbcType=JdbcType.VARCHAR),        
        @Result(column="Para1_Basic", property="Para1_Basic", jdbcType=JdbcType.VARCHAR), 
        @Result(column="screenwidth", property="screenwidth", jdbcType=JdbcType.INTEGER),
        @Result(column="screenheight", property="screenheight", jdbcType=JdbcType.INTEGER),
        @Result(column="projectid", property="projectid", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubid", property="pubid", jdbcType=JdbcType.INTEGER),
        @Result(column="plpubid", property="plpubid", jdbcType=JdbcType.INTEGER),
        @Result(column="DelIndex", property="delindex", jdbcType=JdbcType.INTEGER)
    })
    List<group> selectbyProjectid(String projectid);
    
    @Select({
        "select",
        "UpdateRate",
        "from t_group",
        "where groupid = #{0}"
    })
    @Result(column="UpdateRate", property="UpdateRate", jdbcType=JdbcType.DOUBLE)
       
    double selectUpdateRateBygroupid(Integer groupid);
    
    @Select({
        "select",
        "pubid",
        "from t_group",
        "where groupid = #{groupid,jdbcType=INTEGER}"
    })
    @Result(column="pubid", property="pubid", jdbcType=JdbcType.INTEGER)
    int selectpubidBygroupid(Integer groupid);
    
    @Select({
        "select",
        "plpubid",
        "from t_group",
        "where groupid = #{groupid,jdbcType=INTEGER}"
    })
    @Result(column="pubid", property="pubid", jdbcType=JdbcType.INTEGER)
       
    int selectplpubidBygroupid(Integer groupid);

    @Select({
        "select",
        "count(*)",
        "from t_group",
        "where groupName = #{0} and DelIndex=0"
    })    
    int selectCountByName(String grpName);
    
    @Select({
        "select",
        "count(*)",
        "from t_group",
        "where groupName = #{0} and groupid <> #{1}"
    })    
    int selectCountByNameid(String grpName,int grpid);
    
    @UpdateProvider(type=groupSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(group record);

    @Update({
        "update t_group",
        "set groupName = #{groupname,jdbcType=VARCHAR},",
          "maxPackLength = #{maxPackLength,jdbcType=INTEGER},",
          "Para2_User = #{Para2_User,jdbcType=VARCHAR},",          
          "Para3_TimeLight = #{Para3_TimeLight,jdbcType=VARCHAR},",          
          "Para1_Basic = #{Para1_Basic,jdbcType=VARCHAR},",          
          "screenwidth = #{screenwidth,jdbcType=INTEGER},",
          "screenheight = #{screenheight,jdbcType=INTEGER},",
          "projectid = #{projectid,jdbcType=VARCHAR},",
          "pubid = #{pubid,jdbcType=INTEGER},",
          "plpubid = #{plpubid,jdbcType=INTEGER},",
          "DelIndex = #{delindex,jdbcType=INTEGER}",
        "where groupid = #{groupid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(group record);
    
    @Update({
        "update t_group",
        "set pubid = #{pubid,jdbcType=INTEGER}",          
        "where groupid = #{groupid,jdbcType=INTEGER}"
    })
    int updatepubidBygroupid(group record);
    
    @Update({
        "update t_group",
        "set plpubid = #{plpubid,jdbcType=INTEGER}",          
        "where groupid = #{groupid,jdbcType=INTEGER}"
    })
    int updateplpubidBygroupid(group record);
}