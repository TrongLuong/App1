package com.example.app1;

import java.util.Objects;

public class NhanVien {
    String id, name, address;

    public NhanVien(String id) {
    }

    @Override
    public String toString() {
        return
              id + " - " + name +" - " + address;

    }

    public NhanVien(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien nhanVien = (NhanVien) o;
        return Objects.equals(id, nhanVien.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
