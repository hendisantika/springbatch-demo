package com.hendisantika.springbatchdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-09
 * Time: 21:47
 */
@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
    private String type;
    private String model;
    private String built;

    public Vehicle() {
    }

    public Vehicle(long id, String type, String model, String built) {
        super();
        this.type = type;
        this.model = model;
        this.built = built;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBuilt() {
        return built;
    }

    public void setBuilt(String built) {
        this.built = built;
    }

    @Override
    public String toString() {
        return "Vechile [id=" + id + ", type=" + type + ", model=" + model + ", built=" + built + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((built == null) ? 0 : built.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehicle other = (Vehicle) obj;
        if (built == null) {
            if (other.built != null)
                return false;
        } else if (!built.equals(other.built))
            return false;
        if (id != other.id)
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (type == null) {
            return other.type == null;
        } else return type.equals(other.type);
    }
}
