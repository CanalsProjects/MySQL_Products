package canalsprojects.mysql_products;

/**
 * Created by knals on 27/08/2014.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;
    private int resource;

    // View lookup cache
    private static class ViewHolder {
        TextView pid;
        TextView name;
        TextView price;
        TextView description;
        ImageView img;
    }

    public CustomArrayAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = (Product) this.getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (inflater == null)
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.pid = (TextView) convertView.findViewById(R.id.pid);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.pid.setText(product.getPid());
        viewHolder.name.setText(product.getName());
        viewHolder.price.setText(product.getPrice());
        viewHolder.description.setText(product.getDescription());

        if (product.getImg() != null){
            viewHolder.img.setImageDrawable(product.getImg());
        } else {
            new DownloadImageTask(viewHolder.img).execute(product.getLinkImg());
            product.setImg(viewHolder.img.getDrawable());
        }


        // Return the completed view to render on screen
        return convertView;
    }

}
