# SOLID Principles in Object-Oriented Programming

SOLID là một tập hợp 5 nguyên tắc thiết kế trong lập trình hướng đối tượng (OOP), giúp viết code dễ bảo trì, mở rộng, và tái sử dụng.

---

## S - Single Responsibility Principle (SRP)
**Nguyên tắc trách nhiệm duy nhất**

> Một class chỉ nên có một lý do để thay đổi, tức là chỉ có một trách nhiệm duy nhất.

### Ví dụ thực tế:

**❌ VI PHẠM SRP:**
```java
// Class này làm quá nhiều việc
public class UserService {
    public void registerUser(User user) {
        // Logic đăng ký
        validateUser(user);
        saveToDatabase(user);
        sendWelcomeEmail(user);
        logRegistration(user);
    }
    
    private void validateUser(User user) { /* validation logic */ }
    private void saveToDatabase(User user) { /* database logic */ }
    private void sendWelcomeEmail(User user) { /* email logic */ }
    private void logRegistration(User user) { /* logging logic */ }
}
```

**✌️ TUÂN THỦ SRP:**
```java
// Mỗi class chỉ có một trách nhiệm
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final LoggingService loggingService;
    
    public UserService(UserRepository userRepository, EmailService emailService, LoggingService loggingService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.loggingService = loggingService;
    }
    
    public void registerUser(User user) {
        validateUser(user);
        userRepository.save(user);
        emailService.sendWelcomeEmail(user);
        loggingService.logRegistration(user);
    }
    
    private void validateUser(User user) { /* validation logic */ }
}

public class UserRepository {
    public void save(User user) { /* database logic */ }
}

public class EmailService {
    public void sendWelcomeEmail(User user) { /* email logic */ }
}

public class LoggingService {
    public void logRegistration(User user) { /* logging logic */ }
}
```

---

## O - Open/Closed Principle (OCP)
**Nguyên tắc mở rộng - đóng gói**

> Phần mềm nên mở để mở rộng, nhưng đóng để sửa đổi.
> Khi muốn thêm tính năng mới, nên kế thừa hoặc mở rộng, không nên sửa trực tiếp class cũ.

### Ví dụ thực tế:

**❌ VI PHẠM OCP:**
```java
public class PaymentProcessor {
    public void processPayment(String paymentType, double amount) {
        if (paymentType.equals("CREDIT_CARD")) {
            // Logic xử lý thẻ tín dụng
            processCreditCard(amount);
        } else if (paymentType.equals("PAYPAL")) {
            // Logic xử lý PayPal
            processPayPal(amount);
        } else if (paymentType.equals("BANK_TRANSFER")) {
            // Logic xử lý chuyển khoản
            processBankTransfer(amount);
        }
        // Mỗi lần thêm phương thức thanh toán mới phải sửa class này
    }
}
```

**✌️ TUÂN THỦ OCP:**
```java
// Interface định nghĩa hành vi chung
public interface PaymentMethod {
    void pay(double amount);
}

// Các class implement interface
public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        // Logic xử lý thẻ tín dụng
        System.out.println("Processing credit card payment: $" + amount);
    }
}

public class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        // Logic xử lý PayPal
        System.out.println("Processing PayPal payment: $" + amount);
    }
}

public class BankTransferPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        // Logic xử lý chuyển khoản
        System.out.println("Processing bank transfer: $" + amount);
    }
}

// Class xử lý thanh toán không cần sửa đổi khi thêm phương thức mới
public class PaymentProcessor {
    public void processPayment(PaymentMethod paymentMethod, double amount) {
        paymentMethod.pay(amount);
    }
}

// Sử dụng
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();
        
        // Dễ dàng thêm phương thức mới mà không sửa PaymentProcessor
        PaymentMethod creditCard = new CreditCardPayment();
        PaymentMethod payPal = new PayPalPayment();
        
        processor.processPayment(creditCard, 100.0);
        processor.processPayment(payPal, 50.0);
    }
}
```

