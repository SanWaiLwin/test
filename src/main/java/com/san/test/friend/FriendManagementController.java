package com.san.test.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author sanwailwin on ၁၂-၀၃-၂၀
 */
@RestController
@RequestMapping("/api/friend")
public class FriendManagementController {

    @Autowired
    private FriendService firendService;

    @PostMapping("/save-user")
    public ResponseEntity saveUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(firendService.saveUser(user));
    }

    @PostMapping("/add-friend")
    public RestWrapperDTO addFriend(@Valid @RequestBody FriendDto friendDto) {
        return firendService.saveFriend(friendDto);
    }

    @GetMapping("/find-friend-by-user")
    public ResponseEntity findFriendByUser(@RequestParam String email) {
        return ResponseEntity.ok(firendService.findFriendByEmail(email));
    }

    @GetMapping("/find-common-friend")
    public ResponseEntity findCommonFriend(@RequestParam String email1, @RequestParam String email2) {
        return ResponseEntity.ok(firendService.findCommonFriend(email1, email2));
    }

    @PostMapping("/subscribe-noti")
    public RestWrapperDTO subscribeNoti(@Valid @RequestBody SubscribeNotiDto subscribeNotiDto) {
        return firendService.subscribeNoti(subscribeNotiDto);
    }

}
