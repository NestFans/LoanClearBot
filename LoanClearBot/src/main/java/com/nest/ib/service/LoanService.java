package com.nest.ib.service;


import com.alibaba.fastjson.JSONObject;

/**
 * ClassName:LoanService
 * Description:
 */
public interface LoanService {
    // 存储借贷合约
    void getLoanContract();
    // 平仓
    void loanClearBot();
    // 更新私钥
    void updatePrivateKey(String privateKey);
    // 开启平仓
    void updateCloseRate();
    // 更换节点
    void updateNode(String node);
    // 平仓配置数据展示
    JSONObject loanCloseRateData();
}
