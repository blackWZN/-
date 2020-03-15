package com.wzn.azblog.Statistics.dao;

import com.wzn.ablog.common.entity.EchartsDimensions;
import com.wzn.ablog.common.entity.EchartsSource;
import com.wzn.ablog.common.vo.EchartsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EchartsSourceDao extends JpaRepository<EchartsSource,String>,
        JpaSpecificationExecutor<EchartsSource> {

    @Query(value = "SELECT \n" +
            "  x.name,\n" +
            "  f.name fname,\n" +
            "  s.data \n" +
            "FROM\n" +
            "  echarts_xaxis X,\n" +
            "  echarts_flag f,\n" +
            "  echarts_source s \n" +
            "WHERE s.flag = f.id \n" +
            "  AND s.xaxis = x.id \n",nativeQuery = true)
    List<Object[]> findData();


}
