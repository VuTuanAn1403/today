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

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    private List<NhanVien> list;

    public NhanVienAdapter(@NonNull Context context, @NonNull List<NhanVien> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nhanvien, parent, false);
        }

        NhanVien nv = list.get(position);

        TextView txtma = convertView.findViewById(R.id.txtlistma);
        TextView txtten = convertView.findViewById(R.id.txtlisgt);
        TextView txtngaysinh = convertView.findViewById(R.id.txtlistngaysinh);
        TextView txtgioitinh = convertView.findViewById(R.id.txtlistgioitinh);

        if (nv != null) {
            txtma.setText("Mã NV: " + nv.getMa());
            txtten.setText("Họ tên: " + nv.getTen());
            txtngaysinh.setText("Ngày sinh: " + nv.getNgaysinh());
            txtgioitinh.setText("Giới tính: " + nv.getGioitinh());
        }

        return convertView;
    }
}
