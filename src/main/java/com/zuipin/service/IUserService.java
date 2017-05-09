package com.zuipin.service;

import java.util.List;

import com.zuipin.entity.User;
import com.zuipin.util.Pagination;

public interface IUserService {
    public User findById(Long id);

    public void insert(User user);
    
    public void insert(User user, User u) throws Exception;

    public void update(User user);

    public void delete(Long id);
    
    public List<User> findMap();
    
    public List<User> findWithAddress();
    
    public List<User> findByName(String name, Pagination pagination);
    
    public void insertBatch(List<User> list);
}
