package com.elmergram.services;

import com.elmergram.enums.ExceptionErrorMessage;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.FollowEntity;
import com.elmergram.models.UserEntity;
import com.elmergram.repositories.FollowRepository;
import com.elmergram.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.elmergram.enums.ExceptionErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void followUser(int followerId, String followingUsername){
        UserEntity following = userRepository
                .findByUsernameIgnoreCase(followingUsername);
        if(following==null) throw new UserNotFoundException(USER_NOT_FOUND);
        int followingId = following.getId();
        if(followRepository.existsByFollower_IdAndFollowing_Id(followerId,followingId))
           return;

        followRepository.save(new FollowEntity(followerId,followingId));
    }

    @Transactional
    public void removeFollow(int followerId, String followingUsername){
        UserEntity following = userRepository
                .findByUsernameIgnoreCase(followingUsername);
        if(following==null) throw new UserNotFoundException(USER_NOT_FOUND);
        int followingId = following.getId();
        followRepository.deleteByFollower_IdAndFollowing_Id(followerId, followingId);
    }

    public long followersCount(String username){
        UserEntity user = userRepository
                .findByUsernameIgnoreCase(username);
        if(user==null) throw new UserNotFoundException(USER_NOT_FOUND);
        int userId = user.getId();
        return followRepository.countFollowers(userId);
    }

    public long followingCount(String username){
        UserEntity user = userRepository
                .findByUsernameIgnoreCase(username);
        if(user==null) throw new UserNotFoundException(USER_NOT_FOUND);
        int userId = user.getId();
        return followRepository.countFollowing(userId);
    }
}
