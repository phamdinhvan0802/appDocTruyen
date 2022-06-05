package com.example.appdoctruyen.adapterTruyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.TaiKhoan;

import java.util.List;

public class adapterThongtin extends BaseAdapter {

    private Context context;
    private int layout;
    private List<TaiKhoan> listTK;

    public adapterThongtin(Context context, int layout, List<TaiKhoan> listTK) {
        this.context = context;
        this.layout = layout;
        this.listTK = listTK;
    }

    @Override
    public int getCount() {
        return listTK.size();
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

        TextView txtEmail = (TextView) view.findViewById(R.id.txtgmail);

        TaiKhoan taikhoan = listTK.get(i);

        txtEmail.setText(taikhoan.getmEmail());

        return view;
    }
}
