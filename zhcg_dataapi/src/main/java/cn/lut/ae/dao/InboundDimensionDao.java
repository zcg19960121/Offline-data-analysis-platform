package cn.lut.ae.dao;

import cn.lut.ae.model.InboundDimension;

public interface InboundDimensionDao {
    public InboundDimension getInboundDimension(InboundDimension inbound);

    public InboundDimension getInboundDimension(int id);

    public InboundDimension getInboundDimension(String name);
}
