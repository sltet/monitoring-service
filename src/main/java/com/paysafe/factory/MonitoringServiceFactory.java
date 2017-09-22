package com.paysafe.factory;

import com.paysafe.entity.MerchantServiceStatusResponse;
import com.paysafe.entity.ServiceStatus;
import com.paysafe.entity.Status;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MonitoringServiceFactory {


    public ServiceStatus createDownStatus(){
        return ServiceStatus.builder()
                .status(Status.DOWN)
                .date(new Date())
                .build();
    }
    public ServiceStatus createUpStatus(MerchantServiceStatusResponse merchantServiceStatusResponse){
        return ServiceStatus.builder()
                .status(Status.UP)
                .apiStatus(merchantServiceStatusResponse.getStatus())
                .date(new Date())
                .build();
    }
    public ServiceStatus createUnknownStatus(){
        return ServiceStatus.builder()
                .status(Status.UP)
                .date(new Date())
                .build();
    }

}
