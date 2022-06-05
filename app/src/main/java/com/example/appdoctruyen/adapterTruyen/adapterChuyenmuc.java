package com.example.appdoctruyen.adapterTruyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.chuyenmuc;

import java.util.List;

public class adapterChuyenmuc extends BaseAdapter {

    private Context context;
    private int layout;
    private List<chuyenmuc> listChuyenmuc;

    public adapterChuyenmuc(Context context, int layout, List<chuyenmuc> listChuyenmuc) {
        this.context = context;
        this.layout = layout;
        this.listChuyenmuc = listChuyenmuc;
    }

    @Override
    public int getCount() {
        return listChuyenmuc.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layout, null);

        ImageView img = (ImageView) view.findViewById(R.id.imgchuyenmuc);

        TextView txt = (TextView) view.findViewById(R.id.txttenchuyenmuc);

        chuyenmuc cm = listChuyenmuc.get(i);

        txt.setText(cm.getTenchuyenmuc());

        img.setImageResource(cm.getHinhanhchuyenmuc());

        return view;
    }
}
