package com.san.test.friend;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author sanwailwin on ၁၃-၀၃-၂၀
 */
@Getter
@Setter
@Entity
@Table(name = "notify")
public class SubscribeNoti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User requestor;

    @ManyToOne
    private User targer;
}
