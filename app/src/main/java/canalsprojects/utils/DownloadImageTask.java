package canalsprojects.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

import canalsprojects.definitions.Product;

/**
 * Created by knals on 27/08/2014.
 */
public class DownloadImageTask extends AsyncTask<Product, Void, Bitmap> {

    private static int MaxTextureSize = 2048; /* True for most devices. */
    public ImageView bmImage;
    private Product product;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    public Bitmap doInBackground(Product... urls) {
        product = urls[0];
        String url = product.getLinkImg();
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    public void onPostExecute(Bitmap result) {
        if(result != null) {
            bmImage.setImageBitmap(result);
            //product.setImg(bmImage.getDrawable());
        }
    }
}