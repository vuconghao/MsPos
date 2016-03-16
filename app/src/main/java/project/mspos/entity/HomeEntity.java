package project.mspos.entity;

import java.util.ArrayList;

/**
 * Created by CONGHAO on 3/16/2016.
 */
public class HomeEntity {

    private ArrayList<BannerEntity> list_banner;

    private ArrayList<CategoryEntity> list_category;

    public ArrayList<BannerEntity> getList_banner() {
        return list_banner;
    }

    public void setList_banner(ArrayList<BannerEntity> list_banner) {
        this.list_banner = list_banner;
    }

    public ArrayList<CategoryEntity> getList_category() {
        return list_category;
    }

    public void setList_category(ArrayList<CategoryEntity> list_category) {
        this.list_category = list_category;
    }
}
