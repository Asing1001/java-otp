package com.odde.securetoken;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class AuthenticationServiceTest {

    Profile stubProfile = mock(Profile.class);
    Token stubToken = mock(Token.class);
    Token.AuthLogger mockAuthLogger = mock(Token.AuthLogger.class);
    AuthenticationService target = new AuthenticationService(stubProfile, stubToken, mockAuthLogger );

    @Test
    public void is_valid_test() {
        givenAccountPassword("joey", "91");
        givenRandomToken("000000");
        shouldBeValid("joey", "91000000");
    }

    @Test
    public void is_invalid_test() {
        givenAccountPassword("joey", "91");
        givenRandomToken("000000");
        shouldBeInValid("joey", "not valid passcode");
    }


    @Test
    public void log_should_be_called_with_account() {
        givenAccountPassword("joey", "91");
        givenRandomToken("000000");
        target.isValid("joey", "not valid passcode");
        shouldLog("joey", "fail");
    }


    @Test
    public void log_should_be_called() {
        givenAccountPassword("joey", "91");
        givenRandomToken("000000");
        target.isValid("joey", "91000000");
        shouldNotLog();
    }

    private void shouldNotLog() {
        verify(mockAuthLogger, never()).save(any());
    }

    private void shouldLog(String... messages) {
        ArgumentCaptor<String> captor = forClass(String.class);
        verify(mockAuthLogger).save(captor.capture());
        assertThat(captor.getValue()).contains(messages);
    }

    private void shouldBeInValid(String account, String passcode) {
        assertFalse(target.isValid(account, passcode));
    }

    private void shouldBeValid(String account, String passCode) {
        assertTrue(target.isValid(account, passCode));
    }


    private void givenRandomToken(String token) {
        when(stubToken.getRandom(anyString())).thenReturn("000000");
    }

    private void givenAccountPassword(String account, String password) {
        when(stubProfile.getPassword(account)).thenReturn(password);
    }

}
