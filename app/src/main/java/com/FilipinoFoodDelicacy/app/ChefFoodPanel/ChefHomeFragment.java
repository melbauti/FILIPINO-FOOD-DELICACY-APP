package com.FilipinoFoodDelicacy.app.ChefFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.FilipinoFoodDelicacy.app.Chef;
import com.FilipinoFoodDelicacy.app.MainMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.FilipinoFoodDelicacy.app.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChefHomeFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView recyclerViewLunch;
    RecyclerView recyclerViewMeryenda;

    private List<UpdateDishModel> updateDishModelList;
    private List<UpdateDishModel> updateDishModelListL;
    private List<UpdateDishModel> updateDishModelListM;
    private ChefhomeAdapter adapter;
    DatabaseReference dataaa;
    private String Region, City;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_home, null);
        getActivity().setTitle("Food Item");
        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.Recycle_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewLunch = v.findViewById(R.id.Recycle_menu_lunch);
        recyclerViewLunch.setHasFixedSize(true);
        recyclerViewLunch.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewMeryenda = v.findViewById(R.id.Recycle_menu_meryenda);
        recyclerViewMeryenda.setHasFixedSize(true);
        recyclerViewMeryenda.setLayoutManager(new LinearLayoutManager(getContext()));

        updateDishModelList = new ArrayList<>();
        updateDishModelListL = new ArrayList<>();
        updateDishModelListM = new ArrayList<>();

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = FirebaseDatabase.getInstance().getReference("Chef").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Chef chefc = dataSnapshot.getValue(Chef.class);
                Region = chefc.getRegion();
                City = chefc.getCity();
                chefDishesBreakfast();
                chefDishesLunch();
                chefDishesMeryenda();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }


    private void chefDishesBreakfast() {

        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child("Breakfast").child(Region).child(City).child(useridd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UpdateDishModel updateDishModel = snapshot.getValue(UpdateDishModel.class);
                    updateDishModelList.add(updateDishModel);

                }
                adapter = new ChefhomeAdapter(getContext(), updateDishModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void chefDishesLunch() {

        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child("Lunch").child(Region).child(City).child(useridd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelListL.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UpdateDishModel updateDishModel = snapshot.getValue(UpdateDishModel.class);
                    updateDishModelListL.add(updateDishModel);

                }
                ChefhomeLunchAdapter adapter = new ChefhomeLunchAdapter(getContext(), updateDishModelListL);
                recyclerViewLunch.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void chefDishesMeryenda() {

        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child("Meryenda").child(Region).child(City).child(useridd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelListM.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UpdateDishModel updateDishModel = snapshot.getValue(UpdateDishModel.class);
                    updateDishModelListM.add(updateDishModel);

                }
                ChefhomeMeryendaAdapter adapter = new ChefhomeMeryendaAdapter(getContext(), updateDishModelListM);
                recyclerViewMeryenda.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int idd = item.getItemId();
        if (idd == R.id.LogOut) {
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);

    }



}
