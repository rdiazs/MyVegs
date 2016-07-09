package org.rdiazs.android.myvegs.vegList.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.rdiazs.android.myvegs.MyVegsApp;
import org.rdiazs.android.myvegs.R;
import org.rdiazs.android.myvegs.addveg.ui.AddVegActivity;
import org.rdiazs.android.myvegs.dialogs.AboutDialogFragment;
import org.rdiazs.android.myvegs.dialogs.DeleteVegDialogFragment;
import org.rdiazs.android.myvegs.dialogs.LogoutDialogFragment;
import org.rdiazs.android.myvegs.dialogs.VegDetailDialogFragment;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.login.ui.LoginActivity;
import org.rdiazs.android.myvegs.vegList.mvp.VegListPresenter;
import org.rdiazs.android.myvegs.vegList.ui.adapters.OnItemClickListener;
import org.rdiazs.android.myvegs.vegList.ui.adapters.VegListAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VegListActivity extends AppCompatActivity implements VegListView, OnItemClickListener {
    @Bind(R.id.container)
    CoordinatorLayout container;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewVegs)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Inject
    VegListPresenter presenter;

    @Inject
    VegListAdapter adapter;

    private MyVegsApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg_list);
        ButterKnife.bind(this);

        app = (MyVegsApp) getApplication();
        setupInjection();
        setupToolbar();

        setupRecyclerView();

        presenter.onCreate();
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    private void setupToolbar() {
        toolbar.setTitle(
                String.format(getString(R.string.veglist_title), presenter.getAuthEmail()));
        setSupportActionBar(toolbar);
    }

    private void setupInjection() {
        app.getVegListComponent(this, this, this).inject(this);

        //adapter = new VegListAdapter(this, new ArrayList<Veg>(), null, this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_veglist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            FragmentManager fm = getSupportFragmentManager();
            LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment();
            logoutDialogFragment.show(fm, "logout_fragment");
        }

        if (id == R.id.action_about) {
            FragmentManager fm = getSupportFragmentManager();
            AboutDialogFragment aboutDialogFragment = new AboutDialogFragment();
            aboutDialogFragment.show(fm, "about_fragment");
        }

        if (id == R.id.action_sort_by_name) {
            adapter.sortVegsByName();
        }

        if (id == R.id.action_sort_by_germination) {
            adapter.sortVegsByGerminationTime();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void addVeg() {
        Intent intent = new Intent(this, AddVegActivity.class);
        startActivity(intent);
    }

    @Override
    public void onVegAdded(Veg veg) {
        adapter.add(veg);
    }

    @Override
    public void onVegChanged(Veg veg) {
//        adapter.update(veg);
    }

    @Override
    public void onVegRemoved(Veg veg) {
        adapter.remove(veg);
    }

    @Override
    public void onVegError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Veg veg) {
        FragmentManager fm = getSupportFragmentManager();
        VegDetailDialogFragment vegDetailDialogFragment =
                VegDetailDialogFragment.newInstance(veg, adapter.getImageLoader());
        vegDetailDialogFragment.show(fm, "veg_detail_fragment");
    }

    @Override
    public void onItemLongClick(Veg veg) {
        FragmentManager fm = getSupportFragmentManager();
        DeleteVegDialogFragment deleteVegDialogFragment =
                DeleteVegDialogFragment.newInstance(veg);
        deleteVegDialogFragment.show(fm, "delete_veg_fragment");
    }

    public void onUserSelectLogout(boolean value) {
        if (value) {
            logout();
        }
    }

    public void onUserDeleteVeg(Veg veg) {
        presenter.removeVeg(veg);
    }

    private void logout() {
        presenter.logout();

        Intent intent = new Intent(getApplicationContext(), LoginActivity
                .class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
