/**
 * @Date:Tue Sep 25 01:36:09 CST 2018
 * @Description:由generator自动生成
 */
package com.mybatisgenerator.model;

import com.mybatisgenerator.base.BaseModel;
import java.util.Date;
import lombok.Data;

/**
 @Table:用户
 @Date:Tue Sep 25 01:36:09 CST 2018
 */
@Data
public class User extends BaseModel {
    /**
     @Description:用户名
    */
    private String username;

    /**
     @Description:密码
    */
    private String password;

    /**
     @Description:盐值
    */
    private String salt;

    /**
     @Description:电话号码
    */
    private String phonenumber;

    /**
     @Description:邮箱
    */
    private String email;

    /**
     @Description:最后登录ip地址
    */
    private String lastip;

    private Integer groupid;

    private Integer blogid;

    private Integer roleid;

    private Integer isenabled;

    private Integer isdeleted;

    private Date createtime;
}