---

## L - Liskov Substitution Principle (LSP)
**Nguyên tắc thay thế Liskov**

> Một class con phải có thể thay thế class cha mà không làm hỏng chương trình.
> Nếu S là lớp con của T, thì ta có thể thay thế đối tượng kiểu T bằng đối tượng kiểu S mà không làm thay đổi tính đúng đắn của chương trình.

### Ví dụ thực tế:

**❌ VI PHẠM LSP:**
```java
public class Bird {
    public void fly() {
        System.out.println("Bird is flying");
    }
}

public class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

// Vấn đề: Penguin không thể thay thế Bird
public class BirdTest {
    public static void makeBirdFly(Bird bird) {
        bird.fly(); // Sẽ gây lỗi nếu bird là Penguin
    }
    
    public static void main(String[] args) {
        Bird sparrow = new Bird();
        Bird penguin = new Penguin();
        
        makeBirdFly(sparrow); // OK
        makeBirdFly(penguin); // ERROR!
    }
}
```

**✌️ TUÂN THỦ LSP:**
```java
// Class cha chỉ chứa hành vi chung
public abstract class Bird {
    public abstract void makeSound();
    public abstract void eat();
}

// Interface riêng cho hành vi bay
public interface Flyable {
    void fly();
}

// Các class bird cụ thể
public class Sparrow extends Bird implements Flyable {
    @Override
    public void makeSound() {
        System.out.println("Sparrow chirps");
    }
    
    @Override
    public void eat() {
        System.out.println("Sparrow eats seeds");
    }
    
    @Override
    public void fly() {
        System.out.println("Sparrow flies");
    }
}

public class Penguin extends Bird {
    @Override
    public void makeSound() {
        System.out.println("Penguin squawks");
    }
    
    @Override
    public void eat() {
        System.out.println("Penguin eats fish");
    }
    
    // Penguin không implement Flyable vì không biết bay
}

// Sử dụng đa hình đúng cách
public class BirdTest {
    public static void makeBirdSing(Bird bird) {
        bird.makeSound(); // Bất kỳ bird nào cũng có thể làm điều này
    }
    
    public static void makeBirdFly(Flyable flyableBird) {
        flyableBird.fly(); // Chỉ những bird biết bay mới có thể làm
    }
    
    public static void main(String[] args) {
        Bird sparrow = new Sparrow();
        Bird penguin = new Penguin();
        
        makeBirdSing(sparrow); // OK
        makeBirdSing(penguin); // OK
        
        makeBirdFly((Flyable) sparrow); // OK
        // makeBirdFly(penguin); // Compile error - đúng như mong đợi
    }
}
```

---

## I - Interface Segregation Principle (ISP)
**Nguyên tắc phân tách interface**

> Không nên bắt một class phải implements những method mà nó không dùng.
> Giải pháp: Tách interface lớn thành các interface nhỏ hơn.

### Ví dụ thực tế:

**❌ VI PHẠM ISP:**
```java
public interface Developer {
    void code();
    void design();
    void test();
    void deploy();
}

public class BackendDeveloper implements Developer {
    @Override
    public void code() {
        System.out.println("Writing backend code");
    }
    
    @Override
    public void design() {
        // Backend developer không làm design UI
        throw new UnsupportedOperationException("Backend developers don't design UI");
    }
    
    @Override
    public void test() {
        System.out.println("Writing unit tests");
    }
    
    @Override
    public void deploy() {
        System.out.println("Deploying to server");
    }
}

public class FrontendDeveloper implements Developer {
    @Override
    public void code() {
        System.out.println("Writing frontend code");
    }
    
    @Override
    public void design() {
        System.out.println("Designing UI");
    }
    
    @Override
    public void test() {
        // Frontend developer không làm unit test
        throw new UnsupportedOperationException("Frontend developers don't write unit tests");
    }
    
    @Override
    public void deploy() {
        // Frontend developer không deploy
        throw new UnsupportedOperationException("Frontend developers don't deploy");
    }
}
```

