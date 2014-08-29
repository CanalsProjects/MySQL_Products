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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AllProductsActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    CustomArrayAdapter adapter;

    // url to get all products list
    private static String url_all_products = "http://bd.mumus.es/get_all_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_START = "start";
    private static final String TAG_END = "end";

    // products JSONArray
    JSONArray products = null;
    boolean loadingInfo = false;
    boolean MoreInfo = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_products);

        // Loading products in Background Thread
        new LoadAllProducts().execute(0);

        // Get listview
        ListView lv = getListView();

        //add the footer before adding the adapter, else the footer will not load!
        //View footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        //lv.addFooterView(footerView);

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
                if ((lastItem == totalItemCount) && (totalItemCount!=0) && !loadingInfo && MoreInfo) {
                    Log.d(String.valueOf(lastItem), "Last Item");
                    new LoadAllProducts().execute(lastItem);
                }
            }
        });

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

        ArrayList<Product> productsList;
        View footer = getLayoutInflater().inflate(R.layout.list_footer, null);
        ListView listView = getListView();

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loadingInfo = true;
            // Hashmap for ListView
            productsList = new ArrayList<Product>();

//            pDialog = new ProgressDialog(AllProductsActivity.this);
//            pDialog.setMessage("Loading products. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
            listView.addFooterView(footer);
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
            // Simulo tiempo de carga de datos de 3 segundos
            try {
                Thread.sleep(3000);
            }
            catch (Exception e) {
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
                        listView.removeFooterView(footer);
                        adapter = new CustomArrayAdapter(AllProductsActivity.this, R.layout.list_item, productsList);
                        setListAdapter(adapter);
                    } else {
                        listView.removeFooterView(footer);
                        adapter.addAll(productsList);
                    }
                }
            });

            loadingInfo = false;

        }
    }
}
