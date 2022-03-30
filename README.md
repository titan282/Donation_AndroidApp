# Donation Mobile App
## Donation v1.0
- Thiết kế **content layout** gồm *paymentMethod, amountPicker, donateButton và progressBar*
- Xử lý event khi nhấn **donateButton**

## Donation v2.0
- Thêm **Report** option trong menu
- Thêm **Report Activity** để hiện danh sách donate

## Donation v3.0
- Tạo thêm model **Donation**, và **Base** class để **Donate, Report** extends
- Tạo một **DonationAdapter** để custom hiển thị listview trong Report
- Ẩn/hiện các **menuItem**: Donate, Report, Setttings
## Donation v4.0
- Tạo thêm **DonationApp** class extend **Application** để bao các **Activity** và khởi tạo các giá trị và khai báo database
- Sử dụng Database **SQLite**, các Donations sẽ được lưu vào database và giữ giá trị cho lần khởi chạy tiếp theo
- Thêm **menuItem** Reset
