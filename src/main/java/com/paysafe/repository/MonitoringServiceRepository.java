package com.paysafe.repository;

import com.paysafe.entity.ServiceStatus;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class MonitoringServiceRepository {

    private Set<ServiceStatus> serverUpTime = new LinkedHashSet<>();
    private Set<ServiceStatus> serverDownTime = new LinkedHashSet<>();

    public void saveServerUpTime(ServiceStatus serviceStatus){
        serverUpTime.add(serviceStatus);
    }

    public void saveServerDownTime(ServiceStatus serviceStatus){
        serverDownTime.add(serviceStatus);
    }
    public Set<ServiceStatus> getServerUpTimes(){
        return serverUpTime;
    }

    public Set<ServiceStatus> getServerDownTimes(){
        return serverDownTime;
    }
}
