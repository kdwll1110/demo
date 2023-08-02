package com.hyf.demo.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author ikun
 * @Date 2023/8/2 21:03
 */
@Data
@ApiModel("token信息")
public class TokenResponse {

    @ApiModelProperty("token")
    private String token;

}
