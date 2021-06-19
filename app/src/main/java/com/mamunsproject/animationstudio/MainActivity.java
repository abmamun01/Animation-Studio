package com.mamunsproject.animationstudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.navigation.NavigationView;
import com.mamunsproject.animationstudio.Adapter.SliderAdapter;
import com.mamunsproject.animationstudio.Adapter.VIdeoAdapter;
import com.mamunsproject.animationstudio.Fragment.AnimationFragment;
import com.mamunsproject.animationstudio.Fragment.CartoonCategory;
import com.mamunsproject.animationstudio.Fragment.HomeFragment;
import com.mamunsproject.animationstudio.Fragment.LearningFragment;
import com.mamunsproject.animationstudio.Model.ResponseVideo;
import com.mamunsproject.animationstudio.Model.SliderModel;
import com.mamunsproject.animationstudio.Model.Video;
import com.mamunsproject.animationstudio.Retrofit.ApiClient;
import com.mamunsproject.animationstudio.Retrofit.ApiInterface;
import com.mamunsproject.animationstudio.Utils.MyConsts;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;
    BubbleNavigationConstraintView bubbleNavigationConstraintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerID, new HomeFragment()).commit();


        toolbar = findViewById(R.id.home_toolbar);
        bubbleNavigationConstraintView = findViewById(R.id.top_navigation_constraint);


        checkConnection();
        initNavigationMenu();

        bubbleNavigationConstraintView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                //navigation changed, do something

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


                switch (position) {
                    case 0:
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.fragmentContainerID, new HomeFragment());
                        transaction.commit();
                        break;

                    case 1:
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.fragmentContainerID, new AnimationFragment());
                        transaction.commit();
                        break;

                    case 2:
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.fragmentContainerID, new LearningFragment());
                        transaction.commit();
                        break;

                    case 3:
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.fragmentContainerID, new CartoonCategory());
                        transaction.commit();
                        break;


                }
            }
        });


//===============================================FB BANNER AD============================================


/*
        adView = new AdView(this, "761984221190315_761984364523634", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

*/

//===============================================FB BANNER AD============================================





    }

    public void checkConnection() {

        ConnectivityManager manager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

            }


        } else {


            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection!")
                    .setMessage("You Have To Turn On Your Mobile Data or Wifi!")
                    .setCancelable(false)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation

                            checkConnection();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    //  .setIcon(R.drawable.ic_warn)
                    .show();


        }

    }

    private void initNavigationMenu() {
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();

                if (item.getItemId()==0) {
                    startActivity(new Intent(getApplicationContext(),PlayListPlayer.class));
                }
                drawer.closeDrawers();
                return true;
            }
        });

    }



}