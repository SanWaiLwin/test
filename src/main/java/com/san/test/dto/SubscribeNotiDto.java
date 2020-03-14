package com.san.test.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sanwailwin on ၁၃-၀၃-၂၀
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL
)@NoArgsConstructor
@AllArgsConstructor
public class SubscribeNotiDto {

    private String requester;

    private String target;
}
