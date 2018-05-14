package cn.lut.ae.dao;

import cn.lut.ae.model.CurrencyTypeDimension;

public interface CurrencyTypeDimensionDao {
	public CurrencyTypeDimension getCurrencyTypeDimension(CurrencyTypeDimension currencyTypeDimension);

	public CurrencyTypeDimension getCurrencyTypeDimension(int id);

	public CurrencyTypeDimension getCurrencyTypeDimension(String currencyType);
}
