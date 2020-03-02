package com.nest.ib.service.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nest.ib.contract.ERC20;
import com.nest.ib.contract.NestLoanECContract;
import com.nest.ib.contract.NestOfferPriceContract;
import com.nest.ib.service.LoanService;
import com.nest.ib.utils.HttpClientUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple12;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.utils.Numeric;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * ClassName:MiningServiceImpl
 * Description:
 */
@Service
public class LoanServiceImpl implements LoanService {
    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
    // 访问的以太坊节点
    private String NODE = "https://mainnet.infura.io/v3/3f9b5d82819144ad959c992c94bcb107";
    // ETH计量单为
    private static final BigDecimal UNIT_ETH = new BigDecimal("1000000000000000000");
    // usdt计量单位
    private static final BigDecimal UNIT_USDT = new BigDecimal("1000000");
    // USDT代币合约地址
    private static final String USDT_TOKEN_ADDRESS = "0xdac17f958d2ee523a2206206994597c13d831ec7";
    // 私钥,需要先传入,才能启动
    private static String USER_PRIVATE_KEY = "";
    // 价格合约地址
    private static final String PRICE_CONTRACT_ADDRESS = "0x60cbAeEe17D19458420cE572CA32a8de337Bcc2E";
    // 借贷合约部署input
    private static final String DEPLOY_LOAN_CONTRACT_INPUT = "0x38aa5a88";
    // USDT/ETH 借贷工厂合约地址
    private static final String LOAN_FACTORY_CONTRACT_ADDRESS = "0x9eca6bb666b15ac1242ae043c7de024ca688128a";
    // 最近一笔借贷合约的区块高度
    private int END_LOAN_BLOCK_NUMBER = 0;
    // 存放所有的借贷合约地址
    private LinkedList<String> linkedList = new LinkedList();
    // gasLimit
    private BigInteger GAS_LIMIT = new BigInteger("1500000");
    // gasPrice
    private BigInteger GAS_PRICE = new BigInteger("5000000000");
    // 是否开启平仓
    private boolean START_CLOSE_RATE = false;

