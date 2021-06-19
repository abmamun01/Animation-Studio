package com.mamunsproject.animationstudio;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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
                        transaction.replace(R.id.fragmentContainerID, new HomeFragment());
                        transaction.commit();
                        break;

                    case 1:
                        transaction.replace(R.id.fragmentContainerID, new AnimationFragment());
                        transaction.commit();
                        break;

                    case 2:
                        transaction.replace(R.id.fragmentContainerID, new LearningFragment());
                        transaction.commit();
                        break;

                    case 3:
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


//        bottomNavigationView = findViewById(R.id.bottomBar);
//
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerID,new Home_Fragment()).commit();


//        bottomNavigationView.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public boolean onItemSelect(int i) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//                switch (i) {
//
//
//                    case 0:
//                        transaction.replace(R.id.fragmentContainerID, new Home_Fragment());
//                        transaction.commit();
//                        break;
//
//
//                    case 1:
//                        transaction.replace(R.id.fragmentContainerID, new PlayList_Fragment());
//                        transaction.commit();
//                        break;
//
//
//                }
//
//                return false;
//
//            }
//
//        });


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
                updateCounter(nav_view);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        //drawer.openDrawer(GravityCompat.START);
        updateCounter(nav_view);
    }

    private void updateCounter(NavigationView nav) {
        Menu m = nav.getMenu();
        ((TextView) m.findItem(R.id.nav_all_inbox).getActionView().findViewById(R.id.text)).setText("75");
        ((TextView) m.findItem(R.id.nav_inbox).getActionView().findViewById(R.id.text)).setText("68");

        TextView badgePrioInbx = (TextView) m.findItem(R.id.nav_priority_inbox).getActionView().findViewById(R.id.text);
        badgePrioInbx.setText("3 new");
        badgePrioInbx.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        TextView badgeSocial = (TextView) m.findItem(R.id.nav_social).getActionView().findViewById(R.id.text);
        badgeSocial.setText("51 new");
        badgeSocial.setBackgroundColor(getResources().getColor(R.color.green_500));

        ((TextView) m.findItem(R.id.nav_spam).getActionView().findViewById(R.id.text)).setText("13");
    }
}