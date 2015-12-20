package com.guo.duoduo.pm25rxjava.adapter;


import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.guo.duoduo.customadapterlibrary.CustomAdapter;
import com.guo.duoduo.customadapterlibrary.ViewHolder;
import com.guo.duoduo.pm25rxjava.R;
import com.guo.duoduo.pm25rxjava.entity.City;
import com.guo.duoduo.pm25rxjava.utils.MediaIndexer;


/**
 * Created by 郭攀峰 on 2015/12/20.
 */
public class CityAdapter extends CustomAdapter<City>
{
    /**
     * 字母表分组工具
     */
    private SectionIndexer mIndexer;

    public CityAdapter(Context context, int resLayoutId, List<City> list)
    {
        super(context, resLayoutId, list);
    }

    public void setIndexer(MediaIndexer indexer)
    {
        mIndexer = indexer;
    }

    @Override
    public void convert(ViewHolder viewHolder, City city, int position)
    {
        TextView tvCity = viewHolder.getTextView(R.id.tv_city);
        TextView sortKey = viewHolder.getTextView(R.id.sort_key);
        LinearLayout layout = viewHolder.getLinearLayout(R.id.sort_key_layout);

        int section = mIndexer.getSectionForPosition(position);
        if (position == mIndexer.getPositionForSection(section))
        {
            sortKey.setText(city.getSortKey());
            layout.setVisibility(View.VISIBLE);
        }
        else
            layout.setVisibility(View.GONE);
        tvCity.setText(city.getCityName());
        tvCity.setText(city.getCityName());
    }
}
