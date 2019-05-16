package org.hds.model;

public class basemap {
    private Integer id;

    private String basemapname;

    private String projectid;
    
    private Integer imgtype;
    
    private String basemapclassify;

    private String basemaptype;
    
    private String basemapstyle;

    private String basemapdata;

    private Integer delindex;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBasemapname() {
        return basemapname;
    }

    public void setBasemapname(String basemapname) {
        this.basemapname = basemapname == null ? null : basemapname.trim();
    }
    
    public String getprojectid() {
        return projectid;
    }

    public void setprojectid(String projectid) {
        this.projectid = projectid;
    }
    
    public Integer getimgtype() {
        return imgtype;
    }

    public void setimgtype(Integer imgtype) {
        this.imgtype = imgtype;
    }

    public String getBasemapclassify() {
        return basemapclassify;
    }

    public void setBasemapclassify(String basemapclassify) {
        this.basemapclassify = basemapclassify == null ? null : basemapclassify.trim();
    }

    public String getBasemaptype() {
        return basemaptype;
    }

    public void setBasemaptype(String basemaptype) {
        this.basemaptype = basemaptype == null ? null : basemaptype.trim();
    }
    
    public String getbasemapstyle() {
        return basemapstyle;
    }

    public void setbasemapstyle(String basemapstyle) {
        this.basemapstyle = basemapstyle == null ? null : basemapstyle.trim();
    }

    public String getBasemapdata() {
        return basemapdata;
    }

    public void setBasemapdata(String basemapdata) {
        this.basemapdata = basemapdata == null ? null : basemapdata.trim();
    }
    
    public Integer getdelindex() {
        return delindex;
    }

    public void setdelindex(Integer delindex) {
        this.delindex = delindex;
    }
}