## 1. Thread giải quyết nỗi đau gì của cách làm truyền thống?

Trong cách làm truyền thống (single-thread):

- Chương trình chỉ làm **1 việc tại 1 thời điểm**.
- Nếu một tác vụ bị chậm (gọi DB, gọi API, đọc file...), toàn bộ luồng xử lý phải chờ.
- CPU bị lãng phí vì thời gian chờ I/O rất lớn.

Ví dụ Backend:

```text
Request A -> gọi DB (mất 2s)
            -> xử lý tiếp

Request B -> phải chờ
Request C -> phải chờ
```

Thread xuất hiện để:

- Xử lý nhiều công việc đồng thời.
- Tận dụng CPU tốt hơn.
- Tăng throughput của hệ thống.
- Giảm thời gian chờ của người dùng.

Ví dụ:

```text
Thread 1 -> xử lý Request A
Thread 2 -> xử lý Request B
Thread 3 -> xử lý Request C
```

## 2. 3-5 thành phần cốt lõi nhất

### 1. Thread

Đơn vị thực thi nhỏ nhất trong process.

Hiểu đơn giản:

```text
Process
 ├─ Thread 1
 ├─ Thread 2
 └─ Thread 3
```

Mỗi thread có:

- Program Counter
- Stack riêng

Nhưng dùng chung:

- Heap
- Object
- Biến static

### 2. Runnable (Task)

Công việc cần thực hiện.

Ví dụ về mặt kiến trúc:

```text
Task:
- Gửi email
- Sinh báo cáo
- Xử lý request
- Đồng bộ dữ liệu
```

Thread chỉ là "người làm".

Task mới là "việc phải làm".

### 3. Thread Pool ([Document Thread Pool](./thread_pool/DocumentThreadPool.md))

Nơi quản lý nhiều thread.

Thay vì:

```text
Request tới
=> tạo Thread mới
=> huỷ Thread
```

Thread Pool:

```text
Thread 1
Thread 2
Thread 3
```

được tạo sẵn và tái sử dụng.

Backend hiện đại gần như đều dùng Thread Pool.

### 4. Queue (Hàng đợi) ([Document Queue](../collection_framework/DocumentQueue.md))

Nơi chứa các Task đang chờ xử lý.

```text
Task A
Task B
Task C
    ↓
  Queue
    ↓
Thread Pool
```

Thread rảnh sẽ lấy task từ queue ra chạy.

### 5. Synchronization

Cơ chế bảo vệ dữ liệu dùng chung.

Vì nhiều thread cùng truy cập:

```text
Account.balance
```

nên có thể xảy ra:

```text
Thread A sửa
Thread B sửa
```

gây sai dữ liệu.

Synchronization giúp:

- Đồng bộ
- Tránh race condition
- Đảm bảo tính nhất quán dữ liệu

### 6. Data Flow (luồng đi của dữ liệu)

Ví dụ một request backend:

```text
Client gửi Request
        ↓
Server nhận Request
        ↓
Request được đưa vào Queue
        ↓
Thread Pool lấy một Thread rảnh
        ↓
Thread nhận Task xử lý Request
        ↓
Thread gọi Service
        ↓
Service gọi Database / API khác
        ↓
Kết quả trả về cho Thread
        ↓
Thread tạo Response
        ↓
Response trả về Client
        ↓
Thread quay lại Pool chờ Task mới
```

Tóm tắt bằng 1 dòng:

```text
Request
   ↓
Queue
   ↓
Thread Pool
   ↓
Thread
   ↓
Business Logic
   ↓
DB/API
   ↓
Response
   ↓
Thread quay về Pool
```

### 7. Phá hoại & Trace Bug

