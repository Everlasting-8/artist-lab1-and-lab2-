package my.lab1.order.repositories;

import my.lab1.order.model.OrderCountCheck;
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
