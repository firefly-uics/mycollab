/*Domain class of table m_tracker_query*/
package com.mycollab.module.tracker.domain;

import com.mycollab.core.arguments.ValuedBean;
import com.mycollab.db.metadata.Column;
import com.mycollab.db.metadata.Table;
import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

@SuppressWarnings("ucd")
@Table("m_tracker_query")
public class Query extends ValuedBean {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.id
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Column("id")
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.queryname
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Length(max=255, message="Field value is too long")
    @Column("queryname")
    private String queryname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.sharetype
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Column("sharetype")
    private Integer sharetype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.createddate
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Column("createddate")
    private Date createddate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.updateddate
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Column("updateddate")
    private Date updateddate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.owner
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Length(max=45, message="Field value is too long")
    @Column("owner")
    private String owner;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.querytext
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Length(max=4000, message="Field value is too long")
    @Column("querytext")
    private String querytext;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.description
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Length(max=1000, message="Field value is too long")
    @Column("description")
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_tracker_query.projectid
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    @Column("projectid")
    private Integer projectid;

    private static final long serialVersionUID = 1;

    public final boolean equals(Object obj) {
        if (obj == null) { return false;}
        if (obj == this) { return true;}
        if (!obj.getClass().isAssignableFrom(getClass())) { return false;}
        Query item = (Query)obj;
        return new EqualsBuilder().append(id, item.id).build();
    }

    public final int hashCode() {
        return new HashCodeBuilder(1433, 1935).append(id).build();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.id
     *
     * @return the value of m_tracker_query.id
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.id
     *
     * @param id the value for m_tracker_query.id
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.queryname
     *
     * @return the value of m_tracker_query.queryname
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public String getQueryname() {
        return queryname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.queryname
     *
     * @param queryname the value for m_tracker_query.queryname
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setQueryname(String queryname) {
        this.queryname = queryname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.sharetype
     *
     * @return the value of m_tracker_query.sharetype
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public Integer getSharetype() {
        return sharetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.sharetype
     *
     * @param sharetype the value for m_tracker_query.sharetype
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setSharetype(Integer sharetype) {
        this.sharetype = sharetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.createddate
     *
     * @return the value of m_tracker_query.createddate
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public Date getCreateddate() {
        return createddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.createddate
     *
     * @param createddate the value for m_tracker_query.createddate
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.updateddate
     *
     * @return the value of m_tracker_query.updateddate
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public Date getUpdateddate() {
        return updateddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.updateddate
     *
     * @param updateddate the value for m_tracker_query.updateddate
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.owner
     *
     * @return the value of m_tracker_query.owner
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public String getOwner() {
        return owner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.owner
     *
     * @param owner the value for m_tracker_query.owner
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.querytext
     *
     * @return the value of m_tracker_query.querytext
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public String getQuerytext() {
        return querytext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.querytext
     *
     * @param querytext the value for m_tracker_query.querytext
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setQuerytext(String querytext) {
        this.querytext = querytext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.description
     *
     * @return the value of m_tracker_query.description
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.description
     *
     * @param description the value for m_tracker_query.description
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_tracker_query.projectid
     *
     * @return the value of m_tracker_query.projectid
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public Integer getProjectid() {
        return projectid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_tracker_query.projectid
     *
     * @param projectid the value for m_tracker_query.projectid
     *
     * @mbggenerated Tue Jun 28 10:34:33 ICT 2016
     */
    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public enum Field {
        id,
        queryname,
        sharetype,
        createddate,
        updateddate,
        owner,
        querytext,
        description,
        projectid;

        public boolean equalTo(Object value) {
            return name().equals(value);
        }
    }
}