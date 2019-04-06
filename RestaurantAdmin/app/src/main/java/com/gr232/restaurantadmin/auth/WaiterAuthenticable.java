package com.gr232.restaurantadmin.auth;

import com.gr232.restaurantadmin.models.*;

public class WaiterAuthenticable implements Authenticable{

    private Waiter waiter;

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getHashPassword() {
        return null;
    }
}
