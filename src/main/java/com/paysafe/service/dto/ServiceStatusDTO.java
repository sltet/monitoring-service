package com.paysafe.service.dto;

import com.paysafe.entity.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServiceStatusDTO {


    private List<ServiceStatus> upTimes;

    private List<ServiceStatus> downTimes;

}
