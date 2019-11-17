/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tatou_numerique;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "etudiant", catalog = "gestion_etude", schema = "")
@NamedQueries({
    @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e"),
    @NamedQuery(name = "Etudiant.findById", query = "SELECT e FROM Etudiant e WHERE e.id = :id"),
    @NamedQuery(name = "Etudiant.findByNote", query = "SELECT e FROM Etudiant e WHERE e.note = :note"),
    @NamedQuery(name = "Etudiant.findByTaille", query = "SELECT e FROM Etudiant e WHERE e.taille = :taille"),
    @NamedQuery(name = "Etudiant.findByPoid", query = "SELECT e FROM Etudiant e WHERE e.poid = :poid")})
    public class Etudiant implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "note")
    private String note;
    @Basic(optional = false)
    @Column(name = "taille")
    private String taille;
    @Basic(optional = false)
    @Column(name = "poid")
    private String poid;

    public Etudiant() {
    }

    public Etudiant(String id) {
        this.id = id;
    }

    public Etudiant(String id, String note, String taille, String poid) {
        this.id = id;
        this.note = note;
        this.taille = taille;
        this.poid = poid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        String oldNote = this.note;
        this.note = note;
        changeSupport.firePropertyChange("note", oldNote, note);
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        String oldTaille = this.taille;
        this.taille = taille;
        changeSupport.firePropertyChange("taille", oldTaille, taille);
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        String oldPoid = this.poid;
        this.poid = poid;
        changeSupport.firePropertyChange("poid", oldPoid, poid);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etudiant)) {
            return false;
        }
        Etudiant other = (Etudiant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tatou_numerique.Etudiant[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
