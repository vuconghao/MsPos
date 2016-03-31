package project.mspos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.mspos.R;
import project.mspos.activity.MainActivity;
import project.mspos.adapter.GridViewProductAdapter;
import project.mspos.entity.CategoryEntity;
import project.mspos.entity.ProductEntity;
import project.mspos.entity.ProductImage;
import project.mspos.entity.ProductInCartItem;
import project.mspos.utils.RecyclerItemClickListener;

/**
 * Created by SON on 3/21/2016.
 */
public class FragmentGiridViewProduct extends Fragment {
    private RecyclerView recyclerViewProduct;
    private Spinner spinnerCategory;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<ProductEntity> listProduct;
    private ArrayList<CategoryEntity> listCategory;
    private List<String> listCategoryName;
    AddProductInCartInterface mCallBack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_list_category_product,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDefaultListProduct();
        setDefaultListCategory();
        setupView();
        setAdapterForRecyclerView();
        setAdapterForSpinner();
        registerForEvent();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack=(AddProductInCartInterface)activity;
    }

    private void registerForEvent() {
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recyclerViewProduct.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        ProductInCartItem product=new ProductInCartItem(listProduct.get(position).getList_image().get(0).getmProductImage(),listProduct.get(position).getmProductName(),1,listProduct.get(position).getmProductPrice());
                        mCallBack.addProduct(product);
                    }
                })
        );
    }

    private void setAdapterForSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,listCategoryName);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);


    }

    private void setDefaultListCategory() {
        listCategoryName=new ArrayList<String>();
        listCategory=new ArrayList<CategoryEntity>();
        listCategory.add(new CategoryEntity(1,"Women",0,listProduct));
        listCategoryName.add("Women");
        listCategory.add(new CategoryEntity(1,"Men",0,listProduct));
        listCategoryName.add("Men");
        listCategory.add(new CategoryEntity(1,"Accessories",0,listProduct));
        listCategoryName.add("Accessories");
        listCategory.add(new CategoryEntity(1,"Home & Decor",0,listProduct));
        listCategoryName.add("Home & Decor");
        listCategory.add(new CategoryEntity(1,"Sale",0,listProduct));
        listCategoryName.add("Sale");
        listCategory.add(new CategoryEntity(1,"VIP",0,listProduct));
        listCategoryName.add("VIP");
    }

    private void setAdapterForRecyclerView() {
        GridViewProductAdapter rcAdapter = new GridViewProductAdapter(getActivity(), listProduct);
        recyclerViewProduct.setAdapter(rcAdapter);
    }

    private void setupView() {
        gridLayoutManager=new GridLayoutManager(getActivity(),4);
        recyclerViewProduct=(RecyclerView)getActivity().findViewById(R.id.list_catecory_product);
        spinnerCategory=(Spinner)getActivity().findViewById(R.id.spinner_category);
        recyclerViewProduct.setHasFixedSize(true);
        recyclerViewProduct.setLayoutManager(gridLayoutManager);
    }

    private void setDefaultListProduct(){
        ArrayList<ProductImage> listImageProduct=new ArrayList<ProductImage>();
        listImageProduct.add(new ProductImage(1,R.drawable.glass));
        listProduct=new ArrayList<ProductEntity>();
        listProduct.add(new ProductEntity(1,"sun glass 1",true,"",15.0f,"",listImageProduct));
        listProduct.add(new ProductEntity(1,"sun glass 2",true,"",15.0f,"",listImageProduct));
        listProduct.add(new ProductEntity(1,"sun glass 3",true,"",15.0f,"",listImageProduct));
        listProduct.add(new ProductEntity(1,"sun glass 4",true,"",15.0f,"",listImageProduct));
        listProduct.add(new ProductEntity(1,"sun glass 5",true,"",15.0f,"",listImageProduct));
        listProduct.add(new ProductEntity(1,"sun glass 6",true,"",15.0f,"",listImageProduct));
        listProduct.add(new ProductEntity(1,"sun glass 7",true,"",15.0f,"",listImageProduct));
        listProduct.add(new ProductEntity(1,"sun glass 8",true,"",15.0f,"",listImageProduct));
    }

    public interface AddProductInCartInterface{
        public void addProduct(ProductInCartItem productInCartItem);
    }
}
