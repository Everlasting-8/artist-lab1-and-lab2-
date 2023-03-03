package my.lab1.order.service;

import lombok.AllArgsConstructor;
import my.lab1.order.model.Position;
import my.lab1.order.model.Product;
import my.lab1.order.model.Recipe;
import my.lab1.order.model.RecipeProduct;
import my.lab1.order.model.RecipeProductKey;
import my.lab1.order.model.Role;
import my.lab1.order.model.Roles;
import my.lab1.order.model.User;
import my.lab1.order.repositories.PositionRepository;
import my.lab1.order.repositories.ProductRepository;
import my.lab1.order.repositories.RecipeProductLinkRepository;
import my.lab1.order.repositories.RecipeRepository;
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

    private RecipeProductLinkRepository linkRepository;
    private ProductRepository productRepository;
    private RecipeRepository recipeRepository;
    private PositionRepository positionRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        // заполняю товары на складе
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
        final Product iodine = Product.builder()
                .category("Медицинские изделия")
                .count(5)
                .name("Йод")
                .photo("Фото")
                .build();
        initialCatalog.add(iodine);
        final Product chocolate = Product.builder()
                .category("Пищевые добавки")
                .name("Гематоген")
                .count(10)
                .photo("Фото")
                .build();
        initialCatalog.add(chocolate);
        productRepository.saveAll(initialCatalog);
        // заполяю рецепты
        final List<Recipe> recipes = new LinkedList<>();
        final Recipe apurgative = Recipe.builder()
                .title("Анти-слабительное")
                .description("Рецепт от поноса")
                .build();
        recipes.add(apurgative);
        final Recipe medkit = Recipe.builder()
                .title("Аптечка")
                .description("Рецепт аптечки")
                .build();
        recipes.add(medkit);
        final Recipe chocoOnly = Recipe.builder()
                .title("Гематоген")
                .description("Рецепт пищевой добавки Гематоген")
                .build();
        recipes.add(chocoOnly);
        recipeRepository.saveAll(recipes);
        // связываю товары и рецепты
        final List<RecipeProduct> links = new LinkedList<>();
        final RecipeProduct paraPurgativeLink = RecipeProduct.builder()
                .id(new RecipeProductKey(para.getId(), apurgative.getId()))
                .count(1)
                .product(para)
                .recipe(apurgative)
                .build();
        links.add(paraPurgativeLink);
        final RecipeProduct auPurgativeLink = RecipeProduct.builder()
                .id(new RecipeProductKey(au.getId(), apurgative.getId()))
                .count(1)
                .product(au)
                .recipe(apurgative)
                .build();
        links.add(auPurgativeLink);
        final RecipeProduct tapeMedkitLink = RecipeProduct.builder()
                .id(new RecipeProductKey(tape.getId(), medkit.getId()))
                .count(2)
                .product(tape)
                .recipe(medkit)
                .build();
        links.add(tapeMedkitLink);
        final RecipeProduct iodineMedkitLink = RecipeProduct.builder()
                .id(new RecipeProductKey(iodine.getId(), medkit.getId()))
                .count(1)
                .product(iodine)
                .recipe(medkit)
                .build();
        links.add(iodineMedkitLink);
        final RecipeProduct chocoLink = RecipeProduct.builder()
                .id(new RecipeProductKey(chocolate.getId(), chocoOnly.getId()))
                .count(1)
                .product(chocolate)
                .recipe(chocoOnly)
                .build();
        links.add(chocoLink);
        linkRepository.saveAll(links);
        // заполняю позиции
        final List<Position> positions = new LinkedList<>();
        final Position apurPos = Position.builder()
                .recipe(apurgative)
                .price(1000)
                .title("Анти-понос")
                .isClosed(false)
                .description("Позиция с рецептом лекарств от поноса")
                .photo("Фото")
                .build();
        positions.add(apurPos);
        final Position medkitPos = Position.builder()
                .recipe(medkit)
                .price(50)
                .title("Аптечка")
                .isClosed(false)
                .description("Позиция с аптечкой")
                .photo("Фото")
                .build();
        positions.add(medkitPos);
        final Position chocoPos = Position.builder()
                .recipe(chocoOnly)
                .price(25)
                .title("Гематоген")
                .isClosed(false)
                .description("Позиция с пищевой добавкой Гематоген")
                .photo("Фото")
                .build();
        positions.add(chocoPos);
        positionRepository.saveAll(positions);
        // заполняю возможные роли
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
        // заполняю пользователей
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
