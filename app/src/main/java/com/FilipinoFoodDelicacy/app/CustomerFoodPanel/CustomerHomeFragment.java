package com.FilipinoFoodDelicacy.app.CustomerFoodPanel;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.FilipinoFoodDelicacy.app.ChefFoodPanel.UpdateDishModel;
import com.FilipinoFoodDelicacy.app.Customer;
import com.FilipinoFoodDelicacy.app.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView recyclerView;
    RecyclerView recyclerViewLunch;
    RecyclerView recyclerViewMeryenda;
    private List<UpdateDishModel> updateDishModelListB;
    private List<UpdateDishModel> updateDishModelListL;
    private List<UpdateDishModel> updateDishModelListM;
    private CustomerHomeAdapter adapter;
    String Region, City;
    DatabaseReference dataaa, databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;

    ImageView bgimage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerhome, null);
        getActivity().setTitle("Filipino Food Delicacy");
        setHasOptionsMenu(true);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p1), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p3), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p4), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p5), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p6), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p7), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p8), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p9), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.p10), 3000);



        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);
        bgimage = v.findViewById(R.id.viewPRoduct);
        bgimage.setBackgroundDrawable(animationDrawable);
        bgimage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        animationDrawable.start();

        recyclerView = v.findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);

        recyclerViewLunch = v.findViewById(R.id.recycle_menu_lunch);
        recyclerViewLunch.setHasFixedSize(true);

        recyclerViewMeryenda = v.findViewById(R.id.recycle_menu_meryenda);
        recyclerViewMeryenda.setHasFixedSize(true);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.move);
        recyclerView.startAnimation(animation);
        recyclerViewLunch.startAnimation(animation);

        LinearLayoutManager breakfast = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(breakfast);

        LinearLayoutManager Lunch = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerViewLunch.setLayoutManager(Lunch);

        LinearLayoutManager Meryenda = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerViewMeryenda.setLayoutManager(Meryenda);

        updateDishModelListB = new ArrayList<>();
        updateDishModelListL = new ArrayList<>();
        updateDishModelListM = new ArrayList<>();

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                dataaa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
                dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer cust = dataSnapshot.getValue(Customer.class);
                        Region = cust.getRegion();
                        City = cust.getCity();
                        customermenuBreakfast();
                        customermenuLunch();
                        customermenuMeryenda();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return v;
    }


    @Override
    public void onRefresh() {

        customermenuBreakfast();
        customermenuLunch();
        customermenuMeryenda();
    }

    private void customermenuBreakfast() {

        swipeRefreshLayout.setRefreshing(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child("Breakfast").child(Region).child(City);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelListB.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        UpdateDishModel updateDishModel = snapshot1.getValue(UpdateDishModel.class);
                        updateDishModelListB.add(updateDishModel);
                    }
                }
                adapter = new CustomerHomeAdapter(getContext(), updateDishModelListB);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });

    }

    private void customermenuLunch() {

        swipeRefreshLayout.setRefreshing(true);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child("Lunch").child(Region).child(City);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelListL.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        UpdateDishModel updateDishModel = snapshot1.getValue(UpdateDishModel.class);
                        updateDishModelListL.add(updateDishModel);
                    }
                }
                CustomerHomeLunchAdapter adapter = new CustomerHomeLunchAdapter(getContext(), updateDishModelListL);
                recyclerViewLunch.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });

    }

    private void customermenuMeryenda() {

        swipeRefreshLayout.setRefreshing(true);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child("Meryenda").child(Region).child(City);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelListM.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        UpdateDishModel updateDishModel = snapshot1.getValue(UpdateDishModel.class);
                        updateDishModelListM.add(updateDishModel);
                    }
                }
                CustomerHomeMeryendaAdapter adapter = new CustomerHomeMeryendaAdapter(getContext(), updateDishModelListM);
                recyclerViewMeryenda.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });

    }

    private void search(final String searchtext) {

        ArrayList<UpdateDishModel> mylist = new ArrayList<>();
        for (UpdateDishModel object : updateDishModelListB) {
            if (object.getDishes().toLowerCase().contains(searchtext.toLowerCase())) {
                mylist.add(object);
            }
        }
        adapter = new CustomerHomeAdapter(getContext(), mylist);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.Searchdish);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Dish");


    }
}
