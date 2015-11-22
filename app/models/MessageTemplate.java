package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.annotation.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "messageTemplate")
public class MessageTemplate extends Model {

    @Id
    private Long id;

    @Constraints.Required
    Float title;

    @Constraints.Required
    Float body;

    @CreatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date created_at = new Date();

    @UpdatedTimestamp
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:ii:ss")
    public Date updated_at = new Date();
}
