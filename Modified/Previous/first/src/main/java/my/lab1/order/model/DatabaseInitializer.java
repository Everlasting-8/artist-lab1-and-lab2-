package my.lab1.order.model;

import lombok.AllArgsConstructor;
import my.lab1.order.repositories.ProductRepository;
import my.lab1.order.repositories.RoleRepository;
import my.lab1.order.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements ApplicationRunner
{

    private ProductRepository productRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        final List<Product> initialCatalog = new LinkedList<>();
        final Product para = Product.builder()
                .category("Лекарства")
                .count(10)
                .name("Парацетамон")
                .photo("Фото")
                .build();
        initialCatalog.add(para);
        final Product au = Product.builder()
                .category("Лекарства")
                .count(3)
                .name("Активированный уголь")
                .photo("Фото")
                .build();
        initialCatalog.add(au);
        final Product tape = Product.builder()
                .category("Медицинские изделия")
                .count(100)
                .name("Бинт")
                .photo("Фото")
                .build();
        initialCatalog.add(tape);
        productRepository.saveAll(initialCatalog);
        //
        final List<Role> essentialRoles = new LinkedList<>();
        final Role superuser = Role.builder()
                .name(Roles.ADMIN.roleName())
                .build();
        essentialRoles.add(superuser);
        final Role supplier = Role.builder()
                .name(Roles.SUPPLIER.roleName())
                .build();
        essentialRoles.add(supplier);
        final Role consumer = Role.builder()
                .name(Roles.CONSUMER.roleName())
                .build();
        essentialRoles.add(consumer);
        roleRepository.saveAll(essentialRoles);
        //
        final List<User> exampleUsers = new LinkedList<>();
        final User joeConsumer = User.builder()
                .login("joe-consumer")
                .passwordHash(encoder.encode("joe-pass"))
                .role(consumer)
                .build();
        exampleUsers.add(joeConsumer);
        final User daveSupplier = User.builder()
                .login("dave-supplier")
                .passwordHash(encoder.encode("dave-pass"))
                .role(supplier)
                .build();
        exampleUsers.add(daveSupplier);
        final User johnSuperuser = User.builder()
                .login("john-root")
                .passwordHash(encoder.encode("john-pass"))
                .role(superuser)
                .build();
        exampleUsers.add(johnSuperuser);
        userRepository.saveAll(exampleUsers);
    }
}
