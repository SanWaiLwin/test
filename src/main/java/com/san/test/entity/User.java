package com.san.test.entity;

import com.san.test.enums.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * @author sanwailwin on ၁၂-၀၃-၂၀
 */
@Getter
@Setter
@Entity
@Table(name = "user1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(required = false, hidden = true)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

}
