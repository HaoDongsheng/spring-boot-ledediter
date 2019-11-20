package org.hds.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.hds.model.log;

public interface logMapper {
    @Insert({
        "insert into t_log (logtime, adminid, ",
        "functionName, operateType, ",
        "operate)",
        "values (#{logtime,jdbcType=VARCHAR}, #{adminid,jdbcType=INTEGER}, ",
        "#{functionname,jdbcType=VARCHAR}, #{operatetype,jdbcType=INTEGER}, ",
        "#{operate,jdbcType=LONGVARCHAR})"
    })
    int insert(log record);

    @InsertProvider(type=logSqlProvider.class, method="insertSelective")
    int insertSelective(log record);
}