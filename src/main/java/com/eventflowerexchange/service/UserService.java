package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.User;

public interface UserService {
    public User register(User user);
    public User login(User user);
}
