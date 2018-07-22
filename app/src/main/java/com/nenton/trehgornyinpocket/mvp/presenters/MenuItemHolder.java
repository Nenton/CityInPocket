package com.nenton.trehgornyinpocket.mvp.presenters;

import android.support.v7.widget.SearchView;
import android.view.MenuItem;

public class MenuItemHolder {
    private final CharSequence itemTitle;
    private final int iconResId;
    private final MenuItem.OnMenuItemClickListener mListener;
    private final MenuItemSearch menuItemSearch;

    public MenuItemHolder(CharSequence itemTitle, int iconResId, MenuItem.OnMenuItemClickListener listener, MenuItemSearch menuItemSearch) {
        this.itemTitle = itemTitle;
        this.iconResId = iconResId;
        this.mListener = listener;
        this.menuItemSearch = menuItemSearch;
    }

    public CharSequence getTitle() {
        return itemTitle;
    }

    public int getIconResId() {
        return iconResId;
    }

    public MenuItem.OnMenuItemClickListener getListener() {
        return mListener;
    }

    public MenuItemSearch getMenuItemSearch() {
        return menuItemSearch;
    }

    public static class MenuItemSearch {
        private final String hint;
        private final String query;
        private final boolean isExpand;
        private final MenuItem.OnActionExpandListener expandListener;
        private final SearchView.OnQueryTextListener queryListener;

        public MenuItemSearch(String hint,
                              MenuItem.OnActionExpandListener expandListener,
                              SearchView.OnQueryTextListener queryListener,
                              String query,
                              boolean isExpand) {
            this.hint = hint;
            this.expandListener = expandListener;
            this.queryListener = queryListener;
            this.query = query;
            this.isExpand = isExpand;
        }

        public String getHint() {
            return hint;
        }

        public MenuItem.OnActionExpandListener getExpandListener() {
            return expandListener;
        }

        public SearchView.OnQueryTextListener getQueryListener() {
            return queryListener;
        }

        public String getQuery() {
            return query;
        }

        public boolean isExpand() {
            return isExpand;
        }
    }
}
