package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;
import models.Subscription;
import play.data.format.*;

@Entity
@Table(name = "installment")
public class Installment extends Model {

    @Id
    private Long id;

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date dueDate = new Date();

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date updated_at = new Date();

    @OneToMany(mappedBy = "installment", cascade = CascadeType.ALL)
    public List<Payment> payments = new ArrayList<Payment>();

    @ManyToOne(cascade = CascadeType.ALL)
    public Subscription subscription;
}
