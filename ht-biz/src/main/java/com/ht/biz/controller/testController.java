package com.ht.biz.controller;

import com.ht.entity.shiro.SysUser;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * @ApiOperation() 用于方法；表示一个http请求的操作
 * value用于方法描述
 * notes用于提示内容
 * tags可以重新分组（视情况而用）
 * @ApiParam() 用于方法，参数，字段说明；表示对参数的添加元数据（说明或是否必填等）
 * name–参数名
 * value–参数说明
 * required–是否必填
 */
@RestController
@RequestMapping(value="/testController")
@Api(value="testController",description = "接口文档测试controller")
public class testController {


    @ApiOperation(value="获取用户信息",notes="注意问题点")
    @GetMapping("/getUserInfo")
    public SysUser getUserInfo(@ApiParam(name="id",value="用户id",required=true) Long id,@ApiParam(name="username",value="用户名") String username) {
        // userService可忽略，是业务逻辑
    /*    User user = userService.getUserInfo();*/

        return null;
    }

    @ApiOperation("更改用户信息")
    @PostMapping("/updateUserInfo")
    public int updateUserInfo(@ApiParam(name="用户对象",value="传入json格式",required=true) SysUser user){


        return 1;
    }
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @RequestMapping(value="createUser", method=RequestMethod.POST)
    public String postUser(@ApiParam(name="用户对象",value="传入json格式",required=true) SysUser user) {
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public SysUser getUser(@PathVariable Long id) {
        return null;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "SysUser")
    })
    public String putUser(@PathVariable Long id, @RequestBody SysUser user) {
                return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {

        return "success";
    }


}
