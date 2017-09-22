package com.paysafe.client;

import com.paysafe.entity.MerchantServiceStatusResponse;
import feign.RequestLine;


public interface ApiClient {

    @RequestLine("GET /accountmanagement/monitor")
    MerchantServiceStatusResponse retrieveServiceStatus();
}
