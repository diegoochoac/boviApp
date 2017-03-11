package appcom.bovi.boviapp.notifications;

//import com.herprogramacion.carinsurance.data.PushNotification;

import java.util.ArrayList;

import appcom.bovi.boviapp.BasePresenter;
import appcom.bovi.boviapp.BaseView;
import appcom.bovi.boviapp.data.PushNotification;

/**
 * Interacción MVP en Notificaciones
 */
public interface PushNotificationContract {

    interface View extends BaseView<Presenter> {

        void showNotifications(ArrayList<PushNotification> notifications);

        void showEmptyState(boolean empty);

        void popPushNotification(PushNotification pushMessage);
    }

    interface Presenter extends BasePresenter {

        void registerAppClient();

        void loadNotifications();

        void savePushMessage(String title, String description,
                             String expiryDate, String discount);
    }
}
