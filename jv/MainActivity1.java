package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editma, editten, editngaysinh;
    RadioButton rbnam, rbnu;
    RadioGroup rggioitinh;
    ListView listview;
    Button btthem, btsua, btxoa, btthoat, btthongke;
    ArrayList<NhanVien> dsnv;
    NhanVienAdapter adapter;
    int vitri = -1;
    Calendar ngaychon;

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
        editma = findViewById(R.id.editma);
        editten = findViewById(R.id.editten);
        editngaysinh = findViewById(R.id.editngaysinh);
        rbnam = findViewById(R.id.rbnam);
        rbnu = findViewById(R.id.rbnu);
        rggioitinh =findViewById(R.id.rggioitinh);

        btthem = findViewById(R.id.btthem);
        btsua = findViewById(R.id.btsua);
        btxoa = findViewById(R.id.btxoa);
        btthoat = findViewById(R.id.btthoat);
        btthongke = findViewById(R.id.btthongke);

        listview = findViewById(R.id.listview);

        dsnv = new ArrayList<>();
        adapter = new NhanVienAdapter(this, dsnv);
        listview.setAdapter(adapter);

        // Mặc định chọn Nam theo yêu cầu Câu 1
        rbnam.setChecked(true);
    }

    private void giaLapDL() {
        // Giả lập 3 item theo yêu cầu Câu 1
        dsnv.add(new NhanVien("NV01", "Vũ Nhật Minh", "01/01/2000", "Nam"));
        dsnv.add(new NhanVien("NV02", "Nguyễn Văn B", "15/05/2002", "Nam"));
        dsnv.add(new NhanVien("NV03", "Trần Thị C", "20/10/2004", "Nữ"));
        adapter.notifyDataSetChanged();
    }

    private void suKien() {
        // Chọn ngày sinh bằng DatePickerDialog
        editngaysinh.setOnClickListener(v -> chonNgaySinh());

        // Chọn item trên ListView thì hiển thị dữ liệu lên các View control (Câu 2)
        listview.setOnItemClickListener((parent, view, position, id) -> {
            vitri = position;
            NhanVien nv = dsnv.get(position);
            editma.setText(nv.getMa());
            editten.setText(nv.getTen());
            editngaysinh.setText(nv.getNgaysinh());

            if ("Nam".equalsIgnoreCase(nv.getGioitinh())) {
                rbnam.setChecked(true);
            } else {
                rbnu.setChecked(true);
            }
        });

        // Đăng ký sự kiện các nút bấm
        btthem.setOnClickListener(v -> them());
        btsua.setOnClickListener(v -> sua());
        btxoa.setOnClickListener(v -> xoa());
        btthongke.setOnClickListener(v -> thongKe());
        btthoat.setOnClickListener(v -> xacNhanThoat());
    }

    private void chonNgaySinh() {
        Calendar c = Calendar.getInstance();
        int nam = c.get(Calendar.YEAR);
        int thang = c.get(Calendar.MONTH);
        int ngay = c.get(Calendar.DAY_OF_MONTH);

        // Nếu đã có ngày sinh thì mở DatePicker theo ngày đó
        String curDateStr = editngaysinh.getText().toString().trim();
        if (!curDateStr.isEmpty()) {
            try {
                String[] parts = curDateStr.split("/");
                if (parts.length == 3) {
                    ngay = Integer.parseInt(parts[0]);
                    thang = Integer.parseInt(parts[1]) - 1;
                    nam = Integer.parseInt(parts[2]);
                }
            } catch (Exception ignored) {
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            ngaychon = Calendar.getInstance();
            ngaychon.set(year, month, dayOfMonth);
            String ngaysinh = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
            editngaysinh.setText(ngaysinh);
        }, nam, thang, ngay);

        dialog.show();
    }

    // Kiểm tra tính hợp lệ của dữ liệu (Câu 3)
    private boolean kiemTraHopLe(String ma, String ten, String ngaysinh, boolean isEditMode) {
        if (ma.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã nhân viên!", Toast.LENGTH_SHORT).show();
            editma.requestFocus();
            return false;
        }

        if (ten.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên nhân viên!", Toast.LENGTH_SHORT).show();
            editten.requestFocus();
            return false;
        }

        if (ngaysinh.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày sinh!", Toast.LENGTH_SHORT).show();
            chonNgaySinh();
            return false;
        }

        // Kiểm tra trùng mã nhân viên
        for (int i = 0; i < dsnv.size(); i++) {
            NhanVien nv = dsnv.get(i);
            if (nv.getMa().equalsIgnoreCase(ma)) {
                if (!isEditMode) {
                    Toast.makeText(this, "Mã nhân viên đã tồn tại!", Toast.LENGTH_SHORT).show();
                    editma.requestFocus();
                    return false;
                } else if (i != vitri) {
                    Toast.makeText(this, "Mã nhân viên đã trùng với nhân viên khác!", Toast.LENGTH_SHORT).show();
                    editma.requestFocus();
                    return false;
                }
            }
        }

        // Kiểm tra tuổi >= 15
        int tuoi = tinhTuoi(ngaysinh);
        if (tuoi < 0) {
            Toast.makeText(this, "Ngày sinh không đúng định dạng (dd/MM/yyyy)!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tuoi < 15) {
            Toast.makeText(this, "Tuổi đời của nhân viên phải từ 15 tuổi trở lên (Tuổi hiện tại: " + tuoi + ")!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Tính tuổi đời từ ngày sinh
    private int tinhTuoi(String ngaysinhStr) {
        try {
            String[] parts = ngaysinhStr.split("/");
            if (parts.length != 3) return -1;
            int ngay = Integer.parseInt(parts[0]);
            int thang = Integer.parseInt(parts[1]) - 1;
            int nam = Integer.parseInt(parts[2]);

            Calendar today = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.set(nam, thang, ngay);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return age;
        } catch (Exception e) {
            return -1;
        }
    }

    private void them() {
        String ma = editma.getText().toString().trim();
        String ten = editten.getText().toString().trim();
        String ngaysinh = editngaysinh.getText().toString().trim();
        String gioitinh = rbnam.isChecked() ? "Nam" : "Nữ";

        if (!kiemTraHopLe(ma, ten, ngaysinh, false)) {
            return;
        }

        NhanVien nv = new NhanVien(ma, ten, ngaysinh, gioitinh);
        dsnv.add(nv);
        adapter.notifyDataSetChanged();
        xoaTrangForm();
        Toast.makeText(this, "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
    }

    // Nút lệnh Sửa (Câu 3)
    private void sua() {
        if (vitri == -1) {
            Toast.makeText(this, "Vui lòng chọn một nhân viên trên danh sách để sửa!", Toast.LENGTH_SHORT).show();
            return;
        }

        String ma = editma.getText().toString().trim();
        String ten = editten.getText().toString().trim();
        String ngaysinh = editngaysinh.getText().toString().trim();
        String gioitinh = rbnam.isChecked() ? "Nam" : "Nữ";

        if (!kiemTraHopLe(ma, ten, ngaysinh, true)) {
            return;
        }

        // Yêu cầu xác nhận trước khi cập nhật (Câu 3)
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận cập nhật")
                .setMessage("Bạn có chắc chắn muốn sửa thông tin nhân viên này không?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    NhanVien nv = dsnv.get(vitri);
                    nv.setMa(ma);
                    nv.setTen(ten);
                    nv.setNgaysinh(ngaysinh);
                    nv.setGioitinh(gioitinh);

                    adapter.notifyDataSetChanged();
                    xoaTrangForm();
                    Toast.makeText(MainActivity.this, "Cập nhật thông tin nhân viên thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void xoa() {
        if (vitri == -1) {
            Toast.makeText(this, "Vui lòng chọn một nhân viên trên danh sách để xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        NhanVien nv = dsnv.get(vitri);
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa nhân viên '" + nv.getTen() + "' (Mã: " + nv.getMa() + ") không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    dsnv.remove(vitri);
                    adapter.notifyDataSetChanged();
                    xoaTrangForm();
                    Toast.makeText(MainActivity.this, "Xóa nhân viên thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void thongKe() {
        int tongSo = dsnv.size();
        int soNam = 0;
        int soNu = 0;

        for (NhanVien nv : dsnv) {
            if ("Nam".equalsIgnoreCase(nv.getGioitinh())) {
                soNam++;
            } else {
                soNu++;
            }
        }

        String msg = "Tổng số nhân viên: " + tongSo + "\n" +
                "- Số nhân viên Nam: " + soNam + "\n" +
                "- Số nhân viên Nữ: " + soNu;

        new AlertDialog.Builder(this)
                .setTitle("Thống kê nhân viên")
                .setMessage(msg)
                .setPositiveButton("Đóng", null)
                .show();
    }

    // Nút lệnh Thoát (Câu 4)
    private void xacNhanThoat() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có muốn thoát ứng dụng không?")
                .setPositiveButton("Có", (dialog, which) -> finish())
                .setNegativeButton("Không", null)
                .show();
    }

    private void xoaTrangForm() {
        editma.setText("");
        editten.setText("");
        editngaysinh.setText("");
        rbnam.setChecked(true); // Mặc định giới tính Nam
        vitri = -1;
        editma.requestFocus();
    }
}
