package org.hds.model;

public class item {
	private String infoSN;

	private Integer pageid;

	private Integer itemid;

	private Integer itemleft;

	private Integer itemtop;

	private Integer itemwidth;

	private Integer itemheight;

	private Integer itemfontno;

	private String itembackcolor;

	private Integer itembackopacity;

	private String itemforecolor;

	private Integer itemforeopacity;

	private Integer itemtype;

	private Integer delindex;

	private String itemcontext;

	private String itemcontextjson;

	private String itemstyle;

	public String getInfoSN() {
		return infoSN;
	}

	public void setInfoSN(String infoSN) {
		this.infoSN = infoSN;
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

	public Integer getItemfontno() {
		return itemfontno;
	}

	public void setItemfontno(Integer itemfontno) {
		this.itemfontno = itemfontno;
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

	public Integer getItembackopacity() {
		return itembackopacity;
	}

	public void setItembackopacity(Integer itembackopacity) {
		this.itembackopacity = itembackopacity;
	}

	public String getItemforecolor() {
		return itemforecolor;
	}

	public void setItemforecolor(String itemforecolor) {
		this.itemforecolor = itemforecolor == null ? null : itemforecolor.trim();
	}

	public Integer getItemforeopacity() {
		return itemforeopacity;
	}

	public void setItemforeopacity(Integer itemforeopacity) {
		this.itemforeopacity = itemforeopacity;
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

	public String getItemcontextjson() {
		return itemcontextjson;
	}

	public void setItemcontextjson(String itemcontextjson) {
		this.itemcontextjson = itemcontextjson == null ? null : itemcontextjson.trim();
	}

	public String getItemstyle() {
		return itemstyle;
	}

	public void setItemstyle(String itemstyle) {
		this.itemstyle = itemstyle == null ? null : itemstyle.trim();
	}
}