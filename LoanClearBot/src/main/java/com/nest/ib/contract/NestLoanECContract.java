package com.nest.ib.contract;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple12;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class NestLoanECContract extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b506040516200188638038062001886833981810160405260808110156200003757600080fd5b810190808051906020019092919080519060200190929190805190602001909291908051906020019092919050505033600760006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508360038190555073f033112fea8509ad01e4530e9f0c97e0d2132144600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550826005819055508160068190555062000165612710620001516006546200013d600554600354620002f260201b620013d11790919060201c565b620002f260201b620013d11790919060201c565b6200033060201b6200140b1790919060201c565b60048190555080600181905550600060088190555073eeb4e9edfeecd5b4d91447b0cf94b7340120a9ed6000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555032600960006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555042600c81905550620002a462000237600a600154620002f260201b620013d11790919060201c565b62000290670de0b6b3a76400006200027c600b620002686004546003546200035860201b620014311790919060201c565b620002f260201b620013d11790919060201c565b620002f260201b620013d11790919060201c565b6200033060201b6200140b1790919060201c565b600e81905550620002e26103e8620002ce6002600154620002f260201b620013d11790919060201c565b6200033060201b6200140b1790919060201c565b600b819055505050505062000378565b6000808314156200030757600090506200032a565b60008284029050828482816200031957fe5b04146200032557600080fd5b809150505b92915050565b60008082116200033f57600080fd5b60008284816200034b57fe5b0490508091505092915050565b6000808284019050838110156200036e57600080fd5b8091505092915050565b6114fe80620003886000396000f3fe60806040526004361061009c5760003560e01c80635d9c11ca116100645780635d9c11ca1461022b5780636737b31e146102425780637690e8971461027d57806387f27944146102ab578063a89cd2b2146102c2578063e4d4c3c0146102cc5761009c565b806313e24862146100a157806314a1362b146100cc57806315c43aaf146100fa5780632b5929fd146101f6578063527ce19614610200575b600080fd5b3480156100ad57600080fd5b506100b6610307565b6040518082815260200191505060405180910390f35b6100f8600480360360208110156100e257600080fd5b8101908080359060200190929190505050610333565b005b34801561010657600080fd5b5061010f6105dd565b604051808d81526020018c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018b81526020018a81526020018981526020018881526020018781526020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018381526020018281526020019c5050505050505050505050505060405180910390f35b6101fe6106d6565b005b34801561020c57600080fd5b50610215610754565b6040518082815260200191505060405180910390f35b34801561023757600080fd5b50610240610969565b005b34801561024e57600080fd5b5061027b6004803603602081101561026557600080fd5b8101908080359060200190929190505050610a1e565b005b6102a96004803603602081101561029357600080fd5b8101908080359060200190929190505050610c66565b005b3480156102b757600080fd5b506102c0610f10565b005b6102ca610fb6565b005b3480156102d857600080fd5b50610305600480360360208110156102ef57600080fd5b81019080803590602001909291905050506110de565b005b6000600160085414801561031c5750600d5442115b1561032a5760029050610330565b60085490505b90565b600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161461038d57600080fd5b600760009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146103e757600080fd5b60016103f1610307565b146103fb57600080fd5b60006104256103e86104176002856113d190919063ffffffff16565b61140b90919063ffffffff16565b905061043a818361143190919063ffffffff16565b341461044557600080fd5b61045a8260015461143190919063ffffffff16565b60018190555061047581600b5461143190919063ffffffff16565b600b8190555060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638fe77e866040518163ffffffff1660e01b81526004018080602001828103825260068152602001807f61626f6e7573000000000000000000000000000000000000000000000000000081525060200191505060206040518083038186803b15801561052057600080fd5b505afa158015610534573d6000803e3d6000fd5b505050506040513d602081101561054a57600080fd5b810190808051906020019092919050505090506105678183611450565b6105d2610580600a6001546113d190919063ffffffff16565b6105c4670de0b6b3a76400006105b6600b6105a860045460035461143190919063ffffffff16565b6113d190919063ffffffff16565b6113d190919063ffffffff16565b61140b90919063ffffffff16565b600e81905550505050565b6000806000806000806000806000806000803273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461062757600080fd5b600354600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600654600554600b54600154600454600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600d546106ad610307565b600e549b509b509b509b509b509b509b509b509b509b509b509b50909192939495969798999a9b565b600760009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461073057600080fd5b610747600b5460015461143190919063ffffffff16565b341461075257600080fd5b565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638fe77e866040518163ffffffff1660e01b815260040180806020018281038252600a8152602001807f6f6666657250726963650000000000000000000000000000000000000000000081525060200191505060206040518083038186803b1580156107fa57600080fd5b505afa15801561080e573d6000803e3d6000fd5b505050506040513d602081101561082457600080fd5b81019080805190602001909291905050509050600080905060008090508273ffffffffffffffffffffffffffffffffffffffff1663a834d32e662386f26fc1000073f033112fea8509ad01e4530e9f0c97e0d21321446040518363ffffffff1660e01b8152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019150506060604051808303818588803b1580156108db57600080fd5b505af11580156108ef573d6000803e3d6000fd5b50505050506040513d606081101561090657600080fd5b8101908080519060200190929190805190602001909291908051906020019092919050505050809250819350505061096182610953670de0b6b3a7640000846113d190919063ffffffff16565b61140b90919063ffffffff16565b935050505090565b600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff16146109c357600080fd5b6000600854146109d257600080fd5b610a14600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16610a0f600b5460015461143190919063ffffffff16565b611450565b6003600881905550565b600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff1614610a7857600080fd5b600760009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610ad257600080fd5b6001610adc610307565b14610ae657600080fd5b610afd60045460035461143190919063ffffffff16565b8114610b0857600080fd5b610b36600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600154611450565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16610bae60045460035461143190919063ffffffff16565b6040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b158015610c1757600080fd5b505af1158015610c2b573d6000803e3d6000fd5b505050506040513d6020811015610c4157600080fd5b8101908080519060200190929190505050610c5b57600080fd5b600360088190555050565b600760009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610cc057600080fd5b6001610cca610307565b14610cd457600080fd5b600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415610d2f57600080fd5b600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415610d8a57600080fd5b600e54610d95610754565b10610d9f57600080fd5b662386f26fc100003414610db257600080fd5b610dc960045460035461143190919063ffffffff16565b8114610dd457600080fd5b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16610e4c60045460035461143190919063ffffffff16565b6040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b158015610eb557600080fd5b505af1158015610ec9573d6000803e3d6000fd5b505050506040513d6020811015610edf57600080fd5b8101908080519060200190929190505050610ef957600080fd5b610f0532600154611450565b600460088190555050565b600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff1614610f6a57600080fd5b6002610f74610307565b14610f7e57600080fd5b610fac600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600154611450565b6003600881905550565b600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161461101057600080fd5b600760009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461106a57600080fd5b6001611074610307565b1461107e57600080fd5b600e54611089610754565b1061109357600080fd5b662386f26fc1000034146110a657600080fd5b6110d4600a60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600154611450565b6004600881905550565b600760009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461113857600080fd5b60006008541461114757600080fd5b600354811461115557600080fd5b32600a60006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638fe77e866040518163ffffffff1660e01b81526004018080602001828103825260068152602001807f61626f6e7573000000000000000000000000000000000000000000000000000081525060200191505060206040518083038186803b15801561123b57600080fd5b505afa15801561124f573d6000803e3d6000fd5b505050506040513d602081101561126557600080fd5b8101908080519060200190929190505050905061128481600b54611450565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166003546040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b15801561135157600080fd5b505af1158015611365573d6000803e3d6000fd5b505050506040513d602081101561137b57600080fd5b810190808051906020019092919050505061139557600080fd5b60016008819055506113c76113b6603c6005546113d190919063ffffffff16565b600c5461143190919063ffffffff16565b600d819055505050565b6000808314156113e45760009050611405565b60008284029050828482816113f557fe5b041461140057600080fd5b809150505b92915050565b600080821161141957600080fd5b600082848161142457fe5b0490508091505092915050565b60008082840190508381101561144657600080fd5b8091505092915050565b60006114718373ffffffffffffffffffffffffffffffffffffffff166114bf565b90508073ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f193505050501580156114b9573d6000803e3d6000fd5b50505050565b600081905091905056fea265627a7a72315820fa239c5a0a4a93c3019aad141271cb393c48ce2e4800b2b34854ee985ade9a2764736f6c634300050c0032";

    protected NestLoanECContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NestLoanECContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RemoteCall<NestLoanECContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _borrowAmount, BigInteger _cycle, BigInteger _interestRate, BigInteger _mortgageAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(_borrowAmount),
                new Uint256(_cycle),
                new Uint256(_interestRate),
                new Uint256(_mortgageAmount)));
        return deployRemoteCall(NestLoanECContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<NestLoanECContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _borrowAmount, BigInteger _cycle, BigInteger _interestRate, BigInteger _mortgageAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(_borrowAmount),
                new Uint256(_cycle),
                new Uint256(_interestRate),
                new Uint256(_mortgageAmount)));
        return deployRemoteCall(NestLoanECContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteCall<BigInteger> checkContractState() {
        final Function function = new Function("checkContractState",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> closePositionInvestor(BigInteger weiValue) {
        final Function function = new Function(
                "closePositionInvestor", 
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> closePositionOthers(BigInteger amount, BigInteger weiValue) {
        final Function function = new Function(
                "closePositionOthers", 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Tuple12<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger>> contractInfo() {
        final Function function = new Function("contractInfo",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple12<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple12<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple12<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple12<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (String) results.get(7).getValue(), 
                                (String) results.get(8).getValue(), 
                                (BigInteger) results.get(9).getValue(), 
                                (BigInteger) results.get(10).getValue(), 
                                (BigInteger) results.get(11).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> cover(BigInteger amount, BigInteger weiValue) {
        final Function function = new Function(
                "cover", 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> investment(BigInteger amount) {
        final Function function = new Function(
                "investment", 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> nowPrice() {
        final Function function = new Function(
                "nowPrice", 
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> overdue() {
        final Function function = new Function(
                "overdue", 
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> repayment(BigInteger amount) {
        final Function function = new Function(
                "repayment", 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> retrieveMortgagedAssets() {
        final Function function = new Function(
                "retrieveMortgagedAssets", 
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferredToMortgagedAssets(BigInteger weiValue) {
        final Function function = new Function(
                "transferredToMortgagedAssets", 
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static NestLoanECContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NestLoanECContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static NestLoanECContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NestLoanECContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
