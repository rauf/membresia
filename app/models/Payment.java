package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "payment")
public class Payment extends Model {

    @Id
    private Long id;

    @Constraints.Required
    Float amount;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @Constraints.Required
    Integer status;

    @ManyToOne(cascade = CascadeType.ALL)
    public Installment installment;

    @ManyToOne(cascade = CascadeType.ALL)
    public Member member;
}
