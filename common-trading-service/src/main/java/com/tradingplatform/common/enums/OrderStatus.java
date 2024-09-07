package com.tradingplatform.common.enums;

public enum OrderStatus {
    
    NEW,              // The order has been created but not yet processed
    PENDING,          // The order is waiting for execution in the order book
    PARTIALLY_FILLED, // A portion of the order has been executed
    FILLED,           // The entire order has been fully executed
    CANCELED,         // The order has been canceled before it was fully executed
    REJECTED,         // The order was not accepted by the system
    EXPIRED,          // The order expired without being executed
    PENDING_CANCEL,   // A cancellation request has been made but the order is not yet canceled
    PARTIALLY_FILLED_CANCELED // Part of the order was filled before the remainder was canceled
}