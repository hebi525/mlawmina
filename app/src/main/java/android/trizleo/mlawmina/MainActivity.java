package android.trizleo.mlawmina;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.ActionMenuItem;
import android.trizleo.mlawmina.fragments.CaseMgmtFragment;
import android.trizleo.mlawmina.fragments.DashboardFragment;
import android.trizleo.mlawmina.models.Card;
import android.trizleo.mlawmina.models.Case;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static UserSession currentUserSession = null;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        createUserSession();
    }

    public void createUserSession(){

        Intent intent = getIntent();
        String intentUsername = intent.getStringExtra("USERNAME");
        String intentPassword = intent.getStringExtra("PASSWORD");
        ArrayList<Case> intentCaseList = (ArrayList<Case>) intent.getSerializableExtra("CASE_LIST");

        currentUserSession = new UserSession(this, intentUsername, intentPassword);
        currentUserSession.updateUserCaseList(intentCaseList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            if(!(currentFragment instanceof DashboardFragment)) {
                currentFragment = DashboardFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, 0);
                transaction.replace(R.id.main_fragment_container, currentFragment);
                transaction.commit();
            }
        } else if (id == R.id.nav_case) {
            if(!(currentFragment instanceof CaseMgmtFragment)) {
                currentFragment = CaseMgmtFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, 0);
                transaction.replace(R.id.main_fragment_container, currentFragment);
                transaction.commit();
            }
        } else if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_document) {

        } else if (id == R.id.nav_task) {

        } else if (id == R.id.nav_clients) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
