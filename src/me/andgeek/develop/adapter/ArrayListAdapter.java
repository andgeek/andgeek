package me.andgeek.develop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * GridView，ListView列表基类的父类
 */
public abstract class ArrayListAdapter<T> extends BaseAdapter {
    
    /** 列表界面数据集 */
    protected List<T> mList;
    
    /** 上下文 */
    protected Context mContext;
    
    /** GridView,ListView的父类 */
    protected AbsListView mListView;
    
    /**
     * 创建一个新的实例 ArrayListAdapter.
     * 
     * @param context
     */
    public ArrayListAdapter(Context context) {
        this.mContext = context;
    }
    
    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        
        else {
            return 0;
        }
    }
    
    @Override
    public T getItem(int position) {
        return mList == null ? null : mList.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    /**
     * @description 设置List类型的数据集
     * @date 2015年8月17日
     * @param list
     */
    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }
    
    public List<T> getList() {
        return mList;
    }
    
    /**
     * @description 设置数组类型的数据集
     * @date 2015年1月30日
     * @param 数据集
     * @Exception
     */
    public void setList(T[] list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }
    
    public AbsListView getListView() {
        return mListView;
    }
    
    public void setListView(AbsListView listView) {
        mListView = listView;
    }
    
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
    
}
