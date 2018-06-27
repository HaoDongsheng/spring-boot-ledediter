package org.hds.model;

public class item {
    private Integer adid;

    private Integer pageid;

    private Integer itemid;

    private Integer itemleft;

    private Integer itemtop;

    private Integer itemwidth;

    private Integer itemheight;

    private String itembackcolor;

    private Integer itemtype;

    private Integer delindex;

    private String itemcontext;

    public Integer getAdid() {
        return adid;
    }

    public void setAdid(Integer adid) {
        this.adid = adid;
    }

    public Integer getPageid() {
        return pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public Integer getItemleft() {
        return itemleft;
    }

    public void setItemleft(Integer itemleft) {
        this.itemleft = itemleft;
    }

    public Integer getItemtop() {
        return itemtop;
    }

    public void setItemtop(Integer itemtop) {
        this.itemtop = itemtop;
    }

    public Integer getItemwidth() {
        return itemwidth;
    }

    public void setItemwidth(Integer itemwidth) {
        this.itemwidth = itemwidth;
    }

    public Integer getItemheight() {
        return itemheight;
    }

    public void setItemheight(Integer itemheight) {
        this.itemheight = itemheight;
    }

    public String getItembackcolor() {
        return itembackcolor;
    }

    public void setItembackcolor(String itembackcolor) {
        this.itembackcolor = itembackcolor == null ? null : itembackcolor.trim();
    }

    public Integer getItemtype() {
        return itemtype;
    }

    public void setItemtype(Integer itemtype) {
        this.itemtype = itemtype;
    }

    public Integer getDelindex() {
        return delindex;
    }

    public void setDelindex(Integer delindex) {
        this.delindex = delindex;
    }

    public String getItemcontext() {
        return itemcontext;
    }

    public void setItemcontext(String itemcontext) {
        this.itemcontext = itemcontext == null ? null : itemcontext.trim();
    }
}