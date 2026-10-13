package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editten, editsdt;
    RadioGroup rgDoiTuong;
    RadioButton rbSinhVien, rbGiangVien, rbDoanhNghiep;
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

        initViews();
        giaLapDL();
        initEvents();
    }

    private void initViews() {
        editten = findViewById(R.id.editten);
        editsdt = findViewById(R.id.editsdt);

        rgDoiTuong = findViewById(R.id.rgDoiTuong);
        rbSinhVien = findViewById(R.id.rbSinhVien);
        rbGiangVien = findViewById(R.id.rbGiangVien);
        rbDoanhNghiep = findViewById(R.id.rbDoanhNghiep);

        btthem = findViewById(R.id.btthem);
        btsua = findViewById(R.id.btsua);
        btthongke = findViewById(R.id.btthongke);

        listview = findViewById(R.id.listview);

        dsDangKy = new ArrayList<>();
        adapter = new DangKyAdapter(this, dsDangKy);
        listview.setAdapter(adapter);

        // Mặc định chọn vào đối tượng Sinh viên (Câu 1)
        rbSinhVien.setChecked(true);
        editten.requestFocus();
    }

    private void giaLapDL() {
        // Khởi tạo sẵn 3 dữ liệu mẫu trên danh sách (Câu 1)
        dsDangKy.add(new DangKy("Nguyễn Văn A", "Sinh viên", "0912345678"));
        dsDangKy.add(new DangKy("Trần Thị B", "Giảng viên", "0987654321"));
        dsDangKy.add(new DangKy("Lê Văn C", "Doanh nghiệp", "0905123456"));
        adapter.notifyDataSetChanged();
    }

    private void initEvents() {
        // Chọn 1 dòng trên ListView: hiển thị ngược lại thông tin lên form (Câu 3)
        listview.setOnItemClickListener((parent, view, position, id) -> {
            vitri = position;
            DangKy dk = dsDangKy.get(position);
            editten.setText(dk.getTen());
            editsdt.setText(dk.getSdt());

            if ("Giảng viên".equalsIgnoreCase(dk.getDoiTuong())) {
                rbGiangVien.setChecked(true);
            } else if ("Doanh nghiệp".equalsIgnoreCase(dk.getDoiTuong())) {
                rbDoanhNghiep.setChecked(true);
            } else {
                rbSinhVien.setChecked(true);
            }
        });

        // Sự kiện các nút lệnh
        btthem.setOnClickListener(v -> them());
        btsua.setOnClickListener(v -> sua());
        btthongke.setOnClickListener(v -> thongKe());
    }

    private String getDoiTuongChon() {
        if (rbGiangVien.isChecked()) {
            return "Giảng viên";
        } else if (rbDoanhNghiep.isChecked()) {
            return "Doanh nghiệp";
        } else {
            return "Sinh viên";
        }
    }

    private boolean kiemTraHopLe(String ten, String sdt) {
        if (ten.isEmpty()) {
            Toast.makeText(this, "Họ và tên không được để trống!", Toast.LENGTH_SHORT).show();
            editten.requestFocus();
            return false;
        }

        if (sdt.isEmpty()) {
            Toast.makeText(this, "Số điện thoại không được để trống!", Toast.LENGTH_SHORT).show();
            editsdt.requestFocus();
            return false;
        }

        if (sdt.length() != 10 || !sdt.matches("\\d+")) {
            Toast.makeText(this, "Số điện thoại phải đúng 10 chữ số!", Toast.LENGTH_SHORT).show();
            editsdt.requestFocus();
            return false;
        }

        return true;
    }

    // Viết mã cho nút Thêm mới (Câu 2)
    private void them() {
        String ten = editten.getText().toString().trim();
        String sdt = editsdt.getText().toString().trim();
        String doiTuong = getDoiTuongChon();

        if (!kiemTraHopLe(ten, sdt)) {
            return;
        }

        DangKy dk = new DangKy(ten, doiTuong, sdt);
        dsDangKy.add(dk);
        adapter.notifyDataSetChanged();

        xoaTrangForm();
        Toast.makeText(this, "Thêm đối tượng vào danh sách thành công!", Toast.LENGTH_SHORT).show();
    }

    // Bấm nút Sửa: kiểm tra dữ liệu, cập nhật lại dòng tương ứng (Câu 3)
    private void sua() {
        if (vitri == -1) {
            Toast.makeText(this, "Vui lòng chọn 1 dòng trên danh sách để sửa!", Toast.LENGTH_SHORT).show();
            return;
        }

        String ten = editten.getText().toString().trim();
        String sdt = editsdt.getText().toString().trim();
        String doiTuong = getDoiTuongChon();

        if (!kiemTraHopLe(ten, sdt)) {
            return;
        }

        DangKy dk = dsDangKy.get(vitri);
        dk.setTen(ten);
        dk.setDoiTuong(doiTuong);
        dk.setSdt(sdt);

        adapter.notifyDataSetChanged();
        xoaTrangForm();
        Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
    }

    // Bấm nút Thống kê: hiển thị AlertDialog (Câu 4)
    private void thongKe() {
        int tongSo = dsDangKy.size();
        int soSinhVien = 0;

        for (DangKy dk : dsDangKy) {
            if ("Sinh viên".equalsIgnoreCase(dk.getDoiTuong())) {
                soSinhVien++;
            }
        }

        String msg = "Tổng số lượt đăng ký: " + tongSo + "\nSố lượng là Sinh viên: " + soSinhVien;

        new AlertDialog.Builder(this)
                .setTitle("Thống kê đăng ký")
                .setMessage(msg)
                .setPositiveButton("Đồng ý", (dialog, which) -> dialog.dismiss())
                .setNegativeButton("Đóng", (dialog, which) -> finish())
                .show();
    }

    private void xoaTrangForm() {
        editten.setText("");
        editsdt.setText("");
        rbSinhVien.setChecked(true);
        vitri = -1;
        editten.requestFocus();
    }
}
