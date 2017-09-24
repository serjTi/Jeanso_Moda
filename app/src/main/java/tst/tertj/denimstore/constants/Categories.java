package tst.tertj.denimstore.constants;

/**
 * Created by sergey_tertychenko on 17.09.17.
 */

public class Categories {

    public static final String BASE_URL = "https://timeofstyle.com/prom_yml_drop_catalog.xml";

    public static final String WOMEN_CATEGORY = "Женская одежда";
    public static final String MEN_CATEGORY = "Мужская одежда";
    public static final String CHILDREN_CATEGORY = "Детская одежда";
    public static final String ACCESSORIES_CATEGORY = "Аксессуары";


    public static class WomenCategories {
        //women categories
        public static final String CATEGORY_WOMEN_JEANS = "441";
        public static final String CATEGORY_WOMEN_UNDERWEAR = "411";
        public static final String CATEGORY_WOMEN_BLOUSE_SHIRT = "103";
        public static final String CATEGORY_WOMEN_SWEATERS = "81";
        public static final String CATEGORY_WOMEN_T_SHIRTS = "82";
        public static final String CATEGORY_WOMEN_CARDIGANS = "99";
        public static final String CATEGORY_WOMEN_WAISTCOAT = "84";
        public static final String CATEGORY_WOMEN_TROUSERS = "85";
        public static final String CATEGORY_WOMEN_DRESSES = "98";
        public static final String CATEGORY_WOMEN_SHORTS = "440";
        public static final String CATEGORY_WOMEN_SHOES = "89";
        public static final String CATEGORY_WOMEN_JACKETS = "90";
        public static final String CATEGORY_WOMEN_LEGGINS = "97";
        public static final String CATEGORY_WOMEN_OVERALLS = "489"; //kombezi
        public static final String CATEGORY_WOMEN_SWIMSUIT = "491";//kupalniki
        public static final String CATEGORY_WOMEN_TUNIC = "462";//TUNIKI
        public static final String CATEGORY_WOMEN_SKIRTS = "457";//TUNIKI

        public static final String[] WOMEN_CATEGORIES_NAMES = {
                "Бельё", //401
                "Блузки, рубашки", //402
                "Брюки", //403
                "Джинсы",//404
                "Жилеты, корсеты",//405
                "Кардиганы", //406
                "Комбинезоны",//407
                "Купальники",//408
                "Куртки, пиджаки",//409
                "Лосины",//410
                "Майки, футболки",//411
                "Платья",//412
                "Свитера",//413
                "Туники",//414
                "Шорты",//415
                "Юбки"
        };

        public static final int[] CATEGORY_WOMEN_ID = {
                401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414,
                415, 416};

    }

    public static class MenCategories {
        //    men categories
        public static final String CATEGORY_MEN_JEANS = "109";
        public static final String[] MEN_CATEGORIES_NAMES = {
                "Джинсы",//404
                "Брюки", //403
                "Свитера",//413
                "Куртки",//409
                "Жилетки",//405
                "Майки",//411
                "Рубашки", //401
                "Пиджаки",//408
                "Футболки", //402
                "Белье", //402
        };
    }

    public static class ChildrenCategories {

        public static final String CATEGORY_CHILD_SHORTS = "435";
        public static final String CATEGORY_CHILD_T_SHORTS = "432";
        public static final String CATEGORY_CHILD_SWEATERS = "433";
        public static final String CATEGORY_CHILD_SKIRTS = "434";

        public static final String[] CHILDREN_CATEGORIES_NAMES = {
                "Шорты",
                "Футболки",
                "Батники",
                "Юбки"
        };

        public static final int[] CHILDREN_CATEGORIES_ID = {
                301, 302, 303, 304};
    }

    public static class AccessoriesCategories {

        public static final String[] ACCESSORIES_CATEGORIES_NAMES = {
                "Ремни",
                "Бабочки",
                "Подтяжки",
                "Галстуки",
                "Сумки, кошельки",
                "Шапки",
                "Шарфы",
                "Перчатки",
                "Купальники",
                "Очки",
                "Шляпы"
        };
    }
}
