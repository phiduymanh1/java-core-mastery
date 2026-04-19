# Java Access Modifiers (Mức độ truy cập)

## 1. Access Modifiers cho Top-level Class (Lớp cấp cao)

Trong Java, class ở cấp cao (top-level class) chỉ có **2 mức độ truy cập**:

### Public
- Có thể truy cập từ mọi nơi (bất kỳ package nào)
- Ví dụ:
```java
// File: MyClass.java
public class MyClass {
    // Có thể được truy cập từ bất kỳ đâu
}
```

### Default (Package-private)
- Chỉ có thể truy cập từ các class trong cùng package
- Không cần từ khóa access modifier (không ghi gì trước class)
- Ví dụ:
```java
// File: MyClass.java
class MyClass {
    // Chỉ có thể truy cập từ các class cùng package
}
```

### ⚠️ Lưu ý quan trọng:
- **Top-level class KHÔNG được là private, protected, hay static**
- Nếu cố tình viết sẽ báo lỗi biên dịch:
```java
// ❌ Lỗi biên dịch
private class MyClass { }

// ❌ Lỗi biên dịch
protected class MyClass { }

// ❌ Lỗi biên dịch (top-level class không thể static)
static class MyClass { }
```

---

## 2. Access Modifiers cho thành viên trong Class

Các thành viên của class (fields, methods, constructors, inner classes) có **4 mức độ truy cập**:

### Private
- Chỉ có thể truy cập trong cùng class hiện tại
- Các class bên ngoài (kể cả class con) không thể truy cập
- Ví dụ:
```java
public class MyClass {
    private int privateField = 10;
    
    private void privateMethod() {
        // Chỉ có thể gọi trong MyClass
    }
}
```

### Default (Package-private)
- Có thể truy cập từ các class khác trong cùng package
- Không cần từ khóa access modifier
- Không thể truy cập từ class ở package khác
- Ví dụ:
```java
public class MyClass {
    int defaultField = 20;
    
    void defaultMethod() {
        // Có thể truy cập từ class cùng package
    }
}
```

### Protected
- Có thể truy cập trong cùng package
- Có thể truy cập từ class con (subclass) - kể cả khác package
- Ví dụ:
```java
package org.example.package1;

public class ParentClass {
    protected int protectedField = 30;
    
    protected void protectedMethod() {
        // ...
    }
}

// Package khác
package org.example.package2;

public class ChildClass extends ParentClass {
    public void demo() {
        protectedMethod(); // ✅ Có thể truy cập vì là class con
        System.out.println(protectedField); // ✅ Có thể truy cập
    }
}
```

### Public
- Có thể truy cập từ bất cứ đâu
- Mức độ truy cập rộng nhất
- Ví dụ:
```java
public class MyClass {
    public int publicField = 40;
    
    public void publicMethod() {
        // Có thể truy cập từ bất kỳ đâu
    }
}
```

---

## 3. Bảng tóm tắt mức độ truy cập

| Modifier | Class | Package | Subclass (cùng package) | Subclass (khác package) | World |
|----------|-------|---------|-------------------------|-------------------------|-------|
| **public** | ✅ | ✅ | ✅ | ✅ | ✅ |
| **protected** | ✅ | ✅ | ✅ | ✅ | ❌ |
| **default** | ✅ | ✅ | ✅ | ❌ | ❌ |
| **private** | ✅ | ❌ | ❌ | ❌ | ❌ |

---

## 4. Bổ sung

### Inner Classes
- Inner classes (class lồng nhau) có thể sử dụng tất cả 4 access modifiers
- Nested static class cũng có thể sử dụng tất cả 4 access modifiers

### Constructors
- Constructor cũng tuân theo các quy tắc access modifiers giống như methods
- Private constructor được sử dụng cho Singleton pattern hoặc để ngăn việc tạo instance từ bên ngoài

### Best Practices
- Sử dụng mức độ truy cập hẹp nhất có thể (principle of least privilege)
- Mặc định nên dùng `private` cho fields, chỉ dùng public/private methods qua getter/setter
- Chỉ dùng `public` khi thực sự cần thiết
- `protected` thường dùng cho methods cần được override bởi class con
