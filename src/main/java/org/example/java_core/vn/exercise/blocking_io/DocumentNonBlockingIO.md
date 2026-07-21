# Non-blocking I/O (Vào/Ra không chặn)

## 1. Khái niệm cơ bản

**Non-blocking I/O** là mô hình xử lý vào/ra dữ liệu (đọc/ghi file, gửi/nhận dữ liệu qua mạng) mà ở đó, các lời gọi hàm từ ứng dụng sẽ **trả về phản hồi ngay lập tức** thay vì bắt luồng xử lý (Thread) phải dừng lại và ngồi chờ (Blocked).

- **Blocking I/O:** Ứng dụng gọi hàm $\rightarrow$ Thread bị đóng băng, OS gỡ OS Thread ra khỏi CPU $\rightarrow$ Chờ dữ liệu xong $\rightarrow$ OS Thread chạy tiếp.
- **Non-blocking I/O:** Ứng dụng gọi hàm $\rightarrow$ Trả về kết quả ngay (thành công hoặc báo "chưa xong") $\rightarrow$ Thread rảnh tay làm việc khác $\rightarrow$ Xử lý dữ liệu sau khi nhận được tín hiệu sẵn sàng từ OS.

---

## 2. Mô hình hoạt động bên dưới Hệ điều hành (OS)

- **Trong Blocking I/O:** Khi Thread bị blocked, OS sẽ thực hiện **Context Switch** để nhường CPU cho tác vụ khác. Tuy nhiên, nếu có hàng ngàn kết nối, việc tạo hàng ngàn Thread sẽ gây ngốn RAM dữ dội (mỗi Thread tốn ~1MB Stack) và CPU bị quá tải do phải đổi chỗ (Context Switching Overhead) liên tục.
- **Trong Non-blocking I/O:** Hệ thống thường kết hợp với cơ chế **I/O Multiplexing** (`epoll` trên Linux). Chỉ cần **một hoặc một vài Thread** (ví dụ Event Loop) đăng ký danh sách hàng ngàn socket với Kernel. Khi nào socket nào có dữ liệu, Kernel mới báo về để Thread đó xử lý.

---

## 3. Bảng so sánh nhanh

| Tiêu chí                      | Blocking I/O                                       | Non-blocking I/O                                          |
| :---------------------------- | :------------------------------------------------- | :-------------------------------------------------------- |
| **Trạng thái luồng (Thread)** | Bị treo (Blocked) cho đến khi hoàn thành.          | Luôn sẵn sàng (Runnable), không bị block.                 |
| **Tài nguyên (RAM/CPU)**      | Tốn nhiều RAM tạo Thread, tốn CPU để đổi ngữ cảnh. | Tiết kiệm tài nguyên, một Thread cân hàng vạn kết nối.    |
| **Độ phức tạp mã nguồn**      | Thẳng hàng, tuần tự, dễ viết và dễ đọc.            | Phức tạp hơn, cần xử lý qua Callback/Promise/Async-Await. |
| **Ứng dụng thực tế**          | PHP (truyền thống), Java IO cũ.                    | Node.js, Nginx, Java NIO, Netty.                          |

---

## 4. Tóm tắt ưu điểm cốt lõi

1. **Triệt tiêu chi phí Context Switch:** Giúp CPU tập trung vào việc xử lý logic thay vì quản lý luồng.
2. **Khả năng mở rộng tối đa (Scalability):** Lời giải cho bài toán **C10K** (10,000 kết nối đồng thời trên một Server).
3. **Tiết kiệm RAM:** Loại bỏ việc phải nuôi hàng ngàn Thread nhàn rỗi.

---

> **Mở rộng kiến thức:** Hiện nay, **Virtual Threads** (như Project Loom trong Java hiện đại) cho phép lập trình viên viết code theo phong cách Blocking trực quan, nhưng bên dưới OS vẫn chạy tối ưu theo cơ chế Non-blocking siêu nhẹ.
