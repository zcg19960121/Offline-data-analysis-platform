package com.lut.transformer.mr.sessions;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;

import com.lut.common.GlobalConstants;
import com.lut.transformer.model.dim.StatsUserDimension;
import com.lut.transformer.model.dim.base.BaseDimension;
import com.lut.transformer.model.value.BaseStatsValueWritable;
import com.lut.transformer.model.value.reduce.MapWritableValue;
import com.lut.transformer.mr.IOutputCollector;
import com.lut.transformer.service.IDimensionConverter;
public class SessionsCollector implements IOutputCollector{
	@Override
    public void collect(Configuration conf, BaseDimension key, BaseStatsValueWritable value, PreparedStatement pstmt, IDimensionConverter converter) throws SQLException, IOException {
        StatsUserDimension statsUser = (StatsUserDimension) key;
        MapWritableValue mapWritableValue = (MapWritableValue) value;
        MapWritable map = mapWritableValue.getValue();
        int sessions = ((IntWritable)(map.get(new IntWritable(-1)))).get();
        int sessionsLength = ((IntWritable)(map.get(new IntWritable(-2)))).get();

        // 设置value
        int i = 0;
        switch (mapWritableValue.getKpi()) {
        case SESSIONS:
            pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getPlatform()));
            pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getDate()));
            pstmt.setInt(++i, sessions); // 会话个数
            pstmt.setInt(++i, sessionsLength); // 会话长度
            pstmt.setString(++i, conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
            pstmt.setInt(++i, sessions); // 会话个数
            pstmt.setInt(++i, sessionsLength); // 会话长度
            break;
        case BROWSER_SESSIONS:
            pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getPlatform()));
            pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getDate()));
            pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getBrowser()));
            pstmt.setInt(++i, sessions); // 会话个数
            pstmt.setInt(++i, sessionsLength); // 会话长度
            pstmt.setString(++i, conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
            pstmt.setInt(++i, sessions); // 会话个数
            pstmt.setInt(++i, sessionsLength); // 会话长度
            break;
        default:
            throw new RuntimeException("不支持该kpi的输出" + mapWritableValue.getKpi());
        }

        // 添加batch
        pstmt.addBatch();
    }

}