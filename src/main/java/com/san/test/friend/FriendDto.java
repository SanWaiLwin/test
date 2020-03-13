package com.san.test.friend;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author sanwailwin on ၁၂-၀၃-၂၀
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL
)@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {

    private Boolean success = false;

    private List<String> friends;

    private Integer count;
}
