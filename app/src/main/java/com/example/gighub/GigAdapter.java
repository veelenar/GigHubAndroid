package com.example.gighub;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class GigAdapter extends RecyclerView.Adapter<GigAdapter.GigViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Gig> gigList;

    //getting the context and product list with constructor
    public GigAdapter(Context mCtx, List<Gig> gigList) {
        this.mCtx = mCtx;
        this.gigList = gigList;
    }

    @Override
    public GigViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new GigViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GigViewHolder holder, int position) {
        //getting the gig of the specified position
        Gig gig = gigList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(gig.getArtist());
        holder.textViewGenre.setText(gig.getGenre());
        holder.textViewVenue.setText(String.valueOf(gig.getVenue()));
        holder.textViewPrice.setText(String.valueOf(gig.getPrice()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.logo));
        holder.textViewAddress.setText(String.valueOf(gig.getAddress()));
        holder.textViewShortDesc.setText(String.valueOf(gig.getShortDesc()));
        holder.textViewDate.setText(String.valueOf(gig.getDate()));

    }


    @Override
    public int getItemCount() {
        return gigList.size();
    }


    class GigViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewGenre, textViewVenue, textViewPrice, textViewAddress, textViewShortDesc, textViewDate;
        ImageView imageView;

        public GigViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewArtist);
            textViewGenre = itemView.findViewById(R.id.textViewGenre);
            textViewVenue = itemView.findViewById(R.id.textViewVenue);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            imageView = itemView.findViewById(R.id.imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    // item clicked
                    if(textViewAddress.getVisibility() == View.GONE) {
                        textViewAddress.setVisibility(View.VISIBLE);
                        textViewShortDesc.setVisibility(View.VISIBLE);
                        textViewPrice.setVisibility(View.VISIBLE);
                    }
                    else {
                        textViewAddress.setVisibility(View.GONE);
                        textViewShortDesc.setVisibility(View.GONE);
                        textViewPrice.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
