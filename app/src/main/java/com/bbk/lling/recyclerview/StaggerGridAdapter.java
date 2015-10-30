package com.bbk.lling.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class: StaggerGridAdapter
 * @Description:
 * @author: lling(www.cnblogs.com/liuling)
 * @Date: 2015/10/29
 */
public class StaggerGridAdapter extends RecyclerView.Adapter<StaggerGridAdapter.ItemViewHolder> {

    private List<String> mDatas;
    private List<Integer> mHeights;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public StaggerGridAdapter(Context context, List<String> mDatas) {
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add( (int) (100 + Math.random() * 300));
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(mInflater.inflate(
                R.layout.stagger_item_layout, parent, false));
        return holder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.mTextView.setText(mDatas.get(position));
        ViewGroup.LayoutParams lp = itemViewHolder.mTextView.getLayoutParams();
        lp.height = mHeights.get(position);

        itemViewHolder.mTextView.setLayoutParams(lp);
        if(mOnItemClickListener != null) {
            if(!itemViewHolder.itemView.hasOnClickListeners()) {
                Log.e("ListAdapter", "setOnClickListener");
                itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = itemViewHolder.getPosition();
                        mOnItemClickListener.onItemClick(v, pos);
                    }
                });
                itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = itemViewHolder.getPosition();
                        mOnItemClickListener.onItemLongClick(v, pos);
                        return true;
                    }
                });
            }
        }
    }

    /**
     * ����Ԫ��
     * @param position  ���ӵ�λ��
     * @param value     ����Ԫ�ص�ֵ
     */
    public void add(int position, String value) {
        if(position > mDatas.size()) {
            position = mDatas.size();
        }
        if(position < 0) {
            position = 0;
        }
        mDatas.add(position, value);
        mHeights.add(position, (int) (100 + Math.random() * 300));
        notifyItemInserted(position);
    }

    /**
     * �Ƴ�ָ��λ�õ�Ԫ��
     * @param position
     * @return
     */
    public String remove(int position) {
        if(position >= mDatas.size() || position < 0) {
            return null;
        }
        String value = mDatas.remove(position);
        mHeights.remove(position);
        notifyItemRemoved(position);
        return value;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * ����item�ĵ���¼��ͳ����¼�
     */
    interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textview);
        }
    }
}