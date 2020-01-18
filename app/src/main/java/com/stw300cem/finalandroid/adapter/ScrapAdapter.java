package com.stw300cem.finalandroid.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.stw300cem.finalandroid.MainActivity;
import com.stw300cem.finalandroid.R;
import com.stw300cem.finalandroid.api.ScrapAPI;
import com.stw300cem.finalandroid.helper.Url;
import com.stw300cem.finalandroid.models.Scrap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrapAdapter extends RecyclerView.Adapter<ScrapAdapter.MyViewHolder> {

    private Context context;
    List<Scrap> scrapList;

    public ScrapAdapter(Context context, List<Scrap> scrapList) {
        this.context = context;
        this.scrapList = scrapList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_scrap_list, parent, false);
        ScrapAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Scrap scrap = scrapList.get(position);
        holder.tvProductType.setText(scrap.getProductType());
        holder.tvDescription.setText(scrap.getDescription());
        holder.tvLocation.setText(scrap.getLocation());
        Picasso.get().load(Url.IMAGE_URL + scrap.getImage()).into(holder.imageView);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", scrap.get_id());
                intent.putExtra("image", scrap.getImage());
                intent.putExtra("producttype", scrap.getProductType());
                intent.putExtra("description", scrap.getDescription());
                intent.putExtra("location", scrap.getLocation());
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = scrap.get_id();
                deleteScrap(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return scrapList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvProductType, tvDescription, tvLocation, tvName;
        Button btnEdit, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageName);
            tvProductType = itemView.findViewById(R.id.listProductType);
            tvDescription = itemView.findViewById(R.id.listDescription);
            tvLocation = itemView.findViewById(R.id.listLocation);
            tvName = itemView.findViewById(R.id.textName);
            btnEdit = itemView.findViewById(R.id.buttonEdit);
            btnDelete = itemView.findViewById(R.id.buttonDelete);

        }
    }

    private void deleteScrap(String id) {

        ScrapAPI scrapAPI = Url.getInstance().create(ScrapAPI.class);
        Call<Void> deleteScrap = scrapAPI.deleteScrap(id);
        deleteScrap.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
