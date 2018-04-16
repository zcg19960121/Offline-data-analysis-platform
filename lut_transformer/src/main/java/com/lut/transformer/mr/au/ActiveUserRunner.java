package com.lut.transformer.mr.au;

import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.log4j.Logger;

import com.lut.common.EventLogConstants;
import com.lut.transformer.model.dim.StatsUserDimension;
import com.lut.transformer.model.value.map.TimeOutputValue;
import com.lut.transformer.model.value.reduce.MapWritableValue;
import com.lut.transformer.mr.TransformerBaseRunner;

/**
 * 统计active user的入口类
 * 
 * @author gg
 *
 */
public class ActiveUserRunner extends TransformerBaseRunner {
    private static final Logger logger = Logger.getLogger(ActiveUserRunner.class);

    public static void main(String[] args) {
        ActiveUserRunner runner = new ActiveUserRunner();
        runner.setupRunner("active-user", ActiveUserRunner.class, ActiveUserMapper.class, ActiveUserReducer.class, StatsUserDimension.class, TimeOutputValue.class, StatsUserDimension.class, MapWritableValue.class);
        try {
            runner.startRunner(args);
        } catch (Exception e) {
            logger.error("运行active user任务出现异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Filter fetchHbaseFilter() {
        FilterList filterList = new FilterList();
        // 定义mapper中需要获取的列名
        String[] columns = new String[] { EventLogConstants.LOG_COLUMN_NAME_UUID, // 用户id
                EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME, // 服务器时间
                EventLogConstants.LOG_COLUMN_NAME_PLATFORM, // 平台名称
                EventLogConstants.LOG_COLUMN_NAME_BROWSER_NAME, // 浏览器名称
                EventLogConstants.LOG_COLUMN_NAME_BROWSER_VERSION // 浏览器版本号
        };
        filterList.addFilter(this.getColumnFilter(columns));
        return filterList;
    }
}
