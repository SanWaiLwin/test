package com.san.test.service;

import com.san.test.dto.*;
import com.san.test.entity.Friend;
import com.san.test.entity.SubscribeNoti;
import com.san.test.entity.User;
import com.san.test.enums.UserType;
import com.san.test.repository.FriendRepository;
import com.san.test.repository.SubscribeNotiRepository;
import com.san.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sanwailwin on ၁၃-၀၃-၂၀
 */
@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscribeNotiRepository subscribeNotiRepository;

    public User saveUser(User user) {
        User usr = new User();
        usr.setEmail(user.getEmail());
        return userRepository.save(usr);
    }

    public RestWrapperDTO saveFriend(FriListDto friListDto) {
        RestWrapperDTO wrapperDTO = new RestWrapperDTO();
        List<String> friendDtoList = friListDto.getFriends();
        Friend friend = new Friend();
        Optional<User> user1 = userRepository.findByEmail(friendDtoList.get(0));
        Optional<User> user2 = userRepository.findByEmail(friendDtoList.get(1));
        if(!user1.equals(user2)) {
            List<Friend> friList = friendRepository.findByUser1OrUser2(user1.get(), user1.get());
            for (Friend fri : friList) {
                if (fri.getUser1().equals(user2.get()) || fri.getUser2().equals(user2.get())){
                    wrapperDTO.setSuccess(false);
                    return wrapperDTO;
                }
            }
            friend.setUser1(user1.get());
            friend.setUser2(user2.get());
            friend.setUserType(UserType.YES_FRIEND);
            friendRepository.save(friend);
            wrapperDTO.setSuccess(true);
        } else {
            wrapperDTO.setSuccess(false);
        }

        return wrapperDTO;
    }

    public FriendDto findFriendByEmail(String email) {
        Optional<User> optUser = userRepository.findByEmail(email);
        List<Friend> friList = friendRepository.findByUser1OrUser2(optUser.get(), optUser.get());

        FriendDto friendDto = new FriendDto();
        List<String> userFri = new ArrayList<>();
        Integer count = 0;

        for (Friend fri: friList){
            String friEmail = null;
            if(!fri.getUser1().equals(optUser.get())){
                friEmail = (userRepository.findById(fri.getUser1().getId())).get().getEmail();
                count++;
            }
            if(!fri.getUser2().equals(optUser.get())){
                friEmail = (userRepository.findById(fri.getUser2().getId())).get().getEmail();
                count++;
            }
            userFri.add(friEmail);
        }

        friendDto.setFriends(userFri);
        friendDto.setCount(count);
        friendDto.setSuccess(true);
        return friendDto;
    }

    public FriendDto findCommonFriend(String email1, String email2) {
        FriendDto friendDto = new FriendDto();
        Optional<User> optu1 = userRepository.findByEmail(email1);
        Optional<User> optu2 = userRepository.findByEmail(email2);
        List<Friend> u1FriList = friendRepository.findByUser1OrUser2(optu1.get(), optu1.get());

        u1FriList.stream().distinct().forEach(System.out::println);
        List<Friend> u2FriList = friendRepository.findByUser1OrUser2(optu2.get(), optu2.get());
        List<String> u1Fri = new ArrayList<>();
        List<String> u2Fri = new ArrayList<>();

        for (Friend u1: u1FriList){
            String friEmail = null;
            if(!u1.getUser1().equals(optu1.get())){
                friEmail = (userRepository.findById(u1.getUser1().getId())).get().getEmail();
            }
            if(!u1.getUser2().equals(optu1.get())){
                friEmail = (userRepository.findById(u1.getUser2().getId())).get().getEmail();
            }
            u1Fri.add(friEmail);
        }

        for (Friend u2: u2FriList){
            String friEmail = null;
            if(!u2.getUser1().equals(optu2.get())){
                friEmail = (userRepository.findById(u2.getUser1().getId())).get().getEmail();
            }
            if(!u2.getUser2().equals(optu2.get())){
                friEmail = (userRepository.findById(u2.getUser2().getId())).get().getEmail();
            }
            u2Fri.add(friEmail);
        }
        u1Fri.retainAll(u2Fri);
        friendDto.setFriends(u1Fri);
        friendDto.setSuccess(true);
        friendDto.setCount(u1Fri.size());
        RestWrapperDTO wrapperDTO = new RestWrapperDTO();
        return friendDto;
    }

    public RestWrapperDTO subscribeNoti(SubscribeNotiDto subscribeNotiDto) {
        RestWrapperDTO wrapperDTO = new RestWrapperDTO();
        SubscribeNoti subscribeNoti = new SubscribeNoti();
        Optional<User> requester = userRepository.findByEmail(subscribeNotiDto.getRequester());
        Optional<User> target = userRepository.findByEmail(subscribeNotiDto.getTarget());
        Optional<SubscribeNoti> sNoti = subscribeNotiRepository.findByRequesterAndTarget(requester, target);
        if (sNoti.isPresent()) {
            wrapperDTO.setSuccess(false);
            return wrapperDTO;
        }
        subscribeNoti.setRequester((userRepository.findByEmail(requester.get().getEmail())).get());
        subscribeNoti.setTarget((userRepository.findByEmail(target.get().getEmail())).get());
        subscribeNoti.setIsNotify(true);
        subscribeNotiRepository.save(subscribeNoti);
        wrapperDTO.setSuccess(true);
        return wrapperDTO;
    }

    public RestWrapperDTO blockNoti(SubscribeNotiDto subscribeNotiDto) {
        RestWrapperDTO wrapperDTO = new RestWrapperDTO();
        Optional<User> requester = userRepository.findByEmail(subscribeNotiDto.getRequester());
        Optional<User> target = userRepository.findByEmail(subscribeNotiDto.getTarget());

        Optional<SubscribeNoti> sNoti = subscribeNotiRepository.findByRequesterAndTargetAndIsNotifyTrue(requester, target);
        if (sNoti.isPresent()){
            sNoti.get().setIsNotify(false);
            subscribeNotiRepository.save(sNoti.get());

            List<Friend> friList = friendRepository.findByUser1OrUser2(requester.get(), requester.get());
            if (friList.size() > 0) {
                Integer count = 0;
                for (Friend fri : friList) {
                    if (fri.getUser1().equals(target.get()) || fri.getUser2().equals(target.get())) {
                        count++;
                        friendRepository.findById(fri.getId()).map(friend -> {
                            friend.setUser1(fri.getUser1());
                            friend.setUser2(fri.getUser2());
                            friend.setUserType(UserType.NO_NOTIFY);
                            friendRepository.save(friend);
                            wrapperDTO.setSuccess(true);
                            return wrapperDTO;
                        });
                    }
                }
                if (count == 0){
                    Friend friend = new Friend();
                    friend.setUser1(requester.get());
                    friend.setUser2(target.get());
                    friend.setUserType(UserType.NO_NEW_FRIEND);
                    friendRepository.save(friend);
                }
            }
            wrapperDTO.setSuccess(true);
            return wrapperDTO;
        } else {
            wrapperDTO.setSuccess(false);
            return wrapperDTO;
        }
    }

    public FriendDto getNotifyUser(String sender, String text){

        FriendDto friendDto = new FriendDto();
        Optional<User> optUser = userRepository.findByEmail(sender);

        // Notify User List
        List<SubscribeNoti> subscribeNotiList = subscribeNotiRepository.findByTargetAndIsNotifyTrue(optUser.get());
        List<String> notifyUserList = new ArrayList<>();
        for(SubscribeNoti sn: subscribeNotiList) {
            notifyUserList.add(sn.getRequester().getEmail());
        }

        // friend User List
        List<Friend> friList = friendRepository.findByUser1OrUser2AndUserType(optUser.get(), optUser.get(), UserType.YES_FRIEND);
        List<String> friUserList = new ArrayList<>();
        for (Friend fri: friList){
            String friEmail = null;
            if(!fri.getUser1().equals(optUser.get())){
                friEmail = (userRepository.findById(fri.getUser1().getId())).get().getEmail();
            }
            if(!fri.getUser2().equals(optUser.get())){
                friEmail = (userRepository.findById(fri.getUser2().getId())).get().getEmail();
            }
            friUserList.add(friEmail);
        }

        // Split Spring to word ..
        String str[] = text.split(" ");
        List<String> wordList = new ArrayList<String>();
        wordList = Arrays.asList(str);

        // Test word is mail or not
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);

        // mail list from text
        List<String> emailUserList = new ArrayList<>();
        for(String word: wordList){
            System.out.println(word);
            Matcher matcher = pattern.matcher(word);
            if (matcher.matches()) {
                emailUserList.add(word);
            }
        }

        Set<String> set = new HashSet<String>();
        set.addAll(notifyUserList);
        set.addAll(friUserList);
        set.addAll(emailUserList);

        friendDto.setRecipients(new ArrayList<String>(set));
        friendDto.setSuccess(true);
        return friendDto;
    }

}
