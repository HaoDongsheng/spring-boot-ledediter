package org.hds.logmapper;

import java.util.List;

import org.hds.model.oldplaylist;

public interface oldplaylistMapper {
	int deleteByPrimaryKey(String playlistsn);

	int insert(oldplaylist record);

	int insertSelective(oldplaylist record);

	oldplaylist selectByPrimaryKey(String playlistsn);

	int updateByPrimaryKeySelective(oldplaylist record);

	int updateByPrimaryKeyWithBLOBs(oldplaylist record);

	int updateByPrimaryKey(oldplaylist record);

	List<oldplaylist> selecthistorybyDate(String lifeAct, String lifeDie, int groupid);
}