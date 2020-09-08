package com.myzy.patient.core;

import com.myzy.patient.core.entity.TokenEntity;

/**
 * 用户信息上下文
 *
 * @author leekejin
 */
public class UserContext {

    private static ThreadLocal<TokenEntity> threadLocal = new ThreadLocal<>();

    public static TokenEntity getUser() {
        return threadLocal.get();
    }

    public static void setUser(TokenEntity entity) {
        threadLocal.set(entity);
    }

    public static void removeUser() {
        threadLocal.remove();
    }

}
