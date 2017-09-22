package com.paysafe.config;

import com.paysafe.config.properties.SchedulerProperties;
import com.paysafe.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer {

    @Autowired
    private SchedulerProperties schedulerProperties;
    @Autowired
    private MonitoringService monitoringService;

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(1);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.scheduleTriggerTask(new TriggerTask(
                () -> monitoringService.verifyMerchantServiceAvailable(),
                triggerContext -> {
                    Calendar nextExecutionTime =  new GregorianCalendar();
                    Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                    nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                    nextExecutionTime.add(Calendar.MILLISECOND, schedulerProperties.getRate().intValue());
                    return nextExecutionTime.getTime();
                })
         );
    }
}
