package com.wecyberstage.wecyberstage.view.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewTypeHelper {

    final static Set<ViewType> levelAccount = new HashSet<ViewType>() {
        {
            add(ViewType.SIGN_IN);
            add(ViewType.SIGN_UP);
            add(ViewType.USER_PROFILE);
        }
    };
    final static Set<ViewType> levelBrowse = new HashSet<ViewType>() {
        {
            add(ViewType.BROWSE);
        }
    };
    final static Set<ViewType> levelCompose = new HashSet<ViewType>() {
        {
            add(ViewType.COMPOSE_X);
            add(ViewType.COMPOSE_Y);
            add(ViewType.COMPOSE_Z);
        }
    };
    final static List<Set<ViewType>> levelSet = Arrays.asList(levelAccount,levelBrowse,levelCompose);

    static public boolean isTwoTypesSameLevel(ViewType viewType1, ViewType viewType2) {
        boolean status = false;
        for(Set<ViewType> set: levelSet) {
            if( set.contains(viewType1) || set.contains(viewType2)) {
                if( set.contains(viewType1) && set.contains(viewType2) ) {
                    status = true;
                }
                break;
            }
        }
        return status;
    }
}
