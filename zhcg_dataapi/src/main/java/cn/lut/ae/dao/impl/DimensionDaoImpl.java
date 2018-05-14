package cn.lut.ae.dao.impl;

import java.util.List;
import java.util.Map;

import cn.lut.ae.dao.DimensionDao;
import cn.lut.ae.dao.mybatis.BaseDao;

public class DimensionDaoImpl extends BaseDao implements DimensionDao {

    private static String nameSpace = DimensionDao.class.getName();
    private static String queryAllDimensionsId = ".queryDimensions";
    private static String queryAllDimensionsSql = nameSpace + queryAllDimensionsId;

    @Override
    public List<Map<String, Object>> queryDimensionData(Map<String, String> queryMap) {
        List<Map<String, Object>> list = this.getSqlSession().selectList(queryAllDimensionsSql, queryMap);
        return list;
    }

}