**✌️ TUÂN THỦ ISP:**
```java
// Tách thành các interface nhỏ, chuyên biệt
public interface Coder {
    void code();
}

public interface Designer {
    void design();
}

public interface Tester {
    void test();
}

public interface Deployer {
    void deploy();
}

// Các class chỉ implements những interface cần thiết
public class BackendDeveloper implements Coder, Tester, Deployer {
    @Override
    public void code() {
        System.out.println("Writing backend code");
    }
    
    @Override
    public void test() {
        System.out.println("Writing unit tests");
    }
    
    @Override
    public void deploy() {
        System.out.println("Deploying to server");
    }
}

public class FrontendDeveloper implements Coder, Designer {
    @Override
    public void code() {
        System.out.println("Writing frontend code");
    }
    
    @Override
    public void design() {
        System.out.println("Designing UI");
    }
}

public class FullStackDeveloper implements Coder, Designer, Tester, Deployer {
    @Override
    public void code() {
        System.out.println("Writing full stack code");
    }
    
    @Override
    public void design() {
        System.out.println("Designing full system");
    }
    
    @Override
    public void test() {
        System.out.println("Writing comprehensive tests");
    }
    
    @Override
    public void deploy() {
        System.out.println("Deploying full application");
    }
}
```

---

## D - Dependency Inversion Principle (DIP)
**Nguyên tắc đảo ngược sự phụ thuộc**

> Code nên phụ thuộc vào abstraction (giao diện), không phải phụ thuộc vào cụ thể (implementation).
> - Inject phụ thuộc qua constructor hoặc @Autowired
> - Làm việc qua interface thay vì class cụ thể

### Ví dụ thực tế:

**❌ VI PHẠM DIP:**
```java
public class OrderService {
    // Phụ thuộc trực tiếp vào implementation cụ thể
    private MySQLDatabase database = new MySQLDatabase();
    private EmailNotification emailNotification = new EmailNotification();
    
    public void processOrder(Order order) {
        // Logic xử lý đơn hàng
        database.saveOrder(order);
        emailNotification.sendOrderConfirmation(order);
    }
}

// Nếu muốn đổi sang PostgreSQL hoặc SMS notification phải sửa OrderService
```

**✌️ TUÂN THỦ DIP:**
```java
// Các interface abstraction
public interface IDatabase {
    void saveOrder(Order order);
    Order getOrder(String orderId);
}

public interface INotificationService {
    void sendOrderConfirmation(Order order);
    void sendShippingNotification(Order order);
}

// Các implementation cụ thể
public class MySQLDatabase implements IDatabase {
    @Override
    public void saveOrder(Order order) {
        System.out.println("Saving order to MySQL: " + order.getId());
    }
    
    @Override
    public Order getOrder(String orderId) {
        System.out.println("Getting order from MySQL: " + orderId);
        return new Order(orderId);
    }
}

public class PostgreSQLDatabase implements IDatabase {
    @Override
    public void saveOrder(Order order) {
        System.out.println("Saving order to PostgreSQL: " + order.getId());
    }
    
    @Override
    public Order getOrder(String orderId) {
        System.out.println("Getting order from PostgreSQL: " + orderId);
        return new Order(orderId);
    }
}

public class EmailNotification implements INotificationService {
    @Override
    public void sendOrderConfirmation(Order order) {
        System.out.println("Sending email confirmation for order: " + order.getId());
    }
    
    @Override
    public void sendShippingNotification(Order order) {
        System.out.println("Sending shipping email for order: " + order.getId());
    }
}

public class SMSNotification implements INotificationService {
    @Override
    public void sendOrderConfirmation(Order order) {
        System.out.println("Sending SMS confirmation for order: " + order.getId());
    }
    
    @Override
    public void sendShippingNotification(Order order) {
        System.out.println("Sending shipping SMS for order: " + order.getId());
    }
}

// OrderService phụ thuộc vào abstraction, không phải implementation
public class OrderService {
    private final IDatabase database;
    private final INotificationService notificationService;
    
    // Dependency Injection qua constructor
    public OrderService(IDatabase database, INotificationService notificationService) {
        this.database = database;
        this.notificationService = notificationService;
    }
    
    public void processOrder(Order order) {
        // Logic xử lý đơn hàng
        database.saveOrder(order);
        notificationService.sendOrderConfirmation(order);
    }
}

// Sử dụng với Spring Boot (@Autowired)
@Service
public class OrderServiceSpring {
    private final IDatabase database;
    private final INotificationService notificationService;
    
    // Spring sẽ tự động inject appropriate implementations
    @Autowired
    public OrderServiceSpring(IDatabase database, INotificationService notificationService) {
        this.database = database;
        this.notificationService = notificationService;
    }
    
    public void processOrder(Order order) {
        database.saveOrder(order);
        notificationService.sendOrderConfirmation(order);
    }
}

// Configuration class để định nghĩa implementations
@Configuration
public class AppConfig {
    
    @Bean
    public IDatabase database() {
        // Dễ dàng đổi implementation ở đây
        return new MySQLDatabase(); // hoặc new PostgreSQLDatabase()
    }
    
    @Bean
    public INotificationService notificationService() {
        // Dễ dàng đổi implementation ở đây
        return new EmailNotification(); // hoặc new SMSNotification()
    }
    
    @Bean
    public OrderService orderService(IDatabase database, INotificationService notificationService) {
        return new OrderService(database, notificationService);
    }
}
```

