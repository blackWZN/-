package com.wzn.azblog.Statistics.quartz;

import com.wzn.ablog.common.entity.EchartsSource;
import com.wzn.ablog.common.utils.BlogUtils;
import com.wzn.ablog.common.utils.IdWorker;
import com.wzn.ablog.common.vo.AzResult;
import com.wzn.azblog.Statistics.dao.EchartsSourceDao;
import com.wzn.azblog.Statistics.feign.ArticleFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
@Transactional
public class CountService {

    @Autowired
    private ArticleFegin articleFegin;

    @Autowired
    private EchartsSourceDao echartsSourceDao;

    @Autowired
    private IdWorker idWorker;

    @Scheduled(cron = "0 10 23 1/1 * ?")
    public void CountArticle() {
        System.out.println("统计文章数");
        AzResult azResult = articleFegin.todayCount();
        if(azResult.getStatus() == 200){
            String count = (String) azResult.getData();
            String weekDay = BlogUtils.getWeek(LocalDate.now().getDayOfWeek());
            String weekNum = BlogUtils.getWeekNum(weekDay);
            EchartsSource echartsSource = new EchartsSource();
            echartsSource.setId(idWorker.nextId() + "")
                    .setXaxis(weekNum)
                    .setFlag("2")
                    .setData(count)
                    .setUpdateTime(LocalDate.now() + "");
            echartsSourceDao.save(echartsSource);
        }else {
           throw new RuntimeException(azResult.getMessage());
        }

    }
}
