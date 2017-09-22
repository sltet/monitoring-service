package com.paysafe.web.rest;

import com.paysafe.service.MonitoringService;
import com.paysafe.service.dto.MonitoringServicePropertiesDTO;
import com.paysafe.service.dto.ServiceStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/monitoring-service")
@RequiredArgsConstructor
public class MonitoringRestController {

    private final MonitoringService monitoringService;


    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity startMonitoring(@RequestBody @Valid MonitoringServicePropertiesDTO monitoringServicePropertiesDTO){
        monitoringService.start(monitoringServicePropertiesDTO);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public ResponseEntity stopMonitoring(){
        monitoringService.stop();
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<ServiceStatusDTO> getHealth(){
        ServiceStatusDTO serviceStatus = monitoringService.getHealth();
        return ResponseEntity.ok(serviceStatus);
    }

}
