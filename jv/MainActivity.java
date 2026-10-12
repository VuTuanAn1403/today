package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editten, edittuoi;
    CheckBox chklaptrinh, chkkiemthu, chkthu2, chkthu5;
    Button btthem, btsua, btthongke;
    ListView listview;

    ArrayList<DangKy> dsDangKy;
    DangKyAdapter adapter;
    int vitri = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dl();
        giaLapDL();
        suKien();
    }

    private void dl() {
        editten = findViewById(R.id.editten);
        edittuoi = findViewById(R.id.edittuoi);

        chklaptrinh = findViewById(R.id.chklaptrinh);
        chkkiemthu = findViewById(R.id.chkkiemthu);

        chkthu2 = findViewById(R.id.chkthu2);
        chkthu5 = findViewById(R.id.chkthu5);

        btthem = findViewById(R.id.btthem);
        btsua = findViewById(R.id.btsua);
        btthongke = findViewById(R.id.btthongke);

        listview = findViewById(R.id.listview);

        dsDangKy = new ArrayList<>();
        adapter = new DangKyAdapter(this, dsDangKy);
        listview.setAdapter(adapter);

        // Mặc định chọn Lập trình và Thứ 2
        chklaptrinh.setChecked(true);
        chkthu2.setChecked(true);

        // Mặc định con trỏ vào tên (Câu 1)
        editten.requestFocus();
    }

    private void giaLapDL() {
        // Giả lập 3 thông tin đăng ký phỏng vấn (Câu 1)
        dsDangKy.add(new DangKy("Nguyễn Văn A", "20", "Lập trình", "Thứ 2"));
        dsDangKy.add(new DangKy("Trần Thị B", "21", "Kiểm thử", "Thứ 5"));
        dsDangKy.add(new DangKy("Lê Văn C", "19", "Lập trình, Kiểm thử", "Thứ 2, Thứ 5"));
        adapter.notifyDataSetChanged();
    }

    private void suKien() {
        // Chọn 1 dòng trong ListView (Câu 3)
        listview.setOnItemClickListener((parent, view, position, id) -> {
            vitri = position;
            DangKy dk = dsDangKy.get(position);
            editten.setText(dk.getTen());
            edittuoi.setText(dk.getTuoi());

            chklaptrinh.setChecked(dk.getLinhVuc().contains("Lập trình"));
            chkkiemthu.setChecked(dk.getLinhVuc().contains("Kiểm thử"));

            chkthu2.setChecked(dk.getNgayHen().contains("Thứ 2"));
            chkthu5.setChecked(dk.getNgayHen().contains("Thứ 5"));
        });

        // Sự kiện các nút bấm
        btthem.setOnClickListener(v -> them());
        btsua.setOnClickListener(v -> sua());
        btthongke.setOnClickListener(v -> thongKe());
    }

    private String getLinhVucChon() {
        StringBuilder sb = new StringBuilder();
        if (chklaptrinh.isChecked()) {
            sb.append("Lập trình");
        }
        if (chkkiemthu.isChecked()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("Kiểm thử");
        }
        return sb.length() > 0 ? sb.toString() : "Chưa chọn";
    }

    private String getNgayHenChon() {
        StringBuilder sb = new StringBuilder();
        if (chkthu2.isChecked()) {
            sb.append("Thứ 2");
        }
        if (chkthu5.isChecked()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("Thứ 5");
        }
        return sb.length() > 0 ? sb.toString() : "Chưa chọn";
    }

    private boolean kiemTraHopLe(String ten, String tuoi) {
        if (ten.isEmpty()) {
            Toast.makeText(this, "Tên không được để trống!", Toast.LENGTH_SHORT).show();
            editten.requestFocus();
            return false;
        }

        if (tuoi.isEmpty()) {
            Toast.makeText(this, "Tuổi không được để trống!", Toast.LENGTH_SHORT).show();
            edittuoi.requestFocus();
            return false;
        }

        return true;
    }

    // Thêm mới thông tin đăng ký (Câu 2)
    private void them() {
        String ten = editten.getText().toString().trim();
        String tuoi = edittuoi.getText().toString().trim();
        String linhVuc = getLinhVucChon();
        String ngayHen = getNgayHenChon();

        if (!kiemTraHopLe(ten, tuoi)) {
            return;
        }

        DangKy dk = new DangKy(ten, tuoi, linhVuc, ngayHen);
        dsDangKy.add(dk);
        adapter.notifyDataSetChanged();

        xoaTrangForm();
        Toast.makeText(this, "Thêm thông tin đăng ký thành công!", Toast.LENGTH_SHORT).show();
    }

    // Sửa thông tin đăng ký (Câu 3)
    private void sua() {
        if (vitri == -1) {
            Toast.makeText(this, "Vui lòng chọn 1 dòng trong ListView để sửa!", Toast.LENGTH_SHORT).show();
            return;
        }

        String ten = editten.getText().toString().trim();
        String tuoi = edittuoi.getText().toString().trim();
        String linhVuc = getLinhVucChon();
        String ngayHen = getNgayHenChon();

        if (!kiemTraHopLe(ten, tuoi)) {
            return;
        }

        DangKy dk = dsDangKy.get(vitri);
        dk.setTen(ten);
        dk.setTuoi(tuoi);
        dk.setLinhVuc(linhVuc);
        dk.setNgayHen(ngayHen);

        adapter.notifyDataSetChanged();
        xoaTrangForm();
        Toast.makeText(this, "Sửa thông tin đăng ký thành công!", Toast.LENGTH_SHORT).show();
    }

    // Thống kê đăng ký theo Lĩnh vực (Câu 3 - Thống kê)
    private void thongKe() {
        int soLapTrinh = 0;
        int soKiemThu = 0;

        for (DangKy dk : dsDangKy) {
            if (dk.getLinhVuc().contains("Lập trình")) {
                soLapTrinh++;
            }
            if (dk.getLinhVuc().contains("Kiểm thử")) {
                soKiemThu++;
            }
        }

        String msg = "Thống kê đăng ký phỏng vấn theo Lĩnh vực:\n\n" +
                "- Lập trình: " + soLapTrinh + " lượt\n" +
                "- Kiểm thử: " + soKiemThu + " lượt\n" +
                "----------------------------------------\n" +
                "Tổng số đăng ký: " + dsDangKy.size();

        new AlertDialog.Builder(this)
                .setTitle("Thống kê đăng ký")
                .setMessage(msg)
                .setPositiveButton("Đóng", null)
                .show();
    }

    private void xoaTrangForm() {
        editten.setText("");
        edittuoi.setText("");
        chklaptrinh.setChecked(true);
        chkkiemthu.setChecked(false);
        chkthu2.setChecked(true);
        chkthu5.setChecked(false);
        vitri = -1;
        editten.requestFocus();
    }
}
