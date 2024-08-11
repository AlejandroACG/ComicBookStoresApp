package com.svalero.comicbookstoresapp.contract;

import com.svalero.comicbookstoresapp.dto.LoginDTO;

public interface LoginContract {
    interface Model {
        interface OnLoginListener {
            void onLoginSuccess(Long id);
            void onLoginError(String message);
        }
        void login(LoginDTO loginDTO, OnLoginListener onLoginListener);
    }

    interface View {
        void showLoginSuccessDialog(String message);
        void showLoginErrorDialog(String message);
    }

    interface Presenter {
        void login(String username, String password);
    }
}
