package com.nest.ib.controller;

import com.alibaba.fastjson.JSONObject;
import com.nest.ib.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

/**
 * ClassName:MiningController
 * Description:
 */
@RestController
@RequestMapping("/loan")
public class MiningController {

    @Autowired
    private LoanService loanService;

    /**
    *   更新私钥
    */
    @PostMapping("/updatePrivateKey")
    public void updatePrivateKey(@RequestParam(name = "privateKey") String privateKey){
        try{
            Credentials credentials = Credentials.create(privateKey);
            String address = credentials.getAddress();
            if(address.substring(0,2).equalsIgnoreCase("0x") && address.length() == 42){
                loanService.updatePrivateKey(privateKey);
            }
        }catch (Exception e){
            System.out.println("请填写正确的私钥");
            return;
        }
    }
    /**
    *   更改平仓状态： 开启/关闭
    */
    @RequestMapping("/updateCloseRate")
    public void updateCloseRate(){
        loanService.updateCloseRate();
    }
    /**
    *   平仓配置详情
    */
    @GetMapping("/miningData")
    public ModelAndView miningData(){
        JSONObject jsonObject = loanService.loanCloseRateData();
        ModelAndView mav = new ModelAndView("miningData");
        mav.addObject("address",jsonObject.getString("walletAddress"));
        mav.addObject("node",jsonObject.getString("node"));
        mav.addObject("startCloseRate",jsonObject.getBooleanValue("startCloseRate") == true ? "自动平仓状态: 开启" : "自动平仓状态: 关闭");
        return mav;
    }

    /**
     *   更换节点
     */
    @PostMapping("/updateNode")
    public void updateNode(@RequestParam(name = "node") String node){
        Web3j web3j = Web3j.build(new HttpService(node));
        try {
            BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();
            System.out.println("更换节点时的区块高度： " + blockNumber);
            loanService.updateNode(node);
        } catch (IOException e) {
            System.out.println("请填写正确的节点");
        }
    }



}
