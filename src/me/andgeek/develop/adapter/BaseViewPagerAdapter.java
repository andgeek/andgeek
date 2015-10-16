package me.andgeek.develop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseViewPagerAdapter<T> extends PagerAdapter {
    
    protected Context mContext;
    
    protected LayoutInflater mInflater;
    
    protected List<T> mList;
    
    public BaseViewPagerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    
    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
    
    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);
    
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
    
    @Override
    public Parcelable saveState() {
        return null;
    }
    
    public void setList(List<T> list) {
        this.mList = list;
    }
    
    public List<T> getList() {
        return mList;
    }
    
    public void setList(T[] list) {
        if (list == null || list.length == 0) {
            mList = new ArrayList<T>();
            return;
        }
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }
    
}
