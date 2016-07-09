package org.rdiazs.android.myvegs.login;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.login.events.LoginEvent;
import org.rdiazs.android.myvegs.login.ui.LoginView;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Prueba el <code>Presenter</code>.
 */
public class LoginPresenterImplTest extends BaseTest {
    @Mock
    private EventBus eventBus;

    @Mock
    private LoginView view;

    @Mock
    private LoginInteractor interactor;

    @Mock
    LoginEvent event;

    private LoginPresenter presenter;

    @Override
    public void setup() throws Exception {
        super.setup();
        presenter = new LoginPresenterImpl(eventBus, view, interactor);
    }

    @Test
    public void testOnCreate_subscribedToEventBus() throws Exception {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_unsubscribedFromEventBus() throws Exception {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);

        assertNull(presenter.getView());
    }

    @Test
    public void testValidateLogin_executeInteractor() throws Exception {
        String email = "email";
        String password = "password";

        presenter.validateLogin(email, password);

        assertNotNull(presenter.getView());
        verify(view).disableInputs();
        verify(view).showProgress();
        verify(interactor).doSignIn(email, password);
    }

    @Test
    public void testRegisterNewUser_executeInteractor() throws Exception {
        String email = "email";
        String password = "password";

        presenter.registerNewUser(email, password);

        assertNotNull(presenter.getView());
        verify(view).disableInputs();
        verify(view).showProgress();
        verify(interactor).doSignUp(email, password);
    }

    @Test
    public void testOnEvent_hasError() throws Exception {

    }
}
