package cn.lut.ae.dao;

import cn.lut.ae.model.BrowserDimension;

public interface BrowserDimensionDao {
	public BrowserDimension getBrowserDimension(BrowserDimension browserDimension);

	public BrowserDimension getBrowserDimension(int id);

	public BrowserDimension getBrowserDimension(String browser,String browserVersion);
}