| No  | Kịch bản phá hoại             | Cách phá hoại                                                                                           | Exception bắn ra              | Từ khóa cốt lõi trong Log(Keyword)                                        |                             Cách xử lý nhanh                             |
| :-- | :---------------------------- | :------------------------------------------------------------------------------------------------------ | :---------------------------- | :------------------------------------------------------------------------ | :----------------------------------------------------------------------: |
| 1   | Bỏ AtomicInteger              | Bỏ AtomicInteger                                                                                        | No                            | Race Condition                                                            |    <ul><li>Sử dụng lại các lớp Atomic</li><li>synchronized</li></ul>     |
| 2   | Chia cho 0 trong lambda       | Chia cho 0 trong lambda                                                                                 | java.lang.ArithmeticException | ArithmeticException <br> ExecutionException <br> CompletionException      | <ul><li>Exceptionally</li> <li> Handle </li> <li> Try - catch </li></ul> |
| 3   | Thread Pool bị nghẽn          | Thread.sleep(60000)                                                                                     | AsyncRequestTimeoutException  | <ul><li>Task rejected</li><li>Thread starvation</li><li>Timeout</li></ul> |               Tăng timeout cấu hình trong application.yml                |
| 4   | Check-Then-Act Race Condition | Gửi 2 thread cùng 1 lúc với value = 100 làm cho 1 thread trừ số dư = 0. Thread còn lại trả về số dư -00 | No                            | <li>Check-Then-Act Race Condition</li><li>CAS loop</li>                   |                                 CAS loop                                 |

### 8.Executor Service

- **Bản chất:** Framework quản lý **Thread Pool** tự động, giúp tái sử dụng Thread, tránh quá tải hệ thống so với việc tạo `new Thread()` thủ công.
- **Khởi tạo (`Executors`):**
  - `newFixedThreadPool(n)`: Cố định `n` Thread.
  - `newCachedThreadPool()`: Tự co giãn số Thread theo lượng việc.
  - `newSingleThreadExecutor()`: Chỉ có 1 Thread, chạy tuần tự.
- **Gửi task:** \* `execute()`: Chạy không cần kết quả.
  - `submit()`: Trả về `Future<T>` để lấy kết quả sau đó.
- **Lưu ý:** Bắt buộc gọi `executor.shutdown()` khi dùng xong để tránh rò rỉ bộ nhớ (**Memory Leak**).

### 9.ThreadPoolTaskExecutor

- **Bản chất:** Là lớp bọc (wrapper) của Spring dựa trên `ThreadPoolExecutor` của Java thuần, giúp tích hợp mượt mà với Spring IoC Container và tự động đóng pool khi ứng dụng tắt.
- **Cách dùng chính:** Thường được cấu hình làm Spring Bean để làm bệ đỡ cho annotation `@Async`, giúp xử lý các tác vụ bất đồng bộ (chạy ngầm) như gửi email, export file.
- **Luồng xử lý:** Ưu tiên dùng hết `CorePoolSize` -> Đẩy vào `QueueCapacity` chờ -> Nếu queue đầy mới tạo thêm thread đến mức `MaxPoolSize` -> Quá tải sẽ kích hoạt chính sách từ chối.

### 10. Blocking I/O

Thread đang chờ

- Database
- REST API
- File
- Redis
- Kafka

=> Thread vẫn chiếm tài nguyên dù không làm gì.

Nếu có 1000 request

và mỗi request chờ DB 500ms

thì rất nhanh Thread Pool sẽ đầy.

#### 10.1. Non-blocking I/O ([Non blocking I/O](./blocking_io/DocumentNonBlockingIO.md))

### 11. Lock Contention
Nhiều Thread cùng muốn truy cập

- synchronized
- Lock
- Shared Object

=> tất cả phải xếp hàng.

CPU không bận làm việc.

CPU bận... chờ.

#### 11.1. Lock-free Programming
#### 11.2. CAS (Compare-And-Swap) 
- Tham khảo `repo letcode`


### 12. Context Switching
1000 Thread

chỉ có

8 CPU Core.

CPU phải đổi qua đổi lại giữa các Thread.

Đổi càng nhiều

=> càng mất thời gian.

Có lúc CPU dành nhiều thời gian chuyển Thread hơn là chạy business.

### 13. Best practive thread ([Best Practive doc](./BestPractiveThread.md))

# To learn later

* Program Counter (PC)
* Saturation
* Blocking I/O
    * Reactive Programming
    * Async I/O
    * Event Loop
* Lock Contention
    * Lock-free Programming
    * Atomic Variables
    * Concurrent Collections
* Context Switching
    * Context Switching
    * CPU Scheduling
    * Work Stealing
    * ForkJoinPool
