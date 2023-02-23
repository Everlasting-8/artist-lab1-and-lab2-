package  com.lab1.lab1.repositories;

import com.lab1.lab1.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer>
{

    @Query(value = "UPDATE Position p SET p.isClosed = true WHERE p.id = :id")
    void closePosition(@Param(value = "id") int id);

}
