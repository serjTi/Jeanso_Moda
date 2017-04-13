package tst.tertj.denimstore.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Offer implements Parcelable {
//

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    private String offer_id;
    private String price;
    private String categoryId; //категория( мужские джинсы, женские, шорты, брюки)
    private String currencyId; // валюта UAH
    private String picture_1_URL;
    private String picture_2_URL;
    private String picture_3_URL;
    private String picture_4_URL;
    private String picture_5_URL;
    private String picture_6_URL;
    private String picture_7_URL;
    private String picture_8_URL;
    private String name;
    private String description;
    private String sizes; // размеры
    private String color;
    private String brand;
    private String country_of_origin;
    private int howMuchPhotos;
    public void setHowMuchPhotos(int howMuchPhotos) {
        this.howMuchPhotos = howMuchPhotos;
    }

    public int getHowMuchPhotos() {
        return howMuchPhotos;
    }



    public String getPrice() {
        return price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public String getPicture_1_URL() {
        return picture_1_URL;
    }

    public String getPicture_2_URL() {
        return picture_2_URL;
    }

    public String getPicture_3_URL() {
        return picture_3_URL;
    }

    public String getPicture_4_URL() {
        return picture_4_URL;
    }

    public String getPicture_5_URL() {
        return picture_5_URL;
    }

    public String getPicture_6_URL() {
        return picture_6_URL;
    }

    public String getPicture_7_URL() {
        return picture_7_URL;
    }

    public String getPicture_8_URL() {
        return picture_8_URL;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSizes() {
        return sizes;
    }

    public String getCountry_of_origin() {
        return country_of_origin;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public void setPicture_1_URL(String picture_1_URL) {
        this.picture_1_URL = picture_1_URL;


    }

    public void setPicture_2_URL(String picture_2_URL) {
        this.picture_2_URL = picture_2_URL;
    }

    public void setPicture_3_URL(String picture_3_URL) {
        this.picture_3_URL = picture_3_URL;
    }

    public void setPicture_4_URL(String picture_4_URL) {
        this.picture_4_URL = picture_4_URL;
    }

    public void setPicture_5_URL(String picture_5_URL) {
        this.picture_5_URL = picture_5_URL;
    }

    public void setPicture_6_URL(String picture_6_URL) {
        this.picture_6_URL = picture_6_URL;
    }

    public void setPicture_7_URL(String picture_7_URL) {
        this.picture_7_URL = picture_7_URL;
    }

    public void setPicture_8_URL(String picture_8_URL) {
        this.picture_8_URL = picture_8_URL;
    }

    public void setName(String name)
    {
        this.name = name;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCountry_of_origin(String country_of_origin) {
        this.country_of_origin = country_of_origin;
    }
    public Offer(){
        this.name = "";
        this.price = "";
        this.currencyId = "";
        this.brand = "";
        this.country_of_origin = "";
        this.description = "";
    }
    public Offer(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        this.name = data[0];
        this.price = data[1];
        this.currencyId = data[2];
        this.brand = data[3];
        this.country_of_origin = data[4];
        this.description = data[5];
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.name, this.price, this.currencyId, this.brand, this.country_of_origin, this.description});
    }
    public static final Parcelable.Creator<Offer> CREATOR= new Parcelable.Creator<Offer>() {

        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };



}
