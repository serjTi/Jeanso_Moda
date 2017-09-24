package tst.tertj.denimstore.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import tst.tertj.denimstore.POJO.Category;
import tst.tertj.denimstore.R;
import tst.tertj.denimstore.constants.Categories;

public class CategoriesFragment extends Fragment implements
        AdapterView.OnItemClickListener {

    private IOnMyCatalogClickListener catalogClickListener;
    private LinkedList<Category> women_categories_list, child_categories_list;

    // названия компаний (групп)
    String[] lv_groups = new String[]{Categories.WOMEN_CATEGORY,
            Categories.MEN_CATEGORY,
            Categories.CHILDREN_CATEGORY,
            Categories.ACCESSORIES_CATEGORY};

    // названия телефонов (элементов)
    String[] phonesHTC = new String[]{"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[]{"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[]{"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    // коллекция для групп
    ArrayList<Map<String, String>> groupData;

    // коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список атрибутов группы или элемента
    Map<String, String> m;

    ExpandableListView elvMain;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        catalogClickListener = (IOnMyCatalogClickListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
// заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        for (String group : lv_groups) {
            // заполняем список атрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        // список атрибутов групп для чтения
        String groupFrom[] = new String[]{"groupName"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[]{android.R.id.text1};

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список атрибутов для каждого элемента
        for (String category : Categories.WomenCategories.WOMEN_CATEGORIES_NAMES) {
            m = new HashMap<String, String>();
            m.put("categoryName", category); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

        // создаем коллекцию элементов для третьей группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String category : Categories.MenCategories.MEN_CATEGORIES_NAMES) {
            m = new HashMap<String, String>();
            m.put("categoryName", category);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String category : Categories.ChildrenCategories.CHILDREN_CATEGORIES_NAMES) {
            m = new HashMap<String, String>();
            m.put("categoryName", category);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String category : Categories.AccessoriesCategories.ACCESSORIES_CATEGORIES_NAMES) {
            m = new HashMap<String, String>();
            m.put("categoryName", category);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

// список атрибутов элементов для чтения
        String childFrom[] = new String[]{"categoryName"};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[]{android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                getActivity(),
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        elvMain = (ExpandableListView) view.findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
        elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                String data = tv.getText().toString();

                return true;
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Category category = women_categories_list.get(i);
        catalogClickListener.onCatalogClick(category);
    }

    public interface IOnMyCatalogClickListener {
        void onCatalogClick(Category category);
    }

}