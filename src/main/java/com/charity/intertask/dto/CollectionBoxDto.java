package com.charity.intertask.dto;

public class CollectionBoxDto {
    private Long id;
    private boolean assigned;
    private boolean empty;

    public CollectionBoxDto() {
    }

    public CollectionBoxDto(Long id, boolean assigned, boolean empty) {
        this.id = id;
        this.assigned = assigned;
        this.empty = empty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public static class Builder {
        private Long id;
        private boolean assigned;
        private boolean empty;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder assigned(boolean assigned) {
            this.assigned = assigned;
            return this;
        }

        public Builder empty(boolean empty) {
            this.empty = empty;
            return this;
        }

        public CollectionBoxDto build() {
            return new CollectionBoxDto(id, assigned, empty);
        }
    }

}