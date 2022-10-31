package com.example.demo.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sessions")
public class Session {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="session_id")
	private Integer id;
	@Column(name="stitle")
	private String stitle;
	@Column(name="sessdate")
	private String sessdate;
	@Column(name="description")
	private String description;
	@Column(name="subject")
	private String subject;
	@Column(name="vlink")
	private String vlink;
	@Column(name="attendance_status")
	private Boolean status;
	@Column(name="notespath")
	private String notespath;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStitle() {
		return stitle;
	}
	public void setStitle(String stitle) {
		this.stitle = stitle;
	}
	public String getSessdate() {
		return sessdate;
	}
	public void setSessdate(String sessdate) {
		this.sessdate = sessdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getVlink() {
		return vlink;
	}
	public void setVlink(String vlink) {
		this.vlink = vlink;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getNotespath() {
		return notespath;
	}
	public void setNotespath(String notespath) {
		this.notespath = notespath;
	}
	
	 



	

	 
	 

}
