package com.san.test.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sanwailwin on ၁၂-၀၃-၂၀
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUser1OrUser2(User user, User user1);
}
