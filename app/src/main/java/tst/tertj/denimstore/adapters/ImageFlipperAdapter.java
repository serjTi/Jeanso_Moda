package tst.tertj.denimstore.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

import tst.tertj.denimstore.R;
import tst.tertj.denimstore.data.Offer;

public class ImageFlipperAdapter extends BaseAdapter {

    Context context;
    Offer currentOffer;
    LayoutInflater layoutInflater;
    private int howMuchPhotos = 0;

    public ImageFlipperAdapter(Context applicationContext, Offer currentOffer) {
        this.context = applicationContext;
        this.currentOffer = currentOffer;
        this.layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        howMuchPhotos = currentOffer.getHowMuchPhotos();
        return howMuchPhotos;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.layout_image_flipper, null);
        ImageView image = (ImageView) convertView.findViewById(R.id.ivImageFlipper);
        TextView tvImageCounter = (TextView) convertView.findViewById(R.id.tvImageCounter);
        if(position == 0){

        }else if(position == 1){
            image.setImageDrawable(Drawable.createFromPath(Environment.getExternalStorageDirectory() + "/Denim Store/" + currentOffer.getName() + "_1.jpg"));
            tvImageCounter.setText("1/" + (howMuchPhotos-1));
        }else{
            image.setImageDrawable(Drawable.createFromPath(Environment.getExternalStorageDirectory() + "/Denim Store/" + currentOffer.getName() + "_" + position + ".jpg"));
            tvImageCounter.setText(position + "/" + (howMuchPhotos-1));
        }
        return convertView;
    }
}
