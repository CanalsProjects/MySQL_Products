package canalsprojects.mysql_products;

/**
 * Created by knals on 27/08/2014.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<HashMap<String, String>> data;
    private String[] from;
    private int[] to;

    public CustomAdapter(Context context, List<HashMap<String, String>> data,
                         int resource, String[] from, int[] to) {
        super(context, resource);
        this.context = context;
        this.data = data;
        this.resource = resource;
        this.from = from;
        this.to = to;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HashMap<String, String> map = (HashMap) this.data.get(position);

        View rowView = convertView;
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(resource, parent, false);
        }

        TextView pid = (TextView) rowView.findViewById(to[0]);
        pid.setText(map.get(from[0]));

        TextView name = (TextView) rowView.findViewById(to[1]);
        name.setText(map.get(from[1]));

        TextView price = (TextView) rowView.findViewById(to[2]);
        price.setText(map.get(from[2]));

        TextView description = (TextView) rowView.findViewById(to[3]);
        description.setText(map.get(from[3]));

        try{
            ImageView img = (ImageView) rowView.findViewById(to[4]);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(map.get(from[4])).getContent());
            img.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowView;
    }
}

