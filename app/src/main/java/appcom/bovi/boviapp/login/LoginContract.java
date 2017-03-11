package appcom.bovi.boviapp.login;

/**
 * Created by diego on 11/03/17.
 */

import appcom.bovi.boviapp.BasePresenter;
import appcom.bovi.boviapp.BaseView;

/**
 * Interacción MVP en Login
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showProgress(boolean show);

        void setEmailError(String error);

        void setPasswordError(String error);

        void showLoginError(String msg);

        void showPushNotifications();

        void showGooglePlayServicesDialog(int errorCode);

        void showGooglePlayServicesError();

        void showNetworkError();
    }

    interface Presenter extends BasePresenter {
        void attemptLogin(String email, String password);
    }
}