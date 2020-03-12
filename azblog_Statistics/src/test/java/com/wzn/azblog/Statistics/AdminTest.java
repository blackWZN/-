package com.wzn.azblog.Statistics;

import com.wzn.ablog.common.entity.EchartsSource;
import com.wzn.ablog.common.utils.blogUtils;
import com.wzn.ablog.common.vo.EchartsData;
import com.wzn.azblog.Statistics.dao.EchartsSourceDao;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Constructor;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AdminTest {

    @Autowired
    private EchartsSourceDao echartsSourceDao;

    @Test
    public void test1() throws Exception {
        List<Object[]> data = echartsSourceDao.findData();

        List<EchartsData> echartsData = blogUtils.castEntity(data, EchartsData.class);
        echartsData.stream().forEach(System.out::println);
    }

    @Test
    public void test2() throws Exception {
        List<Object[]> data = echartsSourceDao.findData();
        List<EchartsData> echartsData = blogUtils.castEntity(data, EchartsData.class);
        List<Map<String,String>> source = new ArrayList<>();

        source.stream().forEach(System.out::println);
    }



}