package ch.santosalves.jot.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sessions database table.
 * 
 */
@Entity
@Table(name="sessions")
@NamedQuery(name="Session.findAll", query="SELECT s FROM Session s")
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=50)
	private String id;

	@Column(nullable=false)
	private int authenticated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_access", nullable=false)
	private Date lastAccess;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_time")
	private Date loginTime;

	@Column(nullable=false)
	private int valid;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Session() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(int authenticated) {
		this.authenticated = authenticated;
	}

	public Date getLastAccess() {
		return this.lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public int getValid() {
		return this.valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}