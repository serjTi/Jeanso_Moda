package tst.tertj.denimstore.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;

import tst.tertj.denimstore.R;
import tst.tertj.denimstore.data.Offer;

/**
 * Created by Натулик on 13.04.2017.
 */

public class ImageFlipperAdapter extends BaseAdapter {

    Context context;
    Offer currentOffer;
    LayoutInflater layoutInflater;
    private int currentImg = 1;

    public ImageFlipperAdapter(Context applicationContext, Offer currentOffer) {
        this.context = applicationContext;
        this.currentOffer = currentOffer;
        this.layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return 0;
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
        currentImg ++;
        if(currentImg > currentOffer.getHowMuchPhotos())
            currentImg = 1;
        convertView = layoutInflater.inflate(R.layout.layout_image_flipper, null);
        ImageView image = (ImageView) convertView.findViewById(R.id.ivImageFlipper);
        File file = new File(Environment.getExternalStorageDirectory() + "/Denim Store/", currentOffer.getName() + "_" + String.valueOf(currentImg) + ".jpg");
        if (file.exists()) {
            image.setImageDrawable(Drawable.createFromPath(Environment.getExternalStorageDirectory() + "/Denim Store/" + currentOffer.getName() + "_" + String.valueOf(currentImg) + ".jpg"));
        }
        return convertView;
    }
}
