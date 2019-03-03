package com.example.dingliu.creatorcodetfue;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SearchView searchView;
    private ListView listView;
    private TextView basketText;
    private RecyclerView basketList;
    private TextView recipeText;
    private RecyclerView recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action Bar and hamburger menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

        // set search list and make it gone
        listView = findViewById(R.id.search_list);
        listView.setVisibility(View.GONE);

        basketText = findViewById(R.id.basket_text);
        basketList = findViewById(R.id.basket_list);
        recipeText = findViewById(R.id.recipe_text);
        recipeList = findViewById(R.id.recipe_list);



        // search function
        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.onActionViewExpanded();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                searchView.clearFocus();
            }
        }, 300);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    listView.setVisibility(View.GONE);
                    basketText.setVisibility(View.VISIBLE);
                    basketList.setVisibility(View.VISIBLE);
                    recipeText.setVisibility(View.VISIBLE);
                    recipeList.setVisibility(View.VISIBLE);
                }
                else {
                    listView.setVisibility(View.VISIBLE);
                    basketText.setVisibility(View.GONE);
                    basketList.setVisibility(View.GONE);
                    recipeText.setVisibility(View.GONE);
                    recipeList.setVisibility(View.GONE);
                }
                Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();


                return true;
            }
        });



        // hamburger menu actions
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
