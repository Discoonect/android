package com.test.naverapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.test.naverapi.R;
import com.test.naverapi.model.Results;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    ArrayList<Results> resultList;

    public RecyclerViewAdapter(Context context, ArrayList<Results> resultList){
        this.context = context;
        this.resultList = resultList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.translate_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Results result = resultList.get(position);

        String translatedText = result.getTranslatedText();
        String txtkor = result.getTxtKor();

        holder.txtTranslate.setText(translatedText);
        holder.txt_kor.setText(txtkor);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTranslate;
        TextView txt_kor;


        public ViewHolder(@Nullable View itemView){
            super(itemView);
            txtTranslate = itemView.findViewById(R.id.txtTranslate);
            txt_kor = itemView.findViewById(R.id.txt_kor);
        }

        public void openWebPage(String url){
            Uri webPage = Uri.parse(url);
            Intent i = new Intent(Intent.ACTION_VIEW,webPage);
            if(i.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(i);
            }
        }
    }
}