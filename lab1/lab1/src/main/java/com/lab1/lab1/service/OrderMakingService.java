package com.lab1.lab1.service;

import lombok.AllArgsConstructor;
import com.lab1.lab1.model.Order;
import com.lab1.lab1.model.OrderCountCheck;
import com.lab1.lab1.model.Position;
import com.lab1.lab1.model.User;
import com.lab1.lab1.repositories.OrderCountCheckRepository;
import com.lab1.lab1.repositories.OrderRepository;
import com.lab1.lab1.repositories.PositionRepository;
import com.lab1.lab1.repositories.ProductRepository;
import com.lab1.lab1.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderMakingService
{

    private final OrderCountCheckRepository orderCountCheckRepository;
    private final PositionRepository positionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderMakingService.class);

    /**
     * Метод оформляет новый заказ для покупателя. В заказ будут входить позиции,
     * id которых указаны в аргументе. Метод проверяет наличие на складе нужного кол-ва товара.
     * Если товара хватает, уменьшает его кол-во на складе. Иначе оформление заказа прервётся и метод вернёт false.
     *
     * @param positionIdToCount id выбранных покупателем позиций и кол-ва каждой из них.
     * @return true если оформление заказа прошло успешно. False если заказ оформляется на закрытую позицию или на складе
     * недостаточно требуемого товара.
     */
    @Transactional
    public boolean make(final Map<String, Integer> positionIdToCount)
    {
        // Проверить, есть ли на складе достаточное кол-во товара для
        // покупки каждой позиции
        final Set<Integer> positionIds = positionIdToCount.keySet().stream()
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        final List<Position> buyingPositions = positionRepository.findAllById(positionIds);
        if (buyingPositions.stream().anyMatch(Position::isClosed))
        {
            logger.info("Нельзя оформить заказ на закрытую позицию");
            return false;
        }
        // проверяю есть ли требуемое кол-во товара на складе
        final List<OrderCountCheck> checkProducts = orderCountCheckRepository.findAllByPositionIds(positionIds);
        for (OrderCountCheck checkProduct : checkProducts)
        {
            final int requiredAmount = positionIdToCount.get(Integer.toString(checkProduct.getPositionId())) * checkProduct.getRequiredCount();
            final int existingAmount = checkProduct.getStoredCount();
            if ( requiredAmount > existingAmount )
            {
                final String errorMsg = String.format("На складе недостаточно товара '%s' для оформления заказа. Требуется %d единиц, а в наличии всего %d.",
                        checkProduct.getName(),
                        requiredAmount,
                        existingAmount);
                logger.info(errorMsg);
                return false;
            }
        }
        // уменьшаю кол-ов товара на складе и закрываю позицию если товар кончился
        for (OrderCountCheck checkProduct : checkProducts)
        {
            final int requiredAmount = positionIdToCount.get(Integer.toString(checkProduct.getPositionId())) * checkProduct.getRequiredCount();
            final int existingAmount = checkProduct.getStoredCount();
            productRepository.decreaseCount(checkProduct.getProductId(), requiredAmount);
            if (requiredAmount == existingAmount)
                positionRepository.closePosition(checkProduct.getPositionId());
        }
        // оформляю заказ
        final String login = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getDetails())
                .getUsername();
        final User currentUser = userRepository.findByLogin(login)
                .orElseThrow( () -> new RuntimeException("Не найден пользователь с логином " + login));
        final List<Position> positionsInOrder = positionRepository.findAllById(positionIds);
        final Order newOrder = Order.builder()
                .orderStatus("Ожидает оплаты")
                .timestamp(Timestamp.from(Instant.now()))
                .user(currentUser)
                .positions(new HashSet<>(positionsInOrder))
                .build();
        orderRepository.save(newOrder);
        return true;
    }

}
