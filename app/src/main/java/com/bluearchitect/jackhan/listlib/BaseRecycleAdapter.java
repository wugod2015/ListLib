package com.bluearchitect.jackhan.listlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class BaseRecycleAdapter extends RecyclerView.Adapter<BaseRecycleAdapter.BViewHolder>{
    @Override
    public BViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BViewHolder extends RecyclerView.ViewHolder{

        public BViewHolder(View itemView) {
            super(itemView);
        }
    }
}
