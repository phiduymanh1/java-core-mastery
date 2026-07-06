## Quy tắc 1: Không tự tạo Thread trong Business Logic nếu đã có Thread Pool

- **Thực tế:**
  - Tạo Thread là trách nhiệm của framework hoặc executor.
  - Business chỉ mô tả công việc cần làm.
- **Mindset:**
  - Business không quản lý Thread.

## Quy tắc 2: Không chia sẻ mutable state giữa nhiều Thread nếu không thật sự cần

- **Hệ quả nếu dùng Shared mutable object:**
  - Shared mutable object $\rightarrow$ Race Condition $\rightarrow$ Bug rất khó debug.
- **Ưu tiên sử dụng:**
  - Immutable object
  - Local variable
  - Thread-safe collection

## Quy tắc 3: Đặt tên Thread rõ ràng

- **Ví dụ nên đặt:**
  - `ImportWorker-1`
  - `EmailSender-2`
  - `SyncJob-5`
- **Không nên để:**
  - `Thread-1`
  - `Thread-2`
  - `Thread-45`
- **Lợi ích:** Khi hệ thống trên Production gặp lỗi, dữ liệu Log sẽ trực quan và dễ đọc hơn rất nhiều.
