package com.san.test.controller;

import com.san.test.dto.*;
import com.san.test.service.FriendService;
import com.san.test.entity.User;
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
    private FriendService friendService;

    @PostMapping("/save-user")
    public ResponseEntity saveUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(friendService.saveUser(user));
    }

    @PostMapping("/add-friend")
    public RestWrapperDTO addFriend(@Valid @RequestBody FriListDto friListDto) {
        return friendService.saveFriend(friListDto);
    }

    @GetMapping("/find-friend-by-user")
    public ResponseEntity findFriendByUser(@RequestParam String email) {
        return ResponseEntity.ok(friendService.findFriendByEmail(email));
    }

    @GetMapping("/find-common-friend")
    public ResponseEntity findCommonFriend(@RequestParam String email1, @RequestParam String email2) {
        return ResponseEntity.ok(friendService.findCommonFriend(email1, email2));
    }

    @PostMapping("/subscribe-noti")
    public RestWrapperDTO subscribeNoti(@Valid @RequestBody SubscribeNotiDto subscribeNotiDto) {
        return friendService.subscribeNoti(subscribeNotiDto);
    }

    @PutMapping("/block-noti")
    public RestWrapperDTO blockNoti(@Valid @RequestBody SubscribeNotiDto subscribeNotiDto) {
        return friendService.blockNoti(subscribeNotiDto);
    }

    @GetMapping("/get-notify-user")
    public ResponseEntity getNotifyUser(@RequestParam String sender, @RequestParam String text) {
        return ResponseEntity.ok(friendService.getNotifyUser(sender, text));
    }
}
