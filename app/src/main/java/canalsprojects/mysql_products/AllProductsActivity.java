package canalsprojects.mysql_products;

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
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class AllProductsActivity extends ListActivity {

    // Progress Dialog
    //private ProgressDialog pDialog;

    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private CustomArrayAdapter adapter = null;
    private SearchView search;

    // url to get all products list
    private static String url_all_products = "http://bd.mumus.es/get_all_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_START = "start";
    private static final String TAG_END = "end";

    // products JSONArray
    private JSONArray products = null;
    private boolean loadingInfo = false;
    private boolean MoreInfo = true;
    String query = "";
    String query2 = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_products);

        // Get the intent, verify the action and get the query
        /*Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("Query:", query);
            this.query = query;
        }*/

        // Loading products in List view
        new LoadAllProducts(query).execute(0);

        // Get listview
        ListView lv = getListView();

        //enables filtering for the contents of the given ListView
        lv.setTextFilterEnabled(true);

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
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
                    new LoadAllProducts(query).execute(lastItem);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setSubmitButtonEnabled(true);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String q) {
                Log.d("Query",q);
                query = q;
                search.onActionViewCollapsed();
                adapter.clear();
                new LoadAllProducts(q).execute(0);
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
    class LoadAllProducts extends AsyncTask<Integer, String, Integer> {

        String query;
        ArrayList<Product> productsList;
        ListView lv = getListView();
        View loadMoreView;

        public LoadAllProducts(String query) {
            this.query = query;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loadingInfo = true;

            // Hashmap for ListView
            productsList = new ArrayList<Product>();

                //add the footer before adding the adapter, else the footer will not load!
                loadMoreView = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer, null, false);
                lv.addFooterView(loadMoreView);

        }

        /**
         * getting All products from url
         * */
        protected Integer doInBackground(Integer... args) {
            // Building Parameters
            int start = args[0];
            int end = start+10;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_START, args[0].toString()));
            params.add(new BasicNameValuePair(TAG_END, String.valueOf(end)));
            params.add(new BasicNameValuePair("q", query));

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
                    products = json.getJSONArray(TAG_PRODUCTS);

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

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(final Integer lastItem) {
            // dismiss the dialog after getting all products
//            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    if (lastItem == 0) {
                        // Init adapter for ListView
                        adapter = new CustomArrayAdapter(AllProductsActivity.this, R.layout.list_item, productsList);
                        setListAdapter(adapter);
                    } else {
                        adapter.addAll(productsList);
                    }
                }
            });

            lv.removeFooterView(loadMoreView);
            loadingInfo = false;
        }
    }

}
