import com.baizhi.Application;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.StatisticDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Statistic;
import com.baizhi.service.StatisticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AdminTest {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private StatisticService statisticService;
    @Test
    public void test1(){
        List<Statistic> boys = statisticService.queryBySex("男");
        ArrayList<Integer> integers = new ArrayList<>();
       for(int i = 0;i<12;i++){
           integers.add(0);
        }
        List<String> list = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        for (int i = 0; i < list.size(); i++) {
            for (int i1 = 0; i1 < boys.size(); i1++) {
                if(list.get(i).equals(boys.get(i1).getMonth())){
                 integers.remove(i);
                 integers.add(i,boys.get(i1).getValue());
                }
            }
        }
    }
}
