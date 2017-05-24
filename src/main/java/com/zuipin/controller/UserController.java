
package com.zuipin.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.annotations.ApiIgnore;

import com.zuipin.entity.Address;
import com.zuipin.entity.Order;
import com.zuipin.entity.User;
import com.zuipin.framework.annotation.Inject;
import com.zuipin.framework.annotation.Json;
import com.zuipin.framework.annotation.Valid;
import com.zuipin.framework.annotation.Valids;
import com.zuipin.framework.editor.UserEditor;
import com.zuipin.framework.emuns.ErrorCode;
import com.zuipin.framework.result.Result;
import com.zuipin.service.IUserService;
import com.zuipin.util.Pagination;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Resource
	private IUserService	userService;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ApiOperation(value = "根据id查询用户", notes = "查询用户")
	public ModelAndView getUser(@ApiIgnore ModelAndView mv) {
		User user = new User();
		user.setId(1L);
		user.setName("ttest");
		mv.setViewName("/user/user");
		mv.addObject("user", user);
		return mv;
	}
	
	/**以下两种写法与上面这种达到的效果一样
	public String getUser(@RequestParam Long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "/user/user";
	}
	public String getUser(@RequestParam Long id, Map<String, Object> model) {
		model.put("user", userService.findById(id));
		return "/user/user";
	}
	*/
	
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	@ApiOperation(value = "查询用户", notes = "查询用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "order.id", value = "订单id", required = true, dataType = "long", paramType = "query"),
		@ApiImplicitParam(name = "order.code", value = "code", required = true, dataType = "ErrorCode", paramType = "query"),
		@ApiImplicitParam(name = "order.address.city", value = "code", required = true, dataType = "ErrorCode", paramType = "query"),
		@ApiImplicitParam(name = "order.orderNo", value = "订单NO", required = true, dataType = "string", paramType = "query")
	})
	@Json(type=User.class, include="id,name")
	public User getUser(@ApiParam @RequestBody User user, @Inject @ApiIgnore Order order, @RequestParam(required=false) Long id) {
		return user;
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ApiOperation(value = "查询用户", notes = "查询用户")
	@ResponseBody
	public User findById(@RequestParam Long id){
		return userService.findById(id);
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ApiOperation(value = "插入用户", notes = "插入用户")
	@Valids({ @Valid(name = "user.id", maxValue = 100), @Valid(name = "user.name", length = 5),
			@Valid(name = "user.address.city", minLength = 1),
			@Valid(name = "user.address.id", maxValue = 100),
			@Valid(name = "order.id", maxValue = 100),
			@Valid(name = "order.orderNo", minLength = 1),
			@Valid(name = "address.id", maxValue = 5), @Valid(name = "address.city", minLength = 2) })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user.id", value = "用户id", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "user.name", value = "姓名", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "user.address.city", value = "城市", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "user.address.id", value = "地址id", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "order.id", value = "订单id", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "order.orderNo", value = "订单NO", required = true, dataType = "string", paramType = "query"), })
	public User insert(@RequestBody @ApiParam(value = "地址") Address address,
			@Inject @ApiIgnore User user, @Inject @ApiIgnore Order order, @ApiIgnore Model model) {
		userService.insert(user);
		return user;
	}
	
	@RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
	@ApiOperation(value = "插入用户", notes = "插入用户")
	@Valids({ @Valid(name = "user.id", maxValue = 100, minValue=2), 
			@Valid(name = "user.name", length = 5),
			@Valid(name = "user.orderList.id", minValue = 4, required=true),
			@Valid(name = "user.orderList.orderNo", maxValue = 100),
			@Valid(name = "user.orderList.address.id", minValue = 5),
			@Valid(name = "user.orderList.address.city", minLength = 3),
			@Valid(name = "address.id", maxValue = 9), 
			@Valid(name = "address.city", minLength = 2),
			@Valid(name = "order.id", maxValue = 100, minValue=2,required=true), 
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "order.id", value = "订单id", required = true, dataType = "long", paramType = "query"),
			//@ApiImplicitParam(name = "order.code", value = "code", required = true, dataType = "ErrorCode", paramType = "query"),
			//@ApiImplicitParam(name = "order.address.city", value = "code", required = true, dataType = "ErrorCode", paramType = "query"),
			@ApiImplicitParam(name = "order.orderNo", value = "订单NO", required = true, dataType = "string", paramType = "query")
	})
	@Json(type=Order.class, include="id")
	public User insertBatch(@RequestBody User user, @Inject @ApiIgnore Order order) throws Exception {
		//userService.insertBatch(user);
		User a = new User();
		User b = new User();
		a.setName("a");
		b.setName("b");
		userService.insert(a, b);
		return user;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户", notes = "更新用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user.id", value = "用户id", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "user.name", value = "姓名", required = true, dataType = "string", paramType = "query"), })
	public Result<?> update(@Inject @ApiIgnore User user) {
		userService.update(user);
		return new Result<>(ErrorCode.SUCCESS);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ApiOperation(value = "删除用户", notes = "删除用户")
	public Result<?> delete(
			@ApiParam(required = true, name = "id", value = "用户id") @RequestParam Long id) {
		userService.delete(id);
		return new Result<>(ErrorCode.SUCCESS);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ApiOperation(value = "数据传参", notes = "数据传参")
	@ResponseBody
	public Result<?> list(@RequestParam String name, @RequestBody Pagination pagination) {
		List<User> findByName = userService.findByName(name, pagination);
		Result<Object> result = new Result<>(ErrorCode.SUCCESS);
		result.setData(findByName);
		return result;
	}
	
	//@InitBinder
	public void binder(WebDataBinder binder){
		binder.registerCustomEditor(User.class, new UserEditor());
	}
}
