package  com.lab1.lab1.repositories;

import  com.lab1.lab1.model.RecipeProduct;
import  com.lab1.lab1.model.RecipeProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeProductLinkRepository extends JpaRepository<RecipeProduct, RecipeProductKey>
{

}
