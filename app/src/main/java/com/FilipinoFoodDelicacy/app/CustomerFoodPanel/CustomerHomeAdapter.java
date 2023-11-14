package com.FilipinoFoodDelicacy.app.CustomerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.FilipinoFoodDelicacy.app.ChefFoodPanel.UpdateDishModel;
import com.FilipinoFoodDelicacy.app.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {


    private Context mcontext;
    private List<UpdateDishModel>updateDishModellist;

    String RandomId,ChefID;

    public CustomerHomeAdapter(Context context,List<UpdateDishModel>updateDishModellist)
    {
        this.updateDishModellist=updateDishModellist;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.customer_menudish,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final UpdateDishModel updateDishModel=updateDishModellist.get(position);
        Glide.with(mcontext).load(updateDishModel.getImageURL()).into(holder.imageView);
        holder.Dishname.setText(updateDishModel.getDishes());
        RandomId = updateDishModel.getRandomUID();
        updateDishModel.getChefId();

        ChefID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.price.setText("Php: " + updateDishModel.getPrice());
        final int dishprice = Integer.parseInt(updateDishModel.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mcontext, OrderDish.class);
                intent.putExtra("FoodMenu",updateDishModel.getRandomUID());
                intent.putExtra("ChefId",updateDishModel.getChefId());


                mcontext.startActivity(intent);
            }
        });

        holder.additem.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                int num = newValue;

                int totalprice = num * dishprice;
                if (num != 0) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("DishID", updateDishModel.getRandomUID());
                    hashMap.put("DishName", updateDishModel.getDishes());
                    hashMap.put("DishQuantity", String.valueOf(num));
                    hashMap.put("Price", String.valueOf(dishprice));
                    hashMap.put("Totalprice", String.valueOf(totalprice));
                    hashMap.put("ChefId", updateDishModel.getChefId());

                    FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(updateDishModel.getRandomUID()).setValue(hashMap);
                    FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal").setValue(String.valueOf(totalprice));
                } else {
                    FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(updateDishModel.getRandomUID()).removeValue();
                    FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal").removeValue();
                }
            }
        });

    }


    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView Dishname,price;
        ElegantNumberButton additem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.menu_image);
            Dishname=itemView.findViewById(R.id.dishname);
            price=itemView.findViewById(R.id.dishprice);
            additem=itemView.findViewById(R.id.number_btnr);


        }
    }
}
