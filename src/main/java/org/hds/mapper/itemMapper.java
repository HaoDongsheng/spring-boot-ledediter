package org.hds.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.hds.model.item;

public interface itemMapper {
    @Delete({
        "delete from t_item",
        "where adid = #{adid,jdbcType=INTEGER}",
          "and pageid = #{pageid,jdbcType=INTEGER}",
          "and itemid = #{itemid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(@Param("adid") Integer adid, @Param("pageid") Integer pageid, @Param("itemid") Integer itemid);

    @Delete({
        "delete from t_item",
        "where adid = #{adid,jdbcType=INTEGER}"         
    })
    int deleteByadid(@Param("adid") Integer adid);
    
    @Insert({
        "insert into t_item (adid, pageid, ",
        "itemid, itemleft, ",
        "itemtop, itemwidth, ",
        "itemheight, itembackcolor, ",
        "itemtype, delindex, ",
        "itemContext)",
        "values (#{adid,jdbcType=INTEGER}, #{pageid,jdbcType=INTEGER}, ",
        "#{itemid,jdbcType=INTEGER}, #{itemleft,jdbcType=INTEGER}, ",
        "#{itemtop,jdbcType=INTEGER}, #{itemwidth,jdbcType=INTEGER}, ",
        "#{itemheight,jdbcType=INTEGER}, #{itembackcolor,jdbcType=VARCHAR}, ",
        "#{itemtype,jdbcType=INTEGER}, #{delindex,jdbcType=INTEGER}, ",
        "#{itemcontext,jdbcType=LONGVARCHAR})"
    })
    int insert(item record);

    @InsertProvider(type=itemSqlProvider.class, method="insertSelective")
    int insertSelective(item record);

    @Select({
        "select",
        "adid, pageid, itemid, itemleft, itemtop, itemwidth, itemheight, itembackcolor, ",
        "itemtype, delindex, itemContext",
        "from t_item",
        "where adid = #{adid,jdbcType=INTEGER}",
          "and pageid = #{pageid,jdbcType=INTEGER}",
          "and itemid = #{itemid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="adid", property="adid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pageid", property="pageid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemid", property="itemid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemleft", property="itemleft", jdbcType=JdbcType.INTEGER),
        @Result(column="itemtop", property="itemtop", jdbcType=JdbcType.INTEGER),
        @Result(column="itemwidth", property="itemwidth", jdbcType=JdbcType.INTEGER),
        @Result(column="itemheight", property="itemheight", jdbcType=JdbcType.INTEGER),
        @Result(column="itembackcolor", property="itembackcolor", jdbcType=JdbcType.VARCHAR),
        @Result(column="itemtype", property="itemtype", jdbcType=JdbcType.INTEGER),
        @Result(column="delindex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="itemContext", property="itemcontext", jdbcType=JdbcType.LONGVARCHAR)
    })
    item selectByPrimaryKey(@Param("adid") Integer adid, @Param("pageid") Integer pageid, @Param("itemid") Integer itemid);

    @Select({
        "select",
        "adid, pageid, itemid, itemleft, itemtop, itemwidth, itemheight, itembackcolor, ",
        "itemtype, delindex, itemContext",
        "from t_item",
        "where adid = #{adid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="adid", property="adid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pageid", property="pageid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemid", property="itemid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemleft", property="itemleft", jdbcType=JdbcType.INTEGER),
        @Result(column="itemtop", property="itemtop", jdbcType=JdbcType.INTEGER),
        @Result(column="itemwidth", property="itemwidth", jdbcType=JdbcType.INTEGER),
        @Result(column="itemheight", property="itemheight", jdbcType=JdbcType.INTEGER),
        @Result(column="itembackcolor", property="itembackcolor", jdbcType=JdbcType.VARCHAR),
        @Result(column="itemtype", property="itemtype", jdbcType=JdbcType.INTEGER),
        @Result(column="delindex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="itemContext", property="itemcontext", jdbcType=JdbcType.LONGVARCHAR)
    })
    java.util.List<item> selectByadid(@Param("adid") Integer adid);
    
    @UpdateProvider(type=itemSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(item record);

    @Update({
        "update t_item",
        "set itemleft = #{itemleft,jdbcType=INTEGER},",
          "itemtop = #{itemtop,jdbcType=INTEGER},",
          "itemwidth = #{itemwidth,jdbcType=INTEGER},",
          "itemheight = #{itemheight,jdbcType=INTEGER},",
          "itembackcolor = #{itembackcolor,jdbcType=VARCHAR},",
          "itemtype = #{itemtype,jdbcType=INTEGER},",
          "delindex = #{delindex,jdbcType=INTEGER},",
          "itemContext = #{itemcontext,jdbcType=LONGVARCHAR}",
        "where adid = #{adid,jdbcType=INTEGER}",
          "and pageid = #{pageid,jdbcType=INTEGER}",
          "and itemid = #{itemid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(item record);

    @Update({
        "update t_item",
        "set itemleft = #{itemleft,jdbcType=INTEGER},",
          "itemtop = #{itemtop,jdbcType=INTEGER},",
          "itemwidth = #{itemwidth,jdbcType=INTEGER},",
          "itemheight = #{itemheight,jdbcType=INTEGER},",
          "itembackcolor = #{itembackcolor,jdbcType=VARCHAR},",
          "itemtype = #{itemtype,jdbcType=INTEGER},",
          "delindex = #{delindex,jdbcType=INTEGER}",
        "where adid = #{adid,jdbcType=INTEGER}",
          "and pageid = #{pageid,jdbcType=INTEGER}",
          "and itemid = #{itemid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(item record);
}