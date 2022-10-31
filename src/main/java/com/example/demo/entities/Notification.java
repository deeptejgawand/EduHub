package com.example.demo.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="notifications")
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="notification_id")
	private Integer id;
	@Column(name="title")
	private String title;
	@Column(name="ndesc")
	private String ndesc;
	@Column(name="date")
	private String date;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNdesc() {
		return ndesc;
	}
	public void setNdesc(String ndesc) {
		this.ndesc = ndesc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

}
