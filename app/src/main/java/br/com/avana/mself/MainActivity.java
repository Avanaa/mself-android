package br.com.avana.mself;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import br.com.avana.mself.fragment.CardapioFragment;
import br.com.avana.mself.model.ItemModel;
import com.crashlytics.android.Crashlytics;

import br.com.avana.mself.model.ItemPedidoModel;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        //testCrash();

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, new CardapioFragment()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
    }

    private void testCrash() {
        throw new RuntimeException("Test crash report");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent;
        switch (item.getItemId()){

            case R.id.nav_cart:
                intent = new Intent(this, CarrinhoActivity.class);
                intent.putExtra("carrinho", true);
                startActivity(intent);
                break;

            case R.id.nav_hist:
                intent = new Intent(this, CarrinhoActivity.class);
                intent.putExtra("carrinho", false);
                startActivity(intent);
                break;

            case R.id.nav_executivos:
                break;

            case R.id.nav_peixes:
                break;

            case R.id.nav_carnes:
                break;

            case R.id.nav_saladas:
                break;

            case R.id.nav_sanduiches:
                break;

            case R.id.nav_petiscos:
                break;

            case R.id.nav_sobremesas:
                break;

            case R.id.nav_refrigerantes:
                break;

            case R.id.nav_cervejas:
                break;

            case R.id.nav_vinhos:
                break;

            case R.id.nav_doses:
                break;
        }
        drawer.closeDrawer(Gravity.START);
        return true;
    }
}
