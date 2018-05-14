package cn.lut.ae.calculate.impl;

import java.math.BigDecimal;
import java.util.Map;

import cn.lut.ae.calculate.AECalculate;
import cn.lut.ae.common.AEConstants;

/**
 * 计算active rate
 * 
 * @author gg
 * 
 */
public class ActiveRateCalculater implements AECalculate {

    @Override
    public Object calculate(Map<String, Object> metricData) {
        BigDecimal totalUsers = BigDecimal.valueOf(Long.valueOf(metricData.get(AEConstants.PRIFIX + AEConstants.TOTAL_USERS).toString()));
        BigDecimal activeUsers = BigDecimal.valueOf(Long.valueOf(metricData.get(AEConstants.PRIFIX + AEConstants.ACTIVE_USERS).toString()));
        if (totalUsers == null || activeUsers == null) {
            return -1;
        }
        if (totalUsers.doubleValue() == 0.0 || activeUsers.doubleValue() == 0.0) {
            return 0;
        }
        return activeUsers.divide(totalUsers, 4, BigDecimal.ROUND_HALF_UP);
    }

}
