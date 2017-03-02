package com.thoughtworks.devcloud.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeCheckApiController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/tw/services" ,method = RequestMethod.GET)
    public String getServices() {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/tw/services, host:" + instance.getHost() + ", port:" + instance.getPort() + ", service_id:" + instance.getServiceId());
        return "[Delivery, Consultant]";
    }

}