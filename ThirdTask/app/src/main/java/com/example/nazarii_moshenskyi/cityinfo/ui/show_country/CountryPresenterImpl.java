package com.example.nazarii_moshenskyi.cityinfo.ui.show_country;

import com.example.nazarii_moshenskyi.cityinfo.data.model.Country;
import com.example.nazarii_moshenskyi.cityinfo.interactor.repository.DataManager;
import com.example.nazarii_moshenskyi.cityinfo.ui.BasePresenter;
import com.example.nazarii_moshenskyi.cityinfo.util.Filter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CountryPresenterImpl extends BasePresenter<CountryView> implements CountryMvpPresenter {

    private final DataManager manager;
    private Filter itemFilter;

    public CountryPresenterImpl(DataManager manager) {
        this.manager = manager;
    }

    public void getCountries() {
        manager.getCountries().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Country>>() {
                    @Override
                    public void accept(List<Country> countries) throws Exception {
                        getView().onLoad(countries);
                    }
                });
    }

    @Override
    public void updateItems(String input, CountryAdapter adapter) {
        if (itemFilter == null) {
            itemFilter = new Filter(getView().getItems());
        }
        adapter.update(itemFilter.filter(input));
    }
}
