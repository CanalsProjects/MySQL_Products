package canalsprojects.definitions;

import android.graphics.drawable.Drawable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by knals on 28/08/2014.
 */
public class Product {

    private String pid;
    private String name;
    private String price;
    private String description;
    private String linkImg;
    private Drawable img;

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
            this.linkImg = obj.getString(TAG_IMG);
            this.img = null;
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

    public String getLinkImg() {
        return linkImg;
    }

    public Drawable getImg() {
        return img;
    }
    public void setImg(Drawable img) { this.img = img; }

}
