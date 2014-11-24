package com.dualion.view;

/**
 * Created by lluis on 26/08/2014.
 */

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dualion.adapter.SearchActivityAdapter;
import com.dualion.model.Product;
import com.dualion.model.Sort;
import com.dualion.model.TypeSort;
import com.dualion.webserver.JSONParser;

public class SearchActivity extends ListActivity {

    private JSONParser jParser;
    private SearchActivityAdapter searchActivityAdapter;
    private SearchView searchView;

    // url to get search products list
    private static final String url_all_products = "http://bd.mumus.es/get_all_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_START = "start";
    private static final String TAG_END = "end";
    private static final String TAG_QUERY = "q";
    private static final String TAG_SORT = "sort";
    private static final String TAG_TSORT = "tsort";

    private boolean loadingInfo;
    private boolean MoreInfo;
    private String query;
    private Sort sort;
    private TypeSort typeSort;

    public SearchActivity() {
        jParser = new JSONParser();
        searchActivityAdapter = null;
        sort = Sort.Price;
        typeSort = TypeSort.Asc;
        query = "";
        MoreInfo = true;
        loadingInfo = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_products);

        restoreExtras();
        restoreSaveData(savedInstanceState);
        setupSpinners();
        setupListView();

    }

    private void restoreExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            query = extras.getString("SearchText");
            getIntent().getExtras().clear();
        }
    }

    private void restoreSaveData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            query = savedInstanceState.getString("query");
            sort = Sort.valueOf(savedInstanceState.getString("sort"));
            typeSort = TypeSort.valueOf(savedInstanceState.getString("typeSort"));
            savedInstanceState.clear();
        }
    }

    private void setupListView() {

        // Loading products in List view
        //new LoadSearchedProducts(query).execute(0, sort.ordinal(), typeSort.ordinal());

        // Get listview
        ListView lv = getListView();

        //enables filtering for the contents of the given ListView
        lv.setTextFilterEnabled(true);

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        EditProductActivity.class);

                // sending pid to next activity
                in.putExtra("pid", pid);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastItem = firstVisibleItem + visibleItemCount;
                if ((lastItem == totalItemCount) && (totalItemCount != 0) && !loadingInfo && MoreInfo) {
                    //new LoadSearchedProducts(query).execute(lastItem, sort.ordinal(), typeSort.ordinal());
                }
            }
        });

    }

    private void setupSpinners() {

        String[] sortList = getResources().getStringArray(R.array.sort_list);
        String[] typeSortList = getResources().getStringArray(R.array.type_sort_list);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sortList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort = Sort.values()[position];
                if (searchActivityAdapter != null) {
                    searchActivityAdapter.clear();
                    MoreInfo = true;
                    //new LoadSearchedProducts(query).execute(0, sort.ordinal(), typeSort.ordinal());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeSortList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSort = TypeSort.values()[position];
                if (searchActivityAdapter != null) {
                    searchActivityAdapter.clear();
                    MoreInfo = true;
                    //new LoadSearchedProducts(query).execute(0, sort.ordinal(), typeSort.ordinal());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle stateActivity) {
        super.onSaveInstanceState(stateActivity);
        stateActivity.putString("query", query);
        stateActivity.putString("sort", sort.toString());
        stateActivity.putString("typeSort", typeSort.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String q) {
                Log.d("Query", q);
                query = q;
                searchView.onActionViewCollapsed();
                searchActivityAdapter.clear();
                MoreInfo = true;
                //new LoadSearchedProducts(q).execute(0, sort.ordinal(), typeSort.ordinal());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
        return true;
    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    /*private class LoadSearchedProducts extends AsyncTask<Integer, String, Integer> {

        String query;
        ArrayList<Product> productsList;
        ListView lv = getListView();
        View loadMoreView;

        public LoadSearchedProducts(String query) {
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loadingInfo = true;

            // Hashmap for ListView
            productsList = new ArrayList<Product>();

            //add the footer before adding the adapter, else the footer will not load!
            loadMoreView = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer, null);
            lv.addFooterView(loadMoreView);
        }

        *//**
         * getting All products from url
         * *//*
        protected Integer doInBackground(Integer... args) {
            // Building Parameters
            int start = args[0];
            int end = start + 10;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_START, args[0].toString()));
            params.add(new BasicNameValuePair(TAG_END, String.valueOf(end)));
            params.add(new BasicNameValuePair(TAG_QUERY, query));
            params.add(new BasicNameValuePair(TAG_SORT, args[1].toString()));
            params.add(new BasicNameValuePair(TAG_TSORT, args[2].toString()));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    JSONArray products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                        Product product = new Product(c);
                        productsList.add(product);
                    }
                } else {
                    MoreInfo = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                MoreInfo = false;
            }

            return start;
        }

        *//**
         * After completing background task Dismiss the progress dialog
         * **//*
        protected void onPostExecute(final Integer lastItem) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    if (lastItem == 0) {
                        // Init adapter for ListView
                        searchActivityAdapter = new SearchActivityAdapter(SearchActivity.this, R.layout.list_item, productsList);
                        setListAdapter(searchActivityAdapter);
                    } else {
                        searchActivityAdapter.addAll(productsList);
                    }
                }
            });
            lv.removeFooterView(loadMoreView);
            loadingInfo = false;
        }
    }*/

}