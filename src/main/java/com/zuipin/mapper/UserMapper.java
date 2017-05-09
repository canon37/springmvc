package com.zuipin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.zuipin.entity.User;
import com.zuipin.util.Pagination;

@Mapper
public interface UserMapper{
	//查询单个实体
    public User findById(Long id);
    
    //查询list
    public List<User> findAll();
    
    //关联查询
    public List<User> findWithAddress();
    
    //分页
    public List<User> findByName(@Param("name") String name, @Param("pagination") Pagination pagination);

    //批量插入
    public void insertBatch(List<User> list);
    
    //插入操作
    @Insert("insert user(name) values(#{name})")
    public void insert(User user);    

    //更新操作
    @Update("update user set name=#{name} where id=#{id}")
    public void update(User user);

    //删除操作
    @Delete("delete from user where id=#{id}")
    public void delete(Long id);
}
