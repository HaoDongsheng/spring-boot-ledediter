package org.hds.logmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.hds.model.oldlogadv;

public interface oldlogadvMapper {
	int deleteByPrimaryKey(String infoSN);

	int insert(oldlogadv record);

	int insertSelective(oldlogadv record);

	oldlogadv selectByPrimaryKey(String infoSN);

	oldlogadv selectByPrimaryinfosn(String infoSN);

	int selectDelCountBygroupid(@Param("groupid") Integer groupid, @Param("infoname") String infoname,
			@Param("lifeAct") String lifeAct, @Param("lifeDie") String lifeDie);

	List<oldlogadv> selectDelBygroupid(@Param("groupid") Integer groupid, @Param("infoname") String infoname,
			@Param("lifeAct") String lifeAct, @Param("lifeDie") String lifeDie, @Param("startoffset") int startoffset,
			@Param("pageSize") int pageSize, @Param("sort") String sort, @Param("sortOrder") String sortOrder);

	int updateByPrimaryKeySelective(oldlogadv record);

	int updateByPrimaryKeyWithBLOBs(oldlogadv record);

	int updateByPrimaryKey(oldlogadv record);
}