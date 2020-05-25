package org.hds.logmapper;

import org.hds.model.llog;

public interface llogMapper {
    int insert(llog record);

    int insertSelective(llog record);
}