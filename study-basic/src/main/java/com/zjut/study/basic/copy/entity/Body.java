package com.zjut.study.basic.copy.entity;

import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Getter
public class Body implements Serializable {
    private String name;
    private Integer length;

    private Hand hand;

    public Body(String name, Integer length, Hand hand) {
        this.name = name;
        this.length = length;
        this.hand = hand;
    }

    /**
     * 序列化深拷贝
     *
     * @return
     */
    public Body deepClone() {
        Body clone = null;
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bout)) {
            out.writeObject(this);

            try (ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
                 ObjectInputStream in = new ObjectInputStream(bin)) {
                clone = (Body) in.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clone;
    }
}
