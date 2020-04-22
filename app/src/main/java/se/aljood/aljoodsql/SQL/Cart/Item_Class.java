package se.aljood.aljoodsql.SQL.Cart;

public class Item_Class {
    int item_id,item_qty;
    int def_item_unit;
    String item_name_ar, unit1,unit2,unit3,item_marke,item_img,item_weight;
    boolean item_enabled;

    public boolean isItem_type_price() {
        return item_type_price;
    }

    public void setItem_type_price(boolean item_type_price) {
        this.item_type_price = item_type_price;
    }

    boolean item_type_price;
    Double item_pur_price_plus,item_pur_price,item_sale_price,item_price,item_moms;

    public Double getItem_moms() {
        return item_moms;
    }

    public void setItem_moms(Double item_moms) {
        this.item_moms = item_moms;
    }

    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public int getDef_item_unit() {
        return def_item_unit;
    }

    public void setDef_item_unit(int def_item_unit) {
        this.def_item_unit = def_item_unit;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public String getUnit3() {
        return unit3;
    }

    public void setUnit3(String unit3) {
        this.unit3 = unit3;
    }

    public int getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(int item_qty) {
        this.item_qty = item_qty;
    }

    public String getItem_weight() {
        return item_weight;
    }

    public void setItem_weight(String item_weight) {
        this.item_weight = item_weight;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name_ar() {
        return item_name_ar;
    }

    public void setItem_name_ar(String item_name_ar) {
        this.item_name_ar = item_name_ar;
    }

    public String getItem_marke() {
        return item_marke;
    }

    public void setItem_marke(String item_marke) {
        this.item_marke = item_marke;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public Boolean getItem_enabled() {
        return item_enabled;
    }

    public void setItem_enabled(Boolean item_enabled) {
        this.item_enabled = item_enabled;
    }

    public Double getItem_pur_price_plus() {
        return item_pur_price_plus;
    }

    public void setItem_pur_price_plus(Double item_pur_price_plus) {
        this.item_pur_price_plus = item_pur_price_plus;
    }

    public Double getItem_pur_price() {
        return item_pur_price;
    }

    public void setItem_pur_price(Double item_pur_price) {
        this.item_pur_price = item_pur_price;
    }

    public Double getItem_sale_price() {
        return item_sale_price;
    }

    public void setItem_sale_price(Double item_sale_price) {
        this.item_sale_price = item_sale_price;
    }
}
