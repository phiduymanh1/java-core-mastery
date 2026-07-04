# TÀI LIỆU CẤU TRÚC DỮ LIỆU: QUEUE (HÀNG ĐỢI)

## 1. Định nghĩa & Nguyên lý hoạt động
**Queue (Hàng đợi)** là một cấu trúc dữ liệu tuyến tính (Linear Data Structure) hoạt động nghiêm ngặt theo nguyên lý **FIFO (First In, First Out - Vào trước, Ra trước)**. 

Hiểu một cách đơn giản, phần tử nào được đưa vào hàng đợi đầu tiên thì cũng sẽ là phần tử đầu tiên được lấy ra khỏi hàng đợi (tương tự như việc xếp hàng mua vé ngoài đời thực).



---

## 2. Đặc điểm cấu trúc (Các thành phần cốt lõi)
* **Front (Đầu):** Vị trí quản lý phần tử nằm ở đầu hàng đợi. Mọi thao tác lấy dữ liệu ra hoặc xóa dữ liệu đều thực hiện tại đây.
* **Rear / Back (Đuôi):** Vị trí quản lý phần tử nằm ở cuối hàng đợi. Mọi thao tác chèn thêm dữ liệu mới đều thực hiện tại đây.
* **Size (Kích thước):** Tổng số lượng phần tử hiện đang có trong hàng đợi.

---

## 3. Các phương thức cơ bản (API Methods)

Mọi Queue chuẩn hóa đều phải hỗ trợ các phương thức dưới đây với độ phức tạp thời gian tối ưu là **$O(1)$**:

| Phương thức | Tham số | Kiểu trả về | Mô tả |
| :--- | :--- | :--- | :--- |
| `enqueue(item)` | `item` | `void` / `bool` | Thêm (chèn) một phần tử mới vào cuối hàng đợi (`Rear`). |
| `dequeue()` | Không | `Element` | Loại bỏ và trả về phần tử đang đứng ở đầu hàng đợi (`Front`). |
| `peek()` / `front()` | Không | `Element` | Xem giá trị của phần tử ở đầu hàng đợi (`Front`) mà **không xóa** nó. |
| `isEmpty()` | Không | `boolean` | Kiểm tra xem hàng đợi có đang trống hay không. |
| `size()` | Không | `int` | Trả về số lượng phần tử hiện tại trong hàng đợi. |

---

## 4. Các biến thể phổ biến của Queue

1.  **Circular Queue (Hàng đợi vòng):** Là hàng đợi mà phần tử cuối cùng nối ngược lại với phần tử đầu tiên tạo thành một vòng tròn. Nó giải quyết triệt để nhược điểm lãng phí bộ nhớ của hàng đợi tuyến tính dùng mảng tĩnh.
2.  **Priority Queue (Hàng đợi ưu tiên):** Các phần tử lấy ra không phụ thuộc vào việc ai đến trước, mà phụ thuộc vào **độ ưu tiên** (Priority) được gán kèm. Phần tử có độ ưu tiên cao nhất luôn được lấy ra trước. (Thường được tối ưu bằng cấu trúc dữ liệu **Heap**).
3.  **Deque (Double-ended Queue):** Hàng đợi hai đầu, cho phép thực hiện cả việc thêm (`enqueue`) và xóa (`dequeue`) một cách linh hoạt ở **cả hai đầu** Front và Rear.

---

## 5. Hiện thực hóa Queue (Implementation)

Bản chất của Queue là một phiên bản giới hạn quyền lực của List. Dưới đây là cách tự xây dựng một Class `Queue` bằng danh sách liên kết (`Linked List`) để đảm bảo hiệu năng $O(1)$.

### Mã nguồn minh họa (Java)
```java
public class CustomQueue<T> {
    // Định nghĩa cấu trúc của một Node nội bộ
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front; // Con trỏ quản lý đầu hàng
    private Node<T> rear;  // Con trỏ quản lý đuôi hàng
    private int currentSize = 0;

    // Kiểm tra hàng đợi rỗng
    public boolean isEmpty() {
        return front == null;
    }

    // Lấy số lượng phần tử
    public int size() {
        return this.currentSize;
    }

    // Thêm phần tử vào cuối (Enqueue)
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode; // Nối phần tử cũ vào phần tử mới
            rear = newNode;      // Cập nhật con trỏ rear sang vị trí mới
        }
        currentSize++;
    }

    // Lấy phần tử ở đầu ra xử lý và xóa bỏ (Dequeue)
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue Underflow: Hàng đợi đang trống!");
        }
        T data = front.data;
        front = front.next; // Di chuyển con trỏ front tiến lên một nấc
        
        if (front == null) {
            rear = null; // Nếu sau khi xóa mà hàng đợi trống thì xóa luôn rear
        }
        currentSize--;
        return data;
    }

    // Xem giá trị đầu hàng (Peek)
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Hàng đợi trống, không có dữ liệu để xem!");
        }
        return front.data;
    }
}
```
## 6. Lỗi hệ thống thường gặp cần lưu ý
* **Queue Overflow (Tràn hàng đợi):** Xảy ra khi bộ nhớ/sức chứa của Queue bị giới hạn (ví dụ dùng mảng tĩnh) nhưng bạn vẫn cố tình `enqueue` thêm phần tử vào khi hàng đã đầy.
* **Queue Underflow (Hàng đợi trống):** Xảy ra khi bạn cố tình gọi hàm `dequeue()` hoặc `peek()` khi trạng thái hàng đợi đang rỗng (`isEmpty() == true`).

## 7. Ứng dụng thực tế của Queue
* **Thuật toán:** Là thành phần cốt lõi bắt buộc phải có trong thuật toán duyệt đồ thị theo chiều rộng **BFS (Breadth-First Search)**.
* **Hệ điều hành:** Quản lý hàng đợi tiến trình (Process Scheduling), bộ đệm dữ liệu vòng của bàn phím/chuột, hàng đợi lệnh gửi đến máy in.
* **Thiết kế hệ thống (System Design):** Các hệ thống Message Broker xử lý bất đồng bộ chống quá tải dữ liệu lớn như **RabbitMQ, Apache Kafka, Amazon SQS**.