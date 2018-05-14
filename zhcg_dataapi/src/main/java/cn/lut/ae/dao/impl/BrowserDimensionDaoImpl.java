package cn.lut.ae.dao.impl;

import cn.lut.ae.dao.BrowserDimensionDao;
import cn.lut.ae.dao.mybatis.BaseDao;
import cn.lut.ae.model.BrowserDimension;

public class BrowserDimensionDaoImpl extends BaseDao implements BrowserDimensionDao{
	private static String modelClass = BrowserDimension.class.getName();
	private static String getBrowserDimension = modelClass + ".getBrowserDimension";
	 
	@Override
	public BrowserDimension getBrowserDimension(BrowserDimension browserDimension) {
		return this.getSqlSession().selectOne(getBrowserDimension, browserDimension);
	}

	@Override
	public BrowserDimension getBrowserDimension(int id) {
		BrowserDimension browserDimension= new BrowserDimension(id);
		return this.getSqlSession().selectOne(getBrowserDimension, browserDimension);
		
	}

	@Override
	public BrowserDimension getBrowserDimension(String browser, String browserVersion) {
		BrowserDimension browserDimension= new BrowserDimension(browser,browserVersion);
		return this.getSqlSession().selectOne(getBrowserDimension, browserDimension);
	}
	 

}
