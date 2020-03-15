package com.wzn.azblog.Statistics;

import com.wzn.ablog.common.utils.BlogUtils;
import com.wzn.ablog.common.vo.EchartsData;
import com.wzn.azblog.Statistics.dao.EchartsSourceDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AdminTest {

    @Autowired
    private EchartsSourceDao echartsSourceDao;

    @Test
    public void test1() throws Exception {
        List<Object[]> data = echartsSourceDao.findData();

        List<EchartsData> echartsData = BlogUtils.castEntity(data, EchartsData.class);
        echartsData.stream().forEach(System.out::println);
    }

    @Test
    public void test2() throws Exception {
        List<Object[]> data = echartsSourceDao.findData();
        List<EchartsData> echartsData = BlogUtils.castEntity(data, EchartsData.class);
        List<Map<String,String>> source = new ArrayList<>();

        source.stream().forEach(System.out::println);
    }



}