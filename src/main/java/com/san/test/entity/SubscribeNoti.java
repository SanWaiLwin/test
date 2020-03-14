package com.san.test.entity;

import com.san.test.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private User requester;

    @ManyToOne
    private User target;

    private Boolean isNotify = true;
}
