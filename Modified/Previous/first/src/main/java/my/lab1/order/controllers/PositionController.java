package my.lab1.order.controllers;

import my.lab1.order.model.Position;
import my.lab1.order.repositories.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class PositionController
{

    private Logger logger = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionRepository repository;

    @GetMapping("/position/{id}")
    public Position get(@PathVariable("id") int positionId)
    {
        return repository.findById(positionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Позиция с id=" + positionId + " не найдена"));
    }

    @GetMapping("/position")
    public List<Position> all()
    {
        return repository.findAll();
    }

    @PostMapping("/position")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public int save(@RequestBody Position position)
    {
        repository.save(position);
        return position.getId();
    }

    @PutMapping("/position")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public void update(@RequestBody Position position)
    {
        logger.info(position.toString());
        if (position.getId() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Поле id не заполнено");
        else
            repository.save(position);
    }

    @DeleteMapping("/position/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPPLIER"})
    public void delete(@PathVariable("id") int positionId)
    {
        try
        {
            repository.deleteById(positionId);
        }
        catch (EmptyResultDataAccessException erda)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Позиция с id=" + positionId + " не найдена");
        }
    }

}
