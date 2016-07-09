package org.rdiazs.android.myvegs.vegList.mvp;

/**
 * Gestiona la sesión del usuario.
 */
public class VegListSessionInteractorImpl implements VegListSessionInteractor {
    private VegListRepository repository;

    public VegListSessionInteractorImpl(VegListRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getAuthEmail() {
        return repository.getAuthMail();
    }

    @Override
    public void logout() {
        repository.logout();
    }
}
