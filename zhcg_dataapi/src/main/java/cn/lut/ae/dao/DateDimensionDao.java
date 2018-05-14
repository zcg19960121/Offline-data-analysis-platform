package cn.lut.ae.dao;

import cn.lut.ae.model.DateDimension;

public interface DateDimensionDao {
    public Integer getDateDimensionId(DateDimension date);

    public Integer getDateDimensionId(int year, int season, int month, int week, int day);
}