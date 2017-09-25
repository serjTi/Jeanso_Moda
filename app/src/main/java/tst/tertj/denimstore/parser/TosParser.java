package tst.tertj.denimstore.parser;

import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

import tst.tertj.denimstore.POJO.Offer;
import tst.tertj.denimstore.constants.Const;
import tst.tertj.denimstore.constants.Categories;
import tst.tertj.denimstore.manager.DataManager;

/**
 * Created by sergey_tertychenko on 17.09.17.
 */

public class TosParser {

    private LinkedList<Offer> offersList;
    private LinkedList<String> images_list;
    private Offer offer;
    private String text;
    private DataManager data_manager = DataManager.getInstance();
    private String zpt = ", ";
    boolean duplicateOffers = false;

    public TosParser() {
        offersList = new LinkedList<Offer>();

    }

    public boolean parse() {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(new StringReader(DataManager.getInstance().tos_xml_base));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(Const.OFFER)) {
                            offer = new Offer();
                            offer.diller = Const.Dillers.TOS;
                            images_list = new LinkedList<>();
                            duplicateOffers = false;
                            Log.d(Const.TAG_PARSER, "new offer" + offer.toString());

                            Log.d(Const.TAG_PARSER,
                                    "START_TAG: имя тега = " + parser.getName()
                                            + ", уровень = " + parser.getDepth()
                                            + ", число атрибутов = "
                                            + parser.getAttributeCount());
                            String tmp = "";
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                tmp = parser.getAttributeName(i);
                                if (tmp.equalsIgnoreCase(Const.ID_ATTRIBUTE)) {
                                    offer.offer_id = parser.getAttributeValue(i);
                                }
                            }
                            if (!TextUtils.isEmpty(tmp))
                                Log.d(Const.TAG_PARSER, "Атрибуты: " + tmp);


                        } else if (tagname.equalsIgnoreCase(Const.PARAM)) {
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                String attributeValue = parser.getAttributeValue(i);
                                Log.d(Const.TAG_SIZE, "attributeValue = " + attributeValue);
                                if (attributeValue.equalsIgnoreCase(Const.SIZE_ATTRIBUTE)) {
                                    int token = parser.nextToken();
                                    while (token != XmlPullParser.CDSECT) {
                                        token = parser.nextToken();
                                    }
                                    String cdata = parser.getText();


                                    Log.d(Const.TAG_SIZE, "cdata = " + cdata);
                                }
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (offer != null) {
                            if (tagname.equalsIgnoreCase(Const.OFFER)) {
                                if (!duplicateOffers) {
                                    offer.images = images_list;
                                    offersList.add(offer);
                                    addOfferByCategory(offer);
                                }
                                images_list = null;
                                offer = null;
                            } else if (tagname.equalsIgnoreCase(Const.PRICE)) {
                                offer.drop_price = text;
                            } else if (tagname.equalsIgnoreCase(Const.CURRENCY_ID)) {
                                offer.currencyId = text;
                            } else if (tagname.equalsIgnoreCase(Const.CATEGORY_ID)) {
                                offer.categoryId = text;
                            } else if (tagname.equalsIgnoreCase(Const.PICTURE)) {
                                if (!text.equals("      ")) {
                                    images_list.add(text);
                                }
                            } else if (tagname.equalsIgnoreCase(Const.NAME)) {
                                offer.name = text;
                                if (offersList.size() >= 1) {
                                    Offer lastListOffer = offersList.getLast();
                                    if (lastListOffer.name.equals(offer.name)) {
                                        duplicateOffers = true;
                                    }
                                }
                            } else if (tagname.equalsIgnoreCase(Const.DESCRIPTION)) {
                                offer.description = text;
                            } else if (tagname.equalsIgnoreCase(Const.COUNTRY_OF_ORIGIN)) {
                                offer.country_of_origin = text;
                            }
                        }
                        Log.d(Const.TAG_PARSER, "END_TAG: имя тега = " + tagname);
                        Log.d(Const.TAG_PARSER, "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                        break;

                    case XmlPullParser.START_DOCUMENT:
                        Log.d(Const.TAG_PARSER, "Начало документа");

                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void addOfferByCategory(Offer offer) {
        String category_id = offer.categoryId;
        if (category_id != null) {
            switch (category_id) {
                case Categories.WomenCategories.CATEGORY_WOMEN_JEANS:
                    data_manager.women_jeans.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_BLOUSE_SHIRT:
                    data_manager.women_blouse_shirt.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_SWEATERS:
                    data_manager.women_sweaters.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_T_SHIRTS:
                    data_manager.women_t_shirt.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_CARDIGANS:
                    data_manager.women_cardigans.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_WAISTCOAT:
                    data_manager.women_waistcoat.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_TROUSERS:
                    data_manager.women_trousers.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_DRESSES:
                    data_manager.women_dresses.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_SHORTS:
                    data_manager.shortsCatalog.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_SHOES:
                    data_manager.women_shoes.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_JACKETS:
                    data_manager.women_jackets.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_LEGGINS:
                    data_manager.women_leggins.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_UNDERWEAR:
                    data_manager.women_underwear.add(offer);
                    break;


                case Categories.WomenCategories.CATEGORY_WOMEN_OVERALLS:
                    data_manager.women_overalls.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_SWIMSUIT:
                    data_manager.women_swimsuit.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_TUNIC:
                    data_manager.women_tunic.add(offer);
                    break;

                case Categories.WomenCategories.CATEGORY_WOMEN_SKIRTS:
                    data_manager.women_skirts.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_JEANS:
                    data_manager.men_jeans.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_JACKETS:
                    data_manager.men_jackets.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_MAIKI:
                    data_manager.men_maiki.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_PIZDAKI:
                    data_manager.men_pizdaki.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_SHIRTS:
                    data_manager.men_shirts.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_SWEATERS:
                    data_manager.men_sweaters.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_T_SHIRTS:
                    data_manager.men_t_shirts.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_TROUSERS:
                    data_manager.men_trousers.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_UNDERWEAR:
                    data_manager.men_underwear.add(offer);
                    break;

                case Categories.MenCategories.CATEGORY_MEN_WAISTCOAT:
                    data_manager.men_waistcoat.add(offer);
                    break;

                case Categories.ChildrenCategories.CATEGORY_CHILD_SHORTS:
                    data_manager.child_shorts.add(offer);
                    break;

                case Categories.ChildrenCategories.CATEGORY_CHILD_SKIRTS:
                    data_manager.child_skirts.add(offer);
                    break;

                case Categories.ChildrenCategories.CATEGORY_CHILD_T_SHORTS:
                    data_manager.child_t_shorts.add(offer);
                    break;

                case Categories.ChildrenCategories.CATEGORY_CHILD_SWEATERS:
                    data_manager.child_sweaters.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_BAGS_WALLETS:
                    data_manager.accessories_bags_wallets.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_TIES:
                    data_manager.accessories_tie.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_BELTS:
                    data_manager.accessories_belt.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_BOW_TIE:
                    data_manager.accessories_bow_tea.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_GLOVES:
                    data_manager.accessories_gloves.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_HAT:
                    data_manager.accessories_hat.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_HATS:
                    data_manager.accessories_hat_2.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_SCARFS:
                    data_manager.accessories_scarf.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_SUNGLUSSES:
                    data_manager.accessories_sunglasses.add(offer);
                    break;

                case Categories.AccessoriesCategories.ACCESSORIES_SUSPENDERS:
                    data_manager.accessories_suspenders.add(offer);
                    break;

                default:
                    break;
            }
        }

    }
}