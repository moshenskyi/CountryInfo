package com.example.nazarii_moshenskyi.cityinfo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nazarii_moshenskyi.cityinfo.R;
import com.example.nazarii_moshenskyi.cityinfo.data.model.Country;
import com.example.nazarii_moshenskyi.cityinfo.interactor.api.ApiFactory;
import com.example.nazarii_moshenskyi.cityinfo.interactor.api.CountryService;
import com.example.nazarii_moshenskyi.cityinfo.interactor.repository.CountryRepository;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_cities.CountryAdapter;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_cities.CountryFragment;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_cities.CountryItemDecorator;
import com.example.nazarii_moshenskyi.cityinfo.ui.show_cities.CountryPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseView<List<Country>>,
        CountryFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private CountryPresenter presenter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CountryAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        checkLayout();

        initList();

        CountryService service = ApiFactory.getCountryService();
        CountryRepository repository = new CountryRepository(service);
        presenter = new CountryPresenter(this, repository);
        presenter.getCountries();
    }

    private void checkLayout() {

    }

    private void initList() {
        recyclerView = findViewById(R.id.country_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CountryItemDecorator((int) getResources().
                getDimension(R.dimen.margins)));
    }

    @Override
    public void onLoad(List<Country> countries) {
        /*adapter = new CountryAdapter(countries, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);*/

    }


    @Override
    public void onCountryClicked(int position) {
        /*Intent intent = new Intent(getApplicationContext(), CountryInfoActivity.class);
        intent.putExtra(COUNTRY_EXTRA, country.getName());
        startActivity(intent);*/
    }
}
