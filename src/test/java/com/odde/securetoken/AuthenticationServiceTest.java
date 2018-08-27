package com.odde.securetoken;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AuthenticationServiceTest {

    @Test
    public void is_valid_test() {
        AuthenticationService target = new AuthenticationService(new StubProfile(), new StubToken());

        boolean actual = target.isValid("joey", "91000000");

        assertTrue(actual);
    }

    private class StubProfile implements Profile {
        @Override
        public String getPassword(String account) {
            if (account.equals("joey")) {
                return "91";
            } else {
                throw new UnknownError();
            }
        }
    }

    private class StubToken implements Token {
        @Override
        public String getRandom(String account) {
            return "000000";
        }
    }
}

