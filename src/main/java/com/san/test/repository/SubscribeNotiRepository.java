package com.san.test.repository;

import com.san.test.entity.SubscribeNoti;
import com.san.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author sanwailwin on ၁၃-၀၃-၂၀
 */
@Repository
public interface SubscribeNotiRepository extends JpaRepository<SubscribeNoti, Long>  {

    Optional<SubscribeNoti> findByRequesterAndTarget(Optional<User> requester, Optional<User> target);

    Optional<SubscribeNoti> findByRequesterAndTargetAndIsNotifyTrue(Optional<User> requester, Optional<User> target);

    List<SubscribeNoti> findByTargetAndIsNotifyTrue(User user);
}
