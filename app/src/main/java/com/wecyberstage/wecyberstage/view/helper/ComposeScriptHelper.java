package com.wecyberstage.wecyberstage.view.helper;

import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.Role;

import java.util.List;

public interface ComposeScriptHelper {
    List<Mask> getMaskList(); // return current scene used masks
    Mask getMask(long maskId);
    Mask getMaskByRole(long roleId);
    List<Role> getRoleList(); // all roles in a play
}
