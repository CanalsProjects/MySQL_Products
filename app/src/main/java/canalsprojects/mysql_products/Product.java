package canalsprojects.mysql_products;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by knals on 28/08/2014.
 */
public class Product {

    private String pid;
    private String name;
    private String price;
    private String description;
    private Bitmap img;

    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_IMG = "img";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";

    public Product(JSONObject obj) {

        try {
            // Storing each json item in variable
            this.pid = obj.getString(TAG_PID);
            this.name = obj.getString(TAG_NAME);
            this.price = obj.getString(TAG_PRICE);
            this.description = obj.getString(TAG_DESCRIPTION);
            new DownloadImageTask().execute(obj.getString(TAG_IMG));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImg() {
        return img;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            img = result;
        }
    }
}
