/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Muggi
 */
@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String Street;
    private String Additionalinfo;
    @OneToMany(mappedBy = "address")
    private List<InfoEntity> infoEntitys;
    
    @ManyToOne
    private CityInfo cityInfo;

    public Address(){}
    
    public Address(String Street, String Additionalinfo) {
        this.infoEntitys = new ArrayList();
        this.Street = Street;
        this.Additionalinfo = Additionalinfo;
        this.infoEntitys = new ArrayList();
    }

    public List<InfoEntity> getInfoEntitys() {
        return infoEntitys;
    }

    public void setInfoEntitys(List<InfoEntity> infoEntitys) {
        this.infoEntitys = infoEntitys;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }
    
    public void addInfoEntity(InfoEntity ie){
        infoEntitys.add(ie);
    }
    
    public void removeInfoEntity(InfoEntity ie){
        infoEntitys.remove(ie);
    }
    
    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getAdditionalinfo() {
        return Additionalinfo;
    }

    public void setAdditionalinfo(String Additionalinfo) {
        this.Additionalinfo = Additionalinfo;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
