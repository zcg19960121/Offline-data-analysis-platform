package com.lut.transformer.mr.nu;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.lut.common.DateEnum;
import com.lut.common.EventLogConstants;
import com.lut.common.KpiType;
import com.lut.transformer.model.dim.StatsCommonDimension;
import com.lut.transformer.model.dim.StatsUserDimension;
import com.lut.transformer.model.dim.base.BrowserDimension;
import com.lut.transformer.model.dim.base.DateDimension;
import com.lut.transformer.model.dim.base.KpiDimension;
import com.lut.transformer.model.dim.base.PlatformDimension;
import com.lut.transformer.model.value.map.TimeOutputValue;

/**
 * 自定义的计算新用户的mapper类
 * 
 * @author gg
 *
 */
public class NewInstallUserMapper extends TableMapper<StatsUserDimension, TimeOutputValue>{
	private static final Logger logger = Logger.getLogger(NewInstallUserMapper.class);
	private StatsUserDimension statsUserDimension = new StatsUserDimension();
	private TimeOutputValue timeOutputValue = new TimeOutputValue();
	private byte[] family = Bytes.toBytes(EventLogConstants.EVENT_LOGS_FAMILY_NAME);
	private KpiDimension newInstallUserKpi = new KpiDimension(KpiType.NEW_INSTALL_USER.name);
	private KpiDimension newInstallUserOfBrowserKpi = new KpiDimension(KpiType.BROWSER_NEW_INSTALL_USER.name);
	
	@Override
	protected void map(ImmutableBytesWritable key, Result value,
			Context context)
			throws IOException, InterruptedException {
		String uuid = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_UUID)));
		String serverTime = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME)));
		String platform = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_PLATFORM)));
		if(StringUtils.isBlank(uuid) || StringUtils.isBlank(serverTime) || StringUtils.isBlank(platform)){
			logger.warn("uuid&servertime&platform不能为空");
			return;
		}
		long longOfTime = Long.valueOf(serverTime.trim());
		timeOutputValue.setId(uuid);// 设置id为uuid
		timeOutputValue.setTime(longOfTime);// 设置时间为服务器时间
		DateDimension dateDimension = DateDimension.buildDate(longOfTime, DateEnum.DAY);
		List<PlatformDimension> platformDimensions = PlatformDimension.buildList(platform);
		//设置date维度
		StatsCommonDimension statsCommonDimension = this.statsUserDimension.getStatsCommon();
		statsCommonDimension.setDate(dateDimension);
		//写browser相关的数据
		String browserName = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_BROWSER_NAME)));
		String browserVersion = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_BROWSER_VERSION)));
		List<BrowserDimension> browserDimensions = BrowserDimension.buildList(browserName, browserVersion);
		BrowserDimension defaultBrowser = new BrowserDimension("","");
		for(PlatformDimension pf : platformDimensions){
			//设置一个默认值
			statsUserDimension.setBrowser(defaultBrowser);
			//解决有空的browser输出的bug
			//statsUserDimension.getBrowser().clean();
			statsCommonDimension.setKpi(newInstallUserKpi);
			statsCommonDimension.setPlatform(pf);
			context.write(statsUserDimension, timeOutputValue);
			for(BrowserDimension br : browserDimensions){
				statsCommonDimension.setKpi(newInstallUserOfBrowserKpi);
				statsUserDimension.setBrowser(br);
				//由于上面需要进行clean操作，故将该值进行clone后填充
			    //statsUserDimension.setBrowser(WritableUtils.clone(br, context.getConfiguration()));
				context.write(statsUserDimension, timeOutputValue);
			}
		}
	}
}
