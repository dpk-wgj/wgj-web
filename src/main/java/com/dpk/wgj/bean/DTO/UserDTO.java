package com.dpk.wgj.bean.DTO;

import com.dpk.wgj.bean.DriverInfo;
import com.dpk.wgj.bean.Passenger;
import com.dpk.wgj.bean.UserGroup;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhoulin on 2018/7/9.
 */
public class UserDTO implements Serializable {

    private int userId;

    private String username;

    private DriverInfo driverInfo;

    private Passenger passenger;

    private List<String> roles;

    private UserGroup userGroup;

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }


}
