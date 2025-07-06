package com.bci.user.adapters.inbound.mapper;

import com.bci.user.adapters.inbound.api.request.UserPhoneRequest;
import com.bci.user.adapters.inbound.api.request.UserRequest;
import com.bci.user.adapters.inbound.api.response.UserCreatedResponse;
import com.bci.user.adapters.inbound.api.response.UserPhoneResponse;
import com.bci.user.adapters.inbound.api.response.UserResponse;
import com.bci.user.domain.model.User;
import com.bci.user.domain.model.UserPhone;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiMapper {

    User toDomain(UserRequest request, String token);
    UserPhone toDomain(UserPhoneRequest request);

    default UserResponse toResponse(User user){
        return UserResponse.builder()
                .user(toResponseCreated(user))
                .id(user.getUuid())
                .created(user.getCreated())
                .modified(user.getModified())
                .token(user.getToken())
                .lastLogin(user.getLastLogin())
                .isActive(user.getActive())
                .build();
    }

    default UserCreatedResponse toResponseCreated(User user){
        return UserCreatedResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phones(toResponse(user.getPhones()))
                .build();
    }

    default List<UserPhoneResponse> toResponse(List<UserPhone> userPhones){
        if(CollectionUtils.isEmpty(userPhones)) return Collections.emptyList();
        return userPhones.stream()
                .map(userPhone -> UserPhoneResponse.builder()
                        .number(userPhone.getNumber())
                        .cityCode(userPhone.getCityCode())
                        .contryCode(userPhone.getContryCode())
                        .build())
                .toList();
    }
}
