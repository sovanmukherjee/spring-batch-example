package com.sm.demo.springbatch.proxy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserExternalClientResponse {
    @Builder.Default
    private boolean isSuccess = false;
}
