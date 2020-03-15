package com.wzn.azblog.Statistics.service;

import com.wzn.ablog.common.entity.EchartsDimensions;
import com.wzn.ablog.common.utils.BlogUtils;
import com.wzn.ablog.common.vo.EchartsData;
import com.wzn.ablog.common.vo.EchartsResult;
import com.wzn.azblog.Statistics.dao.EchartsDimensionsDao;
import com.wzn.azblog.Statistics.dao.EchartsSourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EchartsService {

    @Autowired
    private EchartsDimensionsDao EchartsDimensionsDao;

    @Autowired
    private EchartsSourceDao echartsSourceDao;

    private String flag = "name";

    public EchartsResult list(){
        //查询出图表的dimensions数据
        List<EchartsDimensions> dimensionsList = EchartsDimensionsDao.findAll();
        List<String> dimensions = new ArrayList<>();
        dimensions.add(flag);
        dimensionsList.stream().forEach( item -> {
            dimensions.add(item.getName());
        });
        //查询出图表的source数据
        List<Object[]> data = echartsSourceDao.findData();
        List<EchartsData> echartsData = BlogUtils.castEntity(data, EchartsData.class);
        Map<String,String> monday = new LinkedHashMap<>();
        Map<String,String> Tuesday = new LinkedHashMap<>();
        Map<String,String> Wednesday = new LinkedHashMap<>();
        Map<String,String> Thursday = new LinkedHashMap<>();
        Map<String,String> Friday = new LinkedHashMap<>();
        Map<String,String> Saturday = new LinkedHashMap<>();
        Map<String,String> weekday = new LinkedHashMap<>();

        echartsData.stream().forEach(item -> {
            if(item.getName().contains("周一")){
                monday.put("name",item.getName());
                monday.put(item.getFname(),item.getData());
            }
            if(item.getName().contains("周二")){
                Tuesday.put("name",item.getName());
                Tuesday.put(item.getFname(),item.getData());
            }
            if(item.getName().contains("周三")){
                Wednesday.put("name",item.getName());
                Wednesday.put(item.getFname(),item.getData());
            }
            if(item.getName().contains("周四")){
                Thursday.put("name",item.getName());
                Thursday.put(item.getFname(),item.getData());
            }
            if(item.getName().contains("周五")){
                Friday.put("name",item.getName());
                Friday.put(item.getFname(),item.getData());
            }
            if(item.getName().contains("周六")){
                Saturday.put("name",item.getName());
                Saturday.put(item.getFname(),item.getData());
            }
            if(item.getName().contains("周日")){
                weekday.put("name",item.getName());
                weekday.put(item.getFname(),item.getData());
            }
        });
        List<Map<String,String>> source = new ArrayList<>();
        source.add(monday);
        source.add(Tuesday);
        source.add(Wednesday);
        source.add(Thursday);
        source.add(Friday);
        source.add(Saturday);
        source.add(weekday);
        return new EchartsResult(dimensions,source);
    }
}
