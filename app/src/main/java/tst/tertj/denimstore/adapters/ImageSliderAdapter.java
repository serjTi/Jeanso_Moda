package tst.tertj.denimstore.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tst.tertj.denimstore.R;
import tst.tertj.denimstore.POJO.Offer;

/**
 * Created by sergey_tertychenko on 11.08.17.
 */

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    private View sliderView;
    private Offer current_offer;

    public ImageSliderAdapter(Context context, Offer inputSlerList) {
        this.context = context;
        current_offer = inputSlerList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return current_offer.images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView)object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        sliderView = inflater.inflate(R.layout.slider_item, container, false);
        viewHolder = new ViewHolder(sliderView);
        Glide.with(context).load(current_offer.images.get(position)).into(viewHolder.image);
        container.addView(sliderView, 0);
        return sliderView;
    }

    private class ViewHolder {
        public ImageView image;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.ivSliderItem);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