    /**
    *   轮询所有的借贷合约，看是否有可平仓合约
    */
    @Override
    public void loanClearBot() {
        if(!START_CLOSE_RATE){
            logger.info("还未开启平仓");
            return;
        }
        if(USER_PRIVATE_KEY.equalsIgnoreCase("")){
            logger.info("开启平仓必须填写私钥");
            return;
        }
        Web3j web3j = Web3j.build(new HttpService(NODE));
        try {
            approveUsdtToOfferFactoryContract(web3j);
        } catch (Exception e) {
            logger.info("对借贷工厂合约授权USDT失败");
            return;
        }
        Credentials credentials = Credentials.create(USER_PRIVATE_KEY);
        for (String loanContractAddress : linkedList) {

            /**
             *   检查合约状态是否为1 （已经投资未逾期）
             */
            NestLoanECContract nestLoanECContract = NestLoanECContract.load(
                    loanContractAddress,
                    web3j,
                    credentials,
                    GAS_PRICE,
                    GAS_LIMIT);
            BigInteger contractState = null;
            try {
                contractState = nestLoanECContract.checkContractState().send();
                // 只要合约状态为1的时候，才能进行平仓
                if (contractState.compareTo(new BigInteger("1")) != 0) {
                    // 如果 合约状态 > 1,那么从存储库中删除掉该合约
                    if(contractState.compareTo(new BigInteger("1")) > 0){
                        linkedList.remove(loanContractAddress);
                    }
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             *   获取借贷合约平仓价格
             */
            BigInteger closeRate = null;
            String tokenAddress = null;
            BigInteger interestMoney = null;
            String borrowerAddress = null;
            String investorAddress = null;
            BigInteger borrowValue = null;
            Tuple12<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger> loanContractData;
            try {
                // 借款金额，借币 token, 利率，周期，手续费，抵押数量，利息，借币人地址, 投资人地址，结束时间，状态，平仓价格
                loanContractData = nestLoanECContract.contractInfo().send();
                // 借款金额
                borrowValue = loanContractData.getValue1();
                // 借币token地址
                tokenAddress = loanContractData.getValue2();
                // 利息
                interestMoney = loanContractData.getValue7();
                // 借币人地址
                borrowerAddress = loanContractData.getValue8();
                // 投资人地址
                investorAddress = loanContractData.getValue9();
                // 平仓价格
                closeRate = loanContractData.getValue12();
            } catch (Exception e) {
                return;
            }
            // 第三方平仓需要打入的USDT数量（本息和）
            BigInteger usdtAmount = borrowValue.add(interestMoney);
            /**
            *   获取当前价格
            */
            BigInteger nowPrice = null;
            NestOfferPriceContract load = NestOfferPriceContract.load(PRICE_CONTRACT_ADDRESS, web3j, credentials, GAS_PRICE, GAS_LIMIT);
            Tuple3<BigInteger, BigInteger, BigInteger> send = null;
            try {
                send = load.checkPriceNow(tokenAddress).send();
            } catch (Exception e) {
                return;
            }
            // eth数量(单位WEI)
            BigInteger ethAmount = send.getValue1();
            // erc20数量(单位WEI)
            BigInteger erc20Amount = send.getValue2();
            // token精度
            BigDecimal UNIT_TOKEN = null;
            // 保留小数位
            if(tokenAddress.equalsIgnoreCase(USDT_TOKEN_ADDRESS)){
                UNIT_TOKEN = UNIT_USDT;
            }else{
                UNIT_TOKEN = UNIT_ETH;
            }
            nowPrice = new BigInteger(String.valueOf(new BigDecimal(erc20Amount).divide(UNIT_TOKEN).divide(new BigDecimal(ethAmount).divide(UNIT_ETH),18,BigDecimal.ROUND_DOWN).multiply(UNIT_TOKEN).setScale(0,BigDecimal.ROUND_DOWN)));
            /**
            *   如果当前价格 < 平仓价格，那么即可进行平仓
            */
            if(nowPrice.compareTo(closeRate) < 0){
                logger.info("合约地址：" + loanContractAddress + " 合约状态：" + contractState + "  平仓价格：" + closeRate + "  当前价格：" + nowPrice);
                // 钱包地址
                String walletAddress = credentials.getAddress();
                // 如果该借贷合约是自己发布的
                if(walletAddress.equalsIgnoreCase(borrowerAddress)){
                    System.out.println("该借贷合约是自己发布，需要进行补仓：" + loanContractAddress);
                    continue;
                }
                BigInteger nonce;
                BigInteger gasPrice;
                BigInteger ethBalance;
                BigInteger usdtBalance;
                try {
                    nonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
                    gasPrice = web3j.ethGasPrice().send().getGasPrice().multiply(new BigInteger("2"));
                    // 当前钱包ETH余额
                    ethBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
                    // 当前钱包USDT余额
                    usdtBalance = ERC20.load(USDT_TOKEN_ADDRESS, web3j, credentials, GAS_PRICE, GAS_LIMIT).balanceOf(credentials.getAddress()).send();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                // 需要打入的ETH数量： 平仓只需要打入 0.01ETH即可
                BigInteger payableAmount = new BigInteger("10000000000000000");
                // 检查ETH是否足够
                if(ethBalance.compareTo(ethAmount) <= 0){
                    logger.info("当前账户ETH不够，无法进行平仓操作");
                    return;
                }
                Function function = null;
                // 投资方平仓
                if(walletAddress.equalsIgnoreCase(investorAddress)){
                    function = new Function(
                            "closePositionInvestor",
                            Arrays.<Type>asList(
                                    new Address(loanContractAddress)),
                            Collections.<TypeReference<?>>emptyList());
                }else {
                    if(usdtBalance.compareTo(usdtAmount) < 0){
                        logger.info("当前账户usdt不够进行该合约平仓，合约地址为：" + loanContractAddress);
                        continue;
                    }
                    function = new Function(
                            "closePositionOthers",
                            Arrays.<Type>asList(
                                    new Uint256(usdtAmount),
                                    new Address(loanContractAddress)),
                            Collections.<TypeReference<?>>emptyList());
                }
                String encode = FunctionEncoder.encode(function);
                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        nonce,
                        gasPrice,
                        GAS_LIMIT,
                        LOAN_FACTORY_CONTRACT_ADDRESS,
                        new BigInteger(String.valueOf(payableAmount)),
                        encode);
                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,credentials);
                String hexValue = Numeric.toHexString(signedMessage);
                String transactionHash = null;
                try {
                    transactionHash = web3j.ethSendRawTransaction(hexValue).sendAsync().get().getTransactionHash();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                logger.info("平仓hash： " + transactionHash);
                try {
                    Thread.sleep(1000*60*5);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
    /**
    *   更新私钥
    */
    @Override
    public void updatePrivateKey(String privateKey) {
        USER_PRIVATE_KEY = privateKey;
    }
    /**
    *   开启/关闭 平仓
    */
    @Override
    public void updateCloseRate() {
        if(START_CLOSE_RATE == false){
            START_CLOSE_RATE = true;
        }else {
            START_CLOSE_RATE = false;
        }
    }
    /**
    *   更换节点
    */
    @Override
    public void updateNode(String node) {
        NODE = node;
    }
    /**
    *   平仓配置数据
    */
    @Override
    public JSONObject loanCloseRateData() {
        JSONObject jsonObject = new JSONObject();
        String address;
        try{
            Credentials credentials = Credentials.create(USER_PRIVATE_KEY);
            address = credentials.getAddress();
        }catch (Exception e){
            address = "请填写正确的私钥";
        }
        jsonObject.put("walletAddress",address);
        jsonObject.put("node",NODE);
        jsonObject.put("startCloseRate",START_CLOSE_RATE);
        return jsonObject;
    }

    /**
    *  获取所有的借贷合约
    */
    @Override
    public void getLoanContract() {
        // 获取借贷工厂合约所有数据
        String url = "https://api-cn.etherscan.com/api?module=account&action=txlist&address=0x9eCA6bb666b15aC1242ae043C7de024cA688128A&startblock=" + END_LOAN_BLOCK_NUMBER + "&endblock=999999999&sort=asc&apikey=YourApiKeyToken";
        String s = null;
        try{
            s = HttpClientUtil.sendHttpGet(url);
        }catch (Exception e){
            logger.error("请检查当前网络状态，连接etherscan api错误");
            return;
        }
        if(s == null){
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(s);
        String status = jsonObject.getString("status");
        if(!status.equalsIgnoreCase("1")){
            logger.info("etherscan api 连接失败");
            return ;
        }
        JSONArray result = jsonObject.getJSONArray("result");
        if(result == null || result.size() == 0){
            return;
        }
        Web3j web3j = Web3j.build(new HttpService(NODE));
        for (int i = 0; i < result.size(); i++) {
            JSONObject transactionData = result.getJSONObject(i);
            String isError = transactionData.getString("isError");
            // 该交易是成功的
            if(!isError.equalsIgnoreCase("0")){
                continue;
            }
            String input = transactionData.getString("input");
            if(input.length() < 10){
                continue;
            }
            if(input.substring(0,10).equalsIgnoreCase(DEPLOY_LOAN_CONTRACT_INPUT)){
                String to = transactionData.getString("to");
                if(!to.equalsIgnoreCase(LOAN_FACTORY_CONTRACT_ADDRESS)){
                    continue;
                }
                String hash = transactionData.getString("hash");
                String contractAddress = getContractAddress(hash, web3j);
                if(contractAddress == null){
                    logger.info("该hash查询到的合约地址为空：" + hash);
                    return;
                }
                // 合约存储防止重复
                if(linkedList.contains(contractAddress)){
                    continue;
                }
                linkedList.add(contractAddress);
                logger.info("存储借贷合约地址：" + contractAddress);
                // 更新最新的借贷合约区块高度
                int blockNumber = transactionData.getIntValue("blockNumber");
                END_LOAN_BLOCK_NUMBER = Math.max(END_LOAN_BLOCK_NUMBER, blockNumber);
            }
        }
        return;
    }
    /**
     *  通过hash查借贷合约地址
     */
    private static String getContractAddress(String hash, Web3j web3j){
        try {
            EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(hash).sendAsync().get();
            TransactionReceipt result = ethGetTransactionReceipt.getResult();
            List<Log> logs = result.getLogs();
            for(Log log : logs) {
                List<String> topics = log.getTopics();
                for (String topic : topics) {
                    if(topic.equalsIgnoreCase("0x7442bea25fc5f497f0aa40fc4a1b3e2365602b78c35ab6a520fde45e92a383f1")){
                        String data = log.getData();
                        String contractAddress = "0x" + data.substring(26);
                        return contractAddress;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *  检测是否对USDT进行了一次性授权,如果没有,即进行一次性授权
     */
    private void approveUsdtToOfferFactoryContract(Web3j web3j) throws Exception {
        Credentials credentials = Credentials.create(USER_PRIVATE_KEY);
        ERC20 load = ERC20.load(USDT_TOKEN_ADDRESS, web3j, credentials, new BigInteger("10000000000"), new BigInteger("500000"));
        BigInteger approveValue = load.allowance(credentials.getAddress(), LOAN_FACTORY_CONTRACT_ADDRESS).send();
        if(approveValue.compareTo(new BigInteger("10000000000")) < 0){
            String transactionHash = load.approve(LOAN_FACTORY_CONTRACT_ADDRESS, new BigInteger("99999999999999999999")).send().getTransactionHash();
            System.out.println("对借贷工厂合约一次性授权USDT生成hash：" + transactionHash);
            Thread.sleep(1000*60*2);
        }
    }
}
