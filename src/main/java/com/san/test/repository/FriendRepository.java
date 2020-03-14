package com.san.test.repository;

import com.san.test.entity.Friend;
import com.san.test.entity.User;
import com.san.test.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author sanwailwin on ၁၂-၀၃-၂၀
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUser1OrUser2(User user, User user1);

    List<Friend> findByUser1OrUser2AndUserType(User user, User user1, UserType yesFriend);
}
