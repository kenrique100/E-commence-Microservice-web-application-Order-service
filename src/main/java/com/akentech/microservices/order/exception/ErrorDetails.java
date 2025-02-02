package com.akentech.microservices.order.exception;

import lombok.Getter;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String details) {

}