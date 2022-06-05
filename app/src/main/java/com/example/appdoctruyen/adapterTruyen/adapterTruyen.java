package com.example.appdoctruyen.adapterTruyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Truyen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterTruyen extends BaseAdapter {

    private Context context;
    private ArrayList<Truyen> listTruyen;
    private int layout;

    public adapterTruyen(Context context,int layout, ArrayList<Truyen> listTruyen) {
        this.context = context;
        this.listTruyen = listTruyen;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return listTruyen.size();
    }

    @Override
    public Object getItem(int position) {
        return listTruyen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // listSearch
    public void listSearch(ArrayList<Truyen> listSearch) {
        listTruyen = listSearch;
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView txtTenTruyen;
        ImageView imgTruyen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.new_truyen, null);

        viewHolder.txtTenTruyen = convertView.findViewById(R.id.txtTentruyen);
        viewHolder.imgTruyen = convertView.findViewById(R.id.imgTruyen);
        convertView.setTag(viewHolder);

        Truyen truyen = (Truyen) getItem(position);
        viewHolder.txtTenTruyen.setText(truyen.getTenTruyen());
        Picasso.get().load(truyen.getAnh()).placeholder(R.drawable.ic_load).error(R.drawable.ic_image).into(viewHolder.imgTruyen);

        return convertView;
    }
}
