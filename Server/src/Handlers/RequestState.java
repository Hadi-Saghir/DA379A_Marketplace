package Handlers;


/**
 * This enum type is to allow the system to understand the status of product
 * available: searchable and offers can be made
 * offer: searchable and offers have been and can be made
 * sold: unsearchable and offers cannot be made
 * */
public enum RequestState {
    available,
    pending,
    sold
}
