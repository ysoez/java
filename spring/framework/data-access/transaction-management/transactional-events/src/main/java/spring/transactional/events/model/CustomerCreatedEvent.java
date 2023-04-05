package spring.transactional.events.model;

import lombok.Getter;

public record CustomerCreatedEvent(Customer customer) {
}