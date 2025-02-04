package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.utils.MiscUtils;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "service")
@EntityListeners(AuditingEntityListener.class)
public class ChurchServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;
    private String serviceName;
    private String serviceDescription;

    @Transient
    private String excerpt;

    public String getExcerpt() {
        return MiscUtils.generateExcerpt(serviceDescription, 200);
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public ChurchServices() {}

    public ChurchServices(String serviceName, String serviceDescription) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    @Override
    public String toString() {
        return "ChurchServices{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                '}';
    }
}
