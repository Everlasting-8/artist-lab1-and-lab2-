package com.lab1.lab1.model;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "`v_order_count_check`")
@Subselect("select gen_random_uuid() as id, v.* from v_order_count_check v")
@Getter
@ToString
public class OrderCountCheck
{
    @Id
    private String id;
    private int positionId;
    private String description;
    private int requiredCount;
    private int productId;
    private String name;
    private int storedCount;
    // position_id, pos.description, rp.count as required_count, p.id as product_id, p.name, p.count as stored_count

}
