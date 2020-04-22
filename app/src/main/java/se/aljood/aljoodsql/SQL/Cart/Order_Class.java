package se.aljood.aljoodsql.SQL.Cart;

public class Order_Class {
    int order_id;
    Double order_total;
    String order_cus_id,order_cus_name,user_created;
    boolean order_sended,order_packad,order_shipping,order_cancelld;

    public String getUser_created() {
        return user_created;
    }

    public void setUser_created(String user_created) {
        this.user_created = user_created;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_cus_id() {
        return order_cus_id;
    }

    public void setOrder_cus_id(String order_cus_id) {
        this.order_cus_id = order_cus_id;
    }

    public Double getOrder_total() {
        return order_total;
    }

    public void setOrder_total(Double order_total) {
        this.order_total = order_total;
    }

    public String getOrder_cus_name() {
        return order_cus_name;
    }

    public void setOrder_cus_name(String order_cus_name) {
        this.order_cus_name = order_cus_name;
    }

    public boolean isOrder_sended() {
        return order_sended;
    }

    public void setOrder_sended(boolean order_sended) {
        this.order_sended = order_sended;
    }

    public boolean isOrder_packad() {
        return order_packad;
    }

    public void setOrder_packad(boolean order_packad) {
        this.order_packad = order_packad;
    }

    public boolean isOrder_shipping() {
        return order_shipping;
    }

    public void setOrder_shipping(boolean order_shipping) {
        this.order_shipping = order_shipping;
    }

    public boolean isOrder_cancelld() {
        return order_cancelld;
    }

    public void setOrder_cancelld(boolean order_cancelld) {
        this.order_cancelld = order_cancelld;
    }
}
