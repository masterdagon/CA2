/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Muggi
 */
@Entity
public class Phone implements Serializable {
    @ManyToOne
    private InfoEntity infoEntity;
    private String number;
    private String description;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Phone() {
    }

    public Phone(InfoEntity infoEntity, String number, String description) {
        this.infoEntity = infoEntity;
        this.number = number;
        this.description = description;
    }

    public InfoEntity getInfoEntity() {
        return infoEntity;
    }

    public void setInfoEntity(InfoEntity infoEntity) {
        this.infoEntity = infoEntity;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
