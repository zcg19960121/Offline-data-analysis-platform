package cn.lut.ae.dao.impl;

import cn.lut.ae.dao.LocationDimensionDao;
import cn.lut.ae.dao.mybatis.BaseDao;
import cn.lut.ae.model.LocationDimension;

public class LocationDimensionDaoImpl extends BaseDao implements LocationDimensionDao {

    private static String modelClass = LocationDimension.class.getName();
    private static String getLocationDimension = modelClass + ".getLocationDimension";

    @Override
    public LocationDimension getLocationDimension(LocationDimension location) {
        return this.getSqlSession().selectOne(getLocationDimension, location);
    }

    @Override
    public LocationDimension getLocationDimension(String country, String province, String city) {
        LocationDimension location = new LocationDimension(country, province, city);
        return getLocationDimension(location);
    }

    @Override
    public LocationDimension getLocationDimension(int id) {
        LocationDimension location = new LocationDimension(id);
        return getLocationDimension(location);
    }

}