package com.example.demo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// 假设 ProductMapper 接口定义在 com.example.demo 包下，添加正确的导入语句
import com.example.demo.product.ProductMapper;
import com.example.demo.product.Product;
import com.example.demo.product.PurchaseRecord;
// 要解决这个问题，需要确保 InventoryMapper 接口已定义在指定的包中
// 并且项目的构建路径中包含了该类所在的源文件或类文件
// 这里假设 InventoryMapper 接口存在于指定包中，且项目配置无误
import com.example.demo.product.PurchaseRecordMapper;
// 为了解决 `io.jsonwebtoken.Jwts` 无法解析的问题，需要确保项目中已经添加了 Java JWT 库的依赖。
// 如果使用 Maven 项目，可以在 `pom.xml` 中添加以下依赖：

// 如果使用 Gradle 项目，可以在 `build.gradle` 中添加以下依赖：
// implementation 'io.jsonwebtoken:jjwt:0.9.1'
// 添加依赖后，重新构建项目以确保依赖被正确下载。
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import com.example.demo.security.JwtUtils; // 假设 JwtUtils 类在 com.example.demo.utils 包下，根据实际情况修改
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;



public interface UserService {
    String register(User user);
    String login(User user);
// 为了使用 @Transactional 注解，需要引入 Spring 的事务管理依赖
@Transactional
    String purchaseProduct(String username, Long itemId, int quantity);
}

/**
 * 服务层实现类，处理用户注册和登录业务逻辑
 * @Service 注解表示该类是Spring框架管理的服务层组件
 */
@Service
class UserServiceImpl implements UserService {
// 为了使用 @Autowired 注解，需要引入 Spring 的依赖
// 原来的 @Autowired 注解
    /**
     * 使用@Autowired实现依赖注入
     * Spring会自动装配UserMapper接口的实现（MyBatis生成代理对象）
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * Spring Security的密码编码器
     * @Autowired 根据类型自动注入配置的PasswordEncoder bean
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;

    @Autowired
    // 为了解决 JwtUtils 无法解析为类型的问题，需要确保 JwtUtils 类已定义，并且在当前类中正确导入。
    private JwtUtils jwtUtils;

// 为了解决 InventoryMapper 无法解析为类型的问题，需要确保 InventoryMapper 接口已定义，并且在当前类中正确导入。
// 假设 InventoryMapper 接口定义在 com.example.demo 包下，需要在文件开头添加导入语句。
// 同时，要确保项目中已经正确配置了 MyBatis 或其他持久层框架，以便能够生成 InventoryMapper 接口的代理对象。


    @Override
    @Transactional
    public String purchaseProduct(String username, Long productId, int quantity) {
        Product product = productMapper.findById(productId);
        if (product == null || product.getStock() < quantity) {
            throw new RuntimeException("Insufficient inventory");
        }
        
        productMapper.updateStock(productId, product.getStock() - quantity);
        
// 如果 PurchaseRecord 类没有无参构造函数，需要根据其有参构造函数进行实例化
// 假设 PurchaseRecord 类有一个包含 userId, productId, quantity 的构造函数
// 这里先假设已经获取到了 userId，实际情况中需要根据业务逻辑获取
        PurchaseRecord record = new PurchaseRecord(username, productId, quantity);
        purchaseRecordMapper.insert(record);
        
        return "Purchase successful";
    }

    @Override
    public String login(User user) {
        // 假设 userMapper.selectByUsername 返回的是 Optional<User>
        java.util.Optional<User> existingUserOptional = userMapper.selectByUsername(user.getUsername());
        User existingUser = existingUserOptional.orElse(null);
        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        // 假设需要将用户名转换为 Authentication 对象
        // 这里简单创建一个 UsernamePasswordAuthenticationToken 作为示例
// 为了解决 UsernamePasswordAuthenticationToken 无法解析为类型的问题，需要添加对应的导入语句
// ...原始代码中的其他部分...

        Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getUsername(), null);
        return jwtUtils.generateJwtToken(authentication);
    }
    @Override
    public String register(User user) {
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
        return "User registered successfully";
    }

}
  


