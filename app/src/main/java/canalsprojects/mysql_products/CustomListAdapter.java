package canalsprojects.mysql_products;

/**
 * Created by knals on 27/08/2014.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<HashMap<String, String>> data;
    private int resource;
    private String[] from;
    private int[] to;


    public CustomListAdapter(Activity activity, List<HashMap<String, String>> data,
                             int resource, String[] from, int[] to) {
        this.activity = activity;
        this.resource = resource;
        this.data = data;
        this.from = from;
        this.to = to;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int location) {
        return data.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HashMap<String, String> map = (HashMap) this.data.get(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(resource, null);

        TextView pid = (TextView) convertView.findViewById(to[0]);
        pid.setText(map.get(from[0]));

        TextView name = (TextView) convertView.findViewById(to[1]);
        name.setText(map.get(from[1]));

        TextView price = (TextView) convertView.findViewById(to[2]);
        price.setText(map.get(from[2]));

        TextView description = (TextView) convertView.findViewById(to[3]);
        description.setText(map.get(from[3]));


        ImageView img = (ImageView) convertView.findViewById(to[4]);
        new DownloadImageTask((ImageView) convertView.findViewById(R.id.thumbnail)).execute(map.get(from[4]));

        /*try{
            ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(map.get(from[4])).getContent());
            img.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return convertView;
    }

}
