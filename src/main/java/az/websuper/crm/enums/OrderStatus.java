package az.websuper.crm.enums;

public enum OrderStatus {
    /** The order has been placed but not yet processed. */
    PENDING,

    /** The order has been confirmed and is awaiting fulfillment. */
    CONFIRMED,

    /** The order is being picked and packed for shipment. */
    PROCESSING,

    /** The order has been shipped to the customer or is en route. */
    SHIPPED,

    /** The order has been delivered to the customer. */
    DELIVERED,

    /** The order has been cancelled and will not be fulfilled. */
    CANCELLED,

    /** The order was rejected due to various reasons such as out of stock or incorrect details. */
    REJECTED,

    /** The order is currently on hold due to issues like payment problems or customer requests. */
    ON_HOLD,

    /** The order has been returned by the customer. */
    RETURNED,

    /** The order is under review for any discrepancies or issues. */
    UNDER_REVIEW,

    /** The order has been partially fulfilled; some items may be still pending. */
    PARTIALLY_SHIPPED,

    /** The order is awaiting further action or is awaiting more information from the customer or vendor. */
    AWAITING_INFORMATION,

    /** The order is being prepared for dispatch but is not yet in the shipping stage. */
    READY_FOR_SHIPMENT,

    /** The order has been successfully delivered and confirmed by the customer. */
    DELIVERED_CONFIRMED,

    /** The order has been cancelled due to unforeseen issues and cannot be completed. */
    CANCELLED_FAILED,

    /** The order is in the process of being invoiced or billed. */
    INVOICING,

    /** The order's status is unknown, typically used for error states or uninitialized statuses. */
    UNKNOWN
}
