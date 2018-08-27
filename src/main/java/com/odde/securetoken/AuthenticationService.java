package com.odde.securetoken;

public class AuthenticationService {

    private Profile profileDao;
    private Token rsaToken;

    public AuthenticationService(){
        this.profileDao = new ProfileDao();
        this.rsaToken = new RsaTokenDao();
    }


    public AuthenticationService(Profile profile, Token token){
        this.profileDao = profile;
        this.rsaToken = token;
    }

    public boolean isValid(String account, String password) {
        // 根據 account 取得自訂密碼
        String passwordFromDao = profileDao.getPassword(account);

        // 根據 account 取得 RSA token 目前的亂數
        String randomCode = rsaToken.getRandom(account);

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
