package com.paysafe.service;

import com.paysafe.client.ApiClient;
import com.paysafe.config.properties.ApiServerProperties;
import com.paysafe.config.properties.SchedulerProperties;
import com.paysafe.entity.ServiceStatus;
import com.paysafe.factory.MonitoringServiceFactory;
import com.paysafe.repository.MonitoringServiceRepository;
import com.paysafe.service.dto.MonitoringServicePropertiesDTO;
import com.paysafe.service.dto.ServiceStatusDTO;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rx.Observable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringService {

    private boolean started;

    private ApiClient apiClient;
    private final SchedulerProperties schedulerProperties;
    private final ApiServerProperties apiServerProperties;
    private final MonitoringServiceFactory monitoringServiceFactory;
    private final MonitoringServiceRepository monitoringServiceRepository;

    public void start(MonitoringServicePropertiesDTO monitoringServicePropertiesDTO) {
        started = true;
        updateMerchantServiceMonitoringServiceInterval(monitoringServicePropertiesDTO.getInterval());
        updateMerchantServiceHostname(monitoringServicePropertiesDTO.getHostname());
    }

    public void stop() {
        started = false;
    }

    public void verifyMerchantServiceAvailable(){

        if(started) {
            Observable.just(apiClient.retrieveServiceStatus())
                    .filter(Objects::nonNull)
                    .map(monitoringServiceFactory::createUpStatus)
                    .defaultIfEmpty(monitoringServiceFactory.createUnknownStatus())
                    .subscribe(monitoringServiceRepository::saveServerUpTime);
        } else {
            Observable.just(monitoringServiceFactory.createDownStatus())
                    .subscribe(monitoringServiceRepository::saveServerDownTime);
        }
    }

    public ServiceStatusDTO getHealth(){
        List<ServiceStatus> upTimes = new ArrayList<>(monitoringServiceRepository.getServerUpTimes());
        List<ServiceStatus> downTimes = new ArrayList<>(monitoringServiceRepository.getServerDownTimes());
        return ServiceStatusDTO.builder().upTimes(upTimes).downTimes(downTimes).build();
    }

    private void updateMerchantServiceMonitoringServiceInterval(Long interval) {
        if(interval != null)
            schedulerProperties.setRate(interval);
    }

    private void updateMerchantServiceHostname(String hostname){
        updateMerchantServiceClient(hostname);
    }

    @PostConstruct
    private void initializeMerchantServiceClient(){
        updateMerchantServiceClient(null);
    }

    private void updateMerchantServiceClient(String hostname){
        this.apiClient =  Optional.ofNullable(hostname)
                .map(host -> new HystrixFeign.Builder()
                        .decoder(new JacksonDecoder())
                        .target(ApiClient.class, host))
                .orElse( new HystrixFeign.Builder()
                        .decoder(new JacksonDecoder())
                        .target(ApiClient.class, apiServerProperties.getHostname()));
    }

}
