package org.rdiazs.android.myvegs.login;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;

import static org.mockito.Mockito.verify;

/**
 * Prueba el <code>Interactor</code>.
 */
public class LoginInteractorImplTest extends BaseTest {
    @Mock
    private LoginRepository repository;

    private LoginInteractor interactor;

    @Override
    public void setup() throws Exception {
        super.setup();
        interactor=new LoginInteractorImpl(repository);
    }

    @Test
    public void testDoSignUp_repositoryCalled() throws Exception {
        String email="email";
        String password="password";

        interactor.doSignUp(email,password);

        verify(repository).signUp(email,password);

    }

    @Test
    public void testDoSignIn_repositoryCalled() throws Exception {
        String email="email";
        String password="password";

        interactor.doSignIn(email,password);

        verify(repository).signIn(email,password);
    }
}
