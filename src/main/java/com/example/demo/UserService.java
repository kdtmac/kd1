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


// 为了解决 InventoryMapper 无法解析为类型的问题，需要确保 InventoryMapper 接口已定义，并且在当前类中正确导入。
// 假设 InventoryMapper 接口定义在 com.example.demo 包下，需要在文件开头添加导入语句。
// 同时，要确保项目中已经正确配置了 MyBatis 或其他持久层框架，以便能够生成 InventoryMapper 接口的代理对象。


    @Override
    @Transactional
    public String purchaseProduct(String username, Long itemId, int quantity) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return "用户不存在";
        }

        // 为了解决 productMapper 无法解析的问题，需要确保 ProductMapper 接口已定义，并且在当前类中正确导入。
        // 假设 ProductMapper 接口定义在 com.example.demo 包下，需要在文件开头添加导入语句。
        // 同时，要确保项目中已经正确配置了 MyBatis 或其他持久层框架，以便能够生成 ProductMapper 接口的代理对象。
        // 这里假设已经正确导入和配置，添加 @Autowired 注解注入 ProductMapper
        

// 假设 ProductMapper 接口中缺少 getItemById 方法，需要在 ProductMapper 接口中添加该方法的定义
// 以下是修改后的代码，需要确保 ProductMapper 接口中存在 getItemById 方法
        Product item = productMapper.findById(itemId);
        if (item == null || item.getStock() < quantity) {
            return "库存不足";
        }

        productMapper.updateStock(itemId, item.getStock() - quantity);
        purchaseRecordMapper.insert(new PurchaseRecord(username, itemId, quantity));
        return "购买成功";
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

    @Override
    public String login(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null || !passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return "Invalid username or password";
        }
        return "Login successful";
    }
}
  


