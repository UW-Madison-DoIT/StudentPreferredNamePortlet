package edu.wisc.portlet.preferred.form;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PreferredName implements Serializable {
    private static final long serialVersionUID = 3L;

    private String firstName;

    private String middleName;

    private String lastName;

    private String pvi;

    private boolean hideSource;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPvi(String pvi) {
        this.pvi = pvi;
    }

    public String getPvi() {
        return this.pvi;
    }

    public boolean isHideSource() {
        return hideSource;
    }

    public void setHideSource(boolean hideSource) {
        this.hideSource = hideSource;
    }

    public PreferredName() {

    }

    public PreferredName(String first, String middle, String last) {
        this.firstName = first;
        this.middleName = middle;
        this.lastName = last;
    }

    public PreferredName(String first, String middle, String last, String pvi) {
        this.firstName = first;
        this.middleName = middle;
        this.lastName = last;
        this.pvi = pvi;
    }

    public PreferredName(String pvi) {
        this.firstName = null;
        this.middleName = null;
        this.lastName = null;
        this.pvi = pvi;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof PreferredName)) {
            return false;
        }
        PreferredName rhs = (PreferredName) object;
        return new EqualsBuilder()
            .append(this.middleName, rhs.middleName).append(this.lastName, rhs.lastName)
            .append(this.hideSource, rhs.hideSource).append(this.firstName, rhs.firstName)
            .append(this.pvi, rhs.pvi).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(262444801, 597203845)
            .append(this.middleName).append(this.lastName).append(this.hideSource)
            .append(this.firstName).append(this.pvi).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
            .append("firstName", this.firstName)
            .append("middleName", this.middleName)
            .append("lastName", this.lastName)
            .append("pvi", this.pvi)
            .append("hideSource", this.hideSource)
            .toString();
    }
}
