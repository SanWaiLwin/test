package com.san.test.entity;

import com.san.test.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author sanwailwin on ၁၂-၀၃-၂၀
 */
@Getter
@Setter
@Entity
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}
