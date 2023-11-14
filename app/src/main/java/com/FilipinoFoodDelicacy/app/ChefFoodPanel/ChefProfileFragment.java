package com.FilipinoFoodDelicacy.app.ChefFoodPanel;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.FilipinoFoodDelicacy.app.R;
public class ChefProfileFragment extends Fragment {

    Button post;
    ImageView bgimage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_profile, container, false);
        getActivity().setTitle("Post Dish");

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
        bgimage = v.findViewById(R.id.back1);
        bgimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        post = (Button) v.findViewById(R.id.post_dish);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Chef_PostDish.class));
            }
        });

        return v;
    }
}