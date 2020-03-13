package com.san.test.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author sanwailwin on ၁၃-၀၃-၂၀
 */
@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        User usr = new User();
        usr.setEmail(user.getEmail());
        return userRepository.save(usr);
    }

    public RestWrapperDTO saveFriend(FriendDto friendDto) {
        RestWrapperDTO wrapperDTO = new RestWrapperDTO();
        List<String> friendDtoList = friendDto.getFriends();
        Friend friend = new Friend();
        Optional<User> user1 = userRepository.findByEmail(friendDtoList.get(0));
        Optional<User> user2 = userRepository.findByEmail(friendDtoList.get(1));
        List<Friend> friList = friendRepository.findByUser1OrUser2(user1.get(), user1.get());
        for (Friend fri : friList) {
            if (fri.getUser1().equals(user2.get()) || fri.getUser2().equals(user2.get())){
                wrapperDTO.setSuccess(false);
                return wrapperDTO;
            }
        }
        friend.setUser1(user1.get());
        friend.setUser2(user2.get());
        friendRepository.save(friend);
        wrapperDTO.setSuccess(true);
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

        wrapperDTO.setSuccess(true);
        return wrapperDTO;
    }
}
