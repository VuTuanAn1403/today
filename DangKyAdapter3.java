package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DangKyAdapter extends ArrayAdapter<DangKy> {
    private Context context;
    private List<DangKy> list;

    public DangKyAdapter(@NonNull Context context, @NonNull List<DangKy> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dangky, parent, false);
        }

        DangKy dk = list.get(position);

        TextView txtTen = convertView.findViewById(R.id.txtTen);
        TextView txtDoiTuong = convertView.findViewById(R.id.txtDoiTuong);
        TextView txtSdt = convertView.findViewById(R.id.txtSdt);

        if (dk != null) {
            txtTen.setText(dk.getTen());
            txtDoiTuong.setText(dk.getDoiTuong());
            txtSdt.setText(dk.getSdt());
        }

        return convertView;
    }
}