---

## Tổng kết và Best Practices

### Checklist tuân thủ SOLID:

**Single Responsibility Principle:**
- [ ] Class có hơn 1 lý do để thay đổi?
- [ ] Class có quá nhiều responsibilities?
- [ ] Có thể tách class thành các class nhỏ hơn không?

**Open/Closed Principle:**
- [ ] Khi thêm functionality mới có cần sửa code cũ không?
- [ ] Có thể mở rộng bằng inheritance/composition không?
- [ ] Code có dễ dàng mở rộng không?

**Liskov Substitution Principle:**
- [ ] Subclass có thể thay thế parent class không?
- [ ] Có method nào trong subclass throw UnsupportedOperationException không?
- [ ] Subclass có làm vỡ contract của parent class không?

**Interface Segregation Principle:**
- [ ] Class có phải implement methods không dùng không?
- [ ] Interface có quá nhiều responsibilities không?
- [ ] Có thể tách interface thành các interface nhỏ hơn không?

**Dependency Inversion Principle:**
- [ ] Class có phụ thuộc vào concrete class không?
- [ ] Có sử dụng interface/abstraction không?
- [ ] Có sử dụng Dependency Injection không?

### Lợi ích của SOLID:

1. **Maintainability**: Code dễ bảo trì, sửa lỗi
2. **Extensibility**: Dễ dàng mở rộng functionality
3. **Testability**: Dễ viết unit test
4. **Reusability**: Code có thể tái sử dụng
5. **Readability**: Code dễ đọc và hiểu

### Common Pitfalls cần tránh:

1. **Over-engineering**: Áp dụng SOLID quá mức phức tạp
2. **Premature abstraction**: Tạo interface quá sớm
3. **Analysis paralysis**: Quá lo lắng về SOLID mà không viết code
4. **Ignoring context**: Áp dụng SOLID mà không xem xét business context

### Khi nào nên áp dụng SOLID:

- Projects medium đến large scale
- Teams nhiều người
- Projects cần long-term maintenance
- Khi code bắt đầu trở nên khó maintain

### Khi nào có thể bỏ qua SOLID:

- Small projects/prototypes
- One-time scripts
- Performance-critical code (đôi khi abstraction làm giảm performance)
- Simple utilities

Remember: **SOLID là principles, không phải rules**. Hãy sử dụng chúng một cách linh hoạt dựa trên context cụ thể của project.
