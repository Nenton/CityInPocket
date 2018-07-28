package com.nenton.trehgornyinpocket.ui.screens.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.utils.ViewHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolderNews> {

    @Inject
    Picasso picasso;
    @Inject
    NewsScreen.NewsPresenter presenter;
    private List<NewsEntity> news = new ArrayList<>();

    public void reloadAdapter(List<NewsEntity> news) {
        this.news.clear();
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        DaggerService.<NewsScreen.Component>getDaggerComponent(recyclerView.getContext()).inject(this);
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolderNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new, parent, false);
        return new ViewHolderNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNews holder, int position) {
        final NewsEntity currentNew = news.get(position);
        holder.description.setText(currentNew.getTitle());
        picasso.load(currentNew.getImagesUrl().get(0))
                .into(holder.image);
        holder.date.setText(ViewHelper.getDateFromPattern(currentNew.getDate()));
        holder.itemView.setOnClickListener(view -> presenter.clickOnNew(currentNew));
    }

    @Override
    public int getItemCount() {
        if (news != null) {
            return news.size();
        }
        return 0;
    }

    class ViewHolderNews extends RecyclerView.ViewHolder {
        private TextView description;
        private ImageView image;
        private TextView date;

        ViewHolderNews(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.organization_desc_tv);
            image = itemView.findViewById(R.id.announcement_image_iv);
            date = itemView.findViewById(R.id.organization_name_tv);
        }
    }
}
