package com.genz.socio.controller;

import com.genz.socio.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {

    @RequestMapping("/check")
    public ApiResponse<String> healthCheck(){
        return new ApiResponse<String>(true,"application is working fine","Good Health");
    }
}
