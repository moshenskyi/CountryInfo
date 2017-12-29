package com.example.nazarii_moshenskyi.cityinfo.ui;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.example.nazarii_moshenskyi.cityinfo.CountryInfoApplication;
import com.example.nazarii_moshenskyi.cityinfo.R;
import com.example.nazarii_moshenskyi.cityinfo.data.model.Country;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_country.CountryFragment;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_info.CountryDetailActivity;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_info.CountryDetailFragment;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_info.StubFragment;

import javax.inject.Inject;

import static com.example.nazarii_moshenskyi.cityinfo.ui.Contract.COUNTRY_EXTRA;

public class MainActivity extends AppCompatActivity implements MainView, CountryFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";

    @Inject
    MainPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Application application = getApplication();
        if (application == null) {
            return;
        }
        ((CountryInfoApplication) application).getCountryComponent().inject(this);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        presenter = new MainPresenterImpl();

        presenter.attachView(this);
        presenter.start();

        showCountries();
    }

    private void showCountries() {
        CountryFragment masterFragment = CountryFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.country_name_container, masterFragment)
                .commit();
    }

    public void setDetailFragment() {
        Log.d(TAG, "setDetailFragment: Mode = tablet");
        StubFragment fragment = StubFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.city_name_container, fragment)
                .commit();
    }

    public int getSize() {
        Configuration configuration = getResources().getConfiguration();
        return configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    public void replaceDetailFragment(Country country) {
        Log.d(TAG, "onClick: name=" + country.getName() + "sending to DetailFragment");
        CountryDetailFragment detailFragment = CountryDetailFragment.newInstance(country.getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.city_name_container, detailFragment)
                .commit();
    }

    public void startDetailActivity(Country country) {
        Log.d(TAG, "onClick: name=" + country.getName() + "sending to DetailActivity");
        Intent intent = new Intent(getApplicationContext(), CountryDetailActivity.class);
        intent.putExtra(COUNTRY_EXTRA, country.getName());
        startActivity(intent);
    }

    @Override
    public void onCountryClicked(Country country) {
        presenter.onItemClicked(country);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
