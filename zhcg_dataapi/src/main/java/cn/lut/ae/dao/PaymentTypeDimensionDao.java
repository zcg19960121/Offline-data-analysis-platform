package cn.lut.ae.dao;

import cn.lut.ae.model.PaymentTypeDimension;

public interface PaymentTypeDimensionDao {
	public PaymentTypeDimension getPaymentTypeDimension(PaymentTypeDimension paymentTypeDimension);

	public PaymentTypeDimension getPaymentTypeDimension(int id);

	public PaymentTypeDimension getPaymentTypeDimension(String paymentType);
}
