package com.svalero.comicbookstoresapp.presenter;

import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;

import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.LoginContract;
import com.svalero.comicbookstoresapp.dto.LoginDTO;
import com.svalero.comicbookstoresapp.model.LoginModel;
import com.svalero.comicbookstoresapp.view.LoginView;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.Model.OnLoginListener {
    private LoginView view;
    private LoginModel model;

    public LoginPresenter(LoginView view) {
        this.view = view;
        model = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.showLoginErrorDialog(view.getString(R.string.error_empty_fields));
        } else {
            LoginDTO loginDTO = new LoginDTO(username, password);
            model.login(loginDTO, this);
        }
    }

    @Override
    public void onLoginSuccess(Long id) {
        view.getEditor().putLong(PREFERENCES_ID, id);
        view.getEditor().apply();

        view.showLoginSuccessDialog(view.getString(R.string.login_successful));
    }

    @Override
    public void onLoginError(String message) {
        if (message.isEmpty()) {
            message = view.getString(R.string.unexpected_error);
        }
        view.showLoginErrorDialog(message);
    }
}
