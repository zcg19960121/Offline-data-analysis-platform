package cn.lut.ae.dao;

import cn.lut.ae.model.EventDimension;

public interface EventDimensionDao {
    public EventDimension getEventDimension(EventDimension eventDimension);

    public EventDimension getEventDimension(int id);

    public EventDimension getEventDimension(String category,String action);

}
