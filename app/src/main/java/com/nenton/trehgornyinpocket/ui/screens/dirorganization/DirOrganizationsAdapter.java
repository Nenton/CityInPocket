package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DirOrganizationsAdapter extends RecyclerView.Adapter<DirOrganizationsAdapter.OrganizationsViewHolder> {
    @Inject
    Picasso picasso;
    @Inject
    DirOrganizationsScreen.DirOrganizationsPresenter presenter;

    private List<OrganizationEntity> organizations = new ArrayList<>();

    public void reloadAdapter(List<OrganizationEntity> organizations) {
        this.organizations = organizations;
            notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        DaggerService.<DirOrganizationsScreen.Component>getDaggerComponent(recyclerView.getContext()).inject(this);
    }

    @NonNull
    @Override
    public OrganizationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organization, parent, false);
        return new OrganizationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationsViewHolder holder, int position) {
        OrganizationEntity organization = organizations.get(position);
        holder.name.setText(organization.getTitle());
        holder.description.setText(organization.getDescription());

        picasso.load(organization.getImagesUrl())
                .placeholder(R.drawable.ic_organization)
                .resize(150, 150)
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.image, new Callback.EmptyCallback() {
                    @Override
                    public void onError(Exception e) {
                        picasso.load(organization.getImagesUrl())
                                .placeholder(R.drawable.ic_organization)
                                .resize(150, 150)
                                .centerCrop()
                                .into(holder.image);
                    }
                });
        holder.itemView.setOnClickListener(view -> presenter.clickOnOrganization(organization));
    }

    @Override
    public int getItemCount() {
        if (organizations == null) {
            return 0;
        }
        return organizations.size();
    }

    class OrganizationsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;
        private ImageView image;

        OrganizationsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.organization_name_tv);
            description = itemView.findViewById(R.id.organization_desc_tv);
            image = itemView.findViewById(R.id.organization_image_iv);
        }
    }
}
