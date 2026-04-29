# Collection Framework - Tài liệu tham khảo

## List: Danh sách có thứ tự

- Cho phép phần tử trùng nhau
- Có thứ tự (index từ 0)
- Truy cập phần tử bằng chỉ số (list.get(0))
- `new ArrayList<>()` là tạo 1 đối tượng trong heap

### Các class phổ biến:
- **ArrayList** - Nhanh khi truy cập, có lưu số thứ tự chính xác của mỗi phần tử
- **LinkedList** - Nhanh khi thêm xóa giữa danh sách, chỉ lưu thứ tự thêm vào

### Sự khác nhau của ArrayList và LinkedList

#### ArrayList - Như hàng ghế xếp sát nhau
Tưởng tượng có 1 dãy ghế dài trong lớp học. Mỗi ghế đều đánh số từ 0, 1, 2, … nên muốn tìm "ghế số 5" thì chỉ cần nhảy thẳng đến.

- **Truy cập cực nhanh**, vì có số thứ tự rõ ràng
- **Nhưng nếu muốn thêm 1 người vào giữa** (ví dụ ghế số 2 và số 3) thì phải dịch mông tất cả mọi người đằng sau lên 1 chỗ
- **Thêm xóa ở giữa chậm**, vì dịch các phần tử phía sau sang phải hoặc trái, chậm khi phần tử đầy phải resize mảng
- Nếu không chỉ định kích thước thì mặc định sẽ là 10. Khi thêm quá 10 phần tử nó sẽ tự động resize mảng
- Nếu không muốn resize mảng thì có thể thêm kích thước ban đầu là 100 để hạn chế
    - [Chi tiết resize](#resize-arraylist) 

#### LinkedList - Như chuỗi người nắm tay
Tưởng tượng một nhóm người đứng nối đuôi nhau, mỗi người nắm tay người trước và người sau.

- Nếu muốn chèn người mới vào giữa, chỉ cần bỏ tay người nắm ra nắm tay người mới là xong
- **Thêm xóa ở giữa rất tiện**, vì chỉ cần cập nhật 2 đầu liên kết với phần tử phía trước và sau là được
- **Nhưng nếu muốn tìm người số 10**, thì phải đếm từ người đầu tiên tới người thứ 10 khá mất thời gian
- **Truy cập chậm** vì phải duyệt danh sách do không có vị trí cụ thể

Trong LinkedList mỗi phần tử là một node:
- Mỗi node chứa 1 data (dữ liệu ví dụ 5)
- Liên kết tới node tiếp theo (next)
- (Nếu là danh sách liên kết đôi - double linked list - thì còn prev nữa)

### Khi nào dùng cái nào

| Tình huống | Dùng gì? | Vì sao |
|------------|----------|--------|
| Cần truy cập nhanh theo số (Vị trí) | ArrayList | Vì có thể nhảy thẳng đến |
| Cần chèn xóa liên tục ở đầu hoặc giữa | LinkedList | Vì chỉ cần thay đổi tay nắm |
| Dữ liệu lớn, cần tiết kiệm bộ nhớ | ArrayList | Vì không lưu thêm thứ tự |
| Dùng hàng đợi Queue hoặc ngăn xếp Stack | LinkedList | Vì thêm xóa đầu cuối nhanh |

---

## Set: Tập hợp không trùng

- Không cho phép phần tử trùng
- Không có thứ tự (trừ khi dùng LinkedHashSet)

### Các class phổ biến:
- **HashSet** - Nhanh, không thứ tự
- **LinkedHashSet** - Có thứ tự thêm vào
- **TreeSet** - Sắp xếp tự động theo thứ tự tự nhiên (class phải impl Comparable) hoặc theo Comparator

### Sự khác nhau giữa HashSet, TreeSet, LinkedHashSet

#### HashSet
- **Cấu trúc dữ liệu**: Dựa trên bảng băm [hash table](#Hash-table)
- **Thứ tự phần tử**: Không đảm bảo, có thể thay đổi bất kỳ lúc nào
- **Hiệu suất**: Tìm kiếm, thêm, xóa phần tử nhanh (trung bình là O(1))
- **Điểm nổi bật**: Nhanh nhất trong 3 loại nếu không quan tâm đến thứ tự

#### TreeSet
- **Cấu trúc dữ liệu**: Dựa trên cây nhị phân cân bằng [Red-Black tree](#2.-Red-Black-Tree-(Java-8+))
- **Thứ tự phần tử**: Tự động sắp xếp tăng dần theo thứ tự tự nhiên (natural ordering) hoặc theo Comparator nếu cung cấp
- **Hiệu suất**: Thêm, xóa, tìm kiếm mất O(log n)
- **Điểm nổi bật**: Dùng khi cần sắp xếp tự nhiên hoặc có thứ tự cụ thể
- Không được null nếu không có comparator xử lý null

#### LinkedHashSet
- **Cấu trúc dữ liệu**: Kết hợp giữa Hash Table và Linked List
- **Thứ tự phần tử**: Duy trì thứ tự thêm vào (insertion order)
- **Hiệu suất**: Gần bằng HashSet, chỉ chậm hơn chút do quản lý thêm thứ tự
- **Điểm nổi bật**: Dùng khi cần nhớ thứ tự đã thêm vào

---

## Map: Ánh xạ Key-Value

- Dùng để lưu dữ liệu theo cặp khóa-giá trị
- Key không được trùng, nhưng value có thể trùng

### Các class phổ biến:
- **HashMap** - Nhanh, không có thứ tự
- **LinkedHashMap** - Có thứ tự thêm vào
- **TreeMap** - Sắp xếp theo key

### Sự khác nhau của HashMap, LinkedHashMap, TreeMap

#### HashMap
- **Cấu trúc dữ liệu**: Dựa trên bảng băm (hash table)
- **Hiệu suất**: Tốt nhất trong các phép toán put(), get() với thời gian trung bình là O(1) (nghĩa là rất nhanh)
- **Null**: Cho phép một key null và một value null
- **Khi dùng**: Thường được sử dụng khi bạn không quan tâm đến thứ tự các phần tử, và chỉ muốn thao tác nhanh với bộ dữ liệu

#### TreeMap
- **Cấu trúc dữ liệu**: Dựa trên cây nhị phân tìm kiếm (Red-Black tree)
- **Thứ tự**: Sắp xếp tự động các phần tử theo thứ tự tự nhiên của key (ví dụ: theo thứ tự chữ cái với String, hoặc theo thứ tự tăng dần với Integer), hoặc theo một Comparator nếu bạn cung cấp
- **Hiệu suất**: Các phép toán put(), get(), remove() có thời gian O(log n) do phải duyệt cây
- **Null**: Không cho phép key null, nhưng có thể có value null
- **Khi dùng**: Dùng khi bạn cần đảm bảo thứ tự của các phần tử theo một tiêu chí nào đó (ví dụ: thứ tự tăng dần của key)

#### LinkedHashMap
- **Cấu trúc dữ liệu**: Dựa trên HashMap nhưng duy trì thêm một danh sách liên kết (LinkedList) để lưu trữ thứ tự của các phần tử
- **Thứ tự**: Duy trì thứ tự thêm vào (thứ tự phần tử được đưa vào). Các phần tử sẽ được duyệt theo đúng thứ tự bạn đã thêm vào
- **Hiệu suất**: Tương đương với HashMap trong các phép toán put(), get(), nhưng có chút overhead vì phải duy trì thứ tự
- **Null**: Cho phép một key null và một value null
- **Khi dùng**: Dùng khi bạn cần một Map nhưng vẫn muốn duy trì thứ tự chèn các phần tử

---

## Bảng Độ Phức Tạp Thời Gian (Time Complexity)

### List Operations

| Collection | Add (đầu/cuối) | Add (giữa) | Get (bằng index) | Remove (đầu/cuối) | Remove (giữa) | Contains |
|------------|----------------|------------|------------------|-------------------|---------------|-----------|
| **ArrayList** | O(1) amortized | O(n) | **O(1)** | O(n) | O(n) | O(n) |
| **LinkedList** | **O(1)** | **O(1)** | O(n) | **O(1)** | **O(1)** | O(n) |

### Set Operations

| Collection | Add | Remove | Contains | Size | Clear |
|------------|-----|--------|----------|------|-------|
| **HashSet** | **O(1)** | **O(1)** | **O(1)** | O(1) | O(n) |
| **LinkedHashSet** | **O(1)** | **O(1)** | **O(1)** | O(1) | O(n) |
| **TreeSet** | O(log n) | O(log n) | O(log n) | O(1) | O(n) |

### Map Operations

| Collection | Put (thêm/sửa) | Get (lấy theo key) | Remove (xóa theo key) | ContainsKey | ContainsValue |
|------------|----------------|---------------------|----------------------|-------------|----------------|
| **HashMap** | **O(1)** | **O(1)** | **O(1)** | **O(1)** | O(n) |
| **LinkedHashMap** | **O(1)** | **O(1)** | **O(1)** | **O(1)** | O(n) |
| **TreeMap** | O(log n) | O(log n) | O(log n) | O(log n) | O(n) |

### Queue Operations (LinkedList as Queue/Deque)

| Operation | Time Complexity |
|-----------|-----------------|
| addFirst / addLast | O(1) |
| removeFirst / removeLast | O(1) |
| getFirst / getLast | O(1) |
| peek / poll | O(1) |

### Chú thích:
- **O(1)**: Hằng số - thời gian thực hiện không phụ thuộc vào số lượng phần tử (rất nhanh)
- **O(n)**: Tuyến tính - thời gian tăng tỷ lệ thuận với số lượng phần tử
- **O(log n)**: Logarithmic - thời gian tăng chậm hơn so với số lượng phần tử
- **amortized**: Trung bình - đôi khi có thể chậm hơn (ví dụ khi resize mảng) nhưng trung bình vẫn là O(1)

## Resize ArrayList

- **Resize ArrayList** xảy ra khi mảng bên trong bị đầy (không còn chỗ để thêm phần tử mới)

### Cách hoạt động:
1. Tạo mảng mới có kích thước lớn hơn (Java 8: ~1.5 lần mảng cũ)
2. Copy toàn bộ phần tử từ mảng cũ sang mảng mới
3. Thêm phần tử mới vào mảng mới

### Độ phức tạp:
- Thêm bình thường: O(1)
- Khi resize: O(n) (do phải copy toàn bộ phần tử)

👉 Vì vậy:
- **ArrayList có độ phức tạp amortized O(1)** cho thao tác add

### Ví dụ:
```java
List<Integer> list = new ArrayList<>(2);
list.add(1);
list.add(2);

// Lúc này mảng đầy → resize
list.add(3);
```

Best practice:
- Nếu biết trước số lượng phần tử → nên khởi tạo sẵn capacity:
```java
List<Integer> list = new ArrayList<>(1000);
```
→ Giảm số lần resize → tối ưu performance

## Hash table
- **Hash table** là cấu trúc dữ liệu lưu trữ key -> value, cho phép truy cập rất nhanh (gần O(1))

---

### Vì sao gọi là "bảng băm"?

- "Bảng" → vì dữ liệu được lưu trong **một mảng (table/array)**
- "Băm" → vì sử dụng **hash function (hàm băm)** để chuyển key thành một số (index)

👉 Nói đơn giản:
- Key bất kỳ → qua hàm băm → ra vị trí trong mảng

---

### Cách hoạt động

Giả sử ta có một bảng (array) kích thước 16:

```text
index:   0   1   2   3   4   ... 15
value:  [ ] [ ] [ ] [ ] [ ] ... [ ]
```
Bước 1: Thêm phần tử
``` java
map.put("A", 1);
```
* Tính hash:
```
hash("A") = hashCode của "A" (ví dụ minh họa: 65)
```
* Tính index:
```
index = (capacity - 1) & hash
Ví dụ: 
capacity = 16 → (16 - 1) = 15
index = 15 & hash
```
* Lưu vào:
```
array[1] = ("A", 1)
```

Bước 2: Lấy phần tử
``` java
map.get("A");
```
* Tính lại hash:
```
hash("A") = 65 → index = 1
```
* Lấy ngay:
```
array[1] → ("A", 1)
```

### ⚠️ Collision (đụng độ)

Collision xảy ra khi:
- 2 key khác nhau
- nhưng có cùng index trong mảng

Ví dụ:
``` java
hash("A") → index 1
hash("B") → index 1
```
Không ghi đè, mà lưu cùng một bucket

---

### Cách xử lý collision

#### 1. Linked List (Java 7)
array[1] → (A,1) → (B,2)
---
#### 2. Red-Black Tree (Java 8+)

Nếu số phần tử trong bucket lớn:
array[1] → tree structure  
Giúp giảm từ O(n) → O(log n)

- Red-Black Tree là một cây nhị phân tìm kiếm (BST) tự cân bằng

Nghĩa là:
- Có dạng cây (tree)
- Luôn giữ cân bằng tương đối
- Giúp thao tác nhanh: O(log n)

Mục đích:
- Tránh trường hợp LinkedList bị dài → tìm kiếm chậm O(n)
- Đảm bảo hiệu năng ổn định khi có nhiều collision

Cách hoạt động (hiểu đơn giản):
- Mỗi node có màu: đỏ hoặc đen
- Khi thêm/xóa phần tử:
  - Nếu cây bị lệch → sẽ **xoay (rotate) lại**
  - Nếu vi phạm quy tắc → sẽ **đổi màu**
    → giúp cây không bị lệch quá

Trong HashMap:
- Khi số phần tử trong 1 bucket ≥ 8 → chuyển từ LinkedList → Red-Black Tree
- Khi ít lại (≤ 6) → có thể chuyển lại thành LinkedList

Kết luận:
- LinkedList → đơn giản nhưng chậm khi nhiều phần tử
- Red-Black Tree → phức tạp hơn nhưng đảm bảo luôn nhanh O(log n)

### 3. Bucket là gì?

- **Bucket** là một “ô” trong mảng của hash table
- Mỗi bucket chứa **0 hoặc nhiều phần tử** có cùng index
- Ô nào bị collision sẽ lưu các phần tử bằng LinkedList (Java 7) hoặc Tree (Java 8+)

## Note
1. Hash table giúp tối ưu việc tìm kiếm phần tử từ O(n) xuống O(1), trong khi với ArrayList, việc tìm kiếm (contains) vẫn là O(n) dù get theo index là O(1).
2. HashMap có độ phức tạp trung bình O(1), nhưng trong trường hợp nhiều key bị collision và rơi vào cùng bucket, nếu dùng LinkedList thì có thể lên O(n). Tuy nhiên từ Java 8, khi số phần tử trong bucket vượt ngưỡng, nó sẽ chuyển sang Red-Black Tree nên giảm xuống O(log n).
3. HashMap sẽ treeify khi bucket có ≥ 8 phần tử và table size ( kich thuoc mang .sỉze()) ≥ 64. Nếu table nhỏ hơn thì sẽ ưu tiên resize thay vì chuyển sang tree.
4. >= 8 (chuyen qua tree); <= 6 (chuyen ve linked list); >=64 doi qua tree
5. 