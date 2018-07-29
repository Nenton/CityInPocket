package com.nenton.trehgornyinpocket.ui.screens.organization;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class OrganizationView extends AbstractView<OrganizationScreen.OrganizationPresenter> {
    @BindView(R.id.organization_logo_iv)
    ImageView logo;
    @BindView(R.id.organization_name_tv)
    TextView name;
    @BindView(R.id.organization_description_tv)
    TextView description;
    @BindView(R.id.organization_contacts_rv)
    RecyclerView contactsView;

    @Inject
    Picasso picasso;

    private OrganizationAdapter adapter;

    public OrganizationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<OrganizationScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void afterInflate() {
        if (adapter == null) {
            adapter = new OrganizationAdapter();
        }
        contactsView.setAdapter(adapter);
    }

    public void initView(OrganizationEntity organization) {
        name.setText(organization.getTitle());
        description.setText(organization.getDescription());
        picasso.load(organization.getImagesUrl())
                .into(logo);

        adapter.loadData(organization.getContacts());
    }
}
