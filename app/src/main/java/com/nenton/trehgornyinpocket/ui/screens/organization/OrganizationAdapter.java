package com.nenton.trehgornyinpocket.ui.screens.organization;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.OrganizationDto;

import java.util.ArrayList;
import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationViewHolder> {
    private List<OrganizationDto.Contact> contacts = new ArrayList<>();

    public void loadData(List<OrganizationDto.Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        OrganizationDto.Contact contact = contacts.get(position);
        holder.textContact.setText(contact.getContactWay());
        holder.imageContact.setImageResource(getIdResByTypeContact(contact.getType()));

    }

    @DrawableRes
    private int getIdResByTypeContact(OrganizationDto.ContactType type) {
        switch (type) {
            case CONTACT_EMAIL:
                return R.drawable.ic_email;
            case CONTACT_PHONE:
                return R.drawable.ic_phone;
            case CONTACT_TELEGRAM:
                return R.drawable.ic_telegram;
            default:
                return R.drawable.ic_contact;
        }
    }

    @Override
    public int getItemCount() {
        if (contacts == null) {
            return 0;
        }
        return contacts.size();
    }

    class OrganizationViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageContact;
        private TextView textContact;

        OrganizationViewHolder(View itemView) {
            super(itemView);
            imageContact = itemView.findViewById(R.id.contact_image_iv);
            textContact = itemView.findViewById(R.id.contact_name_tv);
        }
    }
}
