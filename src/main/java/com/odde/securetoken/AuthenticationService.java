package com.odde.securetoken;

public class AuthenticationService {

    private Profile profile;
    private Token token;

    public AuthenticationService() {
        profile = new ProfileImpl();
        token = new RsaTokenImpl();
    }

    public AuthenticationService(Profile profile, Token token) {
        this.profile = profile;
        this.token = token;
    }

    public boolean isValid(String account, String password) {
        // 根據 account 取得自訂密碼
        String passwordFromDao = profile.getPassword(account);

        // 根據 account 取得 RSA token 目前的亂數
        String randomCode = token.getRandom(account);

        // 驗證傳入的 password 是否等於自訂密碼 + RSA token亂數
        String validPassword = passwordFromDao + randomCode;
        boolean isValid = password.equals(validPassword);

        if (isValid) {
            return true;
        } else {
            return false;
        }
    }
}
