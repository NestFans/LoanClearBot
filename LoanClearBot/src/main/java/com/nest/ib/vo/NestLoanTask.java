package com.nest.ib.vo;

import com.nest.ib.service.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * ClassName:applicationBonusStorage
 * Description:
 */
@Component
public class NestLoanTask {

    @Autowired
    private LoanService loanService;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(50);
        return taskScheduler;
    }

    /**
    *   平仓
    */
    @Scheduled(fixedDelay = 30000)
    public void nestLoan(){
        loanService.loanClearBot();
    }
    /**
    *   存储借贷合约
    */
    @Scheduled(fixedDelay = 300000)
    public void saveLoanContract(){
        loanService.getLoanContract();
    }

}
