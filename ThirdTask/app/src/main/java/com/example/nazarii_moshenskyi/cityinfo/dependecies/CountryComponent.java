package com.example.nazarii_moshenskyi.cityinfo.dependecies;

import com.example.nazarii_moshenskyi.cityinfo.ui.MainActivity;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_country.CountryFragment;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_info.CountryDetailFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PresentersModule.class, NetModule.class, ApiModule.class} )
public interface CountryComponent {

    void inject(CountryFragment countryFragment);

    void inject(CountryDetailFragment countryDetailFragment);

    void inject(MainActivity mainActivity);
}
