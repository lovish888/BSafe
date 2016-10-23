package com.insane.lovish.informe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by LovishJain on 06-Oct-16.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    int counter = 0;
    private Context context;
    private List<CrimeDetailsVariables> crimeDetails = Collections.emptyList();
    private List<ContactDetails> contactDetails = Collections.emptyList();
    private List<BlogDetailsVariables> blogDetails = Collections.emptyList();
    private LayoutInflater inflator;

    public RecyclerViewAdapter(Context context, List<CrimeDetailsVariables> crimeDetails) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        this.crimeDetails = crimeDetails;
        counter = 1;
    }

    public RecyclerViewAdapter(Context context, List<ContactDetails> contactDetails, int noUse) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        this.contactDetails = contactDetails;
        counter = 2;
    }

    public RecyclerViewAdapter(Context context, List<BlogDetailsVariables> blogDetails, float noUse) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        this.blogDetails = blogDetails;
        counter = 3;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (counter == 1) {
            view = inflator.inflate(R.layout.crime_card, parent, false);

        } else if (counter == 2) {
            view = inflator.inflate(R.layout.contact_card, parent, false);

        } else if (counter == 3) {
            view = inflator.inflate(R.layout.blog_card, parent, false);
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (counter == 1) {
            CrimeDetailsVariables current = crimeDetails.get(position);
            holder.CrimeImage.setImageBitmap(current.getCrimeImage());
            holder.Title.setText(current.getTitle());
            holder.Author.setText(current.getAuthor());
            holder.TimePassed.setText(current.getTimePassed());

        } else if (counter == 2) {
            ContactDetails current = contactDetails.get(position);
            holder.name.setText(current.getName());
            holder.number.setText(current.getNumber());

        } else if (counter == 3) {
            BlogDetailsVariables current = blogDetails.get(position);
            holder.title.setText(current.getTitle());
            holder.author.setText(current.getAuthor());
        }
    }

    @Override
    public int getItemCount() {
        if (counter == 1) {
            return crimeDetails.size();
        } else if (counter == 2) {
            return contactDetails.size();
        } else if (counter == 3) {
            return blogDetails.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        //Counter = 1
        ImageView CrimeImage;
        TextView Title, Author, TimePassed;
        //Counter = 2
        TextView name, number;
        //Counter = 3
        TextView title, author;

        MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            if (counter == 1) {
                CrimeImage = (ImageView) itemView.findViewById(R.id.crime_image);
                Title = (TextView) itemView.findViewById(R.id.crime_title);
                Author = (TextView) itemView.findViewById(R.id.crime_author);
                TimePassed = (TextView) itemView.findViewById(R.id.time_passed);

            } else if (counter == 2) {
                name = (TextView) itemView.findViewById(R.id.name);
                number = (TextView) itemView.findViewById(R.id.number);
                itemView.setOnLongClickListener(this);

            } else if (counter == 3) {
                title = (TextView) itemView.findViewById(R.id.blog_title);
                author = (TextView) itemView.findViewById(R.id.blog_author);
            }
        }

        @Override
        public void onClick(View view) {

            if (counter == 1) {
                Intent intent = new Intent(context, CrimeCardDetails.class);
                intent.putExtra("position", getLayoutPosition());
                context.startActivity(intent);

            } else if (counter == 2) {
                ContactDetails current = contactDetails.get(getLayoutPosition());
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + current.getNumber()));
                context.startActivity(callIntent);

            } else if (counter == 3) {

            }
        }

        @Override
        public boolean onLongClick(View view) {

            if (counter == 2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this contact?");
                builder.setTitle("Delete Contact");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactDetails current = contactDetails.get(getLayoutPosition());
                        InformeDatabaseAdapter adapter = new InformeDatabaseAdapter(context);
                        adapter.deleteContactById(current.id);
                        ((EmergencyContacts) context).onResume();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return true;
        }
    }
}
