package  com.lab1.lab1.repositories;

import com.lab1.lab1.model.OrderCountCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderCountCheckRepository extends JpaRepository<OrderCountCheck, String>
{

    @Query(value = "select occ from OrderCountCheck occ where occ.positionId in :positionIds")
    List<OrderCountCheck> findAllByPositionIds(@Param(value = "positionIds") Set<Integer> positionIds);

}
