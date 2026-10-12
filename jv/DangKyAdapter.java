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

        TextView txtTenTuoiNgayHen = convertView.findViewById(R.id.txtTenTuoiNgayHen);
        TextView txtLinhVuc = convertView.findViewById(R.id.txtLinhVuc);

        if (dk != null) {
            txtTenTuoiNgayHen.setText(dk.getTen() + " - " + dk.getTuoi() + " - " + dk.getNgayHen());
            txtLinhVuc.setText("Lĩnh vực: " + dk.getLinhVuc());
        }

        return convertView;
    }
}
