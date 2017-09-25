package tst.tertj.denimstore.manager;

import java.util.LinkedList;

import tst.tertj.denimstore.POJO.Offer;

/**
 * Created by sergey_tertychenko on 11.08.17.
 */

public class DataManager {

    private static DataManager _instance;

    public String abv_xml_base;
    public String tos_xml_base;
    public String balani_xml_base;
    public boolean is_base_download;

    public LinkedList<Offer> offersList;
    public LinkedList<Offer> shortsCatalog;

    public LinkedList<Offer> women_leggins;
    public LinkedList<Offer> women_jeans;
    public LinkedList<Offer> women_blouse_shirt;
    public LinkedList<Offer> women_sweaters;
    public LinkedList<Offer> women_t_shirt;
    public LinkedList<Offer> women_cardigans;
    public LinkedList<Offer> women_waistcoat;
    public LinkedList<Offer> women_trousers;
    public LinkedList<Offer> women_dresses;
    public LinkedList<Offer> women_shoes;
    public LinkedList<Offer> women_jackets;
    public LinkedList<Offer> women_underwear;
    public LinkedList<Offer> women_overalls;
    public LinkedList<Offer> women_swimsuit;
    public LinkedList<Offer> women_tunic;
    public LinkedList<Offer> women_skirts;

    public LinkedList<Offer> child_t_shorts;
    public LinkedList<Offer> child_skirts;
    public LinkedList<Offer> child_sweaters;
    public LinkedList<Offer> child_shorts;

    public LinkedList<Offer> men_jeans;
    public LinkedList<Offer> men_trousers;
    public LinkedList<Offer> men_sweaters;
    public LinkedList<Offer> men_jackets;
    public LinkedList<Offer> men_waistcoat;
    public LinkedList<Offer> men_maiki;
    public LinkedList<Offer> men_shirts;
    public LinkedList<Offer> men_pizdaki;
    public LinkedList<Offer> men_t_shirts;
    public LinkedList<Offer> men_underwear;


    public LinkedList<Offer> accessories_belt;   //remni
    public LinkedList<Offer> accessories_bow_tea;   // babochki
    public LinkedList<Offer> accessories_suspenders; //podtiagki
    public LinkedList<Offer> accessories_tie;   //galstuki
    public LinkedList<Offer> accessories_bags_wallets;
    public LinkedList<Offer> accessories_hat; //shapki
    public LinkedList<Offer> accessories_scarf;
    public LinkedList<Offer> accessories_gloves;
    public LinkedList<Offer> accessories_underwear;
    public LinkedList<Offer> accessories_sunglasses;
    public LinkedList<Offer> accessories_hat_2; //shliapi


    public Offer choosen_offer;

    public static DataManager getInstance() {
        if (_instance == null) {
            _instance = new DataManager();
        }
        return _instance;
    }

    private DataManager() {
        abv_xml_base = "";
        tos_xml_base = "";
        balani_xml_base = "";
        is_base_download = false;
        choosen_offer = new Offer();

        offersList = new LinkedList<>();

        women_jeans = new LinkedList<>();
        shortsCatalog = new LinkedList<>();
        women_leggins = new LinkedList<>();
        women_blouse_shirt = new LinkedList<>();
        women_sweaters = new LinkedList<>();
        women_t_shirt = new LinkedList<>();
        women_cardigans = new LinkedList<>();
        women_waistcoat = new LinkedList<>();
        women_trousers = new LinkedList<>();
        women_dresses = new LinkedList<>();
        women_shoes = new LinkedList<>();
        women_jackets = new LinkedList<>();
        women_underwear = new LinkedList<>();
        women_overalls = new LinkedList<>();
        women_swimsuit = new LinkedList<>();
        women_tunic = new LinkedList<>();
        women_skirts = new LinkedList<>();

        child_shorts = new LinkedList<>();
        child_skirts = new LinkedList<>();
        child_sweaters = new LinkedList<>();
        child_t_shorts = new LinkedList<>();

        men_jeans = new LinkedList<>();
        men_trousers = new LinkedList<>();
        men_sweaters = new LinkedList<>();
        men_jackets = new LinkedList<>();
        men_waistcoat = new LinkedList<>();
        men_maiki = new LinkedList<>();
        men_shirts = new LinkedList<>();
        men_pizdaki = new LinkedList<>();
        men_t_shirts = new LinkedList<>();
        men_underwear = new LinkedList<>();

        accessories_belt = new LinkedList<>();
        accessories_bow_tea = new LinkedList<>();
        accessories_suspenders = new LinkedList<>();
        accessories_tie = new LinkedList<>();
        accessories_bags_wallets = new LinkedList<>();
        accessories_hat = new LinkedList<>();
        accessories_scarf = new LinkedList<>();
        accessories_gloves = new LinkedList<>();
        accessories_underwear = new LinkedList<>();
        accessories_sunglasses = new LinkedList<>();
        accessories_hat_2 = new LinkedList<>();

    }
}
