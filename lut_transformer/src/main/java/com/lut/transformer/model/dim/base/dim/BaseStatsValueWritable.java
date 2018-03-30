package com.lut.transformer.model.dim.base.dim;

import org.apache.hadoop.io.Writable;

import com.lut.common.KpiType;
/**
 * 自定义顶级的输出value父类
 * 
 * @author gg
 *
 */
public abstract class BaseStatsValueWritable implements Writable {
	/**
     * 获取当前value对应的kpi值
     * 
     * @return
     */
	public abstract KpiType getKpi();
}
