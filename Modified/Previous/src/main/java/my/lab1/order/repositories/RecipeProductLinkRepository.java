package my.lab1.order.repositories;

import my.lab1.order.model.RecipeProduct;
import my.lab1.order.model.RecipeProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeProductLinkRepository extends JpaRepository<RecipeProduct, RecipeProductKey>
{

}
