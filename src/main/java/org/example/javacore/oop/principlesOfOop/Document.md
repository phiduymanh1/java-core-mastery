# 4 Tính Chất Cơ Bản Của OOP

## 1. Tính Đóng Gói (Encapsulation)

![Encapsulation](https://statics.cdn.200lab.io/2023/08/oop-4-tinh-chat.jpg)

### Tính đóng gói là gì?

Đóng gói là quá trình ẩn giấu dữ liệu và chỉ cho phép truy cập, sửa đổi thông qua các phương thức (method) được cung cấp. Ví dụ: getter và setter.

Nói cách khác:
> "Không cho ai đụng trực tiếp vào dữ liệu bên trong, muốn làm gì thì phải thông qua cánh cửa do mình tạo ra."

### Vì sao cần đóng gói?

- **Bảo vệ dữ liệu** bên trong đối tượng khỏi bị sửa đổi bừa bãi.
- **Kiểm soát cách dữ liệu** được truy cập và thay đổi.
- **Giúp phần mềm dễ bảo trì**, an toàn hơn.

### Nguyên tắc giấu thông tin là gì?

Chỉ cung cấp ra bên ngoài các thứ cần thiết, còn lại giấu đi.

Trong lập trình:
- Giấu thông tin là không để lộ chi tiết nội bộ của class (biến, cách xử lý, logic...)
- Những thứ như biến nội bộ, cách tính toán, mã hóa, logic nghiệp vụ phức tạp... nên được ẩn khỏi lớp ngoài.
- Thay vào đó, ta cung cấp giao diện công khai (getter, setter, method) để tương tác an toàn.

### Cách triển khai trong Java

**Quy tắc:**
- Dùng `private` cho thuộc tính.
- Tạo `public` getter/setter để truy cập hoặc thay đổi dữ liệu.

➡️ Từ bên ngoài, bạn không thể truy cập trực tiếp vào thuộc tính của class đó thông qua đối tượng, mà phải thông qua getter/setter.

### Nếu đã cho phép chỉnh sửa qua setter rồi, vậy còn đóng gói để làm gì nữa?

Tính đóng gói không cấm truy cập mà chỉ kiểm soát cách truy cập.

**Ví dụ:**
Class `XeHoi` có thuộc tính `name`:
- Nếu để `name` là `public` thì người dùng có thể gọi qua đối tượng là `xeHoi.name = null`
- Nếu để `private` và chỉ cho truy cập qua Getter/Setter thì có thể thêm điều kiện `if name != null` để tránh lỗi

---

## 2. Tính Kế Thừa (Inheritance)

### Kế thừa là gì?

Kế thừa (Inheritance) là cơ chế cho phép một class (lớp con) thừa hưởng các thuộc tính và phương thức từ một class khác (lớp cha).

Hay nói cách khác: Lớp con mở rộng (extends) lớp cha và có thể tái sử dụng hoặc ghi đè các thành phần đó.

### Lợi ích

- **Tái sử dụng code**: không cần viết lại những gì đã có
- **Tính năng tổ chức**: phân chia các class rõ ràng theo cấp bậc
- **Dễ mở rộng**: có thể thêm tùy chỉnh mà không phá vỡ class cũ
- **Hỗ trợ đa hình**: gọi hàm thông qua lớp cha nhưng thực hiện theo lớp con

### Ghi đè phương thức @Override

- Java chỉ hỗ trợ kế thừa: 1 lớp con extend 1 lớp cha
- Để kế thừa nhiều hành vi, dùng interface (implements)
- 1 class có thể implements nhiều interface
- Dùng từ khóa `super` để gọi đến các thành phần của lớp cha

---

## 3. Tính Đa Hình (Polymorphism)

### Tính đa hình là gì?

**Poly** (nhiều) + **Morphism** (hình thái)

Là khả năng một đối tượng có thể hành xử theo nhiều cách khác nhau, tùy thuộc vào ngữ cảnh.

Một phương thức có thể có nhiều cách thực thi khác nhau, tùy thuộc vào đối tượng thực tế đang gọi nó.

### Có 2 kiểu tính đa hình

#### 3.1. Static Polymorphism (Compile-time)

- **Tên gọi**: Static polymorphism
- **Xảy ra khi**: biên dịch
  - **Thường là**: method overloading
- **Dùng khi**: Khi nhiều phương thức cùng tên nhưng khác tham số, thì trình biên dịch chọn đúng phương thức dựa vào kiểu dữ liệu, số lượng tham số.

**Ví dụ:**
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

#### 3.2. Dynamic Polymorphism (Runtime)

- **Tên gọi**: Dynamic polymorphism
- **Xảy ra khi**: chạy chương trình
- **Thường là**: method overriding
- Khi một lớp con ghi đè (override) phương thức của lớp cha, và ta gọi qua kiểu dữ liệu lớp cha, thì chương trình sẽ xác định hành vi thật sự tại runtime.

**Ví dụ:**
```java
class Animal {
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal myDog = new Dog();
        Animal myCat = new Cat();
        
        myDog.makeSound(); // Output: Dog barks
        myCat.makeSound(); // Output: Cat meows
    }
}
```

---

## 4. Tính Trừu Tượng (Abstraction)

### Abstraction là gì?

Abstraction (Trừu tượng) là khả năng che giấu chi tiết thực thi, chỉ hiển thị những gì cần thiết cho người dùng.

Tưởng tượng như bạn lái xe – bạn chỉ cần biết vặn chìa khóa để nổ máy, không cần biết bên trong động cơ hoạt động ra sao.

**Tập trung vào "cái gì" làm được, ẩn đi "làm như thế nào".**

### Lợi ích của Abstraction

- Giảm độ phức tạp của code
- Tăng khả năng tái sử dụng
- Dễ bảo trì và mở rộng
- Tách biệt giữa interface và implementation

### Cách triển khai Abstraction trong Java

Java có 2 cách chính:

#### 4.1. Abstract Class

**Ví dụ Abstract class:**
```java
public abstract class Animal {
    private String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    // Abstract method - không có body
    public abstract void makeSound();
    
    // Concrete method - có body
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    public String getName() {
        return name;
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}
```

#### 4.2. Interface

**Ví dụ Interface:**
```java
public interface Drawable {
    void draw();
    void resize(int width, int height);
}

class Circle implements Drawable {
    private int radius;
    
    public Circle(int radius) {
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius " + radius);
    }
    
    @Override
    public void resize(int width, int height) {
        this.radius = Math.min(width, height) / 2;
    }
}
```

### So sánh trừu tượng và đóng gói

| Đặc điểm | Abstraction | Encapsulation |
|----------|-------------|---------------|
| **Mục tiêu** | Ẩn chi tiết không cần thiết | Giấu dữ liệu, bảo vệ thông tin |
| **Tập trung vào** | Hành vi (methods) | Dữ liệu (fields) |
| **Dùng cái gì?** | Abstract class, Interface | Access modifier (private, public...) |

**Tóm tắt:**
- **Tính đóng gói** là bảo vệ dữ liệu các field
- **Tính trừu tượng** là không định nghĩa cách hoạt động của method mà cho lớp con tự định nghĩa

**Ví dụ:**
- Con vật phải di chuyển
- Nhưng chim bơi, cá bay thì chưa biết
- Nên phải để lớp con tự định nghĩa

---

## 5. Composition (Tính Kết Hợp/Hợp Thành)

### Composition là gì?

Composition là một mối quan hệ giữa các đối tượng, trong đó một đối tượng lớn được tạo thành từ các đối tượng nhỏ hơn. Trong lập trình, người ta gọi đây là quan hệ "Has-a" (Có một).

**Ví dụ:**
- Một `Car` (xe hơi) có một `Engine` (động cơ)
- Một `Computer` (máy tính) có một `CPU`, `RAM`, `Hard Drive`
- Một `Team` (đội) có nhiều `Member` (thành viên)

### Ví dụ trong Java:

```java
class Engine {
    private String type;
    private int horsepower;
    
    public Engine(String type, int horsepower) {
        this.type = type;
        this.horsepower = horsepower;
    }
    
    public void start() {
        System.out.println("Engine starting...");
    }
}

class Car {
    private String brand;
    private Engine engine; // Composition: Car "has-a" Engine
    
    public Car(String brand, Engine engine) {
        this.brand = brand;
        this.engine = engine;
    }
    
    public void startCar() {
        System.out.println(brand + " is starting...");
        engine.start();
    }
}

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine("V8", 450);
        Car car = new Car("BMW", engine);
        car.startCar();
    }
}
```

### Composition vs Inheritance

| Đặc điểm | Composition (Has-a) | Inheritance (Is-a) |
|----------|-------------------|-------------------|
| **Quan hệ** | Một đối tượng chứa đối tượng khác | Một lớp là loại của lớp khác |
| **Độ耦合** | Lỏng lẻo (loose coupling) | Chặt chẽ (tight coupling) |
| **Thay đổi runtime** | Có thể thay đổi | Không thể thay đổi |
| **Tái sử dụng** | Linh hoạt hơn | Hạn chế hơn |

**Khi nào dùng Composition thay vì Inheritance?**
- Khi bạn cần linh hoạt thay đổi hành vi tại runtime
- Khi bạn muốn tránh vấn đề của đa kế thừa (Java không hỗ trợ)
- Khi quan hệ giữa các đối tượng không phải là "is-a" mà là "has-a"
