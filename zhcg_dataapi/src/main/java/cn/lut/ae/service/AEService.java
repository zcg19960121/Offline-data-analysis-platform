package cn.lut.ae.service;

import java.util.List;
import java.util.Map;

import cn.lut.ae.model.QueryModel;

/**
 * 处理ae基本数据交换的接口
 * 
 * @author gg
 *
 */
public interface AEService {
    public List<Map<String, Object>> execute(QueryModel queryModel) throws Exception;

}
