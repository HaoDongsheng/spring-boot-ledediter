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
import java.util.List;

public interface itemMapper {
    @Delete({
        "delete from t_item",
        "where infoSN = #{infoSN,jdbcType=INTEGER}",
          "and pageid = #{pageid,jdbcType=INTEGER}",
          "and itemid = #{itemid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(@Param("infoSN") Integer infoSN, @Param("pageid") Integer pageid, @Param("itemid") Integer itemid);

    @Delete({
        "delete from t_item",
        "where infoSN = #{infoSN,jdbcType=INTEGER}"
    })
    int deleteByadid(@Param("infoSN") Integer infoSN);
    
    @Insert({
        "insert into t_item (infoSN, pageid, ",
        "itemid, itemleft, ",
        "itemtop, itemwidth, ",
        "itemheight,itemfontno, itembackcolor,itembackopacity, itemforecolor,itemforeopacity,",
        "itemtype, delindex, ",
        "itemContext, itemContextJson, ",
        "itemStyle)",
        "values (#{infoSN,jdbcType=INTEGER}, #{pageid,jdbcType=INTEGER}, ",
        "#{itemid,jdbcType=INTEGER}, #{itemleft,jdbcType=INTEGER}, ",
        "#{itemtop,jdbcType=INTEGER}, #{itemwidth,jdbcType=INTEGER}, ",
        "#{itemheight,jdbcType=INTEGER}, #{itemfontno,jdbcType=INTEGER}, #{itembackcolor,jdbcType=VARCHAR}, ",
        "#{itembackopacity,jdbcType=INTEGER},#{itemforecolor,jdbcType=VARCHAR}, #{itemforeopacity,jdbcType=INTEGER}, ",
        "#{itemtype,jdbcType=INTEGER}, #{delindex,jdbcType=INTEGER}, ",
        "#{itemcontext,jdbcType=LONGVARCHAR}, #{itemcontextjson,jdbcType=LONGVARCHAR}, ",
        "#{itemstyle,jdbcType=LONGVARCHAR})"
    })
    int insert(item record);

    @InsertProvider(type=itemSqlProvider.class, method="insertSelective")
    int insertSelective(item record);

    @Select({
        "select",
        "infoSN, pageid, itemid, itemleft, itemtop, itemwidth, itemheight,itemfontno, itembackcolor, itembackopacity, itemforecolor,itemforeopacity,",
        "itemtype, delindex, itemContext, itemContextJson, itemStyle",
        "from t_item",
        "where infoSN = #{infoSN,jdbcType=INTEGER}",
          "and pageid = #{pageid,jdbcType=INTEGER}",
          "and itemid = #{itemid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="infoSN", property="infoSN", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pageid", property="pageid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemid", property="itemid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemleft", property="itemleft", jdbcType=JdbcType.INTEGER),
        @Result(column="itemtop", property="itemtop", jdbcType=JdbcType.INTEGER),
        @Result(column="itemwidth", property="itemwidth", jdbcType=JdbcType.INTEGER),
        @Result(column="itemheight", property="itemheight", jdbcType=JdbcType.INTEGER),
        @Result(column="itemfontno", property="itemfontno", jdbcType=JdbcType.INTEGER),
        @Result(column="itembackcolor", property="itembackcolor", jdbcType=JdbcType.VARCHAR),
        @Result(column="itembackopacity", property="itembackopacity", jdbcType=JdbcType.INTEGER),
        @Result(column="itemforecolor", property="itemforecolor", jdbcType=JdbcType.VARCHAR),
        @Result(column="itemforeopacity", property="itemforeopacity", jdbcType=JdbcType.INTEGER),
        @Result(column="itemtype", property="itemtype", jdbcType=JdbcType.INTEGER),
        @Result(column="delindex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="itemContext", property="itemcontext", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="itemContextJson", property="itemcontextjson", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="itemStyle", property="itemstyle", jdbcType=JdbcType.LONGVARCHAR)
    })
    item selectByPrimaryKey(@Param("infoSN") Integer infoSN, @Param("pageid") Integer pageid, @Param("itemid") Integer itemid);

    @Select({
        "select",
        "infoSN, pageid, itemid, itemleft, itemtop, itemwidth, itemheight, itemfontno, itembackcolor, itembackopacity, itemforecolor,itemforeopacity,",
        "itemtype, delindex, itemContext, itemContextJson, itemStyle",
        "from t_item",
        "where infoSN = #{infoSN,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="infoSN", property="infoSN", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pageid", property="pageid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemid", property="itemid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemleft", property="itemleft", jdbcType=JdbcType.INTEGER),
        @Result(column="itemtop", property="itemtop", jdbcType=JdbcType.INTEGER),
        @Result(column="itemwidth", property="itemwidth", jdbcType=JdbcType.INTEGER),
        @Result(column="itemheight", property="itemheight", jdbcType=JdbcType.INTEGER),
        @Result(column="itemfontno", property="itemfontno", jdbcType=JdbcType.INTEGER),
        @Result(column="itembackcolor", property="itembackcolor", jdbcType=JdbcType.VARCHAR),
        @Result(column="itembackopacity", property="itembackopacity", jdbcType=JdbcType.INTEGER),
        @Result(column="itemforecolor", property="itemforecolor", jdbcType=JdbcType.VARCHAR),
        @Result(column="itemforeopacity", property="itemforeopacity", jdbcType=JdbcType.INTEGER),
        @Result(column="itemtype", property="itemtype", jdbcType=JdbcType.INTEGER),
        @Result(column="delindex", property="delindex", jdbcType=JdbcType.INTEGER),
        @Result(column="itemContext", property="itemcontext", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="itemContextJson", property="itemcontextjson", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="itemStyle", property="itemstyle", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<item> selectByadid(@Param("infoSN") Integer infoSN);
    
    @UpdateProvider(type=itemSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(item record);

    @Update({
        "update t_item",
        "set itemleft = #{itemleft,jdbcType=INTEGER},",
          "itemtop = #{itemtop,jdbcType=INTEGER},",
          "itemwidth = #{itemwidth,jdbcType=INTEGER},",
          "itemheight = #{itemheight,jdbcType=INTEGER},",
          "itemfontno = #{itemfontno,jdbcType=INTEGER},",
          "itembackcolor = #{itembackcolor,jdbcType=VARCHAR},",
          "itembackopacity = #{itembackopacity,jdbcType=INTEGER},",
          "itemforecolor = #{itemforecolor,jdbcType=VARCHAR},",
          "itemforeopacity = #{itemforeopacity,jdbcType=INTEGER},",
          "itemtype = #{itemtype,jdbcType=INTEGER},",
          "delindex = #{delindex,jdbcType=INTEGER},",
          "itemContext = #{itemcontext,jdbcType=LONGVARCHAR},",
          "itemContextJson = #{itemcontextjson,jdbcType=LONGVARCHAR},",
          "itemStyle = #{itemstyle,jdbcType=LONGVARCHAR}",
        "where infoSN = #{infoSN,jdbcType=INTEGER}",
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
          "itemfontno = #{itemfontno,jdbcType=INTEGER},",
          "itembackcolor = #{itembackcolor,jdbcType=VARCHAR},",
          "itembackopacity = #{itembackopacity,jdbcType=INTEGER},",
          "itemforecolor = #{itemforecolor,jdbcType=VARCHAR},",
          "itemforeopacity = #{itemforeopacity,jdbcType=INTEGER},",
          "itemtype = #{itemtype,jdbcType=INTEGER},",
          "delindex = #{delindex,jdbcType=INTEGER}",
        "where infoSN = #{infoSN,jdbcType=INTEGER}",
          "and pageid = #{pageid,jdbcType=INTEGER}",
          "and itemid = #{itemid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(item record);
    
    @Update({
        "update t_item",
        "set delindex = #{1}",                    
        "where infoSN = #{0}"          
    })
    int updateDelindexByPrimaryinfoSN(int infoSN,int delindex);
}