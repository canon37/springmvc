package com.zuipin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zuipin.entity.User;
import com.zuipin.mapper.UserMapper;
import com.zuipin.service.IUserService;
import com.zuipin.util.Pagination;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    public User findById(Long id) {
    	return userMapper.findById(id);
    }

    public void insert(User user) {
    	userMapper.insert(user);
    }

    public void update(User user) {
    	userMapper.update(user);
    }

    public void delete(Long id) {
    	userMapper.delete(id);
    }

	public List<User> findMap() {
		return userMapper.findAll();
	}
	
	public List<User> findWithAddress(){
		return userMapper.findWithAddress();
	}
    @Transactional(rollbackFor=Throwable.class)
    public List<User> findByName(String name, Pagination pagination){
    	return userMapper.findByName(name, pagination);
    }
    
    public void insertBatch(List<User> list){
    	userMapper.insertBatch(list);
    }

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void insert(User user, User u) throws Exception {
		userMapper.insert(user);
		userMapper.insert(u);
	}
}
