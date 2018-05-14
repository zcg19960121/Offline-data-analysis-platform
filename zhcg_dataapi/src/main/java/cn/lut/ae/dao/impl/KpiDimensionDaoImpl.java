package cn.lut.ae.dao.impl;

import cn.lut.ae.dao.KpiDimensionDao;
import cn.lut.ae.dao.mybatis.BaseDao;
import cn.lut.ae.model.KpiDimension;

public class KpiDimensionDaoImpl extends BaseDao implements KpiDimensionDao {

    private static String modelClass = KpiDimension.class.getName();
    private static String getKpiDimension = modelClass + ".getKpiDimension";

    @Override
    public KpiDimension getKpiDimension(KpiDimension kpi) {
        return this.getSqlSession().selectOne(getKpiDimension, kpi);
    }

    @Override
    public KpiDimension getKpiDimension(String name) {
        KpiDimension kpi = new KpiDimension(name);
        return getKpiDimension(kpi);
    }

    @Override
    public KpiDimension getKpiDimension(int id) {
        KpiDimension kpi = new KpiDimension(id);
        return getKpiDimension(kpi);
    }

}