package com.example.dingliu.creatorcodetfue;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SearchView searchView;
    private RecyclerView searchIngredientsList;
    private SearchIngredientsAdapter searchIngredientsAdapter;
    private TextView basketText;
    private RecyclerView basketList;
    private IngredientsAdapter ingredientsAdapter;
    private TextView recipeText;
    private RecyclerView recipeList;
    private RecipeAdapter recipeAdapter;
    private FirebaseFirestore database;
    private Task<QuerySnapshot> ingredients;
    private Task<QuerySnapshot> recipes;

    private ArrayList<Ingredient> myIngredients;
    private ArrayList<Recipe> myRecipes;
    private ArrayList<Ingredient> mySearchedIngredients;

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

        myIngredients = new ArrayList<>();
        mySearchedIngredients = new ArrayList<>();
        myRecipes = new ArrayList<>();

        // set search list and make it gone
        searchIngredientsList = findViewById(R.id.search_list);
        searchIngredientsAdapter = new SearchIngredientsAdapter(this, mySearchedIngredients);
        searchIngredientsList.setAdapter(searchIngredientsAdapter);
        searchIngredientsList.setLayoutManager(new LinearLayoutManager(this));
        searchIngredientsList.setVisibility(View.GONE);

        basketText = findViewById(R.id.basket_text);
        basketList = findViewById(R.id.basket_list);
        ingredientsAdapter = new IngredientsAdapter(this, myIngredients);
        basketList.setAdapter(ingredientsAdapter);
        basketList.setLayoutManager(new LinearLayoutManager(this));

        recipeText = findViewById(R.id.recipe_text);
        recipeList = findViewById(R.id.recipe_list);
        recipeAdapter = new RecipeAdapter(this, myRecipes);
        recipeList.setAdapter(recipeAdapter);
        recipeList.setLayoutManager(new LinearLayoutManager(this));


        // retrieves an instance of the firebase database
        FirebaseApp.initializeApp(this);
        database = FirebaseFirestore.getInstance();

        // collections from the firebase
        ingredients = database.collection("ingredients").get();
        recipes = database.collection("recipe").get();

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
                mySearchedIngredients.add(new Ingredient(query));
                searchIngredientsAdapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    originalView();
                }
                else {
                    searchView();

                    ingredients.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty()){
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot d : list){
                                    Ingredient i = d.toObject(Ingredient.class);
                                    myIngredients.add(i);
                                }

                                Toast.makeText(getApplicationContext(),myIngredients.size(),Toast.LENGTH_SHORT);

                                ingredientsAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }
                return true;
            }
        });

        // hamburger menu actions
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
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


        // hard coded ingredients list
        myIngredients.add(new Ingredient("- chicken"));
        myIngredients.add(new Ingredient("- beef"));
        myIngredients.add(new Ingredient("- pork"));

        ingredientsAdapter.notifyDataSetChanged();

        // hard coded recipes list
        myRecipes.add(new Recipe(new ArrayList<Ingredient>(),"- chicken pot pie"));
        myRecipes.add(new Recipe(new ArrayList<Ingredient>(),"- spaghetti"));
        myRecipes.add(new Recipe(new ArrayList<Ingredient>(),"- seasoned pork chops"));

        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                hideKeyboard(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void originalView(){
        searchIngredientsList.setVisibility(View.GONE);
        basketText.setVisibility(View.VISIBLE);
        basketList.setVisibility(View.VISIBLE);
        recipeText.setVisibility(View.VISIBLE);
        recipeList.setVisibility(View.VISIBLE);
    }

    public void searchView(){
        searchIngredientsList.setVisibility(View.VISIBLE);
        basketText.setVisibility(View.GONE);
        basketList.setVisibility(View.GONE);
        recipeText.setVisibility(View.GONE);
        recipeList.setVisibility(View.GONE);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
