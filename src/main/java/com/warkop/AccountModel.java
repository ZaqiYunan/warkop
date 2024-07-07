package com.warkop;

public class AccountModel {
    private static AccountModel model;
    private final SessionManager sessionManager;
    private final UserAcount accUser;

    private AccountModel() {
        this.sessionManager = new SessionManager();
        this.accUser = new UserAcount();
    }

    public static synchronized AccountModel getInstance() {
        if (model == null) {
            model = new AccountModel();
        }
        return model;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public UserAcount getAccount() {
        return accUser;
    }

}
