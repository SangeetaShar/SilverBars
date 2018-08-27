package com.silverbars.domain;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

final public class UserId {
    private final String userId;

    public UserId(String userId) {
        this.userId = userId;
    }

    public static UserId of(String userId) {
        return new UserId(userId);
    }

    @Override
    public String toString() {
        return "UserId{" +
                "userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
