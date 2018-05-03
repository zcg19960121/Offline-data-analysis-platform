package com.lut.transformer.mr.au;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;

import com.lut.common.GlobalConstants;
import com.lut.transformer.model.dim.StatsUserDimension;
import com.lut.transformer.model.dim.base.BaseDimension;
import com.lut.transformer.model.value.BaseStatsValueWritable;
import com.lut.transformer.model.value.reduce.MapWritableValue;
import com.lut.transformer.mr.IOutputCollector;
import com.lut.transformer.service.rpc.IDimensionConverter;

public class ActiveUserCollector implements IOutputCollector{

	@Override
	public void collect(Configuration conf, BaseDimension key, BaseStatsValueWritable value, PreparedStatement pstmt,
			IDimensionConverter converter) throws SQLException, IOException {

		//进行强制转型后获取对应的值
		StatsUserDimension statsUser = (StatsUserDimension) key;
		IntWritable activeUserValue = (IntWritable) ((MapWritableValue)value).getValue().get(new IntWritable(-1));
		//进行参数设置
		int i = 0;
		pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getPlatform()));
		pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getDate()));
		pstmt.setInt(++i, activeUserValue.get());
		pstmt.setString(++i, conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
		pstmt.setInt(++i, activeUserValue.get());

        // 添加到batch中
		pstmt.addBatch();
	}

}
