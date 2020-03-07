package com.demo.musicdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.musicdemo.R;
import com.demo.musicdemo.activitys.PlayMusicActivity;
import com.demo.musicdemo.models.MusicModel;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalculationRvHeight = false; // 标记 RecyclerView 的高度只需计算和设置一次
    private List<MusicModel> mDataSource;

    public MusicListAdapter(Context context, RecyclerView recyclerView, List<MusicModel> dataSource) {
        mContext = context;
        mRv = recyclerView;
        this.mDataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_music, parent, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // onBindViewHolder 可以获取 ItemView 的高度
        setRecyclerViewHeight();

        final MusicModel musicModel = mDataSource.get(position);


        Glide.with(mContext)
                .load(musicModel.getPoster())
                .into(holder.ivIcon);

        holder.tvName.setText(musicModel.getName());
        holder.tvAuthor.setText(musicModel.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID, musicModel.getMusicId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    /**
     * RecyclerView 和 ScrollView 重叠使用的时候会导致列表高度出现异常，所以 RecyclerView 需要设置具体的高度
     * 1. 获取 ItemView 的高度
     * 2. 获取 ItemView 的数量
     * 3. 使用 itemViewHeight * ItemViewCount = RecyclerView 的高度
     */
    public void setRecyclerViewHeight() {

        if (isCalculationRvHeight || mRv == null) return;

        isCalculationRvHeight = true;

        // 获取 ItemView 的高度
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        // 获取 ItemView 的数量
        int itemCount = getItemCount();
        // 使用 itemViewHeight * ItemViewCount = RecyclerView 的高度
        int recyclerViewHeight = itemViewLp.height * itemCount;
        // 设置 RecyclerView 的高度
        LinearLayout.LayoutParams rvLp = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvLp.height = recyclerViewHeight;
        // RecyclerView 重新设置 setLayoutParams
        mRv.setLayoutParams(rvLp);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView ivIcon;
        TextView tvName;
        TextView tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
