package models;

public class Transaction {
    // TODO !!!!!!!!!!!!!!!!!!!
    private User buyer;
    private NPC buyerNPC; // null if User is seller
    private User seller; // null if a NPC is seller
    private NPC sellerNPC; // null if a User is seller

    private Integer cost; // null if item-item transaction
    private Item itemBuyerGives; // null if item-money transaction
    private Item itemSellerGives; // never null
}
