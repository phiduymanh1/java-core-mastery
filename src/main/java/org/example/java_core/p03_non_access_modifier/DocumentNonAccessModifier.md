# Non-Access Modifiers in Java

## Static

Khi khai báo 1 phương thức hoặc 1 thuộc tính với static thì:
- Nó thuộc về class, chứ không thuộc về instance (đối tượng)
- Có thể truy cập trực tiếp qua tên class mà không cần tạo đối tượng
- Hoặc truy cập qua import static thẳng vào biến/phương thức đó thì có thể gọi thẳng luôn trong class đang được import mà không cần gọi tên Class ra

**Static method:**
- Chiếm bộ nhớ khi class được load vào JVM, và được lưu trong vùng method area
- Chỉ tồn tại một bản duy nhất trong bộ nhớ và dùng chung cho tất cả các object
- Không thể truy cập được non-static members (biến/phương thức instance) trực tiếp
- Không thể dùng từ khóa `this` và `super` trong static method
- Không thể được override (chỉ có thể bị hide nếu subclass có static method cùng tên)

**Non-static method:**
- Chiếm bộ nhớ trong vùng heap, chiếm bộ nhớ khi object được khởi tạo
- Mỗi object có bản copy riêng của non-static members
- Có thể truy cập cả static và non-static members

**Static Block:**
- Static block là một khối lệnh static, được chạy 1 lần duy nhất khi class đó được load lần đầu tiên vào JVM (tức là khi bạn lần đầu dùng class đó)
- Thường dùng để khởi tạo dữ liệu static phức tạp (ví dụ đọc file config lúc khởi động chương trình)
- Static còn được dùng trong khối khởi tạo tĩnh (static initializer) để thực hiện các thao tác khởi tạo khi lớp được nạp, như cấu hình hoặc thiết lập dữ liệu mặc định
- Lần đầu class đó được sử dụng là lúc làm bất kì điều gì với class đó kể cả extend

**Static Import:**
```java
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

// Có thể dùng trực tiếp mà không cần Math.PI, Math.sqrt()
double result = sqrt(PI);
```

## Final

**Final** là từ khóa dùng để khai báo rằng một entity không thể thay đổi sau khi được khởi tạo/gán.

### Final Variables (Biến final)
- **Final local variable:** Phải được khởi tạo trước khi sử dụng, không thể thay đổi giá trị sau khi gán
- **Final instance variable:** Phải được khởi tạo khi khai báo, trong constructor, hoặc trong instance initializer block
- **Final static variable:** Phải được khởi tạo khi khai báo hoặc trong static block
- Khi dùng với reference type, chỉ có reference không thể thay đổi, nhưng nội dung object vẫn có thể thay đổi (trừ khi object đó immutable)

```java
final int x = 10;  // Không thể gán lại x
final List<String> list = new ArrayList<>();
list.add("item");  // OK - nội dung object có thể thay đổi
// list = new ArrayList<>();  // ERROR - không thể gán lại reference
```

### Final Methods
- Không thể bị override bởi subclass
- Dùng để prevent subclass từ việc thay đổi hành vi của method
- Constructor không thể final (vì constructor không được inherit)

```java
class Parent {
    public final void show() {
        System.out.println("Cannot be overridden");
    }
}
```

### Final Classes
- Không thể được extend (không có subclass)
- Tất cả methods trong final class đều implicitly final
- Dùng để prevent inheritance và đảm bảo class không bị modify

```java
final class Utility {
    // Cannot extend this class
}
```

### Final Parameters
- Parameter của method có thể khai báo final
- Không thể thay đổi giá trị của parameter bên trong method

```java
void process(final int value) {
    // value = 10;  // ERROR
}
```

## Abstract

**Abstract** là từ khóa dùng để khai báo abstraction - không thể tạo instance trực tiếp, dùng làm template cho các class con.

### Abstract Classes
- Không thể tạo instance trực tiếp với `new`
- Có thể có cả abstract và non-abstract methods
- Có thể có instance variables, constructors, static methods
- Subclass phải override tất cả abstract methods hoặc chính nó cũng phải là abstract
- Dùng khi muốn cung cấp implementation chung nhưng vẫn enforce subclass implement certain behaviors

```java
abstract class Animal {
    String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    // Abstract method - không có body
    abstract void makeSound();
    
    // Non-abstract method - có implementation
    void sleep() {
        System.out.println(name + " is sleeping");
    }
}
```

### Abstract Methods
- Chỉ có thể khai báo trong abstract class hoặc interface
- Chỉ có signature (khai báo), không có body (không có {})
- Subclass bắt buộc phải override abstract method
- Không thể là static, final, hoặc private (vì cần được override)

```java
abstract void makeSound();  // Chỉ khai báo, không có body
```

### Abstract vs Interface
- **Abstract class:** Có thể có state (instance variables), constructors, có thể có methods với implementation
- **Interface:** Trước Java 8: chỉ có abstract methods và constants. Từ Java 8: có default và static methods. Từ Java 9: có private methods
- Class có thể implement nhiều interfaces nhưng chỉ extend một abstract class

### Khi nào dùng Abstract Class
- Khi muốn chia sẻ code giữa các class liên quan chặt chẽ
- Khi cần các non-static/non-final fields (state)
- Khi cần sử dụng access modifier khác public
- Khi muốn khai báo methods với non-public access modifiers

### Khi nào dùng Interface
- Khi muốn define contract mà các class không liên quan có thể implement
- Khi muốn multiple inheritance type
- Khi muốn specify behavior mà không quan tâm đến implementation

### Bảng so sánh nhanh Abstract vs Interface

| Đặc điểm | Abstract Class | Interface |
|----------|----------------|-----------|
| **Inheritance** | Chỉ extend 1 class | Có thể implement nhiều interfaces |
| **State (Fields)** | Có instance variables (non-static, non-final) | Chỉ có constants (public static final) |
| **Constructor** | Có constructor | Không có constructor |
| **Methods** | Có thể có cả abstract và concrete methods | Trước Java 8: chỉ abstract. Java 8+: default, static. Java 9+: private |
| **Access Modifiers** | Có thể dùng các modifier (protected, private, etc.) | Methods mặc định public |
| **Performance** | Tốt hơn (nhưng khác biệt nhỏ) | Gọi interface method có overhead nhỏ |
| **Mục đích** | Chia sẻ code giữa các class liên quan chặt chẽ | Define contract cho các class không liên quan |

**Khi nào dùng Abstract Class:**
- Khi các class có mối quan hệ is-a (chó là animal)
- Khi muốn chia sẻ code giữa các class liên quan
- Khi cần non-static/non-final fields
- Khi cần access modifiers khác public
- Khi cần declare non-public methods

**Khi nào dùng Interface:**
- Khi các class có mối quan hệ can-do (bird can fly, airplane can fly)
- Khi muốn multiple inheritance
- Khi muốn define contract không quan tâm implementation
- Khi muốn thêm functionality đến class đã có (đã extend class khác)

## So sánh Static, Final, Abstract

| Modifier | Class | Method | Variable |
|----------|-------|--------|----------|
| **static** | Nested class có thể static | Thuộc về class, không cần instance | Thuộc về class, chia sẻ cho tất cả instances |
| **final** | Không thể extend | Không thể override | Không thể thay đổi giá trị sau khi gán |
| **abstract** | Không thể instantiate | Không có body, subclass phải override | Không áp dụng |