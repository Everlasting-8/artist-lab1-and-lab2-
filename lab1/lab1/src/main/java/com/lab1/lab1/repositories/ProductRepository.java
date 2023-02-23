package  com.lab1.lab1.repositories;

import  com.lab1.lab1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{

    @Query(value = "UPDATE Product p SET p.count = p.count - (:subtrahend) WHERE p.id = :id")
    void decreaseCount(@Param(value = "id") int id, @Param(value = "subtrahend") int subtrahend);

}
