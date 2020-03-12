package com.wzn.azblog.Statistics.dao;

import com.wzn.ablog.common.entity.EchartsDimensions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EchartsDimensionsDao extends JpaRepository<EchartsDimensions,String>,
        JpaSpecificationExecutor<EchartsDimensions> {
}
